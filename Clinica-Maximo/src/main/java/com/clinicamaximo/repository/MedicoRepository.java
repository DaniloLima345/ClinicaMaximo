package com.clinicamaximo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clinicamaximo.model.Medico;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long>{

	Page<Medico> findByEspecialidade(String especialidade, Pageable paginacao);

}
