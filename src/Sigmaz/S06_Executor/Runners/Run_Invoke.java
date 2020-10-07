package Sigmaz.S06_Executor.Runners;

import Sigmaz.S06_Executor.Escopo;
import Sigmaz.S06_Executor.Invokes.*;
import Sigmaz.S06_Executor.RunTime;
import Sigmaz.S00_Utilitarios.AST;

public class Run_Invoke {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private String mLocal;

    public Run_Invoke(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mLocal = "Run_Invoke";

    }


    public void init(AST ASTCorrente) {

        AST mSaida = ASTCorrente.getBranch("EXIT");
        AST mArgumentos = ASTCorrente.getBranch("ARGUMENTS");

        String eNome = ASTCorrente.getNome();
        String eAcao = ASTCorrente.getValor();
        String eSaida = mSaida.getNome();


        if (eNome.contentEquals("__COMPILER__")) {


            InvokeCompiler mCor = new InvokeCompiler(mRunTime, mEscopo, this);
            mCor.init(eAcao, eSaida, mArgumentos);

        } else if (eNome.contentEquals("terminal")) {

            InvokeTerminal mCor = new InvokeTerminal(mRunTime, mEscopo, this);
            mCor.init(eAcao, eSaida, mArgumentos);

        } else if (eNome.contentEquals("casting")) {

            InvokeCasting mCor = new InvokeCasting(mRunTime, mEscopo, this);
            mCor.init(eAcao, eSaida, mArgumentos);


        } else if (eNome.contentEquals("math")) {

            InvokeMath mCor = new InvokeMath(mRunTime, mEscopo, this);
            mCor.init(eAcao, eSaida, mArgumentos);

        } else if (eNome.contentEquals("stages")) {

            InvokeStages mCor = new InvokeStages(mRunTime, mEscopo, this);
            mCor.init(eAcao, eSaida, mArgumentos);

        } else if (eNome.contentEquals("__UTILS__")) {

            InvokeUtils mCor = new InvokeUtils(mRunTime, mEscopo, this);
            mCor.init(eAcao, eSaida, mArgumentos);

        } else {

            mRunTime.errar(mLocal, "Invocacao : Biblioteca nao encontrada ->  " + eNome);

        }


    }

    public int argumentosContagem(AST ASTArgumentos) {

        int i = 0;

        for (AST eAST : ASTArgumentos.getASTS()) {

            if (eAST.mesmoTipo("ARGUMENT")) {

                i += 1;

            }

        }
        return i;
    }


    public String getQualquer(AST ASTArgumentos, int e) {

        int i = 0;

        String p1 = "";
        String p2 = "";

        for (AST eAST : ASTArgumentos.getASTS()) {

            if (eAST.mesmoTipo("ARGUMENT")) {

                //System.out.println(" \t - Argumento : " + eAST.getNome() + " : " + eAST.getValor());


                if (eAST.mesmoValor("ID")) {
                    String eRet = "";

                    if (eAST.mesmoNome("true") || eAST.mesmoNome("false")) {
                        eRet = eAST.getNome();
                    } else {
                        eRet = mEscopo.getDefinido(eAST.getNome());
                    }

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

                    if (i == 0) {
                        p1 = eAST.getNome();
                    } else {
                        p2 = eAST.getNome();
                    }

                }

                i += 1;

            }

        }

        if (e == 1) {
            return p1;
        } else if (e == 2) {
            return p2;
        } else {
            return "";
        }

    }

    public String getTipo(AST ASTArgumentos, int e) {

        int i = 0;

        String p1 = "";
        String p2 = "";

        for (AST eAST : ASTArgumentos.getASTS()) {

            if (eAST.mesmoTipo("ARGUMENT")) {

                if (eAST.mesmoValor("ID")) {
                    String eRet = "";

                    if (eAST.mesmoNome("true") || eAST.mesmoNome("false")) {
                        eRet = "bool";
                    } else {
                        eRet = mEscopo.getDefinidoTipo(eAST.getNome());
                    }

                    if (i == 0) {
                        p1 = eRet;
                    } else {
                        p2 = eRet;
                    }


                } else if (eAST.mesmoValor("Num")) {
                    if (i == 0) {
                        p1 = "num";
                    } else {
                        p2 = "num";
                    }
                } else if (eAST.mesmoValor("Text")) {

                    if (i == 0) {
                        p1 = "string";
                    } else {
                        p2 = "string";
                    }

                }

                i += 1;

            }

        }

        if (e == 1) {
            return p1;
        } else if (e == 2) {
            return p2;
        } else {
            return "";
        }

    }

