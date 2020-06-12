package Sigmaz.Analisador;

import Sigmaz.Compilador.AST;

import java.util.ArrayList;

public class Analisador {

	private ArrayList<AST> mASTS;

	private ArrayList<String> mErros;

	public Analisador() {

		mASTS = new ArrayList<>();


		mErros = new ArrayList<>();

	}

	public ArrayList<String> getErros() {
		return mErros;
	}

	public void init(ArrayList<AST> eASTs) {
		mASTS = eASTs;



		mErros.clear();





	}


}
