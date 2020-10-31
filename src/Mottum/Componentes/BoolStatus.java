package Mottum.Componentes;

public class BoolStatus extends Componente {

	private boolean mConteudo;

	public static final String ID = "BoolStatus";

	public static final String StatusNome(String eNome) {
		return ID + "::" + eNome;
	}

	public BoolStatus(String eNome, boolean eStatus) {
		super(StatusNome(eNome));

		this.mConteudo = eStatus;

	}

	public String toString() {
		return " - " + this.getNome() + " { CONTEUDO = " + mConteudo + " } ";
	}

	// FUNCOES NOVAS

	public boolean getConteudo() {
		return mConteudo;
	}

	public void setConteudo(boolean eConteudo) {
		this.mConteudo = eConteudo;
	}

	public void Trocar() {
		this.mConteudo = !this.mConteudo;
	}

}
