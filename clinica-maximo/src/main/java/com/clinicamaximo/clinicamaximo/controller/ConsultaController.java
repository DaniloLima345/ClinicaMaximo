package com.clinicamaximo.clinicamaximo.controller;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
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

import com.clinicamaximo.clinicamaximo.controller.form.DiagnosticoConsultaForm;
import com.clinicamaximo.clinicamaximo.controller.form.ReagendarHorarioConsultaForm;
import com.clinicamaximo.clinicamaximo.model.Consulta;
import com.clinicamaximo.clinicamaximo.model.StatusConsulta;
import com.clinicamaximo.clinicamaximo.repository.ConsultaRepository;
import com.clinicamaximo.clinicamaximo.repository.MedicoRepository;
import com.clinicamaximo.clinicamaximo.repository.PacienteRepository;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {
	
	@Autowired
	private ConsultaRepository consultaRepository;
	
	@Autowired
	private PacienteRepository pacienteRepository;
	
	@Autowired
	private MedicoRepository medicoRepository;

	@GetMapping
	public Page<Consulta> consultarTodas(@PageableDefault(sort = "id", direction = Direction.ASC) Pageable paginacao) {
		Page<Consulta> consultas = consultaRepository.findAll(paginacao);
		
		return consultas;
	}
	
	@GetMapping("/agendadas")
	public ResponseEntity<Page<Consulta>> consultarConsultasAgendadas(@PageableDefault(sort = "id", direction = Direction.ASC) Pageable paginacao) {
		Page<Consulta> consultas = consultaRepository.findByStatus(StatusConsulta.AGENDADA, paginacao);
		
		return ResponseEntity.ok(consultas);
	}
	
	@Transactional
	@PostMapping
	public ResponseEntity<Consulta> cadastrar(@RequestBody Consulta form, UriComponentsBuilder uriBuilder) {
		Consulta consulta = form.convereter(pacienteRepository, medicoRepository);
		consultaRepository.save(consulta);
		
		URI uri = uriBuilder.path("/consultas/{id}").buildAndExpand(consulta.getId()).toUri();
		return ResponseEntity.created(uri).body(consulta);
	}
	
	@Transactional
	@PatchMapping("/{id}/realizarDiagnostico")
	public ResponseEntity<Consulta> realizarDiagnosticoDaConsulta(@PathVariable Long id, @RequestBody @Valid DiagnosticoConsultaForm form) {
		Optional<Consulta> optional = consultaRepository.findById(id);
		
		if(optional.isPresent()) {
			Consulta consulta = form.atualizar(id, consultaRepository);
			
			return ResponseEntity.ok(consulta);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@Transactional
	@PatchMapping("/{id}/cancelarConsulta")
	public ResponseEntity<Consulta> cancelarConsulta(@PathVariable Long id) {
		Optional<Consulta> optional = consultaRepository.findById(id);
		
		if(optional.isPresent()) {
			Consulta consulta = consultaRepository.getById(id);
			
			consulta.setStatus(StatusConsulta.CANCELADA);
			return ResponseEntity.ok(consulta);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@Transactional
	@PatchMapping("/{id}/reagendarHorario")
	public ResponseEntity<Consulta> reagendarHorario(@PathVariable Long id, @RequestBody @Valid ReagendarHorarioConsultaForm form) {
		Optional<Consulta> optional = consultaRepository.findById(id);
		
		if(optional.isPresent()) {
			Consulta consulta = form.atualizar(id, consultaRepository);
			
			return ResponseEntity.ok(consulta);
		}
		
		return ResponseEntity.notFound().build();
	}
}
