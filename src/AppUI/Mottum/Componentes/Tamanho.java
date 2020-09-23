package AppUI.Mottum.Componentes;

public class Tamanho extends Componente {

	private int mLargura;
	private int mAltura;

	public static final String ID = "TAMANHO";

	public Tamanho(int eLargura, int eAltura) {
		super(ID);

		this.mLargura = eLargura;
		this.mAltura = eAltura;

	}

	public String toString() {
		return " - " + "Tamanho { Largura = " + mLargura + " , Altura = " + mAltura + " } ";
	}

	// FUNCOES NOVAS

	public void SetTam(int eLargura, int eAltura) {

		this.mLargura = eLargura;
		this.mAltura = eAltura;

	}

	public void aumentarLargura(int eLargura) {
		this.mLargura += eLargura;
	}

	public void aumentarAltura(int eAltura) {
		this.mAltura += eAltura;
	}

	public void diminuirLargura(int eLargura) {
		this.mLargura -= eLargura;
	}

	public void diminuirAltura(int eAltura) {
		this.mAltura -= eAltura;
	}

	// METODOS

	public int getLargura() {
		return mLargura;
	}

	public void setLargura(int eLargura) {
		this.mLargura = eLargura;
	}

	public int getAltura() {
		return mAltura;
	}

	public void setAltura(int eAltura) {
		this.mAltura = eAltura;
	}

}
