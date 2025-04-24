package com.example.demo;

import com.example.demo.model.ResultadoExamen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ResultadoExamenService {

    @Autowired
    private ResultadoExamenRepository resultadoExamenRepository;

    // Method to save if not already exists
    public void guardarSiNoExiste(ResultadoExamen resultadoExamen) {
        // Check if the record already exists by laboratorioId, pacienteId, tipoExamen, and fechaExamen
        Optional<ResultadoExamen> existingExamen = resultadoExamenRepository
                .findByLaboratorioIdAndPacienteIdAndTipoExamenAndFechaExamen(
                        resultadoExamen.getLaboratorioId(),
                        resultadoExamen.getPacienteId(),
                        resultadoExamen.getTipoExamen(),
                        resultadoExamen.getFechaExamen()
                );

        // If it doesn't exist, save the new ResultadoExamen
        if (existingExamen.isEmpty()) {
            resultadoExamenRepository.save(resultadoExamen);
        } else {
            System.out.println("Examen duplicado no guardado: " + resultadoExamen);
        }
    }
}
