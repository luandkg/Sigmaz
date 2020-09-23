package AppUI.Mottum.Organizacao;

import AppUI.Mottum.Componentes.Entidade;
import AppUI.Mottum.Componentes.Posicao;
import AppUI.Mottum.Componentes.Tamanho;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;


public class SuperGrupo {

	private ArrayList<Grupo> mGrupos;
	private ArrayList<Entidade> mEntidades;
	private LinkedList<Line2D.Float> mLimitadores;

	public SuperGrupo() {

		mGrupos = new ArrayList<Grupo>();
		mEntidades = new ArrayList<Entidade>();
		mLimitadores = new LinkedList<Line2D.Float>();

	}

	public ArrayList<Entidade> getEntidades() {
		return mEntidades;
	}

	public Grupo getGrupo(String eNome) {

		Grupo ret = null;

		for (Grupo mGrupo : mGrupos) {
			if (mGrupo.getNome().contentEquals(eNome)) {
				ret = mGrupo;
				break;
			}

		}

		if (ret == null) {
			ret = new Grupo(eNome);
			mGrupos.add(ret);
		}

		return ret;
	}

	public void IndexarEntidades(ArrayList<Entidade> eEntidades) {

		mGrupos.clear();
		mEntidades = eEntidades;

		Ordenar(mEntidades);

		for (Entidade mEntidade : eEntidades) {

			Grupo g = getGrupo(mEntidade.getTipo());
			g.AdicionarEntidade(mEntidade);

		}

		mLimitadores.clear();

		for (Entidade mEntidade : this.getEntidades()) {

			if (!mEntidade.getTipo().contentEquals("FogoDock")) {
				if (mEntidade.existeComponente(Posicao.ID) && (mEntidade.existeComponente(Tamanho.ID))) {

					Posicao P = (Posicao) mEntidade.obterComponente(Posicao.ID);
					Tamanho T = (Tamanho) mEntidade.obterComponente(Tamanho.ID);

					mLimitadores.add(new Line2D.Float(P.getX(), P.getY(), P.getX() + T.getLargura(), P.getY()));

					mLimitadores.add(new Line2D.Float(P.getX(), P.getY() + T.getAltura(), P.getX() + T.getLargura(),
							P.getY() + T.getAltura()));

					mLimitadores.add(new Line2D.Float(P.getX(), P.getY(), P.getX(), P.getY() + T.getAltura()));

					mLimitadores.add(new Line2D.Float(P.getX() + T.getLargura(), P.getY(), P.getX() + T.getLargura(),
							P.getY() + T.getAltura()));

				}

			}
		}

	}

	public void Ordenar(ArrayList<Entidade> mLS_Entidades) {

		Collections.sort(mLS_Entidades, new Comparator<Entidade>() {

			public int compare(Entidade o1, Entidade o2) {
				int r = 0;
				if (o2.getZIndex() > o1.getZIndex()) {
					r = -1;
				}
				if (o2.getZIndex() < o1.getZIndex()) {
					r = 1;
				}

				return r;
			}
		});

	}

	public LinkedList<Line2D.Float> getLimitadores() {
		return mLimitadores;
	}
}
