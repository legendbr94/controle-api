package com.controle.api.repository.listener;

import javax.persistence.PostLoad;

import org.springframework.util.StringUtils;

import com.controle.api.ControleApiApplication;
import com.controle.api.model.Processo;
import com.controle.api.storage.S3;

public class ProcessoAnexoListener {

	@PostLoad
	public void postLoad(Processo processo) {
		if (StringUtils.hasText(processo.getAnexo())) {
			S3 s3 = ControleApiApplication.getBean(S3.class);
			processo.setUrlanexo(s3.configurarUrl(processo.getAnexo()));
		}
	}

}
