package com.clinicamaximo.clinicamaximo.controller;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable ;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Page;
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

import com.clinicamaximo.clinicamaximo.dto.in.InAtualizarPacienteDTO;
import com.clinicamaximo.clinicamaximo.dto.in.InPacienteDTO;
import com.clinicamaximo.clinicamaximo.dto.out.OutPacienteDTO;
import com.clinicamaximo.clinicamaximo.model.Paciente;
import com.clinicamaximo.clinicamaximo.repository.PacienteRepository;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

	@Autowired
	private PacienteRepository pacienteRepository;
	
	@PostMapping
	public ResponseEntity<OutPacienteDTO> cadastrar(@RequestBody @Valid InPacienteDTO dto, UriComponentsBuilder uriBuilder){
		Paciente pacientes = dto.converter();
		pacienteRepository.save(pacientes);
		
		URI uri = uriBuilder.path("pacientes/{id}").buildAndExpand(pacientes.getId()).toUri();
		
		return ResponseEntity.created(uri).body(new OutPacienteDTO(pacientes));
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<OutPacienteDTO> atualizar(@PathVariable Long id, @RequestBody @Valid InAtualizarPacienteDTO dto){
		
		Optional<Paciente> optional = pacienteRepository.findById(id);
		
		if(optional.isPresent()) {
			Paciente pacientes = dto.atualizar(id, pacienteRepository);
			return ResponseEntity.ok(new OutPacienteDTO(pacientes));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<OutPacienteDTO> excluir(@PathVariable Long id){
		Optional<Paciente> optional = pacienteRepository.findById(id);
		
		if(optional.isPresent()) {
			pacienteRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping
	public ResponseEntity<Page<OutPacienteDTO>> buscarTodos(@PageableDefault(sort = "id", direction = Direction.ASC) Pageable paginacao){
		Page<Paciente> pacientes = pacienteRepository.findAll(paginacao);
		
		return ResponseEntity.ok(OutPacienteDTO.converter(pacientes));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<OutPacienteDTO> buscarPorId(@PathVariable Long id){
		Optional<Paciente> paciente = pacienteRepository.findById(id);
		
		if(paciente.isPresent()) {
			return ResponseEntity.ok(OutPacienteDTO.converterApenasUm(paciente.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
}
