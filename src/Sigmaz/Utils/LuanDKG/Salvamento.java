package Sigmaz.Utils.LuanDKG;

import java.util.ArrayList;

public class Salvamento {

	public String Codifica(String e) {
		e = e.replace("@", "@A");
		e = e.replace("'", "@S");
		e = e.replace("\"", "@D");
		e = e.replace("-", "@H");

		return e;
	}

	private void Identificadores(ITexto ITextoC, String ePrefixo, ArrayList<Identificador> mIdentificadores) {

		for (Identificador IdentificadorC : mIdentificadores) {

			ITextoC.AdicionarLinha(ePrefixo + "   " + "ID " + Codifica(IdentificadorC.getNome()) + " = " + "\""
					+ Codifica(IdentificadorC.getValor()) + "\"");

		}

	}

	private void Listas(ITexto ITextoC, String ePrefixo, ArrayList<Lista> mListas) {

		for (Lista ListaC : mListas) {

			if (ListaC.getItens().size() == 0) {

				ITextoC.AdicionarLinha(ePrefixo + "   " + "LISTA " + Codifica(ListaC.getNome()) + " { } ");

			} else {

				String Itens = "";

				for (String eItem : ListaC.getItens()) {

					Itens += eItem + " ";

				}

				ITextoC.AdicionarLinha(ePrefixo + "   " + "LISTA " + Codifica(ListaC.getNome()) + " { " + Itens + "} ");

			}

		}

	}

	private void Comentarios(ITexto ITextoC, String ePrefixo, ArrayList<Comentario> mComentarios) {

		for (Comentario ComentarioC : mComentarios) {

			ITextoC.AdicionarLinha(ePrefixo + "   " + "-- " + Codifica(ComentarioC.getNome()) + " : " + "\""
					+ Codifica(ComentarioC.getValor()) + "\" --");

		}

	}

	public void Objetos(ITexto ITextoC, String ePrefixo, ArrayList<Objeto> mObjetos) {

		for (Objeto ObjetoC : mObjetos) {

			// if (ObjetoC.getIdentificadores().size() == 0 && ObjetoC.getListas().size() ==
			// 0
			// && ObjetoC.getComentarios().size() == 0) {

			// ITextoC.AdicionarLinha(ePrefixo + " " + "OBJETO " + ObjetoC.getNome() + " { }
			// ");

			// } else {

			ITextoC.AdicionarLinha(ePrefixo + "   " + "OBJETO " + ObjetoC.getNome() + " { ");

			for (Identificador IdentificadorC : ObjetoC.getIdentificadores()) {

				ITextoC.AdicionarLinha(ePrefixo + "   " + "   " + "ID " + Codifica(IdentificadorC.getNome()) + " = " + "\""
						+ Codifica(IdentificadorC.getValor()) + "\"");

			}

			for (Lista ListaC : ObjetoC.getListas()) {

				// if (ListaC.getItens().size() == 0) {

				// ITextoC.AdicionarLinha(
				// ePrefixo + " " + " " + "LISTA " + Codifica(ListaC.getNome()) + " = { } ");

				// } else {

				String Itens = "";
				int o = ListaC.getItens().size();
				int i = 1;

				for (String eItem : ListaC.getItens()) {

					if (i == o) {
						Itens += eItem + " ";
					} else {
						Itens += eItem + " , ";
					}
					i += 1;
				}

				ITextoC.AdicionarLinha(
						ePrefixo + "   " + "   " + "LISTA " + Codifica(ListaC.getNome()) + " = { " + Itens + "} ");

			}

			// }

			for (Comentario ComentarioC : ObjetoC.getComentarios()) {

				ITextoC.AdicionarLinha(ePrefixo + "   " + "   " + "-- " + Codifica(ComentarioC.getNome()) + " : \""
						+ Codifica(ComentarioC.getValor()) + "\" -- ");

			}

			ITextoC.AdicionarLinha(ePrefixo + "   " + "}");
		}

	}

	public void Pacote_Listar(ITexto ITextoC, String ePrefixo, ArrayList<Pacote> lsPacotes) {

		for (Pacote PacoteC : lsPacotes) {

			if (PacoteC.getPacotes().size() == 0 && PacoteC.getListas().size() == 0
					&& PacoteC.getIdentificadores().size() == 0 && PacoteC.getObjetos().size() == 0) {

				ITextoC.AdicionarLinha(ePrefixo + "PACOTE " + Codifica(PacoteC.getNome()) + " { } ");

			} else {

				ITextoC.AdicionarLinha(ePrefixo + "PACOTE " + Codifica(PacoteC.getNome()));
				ITextoC.AdicionarLinha(ePrefixo + " {");

				Identificadores(ITextoC, ePrefixo, PacoteC.getIdentificadores());

				Listas(ITextoC, ePrefixo, PacoteC.getListas());

				Comentarios(ITextoC, ePrefixo, PacoteC.getComentarios());

				Objetos(ITextoC, ePrefixo, PacoteC.getObjetos());

				Pacote_Listar(ITextoC, ePrefixo + "   ", PacoteC.getPacotes());

				ITextoC.AdicionarLinha(ePrefixo + " } ");

			}


		}

	}
}
