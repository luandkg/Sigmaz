package Mottum.Core;


import Mottum.Estrutural.Rect;
import Mottum.Estrutural.Vetor2;

import java.util.ArrayList;

public class Contato {

	private int mUnidade;
	
	public Contato(int eUnidade) {
		
		mUnidade=eUnidade;
		
	}
	
	public ArrayList<Vetor2> OcupandoCom(Rect mAreaDoCampo, Vetor2 SomarLocal) {
		ArrayList<Vetor2> LS_1 = Ocupando(mAreaDoCampo);

		for (Vetor2 V1 : LS_1) {
			V1.setX(V1.getX() + SomarLocal.getX());
			V1.setY(V1.getY() + SomarLocal.getY());

		}

		return LS_1;
	}

	public ArrayList<Vetor2> Ocupando(Rect mAreaDoCampo) {
		ArrayList<Vetor2> ret = new ArrayList<Vetor2>();

		int PX = ((mAreaDoCampo.getX() + mAreaDoCampo.getLargura()));
		while (PX > ((mAreaDoCampo.getX()))) {
			PX -= mUnidade;

			int PY = (mAreaDoCampo.getY() + mAreaDoCampo.getAltura());

			while (PY > ((mAreaDoCampo.getY()))) {
				PY -= mUnidade;

				ret.add(new Vetor2(PX, PY));

			}

		}

		return ret;
	}

	public ArrayList<Rect> OcupandoAreas(Rect mAreaDoCampo) {
		ArrayList<Rect> ret = new ArrayList<Rect>();

		int PX = ((mAreaDoCampo.getX() + mAreaDoCampo.getLargura()));
		while (PX > ((mAreaDoCampo.getX()))) {
			PX -= mUnidade;

			int PY = (mAreaDoCampo.getY() + mAreaDoCampo.getAltura());

			while (PY > ((mAreaDoCampo.getY()))) {
				PY -= mUnidade;
				ret.add(new Rect(PX, PY,mUnidade, mUnidade));

			}

		}

		return ret;
	}

	public ArrayList<Vetor2> OcupandoDaArea(Rect eArea) {
		ArrayList<Vetor2> ret = new ArrayList<Vetor2>();

		int PX = ((eArea.getX() + eArea.getLargura()));
		while (PX > ((eArea.getX()))) {
			PX -= mUnidade;

			int PY = (eArea.getY() + eArea.getAltura());

			while (PY > ((eArea.getY()))) {
				PY -= mUnidade;

				ret.add(new Vetor2(PX, PY));

			}

		}

		return ret;
	}

	public boolean ColideArea(Rect mAreaDoCampo, Rect eArea) {
		ArrayList<Vetor2> ret = OcupandoDaArea(eArea);
		return ColideGrupo(mAreaDoCampo, ret);
	}

	public boolean Colide(Rect mAreaDoCampo, Vetor2 eV) {
		boolean ret = false;

		for (Vetor2 v : OcupandoDaArea(mAreaDoCampo)) {

			if (v.Igual(eV)) {
				ret = true;
				break;
			}

		}

		return ret;
	}

	public boolean ColideGrupo(Rect mAreaDoCampo, ArrayList<Vetor2> LS_2) {
		boolean ret = false;

		ArrayList<Vetor2> LS_1 = this.Ocupando(mAreaDoCampo);

		for (Vetor2 V1 : LS_1) {

			for (Vetor2 V2 : LS_2) {

				if (V1.Igual(V2)) {
					ret = true;
					break;
				}

			}

			if (ret == true) {
				break;
			}
		}

		return ret;
	}

}
