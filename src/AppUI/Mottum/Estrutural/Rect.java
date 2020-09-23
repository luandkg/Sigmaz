package AppUI.Mottum.Estrutural;

public class Rect {

	private int mX;
	private int mY;
	private int mLargura;
	private int mAltura;

	public Rect(int eX, int eY, int eLargura, int eAltura) {
		this.mX = eX;
		this.mY = eY;
		this.mLargura = eLargura;
		this.mAltura = eAltura;

	}

	public int getX() {
		return mX;
	}

	public int getY() {
		return mY;
	}

	public void setPosicao(int eX,int eY) {
		mX=eX;
		mY=eY;
	}

	
	public int getLargura() {
		return mLargura;
	}

	public int getAltura() {
		return mAltura;
	}

	public void setTamanho(int eLargura,int eAltura) {
		mLargura=eLargura;
		mAltura=eAltura;
	}
	
	public int getX2() {
		return mX + mLargura;
	}

	public int getY2() {
		return mY + mAltura;
	}

	public void setX(int eX) {
		mX = eX;
	}

	public void setY(int eY) {
		mY = eY;
	}

}
