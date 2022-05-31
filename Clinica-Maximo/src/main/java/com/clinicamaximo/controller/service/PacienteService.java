package com.clinicamaximo.controller.service;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.clinicamaximo.model.Paciente;

@Service
public interface PacienteService {

	void cadastrar(@Valid Paciente paciente);

	Page<Paciente> consultarTodos(Pageable paginacao);

	Optional<Paciente> consultarPorId(Long id);

	Paciente atualizar(Long id, Paciente pacienteAtualizado);

	void excluirPorId(Long id);

}
