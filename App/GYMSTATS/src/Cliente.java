public class Cliente {
//    dni VARCHAR(20) PRIMARY KEY
//    nombre VARCHAR(50)
//    apellido VARCHAR(50)
//    id_membresia INT
//    id_calculadora INT
//    fecha_compra DATE
	
	private String dni; 
	private String nombre; 
	private int id_membresia;
	private int id_calculadora;
	// Falta fecha (Date)
	
	public Cliente(String dni) {
		this.dni = dni;
	}

	public Cliente(String dni, String nombre, int id_membresia, int id_calculadora) {
		this.dni = dni;
		this.nombre = nombre;
		this.id_membresia = id_membresia;
		this.id_calculadora = id_calculadora;
	}
	
	
	public String getDni() {
		return dni;
	}


	public void setDni(String dni) {
		this.dni = dni;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public int getId_membresia() {
		return id_membresia;
	}


	public void setId_membresia(int id_membresia) {
		this.id_membresia = id_membresia;
	}


	public int getId_calculadora() {
		return id_calculadora;
	}


	public void setId_calculadora(int id_calculadora) {
		this.id_calculadora = id_calculadora;
	}
	
}
