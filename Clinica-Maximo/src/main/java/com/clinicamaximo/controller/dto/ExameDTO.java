package com.clinicamaximo.controller.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.clinicamaximo.controller.service.MedicoService;
import com.clinicamaximo.controller.service.PacienteService;
import com.clinicamaximo.model.Exame;
import com.clinicamaximo.model.Medico;
import com.clinicamaximo.model.Paciente;

import lombok.Data;

@Data
public class ExameDTO {

	private String nome;
	private double duracao;
	private LocalDate data;
	private LocalTime hora;
	private Long medico_id;
	private Long paciente_id;
	
	public Exame converter(PacienteService pacienteService, MedicoService medicoService) {
		Paciente paciente = pacienteService.consultarPorId(this.paciente_id).get();
		Medico medico = medicoService.consultarPorId(this.medico_id).get();
		
		Exame exame = new Exame(nome, duracao, data, hora, medico, paciente);
		
		return exame;
	}
}
