package com.clinicamaximo.clinicamaximo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clinicamaximo.clinicamaximo.model.Paciente;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

	
}
