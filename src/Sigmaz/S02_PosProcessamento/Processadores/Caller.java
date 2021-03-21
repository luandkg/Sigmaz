package Sigmaz.S02_PosProcessamento.Processadores;

import Sigmaz.S08_Utilitarios.AST;
import Sigmaz.S02_PosProcessamento.PosProcessador;

import java.util.ArrayList;

public class Caller {

    private PosProcessador mPosProcessador;

    public Caller(PosProcessador ePosProcessador) {
        mPosProcessador = ePosProcessador;
    }


    public void mensagem(String e) {
        mPosProcessador.mensagem(e);
    }

    private void errar(String eErro) {
        mPosProcessador.getErros().add(eErro);
    }


    public void init(ArrayList<AST> mTodos) {

        for (AST eAST : mTodos) {
            if (eAST.mesmoTipo("SIGMAZ")) {

                if (eAST.mesmoNome("EXECUTABLE")) {

                    int mCalls = 0;

                    for (AST oAST : eAST.getASTS()) {
                        if (oAST.mesmoTipo("CALL")) {
                            mCalls += 1;
                        }
                    }

                    if (mCalls == 0) {
                        errar("O Executavel precisa ter um chamador : CALL");
                    } else if (mCalls > 1) {
                        errar("O Executavel so pode ter um unico chamador : CALL");
                    }

                } else if (eAST.mesmoNome("LIBRARY")) {


                    int mCalls = 0;

                    for (AST oAST : eAST.getASTS()) {
                        if (oAST.mesmoTipo("CALL")) {
                            mCalls += 1;
                        }
                    }

                    if (mCalls > 0) {
                        errar("A Biblioteca nao pode ter chamadores : CALL");
                    }

                }


            }
        }

    }
}
