package com.clinicamaximo.controller;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;

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

import com.clinicamaximo.controller.dto.ConusltaDTO;
import com.clinicamaximo.controller.dto.ReagendamentoDTO;
import com.clinicamaximo.controller.dto.RealizarAtendimentoDTO;
import com.clinicamaximo.controller.service.ConsultaService;
import com.clinicamaximo.controller.service.MedicoService;
import com.clinicamaximo.controller.service.PacienteService;
import com.clinicamaximo.model.Consulta;

@RestController
@RequestMapping("/consulta")
public class ConsultaController {

	@Autowired
	private ConsultaService consultaService;
	
	@Autowired
	private PacienteService pacienteService;
	
	@Autowired
	private MedicoService medicoService;
	
	@Transactional
	@PostMapping
	public ResponseEntity<Consulta> cadastrar(@RequestBody ConusltaDTO dto, UriComponentsBuilder uriBuilder) {
		Consulta consulta = dto.converter(pacienteService, medicoService);
		consultaService.cadastrar(consulta);
		
		URI uri = uriBuilder.path("consulta/{id}").buildAndExpand(consulta.getId()).toUri();
		return ResponseEntity.created(uri).body(consulta);
	}
	
	@GetMapping
	public ResponseEntity<Page<Consulta>> consultarTodas(@PageableDefault(sort = "id") Pageable paginacao) {
		Page<Consulta> consultas = consultaService.consultarTodas(paginacao);
		
		return ResponseEntity.ok(consultas);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Consulta> consultarPorId(@PathVariable Long id) {
		Optional<Consulta> consulta = consultaService.consultarPorId(id);
		
		if(consulta.isPresent()) {
			return ResponseEntity.ok(consulta.get());
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/status={status}")
	public ResponseEntity<Page<Consulta>> consultaPorStatus(@PathVariable String status, @PageableDefault(sort ="id") Pageable
			paginacao) { 
		Optional<Page<Consulta>> consultas = consultaService.consultarPorStatus(status, paginacao);
		
		if(consultas.isPresent()) {
			return ResponseEntity.ok(consultas.get());
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@Transactional
	@PatchMapping("/{id}")
	public ResponseEntity<Consulta> reagendarDataEHora(@PathVariable Long id, @RequestBody ReagendamentoDTO dto) {
		Optional<Consulta> optional = consultaService.consultarPorId(id);
		
		if(optional.isPresent()) {
			Consulta consulta = consultaService.reagendarDataEHora(id, dto);
			
			return ResponseEntity.ok(consulta);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@Transactional
	@PatchMapping("/cancelarConsulta/{id}")
	public ResponseEntity<Consulta> cancelarConsulta(@PathVariable Long id) {
		Optional<Consulta> optional = consultaService.consultarPorId(id);
		
		if(optional.isPresent()) {
			Consulta consulta = consultaService.cancelarConsulta(id);
			
			return ResponseEntity.ok(consulta);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@Transactional
	@PatchMapping("/realizarConsulta/{id}")
	public ResponseEntity<Consulta> realizarConsulta(@PathVariable Long id, @RequestBody RealizarAtendimentoDTO dto) {
		Optional<Consulta> optional = consultaService.consultarPorId(id);
		
		if(optional.isPresent()) {
			Consulta consulta = consultaService.realizarConsulta(id, dto);
			
			return ResponseEntity.ok(consulta);
		}
		
		return ResponseEntity.notFound().build();
	}
}
