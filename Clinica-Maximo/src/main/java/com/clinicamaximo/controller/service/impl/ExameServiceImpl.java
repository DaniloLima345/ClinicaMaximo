package com.clinicamaximo.controller.service.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.clinicamaximo.controller.dto.ReagendamentoDTO;
import com.clinicamaximo.controller.dto.RealizarAtendimentoDTO;
import com.clinicamaximo.controller.service.ExameService;
import com.clinicamaximo.model.Exame;
import com.clinicamaximo.model.Medico;
import com.clinicamaximo.model.Paciente;
import com.clinicamaximo.model.StatusAgendamento;
import com.clinicamaximo.repository.ExameRepository;
import com.clinicamaximo.repository.PacienteRepository;

@Component
public class ExameServiceImpl implements ExameService {

	@Autowired
	private ExameRepository exameRepository;
	
	@Autowired
	private PacienteRepository pacienteRepository;
	
	@Override
	public void cadastrar(Exame exame) {
		exameRepository.save(exame);
	}

	@Override
	public Page<Exame> consultarTodos(Pageable paginacao) {
		return exameRepository.findAll(paginacao);
	}

	@Override
	public Optional<Exame> consultarPorId(Long id) {
		return exameRepository.findById(id);
	}

	@Override
	public Optional<Page<Exame>> consultarPorNome(String nome, Pageable paginacao) {
		return exameRepository.findByNome(nome, paginacao);
	}

	@Override
	public Optional<Page<Exame>> consultarPorStatus(String status, Pageable paginacao) {
		return exameRepository.findByStatus(StatusAgendamento.valueOf(status), paginacao);
	}

	@Override
	public Exame reagendarDataEHora(Long id, ReagendamentoDTO dto) {
		Exame exame = this.consultarPorId(id).get();

		if(dto.getNovaData() != null) {
			exame.setData(LocalDate.parse(dto.getNovaData(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		}
		
		if(dto.getNovaHora() != null) {
			exame.setHora(LocalTime.parse(dto.getNovaHora()));
		}
		
		return exame;
	}

	@Override
	public Exame cancelarExame(Long id) {
		Exame exame = exameRepository.findById(id).get();
		
		exame.setStatus(StatusAgendamento.CANCELADA);
		
		return exame;
	}

	@Override
	public Exame realizarExame(Long id, RealizarAtendimentoDTO dto) {
		Exame exame = exameRepository.findById(id).get();
		
		exame.setStatus(StatusAgendamento.REALIZADA);
		exame.setDiagnostico(dto.getDiagnostico());
		
		return exame;
	}

	@Override
	public Page<Exame> consultarProntuarioPaciente(Long id, Pageable paginacao) {
		Paciente paciente = pacienteRepository.findById(id).get();
		
		return exameRepository.findByPaciente(paciente, paginacao);
	}

	@Override
	public Page<Exame> consultarExamesHojeMedico(Optional<Medico> medico, LocalDate now, Pageable paginacao) {
		return exameRepository.findByMedicoAndData(medico, now, paginacao);
	}

	@Override
	public Page<Exame> consultarExamesPeloDia(Optional<Medico> medico, LocalDate parse, Pageable paginacao) {
		return exameRepository.findByMedicoAndData(medico, parse, paginacao);
	}
}
