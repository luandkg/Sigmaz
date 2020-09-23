package AppUI.Mottum.Componentes;

public class FloatStatus extends Componente {

	private double mConteudo;

	public static final String ID = "FloatStatus";

	public static final String StatusNome(String eNome) {
		return ID + "::" + eNome;
	}

	public FloatStatus(String eNome, double eStatus) {
		super(StatusNome(eNome));

		this.mConteudo = eStatus;

	}

	public String toString() {
		return " - " + this.getNome() + " { CONTEUDO = " + mConteudo + " } ";
	}

	// FUNCOES NOVAS

	public double getConteudo() {
		return mConteudo;
	}

	public void setConteudo(double eConteudo) {
		this.mConteudo = eConteudo;
	}

	public void Aumentar(double eMais) {
		this.mConteudo += eMais;
	}

	public void Diminuir(double eMenos) {
		this.mConteudo -= eMenos;
	}

}
