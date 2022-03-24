package com.clinicamaximo.clinicamaximo.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clinicamaximo.clinicamaximo.model.Medico;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long>{

	Optional<Page<Medico>> findByEspecialidade(String especialidade, Pageable paginacao);

}
