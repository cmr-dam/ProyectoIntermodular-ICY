public class Zona_Entrenos {
	
	private int id;
	private String tipo;
	
	public Zona_Entrenos (int id) {
		this.id = id;
	}
	
	public Zona_Entrenos (String tipo, int id) {
		this.tipo = tipo;
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}