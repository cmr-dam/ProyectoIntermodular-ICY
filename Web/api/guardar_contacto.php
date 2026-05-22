<?php
include_once 'conexion.php';

$datos = json_decode(file_get_contents("php://input"));

if(!empty($datos->email) && !empty($datos->telefono) && !empty($datos->mensaje)) {
    try {
        $query = "INSERT INTO Mensajes_Contacto (email, telefono, mensaje, es_empresa) VALUES (:email, :telefono, :mensaje, :es_empresa)";
        $stmt = $conexion->prepare($query);
        
        $email = htmlspecialchars(strip_tags($datos->email));
        $telefono = htmlspecialchars(strip_tags($datos->telefono));
        $mensaje = htmlspecialchars(strip_tags($datos->mensaje));
        $es_empresa = $datos->esEmpresa ? 'true' : 'false';
        
        $stmt->bindParam(":email", $email);
        $stmt->bindParam(":telefono", $telefono);
        $stmt->bindParam(":mensaje", $mensaje);
        $stmt->bindParam(":es_empresa", $es_empresa);
        
        if($stmt->execute()) {
            http_response_code(201);
            echo json_encode(["mensaje" => "Contacto guardado correctamente."]);
        } else {
            http_response_code(503);
            echo json_encode(["mensaje" => "No se pudo guardar."]);
        }
    } catch(Exception $e) {
        http_response_code(500);
        echo json_encode(["mensaje" => "Error: " . $e->getMessage()]);
    }
} else {
    http_response_code(400);
    echo json_encode(["mensaje" => "Datos incompletos."]);
}
?>