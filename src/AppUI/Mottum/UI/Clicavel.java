package AppUI.Mottum.UI;


import AppUI.Mottum.Utils.Cronometro;

public class Clicavel {

	Cronometro Tempo;

	boolean clicavel = false;
	boolean mClicado = false;
	
	public Clicavel() {
		
		Tempo = new Cronometro(200);
		
	}
	
	public void update(double dt,boolean ePrecionado) {
	
		mClicado =false;
		
		Tempo.Esperar();

		if (Tempo.Esperado()) {
			clicavel = true;
		}
		
		if (clicavel) {
			if (ePrecionado == true) {
				Tempo.Zerar();
				clicavel = false;
				mClicado=true;
			}
		}
		
		
	}
	
	public boolean getClicado() {return mClicado;}
}
