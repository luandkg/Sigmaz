package AppUI.Mottum.Utils;

public class Cronometro {

	private long mTempo_inicio;
	private long mEsperar;

	private boolean mEsperado;

	public Cronometro(long eEsperar) {

		mTempo_inicio = System.nanoTime();
		mEsperar = eEsperar * 1000000;
		mEsperado = false;
	}

	public void Esperar() {

		mEsperado = false;

		if ((System.nanoTime() - mTempo_inicio) >= mEsperar) {
			mTempo_inicio = System.nanoTime();
			mEsperado = true;
		}

	}

	public boolean Esperado() {
		return mEsperado;
	}

	public void Zerar() {
		mTempo_inicio = System.nanoTime();
		mEsperado = false;
	}
}
