package AppUI.Mottum.Componentes;

public class Movimento extends Componente {

	private int mVelX;
	private int mVelY;

	public static final String ID = "MOVIMENTO";

	public Movimento(int eVelX, int eVelY) {
		super(ID);

		this.mVelX = eVelX;
		this.mVelY = eVelY;

	}

	public String toString() {
		return " - " + "Movimento { X = " + mVelX + " , Y = " + mVelY + " } ";
	}

	// FUNCOES NOVAS

	public void setMov(int eVelX, int eVelY) {
		this.mVelX = eVelX;
		this.mVelY = eVelY;
	}

	public void SoMovimentoHorizontal(int eVelX) {
		this.mVelX = eVelX;
		this.mVelY = 0;
	}

	public void SoMovimentoVertical(int eVelY) {
		this.mVelX = 0;
		this.mVelY = eVelY;
	}

	public void aumentarVelX(int eVelX) {
		this.mVelX += eVelX;
	}

	public void aumentarVelY(int eVelY) {
		this.mVelY += eVelY;
	}

	public void diminuirVelX(int eVelX) {
		this.mVelX -= eVelX;
	}

	public void diminuirVelY(int eVelY) {
		this.mVelY -= eVelY;
	}

	// METODOS

	public int getVelX() {
		return mVelX;
	}

	public void setVelX(int eX) {
		this.mVelX = eX;
	}

	public int getVelY() {
		return mVelY;
	}

	public void setVelY(int eY) {
		this.mVelY = eY;
	}

}