    public String getBool(AST ASTArgumentos, int e) {

        int i = 0;

        String p1 = "";
        String p2 = "";

        for (AST eAST : ASTArgumentos.getASTS()) {

            if (eAST.mesmoTipo("ARGUMENT")) {

                //System.out.println(" \t - Argumento : " + eAST.getNome() + " : " + eAST.getValor());


                if (eAST.mesmoValor("ID")) {
                    String eRet = "";

                    if (eAST.mesmoNome("true") || eAST.mesmoNome("false")) {
                        eRet = eAST.getNome();
                    } else {

                        if (mEscopo.getDefinidoTipo(eAST.getNome()).contentEquals("bool")) {
                            eRet = mEscopo.getDefinido(eAST.getNome());
                        } else {
                            mRunTime.errar(mLocal, "Invocacao : Ação inconsistente -> Era esperado um bool");
                        }


                    }

                    if (i == 0) {
                        p1 = eRet;
                    } else {
                        p2 = eRet;
                        break;
                    }


                } else if (eAST.mesmoValor("Num")) {
                    mRunTime.errar(mLocal, "Invocacao : Ação inconsistente -> Era esperado um bool");

                } else if (eAST.mesmoValor("Text")) {
                    mRunTime.errar(mLocal, "Invocacao : Ação inconsistente -> Era esperado um bool");
                }

                i += 1;

            }

        }

        if (e == 1) {
            return p1;
        } else if (e == 2) {
            return p2;
        } else {
            return "";
        }

    }

    public String getString(AST ASTArgumentos, int e) {

        int i = 0;

        String p1 = "";
        String p2 = "";

        for (AST eAST : ASTArgumentos.getASTS()) {

            if (eAST.mesmoTipo("ARGUMENT")) {

                //System.out.println(" \t - Argumento : " + eAST.getNome() + " : " + eAST.getValor());


                if (eAST.mesmoValor("ID")) {
                    String eRet = "";

                    if (eAST.mesmoNome("true") || eAST.mesmoNome("false")) {
                        mRunTime.errar(mLocal, "Invocacao : Ação inconsistente -> Era esperado uma string");
                    } else {

                        if (mEscopo.getDefinidoTipo(eAST.getNome()).contentEquals("string")) {
                            eRet = mEscopo.getDefinido(eAST.getNome());
                        } else {
                            mRunTime.errar(mLocal, "Invocacao : Ação inconsistente -> Era esperado uma string");
                        }


                    }

                    if (i == 0) {
                        p1 = eRet;
                    } else {
                        p2 = eRet;
                    }


                } else if (eAST.mesmoValor("Num")) {
                    mRunTime.errar(mLocal, "Invocacao : Ação inconsistente -> Era esperado um string");

                } else if (eAST.mesmoValor("Text")) {

                    if (i == 0) {
                        p1 = eAST.getNome();
                    } else {
                        p2 = eAST.getNome();
                    }

                }

                i += 1;

            }

        }

        if (e == 1) {
            return p1;
        } else if (e == 2) {
            return p2;
        } else {
            return "";
        }

    }

    public String getNum(AST ASTArgumentos, int e) {

        int i = 0;

        String p1 = "";
        String p2 = "";

        for (AST eAST : ASTArgumentos.getASTS()) {

            if (eAST.mesmoTipo("ARGUMENT")) {

                //System.out.println(" \t - Argumento : " + eAST.getNome() + " : " + eAST.getValor());


                if (eAST.mesmoValor("ID")) {
                    String eRet = "";

                    if (eAST.mesmoNome("true") || eAST.mesmoNome("false")) {
                        mRunTime.errar(mLocal, "Invocacao : Ação inconsistente -> Era esperado um num");
                    } else {

                        if (mEscopo.getDefinidoTipo(eAST.getNome()).contentEquals("num")) {
                            eRet = mEscopo.getDefinido(eAST.getNome());
                        } else {
                            mRunTime.errar(mLocal, "Invocacao : Ação inconsistente -> Era esperado um num");
                        }


                    }

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
                    mRunTime.errar(mLocal, "Invocacao : Ação inconsistente -> Era esperado um num");
                }

                i += 1;

            }

        }

        if (e == 1) {
            return p1;
        } else if (e == 2) {
            return p2;
        } else {
            return "";
        }

    }

