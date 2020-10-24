package com.controle.api.resource;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.controle.api.dto.ProcessoEstatisticaCategoria;
import com.controle.api.dto.ProcessoEstatisticaDia;
import com.controle.api.event.RecursoCriadoEvent;
import com.controle.api.exceptionhandler.ControleExceptionHandler.Erro;
import com.controle.api.model.Processo;
import com.controle.api.repository.ProcessoRepository;
import com.controle.api.repository.filter.ProcessoFilter;
import com.controle.api.repository.projection.ResumoProcesso;
import com.controle.api.service.ProcessoService;
import com.controle.api.service.exception.PessoaInexistenteOuInativaException;

@RestController
@RequestMapping("/processos")
public class ProcessoResource {

	@Autowired
	private ProcessoRepository processoRepository;

	@Autowired
	private ProcessoService processoService;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private MessageSource messageSource;
	
//	@Autowired
//	private com.controle.api.storage.S3 s3;

//	@PostMapping("/anexo")
//	public Anexo uploadAnexo(@RequestParam MultipartFile anexo) throws IOException {
//		String nome = s3.salvarTemporariamente(anexo);
//		
//		return new Anexo(nome,s3.configurarUrl(nome));
//	}
	
	// Gerar relatório em PDF
	@GetMapping("/relatorios/por-pessoa")
	public ResponseEntity<byte[]> relatorioPorPessoa(
			@RequestParam @DateTimeFormat (pattern = "yyyy-MM-dd") LocalDate inicio, 
			@RequestParam @DateTimeFormat (pattern = "yyyy-MM-dd") LocalDate fim) throws Exception{
		byte[] relatorio = processoService.relatorioPorPessoa(inicio, fim);
		
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE,
				org.springframework.http.MediaType.APPLICATION_PDF_VALUE)
				.body(relatorio);
	}
	
	
	// Filtro de categoria por dia para estatisticas
	@GetMapping("estatisticas/por-dia")
	public List<ProcessoEstatisticaDia> porDia() {
		return this.processoRepository.porDia(LocalDate.now());
	}

	// Filtro de categoria por conclusão do processo para estatisticas
	@GetMapping("estatisticas/por-categoria")
	public List<ProcessoEstatisticaCategoria> porCategoria() {
		return this.processoRepository.porCategoria(LocalDate.now());
	}

	// filtros de pesquisa
	@GetMapping
	public Page<Processo> pesquisar(ProcessoFilter processoFilter, Pageable pageable) {
		return processoRepository.filtrar(processoFilter, pageable);
	}

	// consultar resumo do processo
	@GetMapping(params = "resumo")
	public Page<ResumoProcesso> resumir(ProcessoFilter processoFilter, Pageable pageable) {
		return processoRepository.resumir(processoFilter, pageable);
	}

	// consultar processo pelo ID
	@GetMapping("/{codigo}")
	public ResponseEntity<Processo> buscarPeloCodigo(@PathVariable Long codigo) {
		Optional<Processo> processo = processoRepository.findById(codigo);
		return processo.isPresent() ? ResponseEntity.ok(processo.get()) : ResponseEntity.notFound().build();
	}

	// salvar processo e retornar location
	@PostMapping
	public ResponseEntity<Processo> criar(@Valid @RequestBody Processo processo, HttpServletResponse response) {
		Processo processoSalva = processoService.salvar(processo);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, processoSalva.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(processoSalva);
	}

	// Deletar processo pelo ID
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		processoRepository.deleteById(codigo);
	}

	// Regra para não cadastrar processo com pessoa inativa ou inexistente
	@ExceptionHandler({ PessoaInexistenteOuInativaException.class })
	public ResponseEntity<Object> handlePessoaInexistenteOuInativaException(PessoaInexistenteOuInativaException ex) {
		String mensagemUsuario = messageSource.getMessage("pessoa.inexistente-ou-inativa", null,
				LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);
	}

	// Atualizar processo pelo codigo
	@PutMapping("/{codigo}")
	public ResponseEntity<Processo> atualizar(@PathVariable Long codigo, @Valid @RequestBody Processo processo) {
		try {
			Processo processoSalvo = processoService.atualizar(codigo, processo);
			return ResponseEntity.ok(processoSalvo);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.notFound().build();
		}
	}

}
