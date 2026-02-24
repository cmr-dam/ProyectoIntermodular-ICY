package Main;

public class Clases {
	
	private int codigo;
	private String tipo;
	
	public Clases(int codigo, String tipo) {
		this.codigo = codigo;
		this.tipo = tipo;
	}

	public Clases(int codigo) {
		this.codigo = codigo;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}