package com.example.demo;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.file.GenericFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class PedidoFileRoute extends RouteBuilder {

    private static final String EXPECTED_HEADER = "laboratorio_id,paciente_id,tipo_examen,resultado,fecha_examen";

    @Autowired
    private ProcessedFolderCsvProcessor processedFolderCsvProcessor;

    @Override
    public void configure() {

        // Route 1: Input folder → Validate → /processed or /error
        from("file:input-labs?noop=true&include=.*\\.csv&delay=5000")
            .routeId("csv-validation-route")
            .process(this::validateCsvFile)
            .choice()
                .when(exchange -> exchange.getProperty("validCsv", Boolean.class))
                    .to("file:processed")
                .otherwise()
                    .to("file:error")
            .end();

        // Route 2: Process files in /processed → insert into DB
        from("file:processed?noop=true&include=.*\\.csv&delay=5000")
            .routeId("csv-database-insert-route")
            .process(this::validateCsvFile)
            .choice()
                .when(exchange -> exchange.getProperty("validCsv", Boolean.class))
                    .process(processedFolderCsvProcessor)
                .otherwise()
                    .to("file:error") // Revalidate header in case of manual placement
            .end();
    }

    private void validateCsvFile(Exchange exchange) {
        GenericFile<?> genericFile = (GenericFile<?>) exchange.getIn().getBody();
        File file = new File(genericFile.getAbsoluteFilePath());

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String firstLine = reader.readLine();
            boolean valid = firstLine != null && firstLine.trim().equals(EXPECTED_HEADER);
            exchange.setProperty("validCsv", valid);
        } catch (IOException e) {
            exchange.setProperty("validCsv", false);
        }
    }
}
