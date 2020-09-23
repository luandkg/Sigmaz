package AppUI.Mottum.Componentes;

import java.util.ArrayList;

public class Acumulador extends Componente {

	private ArrayList<String> mConteudo;

	public static final String ID = "Acumulador";

	public static final String StatusNome(String eNome) {
		return ID + "::" + eNome;
	}

	public Acumulador(String eNome) {
		super(StatusNome(eNome));

		this.mConteudo = new ArrayList<String>();

	}

	public String toString() {
		return " - " + this.getNome() + " { Itens = " + mConteudo.size() + " } ";
	}

	// FUNCOES NOVAS

	public int getTamaho() {
		return mConteudo.size();
	}

	public void adiconarItem(String eConteudo) {
		this.mConteudo.add(eConteudo);
	}

	public boolean existeItem(String eConteudo) {
		return this.mConteudo.contains(eConteudo);
	}

	public String obterItem(int index) {
		return this.mConteudo.get(index);
	}

	public void removeItem(String eConteudo) {
		this.mConteudo.remove(eConteudo);
	}

	public void removeItemIndex(int index) {
		this.mConteudo.remove(index);
	}

	public void Limoar() {
		this.mConteudo.clear();
	}

}
