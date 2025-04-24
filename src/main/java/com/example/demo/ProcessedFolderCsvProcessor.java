package com.example.demo;

import com.example.demo.model.ResultadoExamen;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.file.GenericFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.LocalDate;

@Component
public class ProcessedFolderCsvProcessor implements Processor {

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
                if (lineNumber == 1) continue; // skip header

                String[] fields = line.split(",");
                if (fields.length != 5) continue;

                ResultadoExamen examen = new ResultadoExamen();
                examen.setLaboratorioId(Long.parseLong(fields[0].trim()));
                examen.setPacienteId(Long.parseLong(fields[1].trim()));
                examen.setTipoExamen(fields[2].trim());
                examen.setResultado(fields[3].trim());
                examen.setFechaExamen(LocalDate.parse(fields[4].trim()));

                resultadoService.guardarSiNoExiste(examen);
            }
        }
    }
}
