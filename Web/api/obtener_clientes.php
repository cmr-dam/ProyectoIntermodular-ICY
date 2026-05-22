<?php
// Para limpiar cualquier cosa que se haya imprimido antes por error
ob_clean(); 
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");

include_once 'conexion.php';

try {
    $conexion = new PDO("pgsql:host=$host;dbname=$db_name", $username, $password);
    $conexion->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    $conexion->setAttribute(PDO::ATTR_DEFAULT_FETCH_MODE, PDO::FETCH_ASSOC);
    
    $query = "SELECT COUNT(*) as total_clientes FROM Cliente";
    $stmt = $conexion->prepare($query);
    $stmt->execute();
    
    $resultado = $stmt->fetch(PDO::FETCH_ASSOC);
    
    // Imprimimos SOLO el JSON limpio
    http_response_code(200);
    echo json_encode(array(
        "total" => $resultado['total_clientes']
    ));
    
} catch (PDOException $exception) {
    http_response_code(500);
    echo json_encode(["mensaje" => "Error: " . $exception->getMessage()]);
}
?>