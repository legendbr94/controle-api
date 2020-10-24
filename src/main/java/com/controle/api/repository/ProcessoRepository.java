package com.controle.api.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.controle.api.model.Processo;
import com.controle.api.repository.processo.ProcessoRepositoryQuery;

public interface ProcessoRepository extends JpaRepository<Processo, Long>,ProcessoRepositoryQuery {

	
		
		List<Processo> findByConclusaoLessThanEqualAndInicioIsNull(LocalDate data);
}
