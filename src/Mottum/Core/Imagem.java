package Mottum.Core;

import java.awt.image.BufferedImage;

public class Imagem {

	private String mNome;
	private BufferedImage mImagem;

	public Imagem(String eNome, BufferedImage eImagem) {

		mNome = eNome;
		mImagem = eImagem;

	}

	public String getNome() {
		return mNome;
	}

	public BufferedImage getImagem() {
		return mImagem;
	}

}
