package com.clinicamaximo.clinicamaximo.controller.form;

import com.clinicamaximo.clinicamaximo.model.Consulta;
import com.clinicamaximo.clinicamaximo.model.StatusConsulta;
import com.clinicamaximo.clinicamaximo.repository.ConsultaRepository;

import lombok.Data;

@Data
public class DiagnosticoConsultaForm {

	private String cid;
	private String diagnostico;
	
	public Consulta atualizar(Long id, ConsultaRepository consultaRepository) {
		Consulta consulta = consultaRepository.getById(id);
		
		consulta.setCid(cid);
		consulta.setDiagnostico(diagnostico);
		consulta.setStatus(StatusConsulta.REALIZADA);
		
		return consulta;
	}
}
