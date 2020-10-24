package com.controle.api.repository.projection;

import java.math.BigDecimal;
import java.time.LocalDate;


import com.controle.api.model.TipoProcesso;

public class ResumoProcesso {
	
	private Long codigo;
	private String status;
	private String solicitacao;
	private String nome;
	private TipoProcesso tipo;
	private LocalDate inicio;
	private LocalDate conclusao;
	private String duracao;
	private String implantador;
	private String vendedor;
	private LocalDate dataVenda;	
	private BigDecimal valorContrato;
	private String descricao;
	private String observacao;
	private String categoria;
	private String pessoa;
	
	
	
	public ResumoProcesso(Long codigo, String status, String solicitacao, String nome, TipoProcesso tipo,
			LocalDate inicio, LocalDate conclusao, String duracao, String implantador, String vendedor,
			LocalDate dataVenda, BigDecimal valorContrato, String descricao, String observacao, String categoria,
			String pessoa) {
		this.codigo = codigo;
		this.status = status;
		this.solicitacao = solicitacao;
		this.nome = nome;
		this.tipo = tipo;
		this.inicio = inicio;
		this.conclusao = conclusao;
		this.duracao = duracao;
		this.implantador = implantador;
		this.vendedor = vendedor;
		this.dataVenda = dataVenda;
		this.valorContrato = valorContrato;
		this.descricao = descricao;
		this.observacao = observacao;
		this.categoria = categoria;
		this.pessoa = pessoa;
	}
	
	
	
	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSolicitacao() {
		return solicitacao;
	}
	public void setSolicitacao(String solicitacao) {
		this.solicitacao = solicitacao;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public TipoProcesso getTipo() {
		return tipo;
	}
	public void setTipo(TipoProcesso tipo) {
		this.tipo = tipo;
	}
	public LocalDate getInicio() {
		return inicio;
	}
	public void setInicio(LocalDate inicio) {
		this.inicio = inicio;
	}
	public LocalDate getConclusao() {
		return conclusao;
	}
	public void setConclusao(LocalDate conclusao) {
		this.conclusao = conclusao;
	}
	public String getDuracao() {
		return duracao;
	}
	public void setDuracao(String duracao) {
		this.duracao = duracao;
	}
	public String getImplantador() {
		return implantador;
	}
	public void setImplantador(String implantador) {
		this.implantador = implantador;
	}
	public String getVendedor() {
		return vendedor;
	}
	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}
	public LocalDate getDataVenda() {
		return dataVenda;
	}
	public void setDataVenda(LocalDate dataVenda) {
		this.dataVenda = dataVenda;
	}
	public BigDecimal getValorContrato() {
		return valorContrato;
	}
	public void setValorContrato(BigDecimal valorContrato) {
		this.valorContrato = valorContrato;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getPessoa() {
		return pessoa;
	}
	public void setPessoa(String pessoa) {
		this.pessoa = pessoa;
	}
	
	
	
}
