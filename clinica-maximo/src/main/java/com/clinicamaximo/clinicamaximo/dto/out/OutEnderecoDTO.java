package com.clinicamaximo.clinicamaximo.dto.out;

import lombok.Data;

@Data
public class OutEnderecoDTO {

	private String logradouro;
	private String numero;
	private String bairro;
	private String cep;
	private String cidade;
}
