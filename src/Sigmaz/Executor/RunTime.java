package Sigmaz.Executor;

import Sigmaz.Compilador.AST;

import java.util.ArrayList;

public class RunTime {

	private ArrayList<AST> mASTS;
	private ArrayList<String> mErros;

	public RunTime() {

		mASTS = new ArrayList<>();

		mErros = new ArrayList<>();

	}

	public ArrayList<String> getErros() {
		return mErros;
	}

	public void init(ArrayList<AST> eASTs) {
		mASTS = eASTs;

		mErros.clear();

		Escopo OlimpusInternal = new Escopo(this,null);
		AST mOLIMPUS = null;

		for (AST ASTC : mASTS) {

			 if (ASTC.mesmoTipo("OLIMPUS")) {
				 mOLIMPUS = ASTC;
			 }
		}

		if (mOLIMPUS != null) {

			for (AST ASTC : mOLIMPUS.getBranch("LIBS").getASTS()) {



			}


			for (AST ASTC : mOLIMPUS.getBranch("INIT").getASTS()) {
				if (OlimpusInternal.run(ASTC) == false) {
					break;
				}

				if (mErros.size() > 0) {
					break;
				}

			}
		} else {

			mErros.add("Olimpus Vazio !");
		}

	}

}
