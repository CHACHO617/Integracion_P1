CREATE DATABASE laboratorio
GO

-- Crear Usuario para base --
CREATE USER emeri FOR LOGIN emeri;
EXEC sp_addrolemember 'db_datareader', emeri;  -- Read permissions
EXEC sp_addrolemember 'db_datawriter', emeri;  -- Write permissions


USE laboratorio
GO

CREATE TABLE resultados_examenes (
    id INT IDENTITY (1,1) PRIMARY KEY,
    laboratorio_id INT NOT NULL,
    paciente_id INT NOT NULL,
    tipo_examen VARCHAR(255) NOT NULL,
    resultado TEXT NOT NULL,
    fecha_examen DATETIME NOT NULL,
    UNIQUE (laboratorio_id, paciente_id, tipo_examen, fecha_examen) -- Evitar duplicados
);


CREATE TABLE log_cambios_resultados (
    id INT IDENTITY (1,1) PRIMARY KEY,
    operacion VARCHAR(50) NOT NULL,
    paciente_id INT NOT NULL,
    tipo_examen VARCHAR(255) NOT NULL,
    fecha DATETIME NOT NULL
);

DROP TRIGGER after_resultados_insert

CREATE TRIGGER after_resultados_insert
ON resultados_examenes
AFTER INSERT, UPDATE
AS
BEGIN
    -- Para INSERT
    INSERT INTO log_cambios_resultados (operacion, paciente_id, tipo_examen, fecha)
    SELECT 'INSERT', paciente_id, tipo_examen, GETDATE()
    FROM inserted;

    -- Para UPDATE
    IF EXISTS (SELECT * FROM inserted)
       AND EXISTS (SELECT * FROM deleted)
    BEGIN
        INSERT INTO log_cambios_resultados (operacion, paciente_id, tipo_examen, fecha)
        SELECT 'UPDATE', paciente_id, tipo_examen, GETDATE()
        FROM inserted;
    END
END;




-- Test
INSERT INTO resultados_examenes (laboratorio_id, paciente_id, tipo_examen, resultado, fecha_examen)
VALUES (1, 101, 'Hemograma', 'Normal', '2025-04-23');

SELECT * FROM resultados_examenes


UPDATE resultados_examenes
SET resultado = 'Anormal'
WHERE paciente_id = 101 AND tipo_examen = 'Hemograma';

SELECT * FROM resultados_examenes


SELECT * FROM log_cambios_resultados;
