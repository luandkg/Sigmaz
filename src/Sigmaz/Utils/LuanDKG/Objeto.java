package Sigmaz.Utils.LuanDKG;

import java.util.ArrayList;

public class Objeto {

	private String mNome;
	private ArrayList<Identificador> mIdentificadores;
	private ArrayList<Comentario> mComentarios;
	private ArrayList<Lista> mListas;

	public Objeto(String eNome) {
		mNome = eNome;

		mIdentificadores = new ArrayList<Identificador>();
		mComentarios = new ArrayList<Comentario>();
		mListas = new ArrayList<Lista>();

	}

	public void setNome(String eNome) {
		mNome = eNome;
	}

	public String getNome() {
		return mNome;
	}

	public Identificador Identifique(String eNome) {

		boolean enc = false;
		Identificador ret = null;

		for (Identificador mIdentificador : mIdentificadores) {

			if (mIdentificador.getNome().contentEquals(eNome)) {
				enc = true;
				ret = mIdentificador;
				break;
			}

		}

		if (enc == false) {
			ret = new Identificador(eNome, "");
			mIdentificadores.add(ret);
		}

		return ret;

	}
	
public Identificador Identifique(String eNome, short eValor) {
		
		return	this.Identifique(eNome,String.valueOf(eValor));
		
	}
	
	public Identificador Identifique(String eNome, int eValor) {
	
		return	this.Identifique(eNome,String.valueOf(eValor));
		
	}
	
	public Identificador Identifique(String eNome, float eValor) {
		
		return	this.Identifique(eNome,String.valueOf(eValor));
		
	}
	
	public Identificador Identifique(String eNome, double eValor) {
		
		return	this.Identifique(eNome,String.valueOf(eValor));
		
	}
	
	public Identificador Identifique(String eNome, boolean eValor) {
		
		return	this.Identifique(eNome,String.valueOf(eValor));
		
	}

	public Identificador Identifique(String eNome, String eValor) {

		boolean enc = false;
		Identificador ret = null;

		for (Identificador mIdentificador : mIdentificadores) {

			if (mIdentificador.getNome().contentEquals(eNome)) {
				enc = true;
				ret = mIdentificador;
				ret.setValor(eValor);
				break;
			}

		}

		if (enc == false) {
			ret = new Identificador(eNome, eValor);
			mIdentificadores.add(ret);
		}

		return ret;
	}

	public Identificador IdentifiqueInteiro(String eNome, int eValor) {

		boolean enc = false;
		Identificador ret = null;

		for (Identificador mIdentificador : mIdentificadores) {

			if (mIdentificador.getNome().contentEquals(eNome)) {
				enc = true;
				ret = mIdentificador;
				ret.setValor(String.valueOf(eValor));
				break;
			}

		}

		if (enc == false) {
			ret = new Identificador(eNome, String.valueOf(eValor));
			mIdentificadores.add(ret);
		}

		return ret;
	}

	public boolean IdentificadorExiste(String eIdentificadorNome) {

		boolean ret = false;

		for (Identificador mIdentificador : mIdentificadores) {

			if (mIdentificador.getNome().contentEquals(eIdentificadorNome)) {
				ret = true;
				break;
			}

		}
		return ret;
	}

	public ArrayList<Identificador> getIdentificadores() {
		return mIdentificadores;
	}

	public ArrayList<Comentario> getComentarios() {
		return mComentarios;
	}

	public ArrayList<Lista> getListas() {
		return mListas;
	}

	public void RemoverIdentificador(Identificador eIdentificador) {

		for (Identificador mIdentificador : mIdentificadores) {

			if (mIdentificador == eIdentificador) {
				mIdentificadores.remove(eIdentificador);
				break;
			}

		}

	}

	public void RemoverIdentificadorPorNome(String eIdentificador) {

		for (Identificador mIdentificador : mIdentificadores) {

			if (mIdentificador.getNome().contentEquals(eIdentificador)) {
				mIdentificadores.remove(mIdentificador);
				break;
			}

		}

	}

	public Comentario Comentar(String eNome, String eValor) {

		boolean enc = false;
		Comentario ret = null;

		for (Comentario mComentario : mComentarios) {

			if (mComentario.getNome().contentEquals(eNome)) {
				enc = true;
				ret = mComentario;
				ret.setValor(eValor);
				break;
			}

		}

		if (enc == false) {
			ret = new Comentario(eNome, eValor);
			mComentarios.add(ret);
		}

		return ret;
	}

	public void RemoverComentario(Comentario eComentario) {

		for (Comentario mComentario : mComentarios) {

			if (mComentario == eComentario) {
				mComentarios.remove(eComentario);
				break;
			}

		}

	}

	public void RemoverComentarioPorNome(String eComentario) {

		for (Comentario mComentario : mComentarios) {

			if (mComentario.getNome().contentEquals(eComentario)) {
				mComentarios.remove(mComentario);
				break;
			}

		}

	}

	// LISTAS

	public Lista CriarLista(String eNome) {

		Lista ret = new Lista(eNome);
		mListas.add(ret);

		return ret;
	}

	public Lista UnicaLista(String eNome) {

		boolean enc = false;
		Lista ret = null;

		for (Lista mLista : mListas) {

			if (mLista.getNome().contentEquals(eNome)) {
				enc = true;
				ret = mLista;
				break;
			}

		}

		if (enc == false) {
			ret = new Lista(eNome);
			mListas.add(ret);
		}

		return ret;
	}

	public void RemoverLista(Lista eLista) {

		for (Lista mLista : mListas) {

			if (mLista == eLista) {
				mListas.remove(eLista);
				break;
			}

		}

	}

	public void RemoverListaPorNome(String eNome) {

		for (Lista mLista : mListas) {

			if (mLista.getNome().contentEquals(eNome)) {
				mListas.remove(mLista);
				break;
			}

		}

	}

}