    public String getInt(AST ASTArgumentos, int e) {

        int i = 0;

        String p1 = "";
        String p2 = "";

        for (AST eAST : ASTArgumentos.getASTS()) {

            if (eAST.mesmoTipo("ARGUMENT")) {

                //System.out.println(" \t - Argumento : " + eAST.getNome() + " : " + eAST.getValor());


                if (eAST.mesmoValor("ID")) {
                    String eRet = "";

                    if (eAST.mesmoNome("true") || eAST.mesmoNome("false")) {
                        mRunTime.errar(mLocal, "Invocacao : Ação inconsistente -> Era esperado um int");
                    } else {

                        if (mEscopo.getDefinidoTipo(eAST.getNome()).contentEquals("int")) {
                            eRet = mEscopo.getDefinido(eAST.getNome());
                        } else {
                            mRunTime.errar(mLocal, "Invocacao : Ação inconsistente -> Era esperado um num");
                        }


                    }

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
                    mRunTime.errar(mLocal, "Invocacao : Ação inconsistente -> Era esperado um num");
                }

                i += 1;

            }

        }

        if (e == 1) {
            return p1;
        } else if (e == 2) {
            return p2;
        } else {
            return "";
        }

    }

    public boolean isNulo(AST ASTArgumentos, int e) {

        int i = 0;

        boolean sret = false;

        for (AST eAST : ASTArgumentos.getASTS()) {

            boolean ret = false;

            if (eAST.mesmoTipo("ARGUMENT")) {

                //System.out.println(" \t - Argumento : " + eAST.getNome() + " : " + eAST.getValor());


                if (eAST.mesmoValor("ID")) {

                    if (eAST.mesmoNome("true") || eAST.mesmoNome("false")) {

                    } else {

                        ret = mEscopo.getItem(eAST.getNome()).getNulo();


                    }


                } else if (eAST.mesmoValor("Num")) {


                } else if (eAST.mesmoValor("Text")) {

                }

                i += 1;
                if (i == e) {
                    sret = ret;
                }
            }

        }

        return sret;

    }

    public String getArgumento(AST ASTArgumentos, int e) {

        int i = 0;
        String ret = "";

        for (AST eAST : ASTArgumentos.getASTS()) {

            if (eAST.mesmoTipo("ARGUMENT")) {

                //System.out.println(" \t - Argumento : " + eAST.getNome() + " : " + eAST.getValor());

                if (i==e){

                    if (eAST.mesmoValor("ID")) {
                        String eRet = "";

                        if (eAST.mesmoNome("true") || eAST.mesmoNome("false")) {
                            eRet = eAST.getNome();
                        } else {
                            eRet = mEscopo.getDefinido(eAST.getNome());
                        }

                        ret = eRet;


                    } else if (eAST.mesmoValor("Num")) {
                        ret = eAST.getNome();
                    } else if (eAST.mesmoValor("Float")) {
                        ret = eAST.getNome();
                    } else if (eAST.mesmoValor("Text")) {
                        ret = eAST.getNome();
                    }


                }


                i += 1;

            }

        }

        return ret;


    }

    public String getArgumentoTipo(AST ASTArgumentos, int e) {

        int i = 0;
        String ret = "";

        for (AST eAST : ASTArgumentos.getASTS()) {

            if (eAST.mesmoTipo("ARGUMENT")) {

                //System.out.println(" \t - Argumento : " + eAST.getNome() + " : " + eAST.getValor());

                if (i==e){

                    if (eAST.mesmoValor("ID")) {
                        String eRet = "";

                        if (eAST.mesmoNome("true") || eAST.mesmoNome("false")) {
                            eRet =  "bool";
                        } else {
                            eRet = mEscopo.getDefinidoTipo(eAST.getNome());
                        }

                        ret = eRet;


                    } else if (eAST.mesmoValor("Num")) {
                        ret ="int";
                    } else if (eAST.mesmoValor("Float")) {
                        ret ="num";
                    } else if (eAST.mesmoValor("Text")) {
                        ret = "string";
                    }


                }


                i += 1;

            }

        }

        return ret;


    }

    public String getArgumentoNome(AST ASTArgumentos, int e) {

        int i = 0;
        String ret = "";

        for (AST eAST : ASTArgumentos.getASTS()) {

            if (eAST.mesmoTipo("ARGUMENT")) {

                //System.out.println(" \t - Argumento : " + eAST.getNome() + " : " + eAST.getValor());

                if (i==e){

                    ret = eAST.getNome();

                }


                i += 1;

            }

        }

        return ret;


    }

}

