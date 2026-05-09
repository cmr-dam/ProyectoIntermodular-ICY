
public class Administrador extends Usuario {
	String nombre;
	
	
	//CONSTRUCTOR
	public Administrador(int id, String nombreUsuario, String contraseña, String nombre) {
		super(id, nombreUsuario, contraseña);
		this.nombre = nombre;
	}
	
	//GETTERS-SETTERS
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	//TOSTRING
	@Override
	public String toString() {
		return "Administrador " + super.toString() +  ", nombre=" + nombre + "]";
	}
}
