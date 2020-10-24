package com.controle.api.repository.processo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.controle.api.dto.ProcessoEstatisticaCategoria;
import com.controle.api.dto.ProcessoEstatisticaDia;
import com.controle.api.dto.ProcessoEstatisticaPessoa;
import com.controle.api.model.Processo;
import com.controle.api.repository.filter.ProcessoFilter;
import com.controle.api.repository.projection.ResumoProcesso;

public interface ProcessoRepositoryQuery {
	
	public List<ProcessoEstatisticaPessoa> porPessoa(LocalDate inicio, LocalDate fim);
	public List<ProcessoEstatisticaCategoria> porCategoria(LocalDate mesReferencia);
	public List<ProcessoEstatisticaDia> porDia(LocalDate mesReferencia);
	
	//Método do filtro de processo
	public Page<Processo> filtrar (ProcessoFilter processoFilter, Pageable pageable);
	
	//Método do resumo do processo
	public Page<ResumoProcesso> resumir(ProcessoFilter processoFilter, Pageable pageable);

}
