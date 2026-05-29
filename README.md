# GymStats

Aplicación de escritorio para la gestión integral de centros deportivos, con portal web corporativo y base de datos centralizada en PostgreSQL.

Proyecto intermodular de 1º DAM desarrollado por Carlos, Yon e Ian.

---

## Índice

- [Descripción](#descripción)
- [Tecnologías utilizadas](#tecnologías-utilizadas)
- [Arquitectura del proyecto](#arquitectura-del-proyecto)
- [Módulos de la aplicación](#módulos-de-la-aplicación)
- [Base de datos](#base-de-datos)
- [Portal web](#portal-web)
- [Instalación y despliegue](#instalación-y-despliegue)
- [Estructura del repositorio](#estructura-del-repositorio)
- [Control de versiones](#control-de-versiones)
- [Equipo de desarrollo](#equipo-de-desarrollo)
- [Trabajo futuro](#trabajo-futuro)

---

## Descripción

GymStats es un sistema de gestión integral para centros deportivos. Su objetivo es digitalizar y centralizar los procesos operativos del gimnasio mediante una solución tecnológica robusta y escalable.

El sistema abarca tres áreas clave:

- Administración de clientes y sus membresías.
- Organización del personal estructurada por perfiles: recepcionistas, limpiadores, entrenadores y administradores.
- Coordinación de la oferta deportiva: gestión de clases, zonas de entreno y buzón de contacto web.

La aplicación conecta un escritorio desarrollado en Java con un portal web corporativo, ambos operando sobre una misma base de datos PostgreSQL centralizada.

---

## Tecnologías utilizadas

| Capa | Tecnología |
|------|------------|
| Lenguaje principal | Java (JDK 17+) |
| Interfaz gráfica | Java Swing + FlatDarkLaf |
| Persistencia | PostgreSQL + JDBC |
| Portal web | HTML5, CSS3, JavaScript |
| API backend | PHP con PDO y sentencias preparadas |
| IDE | Eclipse |
| Control de versiones | Git + GitHub (GitFlow) |
| Infraestructura | Ubuntu Server con IP fija |

---

## Arquitectura del proyecto

La aplicación de escritorio sigue una arquitectura MVC simplificada. Las clases de modelo (`Cliente`, `Usuario`, `Membresia`) representan la lógica de negocio. Las vistas y controladores están unificados en las clases de interfaz, que se comunican directamente con PostgreSQL mediante JDBC.

```
Aplicación Java (MVC)
        |
        | JDBC
        v
  PostgreSQL
        ^
        | PHP PDO
        |
  Portal Web (HTML + CSS + JS)
```

---

## Módulos de la aplicación

### Autenticación y control de accesos

El `PanelLogin` verifica las credenciales del usuario realizando consultas secuenciales en la base de datos para determinar el rol. En función del resultado, el sistema abre el panel correspondiente: administrador, empleado o cliente. Si el usuario es un cliente, el sistema registra automáticamente su hora de entrada.

### Panel del Administrador

Acceso completo a las siguientes pestañas: socios, personal, actividades, control de accesos y mensajes de contacto. Es el único rol con acceso al módulo de mensajes de contacto por motivos de privacidad corporativa.

### Panel del Empleado

Reutiliza la estructura del panel de administrador pero restringe el acceso a la gestión de personal y a los mensajes de contacto web.

### Panel del Cliente

Interfaz de solo lectura que muestra la membresía activa y calcula los días restantes de acceso. Incluye una calculadora de Índice de Masa Corporal que aplica la fórmula, muestra el resultado en pantalla y guarda el valor en el perfil del cliente.

### Gestión de actividades

Permite visualizar y planificar las clases dirigidas del centro (Zumba, CrossFit, Yoga, entre otras). La tabla resulta de una consulta que une las entidades `Entrenador`, `Clase` y `Zona_Entreno`, representando la relación ternaria del modelo de datos.

### Control de accesos

Registro cronológico de entradas y salidas de los socios. Si un cliente ha fichado su entrada pero aún no ha registrado la salida, el sistema muestra automáticamente el texto "Dentro". Permite a administradores y empleados corregir manualmente los registros.

### Mensajes de contacto

Actúa como puente entre la aplicación de escritorio y el formulario de contacto del portal web. Los mensajes enviados desde la web se insertan en la tabla `Mensajes_Contacto` y el administrador puede leerlos desde la aplicación haciendo doble clic sobre cualquier fila.

### Formularios CRUD

Ventanas emergentes para inserción y modificación de datos: `AñadirSocio`, `AñadirEmpleado`, `AñadirActividad` y sus equivalentes de modificación. Todos los formularios incluyen validación de campos obligatorios antes de ejecutar las sentencias `INSERT` o `UPDATE`.

---

## Base de datos

La base de datos está implementada en PostgreSQL e incluye triggers, vistas y funciones para automatizar reglas de negocio y garantizar la integridad de los datos.

### Entidades principales

| Tabla | Descripción |
|-------|-------------|
| `Cliente` | Datos personales de los socios (nombre, apellido, DNI) |
| `Membresia` | Tipos de membresía disponibles |
| `Empleado` | Datos comunes del personal (DNI, teléfono, nombre, apellido, nómina) |
| `Administrador` | Subentidad con usuario y contraseña de acceso |
| `Recepcionista` | Subentidad con turno asignado |
| `Limpiador` | Subentidad con turno día/noche |
| `Entrenador` | Subentidad con especialidad deportiva |
| `Clase` | Tipos de clases ofertadas |
| `Zona_Entreno` | Espacios físicos del gimnasio |
| `Registrar_Entrada` | Log de entradas con fecha y hora |
| `Registrar_Salida` | Log de salidas con fecha y hora |
| `Mensajes_Contacto` | Buzón de mensajes del portal web (aislado del resto del modelo) |

### Relaciones destacadas

- Un cliente puede comprar una sola membresía; una membresía puede ser comprada por muchos clientes.
- Una clase en una zona de entreno es impartida por uno o varios entrenadores (relación ternaria).
- Un entrenador puede ser mentor de otros entrenadores y a su vez haber sido formado por varios (relación reflexiva).
- Muchos limpiadores limpian en muchas zonas de entreno; se registra la fecha de cada acción.

---

## Portal web

El portal corporativo de GymStats actúa como estrategia de marketing y punto de contacto externo. Está desarrollado con HTML5 semántico, CSS3 (Flexbox y Grid) y JavaScript.

**Secciones:** inicio, tarifas, historia de la empresa y catálogo de rutinas deportivas.

**Funcionalidades implementadas:**

- Sistema de traducción bilingüe (español / catalán) con preferencia guardada en `localStorage`.
- Cambio de tema visual (modo oscuro/claro) persistente entre sesiones.
- Validación del formulario de contacto mediante expresiones regulares para correo electrónico y número de teléfono.
- Lectura en tiempo real del número de usuarios registrados mediante petición asíncrona a la API PHP.
- Inserción segura de mensajes de contacto en la base de datos usando sentencias preparadas PDO, previniendo inyecciones SQL.

---

## Instalación y despliegue

### Requisitos previos

- Java JDK 17 o superior
- PostgreSQL 14 o superior
- Servidor web con PHP 8.0 o superior
- Ubuntu Server con IP fija y acceso remoto configurado

### 1. Clonar el repositorio

```bash
git clone https://github.com/<usuario>/gymstats.git
cd gymstats
```

### 2. Configurar la base de datos

```bash
psql -U postgres
\i database/gymstats_schema.sql
\i database/gymstats_seed.sql
```

### 3. Configurar la conexión JDBC

Editar los parámetros de conexión en `src/main/Main.java`:

```java
String url  = "jdbc:postgresql://localhost:5432/gymstats";
String user = "tu_usuario";
String pass = "tu_contraseña";
```

### 4. Ejecutar la aplicación

Importar el proyecto en Eclipse y ejecutar `Main.java`, o compilar desde línea de comandos:

```bash
javac -cp .:lib/* src/**/*.java -d out/
java -cp out/:lib/* main.Main
```

### 5. Desplegar el portal web

Copiar la carpeta `web/` al directorio raíz del servidor y configurar la conexión en `web/api/config.php`:

```php
$host = 'localhost';
$db   = 'gymstats';
$user = 'tu_usuario';
$pass = 'tu_contraseña';
```

---

## Estructura del repositorio

```
gymstats/
├── src/
│   ├── main/
│   │   └── Main.java
│   ├── modelo/
│   │   ├── Cliente.java
│   │   ├── Usuario.java
│   │   └── Membresia.java
│   └── vistas/
│       ├── PanelLogin.java
│       ├── PanelAdministrador.java
│       ├── PanelEmpleado.java
│       ├── PanelUsuario.java
│       └── formularios/
│           ├── AñadirSocio.java
│           ├── AñadirEmpleado.java
│           ├── ModificarEmpleado.java
│           └── AñadirActividad.java
├── web/
│   ├── index.html
│   ├── rutinas.html
│   ├── css/
│   ├── js/
│   ├── img/
│   └── api/
│       └── contacto.php
├── database/
│   ├── gymstats_schema.sql
│   ├── gymstats_seed.sql
│   └── gymstats_triggers.sql
├── docs/
│   └── memoria.pdf
└── README.md
```

---

## Control de versiones

El proyecto sigue la metodología GitFlow con la siguiente estructura de ramas:

- **main**: versiones estables y entrega final del proyecto.
- **develop**: punto de sincronización e integración continua entre los miembros del equipo.
- **feature/\***: desarrollo aislado de cada funcionalidad. Una vez superadas las pruebas, se integra en `develop`.

Las ramas `release` y `hotfix` no han sido necesarias en el alcance de este proyecto.

---

## Equipo de desarrollo

| Miembro | Responsabilidad principal |
|---------|---------------------------|
| Carlos | Desarrollo de la aplicación de escritorio en Java. Configuración y despliegue de las máquinas virtuales. |
| Yon | Desarrollo del portal web corporativo. Redacción y maquetación de la memoria técnica. |
| Ian | Programación avanzada de la base de datos (triggers, vistas y funciones). Documentación del módulo IPE. Preparación de la presentación oral. |

Todos los miembros participaron en la depuración de errores y en la gestión del control de versiones con Git.

---

## Trabajo futuro

- Pasarela de pago para el cobro automático de membresías desde el portal web.
- Aplicación móvil con tarjeta de acceso digital mediante código QR.
- Panel de métricas con gráficos para analizar la rentabilidad del gimnasio y las horas punta de asistencia.

---

Proyecto académico desarrollado para el ciclo formativo de Desarrollo de Aplicaciones Multiplataforma (DAM).
