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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.clinicamaximo.clinicamaximo.dto.in.InAtualizarMedicoDTO;
import com.clinicamaximo.clinicamaximo.dto.in.InMedicoDTO;
import com.clinicamaximo.clinicamaximo.dto.out.OutMedicoDTO;
import com.clinicamaximo.clinicamaximo.model.Medico;
import com.clinicamaximo.clinicamaximo.repository.MedicoRepository;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

	@Autowired
	private MedicoRepository medicoRepository;
	
	@GetMapping
	public Page<OutMedicoDTO> buscarTodos(@PageableDefault(sort = "id", direction = Direction.ASC) Pageable paginacao) {
		Page<Medico> medicos = medicoRepository.findAll(paginacao);
		
		return OutMedicoDTO.converter(medicos);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<OutMedicoDTO> buscarPorId(@PathVariable Long id) {
		Optional<Medico> medico = medicoRepository.findById(id);
		
		if(medico.isPresent()) {
			return ResponseEntity.ok(OutMedicoDTO.converterApenasUm(medico.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/especialidade={especialidade}")
	public ResponseEntity<Page<OutMedicoDTO>> buscarPorEspecialidade(@PathVariable String especialidade, 
			@PageableDefault(sort = "id", direction = Direction.ASC) Pageable paginacao) {
		Optional<Page<Medico>> medicos = medicoRepository.findByEspecialidade(especialidade, paginacao);
		
		if(medicos.isPresent()) {
			return ResponseEntity.ok(OutMedicoDTO.converter(medicos.get()));
		}
		
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<OutMedicoDTO> cadastrar(@RequestBody @Valid InMedicoDTO dto, UriComponentsBuilder uriBuilder) {
		Medico medico = dto.converter();
		medicoRepository.save(medico);
		
		URI uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
		return ResponseEntity.created(uri).body(new OutMedicoDTO(medico)); 
	}
	
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<OutMedicoDTO> atualizar(@PathVariable Long id, @RequestBody @Valid InAtualizarMedicoDTO dto) {
		Optional<Medico> optional = medicoRepository.findById(id);
		
		if(optional.isPresent()) {
			Medico medico = dto.atualizar(id, medicoRepository);
			return ResponseEntity.ok(new OutMedicoDTO(medico));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<OutMedicoDTO> excluir(@PathVariable Long id) {
		Optional<Medico> optional = medicoRepository.findById(id);
		
		if(optional.isPresent()) {
			medicoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
}
