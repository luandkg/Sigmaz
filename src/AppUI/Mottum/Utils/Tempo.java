package AppUI.Mottum.Utils;

public class Tempo {

	private double mIniciado;

	public Tempo() {
		mIniciado = System.nanoTime();
	}

	public double getAgora() {
		return (System.nanoTime() - mIniciado) * 1E-9;
	}

}
