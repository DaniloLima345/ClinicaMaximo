package com.clinicamaximo.controller.service.impl;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.clinicamaximo.controller.service.MedicoService;
import com.clinicamaximo.model.Medico;
import com.clinicamaximo.repository.MedicoRepository;

@Component
public class MedicoServiceImpl implements MedicoService {

	@Autowired
	private MedicoRepository medicoRepository;
	
	@Override
	public void cadastrar(@Valid Medico medico) {
		medicoRepository.save(medico);
	}

	@Override
	public Page<Medico> consultarTodos(Pageable paginacao) {
		return medicoRepository.findAll(paginacao);
	}

	@Override
	public Page<Medico> consultarPorEspecialidade(Pageable paginacao, String especialidade) {
		return medicoRepository.findByEspecialidade(especialidade, paginacao);
	}

	@Override
	public Optional<Medico> consultarPorId(Long id) {
		return medicoRepository.findById(id);
	}

	@Override
	public Medico atualizar(Long id, Medico medicoAtualizado) {
		Medico medico = medicoRepository.getById(id);
		
		medico.setNome(medicoAtualizado.getNome());
		medico.setCrm(medicoAtualizado.getCrm());
		medico.getEndereco().setLogradouro(medicoAtualizado.getEndereco().getLogradouro());
		medico.getEndereco().setNumero(medicoAtualizado.getEndereco().getNumero());
		medico.getEndereco().setBairro(medicoAtualizado.getEndereco().getBairro());
		medico.getEndereco().setCep(medicoAtualizado.getEndereco().getCep());
		medico.getEndereco().setCidade(medicoAtualizado.getEndereco().getCidade());
		medico.setEspecialidade(medicoAtualizado.getEspecialidade());
		
		return medico;
	}

	@Override
	public void excluir(Long id) {
		medicoRepository.deleteById(id);
	}

}
