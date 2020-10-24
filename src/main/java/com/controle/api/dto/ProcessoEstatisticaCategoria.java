package com.controle.api.dto;

import java.math.BigDecimal;

import com.controle.api.model.Categoria;

public class ProcessoEstatisticaCategoria {
	
	private Categoria categoria;
	
	private BigDecimal total;

	public ProcessoEstatisticaCategoria(Categoria categoria, BigDecimal total) {
		
		this.categoria = categoria;
		this.total = total;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
	
	
	

}
