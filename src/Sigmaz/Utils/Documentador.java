package Sigmaz.Utils;


import Sigmaz.Utils.LuanDKG.LuanDKG;
import Sigmaz.Utils.LuanDKG.Pacote;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Documentador {


	public String mapaObjeto(String eArquivo) {

		String saida = "";

		Path dpath = Paths.get(eArquivo);


		try {
			byte[] l = Files.readAllBytes(dpath);

			int li = 0;
			int lo = l.length;

			int d = 0;

			while (li < lo) {
				int novo = (int) l[li];

				saida += " " + novo;


				if (d >= 50) {
					d = 0;
					saida += "\n";
				}

				li += 1;
				d += 1;
			}


		} catch (IOException e) {
			e.printStackTrace();
		}

		return saida;

	}

	public String tamanhoObjeto(String eArquivo) {

		File file = new File(eArquivo);

		long t = file.length();


		return t + "";
	}


	public void compilar(ArrayList<AST> lsAST,String eArquivo) {


		LuanDKG DocumentoC = new LuanDKG();

		for (AST a : lsAST) {

			Pacote ePacote = new Pacote(a.getTipo());
			ePacote.Identifique("Nome",a.getNome());
			ePacote.Identifique("Valor",a.getValor());
			DocumentoC.getPacotes().add(ePacote);

			subASTPackage(ePacote,a);

		}

		//DocumentoC.Salvar("res/ASTPackage.txt");

		String eDocumento =  DocumentoC.gerarReduzido();


		byte[] bytes = eDocumento.getBytes(StandardCharsets.UTF_8);

		int t = bytes.length;
		//System.out.println("Tam : " + t);

		int i = 0;
		int o = eDocumento.length();

		int auxilador = 53;

		int mCrifrador[] = new int[5];

		mCrifrador[0] = 10;
		mCrifrador[1] = 56;
		mCrifrador[2] = 130;
		mCrifrador[3] = 22;
		mCrifrador[4] = 12;

		int ic = 0;
		int oc = 5;


		while (i < o) {
			int novo = (int) bytes[i];

			//novo += auxilador;

			novo +=mCrifrador[ic];
			ic+=1;
			if(ic==oc){ic=0;}

			if (novo >= 255) {
				novo -= 255;
			}


			bytes[i] = (byte) novo;
			i += 1;
		}

		Path path = Paths.get(eArquivo);
		try {
			Files.write(path, bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}




	}

	private void subASTPackage(Pacote ePacotePai,AST eAST) {

		for (AST a : eAST.getASTS()) {

			Pacote ePacote = new Pacote(a.getTipo());
			ePacote.Identifique("Nome",a.getNome());
			ePacote.Identifique("Valor",a.getValor());
			ePacotePai.getPacotes().add(ePacote);

			subASTPackage(ePacote,a);

		}

	}

	public ArrayList<AST> fromASTPackage(String eConteudo) {

		LuanDKG DocumentoC = new LuanDKG();
		DocumentoC.Parser(eConteudo);


		ArrayList<AST> ASTSaida = new ArrayList<AST>();

		for (Pacote mPacote: DocumentoC.getPacotes()) {

			String aNome = mPacote.Identifique("Nome").getValor();
			String aValor = mPacote.Identifique("Valor").getValor();

			AST mAST = new AST(mPacote.getNome());
			mAST.setNome(aNome);
			mAST.setValor(aValor);
			ASTSaida.add(mAST);

			subFromASTPackage(mAST,mPacote);
		}


		return ASTSaida;
	}

	private void subFromASTPackage(AST eAST,Pacote ePacotePai) {

		for (Pacote mPacote : ePacotePai.getPacotes()) {

			String aNome = mPacote.Identifique("Nome").getValor();
			String aValor = mPacote.Identifique("Valor").getValor();

			AST mAST = new AST(mPacote.getNome());
			mAST.setNome(aNome);
			mAST.setValor(aValor);
			eAST.getASTS().add(mAST);


			subFromASTPackage(mAST,mPacote);

		}

	}

	public ArrayList<AST> Decompilar(String eArquivo) {

		ArrayList<AST> ASTSaida = new ArrayList<AST>();


		Path dpath = Paths.get(eArquivo);
		int auxilador = 53;

		String saida = "";

		int mCrifrador[] = new int[5];

		mCrifrador[0] = 10;
		mCrifrador[1] = 56;
		mCrifrador[2] = 130;
		mCrifrador[3] = 22;
		mCrifrador[4] = 12;

		int ic = 0;
		int oc = 5;

		try {
			byte[] l = Files.readAllBytes(dpath);

			int li = 0;
			int lo = l.length;

			while (li < lo) {
				int novo = (int) l[li];

				//	novo -= auxilador;

				novo -=mCrifrador[ic];
				ic+=1;
				if(ic==oc){ic=0;}


				if (novo < 0) {
					novo += 256;
				}


				l[li] = (byte) novo;
				li += 1;
			}


			saida = new String(l, StandardCharsets.UTF_8);


		//	System.out.println("Decompilado : " + saida);


		} catch (IOException e) {
			e.printStackTrace();
		}


		ASTSaida = fromASTPackage(saida);

		return ASTSaida;

	}


}
