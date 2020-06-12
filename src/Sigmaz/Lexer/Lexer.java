package Sigmaz.Lexer;

import Sigmaz.Utils.Texto;

import java.io.File;
import java.util.ArrayList;

public class Lexer {

	private String mConteudo;
	private int mIndex;
	private int mTamanho;
	
	private final String ALFA = "ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz";
	private final String NUM = "0123456789";
	private final String ALFANUM = "ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz0123456789";

	private ArrayList<Token> mTokens;
	private ArrayList<String> mErros;

	public Lexer() {
		mConteudo="";
		mIndex=0;
		mTamanho=0;
		mTokens = new ArrayList<>();
	}
	
	
	public boolean Continuar() {return mIndex<mTamanho;}
	
	public ArrayList<Token> getTokens(){return mTokens;}
	
	public ArrayList<String> getErros() {
		return mErros;
	}

	public int getChars(){
		return mConteudo.length();
	}

	public void init(String eArquivo) {

		File arq = new File(eArquivo);


		mConteudo= "";
		mIndex=0;
		mTamanho=0;
		mTokens.clear();
		
		mErros = new ArrayList<>();

		if(arq.exists()){
			mConteudo= Texto.Ler(eArquivo);
		}else{
			mErros.add("Arquivo nao encontrado : " +eArquivo );
		}

		mTamanho=mConteudo.length();
		
		while(Continuar()) {
			String charC =String.valueOf( mConteudo.charAt(mIndex));
			if (ALFA.contains(charC)) {
				
				int eInicio =mIndex;
				String eTokenConteudo = ObterID();
				int eFim=mIndex;
				
				mTokens.add(new Token(TokenTipo.ID,eTokenConteudo,eInicio,eFim));
			} else 	if (NUM.contains(charC)) {
				
				int eInicio =mIndex;
				String eTokenConteudo = ObterNUM();
				int eFim=mIndex;
				
				mTokens.add(new Token(TokenTipo.NUMERO,eTokenConteudo,eInicio,eFim));
			} else 	if (charC.contentEquals("+")) {
				int eInicio =mIndex;
				mIndex+=1;
				String eTokenConteudo ="-"+ ObterNUM();
				int eFim=mIndex;
				
				mTokens.add(new Token(TokenTipo.NUMERO,eTokenConteudo,eInicio,eFim));
			} else 	if (charC.contentEquals("-")) {
				int eInicio =mIndex;
				mIndex+=1;

				String eTokenConteudo ="-"+ ObterNUM();
				int eFim=mIndex;
				
				mTokens.add(new Token(TokenTipo.NUMERO,eTokenConteudo,eInicio,eFim));
			
			} else 	if (charC.contentEquals("#")) {
				int eInicio =mIndex;
				String eTokenConteudo = ObterComentario();
				int eFim=mIndex;
				
				mTokens.add(new Token(TokenTipo.COMENTARIO,eTokenConteudo,eInicio,eFim));
				
			} else 	if (charC.contentEquals("\"")) {
				int eInicio =mIndex;
				String eTokenConteudo = ObterTexto("\"");
				int eFim=mIndex;
				
				mTokens.add(new Token(TokenTipo.TEXTO,eTokenConteudo,eInicio,eFim));
			} else 	if (charC.contentEquals("'")) {
				int eInicio =mIndex;
				String eTokenConteudo = ObterTexto("'");
				int eFim=mIndex;
				
				mTokens.add(new Token(TokenTipo.TEXTO,eTokenConteudo,eInicio,eFim));
			
			} else 	if (charC.contentEquals("{")) {
				int eInicio =mIndex;
				int eFim=mIndex;

				mTokens.add(new Token(TokenTipo.ABRE,"{",eInicio,eFim) );
			} else 	if (charC.contentEquals("}")) {
				int eInicio =mIndex;
				int eFim=mIndex;

				mTokens.add(new Token(TokenTipo.FECHA,"}",eInicio,eFim) );
			} else 	if (charC.contentEquals("@")) {
			
				int eInicio =mIndex;
				mIndex+=1;
				String eTokenConteudo = ObterID();
				int eFim=mIndex;
				
				mTokens.add(new Token(TokenTipo.ARROBA,eTokenConteudo,eInicio,eFim) );

			} else 	if (charC.contentEquals("\n")) {
			} else 	if (charC.contentEquals("\t")) {
			} else 	if (charC.contentEquals(" ")) {

			} else {
				
				mErros.add("Lexema Desconhecido : " + charC);

			}
			
			mIndex+=1;

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
					break;
				}
				mIndex+=1;
			}
			
			if (!pontuou) {
				mErros.add("Numerop invalido !");
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
		while(Continuar()) {
			String charC =String.valueOf( mConteudo.charAt(mIndex));

			if (charC.contentEquals(eFinalizador)) {
				break;
			} else {
				ret+=charC;
			}
			mIndex+=1;
		}
		return ret;
	}
	
	
}
