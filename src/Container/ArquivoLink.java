package Container;


public class ArquivoLink {

    private String mNome;
    private String mONome;

    private Container mContainer;
    private Arquivo mArquivo;
    private boolean mIndexado;

    private long mInicio;
    private long mFim;

    public ArquivoLink(Container eContainer, String eNome, String eoNome, long eInicio, long eFim) {

        mNome = eNome;
        mONome=eoNome;
        mInicio = eInicio;
        mFim = eFim;

        mContainer = eContainer;
        mArquivo = null;
        mIndexado = false;
    }


    public String getNome() {
        return mNome;
    }

    public Arquivo getArquivo() {

        if (!mIndexado) {
            mIndexado = true;
            abrir();
        }

        return mArquivo;
    }

    private void abrir() {

        mArquivo = new Arquivo(mContainer,new Ponto(mONome,12,mInicio,mFim));



    }


}
