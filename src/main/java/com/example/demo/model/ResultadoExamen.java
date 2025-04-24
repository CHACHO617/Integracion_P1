package com.example.demo.model;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Column;
import jakarta.persistence.GenerationType;




@Entity
@Table(name = "resultados_examenes", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"laboratorio_id", "paciente_id", "tipo_examen", "fecha_examen"})
})
public class ResultadoExamen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "laboratorio_id", nullable = false)
    private Long laboratorioId;

    @Column(name = "paciente_id", nullable = false)
    private Long pacienteId;

    @Column(name = "tipo_examen", nullable = false)
    private String tipoExamen;

    @Column(name = "resultado", nullable = false)
    private String resultado;

    @Column(name = "fecha_examen", nullable = false)
    private LocalDate fechaExamen;

    // Getters and Setters

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getLaboratorioId() { return laboratorioId; }

    public void setLaboratorioId(Long laboratorioId) { this.laboratorioId = laboratorioId; }

    public Long getPacienteId() { return pacienteId; }

    public void setPacienteId(Long pacienteId) { this.pacienteId = pacienteId; }

    public String getTipoExamen() { return tipoExamen; }

    public void setTipoExamen(String tipoExamen) { this.tipoExamen = tipoExamen; }

    public String getResultado() { return resultado; }

    public void setResultado(String resultado) { this.resultado = resultado; }

    public LocalDate getFechaExamen() { return fechaExamen; }

    public void setFechaExamen(LocalDate fechaExamen) { this.fechaExamen = fechaExamen; }
}
