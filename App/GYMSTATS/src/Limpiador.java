public class Limpiador extends Empleados{
	
	private String turno;
	
	public Limpiador(String dni) {
		super(dni); // DNI 
	}
	
	public Limpiador(String dni, int telefono, double nomina, String nombre, String apellido, String turno) {
		super(dni, telefono, nomina, nombre, apellido);
		this.turno = turno; 
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}
	
	
	
	
}
