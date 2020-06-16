package Sigmaz.Executor.Invokes;

import Sigmaz.Executor.Escopo;
import Sigmaz.Executor.RunTime;
import Sigmaz.Utils.AST;

import java.text.DecimalFormat;

public class InvokeTerminal {

    private RunTime mRunTime;
    private Escopo mEscopo;


    public InvokeTerminal(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;


    }

    public void init(String eAcao, String eSaida, AST ASTArgumentos) {


        if (eAcao.contentEquals("change")) {

            argumentos_vazio(eAcao, eSaida, ASTArgumentos);

        } else if (eAcao.contentEquals("print")) {

            argumentos_1num(eAcao, eSaida, ASTArgumentos);

        } else {

            mRunTime.getErros().add("Invocacao : Acao nao encontrada ->  " + eAcao);

        }

    }

    public void argumentos_1num(String eAcao, String eSaida, AST ASTArgumentos) {


        int i = 0;

        String p1 = "";

        for (AST eAST : ASTArgumentos.getASTS()) {

            if (eAST.mesmoTipo("ARGUMENT")) {

                //System.out.println(" \t - Argumento : " + eAST.getNome() + " : " + eAST.getValor());


                if (eAST.mesmoValor("ID")) {

                    p1 = mEscopo.getDefinido(eAST.getNome());


                } else if (eAST.mesmoValor("Num")) {
                    p1 = eAST.getNome();
                } else if (eAST.mesmoValor("Text")) {

                    mRunTime.getErros().add("Invocacao : Ação inconsistente ->  " + eAcao);

                    break;
                }

                i += 1;

            }

        }

        if (i == 1) {

            if (eAcao.contentEquals("print")) {


                System.out.print(p1);

                mEscopo.setDefinido(eSaida, "true");

            } else if (eAcao.contentEquals("cast_ns")) {

                String s = String.valueOf(p1);

                mEscopo.setDefinido(eSaida, s);

            } else if (eAcao.contentEquals("cast_bs")) {

                String s = String.valueOf(p1);


                mEscopo.setDefinido(eSaida, s);

            } else if (eAcao.contentEquals("cast_sb")) {

                String s = String.valueOf(p1);

                if (s.contentEquals("true") || s.contentEquals("false")) {
                    mEscopo.setDefinido(eSaida, s);
                } else {
                    mRunTime.getErros().add("Conversao impossivel !");
                }


            } else {

                mRunTime.getErros().add("Invocacao : Ação inconsistente ->  " + eAcao);

            }

        }
    }

    public void argumentos_vazio(String eAcao, String eSaida, AST ASTArgumentos) {


        int i = 0;

        for (AST eAST : ASTArgumentos.getASTS()) {

            if (eAST.mesmoTipo("ARGUMENT")) {
                i += 1;
            }

        }

        if (i == 0) {

            if (eAcao.contentEquals("change")) {


                System.out.print("\n");

                mEscopo.setDefinido(eSaida, "true");


            } else {

                mRunTime.getErros().add("Invocacao : Ação inconsistente ->  " + eAcao);

            }

        }
    }


}


