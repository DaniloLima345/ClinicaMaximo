package com.clinicamaximo.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clinicamaximo.model.Exame;
import com.clinicamaximo.model.Medico;
import com.clinicamaximo.model.Paciente;
import com.clinicamaximo.model.StatusAgendamento;

@Repository
public interface ExameRepository extends JpaRepository<Exame, Long> {

	Optional<Page<Exame>> findByNome(String nome, Pageable paginacao);

	Optional<Page<Exame>> findByStatus(StatusAgendamento status, Pageable paginacao);

	Page<Exame> findByPaciente(Paciente paciente, Pageable paginacao);

	Page<Exame> findByMedicoAndData(Optional<Medico> medico, LocalDate now, Pageable paginacao);

}
