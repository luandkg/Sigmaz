package Sigmaz.Executor;

import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Run_ActionFunction {

    private RunTime mRunTime;
    private Escopo mEscopo;

    private String mTipo;

    private String mPrimitivo;
    private Object mObjeto;

    private String mRetornoFunction;

    public Run_ActionFunction(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mTipo = "";
        mPrimitivo = "";
        mObjeto = null;

        mRetornoFunction = "";

    }

    public String getTipo() {
        return mTipo;
    }

    public void setTipo(String eTipo) {
        mTipo = eTipo;
    }

    public String getPrimitivo() {
        return mPrimitivo;
    }

    public void setPrimitivo(String ePrimitivo) {
        mPrimitivo = ePrimitivo;
    }

    public Object getObjeto() {
        return mObjeto;
    }

    public boolean getIsNulo() {
        return mTipo.contentEquals("NULL");
    }

    public boolean getIsPrimitivo() {
        return mTipo.contentEquals("PRIMITIVE");
    }

    public boolean getIsStruct() {
        return mTipo.contentEquals("STRUCT");
    }


    public String getRetornoFunction() {
        return mRetornoFunction;
    }


    public void init(AST ASTCorrente) {


        int argumentos = 0;
        ArrayList<AST> mArgumentos = new ArrayList<AST>();

        for (AST a : ASTCorrente.getASTS()) {
            if (a.mesmoTipo("ARGUMENT")) {

                argumentos += 1;

                AST v = new AST("VALUE");

                Run_Value mAST = new Run_Value(mRunTime, mEscopo);
                mAST.init(a, "<<ANY>>");

                if (mRunTime.getErros().size() > 0) {
                    return;
                }

                if (mAST.getIsNulo()) {
                    //v.setValor("NULL");

                    v.setValor(mAST.getRetornoTipo());
                    v.setNome(mAST.getConteudo());

                    // System.out.println("  - FUNCT ARGUMENTANDO  -> " + mAST.getConteudo() + " : " + mAST.getRetornoTipo());

                } else if (mAST.getIsPrimitivo()) {

                    v.setValor(mAST.getRetornoTipo());
                    v.setNome(mAST.getConteudo());

                    //  System.out.println("  - FUNCT ARGUMENTANDO  -> " + mAST.getConteudo() + " : " + mAST.getRetornoTipo());

                } else {
                    mRunTime.getErros().add("AST_Value com problemas !");
                }

                mArgumentos.add(v);


            }

        }

        if (mRunTime.getErros().size() > 0) {
            return;
        }

        //  System.out.println("Procurando FUNC " + ASTCorrente.getNome());
        //System.out.println("\t - Argumentos :  " + argumentos);

        boolean enc = false;
        boolean algum = false;
        AST mFunction = null;

        ArrayList<String> mArgumentandoNome = new ArrayList<String>();
        ArrayList<String> mArgumentandoTipos = new ArrayList<String>();

        for (
                AST a : mEscopo.getGuardadosCompleto()) {

            if ((a.mesmoTipo("ACTION") || a.mesmoTipo("FUNCTION")) && a.mesmoNome(ASTCorrente.getNome())) {
                enc = true;

                int argumentando = 0;
                mArgumentandoNome.clear();
                mArgumentandoTipos.clear();

                for (AST b : a.getBranch("ARGUMENTS").getASTS()) {
                    if (b.mesmoTipo("ARGUMENT")) {
                        argumentando += 1;

                        mArgumentandoNome.add(b.getNome());
                        mArgumentandoTipos.add(b.getValor());

                        //       System.out.println("\t - Complexo :  " + b.getValor());

                    }

                }


                if (mArgumentos.size() == mArgumentandoTipos.size()) {

                    if (argumentos > 0) {
                        int v = 0;

                        for (int argC = 0; argC < argumentos; argC++) {

                            if (mArgumentos.get(argC).mesmoValor(mArgumentandoTipos.get(argC))) {

                                v += 1;
                            }
                           //   System.out.println("\t - Checando em " + a.getNome() + " Parametro :  " + mArgumentos.get(argC).getValor() + " e " + mArgumentandoTipos.get(argC));

                        }

                        if (v == argumentos) {
                            algum = true;
                            mFunction = a;
                            break;
                        }
                    } else {

                        algum = true;
                        mFunction = a;
                        break;
                    }


                }

            }

        }

        if (enc) {

            if (algum) {

                //  System.out.println("Ret :: " + eReturne);

                execute(mFunction, argumentos, mArgumentandoNome, mArgumentos);

            } else {
                mRunTime.getErros().add("Function " + ASTCorrente.getNome() + " : Argumentos incompativeis !");
            }

        } else {

            mRunTime.getErros().add("Function  " + ASTCorrente.getNome() + " : Nao Encontrada !");
        }


    }

    public void execute(AST mFunction, int argumentos, ArrayList<String> mArgumentandoNome, ArrayList<AST> mArgumentos) {


        mRetornoFunction = mFunction.getValor();

        //  System.out.println("Function " + mFunction.getNome() + " : " + mFunction.getValor());


        Escopo mEscopoInterno = new Escopo(mRunTime, mRunTime.getEscopoGlobal());

        if (argumentos > 0) {

            for (int argC = 0; argC < argumentos; argC++) {

                //   System.out.println("\t - Alocando : " + mArgumentandoNome.get(argC) + " -> " + mArgumentos.get(argC).getNome() + " :: " + mArgumentos.get(argC).getValor());

                if (mArgumentandoNome.get(argC).contentEquals("true") || mArgumentandoNome.get(argC).contentEquals("false")) {

                    mEscopoInterno.criarDefinicao(mArgumentandoNome.get(argC), mArgumentos.get(argC).getValor(), mArgumentos.get(argC).getNome());

                } else {
                    mEscopoInterno.criarDefinicao(mArgumentandoNome.get(argC), mArgumentos.get(argC).getValor(), mArgumentos.get(argC).getNome());
                }
            }

        }

        AST mASTBody = mFunction.getBranch("BODY");

        Run_Body mAST = new Run_Body(mRunTime, mEscopoInterno);
        mAST.init(mASTBody);


    }

}
