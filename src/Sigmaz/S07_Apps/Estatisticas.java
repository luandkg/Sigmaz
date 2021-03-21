package Sigmaz.S07_Apps;

import Sigmaz.S08_Utilitarios.ArquivoTeste;

public class Estatisticas {

    private boolean isPrimeiro;
    private boolean mIsVelocidades;

    private float mMaisRapido;
    private float mMaisDevagar;

    private String mLocalRapido;
    private String mLocalDevagar;

    public Estatisticas() {

        isPrimeiro = true;
        mIsVelocidades = false;

        mMaisRapido = 0;
        mMaisDevagar = 0;

        mLocalRapido = "";
        mLocalDevagar = "";


    }


    public boolean isVelocidades(){return mIsVelocidades;}
public String getLocalRapido(){return mLocalRapido;}
    public String getLocalDevagar(){return mLocalDevagar;}
    public float getTempoRapido(){return mMaisRapido;}
    public float getTempoDevagar(){return mMaisDevagar;}

    public void velocidades(ArquivoTeste mTeste, float eIntervalo) {

        if (isPrimeiro) {
            isPrimeiro = false;

            mMaisRapido = eIntervalo;
            mMaisDevagar = eIntervalo;

            mLocalRapido = mTeste.getArquivo();
            mLocalDevagar = mTeste.getArquivo();

        } else {

            mIsVelocidades = true;

            if (eIntervalo> mMaisDevagar) {
                mMaisDevagar =eIntervalo;
                mLocalDevagar = mTeste.getArquivo();
            }

            if (eIntervalo < mMaisRapido) {
                mMaisRapido = eIntervalo;
                mLocalRapido = mTeste.getArquivo();
            }

        }

    }
}
