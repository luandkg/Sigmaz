package Sigmaz.S05_PosProcessamento.Processadores;

import java.util.ArrayList;

import Sigmaz.S05_PosProcessamento.PosProcessador;
import Sigmaz.S00_Utilitarios.AST;

public class Referenciador {

    private PosProcessador mAnalisador;

    public Referenciador(PosProcessador eAnalisador) {

        mAnalisador = eAnalisador;

    }


    public void init(ArrayList<AST> mTodos) {


        mAnalisador.mensagem("");
        mAnalisador.mensagem(" ------------------ FASE REFERENCIADOR ----------------------- ");
        mAnalisador.mensagem("");


        ArrayList<AST> mPacotes = new ArrayList<AST>();
        ArrayList<AST> mGlobalStruct = new ArrayList<AST>();
        ArrayList<AST> mGlobalRefer = new ArrayList<AST>();

        for (AST mSigmaz : mTodos) {

            if (mSigmaz.mesmoTipo("SIGMAZ")) {

                for (AST Struct_AST : mSigmaz.getASTS()) {
                    if (Struct_AST.mesmoTipo("PACKAGE")) {
                        mPacotes.add(Struct_AST);
                    } else  if (Struct_AST.mesmoTipo("STRUCT")) {
                        mGlobalStruct.add(Struct_AST);
                    } else  if (Struct_AST.mesmoTipo("REFER")) {
                        mGlobalRefer.add(Struct_AST);
                    }
                }

            }

        }

      //  mAnalisador.getErros().add("Referenciador");

        for (AST mPacote : mPacotes) {

            mAnalisador.mensagem("Analisando Pacote " + mPacote.getNome());

            ArrayList<AST> mEstruturas = new ArrayList<AST>();
            ArrayList<AST> mRefers = new ArrayList<AST>();

            for (AST Struct_AST : mPacote.getASTS()) {
                if (Struct_AST.mesmoTipo("STRUCT")) {
                    mEstruturas.add(Struct_AST);
                } else if (Struct_AST.mesmoTipo("REFER")) {
                    mRefers.add(Struct_AST);
                }
            }

            mAnalisador.mensagem("\t -  REFERS : ");
            for (AST mRefer : mRefers) {
                mAnalisador.mensagem("\t\t -  REFER : " + mRefer.getNome());
            }
            mAnalisador.mensagem("\t -  STRUCTS : ");
            for (AST mStruct : mEstruturas) {

                mStruct.getBranch("REFERS").criarBranch("BY").setNome(mPacote.getNome());

                for (AST mRefer : mRefers) {
                    mStruct.getBranch("REFERS").criarBranch("BY").setNome(mRefer.getNome());
                }

                mAnalisador.mensagem("\t\t -  STRUCT : " + mStruct.getNome());



                for (AST mRefer : mStruct.getBranch("REFERS").getASTS()) {
                    mAnalisador.mensagem("\t\t\t -  BY : " + mRefer.getNome());
                }
            }

        }

        mAnalisador.mensagem("Analisando Global " );


        mAnalisador.mensagem("\t -  REFERS : ");
        for (AST mRefer : mGlobalRefer) {
            mAnalisador.mensagem("\t\t -  REFER : " + mRefer.getNome());
        }
        mAnalisador.mensagem("\t -  STRUCTS : ");
        for (AST mStruct : mGlobalStruct) {

            for (AST mRefer : mGlobalRefer) {
                mStruct.getBranch("REFERS").criarBranch("BY").setNome(mRefer.getNome());
            }

            mAnalisador.mensagem("\t\t -  STRUCT : " + mStruct.getNome());




            for (AST mRefer : mStruct.getBranch("REFERS").getASTS()) {
                mAnalisador.mensagem("\t\t\t -  BY : " + mRefer.getNome());
            }
        }


    }
}
