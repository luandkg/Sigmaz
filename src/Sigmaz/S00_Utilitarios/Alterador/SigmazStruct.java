package Sigmaz.S00_Utilitarios.Alterador;

import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S05_PosProcessamento.Pronoco.Pronoco_Function;
import Sigmaz.S07_Executor.Indexador.Index_Function;

import java.util.ArrayList;

public class SigmazStruct {


    private AST mSigmazStruct_Leitura;
    private AST mSigmazStruct_Alteravel;

    public SigmazStruct(AST eSigmazStruct) {

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

    public boolean isGenerica() {
        boolean ret = false;

        if (mSigmazStruct_Leitura.getBranch("GENERIC").mesmoNome("TRUE")) {
            ret = true;
        }

        return ret;
    }

    public boolean isModelada() {
        boolean ret = false;

        if (mSigmazStruct_Leitura.getBranch("MODEL").mesmoValor("TRUE")) {
            ret = true;
        }

        return ret;
    }

    public String getModelo() {
        return mSigmazStruct_Leitura.getBranch("MODEL").getNome();
    }

    public String getCompletude() {
        String eForma = "COMPLETA";

        if (mSigmazStruct_Leitura.getBranch("WITH").mesmoValor("TRUE")) {
            eForma = "INCOMPLETA";
        }

        return eForma;
    }

    public boolean isCompleta() {
        return mSigmazStruct_Leitura.getBranch("WITH").mesmoValor("FALSE");
    }

    public boolean isIncompleta() {
        return mSigmazStruct_Leitura.getBranch("WITH").mesmoValor("TRUE");
    }

    public boolean precisaHeranca() {
        return mSigmazStruct_Leitura.getBranch("WITH").mesmoValor("TRUE");
    }

    public String getBase() {
        return mSigmazStruct_Leitura.getBranch("WITH").getNome();
    }


    public ArrayList<Pronoco_Function> getOperadores() {

        ArrayList<Pronoco_Function> mRet = new ArrayList<Pronoco_Function>();

        for (AST eAST : mSigmazStruct_Leitura.getBranch("BODY").getASTS()) {
            if (eAST.mesmoTipo("OPERATOR")) {
                mRet.add(new Pronoco_Function(eAST));
            }

        }


        return mRet;
    }

    public ArrayList<Pronoco_Function> getDiretores() {

        ArrayList<Pronoco_Function> mRet = new ArrayList<Pronoco_Function>();

        for (AST eAST : mSigmazStruct_Leitura.getBranch("BODY").getASTS()) {

            if (eAST.mesmoTipo("DIRECTOR")) {
                mRet.add(new Pronoco_Function(eAST));
            }
        }


        return mRet;
    }

}
