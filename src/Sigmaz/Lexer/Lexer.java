package Sigmaz.Lexer;

import Sigmaz.Utils.Erro;
import Sigmaz.Utils.Texto;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

public class Lexer {

	private String mConteudo;
	private int mIndex;
	private int mTamanho;
	private int mLinha;
	private int mPosicao;

	private final String ALFA = "ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz";
	private final String NUM = "0123456789";
	private final String ALFANUM = "ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz0123456789";

	private ArrayList<Token> mTokens;
	private ArrayList<Erro> mErros;

	public Lexer() {
		mConteudo="";
		mIndex=0;
		mTamanho=0;
		mLinha=0;
		mPosicao=0;
		mTokens = new ArrayList<>();
	}
	
	
	public boolean Continuar() {return mIndex<mTamanho;}
	
	public ArrayList<Token> getTokens(){return mTokens;}
	
	public ArrayList<Erro> getErros() {
		return mErros;
	}

	public int getChars(){
		return mConteudo.length();
	}

	private void errar(String eMensagem,int eLinha,int ePosicao){
		mErros.add(new Erro(eMensagem,eLinha,ePosicao));
	}

	public void init(String eArquivo) {

		File arq = new File(eArquivo);


		mConteudo= "";
		mIndex=0;
		mTamanho=0;
		mLinha=1;
		mPosicao=0;
		mTokens.clear();
		
		mErros = new ArrayList<>();

		if(arq.exists()){
			mConteudo= Texto.Ler(eArquivo);
		}else{
			errar("Arquivo nao encontrado : " +eArquivo ,0,0);
		}

		mTamanho=mConteudo.length();
		
		while(Continuar()) {
			String charC =String.valueOf( mConteudo.charAt(mIndex));
			String charP ="";

			if(mIndex+1<mTamanho){
				charP=String.valueOf( mConteudo.charAt(mIndex+1));
			}



			if (ALFA.contains(charC)) {
				
				int eInicio =mPosicao;
				String eTokenConteudo = ObterID();
				int eFim=mPosicao;
				
				mTokens.add(new Token(TokenTipo.ID,eTokenConteudo,eInicio,eFim,mLinha));
			} else 	if (NUM.contains(charC)) {
				
				int eInicio =mPosicao;
				String eTokenConteudo = ObterNUM();
				int eFim=mPosicao;
				
				mTokens.add(new Token(TokenTipo.NUMERO,eTokenConteudo,eInicio,eFim,mLinha));
			} else 	if (charC.contentEquals("+") && charP.contentEquals("+")) {
				int eInicio =mPosicao;
				int eFim=mPosicao;

				mIndex+=1;

				mTokens.add(new Token(TokenTipo.SOMADOR,"++",eInicio,eFim,mLinha) );
			} else 	if (charC.contentEquals("+")) {
				int eInicio =mPosicao;
				mIndex+=1;
				String eTokenConteudo ="+"+ ObterNUM();
				int eFim=mPosicao;
				
				mTokens.add(new Token(TokenTipo.NUMERO,eTokenConteudo,eInicio,eFim,mLinha));
			} else 	if (charC.contentEquals("-") && charP.contentEquals(">")) {
				int eInicio =mPosicao;
				int eFim=mPosicao;

				mIndex+=1;

				mTokens.add(new Token(TokenTipo.SETA,"->",eInicio,eFim,mLinha) );
			} else 	if (charC.contentEquals("-") && charP.contentEquals("-")) {
				int eInicio =mPosicao;
				int eFim=mPosicao;

				mIndex+=1;

				mTokens.add(new Token(TokenTipo.DIMINUIDOR,"++",eInicio,eFim,mLinha) );
			} else 	if (charC.contentEquals("-")) {
				int eInicio =mPosicao;
				mIndex+=1;

				String eTokenConteudo ="-"+ ObterNUM();
				int eFim=mPosicao;
				
				mTokens.add(new Token(TokenTipo.NUMERO,eTokenConteudo,eInicio,eFim,mLinha));
			} else 	if (charC.contentEquals("*") && charP.contentEquals("*")) {
				int eInicio =mPosicao;
				int eFim=mPosicao;

				mIndex+=1;

				mTokens.add(new Token(TokenTipo.MULTIPLICADOR,"++",eInicio,eFim,mLinha) );
			} else 	if (charC.contentEquals("/") && charP.contentEquals("/")) {
				int eInicio =mPosicao;
				int eFim=mPosicao;

				mIndex+=1;

				mTokens.add(new Token(TokenTipo.DIVISOR,"++",eInicio,eFim,mLinha) );
			} else 	if (charC.contentEquals(".")) {
				int eInicio =mPosicao;

				String eTokenConteudo =".";
				int eFim=mPosicao;

				mTokens.add(new Token(TokenTipo.PONTO,eTokenConteudo,eInicio,eFim,mLinha));

			} else 	if (charC.contentEquals(":") && charP.contentEquals(":")) {
				int eInicio =mPosicao;
				int eFim=mPosicao;

				mIndex+=1;

				mTokens.add(new Token(TokenTipo.QUAD,"::",eInicio,eFim,mLinha) );

			} else 	if (charC.contentEquals(":")) {


				int eInicio =mPosicao;
				int eFim=mPosicao;

				mTokens.add(new Token(TokenTipo.DOISPONTOS,":",eInicio,eFim,mLinha));
			} else 	if (charC.contentEquals(",")) {


				int eInicio =mPosicao;
				int eFim=mPosicao;

				mTokens.add(new Token(TokenTipo.VIRGULA,",",eInicio,eFim,mLinha));
			} else 	if (charC.contentEquals("=") && charP.contentEquals("=")) {
				int eInicio =mPosicao;
				int eFim=mPosicao;

				mIndex+=1;

				mTokens.add(new Token(TokenTipo.COMPARADOR_IGUALDADE,"==",eInicio,eFim,mLinha) );
			} else 	if (charC.contentEquals("!") && charP.contentEquals("!")) {
				int eInicio =mPosicao;
				int eFim=mPosicao;

				mIndex+=1;

				mTokens.add(new Token(TokenTipo.COMPARADOR_DIFERENTE,"!!",eInicio,eFim,mLinha) );
			} else 	if (charC.contentEquals("!")) {


				int eInicio =mPosicao;
				int eFim=mPosicao;

				mTokens.add(new Token(TokenTipo.NEGADOR,"!",eInicio,eFim,mLinha));

			} else 	if (charC.contentEquals("=")) {


				int eInicio =mPosicao;
				int eFim=mPosicao;

				mTokens.add(new Token(TokenTipo.IGUAL,"=",eInicio,eFim,mLinha));

			} else 	if (charC.contentEquals("<") && charP.contentEquals("<")) {
				int eInicio =mPosicao;
				int eFim=mPosicao;

				mIndex+=1;

				mTokens.add(new Token(TokenTipo.RECEBER,"<<",eInicio,eFim,mLinha) );
			} else 	if (charC.contentEquals("<")) {


				int eInicio =mPosicao;
				int eFim=mPosicao;

				mTokens.add(new Token(TokenTipo.MENOR,"=",eInicio,eFim,mLinha));
			} else 	if (charC.contentEquals(">") && charP.contentEquals(">")) {
				int eInicio =mPosicao;
				int eFim=mPosicao;

				mIndex+=1;

				mTokens.add(new Token(TokenTipo.ENVIAR,">>",eInicio,eFim,mLinha) );
			} else 	if (charC.contentEquals(">")) {


				int eInicio =mPosicao;
				int eFim=mPosicao;

				mTokens.add(new Token(TokenTipo.MAIOR,"=",eInicio,eFim,mLinha));


			} else 	if (charC.contentEquals("#")) {
				int eInicio =mPosicao;
				String eTokenConteudo = ObterComentario();
				int eFim=mPosicao;
				
				mTokens.add(new Token(TokenTipo.COMENTARIO,eTokenConteudo,eInicio,eFim,mLinha));
				
			} else 	if (charC.contentEquals("\"")) {
				int eInicio =mPosicao;
				String eTokenConteudo = ObterTexto("\"");
				int eFim=mPosicao;
				
				mTokens.add(new Token(TokenTipo.TEXTO,eTokenConteudo,eInicio,eFim,mLinha));
			} else 	if (charC.contentEquals("'")) {
				int eInicio =mPosicao;
				String eTokenConteudo = ObterTexto("'");
				int eFim=mPosicao;
				
				mTokens.add(new Token(TokenTipo.TEXTO,eTokenConteudo,eInicio,eFim,mLinha));
			
			} else 	if (charC.contentEquals("{")) {
				int eInicio =mPosicao;
				int eFim=mPosicao;

				mTokens.add(new Token(TokenTipo.CHAVE_ABRE,"{",eInicio,eFim,mLinha) );
			} else 	if (charC.contentEquals("}")) {
				int eInicio =mPosicao;
				int eFim=mPosicao;

				mTokens.add(new Token(TokenTipo.CHAVE_FECHA,"}",eInicio,eFim,mLinha) );
			} else 	if (charC.contentEquals(";")) {
				int eInicio =mPosicao;
				int eFim=mPosicao;

				mTokens.add(new Token(TokenTipo.PONTOVIRGULA,";",eInicio,eFim,mLinha) );
			} else 	if (charC.contentEquals("(")) {
				int eInicio =mPosicao;
				int eFim=mPosicao;

				mTokens.add(new Token(TokenTipo.PARENTESES_ABRE,"(",eInicio,eFim,mLinha) );
			} else 	if (charC.contentEquals(")")) {
				int eInicio =mPosicao;
				int eFim=mPosicao;

				mTokens.add(new Token(TokenTipo.PARENTESES_FECHA,")",eInicio,eFim,mLinha) );
			} else 	if (charC.contentEquals("@")) {
			
				int eInicio =mPosicao;
				mIndex+=1;
				String eTokenConteudo = ObterID();
				int eFim=mPosicao;
				
				mTokens.add(new Token(TokenTipo.ARROBA,eTokenConteudo,eInicio,eFim,mLinha) );


			} else 	if (charC.contentEquals("\n")) {
				mLinha+=1;
				mPosicao=0;
			} else 	if (charC.contentEquals("\t")) {
			} else 	if (charC.contentEquals(" ")) {

			} else {

				errar("Lexema Desconhecido : " + charC,mLinha,mPosicao);

			}
			
			mIndex+=1;
			mPosicao+=1;
		}
		
		
	
		
	}
	
