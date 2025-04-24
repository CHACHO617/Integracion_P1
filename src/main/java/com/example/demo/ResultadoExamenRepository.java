package com.example.demo;

import com.example.demo.model.ResultadoExamen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ResultadoExamenRepository extends JpaRepository<ResultadoExamen, Long> {

    // Custom query method to find a ResultadoExamen by laboratorioId, pacienteId, tipoExamen, and fechaExamen
    Optional<ResultadoExamen> findByLaboratorioIdAndPacienteIdAndTipoExamenAndFechaExamen(
            Long laboratorioId, Long pacienteId, String tipoExamen, LocalDate fechaExamen
    );
}
