package AppUI.Mottum.Estrutural;

public enum Localizacao {

	NENHUMA(-1), IGUAL(0), ESQUERDA(1), DIREITA(2), ACIMA(3), ABAIXO(4), ACIMA_ESQUERDA(5), ACIMA_DIREITA(6),
	ABAIXO_ESQUERDA(7), ABAIXO_DIREITA(8);

	private int mValor;

	Localizacao(int eValor) {
		mValor = eValor;
	}

	public int getValor() {
		return mValor;
	}

	public String toString() {
		String ret = "NENHUMA";

		switch (mValor) {
		case 0:
			ret = "IGUAL";
			break;
		case 1:
			ret = "ESQUERDA";
			break;
		case 2:
			ret = "DIREITA";
			break;
		case 3:
			ret = "ACIMA";
			break;
		case 4:
			ret = "ABAIXO";
			break;
		case 5:
			ret = "ACIMA_ESQUERDA";
			break;
		case 6:
			ret = "ACIMA_DIREITA";
			break;
		case 7:
			ret = "ABAIXO_ESQUERDA";
			break;
		case 8:
			ret = "ABAIXO_DIREITA";
			break;
		}

		return ret;
	}
}
