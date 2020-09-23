package AppUI.Mottum.Componentes;

import java.awt.Graphics;
import java.util.ArrayList;

public class Entidade {

	private ArrayList<Componente> componentes;
	private int mZindex;
	private String mTipo;
	private String mNome;

	public Entidade() {
		componentes = new ArrayList<Componente>();
		mZindex = 0;
		mTipo = "";
	}

	public void adicionarComponente(Componente c) {
		componentes.add(c);
	}

	public Componente SubstituirComponente(Componente c) {

		Componente ret = null;
		int index = 0;

		for (Componente IC : componentes) {
			if (IC.getNome().equals(c.getNome())) {
				ret = c;
				componentes.set(index, c);
				break;
			}
			index += 1;
		}

		return c;
	}

	public Componente obterComponente(String eNome) {
		for (Componente IC : componentes) {
			if (IC.getNome().equals(eNome)) {
				return IC;
			}
		}

		if (eNome.equals(Posicao.ID)) {
			Corpo CorpoC = (Corpo) this.obterComponente(Corpo.ID);
			return CorpoC.getPosicao();
		}

		if (eNome.equals(Tamanho.ID)) {
			Corpo CorpoC = (Corpo) this.obterComponente(Corpo.ID);
			return CorpoC.getTamanho();
		}

		if (eNome.equals(Movimento.ID)) {
			Corpo CorpoC = (Corpo) this.obterComponente(Corpo.ID);
			return CorpoC.getMovimento();
		}

		return null;
	}

	public Componente adicionarEObterComponente(Componente c) {
		componentes.add(c);
		for (Componente IC : componentes) {
			if (IC.getNome().equals(c.getNome())) {
				return IC;
			}
		}
		return null;
	}

	public boolean existeComponente(String eNome) {
		for (Componente IC : componentes) {
			if (IC.getNome().equals(eNome)) {
				return true;
			}
		}
		return false;
	}

	public String toString() {
		String ret = "";

		for (Componente IC : componentes) {
			ret += IC.toString() + "\n";
		}
		return ret;
	}

	public void update(double delta) {
	}

	public void draw(Graphics grafico) {
	}

	public void setZIndex(int eIndex) {
		mZindex = eIndex;
	}

	public int getZIndex() {
		return mZindex;
	}

	public void setTipo(String eTipo) {
		mTipo = eTipo;
	}

	public String getTipo() {
		return mTipo;
	}

	public String getNome() {
		return mNome;
	}

	public void setNome(String eNome) {
		mNome = eNome;
	}

}
