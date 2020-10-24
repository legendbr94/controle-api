package com.controle.api.service;

import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.controle.api.dto.ProcessoEstatisticaPessoa;
import com.controle.api.model.Pessoa;
import com.controle.api.model.Processo;
import com.controle.api.model.Usuario;
import com.controle.api.repository.PessoaRepository;
import com.controle.api.repository.ProcessoRepository;
import com.controle.api.repository.UsuarioRepository;
import com.controle.api.service.exception.PessoaInexistenteOuInativaException;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class ProcessoService {

	private static final String DESTINATARIOS = "";

	private static final Logger logger = LoggerFactory.getLogger(ProcessoService.class);

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private ProcessoRepository processoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	//@Autowired
	//private Mailer mailer;

	//@Autowired
	//private S3 s3;

	// Verificação de processos vencidos e envio de e-mail as 06:00 da manhã
	@Scheduled(cron = "0 0 6 * * *")
	public void avisarSobreProcessosVencidos() {
		if (logger.isDebugEnabled()) {
			logger.debug("Preparando envio de" + "e-mails de processos vencidos.");

		}
		List<Processo> vencidos = processoRepository.findByConclusaoLessThanEqualAndInicioIsNull(LocalDate.now());

		if (vencidos.isEmpty()) {
			logger.info("Sem processos vencidos para aviso");
			return;
		}

		logger.info("Existem {} processos vencidos.", vencidos.size());

		List<Usuario> destinatarios = usuarioRepository.findByPermissoesDescricao(DESTINATARIOS);

		if (destinatarios.isEmpty()) {
			logger.warn("Existem processos vencidos, mas" + "não foram encontrados destinatarios.");
			return;
		}

		//mailer.avisarSobreProcessosVencidos(vencidos, destinatarios);

		logger.info("Envio de e-mail de aviso concluído");
	}

	// Metodo de impressão relatorios JASPER
	public byte[] relatorioPorPessoa(LocalDate inicio, LocalDate fim) throws Exception {
		List<ProcessoEstatisticaPessoa> dados = processoRepository.porPessoa(inicio, fim);

		Map<String, Object> parametros = new HashMap<>();
		parametros.put("DT_INICIO", Date.valueOf(inicio));
		parametros.put("DT_FIM", Date.valueOf(fim));
		parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));

		InputStream inputStream = this.getClass().getResourceAsStream("/relatorios/processos-por-pessoa.jasper");

		JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros,
				new JRBeanCollectionDataSource(dados));

		return JasperExportManager.exportReportToPdf(jasperPrint);

	}

	public Processo salvar(Processo processo) {
		validarPessoa(processo);

//		if (StringUtils.hasText(processo.getAnexo())) {
//			s3.salvar(processo.getAnexo());
//		}

		return processoRepository.save(processo);
	}

	public Processo atualizar(Long codigo, Processo processo) {
		Processo processoSalvo = buscarProcessoExistente(codigo);
		if (!processo.getPessoa().equals(processoSalvo.getPessoa())) {
			validarPessoa(processo);
		}

//		if (StringUtils.isEmpty(processo.getAnexo()) && StringUtils.hasText(processoSalvo.getAnexo())) {
//			s3.remover(processoSalvo.getAnexo());
//		} else if (StringUtils.hasText(processo.getAnexo()) && !processo.getAnexo().equals(processoSalvo.getAnexo())) {
//			s3.substituir(processoSalvo.getAnexo(), processo.getAnexo());
//		}

		BeanUtils.copyProperties(processo, processoSalvo, "codigo");

		return processoRepository.save(processoSalvo);
	}

	private void validarPessoa(Processo processo) {
		Pessoa pessoa = null;
		if (processo.getPessoa().getCodigo() != null) {
			pessoa = pessoaRepository.getOne(processo.getPessoa().getCodigo());
		}

		if (pessoa == null || pessoa.isInativo()) {
			throw new PessoaInexistenteOuInativaException();
		}
	}

	private Processo buscarProcessoExistente(Long codigo) {
		Optional<Processo> processoSalvo = processoRepository.findById(codigo);
		if (!processoSalvo.isPresent()) {
			throw new IllegalArgumentException();
		}
		return processoSalvo.get();
	}

}
