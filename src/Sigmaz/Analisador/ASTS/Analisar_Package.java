package Sigmaz.Analisador.ASTS;

import Sigmaz.Analisador.Analisador;
import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Analisar_Package {

    private Analisador mAnalisador;
    private ArrayList<AST> mPackages;

    public Analisar_Package(Analisador eAnalisador) {

        mAnalisador = eAnalisador;

        mPackages = new ArrayList<AST>();

    }

    public ArrayList<AST> getPackages() {
        return mPackages;
    }




}
