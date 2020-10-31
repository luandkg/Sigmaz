package Mottum.Cenarios;

public class Cenario {

	private int mID;
	private Cena mCena;

	public Cenario(int eID, Cena eCena) {
		this.mID = eID;
		this.mCena = eCena;
	}

	public int getID() {
		return mID;
	}

	public Cena getCena() {
		return mCena;
	}

}
