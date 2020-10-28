package Sigmaz.S00_Utilitarios.Alterador;

import Sigmaz.S00_Utilitarios.AST;

public class SigmazType {

    private AST mSigmazStruct_Leitura;
    private AST mSigmazStruct_Alteravel;

    public SigmazType(AST eSigmazStruct) {

        mSigmazStruct_Leitura = eSigmazStruct.copiar();
        mSigmazStruct_Alteravel = eSigmazStruct;

    }


    public AST getLeitura() {
        return mSigmazStruct_Leitura;
    }

    public AST getAlteravel() {
        return mSigmazStruct_Alteravel;
    }

    public boolean mesmoNome(String eNome) {
        return getNome().contentEquals(eNome);
    }


    public String getNome() {
        return mSigmazStruct_Leitura.getNome();
    }

    public String getCompletude() {

        String eForma = "COMPLETA";

        if (mSigmazStruct_Leitura.getBranch("EXTENDED").mesmoNome("TYPE_UNION")) {
            eForma = "INCOMPLETA";
        }

        return eForma;
    }

    public boolean precisaUnir() {

        boolean ePrecisa = false;

        if (mSigmazStruct_Leitura.getBranch("EXTENDED").mesmoNome("TYPE_UNION")) {
            ePrecisa = true;
        }

        return ePrecisa;
    }

}
