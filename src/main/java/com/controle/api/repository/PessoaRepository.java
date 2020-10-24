package com.controle.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.controle.api.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
	
	//filtro pela descrição do nome da pessoa
	public Page<Pessoa> findByNomeContaining(String nome, Pageable pageable);


}
