package Sigmaz;

import Sigmaz.Analisador.Analisador;
import Sigmaz.Compilador.AST;
import Sigmaz.Executor.RunTime;
import Sigmaz.Lexer.Lexer;
import Sigmaz.Lexer.Token;
import Sigmaz.Utils.Texto;
import Sigmaz.Compilador.Compiler;

import java.io.File;
import java.util.ArrayList;

// COMPILADOR OLIMPUS

// DESENVOLVEDOR : LUAN FREITAS

import java.util.Calendar;


public class Sigmaz {

	public void init(String eArquivo,String saida) {

		
		File arq = new File(eArquivo);
		String local = arq.getParent() + "/";




		
		// System.out.println("####### CONTEUDO #######");

		// System.out.println(conteudo);
		System.out.println("################ SIGMAZ ################");
		System.out.println("");
		System.out.println(" - AUTOR	: LUAN FREITAS");
		System.out.println(" - VERSION  : 1.0");
		System.out.println(" - STATUS  	: ALPHA");

		System.out.println("");





			Compiler CompilerC = new Compiler();
			CompilerC.init(eArquivo,local);

			System.out.println("################# LEXER ##################");
			System.out.println("");
			System.out.println("\t Iniciado : " + getDate().toString());
			System.out.println("\t - Chars : " +  CompilerC.getIChars());
			System.out.println("\t - Tokens : " + CompilerC.getITokens());
			System.out.println("\t - Erros : " + CompilerC.getErros_Lexer().size());
			System.out.println("\t Finalizado : " + getDate().toString());
			System.out.println("");


			System.out.println("############### COMPILADOR ###############");
			System.out.println("");
			System.out.println("\t Iniciado : " + getDate().toString());
			System.out.println("\t - Instrucoes : " + CompilerC.Instrucoes());
			System.out.println("\t - Erros : " + CompilerC.getErros_Compiler().size());
			System.out.println("\t - Requisitados : ");

			for (String Req : CompilerC.getRequisitados()) {
				System.out.println("\t\t - " + Req);
			}
			
			System.out.println("\t Finalizado : " + getDate().toString());


			if (CompilerC.getErros_Lexer().size() > 0) {
				System.out.println("\n\t ERROS DE LEXICOGRAFICA : ");

				for (String Erro : CompilerC.getErros_Lexer()) {
					System.out.println("\t\t" + Erro);
				}
			}
			
			if (CompilerC.getErros_Compiler().size() > 0) {
				System.out.println("\n\t ERROS DE COMPILACAO : ");

				for (String Erro : CompilerC.getErros_Compiler()) {
					System.out.println("\t\t" + Erro);
				}
			}

			
			
		
			
			if (CompilerC.getErros_Lexer().size() == 0 && CompilerC.getErros_Compiler().size() ==0) {

				
		

			
				System.out.println("");
				System.out.println("################ ANALISE ################");
				System.out.println("");
				
				
				
				Analisador AnaliseC = new Analisador();
				String AI = getDate().toString();
				AnaliseC.init(CompilerC.getASTS());
				String AF = getDate().toString();



				System.out.println("\t - Iniciado : " + AI);
				System.out.println("\t - Finalizado : " + AF);

				System.out.println("\t - Erros : " + AnaliseC.getErros().size());

				if (AnaliseC.getErros().size() > 0) {
					System.out.println("\n\t ERROS DE ANALISE : ");

					for (String Erro : AnaliseC.getErros()) {
						System.out.println("\t\t" + Erro);
					}
				} else {
					
					
					
					System.out.println("");
					System.out.println("################ OBJETO ################");
					System.out.println("");
					
					
					String instrucoes = CompilerC.ArvoreDeInstrucoes();
					
					
					CompilerC.Compilar(saida);

					
					System.out.println("\t Iniciado : " + getDate().toString());
					System.out.println("\t - Tamanho : " + CompilerC.tamanhoObjeto(saida));
					System.out.println("\t Finalizado : " + getDate().toString());
					
					System.out.println("");

					
					ArrayList<AST> ASTSaida = CompilerC.Decompilar(saida);
				
					System.out.println("");
			

					System.out.println("");

					
					
					System.out.println( CompilerC.ArvoreDeInstrucoes_De(ASTSaida));
					
					
					
					System.out.println("");
					System.out.println("################ RUNTIME ################");
					System.out.println("");

					RunTime RunTimeC = new RunTime();
					String DI = getDate().toString();

					RunTimeC.init(ASTSaida);
					
					String DF = getDate().toString();

					System.out.println("");
					System.out.println("----------------------------------------------");
					System.out.println("");

					System.out.println("\t - Iniciado : " + DI);
					System.out.println("\t - Finalizado : " + DF);

					System.out.println("\t - Erros : " + RunTimeC.getErros().size());

					if (RunTimeC.getErros().size() > 0) {
						System.out.println("\n\t ERROS DE EXECUCAO : ");

						for (String Erro : RunTimeC.getErros()) {
							System.out.println("\t\t" + Erro);
						}
					}

					System.out.println("");
					System.out.println("----------------------------------------------");

					
					
				}
				


		}

	}

	public String getDate() {

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
