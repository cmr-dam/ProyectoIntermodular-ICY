DROP TABLE IF EXISTS Mensajes_Contacto CASCADE;
DROP TABLE IF EXISTS Entrenar CASCADE;
DROP TABLE IF EXISTS Limpiar CASCADE;
DROP TABLE IF EXISTS Ensenyar CASCADE;
DROP TABLE IF EXISTS Realizar CASCADE;
DROP TABLE IF EXISTS Atender CASCADE;
DROP TABLE IF EXISTS Asistir CASCADE;
DROP TABLE IF EXISTS Acceder CASCADE;
DROP TABLE IF EXISTS Usar CASCADE;
DROP TABLE IF EXISTS Registrar_Salida CASCADE;
DROP TABLE IF EXISTS Registrar_Entrada CASCADE;
DROP TABLE IF EXISTS Cliente CASCADE;
DROP TABLE IF EXISTS Limpiador CASCADE;
DROP TABLE IF EXISTS Recepcionista CASCADE;
DROP TABLE IF EXISTS Entrenador CASCADE;
DROP TABLE IF EXISTS Administrador CASCADE;
DROP TABLE IF EXISTS Empleados CASCADE;
DROP TABLE IF EXISTS Zona_Entrenos CASCADE;
DROP TABLE IF EXISTS Clases CASCADE;
DROP TABLE IF EXISTS Spa CASCADE;
DROP TABLE IF EXISTS Vestuario CASCADE;
DROP TABLE IF EXISTS Registro_Acceso CASCADE;
DROP TABLE IF EXISTS Calculadora CASCADE;
DROP TABLE IF EXISTS Membresia CASCADE;

-- MEMBRESIA
CREATE TABLE Membresia (
    id INT PRIMARY KEY,
    tipo VARCHAR(50)
);

-- CALCULADORA IMC
CREATE TABLE Calculadora (
    id INT PRIMARY KEY,
    IMC DECIMAL(5,2)
);

-- REGISTRO DE ACCESO
CREATE TABLE Registro_Acceso (
    codigo INT PRIMARY KEY
);

-- ZONAS
CREATE TABLE Vestuario (
    id INT PRIMARY KEY,
    genero VARCHAR(20)
);

-- NOMBRE DEL SPA
CREATE TABLE Spa (
    nombre VARCHAR(50) PRIMARY KEY
);

-- TIPO DE CLASES
CREATE TABLE Clases (
    codigo INT PRIMARY KEY,
    tipo VARCHAR(50)
);

-- ZONAS DE ENTRENO
CREATE TABLE Zona_Entrenos (
    id INT PRIMARY KEY,
    tipo VARCHAR(50)
);

-- DATOS EMPLEADOS
CREATE TABLE Empleados (
    dni VARCHAR(20) PRIMARY KEY,
    telefono VARCHAR(20),
    nomina DECIMAL(10,2),
    nombre VARCHAR(50),
    apellido VARCHAR(50)
);

-- ADMINISTRADORES
CREATE TABLE Administrador (
    id INT PRIMARY KEY,
    usuario VARCHAR(50) UNIQUE,
    contraseña VARCHAR(50),
    nombre VARCHAR(50)
);

