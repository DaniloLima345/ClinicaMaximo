package com.clinicamaximo.clinicamaximo.model;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class Endereco {

	private String logradouro;
	private String numero;
	private String bairro;
	private String cep;
	private String cidade;
}
