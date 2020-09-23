package AppUI.Mottum.Componentes;

import java.util.Random;

public class LuanID {

	public static int Contador = 0;

	public static String gerarID() {
		String ret = "";

		String ALFABETO = "ABCDFGHIJKLMNOPQRSTUVWXYZ0123456789";

		Random r = new Random();
		for (int i = 0; i < 5; i++) {
			ret += ALFABETO.charAt((r.nextInt(ALFABETO.length())));
		}

		ret += ".";

		String sufixo = String.valueOf(Contador);
		while (sufixo.length() < 20) {
			sufixo = "0" + sufixo;
		}

		String P1 = String.valueOf(sufixo.charAt(6)) + String.valueOf(sufixo.charAt(15))
				+ (String.valueOf(sufixo.charAt(4)));
		String P2 = String.valueOf(sufixo.charAt(14)) + String.valueOf(sufixo.charAt(18))
				+ (String.valueOf(sufixo.charAt(16)));
		String P3 = (String.valueOf(sufixo.charAt(1))) + String.valueOf(sufixo.charAt(13))
				+ String.valueOf(sufixo.charAt(12)) + (String.valueOf(sufixo.charAt(11)));
		String P4 = (String.valueOf(sufixo.charAt(0))) + String.valueOf(sufixo.charAt(8))
				+ String.valueOf(sufixo.charAt(9)) + (String.valueOf(sufixo.charAt(10)));
		String P5 = String.valueOf(sufixo.charAt(5)) + String.valueOf(sufixo.charAt(17))
				+ (String.valueOf(sufixo.charAt(7)));
		String P6 = String.valueOf(sufixo.charAt(19)) + String.valueOf(sufixo.charAt(3))
				+ (String.valueOf(sufixo.charAt(2)));

		ret += P1;

		for (int i = 0; i < 10; i++) {
			ret += ALFABETO.charAt((r.nextInt(ALFABETO.length())));
		}

		ret += P6;

		ret += ".";

		ret += P2;

		for (int i = 0; i < 12; i++) {
			ret += ALFABETO.charAt((r.nextInt(ALFABETO.length())));
		}

		ret += P5;

		ret += "-";
		ret += P3;

		for (int i = 0; i < 3; i++) {
			ret += ALFABETO.charAt((r.nextInt(ALFABETO.length())));
		}

		ret += P4;

		for (int i = 0; i < 3; i++) {
			ret += ALFABETO.charAt((r.nextInt(ALFABETO.length())));
		}

		Contador += 1;

		return ret;
	}

	public static String verificar(String entrada) {

		int P1 = parcela(entrada, 0, 3);
		int P2 = parcela(entrada, 8, 15);
		int P3 = parcela(entrada, entrada.length() - 10, entrada.length());

		P1 = repartir(P1, 30);
		P2 = repartir(P2, 50);
		P3 = repartir(P1, 70);

		int M1 = P1 + P2;
		int M2 = P3 * P1;
		int M3 = P2 * P3;

		String ALFABETO = "ABCDFGHIJKLMNOPQRSTUVWXYZ0123456789";

		M1 = repartir(M1, ALFABETO.length());
		M2 = repartir(M2, ALFABETO.length());
		M3 = repartir(M3, ALFABETO.length());

		return String.valueOf(ALFABETO.charAt(M1)) + String.valueOf(ALFABETO.charAt(M2))
				+ String.valueOf(ALFABETO.charAt(M3));

	}

	public static int repartir(int entrada, int taxa) {
		int ret = entrada;
		while (ret >= taxa) {
			ret -= taxa;
		}
		return ret;
	}

	public static int parcela(String entrada, int inicio, int fim) {

		int somando = 0;

		for (int i = inicio; i < fim; i++) {
			somando += obterValor(entrada.charAt(i));
		}

		return somando;
	}

	public static int obterValor(char c) {
		int ret = 0;

		String ALFABETO = "ABCDFGHIJKLMNOPQRSTUVWXYZ0123456789.-";

		for (int i = 0; i < ALFABETO.length(); i++) {

			if (ALFABETO.charAt(i) == c) {
				break;
			}
			ret = i;
		}

		return ret;
	}

}
