package AppUI.Mottum.Componentes;

public class IntStatus extends Componente {

	private int mConteudo;

	public static final String ID = "IntStatus";

	public static final String StatusNome(String eNome) {
		return ID + "::" + eNome;
	}

	public IntStatus(String eNome, int eStatus) {
		super(StatusNome(eNome));

		this.mConteudo = eStatus;

	}

	public String toString() {
		return " - " + this.getNome() + " { CONTEUDO = " + mConteudo + " } ";
	}

	// FUNCOES NOVAS

	public int getConteudo() {
		return mConteudo;
	}

	public void setConteudo(int eConteudo) {
		this.mConteudo = eConteudo;
	}

	public void Aumentar(int eMais) {
		this.mConteudo += eMais;
	}

	public void Diminuir(int eMenos) {
		this.mConteudo -= eMenos;
	}

}
