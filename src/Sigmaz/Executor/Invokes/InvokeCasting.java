package Sigmaz.Executor.Invokes;

import Sigmaz.Executor.Escopo;
import Sigmaz.Executor.Item;
import Sigmaz.Executor.RunTime;
import Sigmaz.Executor.Runners.Run_Invoke;
import Sigmaz.Utils.AST;

public class InvokeCasting {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private Run_Invoke mRun_Invoke;

    public InvokeCasting(RunTime eRunTime, Escopo eEscopo, Run_Invoke eRun_Invoke) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mRun_Invoke = eRun_Invoke;


    }

    public void init(String eAcao, String eSaida, AST ASTArgumentos) {


        if (eAcao.contentEquals("cast_ns")) {


            argumentos_1num(eAcao, eSaida, ASTArgumentos);

        } else if (eAcao.contentEquals("cast_bs")) {


            argumentos_1num(eAcao, eSaida, ASTArgumentos);
        } else if (eAcao.contentEquals("cast_sb")) {


            argumentos_1num(eAcao, eSaida, ASTArgumentos);
        } else if (eAcao.contentEquals("cast_fns")) {


            argumentos_2num(eAcao, eSaida, ASTArgumentos);


        } else if (eAcao.contentEquals("cast_integer")) {


            argumentos_1num(eAcao, eSaida, ASTArgumentos);
        } else if (eAcao.contentEquals("cast_decimal")) {


            argumentos_1num(eAcao, eSaida, ASTArgumentos);

        } else if (eAcao.contentEquals("cast_isnull")) {


            argumentos_1num(eAcao, eSaida, ASTArgumentos);

        } else if (eAcao.contentEquals("inverse")) {


            argumentos_1num(eAcao, eSaida, ASTArgumentos);


        } else if (eAcao.contentEquals("cast_type")) {

            mudartipo(eAcao, eSaida, ASTArgumentos);

        } else if (eAcao.contentEquals("typeof")) {

            typeof(eAcao, eSaida, ASTArgumentos);

        } else {

            mRunTime.getErros().add("Invocacao : Acao nao encontrada ->  " + eAcao);

        }

    }


    public void mudartipo(String eAcao, String eSaida, AST ASTArgumentos) {

        String a1 = mRun_Invoke.getString(ASTArgumentos, 1);
        String a2 = mRun_Invoke.getString(ASTArgumentos, 2);

        mEscopo.alterarTipo(eSaida, a1, a2);


    }

    public void typeof(String eAcao, String eSaida, AST ASTArgumentos) {

        String tipo = "";

        for (AST eAST : ASTArgumentos.getASTS()) {

            if (eAST.mesmoTipo("ARGUMENT")) {
                if (eAST.mesmoValor("ID")) {

                    if (eAST.mesmoNome("true") || eAST.mesmoNome("false")) {
                        tipo = "bool";

                    } else {
                        tipo = mEscopo.getDefinidoTipo(eAST.getNome());
                        if (tipo.contentEquals("<<ANY>>")) {
                            tipo = "null";
                        }
                    }


                } else if (eAST.mesmoValor("Num")) {
                    tipo = "num";
                } else if (eAST.mesmoValor("Text")) {
                    tipo = "string";
                    break;
                }


            }
        }


        mEscopo.setDefinido(eSaida, tipo);


    }


    public void argumentos_1num(String eAcao, String eSaida, AST ASTArgumentos) {


        int i = 0;

        String p1 = "";

        boolean isnull = false;

        for (AST eAST : ASTArgumentos.getASTS()) {

            if (eAST.mesmoTipo("ARGUMENT")) {

                //System.out.println(" \t - Argumento : " + eAST.getNome() + " : " + eAST.getValor());


                if (eAST.mesmoValor("ID")) {

                    if (eAST.mesmoNome("true") || eAST.mesmoNome("false")) {
                        isnull = false;
                    } else {

                        p1 = mEscopo.getDefinido(eAST.getNome());

                        isnull = mEscopo.getDefinidoNulo(eAST.getNome());

                    }


                    // System.out.println("Nulo :: " + isnull);


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
            } else if (eAcao.contentEquals("cast_integer")) {

                String s = String.valueOf(p1);
                String inteiro = "";

                int index = 0;
                int o = s.length();
                while (index < o) {
                    String v = s.charAt(index) + "";
                    if (v.contentEquals(".")) {
                        break;
                    } else {
                        inteiro += v;
                    }
                    index += 1;
                }


                mEscopo.setDefinido(eSaida, inteiro);
            } else if (eAcao.contentEquals("cast_decimal")) {

                String s = String.valueOf(p1);
                String inteiro = "";

                int index = 0;
                int o = s.length();
                while (index < o) {
                    String v = s.charAt(index) + "";
                    if (v.contentEquals(".")) {
                        index += 1;
                        break;
                    } else {

                    }
                    index += 1;
                }

                while (index < o) {
                    String v = s.charAt(index) + "";

                    inteiro += v;

                    index += 1;
                }
                if (inteiro.length() == 0) {
                    inteiro = "0";
                }


                mEscopo.setDefinido(eSaida, "0." + inteiro);

            } else if (eAcao.contentEquals("cast_isnull")) {

                if (isnull) {
                    mEscopo.setDefinido(eSaida, "true");
                } else {
                    mEscopo.setDefinido(eSaida, "false");
                }

            } else if (eAcao.contentEquals("inverse")) {

                String s = String.valueOf(p1);

                if (s.contentEquals("true")) {
                    mEscopo.setDefinido(eSaida, "false");
                } else {
                    mEscopo.setDefinido(eSaida, "true");
                }

            } else {

                mRunTime.getErros().add("Invocacao : Ação inconsistente ->  " + eAcao);

            }

        }
    }


    public void argumentos_2num(String eAcao, String eSaida, AST ASTArgumentos) {


        int i = 0;

        String p1 = "";
        String p2 = "";

        for (AST eAST : ASTArgumentos.getASTS()) {

            if (eAST.mesmoTipo("ARGUMENT")) {

                //System.out.println(" \t - Argumento : " + eAST.getNome() + " : " + eAST.getValor());


                if (eAST.mesmoValor("ID")) {

                    String eRet = mEscopo.getDefinidoNum(eAST.getNome());

                    if (i == 0) {
                        p1 = eRet;
                    } else {
                        p2 = eRet;
                    }


                } else if (eAST.mesmoValor("Num")) {
                    if (i == 0) {
                        p1 = eAST.getNome();
                    } else {
                        p2 = eAST.getNome();
                    }
                } else if (eAST.mesmoValor("Text")) {

                    mRunTime.getErros().add("Invocacao : Ação inconsistente ->  " + eAcao);

                    break;
                }

                i += 1;

            }

        }

        if (i == 2) {

            if (eAcao.contentEquals("cast_fns")) {

                try {
                    float f1 = Float.parseFloat(p1);
                    float f2 = Float.parseFloat(p2);

                    if (f2 < 0) {
                        mRunTime.getErros().add("Invocacao : Ação inconsistente ->  " + eAcao);
                        return;
                    }
                    if (f2 > 3) {
                        mRunTime.getErros().add("Invocacao : Ação inconsistente ->  " + eAcao);
                        return;
                    }

                    String f3 = String.valueOf(f1);


                    if (f2 == 0) {

                    }
                    if (f2 == 1) {
                        f3 = String.format("%.1f", f1);
                    }
                    if (f2 == 2) {
                        f3 = String.format("%.2f", f1);
                    }
                    if (f2 == 3) {
                        f3 = String.format("%.3f", f1);
                    }

                    f3 = f3.replace(",", ".");

                    mEscopo.setDefinido(eSaida, String.valueOf(f3));
                } catch (Exception e) {
                    mRunTime.getErros().add("Invocacao : Ação inconsistente ->  " + eAcao);
                }


            } else {

                mRunTime.getErros().add("Invocacao : Ação inconsistente ->  " + eAcao);

            }

        }
    }
}


