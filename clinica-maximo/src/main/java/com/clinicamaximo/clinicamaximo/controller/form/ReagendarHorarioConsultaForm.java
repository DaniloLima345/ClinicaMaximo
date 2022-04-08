package com.clinicamaximo.clinicamaximo.controller.form;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.clinicamaximo.clinicamaximo.model.Consulta;
import com.clinicamaximo.clinicamaximo.repository.ConsultaRepository;

import lombok.Data;

@Data
public class ReagendarHorarioConsultaForm {

	@NotBlank
	@NotNull
	private String novaDataEHora;

	public Consulta atualizar(Long id, ConsultaRepository consultaRepository) {
		Consulta consulta = consultaRepository.getById(id);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		consulta.setDataEHora(LocalDateTime.parse(novaDataEHora, formatter));
		
		return consulta;
	}
}
