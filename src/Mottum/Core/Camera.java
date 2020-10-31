package Mottum.Core;

public class Camera {

	private int mX;
	private int mY;

	private int mLargura;
	private int mAltura;

	private int TX;
	private int TY;

	private int mTaxa;

	private int ESQUERDA;
	private int DIREITA;
	private int ACIMA;
	private int ABAIXO;

	public Camera(int eX, int eY, int eLargura, int eAltura, int eTaxa) {
		this.mX = eX;
		this.mY = eY;

		this.mLargura = eLargura;
		this.mAltura = eAltura;

		mTaxa = eTaxa;

		TX = mLargura / eTaxa;
		TY = mAltura / eTaxa;

		ESQUERDA = TX / 5;
		DIREITA = (TX / 5) * 4;

		ACIMA = TY / 5;
		ABAIXO = (TY / 5) * 4;

	}

	public int getX() {
		return mX;
	}

	public int getY() {
		return mY;
	}

	public void Enquadrar(int PX, int PY) {

		int QX = PX / mTaxa;
		int QY = PY / mTaxa;

		if (QX < ESQUERDA) {

			mX = (QX - 5) * mTaxa;

		} else if (QX > DIREITA) {

			mX = (QX - DIREITA) * mTaxa;

		}

		if (QY < ACIMA) {

			mY = (QY - 5) * mTaxa;

		} else if (QY > ABAIXO) {

			mY = (QY - ABAIXO) * mTaxa;

		}

	}

}
