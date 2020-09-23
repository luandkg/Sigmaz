package Container;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class Pasta {

	private Ponto mPonto;
	private Container mContainer;
	private boolean mAberto;
	private ArrayList<Arquivo> mArquivos;
	private ArrayList<Pasta> mPastas;


	private boolean mAbertoTodos;
	private ArrayList<Arquivo> mArquivosTodos;
	private ArrayList<Pasta> mPastasTodos;

	public Pasta(Container eContainer, Ponto ePonto) {

		mContainer = eContainer;
		mPonto = ePonto;

		mAberto = false;
		mAbertoTodos=false;

		mArquivos = new ArrayList<Arquivo>();
		mPastas = new ArrayList<Pasta>();

		mArquivosTodos = new ArrayList<Arquivo>();
		mPastasTodos = new ArrayList<Pasta>();

	}

	public String getNome() {
		return mPonto.getNome();
	}

	public long getTipo() {
		return mPonto.getTipo();
	}

	public long getInicio() {
		return mPonto.getInicio();
	}

	public long getFim() {
		return mPonto.getFim();
	}

	private void abrir() {

		mPastas.clear();
		mArquivos.clear();

		try {

			RandomAccessFile raf = new RandomAccessFile(new File(mContainer.getArquivo()), "rw");

			FileBinary fu = new FileBinary(raf);

			fu.setPonteiro(getInicio());

			int v = 0;

			while (v != 13) {
				v = (int) fu.readByte();

				if (v == 11) {

					String s1 = fu.readString();
					long l2 = fu.readLong();
					long l3 = fu.readLong();

					mPastas.add(new Pasta(mContainer, new Ponto(s1, 11, l2, l3)));

				} else if (v == 12) {

					String s1 = fu.readString();
					long l2 = fu.readLong();
					long l3 = fu.readLong();

					mArquivos.add(new Arquivo(mContainer, new Ponto(s1, 12, l2, l3)));

				}
			}

			raf.close();

		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	public ArrayList<Arquivo> getArquivos() {

		if (!mAberto) {
			abrir();
		}

		return mArquivos;

	}

	public ArrayList<Pasta> getPastas() {

		if (!mAberto) {
			abrir();
		}

		return mPastas;

	}

	
	public boolean existePasta(String eNome) {
		
		boolean ret = false;
		
		for (Pasta mPasta : getPastas()) {
			if (mPasta.getNome().contentEquals(eNome)) {
				ret=true;
				break;
			}
		}
		return ret;
	}
	
	public boolean existeArquivo(String eNome) {
		
		boolean ret = false;
		
		for (Arquivo mArquivo : getArquivos()) {
			if (mArquivo.getNome().contentEquals(eNome)) {
				ret=true;
				break;
			}
		}
		return ret;
	}
	
	
	public Pasta getPasta(String eNome) {
		
		Pasta ret = null;
		
		for (Pasta mPasta : getPastas()) {
			if (mPasta.getNome().contentEquals(eNome)) {
				ret=mPasta;
				break;
			}
		}
		return ret;
	}
	
	public Arquivo getArquivo(String eNome) {
		
		Arquivo ret = null;
		
		for (Arquivo mArquivo : getArquivos()) {
			if (mArquivo.getNome().contentEquals(eNome)) {
				ret=mArquivo;
				break;
			}
		}
		return ret;
	}
	
	public ArquivoImagem getArquivoImagem(String eNome) {
		return new ArquivoImagem(getArquivo(eNome));
	}
	
	
	public int getArquivosContagem() {
		int i = 0;
		
		i+=this.getArquivos().size();
		
		for (Pasta mPasta : this.getPastas()) {
			i+=getArquivosContagemInterno(mPasta);
		}
		
		return i;
	}

	
	public int getArquivosContagemInterno(Pasta ePasta) {
		int i = 0;
		
		i+=ePasta.getArquivos().size();
		
		for (Pasta mPasta : ePasta.getPastas()) {
			i+=getArquivosContagemInterno(mPasta);
		}
		
		return i;
	}
	
	
	public int getPastasContagem() {
		int i = 0;
		
		i+=this.getPastas().size();
		
		for (Pasta mPasta : this.getPastas()) {
			i+=getPastasContagemInterno(mPasta);
		}
		
		return i;
	}

	public int getPastasContagemInterno(Pasta ePasta) {
		int i = 0;
		
		i+=ePasta.getPastas().size();
		
		for (Pasta mPasta : ePasta.getPastas()) {
			i+=getPastasContagemInterno(mPasta);
		}
		
		return i;
	}
	
	
	public int getContagem() {
		int i = 0;
		
		i+=getArquivos().size();
		i+=this.getPastas().size();
		
		for (Pasta mPasta : this.getPastas()) {
			i+=getContagemInterno(mPasta);
		}
		
		return i;
	}
	
	public int getContagemInterno(Pasta ePasta) {
		int i = 0;
		
		i+=ePasta.getArquivos().size();
		i+=ePasta.getPastas().size();

		for (Pasta mPasta : ePasta.getPastas()) {
			i+=getContagemInterno(mPasta);
		}
		
		return i;
	}


	public ArrayList<Arquivo> getArquivosTodos() {

		if (!mAbertoTodos) {
			abrirTodos();
		}

		return mArquivosTodos;

	}

	public ArrayList<Pasta> getPastasTodos() {

		if (!mAbertoTodos) {
			abrirTodos();
		}

		return mPastasTodos;

	}

	private void abrirTodos() {

		mArquivosTodos.clear();
		mPastasTodos.clear();

		mAbertoTodos=true;

		for(Arquivo ma : getArquivos()){
			mArquivosTodos.add(ma);
		}

		for(Pasta ma : getPastas()){
			mPastasTodos.add(ma);
			abrirTodosInterno(ma);
		}

	}

	private void abrirTodosInterno(Pasta ePasta){

		for(Arquivo ma : ePasta.getArquivos()){
			mArquivosTodos.add(ma);
		}

		for(Pasta ma : ePasta.getPastas()){
			mPastasTodos.add(ma);
			abrirTodosInterno(ma);
		}

	}


}
