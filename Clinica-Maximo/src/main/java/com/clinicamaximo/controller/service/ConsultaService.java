package com.clinicamaximo.controller.service;

import java.time.LocalDate;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.clinicamaximo.controller.dto.ReagendamentoDTO;
import com.clinicamaximo.controller.dto.RealizarAtendimentoDTO;
import com.clinicamaximo.model.Consulta;
import com.clinicamaximo.model.Medico;

@Service
public interface ConsultaService {

	void cadastrar(@Valid Consulta consulta);

	Page<Consulta> consultarTodas(Pageable paginacao);

	Optional<Consulta> consultarPorId(Long id);

	Consulta reagendarDataEHora(Long id, ReagendamentoDTO dto);

	Optional<Page<Consulta>> consultarPorStatus(String status, Pageable paginacao);

	Consulta cancelarConsulta(Long id);

	Consulta realizarConsulta(Long id, RealizarAtendimentoDTO consultaRealizada);

	Page<Consulta> consultarProntuarioPaciente(Long id, Pageable paginacao);

	Page<Consulta> consultarConsultasHojeMedico(Optional<Medico> medico, LocalDate now, Pageable paginacao);

	Page<Consulta> consultarConsultaPeloDia(Optional<Medico> medico, LocalDate data, Pageable paginacao);

}
