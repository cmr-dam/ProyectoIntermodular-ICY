INSERT INTO Membresia (id, tipo) VALUES 
(1, 'Básica'),
(2, 'Premium'),
(3, 'Familiar'),
(4, 'Estudiante'),
(5, 'VIP');

INSERT INTO Calculadora (id, IMC) VALUES 
(1, 22.50),
(2, 27.80),
(3, 19.40),
(4, 24.10),
(5, 30.25);

INSERT INTO Registro_Acceso (codigo) VALUES 
(101),
(102),
(103),
(104),
(105);

INSERT INTO Clases (codigo, tipo) VALUES 
(501, 'Yoga'),
(502, 'Crossfit'),
(503, 'Pilates'),
(504, 'Zumba'),
(505, 'Boxeo');

INSERT INTO Zona_Entrenos (id, tipo) VALUES 
(1, 'Cardio'),
(2, 'Musculación'),
(3, 'Funcional'),
(4, 'Peso Libre'),
(5, 'Estiramientos');

INSERT INTO Empleados (dni, telefono, nomina, nombre, apellido) VALUES 
('11111111A', '600111222', 1500.00, 'Carlos', 'Sánchez'),
('22222222B', '600333444', 1200.00, 'Laura', 'Gómez'),
('33333333C', '600555666', 1100.00, 'Pedro', 'Ruiz'),
('44444444D', '600777888', 1800.00, 'Ana', 'Martínez'),
('55555555E', '600999000', 1600.00, 'Sergio', 'Fernández');

INSERT INTO Entrenador (tipo_empleados, tipo) VALUES 
('11111111A', 'Musculación'),
('44444444D', 'Cardio'),
('55555555E', 'Artes Marciales'),
('22222222B', 'Yoga'),
('33333333C', 'Funcional');

INSERT INTO Recepcionista (tipo_empleados, turno) VALUES 
('22222222B', 'Mañana'),
('11111111A', 'Tarde'),
('33333333C', 'Noche');

INSERT INTO Limpiador (tipo_empleados, turno) VALUES 
('33333333C', 'Tarde'),
('11111111A', 'Mañana');

INSERT INTO Cliente (dni, nombre, apellido, usuario, contraseña, id_membresia, id_calculadora, ultimo_acceso, fecha_compra) VALUES 
('99999999Z', 'Juan',  'Pérez',  'juanp', '1234', 2, 1, '2023-10-25 09:00:00', '2023-01-15'),
('88888888Y', 'Maria', 'López',  'marial', '1234', 1, 2, '2023-10-25 10:30:00', '2023-03-10'),
('77777777X', 'Luis',  'Torres', 'luist', '1234', 3, 3, '2023-10-26 08:45:00', '2023-06-20'),
('66666666W', 'Sara',  'Díaz',   'sarad', '1234', 1, 1, '2023-10-26 11:00:00', '2023-09-05'),
('55555555V', 'Marta', 'Ramos',  'martar', '1234', 2, 2, '2023-10-27 09:30:00', '2023-10-01');

INSERT INTO Registrar_Entrada (codigo_registro_acceso, dni_cliente, fecha, hora) VALUES 
(101, '99999999Z', '2023-10-25', '09:00:00'),
(101, '88888888Y', '2023-10-25', '10:30:00'),
(102, '77777777X', '2023-10-26', '08:45:00'),
(102, '66666666W', '2023-10-26', '11:00:00'),
(103, '55555555V', '2023-10-27', '09:30:00');

INSERT INTO Registrar_Salida (codigo_registro_acceso, dni_cliente, fecha, hora) VALUES 
(101, '99999999Z', '2023-10-25', '11:00:00'),
(101, '88888888Y', '2023-10-25', '12:00:00'),
(102, '77777777X', '2023-10-26', '10:30:00'),
(102, '66666666W', '2023-10-26', '12:30:00'),
(103, '55555555V', '2023-10-27', '11:00:00');

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

INSERT INTO Ensenyar (entrenador1, entrenador2) VALUES 
('44444444D', '11111111A'),
('55555555E', '44444444D');

INSERT INTO Limpiar (turno_limpiador, id_zona_de_entrenos, fecha) VALUES 
('33333333C', 1, '2023-10-25'),
('33333333C', 2, '2023-10-25'),
('33333333C', 3, '2023-10-26');

INSERT INTO Realizar (dni_entrenador, codigo_clases, id_zona_entrenos, hora, descripcion) VALUES 
('11111111A', 502, 2, '10:00:00', 'Clase intensa de levantamiento'),
('44444444D', 501, 3, '18:00:00', 'Yoga para principiantes'),
('55555555E', 503, 1, '19:30:00', 'Pilates avanzado'),
('22222222B', 504, 4, '09:00:00', 'Sesión de Zumba cardio'),
('33333333C', 505, 5, '20:00:00', 'Entrenamiento de Boxeo y técnica');

INSERT INTO Entrenar (dni_cliente, id_zona_entrenos) VALUES 
('99999999Z', 2),
('88888888Y', 1),
('77777777X', 3),
('66666666W', 1),
('55555555V', 2);

INSERT INTO Administrador (id, usuario, contraseña, nombre) VALUES 
(1, 'carlos', '1234', 'Carlos'),
(2, 'admin', 'admin', 'Administrador Principal');

INSERT INTO Mensajes_Contacto (email, telefono, mensaje, es_empresa) VALUES 
('juan.interesado@email.com', '611222333', 'Hola, ¿tenéis entrenadores personales disponibles por la tarde?', FALSE),
('marta.gomez@email.com', '622333444', 'Me gustaría probar un día las instalaciones, ¿es posible?', FALSE),
('info@suplementosgym.es', '600123456', 'Buenas, somos una marca de suplementos y nos gustaría patrocinaros.', TRUE),
('lucas.fitness@email.com', '633444555', 'Quiero apuntarme al gimnasio el mes que viene, ¿hay matrícula?', FALSE);