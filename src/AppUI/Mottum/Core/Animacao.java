package AppUI.Mottum.Core;

import AppUI.Mottum.Utils.Cronometro;

import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class Animacao {

	private Cronometro mCron;
	private int mIndex;
	private ArrayList<BufferedImage> mSprites;

	public Animacao(int eTempo) {

		mCron = new Cronometro(eTempo);
		mIndex = 0;
		mSprites = new ArrayList<BufferedImage>();

	}

	public void addSprite(BufferedImage eSprite) {
		mSprites.add(eSprite);
	}

	public BufferedImage getSpriteCorrente() {
		return mSprites.get(mIndex);
	}

	public void update(double dt) {

		mCron.Esperar();

		if (mCron.Esperado()) {

			mIndex += 1;

			if (mIndex > mSprites.size() - 1) {
				mIndex = 0;
			}

		}
	}
}
