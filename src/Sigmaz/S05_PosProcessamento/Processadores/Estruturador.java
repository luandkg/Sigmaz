package Sigmaz.S05_PosProcessamento.Processadores;

import java.util.ArrayList;

import Sigmaz.S00_Utilitarios.*;
import Sigmaz.S05_PosProcessamento.PosProcessador;
import Sigmaz.S05_PosProcessamento.ProcurandoStruct;
import Sigmaz.S05_PosProcessamento.Pronoco.Pronoco_Function;
import Sigmaz.S07_Executor.Alterador;

public class Estruturador {

    private PosProcessador mPosProcessador;

    private String CRIADO = "2020 09 24";
    private String ATUALIZADO = "2020 10 24";
    private String VERSAO = "1.0";

    public Estruturador(PosProcessador ePosProcessador) {

        mPosProcessador = ePosProcessador;

    }


    public void mensagem(String e) {
        if (mPosProcessador.getDebug_Estruturador()) {
            mPosProcessador.mensagem(e);
        }
    }

    public void errar(String eErro) {
        mPosProcessador.errar(eErro);
    }

    public boolean temErros() {
        return mPosProcessador.temErros();
    }

    public void init(ArrayList<AST> mTodos) {

        mensagem("");
        mensagem(" ------------------ FASE ESTRUTURADOR ----------------------- ");


        for (AST mAST : mTodos) {

            if (mAST.mesmoTipo("SIGMAZ")) {

                ArrayList<SigmazPackage> mPacotes = new ArrayList<SigmazPackage>();
                SigmazRaiz mSigmazRaiz = new SigmazRaiz(mAST);


                for (AST Struct_AST : mAST.getASTS()) {
                    if (Struct_AST.mesmoTipo("PACKAGE")) {
                        mPacotes.add(new SigmazPackage(Struct_AST));
                    }
                }


                processarSigmaz(mSigmazRaiz, mPacotes);

                for (SigmazPackage ePacote : mPacotes) {
                    processarPacote(mSigmazRaiz, ePacote, mPacotes);
                }

            }

        }


    }

    public void processarSigmaz(SigmazRaiz mSigmazRaiz, ArrayList<SigmazPackage> mPacotes) {


        mensagem("-->> SIGMAZ");
        mensagem("");

        mensagem("");


        for (SigmazStruct eStruct : mSigmazRaiz.getStructs()) {

            organizar(eStruct);

        }


    }


    public void processarPacote(SigmazRaiz mSigmazRaiz, SigmazPackage mSigmazPackage, ArrayList<SigmazPackage> mPacotes) {

        mensagem("-->> PACKAGE : " + mSigmazPackage.getNome());

        mensagem("");


        for (SigmazStruct eStruct : mSigmazPackage.getStructs()) {

            organizar(eStruct);


        }


    }

    public void organizar(SigmazStruct mStruct) {


        mensagem("\t - STRUCT : " + mStruct.getNome());

        String eGenerica = "Nao";

        if (mStruct.isGenerica()) {
            eGenerica = "Sim";
        }


        mensagem("\t\t Generica : " + eGenerica);

        int temOperador = 0;
        int temDiretor = 0;

        for (Pronoco_Function eDiretor : mStruct.getDiretores()) {
            mensagem("\t\t Diretor : " + eDiretor.getDefinicao());
            temOperador += 1;
        }

        for (Pronoco_Function eOperador : mStruct.getOperadores()) {
            mensagem("\t\t Operador : " + eOperador.getDefinicao());
            temDiretor += 1;
        }

        if (mStruct.isGenerica()) {

            if (temDiretor > 0) {
                errar("A Struct " + mStruct.getNome() + " e generica e nao pode ter diretores !");
            }

            if (temOperador > 0) {
                errar("A Struct " + mStruct.getNome() + " e generica e nao pode ter operadores !");
            }

        }


    }


}
