package Sigmaz.S08_Utilitarios;

public class Chronos_Intervalo {

    private long mInicio;
    private long mFim;

    public Chronos_Intervalo(){

        mInicio=0;
        mFim=0;

    }

    public void marqueInicio(){
        mInicio=System.currentTimeMillis();
    }

    public void marqueFim(){
        mFim=System.currentTimeMillis();
    }

    public long getInicio(){
        return mInicio;
    }
    public long getFim(){
        return mFim;
    }

    public float getIntervalo() {

        return (getFim() - getInicio()) / 1000F;

    }

}
