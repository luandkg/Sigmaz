package AppUI.Mottum.Utils;

import java.io.InputStream;

public class Recurso {

	public Recurso() {
	}

	public static InputStream Carregar(String eLocal) {
		InputStream input = Recurso.class.getResourceAsStream(eLocal);
		if (input == null) {
			input = Recurso.class.getResourceAsStream("/" + eLocal);
		}
		return input;
	}

}
