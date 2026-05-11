<?php
include_once 'conexion.php';

try {
    // Cuenta cuántos registros hay en la tabla Cliente
    $query = "SELECT COUNT(*) as total FROM Cliente";
    $stmt = $conexion->prepare($query);
    $stmt->execute();
    
    $resultado = $stmt->fetch();
    
    http_response_code(200);
    echo json_encode(["total" => $resultado['total']]);
} catch(PDOException $e) {
    http_response_code(500);
    echo json_encode(["mensaje" => "Error: " . $e->getMessage()]);
}
?>