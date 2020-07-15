package OA.LuanDKG;

import OA.Agrupador;

import java.util.ArrayList;

public class Organizador<T> {

    public  Agrupador<T> agrupar(String eNome, ArrayList<Agrupador<T>> mGrupos) {

        boolean enc = false;
        Agrupador<T> ret = null;

        for (Agrupador<T> eAgrupador : mGrupos) {

            if (eAgrupador.getNome().contentEquals(eNome)) {

                ret = eAgrupador;

                enc = true;
                break;
            }


        }

        if (!enc) {

            Agrupador<T> eGrupo = new Agrupador<T>(eNome);

            ret = eGrupo;

            mGrupos.add(eGrupo);

        }

        return ret;
    }

    public  void agruparVazio(String eNome, ArrayList<Agrupador<T>> mGrupos) {

        boolean enc = false;

        for (Agrupador<T> eAgrupador : mGrupos) {

            if (eAgrupador.getNome().contentEquals(eNome)) {

                enc = true;
                break;
            }


        }

        if (!enc) {

            Agrupador<T> eGrupo = new Agrupador<T>(eNome);

            mGrupos.add(eGrupo);

        }

    }

}
