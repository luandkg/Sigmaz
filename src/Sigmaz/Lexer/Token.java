package Sigmaz.Lexer;

public class Token {

	private String mConteudo;
	private int mInicio;
	private int mFim;
	private int mLinha;

	private TokenTipo mTipo= TokenTipo.DESCONHECIDO;
	
	public Token(TokenTipo eTipo,String eConteudo,int eInicio,int eFim,int eLinha) {
		mTipo=eTipo;
		mConteudo=eConteudo;
		mInicio=eInicio;
		mFim=eFim;
		mLinha=eLinha;
	}
	
	public TokenTipo getTipo() {return mTipo;}
	public String getConteudo() {return mConteudo;}

	public int getInicio() {return mInicio;}
	public int  getFim() {return mFim;}
	public int getPosicao() {return mInicio;}
	public int getLinha() {return mLinha;}

	public boolean mesmoConteudo(String eOutroConteudo) { return mConteudo.contentEquals(eOutroConteudo);}
}
