package com.clinicamaximo.clinicamaximo.dto.in;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.clinicamaximo.clinicamaximo.model.Paciente;

import lombok.Data;

@Data
public class InPacienteDTO {

	@NotNull
	@NotBlank
	private String nomePaciente;
	private String cpf;
	private String dataNascimento;
	private String planoSaude;
	private String telefone;
	private InEnderecoDTO endereco;
	
	public Paciente converter() {
		
		return new Paciente(this.cpf, this.nomePaciente, this.dataNascimento, this.planoSaude, this.telefone, 
				this.endereco.getLogradouro(), this.endereco.getNumero(),this.endereco.getBairro(), this.endereco.getCep(), 
				this.endereco.getCidade());
		
	}
}
