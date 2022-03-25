package com.clinicamaximo.clinicamaximo.dto.in;

import com.clinicamaximo.clinicamaximo.model.Paciente;
import com.clinicamaximo.clinicamaximo.model.PlanoDeSaude;
import com.clinicamaximo.clinicamaximo.repository.PacienteRepository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InAtualizarPacienteDTO {

	private String nome;
	private String cpf;
	private InEnderecoDTO endereco = new InEnderecoDTO();
	private String dataNascimento;
	private String planoSaude;
	private String telefone;
	
	public Paciente atualizar(Long id, PacienteRepository pacienteRepository) {
		Paciente paciente = pacienteRepository.getById(id);
		
		paciente.setNomePaciente(nome);
		paciente.setCpf(cpf);
		paciente.getEndereco().setBairro(endereco.getBairro());
		paciente.getEndereco().setCep(endereco.getCep());
		paciente.getEndereco().setCidade(endereco.getCidade());
		paciente.getEndereco().setLogradouro(endereco.getLogradouro());
		paciente.getEndereco().setNumero(endereco.getNumero());
		paciente.setDataNascimento(dataNascimento);
		paciente.setPlanoSaude(PlanoDeSaude.valueOf(planoSaude));
		paciente.setTelefone(telefone);
		
		return paciente;
	}
}
