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
import com.clinicamaximo.clinicamaximo.dto.in.InMedicoDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MedicoControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void deveriaRetornarUmaListaDeMedicos() throws Exception {
		URI uri = new URI("/medicos");
		
		mockMvc
			.perform(MockMvcRequestBuilders.get(uri)
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void deveriaRetornarUmaListaDeMedicosPorEspecialidade() throws Exception {
		URI uri = new URI("/medicos/especialidade=Testologista");
		
		mockMvc
			.perform(MockMvcRequestBuilders.get(uri)
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void deveriaCriarUmMedico() throws Exception {
		URI uri = new URI("/medicos");
		InEnderecoDTO enderecoForm = new InEnderecoDTO("Rua teste", "123", "Teste", "12345-678", "Teste");
		InMedicoDTO medicoForm = new InMedicoDTO("Teste", enderecoForm, "123456789", "Endocrinologista");
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

		mockMvc
			.perform(MockMvcRequestBuilders.post(uri)
					.content(ow.writeValueAsString(medicoForm))
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isCreated());
	}
	
	@Test
	public void deveriaAtualizarUmMedico() throws Exception {
		URI uri = new URI("/medicos/3");
		InEnderecoDTO enderecoForm = new InEnderecoDTO("Rua teste", "123", "Teste", "12345-678", "Teste");
		InMedicoDTO medicoForm = new InMedicoDTO("Teste", enderecoForm, "123456789", "Oncologista");
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		
		mockMvc
			.perform(MockMvcRequestBuilders.put(uri)
					.content(ow.writeValueAsString(medicoForm))
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
}
