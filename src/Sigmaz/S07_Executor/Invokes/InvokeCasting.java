package Sigmaz.S07_Executor.Invokes;

import Sigmaz.S07_Executor.Escopo;
import Sigmaz.S07_Executor.Item;
import Sigmaz.S07_Executor.RunTime;
import Sigmaz.S07_Executor.Runners.Run_Invoke;
import Sigmaz.S00_Utilitarios.AST;

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


        } else if (eAcao.contentEquals("num_to_string")) {

            String p1 = mRun_Invoke.getNum(ASTArgumentos, 1);
            mEscopo.setDefinido(eSaida, p1);

        } else if (eAcao.contentEquals("int_to_string")) {

            String p1 = mRun_Invoke.getInt(ASTArgumentos, 1);
            mEscopo.setDefinido(eSaida, p1);


        } else if (eAcao.contentEquals("bool_to_string")) {

            String p1 = mRun_Invoke.getBool(ASTArgumentos, 1);
            mEscopo.setDefinido(eSaida, p1);
        } else if (eAcao.contentEquals("int_to_string")) {

            String p1 = mRun_Invoke.getInt(ASTArgumentos, 1);
            mEscopo.setDefinido(eSaida, p1);


        } else if (eAcao.contentEquals("cast_integer_num")) {

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
        } else if (eAcao.contentEquals("cast_integer_int")) {

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
        } else if (eAcao.contentEquals("cast_decimal_num")) {

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
        } else if (eAcao.contentEquals("cast_decimal_int")) {

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

            // System.out.println("VERIFICANDO NULIDADE :: " + p1 + " ERROS : " + mRunTime.getErros().size());

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

        } else if (eAcao.contentEquals("typeof")) {
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

        } else if (eAcao.contentEquals("cast_datatype")) {

            String e1 = mRun_Invoke.getArgumentoNome(ASTArgumentos, 0);
            Item eItem = mEscopo.getItem(eSaida);

            if (eItem.getPrimitivo() == true) {

                //  String e2 = eItem.getTipo();
                //  System.out.println("DataType :: " + e2 + " -->> " + e1);

                eItem.setTipo(e1);

            } else {

                mRunTime.getErros().add("Invocacao : Alteracao de datatype so pode ocorrer em primitivos e cast ");

            }

        } else if (eAcao.contentEquals("move_content")) {

            String e1 = mRun_Invoke.getArgumentoNome(ASTArgumentos, 0);

            Item mEntrada = mEscopo.getItem(e1);
            Item mSaida = mEscopo.getItem(eSaida);

          //  System.out.println("Entrada : " + mEntrada.getNome() + " : " + mEntrada.getTipo() + " -->> PRIMITIVO : " + mEntrada.getPrimitivo());
       //     System.out.println("Saida : " + mSaida.getNome() + " : " + mSaida.getTipo() + " -->> PRIMITIVO : " + mSaida.getPrimitivo());

            if (mEntrada.getPrimitivo()) {

                if (mSaida.getPrimitivo()) {

                    mSaida.setNulo(mEntrada.getNulo());
                    mSaida.setValor(mEntrada.getValor(mRunTime,mEscopo),mRunTime,mEscopo);


                }else{
                    mRunTime.getErros().add("Invocacao : Move_Content -> A saida deve ser do tipo PRIMITIVO ou CAST !" );

                }
            }else{

                mRunTime.getErros().add("Invocacao : Move_Content -> A entrada deve ser do tipo PRIMITIVO ou CAST !" );

            }

        } else {

            mRunTime.getErros().add("Invocacao : Ação inconsistente ->  " + eAcao);

        }

    }


    public void argumentos_2num(String eAcao, String eSaida, AST ASTArgumentos) {


        if (eAcao.contentEquals("cast_fns")) {

            try {
                float f1 = Float.parseFloat(mRun_Invoke.getNum(ASTArgumentos, 1));
                float f2 = Float.parseFloat(mRun_Invoke.getNum(ASTArgumentos, 2));

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


