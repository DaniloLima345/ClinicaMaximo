package com.clinicamaximo.controller;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import com.clinicamaximo.controller.service.MedicoService;
import com.clinicamaximo.model.Consulta;
import com.clinicamaximo.model.Exame;
import com.clinicamaximo.model.Medico;

@RestController
@RequestMapping("/medico")
public class MedicoController {
	
	@Autowired
	private MedicoService medicoService;
	
	@Autowired
	private ConsultaService consultaService;
	
	@Autowired
	private ExameService exameService;

	@Transactional
	@PostMapping
	public ResponseEntity<Medico> cadastrar(@RequestBody @Valid Medico medico, UriComponentsBuilder uriBuilder) {
		medicoService.cadastrar(medico);
		
		URI uri = uriBuilder.path("medico/{id}").buildAndExpand(medico.getId()).toUri();
		return ResponseEntity.created(uri).body(medico);
	}
	
	@GetMapping
	public ResponseEntity<Page<Medico>> consultarTodos(@PageableDefault(sort = "id") Pageable paginacao) {
		Page<Medico> medicos = medicoService.consultarTodos(paginacao);
		
		return ResponseEntity.ok(medicos);
	}
	
	@GetMapping("/especialidade={especialidade}")
	public ResponseEntity<Page<Medico>> consultarPorEspecialidade(@PathVariable String especialidade,
			@PageableDefault(sort = "id") Pageable paginacao) {
		Page<Medico> medicos = medicoService.consultarPorEspecialidade(paginacao, especialidade);
		
		return ResponseEntity.ok(medicos);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Medico> consultarPorId(@PathVariable Long id) {
		Optional<Medico> medico = medicoService.consultarPorId(id);
		
		if(medico.isPresent()) {
			return ResponseEntity.ok(medico.get());
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/{id}/consultasHoje")
	public ResponseEntity<Page<Consulta>> consultarConsultasDeHoje(@PathVariable Long id, @PageableDefault(sort = "hora")
			Pageable paginacao) {
		Optional<Medico> medico = medicoService.consultarPorId(id);
		
		if(medico.isPresent()) {
			Page<Consulta> consultasHoje = consultaService.consultarConsultasHojeMedico(medico, LocalDate.now(), paginacao);
			
			return ResponseEntity.ok(consultasHoje);
		}
		
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}/examesHoje")
	public ResponseEntity<Page<Exame>> consultarExamesDeHoje(@PathVariable Long id, @PageableDefault(sort = "id")
			Pageable paginacao) {
		Optional<Medico> medico = medicoService.consultarPorId(id);
		
		if(medico.isPresent()) {
			Page<Exame> examesHoje = exameService.consultarExamesHojeMedico(medico, LocalDate.now(), paginacao);
			
			return ResponseEntity.ok(examesHoje);
		}
		
		return ResponseEntity.noContent().build();
	}
		
	@GetMapping("/{id}/consultasDia={data}")
	public ResponseEntity<Page<Consulta>> consultarConsultasPeloDia(@PathVariable Long id, @PathVariable String data,
			@PageableDefault(sort = "hora") Pageable paginacao) {
		Optional<Medico> medico = medicoService.consultarPorId(id);
		
		if(medico.isPresent()) {
			Page<Consulta> consultasPeloDia = consultaService.consultarConsultaPeloDia(medico, LocalDate.parse(data, DateTimeFormatter.ISO_DATE), paginacao);
			
			return ResponseEntity.ok(consultasPeloDia);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/{id}/examesDia={data}")
	public ResponseEntity<Page<Exame>> consultarExamesPeloDia(@PathVariable Long id, @PathVariable String data, 
			@PageableDefault(sort = "hora") Pageable paginacao) {
		Optional<Medico> medico = medicoService.consultarPorId(id);
		
		if(medico.isPresent()) {
			Page<Exame> examesPeloDia = exameService.consultarExamesPeloDia(medico, LocalDate.parse(data, DateTimeFormatter.ISO_DATE), paginacao);
			
			return ResponseEntity.ok(examesPeloDia);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@Transactional
	@PutMapping("/{id}")
	public ResponseEntity<Medico> atualizar(@PathVariable Long id, @RequestBody Medico medicoAtualizado) {
		Optional<Medico> optional = medicoService.consultarPorId(id);
		
		if(optional.isPresent()) {
			Medico medico = medicoService.atualizar(id, medicoAtualizado);
			
			return ResponseEntity.ok(medico);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@Transactional
	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluir(@PathVariable Long id) {
		Optional<Medico> optional = medicoService.consultarPorId(id);
		
		if(optional.isPresent()) {
			medicoService.excluir(id);
			
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
}
