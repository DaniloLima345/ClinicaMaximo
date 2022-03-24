package com.clinicamaximo.clinicamaximo.dto.in;

import com.clinicamaximo.clinicamaximo.model.Medico;
import com.clinicamaximo.clinicamaximo.repository.MedicoRepository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InAtualizarMedicoDTO {

	private String nome;
	private InEnderecoDTO endereco = new InEnderecoDTO();
	private String crm;
	private String especialidade;

	public Medico atualizar(Long id, MedicoRepository medicoRepository) {
		Medico medico = medicoRepository.getById(id);
		
		medico.setNome(nome);
		medico.getEndereco().setLogradouro(endereco.getLogradouro());
		medico.getEndereco().setNumero(endereco.getNumero());
		medico.getEndereco().setBairro(endereco.getBairro());
		medico.getEndereco().setCep(endereco.getCep());
		medico.getEndereco().setCidade(endereco.getCidade());
		medico.setCrm(crm);
		medico.setEspecialidade(especialidade);
		
		return medico;
	}
}
