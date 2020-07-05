package OAVersion.LuanDKG;

import java.util.ArrayList;

public class Parser {

	private final String ALFABETO_INICIAL = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_@.";
	private final String ALFABETO_FINAL = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_@.0123456789";

	private final String TOKEN_PACOTE = "PACOTE";
	private final String TOKEN_ID = "ID";
	private final String TOKEN_LISTA = "LISTA";
	private final String TOKEN_OBJETO = "OBJETO";

	private String mDocumento;
	private int mIndex;
	private int mMaximo;

	public Parser() {

		mDocumento = "";
		mIndex = 0;
		mMaximo = 0;

	}

	public String Decodifica(String e) {
		e = e.replace("@H", "-");
		e = e.replace("@D", "\"");
		e = e.replace("@S", "'");
		e = e.replace("@A", "@");

		return e;
	}

	// PARSER

	public void Parse(String eDocumento, LuanDKG eEmpacotador) {

		eEmpacotador.getPacotes().clear();

		// mPacotes = new ArrayList<Pacote>();

		mDocumento = eDocumento;
		mIndex = 0;
		mMaximo = eDocumento.length();

		while (mIndex < mMaximo) {
			String l = String.valueOf(eDocumento.charAt(mIndex));

			if (l.contentEquals(" ")) {
			} else if (l.contentEquals("\n")) {
			} else if (l.contentEquals("\t")) {
			} else if (ALFABETO_INICIAL.contains(l)) {
				String Palavra = ParsePalavra().toUpperCase();

				if (Palavra.contentEquals(TOKEN_PACOTE)) {

					mIndex += 1;

					Parser_Pacote(eEmpacotador.getPacotes());

				} else {
					System.out.println("TOKEN DESCONHECIDO : " + Palavra);

				}
			} else {
				System.out.println(mIndex + " : " + l);
			}

			mIndex += 1;
		}

	}

	private String ParsePalavra() {

		String p = String.valueOf(mDocumento.charAt(mIndex));
		String ret = p;

		if (ALFABETO_INICIAL.contains(p)) {

			mIndex += 1;

			while (mIndex < mMaximo) {

				String d = String.valueOf(mDocumento.charAt(mIndex));

				if (ALFABETO_FINAL.contains(d)) {
					ret += d;
				} else {
					mIndex -= 1;
					break;
				}

				mIndex += 1;

			}

		}

		return ret;
	}

	private String ParsePalavraFinal() {

		String p = String.valueOf(mDocumento.charAt(mIndex));
		String ret = p;

		if (ALFABETO_FINAL.contains(p)) {

			mIndex += 1;

			while (mIndex < mMaximo) {

				String d = String.valueOf(mDocumento.charAt(mIndex));

				if (ALFABETO_FINAL.contains(d)) {
					ret += d;
				} else {
					mIndex -= 1;
					break;
				}

				mIndex += 1;

			}

		}

		return ret;
	}

	private String EsperarPalavra() {

		// System.out.println("Aqui " + String.valueOf(mDocumento.charAt(mIndex)));

		String ret = "";

		while (mIndex < mMaximo) {

			String l = String.valueOf(mDocumento.charAt(mIndex));

			if (ALFABETO_INICIAL.contains(l)) {
				ret = ParsePalavra();
				break;

			} else if (l.contentEquals(" ")) {
			} else if (l.contentEquals("\n")) {
			} else if (l.contentEquals("\t")) {

			} else {

			}

			mIndex += 1;

		}

		return ret;
	}

	private boolean EsperarPor(String Esperando) {

		// System.out.println("Aqui " + String.valueOf(mDocumento.charAt(mIndex)));

		boolean ret = false;

		while (mIndex < mMaximo) {

			String l = String.valueOf(mDocumento.charAt(mIndex));

			if (Esperando.contentEquals(l)) {
				ret = true;
				mIndex += 1;
				break;

			} else if (l.contentEquals(" ")) {
			} else if (l.contentEquals("\n")) {
			} else if (l.contentEquals("\t")) {

			} else {

			}

			mIndex += 1;

		}

		return ret;
	}

