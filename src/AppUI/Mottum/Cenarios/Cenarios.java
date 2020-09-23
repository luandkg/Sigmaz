package AppUI.Mottum.Cenarios;

import java.util.ArrayList;

public class Cenarios {

	private ArrayList<Cenario> mCenarios;
	private int mID;

	public Cenarios() {
		mCenarios = new ArrayList<Cenario>();
		mID = 0;
	}

	public int CriarCenario(Cena eCena) {

		Cenario eCenario = new Cenario(mID, eCena);
		mCenarios.add(eCenario);
		mID += 1;

		return eCenario.getID();
	}

	public Cenario getCenario(int eID) {

		for (Cenario mCenario : mCenarios) {
			if (mCenario.getID() == eID) {
				return mCenario;
			}
		}

		return null;
	}

	public void RemoverCenario(int eID) {

		for (Cenario mCenario : mCenarios) {
			if (mCenario.getID() == eID) {
				mCenarios.remove(mCenario);
				break;
			}
		}

	}

}
