package Mottum.Organizacao;


import Mottum.Componentes.Entidade;

import java.util.ArrayList;

public class Grupo {

	private String mNome;
	private ArrayList<Entidade> mEntidades;

	public Grupo(String eNome) {

		mNome = eNome;

		mEntidades = new ArrayList<Entidade>();

	}

	public String getNome() {
		return mNome;
	}

	public ArrayList<Entidade> getEntidades() {
		return mEntidades;
	}

	public void AdicionarEntidade(Entidade eEntidade) {
		mEntidades.add(eEntidade);
	}

}