	private void Parser_Pacote(ArrayList<Pacote> lsPacotes) {

		String Palavra = Decodifica(EsperarPalavra());
		// System.out.println("PACOTE NOME : " + Palavra);

		Pacote NovoPacote = new Pacote(Palavra);
		lsPacotes.add(NovoPacote);

		if (EsperarPor("{")) {
			// System.out.println("Achou {");

			Parser_DentroPacote(NovoPacote);

		} else {
			System.out.println("ERRO : Era esperado {");

		}

	}

	private void Parser_DentroPacote(Pacote PC) {

		while (mIndex < mMaximo) {
			String l = String.valueOf(mDocumento.charAt(mIndex));

			if (l.contentEquals(" ")) {
			} else if (l.contentEquals("\n")) {
			} else if (l.contentEquals("\t")) {
			} else if (ALFABETO_INICIAL.contains(l)) {
				String Palavra = ParsePalavra().toUpperCase();

				if (Palavra.contentEquals(TOKEN_PACOTE)) {

					mIndex += 1;

					Parser_Pacote(PC.getPacotes());

				} else if (Palavra.contentEquals(TOKEN_ID)) {

					mIndex += 1;

					Parser_Identificador(PC.getIdentificadores());
				} else if (Palavra.contentEquals(TOKEN_LISTA)) {

					mIndex += 1;

					Parser_LISTA(PC.getListas());
				} else if (Palavra.contentEquals(TOKEN_OBJETO)) {

					mIndex += 1;

					Parser_DentroObjeto(PC);
				} else {
					System.out.println("TOKEN DESCONHECIDO : " + Palavra);

				}
			} else if (l.contentEquals("-")) {
				mIndex += 1;

				Parser_Comentario(PC.getComentarios());

			} else if (l.contentEquals("}")) {
				// System.out.println("Saindo do Pacote : " + PC.getNome());
				break;
			} else if (l.contentEquals("~")) {
				// System.out.println("Saindo do Pacote : " + PC.getNome());
				break;
			} else {
				System.out.println(mIndex + " : " + l);
			}

			mIndex += 1;
		}

	}

	private void Parser_DentroObjeto(Pacote PC) {

		String NomeDoObjeto = Decodifica(EsperarPalavra());
		// System.out.println("OBJETO NOME : " + NomeDoObjeto);

		Objeto NovoObjeto = new Objeto(NomeDoObjeto);
		PC.getObjetos().add(NovoObjeto);

		if (EsperarPor("{")) {

			while (mIndex < mMaximo) {
				String l = String.valueOf(mDocumento.charAt(mIndex));

				if (l.contentEquals(" ")) {
				} else if (l.contentEquals("\n")) {
				} else if (l.contentEquals("\t")) {
				} else if (ALFABETO_INICIAL.contains(l)) {
					String Palavra = ParsePalavra().toUpperCase();

					if (Palavra.contentEquals(TOKEN_ID)) {

						mIndex += 1;

						Parser_Identificador(NovoObjeto.getIdentificadores());

					} else if (Palavra.contentEquals(TOKEN_LISTA)) {

						mIndex += 1;

						Parser_LISTA(NovoObjeto.getListas());

					} else {
						System.out.println("TOKEN DESCONHECIDO : " + Palavra);

					}
				} else if (l.contentEquals("-")) {
					mIndex += 1;

					Parser_Comentario(PC.getComentarios());

				} else if (l.contentEquals("}")) {
					// System.out.println("Saindo do Objeto : " + PC.getNome());
					break;

				} else {
					System.out.println(mIndex + " : " + l);
				}

				mIndex += 1;
			}

		} else {
			System.out.println("ERRO : Era esperado {");

		}

	}

	private void Parser_Identificador(ArrayList<Identificador> ls_Identificadores) {

		String NomeIdentificador = Decodifica(EsperarPalavra());

		Identificador IDC = new Identificador(NomeIdentificador);
		ls_Identificadores.add(IDC);

		if (EsperarPor("=")) {
			// System.out.println("Achou =");

			String eValor = Esperar_Texto();
			IDC.setValor(Decodifica(eValor));
			// Parser_DentroPacote(NovoPacote);

		} else {
			System.out.println("ERRO : Era esperado =");

		}

	}

