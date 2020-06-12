package Sigmaz.Utils;


import Sigmaz.Compilador.AST;

import java.util.ArrayList;

public class Documentador {

	private String documento;
	private int i;
	private int o;

	private final String ALFA = "ABCDEFGHIJKLMNOPQRSTUVWXYZ_";

	public Documentador() {

		documento = "";
		i = 0;
		o = 0;

	}

	public ArrayList<AST> decompilar(String eConteudo) {

		ArrayList<AST> ASTSaida = new ArrayList<AST>();

		documento = eConteudo;

		i = 0;
		o = documento.length();

		while (i < o) {

			String l = String.valueOf(documento.charAt(i));

			if (ALFA.contains(l)) {
				
				String eNome =  obterID();
				
				//System.out.println(" -->> " +eNome);
				
				AST novo = new AST(eNome);
				ASTSaida.add(novo);
				
				interno(novo);
				
			}

			i += 1;
		}

		return ASTSaida;
	}
	
	
	
	public void interno(AST ASTPai) {

		String a = String.valueOf(documento.charAt(i));

		i+=1;
		
		String t1 = obterTexto();
		
		i+=1;
		
		String b = String.valueOf(documento.charAt(i));

		i+=1;
		String t2 = obterTexto();

		i+=1;
		String c = String.valueOf(documento.charAt(i));

		ASTPai.setNome(t1);
		ASTPai.setValor(t2);

		i+=1;
		String d = String.valueOf(documento.charAt(i));
		
		i+=1;
		
		while (i < o) {

			String l = String.valueOf(documento.charAt(i));

			if (ALFA.contains(l)) {
				
				String eNome =  obterID();
				
				//System.out.println(" -->> " +eNome);
				
				AST novo = new AST(eNome);
				ASTPai.getASTS().add(novo);
				
				interno(novo);
				
			}else if  (l.contentEquals("}")) {
				//i+=1;
				break;
			}

			i += 1;
		}
		
		
	}
	
	

	public String obterID() {

		String ret = "";

		while (i < o) {

			String l = String.valueOf(documento.charAt(i));

			if (ALFA.contains(l)) {
				ret += l;
			} else {
				break;
			}

			i += 1;
		}

		return ret;

	}
	
	public String obterTexto() {

		String a = String.valueOf(documento.charAt(i));
i+=1;
		
		String ret = "";

		while (i < o) {

			String l = String.valueOf(documento.charAt(i));

			if (l.contentEquals("\"")) {
				break;
			} else {
				ret += l;
			}

			i += 1;
		}

		return ret;

	}
	
	public String ImprimirArvoreDeInstrucoes_De(ArrayList<AST> lsAST) {

		Documento DocumentoC = new Documento();

		DocumentoC.adicionarLinha("");

		for (AST a : lsAST) {

			if (a.getValor().length() > 0) {
				DocumentoC.adicionarLinha(" " + a.getTipo() + " -> " + a.getNome() + " : " + a.getValor());
			} else {
				DocumentoC.adicionarLinha(" " + a.getTipo() + " -> " + a.getNome());
			}

			ImprimirSubArvoreDeInstrucoes("   ", a, DocumentoC);

		}

		DocumentoC.adicionarLinha("");

		return DocumentoC.getConteudo();
	}

	private void ImprimirSubArvoreDeInstrucoes(String ePref, AST ASTC, Documento DocumentoC) {

		for (AST a : ASTC.getASTS()) {

			if (a.getValor().length() > 0) {
				DocumentoC.adicionarLinha(ePref + a.getTipo() + " -> " + a.getNome() + " : " + a.getValor());

			} else {
				DocumentoC.adicionarLinha(ePref + a.getTipo() + " -> " + a.getNome());

			}

			ImprimirSubArvoreDeInstrucoes(ePref + "   ", a, DocumentoC);

		}

	}



	public String MontandoArvoreDeInstrucoes(ArrayList<AST> lsAST) {

		Documento DocumentoC = new Documento();

		DocumentoC.adicionar("");

		for (AST a : lsAST) {

			DocumentoC.adicionar("" + a.getTipo() + "[\"" + a.getNome() + "\":\"" + a.getValor() + "\"] { ");

			MontandoSubArvoreDeInstrucoes("", a, DocumentoC);
			DocumentoC.adicionar("}");

		}

		DocumentoC.adicionar("");


		return DocumentoC.getConteudo().replace("\n", "");
	}

	private void MontandoSubArvoreDeInstrucoes(String ePref, AST ASTC, Documento DocumentoC) {

		for (AST a : ASTC.getASTS()) {

			DocumentoC.adicionar( a.getTipo() + "[\"" + a.getNome() + "\":\"" + a.getValor() + "\"] {");

			MontandoSubArvoreDeInstrucoes("", a, DocumentoC);
			DocumentoC.adicionar("}");

		}

	}

}
