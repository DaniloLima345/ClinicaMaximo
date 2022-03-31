package com.clinicamaximo.clinicamaximo.dto.in;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.clinicamaximo.clinicamaximo.model.Medico;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InMedicoDTO {

	@NotNull
	@NotBlank
	private String nome;
	private InEnderecoDTO endereco;
	private String crm;
	private String especialidade;

	public Medico converter() {
		return new Medico(this.nome, this.endereco.getLogradouro(), this.endereco.getNumero(),
				this.endereco.getBairro(), this.endereco.getCep(), this.endereco.getCidade(),
				this.crm, this.especialidade);
	}
}
