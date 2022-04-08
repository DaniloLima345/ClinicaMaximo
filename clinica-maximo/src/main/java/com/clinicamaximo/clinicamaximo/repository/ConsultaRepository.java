package com.clinicamaximo.clinicamaximo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clinicamaximo.clinicamaximo.model.Consulta;
import com.clinicamaximo.clinicamaximo.model.StatusConsulta;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

	Page<Consulta> findByStatus(StatusConsulta agendada, Pageable paginacao);

}
