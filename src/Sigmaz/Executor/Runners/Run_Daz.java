package Sigmaz.Executor.Runners;

import Sigmaz.Executor.Escopo;
import Sigmaz.Executor.Item;
import Sigmaz.Executor.RunTime;
import Sigmaz.Utils.AST;

public class Run_Daz {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private String mLocal;

    private boolean mRetornado;
    private Item mItem;


    public Run_Daz(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mLocal = "Run_Daz";

        mRetornado=false;
        mItem=null;
    }

    public boolean getCancelado() {
        return mEscopo.getCancelar();
    }

    public boolean getContinuar() {
        return mEscopo.getContinuar();
    }


    public Item getRetorno(){ return mItem; }
    public boolean getRetornado() { return mRetornado; }

    public void Retorne(Item eItem){
        mEscopo.retorne(eItem);
        mItem=eItem;
    }


    public void init(AST ASTCorrente) {


        AST mCondicao = ASTCorrente.getBranch("CHOOSABLE");
        AST mCorpo = ASTCorrente.getBranch("BODY");

        Run_Value mAST = new Run_Value(mRunTime, mEscopo);
        mAST.init(mCondicao, "<<ANY>>");

        boolean sucesso = false;

        if (mRunTime.getErros().size() > 0) {
            return;
        }

        float CGeral = 0;

        try {
            CGeral = Float.parseFloat(mAST.getConteudo());
        } catch (Exception e) {
            mRunTime.errar(mLocal,"Uma condição Daz requer argumentos numericos  !");
            return;
        }


        for (AST fAST : ASTCorrente.getBranch("CASES").getASTS()) {


            AST ouCondicao = fAST.getBranch("ARGUMENTS");
            AST ouCorpo = fAST.getBranch("BODY");

            if (ouCondicao.getASTS().size() != 2) {
                mRunTime.errar(mLocal,"Uma condição Daz requer 2 argumentos !");
                return;
            }

            Run_Value RV_1 = new Run_Value(mRunTime, mEscopo);
            RV_1.init(ouCondicao.getASTS().get(0), "<<ANY>>");

            Run_Value RV_2 = new Run_Value(mRunTime, mEscopo);
            RV_2.init(ouCondicao.getASTS().get(1), "<<ANY>>");


            float P1 = 0;
            float P2 = 0;

            try {
                P1 = Float.parseFloat(RV_1.getConteudo());
                P2 = Float.parseFloat(RV_2.getConteudo());
            } catch (Exception e) {
                mRunTime.errar(mLocal,"Uma condição Daz requer argumentos numericos  !");
                return;
            }


            if (fAST.mesmoTipo("INSIDE") && CGeral > P1 && CGeral < P2) {

                sucesso = true;

                Escopo EscopoInterno = new Escopo(mRunTime, mEscopo);

                Run_Body cAST = new Run_Body(mRunTime, EscopoInterno);
                cAST.init(ouCorpo);

                if (cAST.getCancelado()) {
                    sucesso = true;
                    break;
                }
                if (cAST.getRetornado()){
                    Retorne(cAST.getRetorno());
                    break;
                }
                if (mRunTime.getErros().size() > 0) {
                    return;
                }

            } else if (fAST.mesmoTipo("EXTREM") && (CGeral == P1 || CGeral == P2)) {

                sucesso = true;

                Escopo EscopoInterno = new Escopo(mRunTime, mEscopo);

                Run_Body cAST = new Run_Body(mRunTime, EscopoInterno);
                cAST.init(ouCorpo);

                if (cAST.getCancelado()) {
                    sucesso = true;
                    break;
                }
                if (cAST.getRetornado()){
                    Retorne(cAST.getRetorno());
                    break;
                }
                if (mRunTime.getErros().size() > 0) {
                    return;
                }

            } else if (fAST.mesmoTipo("OUTSIDE") && (CGeral < P1 || CGeral > P2)) {

                sucesso = true;

                Escopo EscopoInterno = new Escopo(mRunTime, mEscopo);

                Run_Body cAST = new Run_Body(mRunTime, EscopoInterno);
                cAST.init(ouCorpo);

                if (cAST.getCancelado()) {
                    sucesso = true;
                    break;
                }
                if (cAST.getRetornado()){
                    Retorne(cAST.getRetorno());
                    break;
                }
                if (mRunTime.getErros().size() > 0) {
                    return;
                }

            } else if (fAST.mesmoTipo("START") && (CGeral >= P1 && CGeral < P2)) {

                sucesso = true;

                Escopo EscopoInterno = new Escopo(mRunTime, mEscopo);

                Run_Body cAST = new Run_Body(mRunTime, EscopoInterno);
                cAST.init(ouCorpo);

                if (cAST.getCancelado()) {
                    sucesso = true;
                    break;
                }
                if (cAST.getRetornado()){
                    Retorne(cAST.getRetorno());
                    break;
                }
                if (mRunTime.getErros().size() > 0) {
                    return;
                }
            } else if (fAST.mesmoTipo("END") && (CGeral > P1 && CGeral <= P2)) {




                sucesso = true;

                Escopo EscopoInterno = new Escopo(mRunTime, mEscopo);

                Run_Body cAST = new Run_Body(mRunTime, EscopoInterno);
                cAST.init(ouCorpo);

                if (cAST.getCancelado()) {
                    sucesso = true;
                    break;
                }
                if (cAST.getRetornado()){
                    Retorne(cAST.getRetorno());
                    break;
                }
                if (mRunTime.getErros().size() > 0) {
                    return;
                }
            }

        }

        if (!sucesso) {
            others(ASTCorrente);
        }


    }

    public void others(AST ASTCorrente) {

        for (AST fAST : ASTCorrente.getASTS()) {
            if (fAST.mesmoTipo("OTHERS")) {


                Escopo EscopoInterno = new Escopo(mRunTime, mEscopo);

                Run_Body cAST = new Run_Body(mRunTime, EscopoInterno);
                cAST.init(fAST);

                if (cAST.getCancelado()) {
                    mEscopo.setCancelar(true);
                }
                if (cAST.getContinuar()) {
                    mEscopo.setContinuar(true);
                }

                if (cAST.getRetornado()){
                    Retorne(cAST.getRetorno());
                    break;
                }

                if (mRunTime.getErros().size() > 0) {
                    return;
                }

                break;

            }

        }


    }
}