	public String ObterID() {
		String ret = String.valueOf( mConteudo.charAt(mIndex));
		mIndex+=1;
		while(Continuar()) {
			String charC =String.valueOf( mConteudo.charAt(mIndex));

			if (ALFANUM.contains(charC)) {
				ret+=charC;
			} else {
				mIndex-=1;
				break;
			}
			mIndex+=1;
		}
		return ret;
	}
	
	public String ObterNUM() {
		String ret = String.valueOf( mConteudo.charAt(mIndex));
		mIndex+=1;

		int eIndex = mIndex;

		boolean pontuar = false;
		
		while(Continuar()) {
			String charC =String.valueOf( mConteudo.charAt(mIndex));

			if (NUM.contains(charC)) {
				ret+=charC;
			} else if(charC.contentEquals(".")) {
				pontuar=true;
				mIndex+=1;
				break;
			} else {
				mIndex-=1;
				break;
			}
			mIndex+=1;
		}
		
		
		if (pontuar) {
			boolean pontuou = false;
			ret +=".";
			
			while(Continuar()) {
				String charC =String.valueOf( mConteudo.charAt(mIndex));

				if (NUM.contains(charC)) {
					ret+=charC;
					pontuou=true;
				} else {
					mIndex-=1;

					break;
				}
				mIndex+=1;
			}
			
			if (!pontuou) {
				errar("Numerop invalido !",mLinha,mPosicao);
			}
		}
		
		
		
		return ret;
	}
	
