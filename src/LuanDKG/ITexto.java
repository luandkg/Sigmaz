package LuanDKG;

public class ITexto {

	private String mTexto;

	public ITexto() {
		mTexto = "";
	}

	public void AdicionarLinha(String eLinha) {

		mTexto += eLinha + "\n";

	}

	public void Adicionar(String eMais) {

		mTexto += eMais;

	}

	public String toString() {
		return mTexto;
	}
}
