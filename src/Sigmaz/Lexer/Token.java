package Sigmaz.Lexer;

public class Token {

	private String mConteudo;
	private int mInicio;
	private int mFim;
	private TokenTipo mTipo= TokenTipo.DESCONHECIDO;
	
	public Token(TokenTipo eTipo,String eConteudo,int eInicio,int eFim) {
		mTipo=eTipo;
		mConteudo=eConteudo;
		mInicio=eInicio;
		mFim=eFim;
	}
	
	public TokenTipo getTipo() {return mTipo;}
	public String getConteudo() {return mConteudo;}
	public int getInicio() {return mInicio;}
	public int  getFim() {return mFim;}

	public boolean mesmoConteudo(String eOutroConteudo) { return mConteudo.contentEquals(eOutroConteudo);}
}
