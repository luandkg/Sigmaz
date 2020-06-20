package Sigmaz.Executor.Indexador;

import Sigmaz.Executor.Item;
import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Argumentador {

    public boolean mesmoArgumentos(ArrayList<String> mTipoArgumentos,ArrayList<Item> eArgumentos) {
        boolean ret = false;

      //  System.out.println("\t - Inicio da Checagem :  " + mTipoArgumentos.size() + " e " + eArgumentos.size());


        if (eArgumentos.size() == mTipoArgumentos.size()) {
            int i = 0;
            int v = 0;
            for (Item mArgumentos : eArgumentos) {



                if(mArgumentos.getPrimitivo()){

                    if (mArgumentos.getTipo().contentEquals(mTipoArgumentos.get(i))){

                        v += 1;
                    }

                } else if (mArgumentos.getIsEstrutura()){

                    v += 1;


                }

              //  System.out.println("\t - Checando Tipo :  " + mArgumentos.getTipo() + " e " + mTipoArgumentos.get(i));

                i += 1;


            }


            if (v == i) {
                ret=true;
            }

            //  System.out.println("\t - Contagem Tipo :  " + i + " e " + v + " -> " + ret);

        }

        return ret;
    }

}
