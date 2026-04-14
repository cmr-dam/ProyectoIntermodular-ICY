public class Entrenador extends Empleados{
	
	private String tipo; 
	
	public Entrenador(String dni) {
		super(dni); 
	}
	
	public Entrenador(String dni, int telefono, double nomina, String nombre, String apellido, String tipo) {
		super(dni, telefono, nomina, nombre, apellido);
		this.tipo = tipo; 
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
}
