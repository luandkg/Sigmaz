package Sigmaz.Utils.LuanDKG;

public class Identificador {

	private String mNome;
	private String mValor;

	public Identificador(String eNome) {
		mNome = eNome;
		mValor = "";
	}

	public Identificador(String eNome, String eValor) {
		mNome = eNome;
		mValor = eValor;
	}

	public void setNome(String eNome) {
		mNome = eNome;
	}

	public String getNome() {
		return mNome;
	}

	public void setValor(String eValor) {
		mValor = eValor;
	}

	public void setInteiro(int eValor) {
		mValor = String.valueOf(eValor);
	}

	public void setFloat(float eValor) {
		mValor = String.valueOf(eValor);
	}

	public void setDouble(double eValor) {
		mValor = String.valueOf(eValor);
	}

	public void setBool(boolean eValor) {
		mValor = String.valueOf(eValor);
	}

	public String getValor() {
		return mValor;
	}

	public int getInteiro() {
		return Integer.parseInt(getValor());
	}

	public float getFloat() {
		return Float.parseFloat(getValor());
	}

	public double getDouble() {
		return Double.parseDouble(getValor());
	}

	public boolean getBool() {
		return Boolean.parseBoolean(getValor());
	}

}
