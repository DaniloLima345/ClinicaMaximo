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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.clinicamaximo.controller.dto.ExameDTO;
import com.clinicamaximo.controller.dto.ReagendamentoDTO;
import com.clinicamaximo.controller.dto.RealizarAtendimentoDTO;
import com.clinicamaximo.controller.service.ExameService;
import com.clinicamaximo.controller.service.MedicoService;
import com.clinicamaximo.controller.service.PacienteService;
import com.clinicamaximo.model.Exame;

@RestController
@RequestMapping("/exame")
public class ExameController {

	@Autowired
	private ExameService exameService;
	
	@Autowired
	private PacienteService pacienteService;
	
	@Autowired
	private MedicoService medicoService;
	
	@Transactional
	@PostMapping
	public ResponseEntity<Exame> cadastrar(@RequestBody @Valid ExameDTO dto, UriComponentsBuilder uriBuilder) {
		Exame exame = dto.converter(pacienteService, medicoService);
		exameService.cadastrar(exame);
		
		URI uri = uriBuilder.path("exame/{id}").buildAndExpand(exame.getId()).toUri();
		return ResponseEntity.created(uri).body(exame);
	}
	
	@GetMapping
	public ResponseEntity<Page<Exame>> consultarTodos(@PageableDefault(sort = "id") Pageable paginacao) {
		Page<Exame> exames = exameService.consultarTodos(paginacao);
		
		return ResponseEntity.ok(exames);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Exame> consultarPorId(@PathVariable Long id) {
		Optional<Exame> exame = exameService.consultarPorId(id);
		
		if(exame.isPresent()) {
			return ResponseEntity.ok(exame.get());
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/consultarPorNome/nome={nome}")
	public ResponseEntity<Page<Exame>> consultarPorNome(@PathVariable String nome, @PageableDefault(sort = "data")
			Pageable paginacao) {
		Optional<Page<Exame>> exames = exameService.consultarPorNome(nome, paginacao);
		
		if(exames.isPresent()) {
			return ResponseEntity.ok(exames.get());
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/status={status}")
	public ResponseEntity<Page<Exame>> consultarPorStatus(@PathVariable String status, @PageableDefault(sort = "data")
			Pageable paginacao){
		Optional<Page<Exame>> exames = exameService.consultarPorStatus(status, paginacao);
		
		if(exames.isPresent()) {
			return ResponseEntity.ok(exames.get());
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@Transactional
	@PatchMapping("/{id}")
	public ResponseEntity<Exame> reagendarDataEHora(@PathVariable Long id, @RequestBody ReagendamentoDTO dto) {
		Optional<Exame> optional = exameService.consultarPorId(id);
		
		if(optional.isPresent()) {
			Exame exame = exameService.reagendarDataEHora(id, dto);
			
			return ResponseEntity.ok(exame);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@Transactional
	@PatchMapping("/cancelarExame/{id}")
	public ResponseEntity<Exame> cancelarExame(@PathVariable Long id) {
		Optional<Exame> optional = exameService.consultarPorId(id);
		
		if(optional.isPresent()) {
			Exame exame = exameService.cancelarExame(id);
			
			return ResponseEntity.ok(exame);
		}
				
		return ResponseEntity.notFound().build();
	}
	
	@Transactional
	@PatchMapping("/realizarExame/{id}")
	public ResponseEntity<Exame> realizarExame(@PathVariable Long id, @RequestBody RealizarAtendimentoDTO dto) {
		Optional<Exame> optional = exameService.consultarPorId(id);
		
		if(optional.isPresent()) {
			Exame exame = exameService.realizarExame(id, dto);
			
			return ResponseEntity.ok(exame);
		}
		
		return ResponseEntity.notFound().build();
	}
}
