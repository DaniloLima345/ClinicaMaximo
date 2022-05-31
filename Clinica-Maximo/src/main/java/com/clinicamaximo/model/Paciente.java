package com.clinicamaximo.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Paciente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	@NotBlank
	@NotNull
	private String nome;
	
	@Embedded
	private Endereco endereco = new Endereco();
	
	@Enumerated(EnumType.STRING)
	private PlanoDeSaude planoDeSaude;
	
	public Paciente(String nome, Endereco endereco, String planoDeSaude) {
		this.nome = nome;
		this.endereco = endereco;
		this.planoDeSaude = PlanoDeSaude.valueOf(planoDeSaude);
	}
}
