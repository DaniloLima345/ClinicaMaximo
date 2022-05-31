package com.clinicamaximo.model;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

	private String logradouro;
	private String numero;
	private String bairro;
	private String cep;
	private String cidade;
}
