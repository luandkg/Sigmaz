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
	
	public TokenTipo Tipo() {return mTipo;}
	public String Conteudo() {return mConteudo;}
	public int Inicio() {return mInicio;}
	public int  Fim() {return mFim;}

	public boolean MesmoConteudo(String eOutroConteudo) { return mConteudo.contentEquals(eOutroConteudo);}
}
