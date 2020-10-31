package Mottum.Componentes;

public class Posicao extends Componente {

	private int mX;
	private int mY;

	public static final String ID = "POSICAO";

	public Posicao(int eX, int eY) {
		super(ID);

		this.mX = eX;
		this.mY = eY;

	}

	public String toString() {
		return " - " + "Posicao { X = " + mX + " , Y = " + mY + " } ";
	}

	// FUNCOES NOVAS

	public void setPos(int eX, int eY) {

		this.mX = eX;
		this.mY = eY;

	}

	public void aumentarX(int eX) {
		this.mX += eX;
	}

	public void aumentarY(int eY) {
		this.mY += eY;
	}

	public void diminuirX(int eX) {
		this.mX -= eX;
	}

	public void diminuirY(int eY) {
		this.mY -= eY;
	}

	// METODOS

	public int getX() {
		return mX;
	}

	public void setX(int eX) {
		this.mX = eX;
	}

	public int getY() {
		return mY;
	}

	public void setY(int eY) {
		this.mY = eY;
	}

}
