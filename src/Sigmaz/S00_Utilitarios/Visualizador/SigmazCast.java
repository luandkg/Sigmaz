package Sigmaz.S00_Utilitarios.Visualizador;

import Sigmaz.S00_Utilitarios.AST;

import java.awt.*;
import java.util.ArrayList;

public class SigmazCast {

    private AST mAST;

    public SigmazCast(AST eAST) {

        mAST = eAST;

    }

    public String getNome() {
        return mAST.getNome();
    }

    public String getDefinicao() {
        return mAST.getNome();
    }

    public int getContagem() {

        int contador = 0;

        for (AST Sub2 : mAST.getASTS()) {
            if (Sub2.mesmoTipo("GETTER")) {
                contador += 1;
            } else if (Sub2.mesmoTipo("SETTER")) {
                contador += 1;
            }
        }


        return contador;
    }


    public ArrayList<SigmazGetter> getGetters() {

        ArrayList<SigmazGetter> mLista = new ArrayList<SigmazGetter>();

        for (AST Sub2 : mAST.getASTS()) {
            if (Sub2.mesmoTipo("GETTER")) {
                mLista.add(new SigmazGetter(this, Sub2));
            }
        }

        return mLista;
    }

    public ArrayList<SigmazSetter> getSetters() {

        ArrayList<SigmazSetter> mLista = new ArrayList<SigmazSetter>();

        for (AST Sub2 : mAST.getASTS()) {
            if (Sub2.mesmoTipo("SETTER")) {
                mLista.add(new SigmazSetter(this, Sub2));
            }
        }

        return mLista;
    }

}
