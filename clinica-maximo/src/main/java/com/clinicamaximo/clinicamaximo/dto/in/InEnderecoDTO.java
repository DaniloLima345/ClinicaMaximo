package com.clinicamaximo.clinicamaximo.dto.in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InEnderecoDTO {

	private String logradouro;
	private String numero;
	private String bairro;
	private String cep;
	private String cidade;
}
