-- a. 5 Consultas simples de una sola tabla76

-- Todos los empleados
SELECT *
FROM Empleados;

-- Nombres y apellidos de clientes
SELECT nombre, apellido 
FROM Cliente;

-- Tipos de membresias
SELECT tipo
FROM Membresia;

-- Tipos de zonas de entreno
SELECT tipo
FROM Zona_Entrenos;

-- Correos del formulario de contacto web
SELECT email
FROM Mensajes_Contacto;


-- b. 2 Actualizaciones y 2 Borrados en cualquier tabla

-- Actualizar telefono de un empleado
UPDATE Empleados 
SET telefono = '699000000' 
WHERE dni = '11111111A';

-- Actualizar nombre de una clase
UPDATE Clases 
SET tipo = 'Yoga Avanzado' 
WHERE codigo = 501;

-- Borrar un acceso
DELETE 
FROM Registro_Acceso 
WHERE codigo = 105;

-- Borrar un mensaje de contacto de la web
DELETE 
FROM Mensajes_Contacto 
WHERE id = 1;


-- c. 3 Consultas con más de 1 tabla

-- Cliente y su membresia
SELECT Cliente.nombre, Membresia.tipo 
FROM Cliente, Membresia 
WHERE Cliente.id_membresia = Membresia.id;

-- Entrenador y el codigo de sus clases
SELECT Empleados.nombre, Realizar.codigo_clases 
FROM Empleados, Realizar 
WHERE Empleados.dni = Realizar.dni_entrenador;

-- Cliente y su IMC
SELECT Cliente.nombre, Calculadora.IMC 
FROM Cliente, Calculadora 
WHERE Cliente.id_calculadora = Calculadora.id;


-- d. 3 Consultas usando funciones

-- Total gasto en nominas
SELECT SUM(nomina) 
FROM Empleados;

-- Numero total de clientes
SELECT COUNT(*) 
FROM Cliente;

-- IMC mas alto
SELECT MAX(IMC) 
FROM Calculadora;


-- e. 2 Consultas usando group by

-- Clientes por id de membresia
SELECT id_membresia, COUNT(*) 
FROM Cliente 
GROUP BY id_membresia;

-- Recepcionistas por turno
SELECT turno, COUNT(*) 
FROM Recepcionista 
GROUP BY turno;


-- f. 2 Consultas utilizando subconsultas

-- Clientes con IMC mayor a la media
SELECT nombre FROM Cliente 
WHERE id_calculadora IN (
    SELECT id 
    FROM Calculadora 
    WHERE IMC > (
        SELECT AVG(IMC) 
        FROM Calculadora));

-- Empleados que cobran mas que Carlos Sánchez
SELECT nombre, apellido FROM Empleados 
WHERE nomina > (
    SELECT nomina 
    FROM Empleados 
    WHERE nombre = 'Carlos' 
    AND apellido = 'Sánchez'
);


-- g. 2 Consultas usando group by con having

-- Membresias con un cliente o mas de 1 clientes
SELECT id_membresia, COUNT(*) 
FROM Cliente 
GROUP BY id_membresia 
HAVING COUNT(*) >= 1;

-- Turnos de limpieza con empleados
SELECT turno, COUNT(*) 
FROM Limpiador 
GROUP BY turno 
HAVING COUNT(*) > 0;


-- h. 3 actualizaciones usando subconsultas en where y set

-- Subir sueldo a entrenadores de musculacion
UPDATE Empleados 
SET nomina = nomina * 1.10 
WHERE dni IN (
    SELECT tipo_empleados 
    FROM Entrenador 
    WHERE tipo = 'Musculatura'
);

-- Cambiar a membresia 2 a clientes con IMC mayor a 27
UPDATE Cliente 
SET id_membresia = 2 
WHERE id_calculadora IN (
    SELECT id 
    FROM Calculadora 
    WHERE IMC > 27
);

-- Actualizar descripcion de atencion de Laura Gomez
UPDATE Atender 
SET descripcion = 'Seguimiento prioritario' 
WHERE dni_empleados = (
    SELECT dni 
    FROM Empleados 
    WHERE nombre = 'Laura' 
    AND apellido = 'Gómez'
);


-- EXTRAS

-- Datos completos del cliente
SELECT C.nombre, C.apellido, M.tipo, Ca.IMC 
FROM Cliente C, Membresia M, Calculadora Ca 
WHERE C.id_membresia = M.id 
AND C.id_calculadora = Ca.id;

-- Entrenador, clase y zona de entreno
SELECT E.nombre, Cl.tipo, Z.tipo 
FROM Realizar R, Empleados E, Clases Cl, Zona_Entrenos Z 
WHERE R.dni_entrenador = E.dni 
AND R.codigo_clases = Cl.codigo
AND R.id_zona_entrenos = Z.id;

-- Clientes y la fecha de sus registros de entrada
SELECT C.nombre, R.fecha 
FROM Cliente C, Registrar_Entrada R 
WHERE C.dni = R.dni_cliente;

-- Entrenadores que imparten más clases que la media general
SELECT E.nombre, E.apellido, COUNT(R.codigo_clases) AS Total_Clases
FROM Empleados E, Entrenador En, Realizar R
WHERE E.dni = En.tipo_empleados 
  AND En.tipo_empleados = R.dni_entrenador
GROUP BY E.nombre, E.apellido
HAVING COUNT(R.codigo_clases) >= (
    SELECT AVG(Conteo.Total) 
    FROM (
        SELECT dni_entrenador, COUNT(codigo_clases) AS Total
        FROM Realizar
        GROUP BY dni_entrenador
    ) AS Conteo
);