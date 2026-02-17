INSERT INTO Membresia (id, tipo) VALUES 
(1, 'Básica'),
(2, 'Premium');

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
('Jacuzzi');

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
('44444444D', '600777888', 1800.00, 'Ana', 'Martínez');

INSERT INTO Entrenador (tipo_empleados, tipo) VALUES 
('11111111A', 'Musculación'),
('44444444D', 'General');

INSERT INTO Recepcionista (tipo_empleados, turno) VALUES 
('22222222B', 'Mañana');

INSERT INTO Limpiador (tipo_empleados, turno) VALUES 
('33333333C', 'Tarde');

INSERT INTO Cliente (dni, nombre, apellido, id_membresia, id_calculadora) VALUES 
('99999999Z', 'Juan', 'Pérez', 2, 1),
('88888888Y', 'Maria', 'López', 1, 2),
('77777777X', 'Luis', 'Torres', 3, 3);

INSERT INTO Registrar_Entrada (codigo_registro_acceso, dni_cliente, fecha, hora) VALUES 
(101, '99999999Z', '2023-10-25', '09:00:00'),
(101, '88888888Y', '2023-10-25', '10:30:00');

INSERT INTO Registrar_Salida (codigo_registro_acceso, dni_cliente, fecha, hora) VALUES 
(101, '99999999Z', '2023-10-25', '11:00:00');

INSERT INTO Usar (id_vestuario, dni_cliente) VALUES 
(1, '99999999Z'),
(2, '88888888Y');

INSERT INTO Acceder (nombre_spa, dni_cliente) VALUES 
('Jacuzzi Central', '77777777X');

INSERT INTO Asistir (codigo_clases, dni_cliente) VALUES 
(501, '88888888Y'),
(502, '99999999Z');

INSERT INTO Atender (dni_empleados, dni_cliente) VALUES 
('22222222B', '99999999Z');

INSERT INTO Enseñar (entrenador1, entrenador2) VALUES 
('44444444D', '11111111A');

INSERT INTO Limpiar (turno_limpiador, id_zona_de_entrenos, fecha) VALUES 
('33333333C', 1, '2023-10-25');