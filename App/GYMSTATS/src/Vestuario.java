public class Vestuario {
	
	private int id;
	private String genero;
	
	public Vestuario(int id, String genero) {
		this.id = id;
		this.genero = genero;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}
	public String getGenero() {
		return genero;
	}
	public String toString() {
		return "Genero: " + genero + "\nId: " + id;
	}
}
