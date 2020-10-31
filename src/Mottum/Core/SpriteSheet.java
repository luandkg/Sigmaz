package Mottum.Core;

import java.awt.image.BufferedImage;

public class SpriteSheet {

	private BufferedImage mImagem;
	private int mTam;
	private int mAlt;

	public SpriteSheet(BufferedImage eImagem, int eTam, int eAlt) {
		mImagem = eImagem;
		mTam = eTam;
		mAlt = eAlt;
	}

	public BufferedImage getSprite(int eX, int eY) {

		return mImagem.getSubimage(eX * (mTam), eY * (mAlt), mTam, mAlt);

	}
}
