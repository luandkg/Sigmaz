package OAVersion.LuanDKG;

import java.io.*;

public class Texto {

	public static void Escrever(String eArquivo, String eConteudo) {

		BufferedWriter writer = null;
		try {
			File logFile = new File(eArquivo);

			writer = new BufferedWriter(new FileWriter(logFile));
			writer.write(eConteudo);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (Exception e) {
			}
		}

	}

	public static String Ler(String eArquivo) {

		String ret = "";

		try {
			FileReader arq = new FileReader(eArquivo);
			BufferedReader lerArq = new BufferedReader(arq);

			String linha = lerArq.readLine();

			ret += linha;

			while (linha != null) {

				linha = lerArq.readLine();
				if (linha != null) {
					ret += "\n" + linha;
				}

			}

			arq.close();
		} catch (IOException e) {

		}

		return ret;
	}

	public static String Anexar(String eConteudo, String eLinha) {

		if (eConteudo.contentEquals("")) {
			return eLinha;

		} else {
			return eConteudo + "\n" + eLinha;

		}
	}

}
