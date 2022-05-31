package com.clinicamaximo.controller.service.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.clinicamaximo.controller.dto.ReagendamentoDTO;
import com.clinicamaximo.controller.dto.RealizarAtendimentoDTO;
import com.clinicamaximo.controller.service.ConsultaService;
import com.clinicamaximo.model.Consulta;
import com.clinicamaximo.model.Medico;
import com.clinicamaximo.model.Paciente;
import com.clinicamaximo.model.StatusAgendamento;
import com.clinicamaximo.repository.ConsultaRepository;
import com.clinicamaximo.repository.PacienteRepository;

@Component
public class ConsultaServiceImpl implements ConsultaService {
	
	@Autowired
	private ConsultaRepository consultaRepository;
	
	@Autowired
	private PacienteRepository pacienteRepository;

	@Override
	public void cadastrar(@Valid Consulta consulta) {		
		consultaRepository.save(consulta);
	}

	@Override
	public Page<Consulta> consultarTodas(Pageable paginacao) {
		return consultaRepository.findAll(paginacao);
	}

	@Override
	public Optional<Consulta> consultarPorId(Long id) {
		return consultaRepository.findById(id);
	}

	@Override
	public Consulta reagendarDataEHora(Long id, ReagendamentoDTO dto) {
		Consulta consulta = this.consultarPorId(id).get();
		
		if(dto.getNovaData() != null) {
			consulta.setData(LocalDate.parse(dto.getNovaData(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));			
		}
		
		if(dto.getNovaHora() != null) {
			consulta.setHora(LocalTime.parse(dto.getNovaHora()));			
		}
		
		return consulta;
	}

	@Override
	public Optional<Page<Consulta>> consultarPorStatus(String status, Pageable paginacao) {
		return consultaRepository.findByStatus(StatusAgendamento.valueOf(status), paginacao);
	}

	@Override
	public Consulta cancelarConsulta(Long id) {
		Consulta consulta = consultaRepository.findById(id).get();
		
		consulta.setStatus(StatusAgendamento.CANCELADA);
		
		return consulta;
	}

	@Override
	public Consulta realizarConsulta(Long id, RealizarAtendimentoDTO consultaAtualizada) {
		Consulta consulta = consultaRepository.findById(id).get();
		
		consulta.setCid(consultaAtualizada.getCid());
		consulta.setDiagnostico(consultaAtualizada.getDiagnostico());
		consulta.setStatus(StatusAgendamento.REALIZADA);
		
		return consulta;
	}

	@Override
	public Page<Consulta> consultarProntuarioPaciente(Long id, Pageable paginacao) {
		Paciente paciente = pacienteRepository.findById(id).get();
		
		return consultaRepository.findByPaciente(paciente, paginacao);
	}


	@Override
	public Page<Consulta> consultarConsultasHojeMedico(Optional<Medico> medico, LocalDate now, Pageable paginacao) {
		return consultaRepository.findByMedicoAndData(medico, now, paginacao);
	}

	@Override
	public Page<Consulta> consultarConsultaPeloDia(Optional<Medico> medico, LocalDate data, Pageable paginacao) {
		return consultaRepository.findByMedicoAndData(medico, data, paginacao);
	}

}
