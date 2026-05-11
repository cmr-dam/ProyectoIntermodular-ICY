<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");

$host = "localhost";
$db_name = "tu_base_de_datos"; // CAMBIA ESTO por el nombre de tu BDD
$username = "postgres";        // CAMBIA ESTO por tu usuario
$password = "tu_contraseña";   // CAMBIA ESTO por tu contraseña

try {
    $conexion = new PDO("pgsql:host=$host;dbname=$db_name", $username, $password);
    $conexion->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    $conexion->setAttribute(PDO::ATTR_DEFAULT_FETCH_MODE, PDO::FETCH_ASSOC);
} catch (PDOException $exception) {
    http_response_code(500);
    echo json_encode(["mensaje" => "Error de conexión (PostgreSQL): " . $exception->getMessage()]);
    exit();
}
?>