package com.controle.api.repository.filter;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class ProcessoFilter {
	
	private String nome;
	private String solicitacao;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataInicioDe;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataInicioAte;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataConclusaoDe;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataConclusaoAte;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataVendaDe;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataVendaAte;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSolicitacao() {
		return solicitacao;
	}
	public void setSolicitacao(String solicitacao) {
		this.solicitacao = solicitacao;
	}
	public LocalDate getDataInicioDe() {
		return dataInicioDe;
	}
	public void setDataInicioDe(LocalDate dataInicioDe) {
		this.dataInicioDe = dataInicioDe;
	}
	public LocalDate getDataInicioAte() {
		return dataInicioAte;
	}
	public void setDataInicioAte(LocalDate dataInicioAte) {
		this.dataInicioAte = dataInicioAte;
	}
	public LocalDate getDataConclusaoDe() {
		return dataConclusaoDe;
	}
	public void setDataConclusaoDe(LocalDate dataConclusaoDe) {
		this.dataConclusaoDe = dataConclusaoDe;
	}
	public LocalDate getDataConclusaoAte() {
		return dataConclusaoAte;
	}
	public void setDataConclusaoAte(LocalDate dataConclusaoAte) {
		this.dataConclusaoAte = dataConclusaoAte;
	}
	public LocalDate getDataVendaDe() {
		return dataVendaDe;
	}
	public void setDataVendaDe(LocalDate dataVendaDe) {
		this.dataVendaDe = dataVendaDe;
	}
	public LocalDate getDataVendaAte() {
		return dataVendaAte;
	}
	public void setDataVendaAte(LocalDate dataVendaAte) {
		this.dataVendaAte = dataVendaAte;
	}

	
}
