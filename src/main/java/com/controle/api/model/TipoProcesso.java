package com.controle.api.model;

public enum TipoProcesso {
	ONLINE("Online"), PRESENCIAL("Presencial");

	private final String descricao;

	TipoProcesso(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
