package com.example.demo;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.file.GenericFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.model.ResultadoExamen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;

@Component
public class CsvToDatabaseProcessor implements Processor {

    @Autowired
    private ResultadoExamenService resultadoService;

    @Override
    public void process(Exchange exchange) throws Exception {
        GenericFile<?> file = (GenericFile<?>) exchange.getIn().getBody();
        File csvFile = new File(file.getAbsoluteFilePath());

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;

                // Skip header
                if (lineNumber == 1) continue;

                // Parse the line
                String[] fields = line.split(",");
                if (fields.length != 5) {
                    System.err.println("Línea inválida en archivo CSV: " + line);
                    continue;
                }

                try {
                    ResultadoExamen examen = new ResultadoExamen();
                    examen.setLaboratorioId(Long.parseLong(fields[0].trim()));
                    examen.setPacienteId(Long.parseLong(fields[1].trim()));
                    examen.setTipoExamen(fields[2].trim());
                    examen.setResultado(fields[3].trim());
                    examen.setFechaExamen(LocalDate.parse(fields[4].trim()));

                    resultadoService.guardarSiNoExiste(examen);
                } catch (Exception e) {
                    System.err.println("Error al procesar línea: " + line);
                    e.printStackTrace();
                }
            }
        }
    }
}