	public String ObterComentario() {
		String ret = String.valueOf( mConteudo.charAt(mIndex));
		mIndex+=1;
		while(Continuar()) {
			String charC =String.valueOf( mConteudo.charAt(mIndex));

			if (charC.contentEquals("\n")) {
				mLinha+=1;
				mPosicao=0;
				break;
			} else {
				ret+=charC;
			}
			mIndex+=1;
		}
		return ret;
	}
	
	public String ObterTexto(String eFinalizador) {
		String ret ="";
		mIndex+=1;
		int eIndex=mIndex;

		boolean finalizado = false;

		while(Continuar()) {
			String charC =String.valueOf( mConteudo.charAt(mIndex));

			if (charC.contentEquals(eFinalizador)) {
				finalizado=true;
				break;
			} else {
				ret+=charC;
			}
			mIndex+=1;
		}

		if(finalizado==false){
			errar("Texto nao finalizado !",mLinha,mPosicao);

		}

		return ret;
	}

	public String getData() {

		Calendar c = Calendar.getInstance();

		int dia = c.get(Calendar.DAY_OF_MONTH);
		int mes = c.get(Calendar.MONTH) + 1;
		int ano = c.get(Calendar.YEAR);

		int hora = c.get(Calendar.HOUR);
		int minutos = c.get(Calendar.MINUTE);
		int segundos = c.get(Calendar.SECOND);

		return dia + "/" + mes + "/" + ano + " " + hora + ":" + minutos + ":" + segundos;

	}
	
}
