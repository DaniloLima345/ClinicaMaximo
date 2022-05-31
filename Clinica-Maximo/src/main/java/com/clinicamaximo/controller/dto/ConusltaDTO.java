package com.clinicamaximo.controller.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.clinicamaximo.controller.service.MedicoService;
import com.clinicamaximo.controller.service.PacienteService;
import com.clinicamaximo.model.Consulta;
import com.clinicamaximo.model.Medico;
import com.clinicamaximo.model.Paciente;

import lombok.Data;

@Data
public class ConusltaDTO {

	private String cid;
	private LocalDate data;
	private LocalTime hora;
	private String diagnostico;
	private Long medico_id;
	private Long paciente_id;
	
	public Consulta converter(PacienteService pacienteService, MedicoService medicoService) {
		Paciente paciente = pacienteService.consultarPorId(this.paciente_id).get();
		Medico medico = medicoService.consultarPorId(this.medico_id).get();
		
		Consulta consulta = new Consulta(this.cid, this.data, this.hora, this.diagnostico,
				medico, paciente);
		
		return consulta;
	}
}
