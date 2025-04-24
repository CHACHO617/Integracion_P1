package com.example.demo;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.component.file.GenericFile;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class PedidoFileRoute extends RouteBuilder {

    private static final String EXPECTED_HEADER = "laboratorio_id,paciente_id,tipo_examen,resultado,fecha_examen";

    @Override
    public void configure() {
        from("file:input-labs?noop=true&include=.*\\.csv&delay=5000")
            .process(this::validateCsvFile)
            .choice()
                .when(exchange -> exchange.getProperty("validCsv", Boolean.class))
                    .to("file:processed")
                    .process(new CsvToDatabaseProcessor()) // Insertar en DB
                .otherwise()
                    .to("file:error")
            .end();
    }

    private void validateCsvFile(Exchange exchange) {
        GenericFile<?> genericFile = (GenericFile<?>) exchange.getIn().getBody();
        File file = new File(genericFile.getAbsoluteFilePath());

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String firstLine = reader.readLine();
            boolean valid = firstLine != null && firstLine.equals(EXPECTED_HEADER);
            exchange.setProperty("validCsv", valid);
        } catch (IOException e) {
            exchange.setProperty("validCsv", false);
        }
    }
}
