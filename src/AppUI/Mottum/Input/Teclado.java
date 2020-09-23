package AppUI.Mottum.Input;

import AppUI.Mottum.Utils.Cronometro;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;


public class Teclado extends KeyAdapter implements KeyListener {

	public class Tecla {

		private int mValor;
		private boolean mPrecionada;
		private String mValorString;

		public Tecla(int eValor, boolean ePrecionada) {
			mValor = eValor;
			mPrecionada = ePrecionada;
			mValorString = "";
		}

		public void Precionar() {
			mPrecionada = true;
		}

		public void Liberar() {
			mPrecionada = false;
		}

		public boolean getPrecionada() {
			return mPrecionada;
		}

		public int getValor() {
			return mValor;
		}

		public String getValorString() {
			return mValorString;
		}

		public void AtribuirString(String eValorString) {
			mValorString = eValorString;
		}
	}

	private ArrayList<Tecla> Teclas;
	private Cronometro mCron;
	private boolean mObservar;

	public Teclado() {
		super();
		Teclas = new ArrayList<Tecla>();
		mCron = new Cronometro(50);
		mObservar = false;
	}

	public void update() {
		mCron.Esperar();
		if (mCron.Esperado()) {
			mObservar = false;
		}
	}

	public boolean getObservar() {
		return !mObservar;
	}

	public void Observe() {
		mObservar = true;
	}

	@Override
	public void keyPressed(KeyEvent e) {

		synchronized (Teclas) {

			boolean enc = false;
			for (Tecla TeclaC : Teclas) {
				if (TeclaC.getValor() == e.getKeyCode()) {
					TeclaC.Precionar();
					enc = true;
					break;
				}
			}

			if (enc == false) {
				Tecla TeclaNova = new Tecla(e.getKeyCode(), true);
				Teclas.add(TeclaNova);
			}

			keyCharPressed(e);

		}

	}

	public boolean iskeyPressedELibere(int eKeyCode) {
		boolean ret = isKeyPressed(eKeyCode);
		Liberar(eKeyCode);
		return ret;
	}

	public boolean iskeyPressedELibereDuplo(int eKeyCode_1, int eKeyCode_2) {
		boolean ret = isKeyPressed(eKeyCode_1) && isKeyPressed(eKeyCode_2);
		if (ret) {
			Liberar(eKeyCode_1);
			Liberar(eKeyCode_2);
		}
		return ret;
	}

	public void keyCharPressed(KeyEvent e) {

		boolean enc = false;

		try {

			synchronized (Teclas) {

				for (Tecla TeclaC : Teclas) {
					if (TeclaC.getValorString().equals(String.valueOf("C:" + e.getKeyChar()))) {

						TeclaC.Precionar();
						enc = true;
						break;
					}
				}

				if (enc == false) {
					Tecla TeclaNova = new Tecla(-100000, true);
					TeclaNova.AtribuirString("C:" + e.getKeyChar());
					TeclaNova.Precionar();
					Teclas.add(TeclaNova);
				}

			}
		} catch (Exception ee) {

		}

	}

	@Override
	public void keyReleased(KeyEvent e) {

		try {

			synchronized (Teclas) {

				for (Tecla TeclaC : Teclas) {
					if (TeclaC.getValor() == e.getKeyCode()) {
						TeclaC.Liberar();
						break;
					}
				}

				for (Tecla TeclaC : Teclas) {
					if (TeclaC.getValorString().equals(String.valueOf("C:" + e.getKeyChar()))) {
						TeclaC.Liberar();
						break;
					}
				}

			}
		} catch (Exception ee) {

		}

	}

	public boolean isKeyPressed(int eKeyCode) {

		boolean enc = false;

		try {

			synchronized (Teclas) {

				for (Tecla TeclaC : Teclas) {
					if (TeclaC.getValor() == eKeyCode) {

						enc = TeclaC.getPrecionada();

						break;
					}
				}

			}
		} catch (Exception ee) {

		}

		return enc;

	}

	public boolean isCharPressedELibere(String s) {
		boolean ret = isCharPressed(s);
		LiberarChar(s);
		return ret;
	}

	public boolean isCharPressed(String s) {

		boolean enc = false;

		try {

			synchronized (Teclas) {

				for (Tecla TeclaC : Teclas) {
					if (TeclaC.getValorString().equals(String.valueOf("C:" + s))) {
						enc = TeclaC.getPrecionada();
						break;
					}
				}

			}
		} catch (Exception ee) {

		}

		return enc;

	}

	public void Liberar(int eKeyCode) {

		boolean enc = false;

		try {

			synchronized (Teclas) {

				for (Tecla TeclaC : Teclas) {
					if (TeclaC.getValor() == eKeyCode) {
						TeclaC.Liberar();
						enc = true;
						break;

					}

				}

				if (enc == false) {
					Tecla TeclaNova = new Tecla(eKeyCode, false);
					Teclas.add(TeclaNova);
				}

			}
		} catch (Exception ee) {

		}

	}

	public void LiberarChar(String s) {

		boolean enc = false;

		try {

			synchronized (Teclas) {

				for (Tecla TeclaC : Teclas) {
					if (TeclaC.getValorString().equals(String.valueOf("C:" + s))) {
						TeclaC.Liberar();

						enc = true;
						break;

					}

				}

				if (enc == false) {
					Tecla TeclaNova = new Tecla(-100000, false);
					TeclaNova.AtribuirString("C:" + s);
					Teclas.add(TeclaNova);
				}

			}
		} catch (Exception ee) {

		}

	}

}
