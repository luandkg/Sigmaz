package Sigmaz.S06_Montador;



public class OLM {

    public static final String CRIADO = "2020 10 10";

    public static final String Titulo = "OLM";
    public static final int Versao = 1;


    public static OLMCabecalho criar(String eArquivo, byte[] mCabecalho, byte[] mCodigo,byte[] mAssinatura) {

        OLMCabecalho mOLMCabecalho = new OLMCabecalho();

        Arquivador mArquivador = new Arquivador();

        mArquivador.limpar(eArquivo);

        // OLM -> Cabececalho + Sigmaz + Codigo

        mArquivador.marcar(Titulo, Versao, eArquivo);

        long eSetorSigmaz_Inicio = mArquivador.criarSetor(eArquivo);
        long eSetorSigmaz_Tamanho = mArquivador.criarSetor(eArquivo);

        long eSetorDados_Inicio = mArquivador.criarSetor(eArquivo);
        long eSetorDados_Tamanho = mArquivador.criarSetor(eArquivo);

        long eSetorAssinatura_Inicio = mArquivador.criarSetor(eArquivo);
        long eSetorAssinatura_Tamanho = mArquivador.criarSetor(eArquivo);


        long mSetorCab = mArquivador.guardarSetor(mCabecalho, eArquivo);
        long mSetorDados = mArquivador.guardarSetor(mCodigo, eArquivo);
        long mSetorAssinatura = mArquivador.guardarSetor(mAssinatura, eArquivo);


        mArquivador.marcarLocal(eSetorSigmaz_Inicio, mSetorCab, eArquivo);
        mArquivador.marcarLocal(eSetorDados_Inicio, mSetorDados, eArquivo);
        mArquivador.marcarLocal(eSetorAssinatura_Inicio, mSetorAssinatura, eArquivo);



        mArquivador.marcarLocal(eSetorSigmaz_Tamanho, mCabecalho.length, eArquivo);
        mArquivador.marcarLocal(eSetorDados_Tamanho,  mCodigo.length, eArquivo);
        mArquivador.marcarLocal(eSetorAssinatura_Tamanho,  mAssinatura.length, eArquivo);



        mOLMCabecalho.definir(Titulo,Versao,mSetorCab,mCabecalho.length,mSetorDados,mCodigo.length,mSetorAssinatura, mAssinatura.length);


        return mOLMCabecalho;
    }


    public OLMCabecalho lerCabecalho(String eArquivo) {

        OLMCabecalho mOLMCabecalho = new OLMCabecalho();
        mOLMCabecalho.ler(eArquivo);


        return mOLMCabecalho;
    }


}
