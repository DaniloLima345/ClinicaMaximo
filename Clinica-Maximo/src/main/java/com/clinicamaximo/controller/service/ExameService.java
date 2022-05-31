package com.clinicamaximo.controller.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.clinicamaximo.controller.dto.ReagendamentoDTO;
import com.clinicamaximo.controller.dto.RealizarAtendimentoDTO;
import com.clinicamaximo.model.Exame;
import com.clinicamaximo.model.Medico;

@Service
public interface ExameService {

	void cadastrar(Exame exame);

	Page<Exame> consultarTodos(Pageable paginacao);

	Optional<Exame> consultarPorId(Long id);

	Optional<Page<Exame>> consultarPorNome(String nome, Pageable paginacao);

	Optional<Page<Exame>> consultarPorStatus(String status, Pageable paginacao);

	Exame reagendarDataEHora(Long id, ReagendamentoDTO dto);

	Exame cancelarExame(Long id);

	Exame realizarExame(Long id, RealizarAtendimentoDTO dto);

	Page<Exame> consultarProntuarioPaciente(Long id, Pageable paginacao);

	Page<Exame> consultarExamesHojeMedico(Optional<Medico> medico, LocalDate now, Pageable paginacao);

	Page<Exame> consultarExamesPeloDia(Optional<Medico> medico, LocalDate parse, Pageable paginacao);

}
