package Container;

public class Ponto {

	private String mNome;

	private long mTipo;
	private long mInicio;
	private long mFim;
	
	public Ponto(String eNome,long eTipo,long eInicio,long eFim) {
		
		mNome=eNome;
		
		mTipo=eTipo;
		mInicio=eInicio;
		mFim=eFim;

	}

	public Ponto(long eTipo,long eInicio,long eFim) {

		mNome="";

		mTipo=eTipo;
		mInicio=eInicio;
		mFim=eFim;

	}


	public String getNome() {return mNome;}
	public long getTipo() {return mTipo;}
	public long getInicio() {return mInicio;}
	public long getFim() {return mFim;}

}
