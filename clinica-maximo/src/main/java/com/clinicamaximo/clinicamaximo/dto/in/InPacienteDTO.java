package com.clinicamaximo.clinicamaximo.dto.in;

import com.clinicamaximo.clinicamaximo.model.Paciente;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InPacienteDTO {

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
