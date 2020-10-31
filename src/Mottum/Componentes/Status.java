package Mottum.Componentes;

public class Status extends Componente {

	private String mConteudo;

	public static final String ID = "Status";

	public static final String StatusNome(String eNome) {
		return ID + "::" + eNome;
	}

	public Status(String eNome, String eStatus) {
		super(StatusNome(eNome));

		this.mConteudo = eStatus;

	}

	public String toString() {
		return " - " + this.getNome() + " { CONTEUDO = " + mConteudo + " } ";
	}

	// FUNCOES NOVAS

	public String getConteudo() {
		return mConteudo;
	}

	public void setConteudo(String eConteudo) {
		this.mConteudo = eConteudo;
	}

}