	private void Parser_LISTA(ArrayList<Lista> ls_Listas) {

		String NomeIdentificador = Decodifica(EsperarPalavra());

		Lista ListaC = new Lista(NomeIdentificador);
		ls_Listas.add(ListaC);

		if (EsperarPor("{")) {

			Parser_Dentro_Lista(ListaC);

		} else {
			System.out.println("ERRO : Era esperado {");

		}

	}

	private void Parser_Dentro_Lista(Lista PC) {

		while (mIndex < mMaximo) {
			String l = String.valueOf(mDocumento.charAt(mIndex));

			if (l.contentEquals(" ")) {
			} else if (l.contentEquals("\n")) {
			} else if (l.contentEquals("\t")) {
			} else if (ALFABETO_FINAL.contains(l)) {
				String Palavra = ParsePalavraFinal();

				PC.AdicionarItem(Palavra);
				// mIndex += 1;

			} else if (l.contentEquals("}")) {
				// System.out.println("Saindo da Lista : " + PC.getNome());
				break;

			} else {
				System.out.println(mIndex + " : " + l);
			}

			mIndex += 1;
		}

	}

	private void Parser_Comentario(ArrayList<Comentario> ls_Comentarios) {

		String l = String.valueOf(mDocumento.charAt(mIndex));

		if (l.contentEquals("-")) {

			mIndex += 1;

			String NomeIdentificador = Decodifica(EsperarPalavra());
			String Valor = "";

			mIndex += 1;

			boolean ret = EsperarPor(":");

			if (mIndex < mMaximo) {
				String l2 = String.valueOf(mDocumento.charAt(mIndex));

			}

			if (ret) {
				// System.out.println("Achou =");

				String eValor = Esperar_Texto();
				Valor = Decodifica(eValor);

				if (mIndex < mMaximo) {
					String l2 = String.valueOf(mDocumento.charAt(mIndex));
					// System.out.println("Aqui 1 : " + l2);
				}

				EsperarPor("-");

				if (mIndex < mMaximo) {
					String l2 = String.valueOf(mDocumento.charAt(mIndex));
					// System.out.println("Aqui 2 : " + l2);
				}
				EsperarPor("-");

				if (mIndex < mMaximo) {
					String l2 = String.valueOf(mDocumento.charAt(mIndex));
					// System.out.println("Aqui 3 : " + l2);
				}

			} else {
				System.out.println("ERRO : Era esperado : " + "");

			}

			Comentario NovoComentario = new Comentario(NomeIdentificador, Valor);

			ls_Comentarios.add(NovoComentario);
		} else {

			System.out.println("ERRO : Era esperado -");

		}

	}

	private String Esperar_Texto() {

		// System.out.println("Aqui " + String.valueOf(mDocumento.charAt(mIndex)));

		String ret = "";

		while (mIndex < mMaximo) {

			String l = String.valueOf(mDocumento.charAt(mIndex));

			if (l.contentEquals("'")) {
				mIndex += 1;

				ret = BuscandoTexto("'");
				break;
			} else if (l.contentEquals("\"")) {
				mIndex += 1;

				ret = BuscandoTexto("\"");
				break;
			} else if (l.contentEquals(" ")) {
			} else if (l.contentEquals("\n")) {
			} else if (l.contentEquals("\t")) {

			} else {

			}

			mIndex += 1;

		}

		return ret;
	}

	private String BuscandoTexto(String Finalizador) {

		// System.out.println("Aqui " + String.valueOf(mDocumento.charAt(mIndex)));

		String ret = "";

		while (mIndex < mMaximo) {

			String l = String.valueOf(mDocumento.charAt(mIndex));

			if (l.contentEquals(Finalizador)) {
				mIndex += 1;
				break;

			} else {
				ret += l;
			}

			mIndex += 1;

		}

		return ret;
	}

}
