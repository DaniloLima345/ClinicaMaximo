package com.clinicamaximo.clinicamaximo.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Paciente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String cpf;
	
	@Column(nullable = false)
	private String nomePaciente;
	private String dataNascimento;
	
	@Enumerated(EnumType.STRING)
	private PlanoDeSaude planoSaude;
	private String telefone;
	
	@Embedded
	private Endereco endereco = new Endereco();
	
	public Paciente(String cpf, String nomePaciente, String dataNascimento, String planoSaude, String telefone, 
			String logradouro, String numero, String bairro, String cep, String cidade) {
		this.cpf = cpf;
		this.nomePaciente = nomePaciente;
		this.dataNascimento = dataNascimento;
		this.planoSaude = PlanoDeSaude.valueOf(planoSaude);
		this.telefone = telefone;
		this.endereco.setLogradouro(logradouro);
		this.endereco.setNumero(numero);
		this.endereco.setBairro(bairro);
		this.endereco.setCep(cep);
		this.endereco.setCidade(cidade);
	}
}
