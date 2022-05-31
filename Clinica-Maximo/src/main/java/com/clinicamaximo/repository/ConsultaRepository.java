package com.clinicamaximo.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clinicamaximo.model.Consulta;
import com.clinicamaximo.model.Medico;
import com.clinicamaximo.model.Paciente;
import com.clinicamaximo.model.StatusAgendamento;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

	Optional<Page<Consulta>> findByStatus(StatusAgendamento status, Pageable paginacao);

	Page<Consulta> findByPaciente(Paciente paciente, Pageable paginacao);

	Page<Consulta> findByMedicoAndData(Optional<Medico> medico, LocalDate now, Pageable paginacao);

}
