package Sigmaz.Analisador.ASTS;

import Sigmaz.Analisador.Analisador;

import java.util.ArrayList;
import Sigmaz.Utils.AST;

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
