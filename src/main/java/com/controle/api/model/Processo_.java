package com.controle.api.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Processo.class)
public abstract class Processo_ {

	public static volatile SingularAttribute<Processo, Long> codigo;
	public static volatile SingularAttribute<Processo, String> observacao;
	public static volatile SingularAttribute<Processo, TipoProcesso> tipo;
	public static volatile SingularAttribute<Processo, LocalDate> inicio;
	public static volatile SingularAttribute<Processo, Pessoa> pessoa;
	public static volatile SingularAttribute<Processo, String> anexo;
	public static volatile SingularAttribute<Processo, LocalDate> conclusao;
	public static volatile SingularAttribute<Processo, Categoria> categoria;
	public static volatile SingularAttribute<Processo, BigDecimal> valor;
	public static volatile SingularAttribute<Processo, String> descricao;

}

