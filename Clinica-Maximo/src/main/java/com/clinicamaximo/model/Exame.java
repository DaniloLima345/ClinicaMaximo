package com.clinicamaximo.model;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Exame {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	private String diagnostico;
	private double duracao;
	
	@Column(nullable = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate data;

	@Column(nullable = false)
	private LocalTime hora;

	@ManyToOne(fetch = FetchType.EAGER)
	private Medico medico;

	@ManyToOne(fetch = FetchType.EAGER)
	private Paciente paciente;
	
	@Enumerated(EnumType.STRING)
	private StatusAgendamento status;
	
	public Exame(String nome, double duracao, LocalDate data, LocalTime hora, Medico medico, Paciente paciente) {
		this.nome = nome;
		this.duracao = duracao;
		this.data = data;
		this.hora = hora;
		this.medico = medico;
		this.paciente = paciente;
		this.status = StatusAgendamento.AGENDADA;
	}
}
