package com.clinicamaximo.controller;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.clinicamaximo.model.Endereco;
import com.clinicamaximo.model.Paciente;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PacienteControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void deveriaDevolverUmaListaDePacientes() throws Exception {
		URI uri = new URI("/paciente");
		
		mockMvc
			.perform(MockMvcRequestBuilders.get(uri)
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void deveriaCriarUmPaciente() throws Exception {
		URI uri = new URI("/paciente");
		Endereco endereco = new Endereco("Rua teste", "123", "Teste", "12345-678", "Teste");
		Paciente pacienteForm = new Paciente("Teste", endereco, "PLANO_2");
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		
		mockMvc
			.perform(MockMvcRequestBuilders.post(uri)
					.content(ow.writeValueAsString(pacienteForm))
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isCreated());
	}
	
	@Test
	public void deveriaAtualizarUmPaciente() throws Exception {
		URI uri = new URI("/paciente/1");
		Endereco endereco = new Endereco("Rua teste2", "123", "Teste", "12345-678", "Teste");
		Paciente pacienteAtualizado = new Paciente("Teste", endereco, "PLANO_2");
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		
		mockMvc
			.perform(MockMvcRequestBuilders.put(uri)
					.content(ow.writeValueAsString(pacienteAtualizado))
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
}
