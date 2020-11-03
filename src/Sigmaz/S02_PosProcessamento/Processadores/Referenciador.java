package Sigmaz.S02_PosProcessamento.Processadores;

import java.util.ArrayList;

import Sigmaz.S02_PosProcessamento.PosProcessador;
import Sigmaz.S00_Utilitarios.AST;

public class Referenciador {

    private PosProcessador mPosProcessador;

    public Referenciador(PosProcessador ePosProcessador) {

        mPosProcessador = ePosProcessador;

    }

    public void mensagem(String e) {
        if (mPosProcessador.getDebug_Referenciador()) {
            mPosProcessador.mensagem(e);
        }
    }

    public void errar(String e) {
        mPosProcessador.errar(e);
    }


    public void init(ArrayList<AST> mTodos) {


       mensagem("");
        mensagem(" ------------------ FASE REFERENCIADOR ----------------------- ");
       mensagem("");


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


        for (AST mPacote : mPacotes) {

           mensagem("Analisando Pacote " + mPacote.getNome());

            ArrayList<AST> mEstruturas = new ArrayList<AST>();
            ArrayList<AST> mRefers = new ArrayList<AST>();

            for (AST Struct_AST : mPacote.getASTS()) {
                if (Struct_AST.mesmoTipo("STRUCT")) {
                    mEstruturas.add(Struct_AST);
                } else if (Struct_AST.mesmoTipo("REFER")) {
                    mRefers.add(Struct_AST);
                }
            }

          mensagem("\t -  REFERS : ");
            for (AST mRefer : mRefers) {
              mensagem("\t\t -  REFER : " + mRefer.getNome());
            }
            mensagem("\t -  STRUCTS : ");
            for (AST mStruct : mEstruturas) {

                mStruct.getBranch("REFERS").criarBranch("BY").setNome(mPacote.getNome());

                for (AST mRefer : mRefers) {
                    mStruct.getBranch("REFERS").criarBranch("BY").setNome(mRefer.getNome());
                }

               mensagem("\t\t -  STRUCT : " + mStruct.getNome());



                for (AST mRefer : mStruct.getBranch("REFERS").getASTS()) {
                 mensagem("\t\t\t -  BY : " + mRefer.getNome());
                }
            }

        }

      mensagem("Analisando Global " );


      mensagem("\t -  REFERS : ");
        for (AST mRefer : mGlobalRefer) {
            mensagem("\t\t -  REFER : " + mRefer.getNome());
        }
        mensagem("\t -  STRUCTS : ");
        for (AST mStruct : mGlobalStruct) {

            for (AST mRefer : mGlobalRefer) {
                mStruct.getBranch("REFERS").criarBranch("BY").setNome(mRefer.getNome());
            }

          mensagem("\t\t -  STRUCT : " + mStruct.getNome());




            for (AST mRefer : mStruct.getBranch("REFERS").getASTS()) {
             mensagem("\t\t\t -  BY : " + mRefer.getNome());
            }
        }


    }
}
