package Sigmaz.Utils;

public class Documento {

	
	private String mConteudo;
	
	public Documento() {
		mConteudo="";
	}
	
	
	public void adicionar(String eConteudo) {
		mConteudo+=eConteudo;
	}
	
	
	public void adicionarLinha(String eConteudo) {
		if(mConteudo.length()==0) {
			mConteudo+= eConteudo;
		}else {
			mConteudo+="\n" + eConteudo;
		}
	}
	
	
	public String getConteudo() {
		return mConteudo;
	}
	
	
	public void limpar() {
		mConteudo="";
	}
}
