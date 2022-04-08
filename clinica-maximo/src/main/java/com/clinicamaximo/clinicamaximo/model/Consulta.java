package com.clinicamaximo.clinicamaximo.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.clinicamaximo.clinicamaximo.repository.MedicoRepository;
import com.clinicamaximo.clinicamaximo.repository.PacienteRepository;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Consulta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private LocalDateTime dataEHora;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Medico medico;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Paciente paciente;
	
	private String cid;
	private String diagnostico;
	
	@Enumerated(EnumType.STRING)
	private StatusConsulta status;
	
	public Consulta(@NotNull @NotBlank LocalDateTime dataEHota, Medico medico, Paciente paciente) {
		this.dataEHora = dataEHota;
		this.medico = medico;
		this.paciente = paciente;
		this.status = StatusConsulta.AGENDADA;
		this.cid = "";
		this.diagnostico = "";
	}

	public Consulta convereter(PacienteRepository pacienteRepository, MedicoRepository medicoRepository) {
		Medico medico = medicoRepository.findById(this.medico.getId()).get();
		Paciente paciente = pacienteRepository.findById(this.paciente.getId()).get();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		
		return new Consulta(LocalDateTime.parse(this.dataEHora.toString(), formatter), medico, paciente);
	}
}