-- DATOS TIPOS DE EMPLEADOS
CREATE TABLE Entrenador (
    tipo_empleados VARCHAR(20) PRIMARY KEY,
    tipo VARCHAR(50),
    FOREIGN KEY (tipo_empleados) REFERENCES Empleados(dni) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Recepcionista (
    tipo_empleados VARCHAR(20) PRIMARY KEY,
    turno VARCHAR(20),
    FOREIGN KEY (tipo_empleados) REFERENCES Empleados(dni) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Limpiador (
    tipo_empleados VARCHAR(20) PRIMARY KEY,
    turno VARCHAR(20),
    FOREIGN KEY (tipo_empleados) REFERENCES Empleados(dni) ON DELETE CASCADE ON UPDATE CASCADE
);

-- DATOS CLIENTES
CREATE TABLE Cliente (
    dni VARCHAR(20) PRIMARY KEY,
    nombre VARCHAR(50),
    apellido VARCHAR(50),
    usuario VARCHAR(50) UNIQUE,
    contraseña VARCHAR(50),
    id_membresia INT,
    id_calculadora INT,
    ultimo_acceso TIMESTAMP,
    fecha_compra DATE DEFAULT CURRENT_DATE,
    FOREIGN KEY (id_membresia) REFERENCES Membresia(id) ON DELETE SET NULL ON UPDATE CASCADE,
    FOREIGN KEY (id_calculadora) REFERENCES Calculadora(id) ON DELETE SET NULL ON UPDATE CASCADE
);

-- REGISTRAR LA ENTRADA/SALIDA
CREATE TABLE Registrar_Entrada (
    codigo_registro_acceso INT,
    dni_cliente VARCHAR(20),
    fecha DATE,
    hora TIME,
    PRIMARY KEY (codigo_registro_acceso, dni_cliente),
    FOREIGN KEY (codigo_registro_acceso) REFERENCES Registro_Acceso(codigo) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (dni_cliente) REFERENCES Cliente(dni) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Registrar_Salida (
    codigo_registro_acceso INT,
    dni_cliente VARCHAR(20),
    fecha DATE,
    hora TIME,
    PRIMARY KEY (codigo_registro_acceso, dni_cliente),
    FOREIGN KEY (codigo_registro_acceso) REFERENCES Registro_Acceso(codigo) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (dni_cliente) REFERENCES Cliente(dni) ON DELETE CASCADE ON UPDATE CASCADE
);

-- USAR VESTUARIO 
CREATE TABLE Usar (
    id_vestuario INT,
    dni_cliente VARCHAR(20),
    PRIMARY KEY (id_vestuario, dni_cliente),
    FOREIGN KEY (id_vestuario) REFERENCES Vestuario(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (dni_cliente) REFERENCES Cliente(dni) ON DELETE CASCADE ON UPDATE CASCADE
);

-- ACCEDER AL SPA
CREATE TABLE Acceder (
    nombre_spa VARCHAR(50),
    dni_cliente VARCHAR(20),
    PRIMARY KEY (nombre_spa, dni_cliente),
    FOREIGN KEY (nombre_spa) REFERENCES Spa(nombre) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (dni_cliente) REFERENCES Cliente(dni) ON DELETE CASCADE ON UPDATE CASCADE
);

-- ASISTIR A CLASES
CREATE TABLE Asistir (
    codigo_clases INT,
    dni_cliente VARCHAR(20),
    PRIMARY KEY (codigo_clases, dni_cliente),
    FOREIGN KEY (codigo_clases) REFERENCES Clases(codigo) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (dni_cliente) REFERENCES Cliente(dni) ON DELETE CASCADE ON UPDATE CASCADE
);

-- EMPLEADO ATIENDE A CLIENTE
CREATE TABLE Atender (
    dni_empleados VARCHAR(20),
    dni_cliente VARCHAR(20),
    fecha DATE,
    descripcion VARCHAR(255),
    PRIMARY KEY (dni_empleados, dni_cliente),
    FOREIGN KEY (dni_empleados) REFERENCES Empleados(dni) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (dni_cliente) REFERENCES Cliente(dni) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Relación ternaria Realizar -> Entrenadores, Zona Entrenos y Clases
CREATE TABLE Realizar (
    dni_entrenador VARCHAR(20),
    codigo_clases INT,
    id_zona_entrenos INT NOT NULL,
    hora TIME,
    descripcion VARCHAR(255),
    PRIMARY KEY (dni_entrenador, codigo_clases),
    FOREIGN KEY (dni_entrenador) REFERENCES Entrenador(tipo_empleados) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (codigo_clases) REFERENCES Clases(codigo) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_zona_entrenos) REFERENCES Zona_Entrenos(id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- UN ENTRENADOR PUEDE ENSEÑAR A OTRO ENTRENADOR
CREATE TABLE Ensenyar (
    entrenador1 VARCHAR(20),
    entrenador2 VARCHAR(20),
    PRIMARY KEY (entrenador1, entrenador2),
    FOREIGN KEY (entrenador1) REFERENCES Entrenador(tipo_empleados) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (entrenador2) REFERENCES Entrenador(tipo_empleados) ON DELETE CASCADE ON UPDATE CASCADE
);

-- LIMPIADORES LIMPIAN ZONAS
CREATE TABLE Limpiar (
    turno_limpiador VARCHAR(20),
    id_zona_de_entrenos INT,
    fecha DATE,
    PRIMARY KEY (turno_limpiador, id_zona_de_entrenos),
    FOREIGN KEY (turno_limpiador) REFERENCES Limpiador(tipo_empleados) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_zona_de_entrenos) REFERENCES Zona_Entrenos(id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- ENTRENAR Clientes -> zonas de entreno
CREATE TABLE Entrenar (
    dni_cliente VARCHAR(20),
    id_zona_entrenos INT,
    PRIMARY KEY (dni_cliente, id_zona_entrenos),
    FOREIGN KEY (dni_cliente) REFERENCES Cliente(dni) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_zona_entrenos) REFERENCES Zona_Entrenos(id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Contacto que llega de la página web
CREATE TABLE Mensajes_Contacto (
    id SERIAL PRIMARY KEY,
    email VARCHAR(100),
    telefono VARCHAR(20),
    mensaje TEXT,
    es_empresa BOOLEAN
);