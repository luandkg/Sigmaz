package OAVersion.LuanDKG;

import java.util.ArrayList;

public class Lista {

	private String mNome;
	private ArrayList<String> mItens;

	public Lista(String eNome) {
		mNome = eNome;
		mItens = new ArrayList<String>();

	}

	public void setNome(String eNome) {
		mNome = eNome;
	}

	public String getNome() {
		return mNome;
	}

	public void AdicionarItem(String eItem) {
		mItens.add(eItem);
	}

	public void UnicoItem(String eItem) {
		if (!ExisteItem(eItem)) {
			mItens.add(eItem);
		}
	}

	public void RemoverItem(String eItem) {
		if (ExisteItem(eItem)) {
			mItens.remove(eItem);
		}
	}

	public boolean ExisteItem(String eItem) {
		return mItens.contains(eItem);
	}

	public ArrayList<String> getItens() {
		return mItens;
	}

}
