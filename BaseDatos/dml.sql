INSERT INTO Membresia (id, tipo) VALUES 
(1, 'Básica'),
(2, 'Premium'),
(3, 'Familiar');

INSERT INTO Calculadora (id, IMC) VALUES 
(1, 22.50),
(2, 27.80),
(3, 19.40);

INSERT INTO Registro_Acceso (codigo) VALUES 
(101),
(102);

INSERT INTO Vestuario (id, genero) VALUES 
(1, 'Masculino'),
(2, 'Femenino');

INSERT INTO Spa (nombre) VALUES 
('Sauna Húmeda'),
('Jacuzzi Central'),
('Zona Relax');

INSERT INTO Clases (codigo, tipo) VALUES 
(501, 'Yoga'),
(502, 'Crossfit'),
(503, 'Pilates');

INSERT INTO Zona_Entrenos (id, tipo) VALUES 
(1, 'Cardio'),
(2, 'Musculación'),
(3, 'Funcional');

INSERT INTO Empleados (dni, telefono, nomina, nombre, apellido) VALUES 
('11111111A', '600111222', 1500.00, 'Carlos', 'Sánchez'),
('22222222B', '600333444', 1200.00, 'Laura', 'Gómez'),
('33333333C', '600555666', 1100.00, 'Pedro', 'Ruiz'),
('44444444D', '600777888', 1800.00, 'Ana', 'Martínez'),
('55555555E', '600999000', 1600.00, 'Sergio', 'Fernández');

INSERT INTO Entrenador (tipo_empleados, tipo) VALUES 
('11111111A', 'Musculación'),
('44444444D', 'Cardio'),
('55555555E', 'Artes Marciales');

INSERT INTO Recepcionista (tipo_empleados, turno) VALUES 
('22222222B', 'Mañana');

INSERT INTO Limpiador (tipo_empleados, turno) VALUES 
('33333333C', 'Tarde');

-- CLIENTES (corregido: id_membresia=3 ahora existe, añadida fecha_compra)
INSERT INTO Cliente (dni, nombre, apellido, id_membresia, id_calculadora, fecha_compra) VALUES 
('99999999Z', 'Juan',  'Pérez',  2, 1, '2023-01-15'),
('88888888Y', 'Maria', 'López',  1, 2, '2023-03-10'),
('77777777X', 'Luis',  'Torres', 3, 3, '2023-06-20'),
('66666666W', 'Sara',  'Díaz',   1, 1, '2023-09-05'),
('55555555V', 'Marta', 'Ramos',  2, 2, '2023-10-01');

INSERT INTO Registrar_Entrada (codigo_registro_acceso, dni_cliente, fecha, hora) VALUES 
(101, '99999999Z', '2023-10-25', '09:00:00'),
(101, '88888888Y', '2023-10-25', '10:30:00'),
(102, '77777777X', '2023-10-26', '08:45:00'),
(102, '66666666W', '2023-10-26', '11:00:00');

INSERT INTO Registrar_Salida (codigo_registro_acceso, dni_cliente, fecha, hora) VALUES 
(101, '99999999Z', '2023-10-25', '11:00:00'),
(101, '88888888Y', '2023-10-25', '12:00:00'),
(102, '77777777X', '2023-10-26', '10:30:00');

INSERT INTO Usar (id_vestuario, dni_cliente) VALUES 
(1, '99999999Z'),
(2, '88888888Y'),
(1, '77777777X'),
(2, '55555555V');

INSERT INTO Acceder (nombre_spa, dni_cliente) VALUES 
('Jacuzzi Central', '77777777X'),
('Sauna Húmeda',    '99999999Z'),
('Zona Relax',      '55555555V');

INSERT INTO Asistir (codigo_clases, dni_cliente) VALUES 
(501, '88888888Y'),
(502, '99999999Z'),
(503, '77777777X'),
(501, '66666666W');

INSERT INTO Atender (dni_empleados, dni_cliente, fecha, descripcion) VALUES 
('22222222B', '99999999Z', '2023-10-25', 'Consulta sobre horarios y membresía'),
('22222222B', '88888888Y', '2023-10-25', 'Alta de nuevo cliente'),
('11111111A', '77777777X', '2023-10-26', 'Revisión de rutina de entrenamiento'),
('44444444D', '55555555V', '2023-10-26', 'Evaluación física inicial');

INSERT INTO Enseñar (entrenador1, entrenador2) VALUES 
('44444444D', '11111111A'),
('55555555E', '44444444D');

INSERT INTO Limpiar (turno_limpiador, id_zona_de_entrenos, fecha) VALUES 
('33333333C', 1, '2023-10-25'),
('33333333C', 2, '2023-10-25'),
('33333333C', 3, '2023-10-26');

INSERT INTO Realizar (dni_entrenador, codigo_clases, id_zona_entrenos) VALUES 
('11111111A', 502, 2),
('44444444D', 501, 3),
('55555555E', 503, 1);

INSERT INTO Entrenar (dni_cliente, id_zona_entrenos) VALUES 
('99999999Z', 2),
('88888888Y', 1),
('77777777X', 3),
('66666666W', 1),
('55555555V', 2);