package com.controle.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.controle.api.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
