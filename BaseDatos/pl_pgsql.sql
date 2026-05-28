
-- GYMSTATS - pl_pgsql.sql
-- Vistas, Funciones (con Cursor) y Triggers

-- VISTAS

-- Vista 1: Clientes con su membresia
-- Une la tabla Cliente con Membresia para ver de un vistazo
-- que tipo de membresia tiene cada socio del gimnasio.
CREATE OR REPLACE VIEW vista_clientes_membresia AS
SELECT c.dni, c.nombre, c.apellido, m.tipo AS tipo_membresia
FROM Cliente c, Membresia m
WHERE c.id_membresia = m.id;


-- Vista 2: Clases con el entrenador que las imparte
-- Une Clases, Realizar y Empleados para ver que entrenador
-- da cada clase y a que hora.
CREATE OR REPLACE VIEW vista_clases_entrenador AS
SELECT cl.codigo, cl.tipo AS tipo_clase, r.hora, e.nombre AS nombre_entrenador, e.apellido AS apellido_entrenador
FROM Clases cl, Realizar r, Empleados e
WHERE cl.codigo = r.codigo_clases
AND r.dni_entrenador = e.dni;


-- FUNCIONES

-- Funcion 1: Contar clientes segun su tipo de membresia
-- Le pasas el tipo ('Basica', 'Premium'...) y te dice cuantos clientes la tienen.
CREATE OR REPLACE FUNCTION contar_clientes_por_membresia(p_tipo VARCHAR)
RETURNS INT AS $$
DECLARE
    total INT;
BEGIN
    SELECT COUNT(*)
    INTO total
    FROM Cliente c, Membresia m
    WHERE c.id_membresia = m.id
    AND m.tipo = p_tipo;

    RETURN total;
END;
$$ LANGUAGE plpgsql;

-- Ejemplo de uso:
-- SELECT contar_clientes_por_membresia('Premium');


-- Funcion 2: Ver la nomina de un empleado
-- Le pasas el DNI y te devuelve su nomina.
-- Si el empleado no existe devuelve -1.
CREATE OR REPLACE FUNCTION obtener_nomina_empleado(p_dni VARCHAR)
RETURNS DECIMAL(10,2) AS $$
DECLARE
    v_nomina DECIMAL(10,2);
BEGIN
    SELECT nomina
    INTO v_nomina
    FROM Empleados
    WHERE dni = p_dni;

    IF NOT FOUND THEN
        RETURN -1;
    END IF;

    RETURN v_nomina;
END;
$$ LANGUAGE plpgsql;

-- Ejemplo de uso:
-- SELECT obtener_nomina_empleado('12345678A');


-- Funcion 3: Ver cuantas clases tiene un entrenador
-- Le pasas el DNI del entrenador y te dice cuantas clases imparte.
CREATE OR REPLACE FUNCTION clases_por_entrenador(p_dni VARCHAR)
RETURNS INT AS $$
DECLARE
    total INT;
BEGIN
    SELECT COUNT(*)
    INTO total
    FROM Realizar
    WHERE dni_entrenador = p_dni;

    RETURN total;
END;
$$ LANGUAGE plpgsql;

-- Ejemplo de uso:
-- SELECT clases_por_entrenador('12345678A');


-- Funcion 4 (CON CURSOR): Listar clientes con su membresia
-- Usa un cursor para recorrer todos los clientes uno a uno
-- y devuelve su nombre completo junto al tipo de membresia.
-- Si no tiene membresia asignada aparece 'Sin membresia'.
CREATE OR REPLACE FUNCTION listar_clientes_con_membresia()
RETURNS TABLE(nombre_completo TEXT, tipo_membresia TEXT) AS $$
DECLARE
    cur_clientes CURSOR FOR
        SELECT c.nombre, c.apellido, m.tipo
        FROM Cliente c, Membresia m
        WHERE c.id_membresia = m.id;

    v_nombre   VARCHAR(50);
    v_apellido VARCHAR(50);
    v_tipo     VARCHAR(50);
BEGIN
    OPEN cur_clientes;

    LOOP
        FETCH cur_clientes INTO v_nombre, v_apellido, v_tipo;
        EXIT WHEN NOT FOUND;

        nombre_completo := v_nombre || ' ' || v_apellido;
        tipo_membresia  := COALESCE(v_tipo, 'Sin membresia');
        RETURN NEXT;
    END LOOP;

    CLOSE cur_clientes;
END;
$$ LANGUAGE plpgsql;

-- Ejemplo de uso:
-- SELECT * FROM listar_clientes_con_membresia();


-- TRIGGERS

-- Trigger 1 (GLOBAL - FOR EACH STATEMENT)
-- No se puede borrar ningun administrador del sistema.
-- Al ser de tipo STATEMENT se ejecuta una vez por sentencia DELETE
-- y la bloquea entera, sin importar cuantas filas afecte.
CREATE OR REPLACE FUNCTION fn_no_borrar_administradores()
RETURNS TRIGGER AS $$
BEGIN
    RAISE EXCEPTION 'No se permite eliminar administradores del sistema.';
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_no_borrar_administradores ON Administrador;
CREATE TRIGGER trg_no_borrar_administradores
BEFORE DELETE ON Administrador
FOR EACH STATEMENT
EXECUTE FUNCTION fn_no_borrar_administradores();


-- Trigger 2 (GLOBAL - FOR EACH STATEMENT)
-- No se puede borrar ningun cliente del sistema.
-- Igual que el anterior, actua a nivel de sentencia y
-- cancela cualquier DELETE sobre la tabla Cliente.
CREATE OR REPLACE FUNCTION fn_no_borrar_clientes()
RETURNS TRIGGER AS $$
BEGIN
    RAISE EXCEPTION 'No se permite eliminar clientes desde este sistema.';
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_no_borrar_clientes ON Cliente;
CREATE TRIGGER trg_no_borrar_clientes
BEFORE DELETE ON Cliente
FOR EACH STATEMENT
EXECUTE FUNCTION fn_no_borrar_clientes();


-- Trigger 3 (POR FILA - FOR EACH ROW)
-- Cada vez que un cliente registra una entrada al gimnasio,
-- se actualiza automaticamente su campo ultimo_acceso en la tabla Cliente.
-- Asi no hay que hacerlo a mano cada vez que alguien entre.
CREATE OR REPLACE FUNCTION fn_actualizar_ultimo_acceso()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE Cliente
    SET ultimo_acceso = NOW()
    WHERE dni = NEW.dni_cliente;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_actualizar_ultimo_acceso ON Registrar_Entrada;
CREATE TRIGGER trg_actualizar_ultimo_acceso
AFTER INSERT ON Registrar_Entrada
FOR EACH ROW
EXECUTE FUNCTION fn_actualizar_ultimo_acceso();


-- Trigger 4 (POR FILA - FOR EACH ROW)
-- Antes de insertar o modificar un empleado, comprueba que
-- la nomina no sea un numero negativo. Si lo es, lanza un error
-- y no deja guardar el dato.
CREATE OR REPLACE FUNCTION fn_validar_nomina()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.nomina IS NOT NULL AND NEW.nomina < 0 THEN
        RAISE EXCEPTION 'La nomina no puede ser negativa. Valor introducido: %', NEW.nomina;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_validar_nomina ON Empleados;
CREATE TRIGGER trg_validar_nomina
BEFORE INSERT OR UPDATE ON Empleados
FOR EACH ROW
EXECUTE FUNCTION fn_validar_nomina();
