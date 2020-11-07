package OA.Utils;

import java.util.ArrayList;

public class EmTrila {

    private ArrayList<Grupo<String>> mGrupos;
    private int mAcima;
    private int mAbaixo;
    private boolean mIntercalador;

    public EmTrila() {

        mAcima = 0;
        mAbaixo = 0;
        mIntercalador=true;
        mGrupos = new ArrayList<Grupo<String>>();

    }

    public boolean getIntercalador(){
        return mIntercalador;
    }

    public void calcular(boolean eIntercalador) {

        mAcima = 0;
        mAbaixo = 0;
        mIntercalador=eIntercalador;

        for (Grupo<String> mGrupo : mGrupos) {

            int alturando = 0;


            for (String mObjeto : mGrupo.getObjetos()) {
                alturando += 1;
            }

            if (eIntercalador) {

                if (alturando > mAbaixo) {
                    mAbaixo = alturando;
                }

                eIntercalador = false;
            } else {

                if (alturando > mAcima) {
                    mAcima = alturando;
                }

                eIntercalador = true;
            }

        }
    }

    public ArrayList<Grupo<String>> getGrupos() {
        return mGrupos;
    }


    public int getAcima() {
        return mAcima;
    }

    public int getAbaixo() {
        return mAbaixo;
    }


    public void adicionar(Grupo<String> eGrupo) {

        mGrupos.add(eGrupo);


    }

}
