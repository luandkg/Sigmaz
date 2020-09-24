package Sigmaz;

import Sigmaz.PosProcessamento.Cabecalho;
import java.util.ArrayList;


// COMPILADOR SIGMAZ

// DESENVOLVEDOR : LUAN FREITAS


public class Sigmaz {

    private boolean mObject;
    private boolean mPosProcess;
    private boolean mStackProcess;
    private boolean mAnaliseProcess;


    private Cabecalho mCabecalho;

    public Sigmaz() {

        mObject = true;
        mPosProcess = true;
        mStackProcess = true;
        mAnaliseProcess = true;


        mCabecalho = new Cabecalho();

    }

    public void setCabecalho(Cabecalho eCabecalho) {
        mCabecalho = eCabecalho;
    }

    public Cabecalho getCabecalho() {
        return mCabecalho;
    }

    public void setObject(boolean e) {
        mObject = e;
    }

    public boolean getObject() {
        return mObject;
    }

    public void setPosProcess(boolean e) {
        mPosProcess = e;
    }

    public boolean getPosProcess() {
        return mPosProcess;
    }

    public void setStackProcess(boolean e) {
        mStackProcess = e;
    }

    public boolean getStackProcess() {
        return mStackProcess;
    }


    public void setAnalysisProcess(boolean e) {
        mAnaliseProcess = e;
    }

    public boolean getAnalysisProcess() {
        return mAnaliseProcess;
    }


    public void compilar_executavel(String eArquivo, String saida) {

        Sigmaz_CS eSigmazCS = new Sigmaz_CS(this);

        if (eSigmazCS.geral(eArquivo, saida, 1)) {

        }

    }

    public void compilar_biblioteca(String eArquivo, String saida) {

        Sigmaz_CS eSigmazCS = new Sigmaz_CS(this);

        if (eSigmazCS.geral(eArquivo, saida, 2)) {

        }

    }

    public void init(String eArquivo, String saida, int mOpcao) {

        Sigmaz_CS eSigmazCS = new Sigmaz_CS(this);

        if (eSigmazCS.geral(eArquivo, saida, mOpcao)) {

            Sigmaz_Executor mSigmaz_Executor = new Sigmaz_Executor();
            mSigmaz_Executor.executar(eArquivo, saida);

        }

    }

    public void estrutura(String eArquivo, String saida, int mOpcao) {

        Sigmaz_CS eSigmazCS = new Sigmaz_CS(this);

        if (eSigmazCS.geral(eArquivo, saida, mOpcao)) {

            Sigmaz_Executor mSigmaz_Executor = new Sigmaz_Executor();
            mSigmaz_Executor.estruturador(eArquivo, saida);

        }

    }


    public void init_simples(String eArquivo, String saida, int mOpcao) {

        Sigmaz_CR eSigmaz = new Sigmaz_CR(this);

        eSigmaz.geral_simples(eArquivo, saida, mOpcao);

        if (!eSigmaz.temErros()) {

            Sigmaz_Executor mSigmaz_Executor = new Sigmaz_Executor();
            mSigmaz_Executor.executar(eArquivo, saida);

        } else {

            System.out.println("");
            System.out.println("################ SIGMAZ ################");
            System.out.println("");
            System.out.println("\t - Source : " + eArquivo);
            System.out.println("\t - Status : PROBLEMA");
            System.out.println("\t - Fase : " + eSigmaz.getLocalErro());

            System.out.println("----------------------------------------------");
            System.out.println("");

            for (String eMensagem : eSigmaz.getMensagens()) {
                System.out.println(eMensagem);
            }

            System.out.println("");
            System.out.println("----------------------------------------------");

        }

    }

    public void geral_simples(String eArquivo, String saida, int mOpcao) {


        Sigmaz_CR eSigmaz = new Sigmaz_CR(this);

        eSigmaz.geral_simples(eArquivo, saida, mOpcao);

    }

    public void geralvarios_simples(ArrayList<String> eArquivos, String saida, int mOpcao) {

        Sigmaz_CR eSigmaz = new Sigmaz_CR(this);

        eSigmaz.geralvarios_simples(eArquivos, saida, mOpcao);

    }

}
