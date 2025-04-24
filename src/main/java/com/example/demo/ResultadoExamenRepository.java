package com.example.demo;

import com.example.demo.model.ResultadoExamen;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultadoExamenRepository extends JpaRepository<ResultadoExamen, Long> {
    boolean existsByLaboratorioIdAndPacienteIdAndTipoExamenAndFechaExamen(
        Long laboratorioId, Long pacienteId, String tipoExamen, LocalDate fechaExamen
    );
}
