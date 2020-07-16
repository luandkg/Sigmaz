package OA;

import java.util.ArrayList;

public class Agrupador<T> {

    private ArrayList<Grupo<T>> mGrupos;

    public Agrupador (){

        mGrupos = new ArrayList<Grupo<T>>();
    }

    public ArrayList<Grupo<T>> getGrupos(){
        return mGrupos;
    }

    public Grupo<T> agrupar(String eNome) {

        boolean enc = false;
        Grupo<T> ret = null;

        for (Grupo<T> eGrupo : mGrupos) {

            if (eGrupo.getNome().contentEquals(eNome)) {

                ret = eGrupo;

                enc = true;
                break;
            }


        }

        if (!enc) {

            Grupo<T> eGrupo = new Grupo<T>(eNome);

            ret = eGrupo;

            mGrupos.add(eGrupo);

        }

        return ret;
    }


}
