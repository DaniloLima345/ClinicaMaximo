package com.clinicamaximo.clinicamaximo.dto.out;

import org.springframework.data.domain.Page;

import com.clinicamaximo.clinicamaximo.model.Paciente;

import lombok.Getter;

@Getter
public class OutPacienteDTO {

	private String nome;
	private String cpf;
	private String dataNascimento;
	private String planoSaude;
	private String telefone;
	private OutEnderecoDTO endereco = new OutEnderecoDTO();
	
	public OutPacienteDTO(Paciente paciente) {
		this.nome = paciente.getNomePaciente();
		this.cpf = paciente.getCpf();
		this.dataNascimento = paciente.getDataNascimento();
		this.planoSaude = paciente.getPlanoSaude().toString();
		this.telefone = paciente.getTelefone();
		this.endereco.setBairro(paciente.getEndereco().getBairro());
		this.endereco.setCep(paciente.getEndereco().getCep());
		this.endereco.setCidade(paciente.getEndereco().getCidade());
		this.endereco.setLogradouro(paciente.getEndereco().getLogradouro());
		this.endereco.setNumero(paciente.getEndereco().getNumero());
	}
	
	public static Page<OutPacienteDTO> converter(Page<Paciente> pacientes) {
		return pacientes.map(OutPacienteDTO::new);
	}

	public static OutPacienteDTO converterApenasUm(Paciente paciente) {
		return new OutPacienteDTO(paciente);
	}
}
