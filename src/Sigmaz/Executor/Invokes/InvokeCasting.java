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

        int i = mRun_Invoke.argumentosContagem(ASTArgumentos);
        if (i == 1) {

            argumentos_1num(eAcao, eSaida, ASTArgumentos);

        } else if (i == 2) {

            argumentos_2num(eAcao, eSaida, ASTArgumentos);

        } else {

            mRunTime.getErros().add("Invocacao : Acao nao encontrada ->  " + eAcao);

        }


    }






    public void argumentos_1num(String eAcao, String eSaida, AST ASTArgumentos) {


        if (eAcao.contentEquals("print")) {

            String p1 = mRun_Invoke.getQualquer(ASTArgumentos, 1);
            if (mRunTime.getErros().size() > 0) {
                return;
            }
            System.out.print(p1);

            mEscopo.setDefinido(eSaida, "true");


        } else if (eAcao.contentEquals("cast_integer_num") ) {

            String p1 = mRun_Invoke.getNum(ASTArgumentos, 1);
            if (mRunTime.getErros().size() > 0) {
                return;
            }

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


            mEscopo.setDefinido(eSaida, inteiro + ".0");
        } else if (eAcao.contentEquals("cast_integer_int") ) {

            String p1 = mRun_Invoke.getInt(ASTArgumentos, 1);
            if (mRunTime.getErros().size() > 0) {
                return;
            }

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
        } else if (eAcao.contentEquals("cast_decimal_num") ) {

            String p1 = mRun_Invoke.getNum(ASTArgumentos, 1);
            if (mRunTime.getErros().size() > 0) {
                return;
            }
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
        } else if (eAcao.contentEquals("cast_decimal_int") ) {

            String p1 = mRun_Invoke.getInt(ASTArgumentos, 1);
            if (mRunTime.getErros().size() > 0) {
                return;
            }
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

            boolean p1 = mRun_Invoke.isNulo(ASTArgumentos, 1);
            if (mRunTime.getErros().size() > 0) {
                return;
            }

            if (p1) {
                mEscopo.setDefinido(eSaida, "true");
            } else {
                mEscopo.setDefinido(eSaida, "false");
            }

        } else if (eAcao.contentEquals("inverse")) {

            String p1 = mRun_Invoke.getBool(ASTArgumentos, 1);
            if (mRunTime.getErros().size() > 0) {
                return;
            }

            if (p1.contentEquals("true")) {
                mEscopo.setDefinido(eSaida, "false");
            } else {
                mEscopo.setDefinido(eSaida, "true");
            }

        } else if (eAcao.contentEquals("typeof") ) {
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
                        tipo = "int";
                        break;
                    } else if (eAST.mesmoValor("Text")) {
                        tipo = "string";
                        break;
                    } else if (eAST.mesmoValor("Float")) {
                        tipo = "num";
                        break;
                    }


                }
            }


            mEscopo.setDefinido(eSaida, tipo);
        } else {

            mRunTime.getErros().add("Invocacao : Ação inconsistente ->  " + eAcao);

        }

    }


    public void argumentos_2num(String eAcao, String eSaida, AST ASTArgumentos) {




            if (eAcao.contentEquals("cast_fns")) {

                try {
                    float f1 = Float.parseFloat(mRun_Invoke.getNum(ASTArgumentos,1));
                    float f2 = Float.parseFloat(mRun_Invoke.getNum(ASTArgumentos,2));

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
            } else if (eAcao.contentEquals("cast_type")) {

                String a1 = mRun_Invoke.getString(ASTArgumentos, 1);
                String a2 = mRun_Invoke.getString(ASTArgumentos, 2);

                mEscopo.alterarTipo(eSaida, a1, a2);


            } else {

                mRunTime.getErros().add("Invocacao : Ação inconsistente ->  " + eAcao);

            }

        }

}


