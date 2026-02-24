package Main;

public class Recepcionista extends Empleados{
	
	private String turno;
	
	public Recepcionista(String dni) {
		super(dni);
	}
	
	
	public Recepcionista(String dni, int telefono, double nomina, String nombre, String apellido, String turno) {
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
