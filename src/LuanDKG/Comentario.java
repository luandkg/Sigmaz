package LuanDKG;

public class Comentario {

	private String mNome;
	private String mValor;

	public Comentario(String eNome, String eValor) {
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

	public String getValor() {
		return mValor;
	}

}
