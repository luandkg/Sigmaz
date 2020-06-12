package Sigmaz.Utils;

public class Requerimento {

	private String mNome;
	private String mConteudo;

	public Requerimento(String eNome, String eConteudo) {
		mNome = eNome;
		mConteudo = eConteudo;
	}

	public String getNome() {
		return mNome;
	}

	public String getConteudo() {
		return mConteudo;
	}

}
