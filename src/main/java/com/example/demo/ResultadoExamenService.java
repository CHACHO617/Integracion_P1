package com.example.demo;

import com.example.demo.model.ResultadoExamen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResultadoExamenService {

    @Autowired
    private ResultadoExamenRepository repository;

    public void guardarSiNoExiste(ResultadoExamen examen) {
        boolean exists = repository.existsByLaboratorioIdAndPacienteIdAndTipoExamenAndFechaExamen(
            examen.getLaboratorioId(),
            examen.getPacienteId(),
            examen.getTipoExamen(),
            examen.getFechaExamen()
        );

        if (!exists) {
            repository.save(examen);
        }
    }
}
