package com.clinicamaximo.controller.service;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.clinicamaximo.model.Medico;

@Service
public interface MedicoService {

	void cadastrar(@Valid Medico medico);

	Page<Medico> consultarTodos(Pageable paginacao);

	Page<Medico> consultarPorEspecialidade(Pageable paginacao, String especialidade);

	Optional<Medico> consultarPorId(Long id);

	Medico atualizar(Long id, Medico medicoAtualizado);

	void excluir(Long id);

}
