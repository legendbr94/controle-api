package com.controle.api.dto;

import java.math.BigDecimal;

import com.controle.api.model.Pessoa;
import com.controle.api.model.TipoProcesso;

public class ProcessoEstatisticaPessoa {

	private TipoProcesso tipo;
	
	private Pessoa pessoa;
	
	private BigDecimal total;

	public ProcessoEstatisticaPessoa(TipoProcesso tipo, Pessoa pessoa, BigDecimal total) {
		
		this.tipo = tipo;
		this.pessoa = pessoa;
		this.total = total;
	}

	public TipoProcesso getTipo() {
		return tipo;
	}

	public void setTipo(TipoProcesso tipo) {
		this.tipo = tipo;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
	
}
