package com.clinicamaximo.controller.service.impl;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.clinicamaximo.controller.service.PacienteService;
import com.clinicamaximo.model.Endereco;
import com.clinicamaximo.model.Paciente;
import com.clinicamaximo.model.PlanoDeSaude;
import com.clinicamaximo.repository.PacienteRepository;

@Component
public class PacienteServiceImpl implements PacienteService {

	@Autowired
	private PacienteRepository pacienteRepository;

	@Override
	public void cadastrar(@Valid Paciente paciente) {
		pacienteRepository.save(paciente);
	}

	@Override
	public Page<Paciente> consultarTodos(Pageable paginacao) {
		Page<Paciente> pacientes = pacienteRepository.findAll(paginacao);
		
		return pacientes;
	}

	@Override
	public Optional<Paciente> consultarPorId(Long id) {
		Optional<Paciente> paciente = pacienteRepository.findById(id);
		
		return paciente;
	}


	@Override
	public Paciente atualizar(Long id, Paciente pacienteAtualizado) {
		Paciente paciente = pacienteRepository.getById(id);
		
		paciente.setNome(pacienteAtualizado.getNome());
		paciente.setEndereco(new Endereco());
		paciente.getEndereco().setLogradouro(pacienteAtualizado.getEndereco().getLogradouro());
		paciente.getEndereco().setNumero(pacienteAtualizado.getEndereco().getNumero());
		paciente.getEndereco().setBairro(pacienteAtualizado.getEndereco().getBairro());
		paciente.getEndereco().setCep(pacienteAtualizado.getEndereco().getCep());
		paciente.getEndereco().setCidade(pacienteAtualizado.getEndereco().getCidade());
		paciente.setPlanoDeSaude(PlanoDeSaude.valueOf(pacienteAtualizado.getPlanoDeSaude().toString()));
		
		return paciente;
	}

	@Override
	public void excluirPorId(Long id) {
		pacienteRepository.deleteById(id);
	}
}
