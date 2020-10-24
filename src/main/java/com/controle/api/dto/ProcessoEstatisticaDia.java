package com.controle.api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.controle.api.model.TipoProcesso;

public class ProcessoEstatisticaDia {

	private TipoProcesso tipo;
	
	private LocalDate dia;
	
	private BigDecimal total;

	public ProcessoEstatisticaDia(TipoProcesso tipo, LocalDate dia, BigDecimal total) {
		
		this.tipo = tipo;
		this.dia = dia;
		this.total = total;
	}

	public TipoProcesso getTipo() {
		return tipo;
	}

	public void setTipo(TipoProcesso tipo) {
		this.tipo = tipo;
	}

	public LocalDate getDia() {
		return dia;
	}

	public void setDia(LocalDate dia) {
		this.dia = dia;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
	
	
}
