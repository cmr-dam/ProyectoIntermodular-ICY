
public class Cliente extends Usuario {
	private String nombre;
	private int edad;
	private double peso;
	private boolean membresia_activa;
	private boolean acceso_spa;
	
	//CONSTRUCTOR
	public Cliente(int id, String nombreUsuario, String contraseña, String nombre, int edad, double peso, boolean membresia_activa, boolean acceso_spa) {
		super(id, nombreUsuario, contraseña);
		this.nombre = nombre;
		this.edad = edad;
		this.peso = peso;
		this.membresia_activa = membresia_activa;
		this.acceso_spa = acceso_spa;
	}

	//SETTERS-GETTERS
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getEdad() {
		return edad;
	}
	public void setEdad(int edad) {
		this.edad = edad;
	}
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}
	public boolean isMembresia_activa() {
		return membresia_activa;
	}
	public void setMembresia_activa(boolean membresia_activa) {
		this.membresia_activa = membresia_activa;
	}
	public boolean isAcceso_spa() {
		return acceso_spa;
	}
	public void setAcceso_spa(boolean acceso_spa) {
		this.acceso_spa = acceso_spa;
	}

	//TO-STRING
	@Override
	public String toString() {
		return "Cliente [nombre=" + nombre + ", edad=" + edad + ", peso=" + peso + ", membresia_activa="
				+ membresia_activa + ", acceso_spa=" + acceso_spa + "]";
	}
}
