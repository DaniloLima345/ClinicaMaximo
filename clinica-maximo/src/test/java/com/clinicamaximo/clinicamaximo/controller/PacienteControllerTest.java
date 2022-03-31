package com.clinicamaximo.clinicamaximo.controller;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.clinicamaximo.clinicamaximo.dto.in.InEnderecoDTO;
import com.clinicamaximo.clinicamaximo.dto.in.InPacienteDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PacienteControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void deveriaRetornarUmaListaDePacientes() throws Exception{
		URI uri = new URI("/pacientes");
		
		mockMvc
			.perform(MockMvcRequestBuilders.get(uri)
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void deveriaCriarUmPaciente() throws Exception{
		URI uri = new URI("/pacientes");
		
		InEnderecoDTO enderecoForm = new InEnderecoDTO("Rua teste0", "123", "Teste0", "12345-678", "Teste0");
		InPacienteDTO pacienteForm = new InPacienteDTO("Teste1", "123456789", "01/01/2000", "PLANO_1", "123456", enderecoForm);
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		
		mockMvc
			.perform(MockMvcRequestBuilders.post(uri)
					.content(ow.writeValueAsString(pacienteForm))
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isCreated());
	}
	
	@Test
	public void deveriaAtualizarUmPaciente() throws Exception{
		URI uri = new URI("/pacientes/2");
		
		InEnderecoDTO enderecoForm = new InEnderecoDTO("Rua teste1", "1234", "Teste1", "12345-678", "Teste1");
		InPacienteDTO pacienteForm = new InPacienteDTO("Teste2", "987654321", "30/12/2000", "PLANO_2", "654321", enderecoForm);
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		
		mockMvc
			.perform(MockMvcRequestBuilders.put(uri)
					.content(ow.writeValueAsString(pacienteForm))
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
}
