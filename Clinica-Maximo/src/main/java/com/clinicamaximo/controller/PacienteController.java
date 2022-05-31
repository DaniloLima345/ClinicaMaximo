package com.clinicamaximo.controller;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.clinicamaximo.controller.service.ConsultaService;
import com.clinicamaximo.controller.service.ExameService;
import com.clinicamaximo.controller.service.PacienteService;
import com.clinicamaximo.model.Consulta;
import com.clinicamaximo.model.Exame;
import com.clinicamaximo.model.Paciente;

@RestController
@RequestMapping("/paciente")
public class PacienteController {

	@Autowired
	private PacienteService pacienteService;
	
	@Autowired
	private ConsultaService consultaService;
	
	@Autowired
	private ExameService exameService;
	
	@Transactional
	@PostMapping
	public ResponseEntity<Paciente> cadastrar(@RequestBody @Valid Paciente paciente, UriComponentsBuilder uriBuilder) {
		pacienteService.cadastrar(paciente);
		
		URI uri = uriBuilder.path("paciente/{id}").buildAndExpand(paciente.getId()).toUri();
		return ResponseEntity.created(uri).body(paciente);
	}
	
	@GetMapping
	public ResponseEntity<Page<Paciente>> consultarTodos(@PageableDefault(sort = "id") Pageable paginacao) {
		Page<Paciente> pacientes = pacienteService.consultarTodos(paginacao);
		
		return ResponseEntity.ok(pacientes);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Paciente> consultarPorId(@PathVariable Long id) {
		Optional<Paciente> paciente = pacienteService.consultarPorId(id);
		
		if(paciente.isPresent()) {
			return ResponseEntity.ok(paciente.get());
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/{id}/prontuarioConsultas")
	public ResponseEntity<Page<Consulta>> consultarProntuarioDeConsultas(@PathVariable Long id, @PageableDefault(sort = "id")
			Pageable paginacao) {
		Optional<Paciente> paciente = pacienteService.consultarPorId(id);
		
		if(paciente.isPresent()) {
			Page<Consulta> prontuario = consultaService.consultarProntuarioPaciente(id, paginacao);
			
			return ResponseEntity.ok(prontuario);
		}
		
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}/prontuarioExames")
	public ResponseEntity<Page<Exame>> consultarProntuarioDeExames(@PathVariable Long id, @PageableDefault(sort = "id")
			Pageable paginacao) {
		Optional<Paciente> paciente = pacienteService.consultarPorId(id);
		
		if(paciente.isPresent()) {
			Page<Exame> prontuario = exameService.consultarProntuarioPaciente(id, paginacao);
			
			return ResponseEntity.ok(prontuario);
		}
		
		return ResponseEntity.noContent().build();
	}
	
	@Transactional
	@PutMapping("/{id}")
	public ResponseEntity<Paciente> atualizar(@PathVariable Long id, @RequestBody Paciente pacienteAtualizado) {
		Optional<Paciente> optional = pacienteService.consultarPorId(id);
		
		if(optional.isPresent()) {
			Paciente paciente = pacienteService.atualizar(id, pacienteAtualizado);
			
			return ResponseEntity.ok(paciente);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@Transactional
	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluir(@PathVariable Long id) {
		Optional<Paciente> optional = pacienteService.consultarPorId(id);
		
		if(optional.isPresent()) {
			pacienteService.excluirPorId(id);
			
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
	
}
