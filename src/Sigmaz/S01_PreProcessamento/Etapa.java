package Sigmaz.S01_PreProcessamento;

import java.util.ArrayList;

public class Etapa {

    private int mEtapaID;

    private String mCorrente;

    private ArrayList<String> mAdicionar;
    private ArrayList<String> mJaEsperando;
    private ArrayList<String> mConcluido;
    private ArrayList<String> mJaProcessadoo;

    public Etapa(int eEtapaID,String eCorrente){

        mEtapaID=eEtapaID;
        mCorrente=eCorrente;

        mAdicionar=new ArrayList<String>();
        mJaEsperando=new ArrayList<String>();
        mConcluido=new ArrayList<String>();
        mJaProcessadoo=new ArrayList<String>();

    }


    public int getEtapaID(){return mEtapaID;}
    public String getCorrente(){return mCorrente;}

    public ArrayList<String> getAdicionar(){return mAdicionar;}

    public ArrayList<String> getJaProcessado(){return mJaProcessadoo;}
    public ArrayList<String> getJaEsperando(){return mJaEsperando;}


    public void adicionar(String e){
        mAdicionar.add(e);
    }
    public void adicionarJaProcessado(String e){
        mJaProcessadoo.add(e);
    }
    public void adicionarJaEsperando(String e){
        mJaEsperando.add(e);
    }
}
