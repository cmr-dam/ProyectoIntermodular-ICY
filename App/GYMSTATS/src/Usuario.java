
public abstract class Usuario {
	private int id;
	private String nombreUsuario;
	private String contraseña;
	
	//CONSTRUCTOR
	public Usuario(int id, String nombre, String contraseña) {
		this.id = id;
		this.nombreUsuario = nombre;
		this.contraseña = contraseña;
	}

	//GETTERS-SETTERS
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombreUsuario;
	}
	public void setNombre(String nombre) {
		this.nombreUsuario = nombre;
	}
	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}
	public String getContraseña() {
		return contraseña;
	}

	//TO-STRING
	@Override
	public String toString() {
		return "Usuario [nombre=" + nombreUsuario + ", contraseña=" + contraseña + "]";
	}
}
