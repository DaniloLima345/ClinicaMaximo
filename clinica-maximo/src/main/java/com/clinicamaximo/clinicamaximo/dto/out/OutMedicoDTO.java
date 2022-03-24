package com.clinicamaximo.clinicamaximo.dto.out;

import org.springframework.data.domain.Page;

import com.clinicamaximo.clinicamaximo.model.Medico;

import lombok.Getter;

@Getter
public class OutMedicoDTO {
	
	private String nome;
	private String crm;
	private String especialidade;
	private OutEnderecoDTO endereco = new OutEnderecoDTO();
	
	public OutMedicoDTO(Medico medico) {
		this.nome = medico.getNome();
		this.endereco.setLogradouro(medico.getEndereco().getLogradouro());
		this.endereco.setNumero(medico.getEndereco().getNumero());
		this.endereco.setBairro(medico.getEndereco().getBairro());
		this.endereco.setCep(medico.getEndereco().getCep());
		this.endereco.setCidade(medico.getEndereco().getCidade());
		this.crm = medico.getCrm();
		this.especialidade = medico.getEspecialidade();
	}

	public static Page<OutMedicoDTO> converter(Page<Medico> medicos) {
		return medicos.map(OutMedicoDTO::new);
	}

	public static OutMedicoDTO converterApenasUm(Medico medico) {
		return new OutMedicoDTO(medico);
	}

}
