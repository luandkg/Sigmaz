package AppUI.Mottum.Core;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Imagens {

	private ArrayList<Imagem> mImagens;

	public Imagens() {

		mImagens = new ArrayList<Imagem>();

	}

	public void adicionar(String eNome, BufferedImage eImagem) {

		mImagens.add(new Imagem(eNome, eImagem));

	}

	public void adicionarUnico(String eNome, BufferedImage eImagem) {

		if (!existe(eNome)) {

			mImagens.add(new Imagem(eNome, eImagem));

		}

	}

	public BufferedImage obter(String eNome) {
		BufferedImage ret = null;

		for (Imagem II : mImagens) {
			if (II.getNome().contentEquals(eNome)) {

				ret = II.getImagem();
				break;

			}
		}
		return ret;
	}

	public boolean existe(String eNome) {
		boolean ret = false;

		for (Imagem II : mImagens) {
			if (II.getNome().contentEquals(eNome)) {
				ret = true;
				break;

			}
		}
		return ret;
	}

}
