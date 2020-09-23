package AppUI.Mottum.Componentes;

public class Corpo extends Componente {

	private Posicao mPosicao;
	private Tamanho mTamanho;
	private Movimento mMovimento;

	public static final String ID = "Corpo";

	public Corpo(int eX, int eY, int eLargura, int eAltura, int eVelX, int eVelY) {
		super(ID);

		mPosicao = new Posicao(eX, eY);
		mTamanho = new Tamanho(eLargura, eAltura);
		mMovimento = new Movimento(eVelX, eVelY);

	}

	public Posicao getPosicao() {
		return mPosicao;
	}

	public Tamanho getTamanho() {
		return mTamanho;
	}

	public Movimento getMovimento() {
		return mMovimento;
	}

	public String toString() {
		return PosicaoToString() + "\n" + MovimentoToString() + "\n" + TamanhoToString();
	}

	public String PosicaoToString() {
		return mPosicao.toString();
	}

	public String MovimentoToString() {
		return mMovimento.toString();
	}

	public String TamanhoToString() {
		return mTamanho.toString();

	}

	// FUNCOES NOVAS

	public void setPos(int eX, int eY) {
		mPosicao.setPos(eX, eY);

	}

	public void aumentarX(int eX) {
		mPosicao.aumentarX(eX);
	}

	public void aumentarY(int eY) {
		mPosicao.aumentarY(eY);
	}

	public void diminuirX(int eX) {
		mPosicao.diminuirX(eX);
	}

	public void diminuirY(int eY) {
		mPosicao.diminuirY(eY);
	}

	// METODOS

	public int getX() {
		return mPosicao.getX();
	}

	public void setX(int eX) {
		mPosicao.setX(eX);
	}

	public int getY() {
		return mPosicao.getY();
	}

	public void setY(int eY) {
		mPosicao.setY(eY);
	}

	// FUNCOES NOVAS

	public void setMov(int eVelX, int eVelY) {
		mMovimento.setMov(eVelX, eVelY);
	}

	public void SoMovimentoHorizontal(int eVelX) {
		mMovimento.SoMovimentoHorizontal(eVelX);
	}

	public void SoMovimentoVertical(int eVelY) {
		mMovimento.SoMovimentoVertical(eVelY);
	}

	public void aumentarVelX(int eVelX) {
		mMovimento.aumentarVelX(eVelX);
	}

	public void aumentarVelY(int eVelY) {
		mMovimento.aumentarVelY(eVelY);
	}

	public void diminuirVelX(int eVelX) {
		mMovimento.diminuirVelX(eVelX);
	}

	public void diminuirVelY(int eVelY) {
		mMovimento.diminuirVelY(eVelY);
	}

	// METODOS

	public int getVelX() {
		return mMovimento.getVelX();
	}

	public void setVelX(int eX) {
		mMovimento.setVelX(eX);
	}

	public int getVelY() {
		return mMovimento.getVelY();
	}

	public void setVelY(int eY) {
		mMovimento.setVelY(eY);
	}

	// FUNCOES NOVAS

	public void SetTam(int eLargura, int eAltura) {

		mTamanho.SetTam(eLargura, eAltura);
		// this.mLargura=eLargura;
		// this.mAltura=eAltura;

	}

	public void aumentarLargura(int eLargura) {
		// this.mLargura +=eLargura;
		mTamanho.aumentarLargura(eLargura);
	}

	public void aumentarAltura(int eAltura) {
		// this.mAltura +=eAltura;
		mTamanho.aumentarAltura(eAltura);
	}

	public void diminuirLargura(int eLargura) {
		// this.mLargura -=eLargura;
		mTamanho.diminuirLargura(eLargura);
	}

	public void diminuirAltura(int eAltura) {
		// this.mAltura -=eAltura;
		mTamanho.diminuirAltura(eAltura);
	}

	// METODOS

	public int getLargura() {
		// return mLargura;
		return mTamanho.getLargura();
	}

	public void setLargura(int eLargura) {
		// this.mLargura = eLargura;
		mTamanho.setLargura(eLargura);
	}

	public int getAltura() {
		// return mAltura;
		return mTamanho.getAltura();
	}

	public void setAltura(int eAltura) {
		// this.mAltura = eAltura;
		mTamanho.setAltura(eAltura);
	}

}
