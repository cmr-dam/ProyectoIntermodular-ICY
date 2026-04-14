public class Calculadora {

	private int id;
	private double IMC;
	
	public Calculadora(int id, double IMC) {
		this.id = id;
		this.IMC = IMC;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setIMC(double IMC) {
		this.IMC = IMC;
	}
	public double getIMC() {
		return IMC;
	}
}
