package AppUI.Mottum.Utils;

import java.util.ArrayList;

public class Debugger {

	private static boolean mDebugar = false;
	private static ArrayList<DebugTag> mTags = new ArrayList<DebugTag>();

	public static void NaoDebugar() {
		mDebugar = false;
	}

	public static void Debugar() {
		mDebugar = true;
	}

	public static void Info(String eMsg) {
		if (mDebugar) {
			System.out.println("INFO : " + eMsg);
		}
	}

	public static void Alerta(String eMsg) {
		if (mDebugar) {
			System.out.println("ALETA : " + eMsg);
		}
	}

	public static void DefinaBoolean(String eNome, boolean eValor) {

		boolean enc = false;
		for (DebugTag dt : mTags) {
			if (dt.getNome().contentEquals(eNome)) {
				dt.setValor(String.valueOf(eValor));
				enc = true;
				break;
			}
		}

		if (enc == false) {
			DebugTag DTN = new DebugTag(eNome, String.valueOf(eValor));
			mTags.add(DTN);
		}
	}

	public static boolean ObterBoolean(String eNome) {

		boolean ret = false;

		for (DebugTag dt : mTags) {
			if (dt.getNome().contentEquals(eNome)) {
				ret = dt.getValorBoolean();
				break;
			}
		}

		return ret;
	}

	public static class DebugTag {

		private String mNome = "";
		private String mValor = "";

		public DebugTag(String eNome, String eValor) {
			mNome = eNome;
			mValor = eValor;
		}

		public void setNome(String eNome) {
			mNome = eNome;
		}

		public void setValor(String eValor) {
			mValor = eValor;
		}

		public String getNome() {
			return mNome;
		}

		public String getValor() {
			return mValor;
		}

		public boolean getValorBoolean() {
			return Boolean.parseBoolean(mValor);
		}

	}
}
