package Sigmaz.Executor;

import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Run_Func {

    private RunTime mRunTime;
    private Escopo mEscopo;


    private boolean mIsNulo;
    private boolean mIsPrimitivo;


    private String mConteudo;
    private Object mObjeto;

    private String mRetornoTipo;


    public Run_Func(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;

        mIsNulo = false;
        mIsPrimitivo = false;

        mObjeto = null;
        mConteudo = null;
        mRetornoTipo = null;

    }

    public String getConteudo() {
        return mConteudo;
    }

    public Object getObjeto() {
        return mObjeto;
    }

    public boolean getIsNulo() {
        return mIsNulo;
    }

    public boolean getIsPrimitivo() {
        return mIsPrimitivo;
    }

    public boolean getIsStruct() {
        return !mIsPrimitivo;
    }

    public String getRetornoTipo() {
        return mRetornoTipo;
    }

    public void setNulo(boolean eNulo) {
        mIsNulo = eNulo;
    }

    public void setPrimitivo(boolean ePrimitivo) {
        mIsPrimitivo = ePrimitivo;
    }

    public void setRetornoTipo(String eRetornoTipo) {
        mRetornoTipo = eRetornoTipo;
    }

    public void setConteudo(String eConteudo) {
        mConteudo = eConteudo;
    }

    public String getRetornoFunction() {
        return mRetornoTipo;
    }


    public void init(AST ASTCorrente, String eReturne) {

        boolean isPrimitivo = mRunTime.isPrimitivo(eReturne);

        int argumentos = 0;
        ArrayList<AST> mArgumentos = new ArrayList<AST>();

        for (AST a : ASTCorrente.getASTS()) {
            if (a.mesmoTipo("ARGUMENT")) {
                argumentos += 1;

                AST v = new AST("VALUE");

                Run_Value mAST = new Run_Value(mRunTime, mEscopo);
                mAST.init(a, "<<ANY>>");


                if (mAST.getIsNulo()) {
                    //  v.setValor("NULL");

                    v.setValor(mAST.getRetornoTipo());
                    v.setNome(mAST.getConteudo());

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

        //  System.out.println("Procurando FUNC " + ASTCorrente.getNome());
        //System.out.println("\t - Argumentos :  " + argumentos);

        boolean enc = false;
        boolean algum = false;
        AST mFunction = null;

        ArrayList<String> mArgumentandoNome = new ArrayList<String>();
        ArrayList<String> mArgumentandoTipos = new ArrayList<String>();

        for (AST a : mEscopo.getGuardadosCompleto()) {

            if (a.mesmoTipo("FUNCTION") && a.mesmoNome(ASTCorrente.getNome())) {
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
                            //   System.out.println("\t - Checando Tipo :  " + mArgumentos.get(argC).getValor() + " e " + mArgumentandoTipos.get(argC));

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

                if (mFunction.mesmoValor(eReturne) || eReturne.contentEquals("<<ANY>>")) {

                    if (mRunTime.getErros().size() > 0) {
                        return;
                    }


                    execute(mFunction, argumentos, mArgumentandoNome, mArgumentos, eReturne);

                } else {
                    mRunTime.getErros().add("Function " + ASTCorrente.getNome() + " : Retorno incompativel !");
                }

            } else {
                mRunTime.getErros().add("Function " + ASTCorrente.getNome() + " : Argumentos incompativeis !");
            }

        } else {

            mRunTime.getErros().add("Function  " + ASTCorrente.getNome() + " : Nao Encontrada !");
        }


    }

    public void execute(AST mFunction, int argumentos, ArrayList<String> mArgumentandoNome, ArrayList<AST> mArgumentos, String eReturne) {


        mRetornoTipo = mFunction.getValor();

        // System.out.println(" EXECUTAR Function " + mFunction.getNome() + " : " + mFunction.getValor());


        Escopo mEscopoInterno = new Escopo(mRunTime, mRunTime.getEscopoGlobal());
        //Escopo mEscopoInterno = new Escopo(mRunTime, null);

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

        mIsNulo = mAST.getIsNulo();
        mIsPrimitivo = mAST.getIsPrimitivo();
        mConteudo = mAST.getConteudo();
        mRetornoTipo = mAST.getRetornoTipo();

        if (mRunTime.getErros().size() > 0) {
            return;
        }

        if (mRetornoTipo.contentEquals("bool")) {
            mIsPrimitivo = true;
        } else if (mRetornoTipo.contentEquals("num")) {
            mIsPrimitivo = true;
        } else if (mRetornoTipo.contentEquals("string")) {
            mIsPrimitivo = true;
        }


        // System.out.println("  Retornando -> " + mAST.getConteudo());


    }

}
