package Sigmaz.Analisador;

import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Analisador {

    private ArrayList<AST> mASTS;

    private ArrayList<String> mErros;

    private ArrayList<String> mPrimitivos;

    private ArrayList<String> mFunctions;
    private ArrayList<String> mFunctions_Nomes;
    private ArrayList<String> mCasts_Nomes;
    private ArrayList<String> mStructs_Nomes;

    private Analisar_All mAnalisar_All;
    private Analisar_When mAnalisar_When;
    private Analisar_Step mAnalisar_Step;
    private Analisar_Condition mAnalisar_Condition;
    private Analisar_While mAnalisar_While;
    private Analisar_Argumentos mAnalisar_Argumentos;
    private Analisar_Apply mAnalisar_Apply;

    private Heranca mHeranca;

    public Analisador() {

        mASTS = new ArrayList<>();


        mErros = new ArrayList<>();
        mPrimitivos = new ArrayList<>();

        mPrimitivos.add("bool");
        mPrimitivos.add("string");
        mPrimitivos.add("num");

        mFunctions = new ArrayList<String>();
        mFunctions_Nomes = new ArrayList<String>();
        mCasts_Nomes = new ArrayList<String>();
        mStructs_Nomes= new ArrayList<String>();

        mAnalisar_All = new Analisar_All(this);
        mAnalisar_When = new Analisar_When(this);
        mAnalisar_Step = new Analisar_Step(this);
        mAnalisar_Condition = new Analisar_Condition(this);
        mAnalisar_While = new Analisar_While(this);
        mAnalisar_Argumentos = new Analisar_Argumentos(this);
        mAnalisar_Apply = new Analisar_Apply(this);

        mHeranca = new Heranca(this);

    }

    public ArrayList<String> getErros() {
        return mErros;
    }

    public ArrayList<String> getFunctions_Nomes() {
        return mFunctions_Nomes;
    }

    public void init(ArrayList<AST> eASTs) {
        mASTS = eASTs;
        mErros.clear();
        mFunctions.clear();
        mFunctions_Nomes.clear();
        mCasts_Nomes.clear();
        mStructs_Nomes.clear();

        ArrayList<String> mAlocados = new ArrayList<String>();

        for (AST ASTCGlobal : mASTS) {

            if (ASTCGlobal.mesmoTipo("SIGMAZ")) {

                analisarGlobal(ASTCGlobal, mAlocados);

            } else {

                mErros.add("Escopo Desconhecido : " + ASTCGlobal.getTipo());

            }

        }


        mHeranca.init(mASTS);

    }

    public ArrayList<String> copiarAlocados(ArrayList<String> mAlocados) {

        ArrayList<String> mCopia = new ArrayList<String>();

        for (String a : mAlocados) {
            mCopia.add(a);
        }

        return mCopia;
    }

    public void analisarAlocacao(AST ASTPai, ArrayList<String> mAlocadosAntes) {

        if (!mAlocadosAntes.contains(ASTPai.getNome())) {
            mAlocadosAntes.add(ASTPai.getNome());
        } else {
            mErros.add("Definicao Duplicada : " + ASTPai.getNome());
        }
    }

    public void analisarValoracao(AST ASTPai, ArrayList<String> mAlocadosAntes) {

        mAnalisar_Apply.analisar_valorizar(ASTPai, mAlocadosAntes);

    }


    public void analisarGlobal(AST ASTPai, ArrayList<String> mAlocadosAntes) {

        ArrayList<String> mAlocados = copiarAlocados(mAlocadosAntes);

        for (AST mAST : ASTPai.getASTS()) {

            if (mAST.mesmoTipo("FUNCTION")) {

                mFunctions_Nomes.add(mAST.getNome());

            } else if (mAST.mesmoTipo("CAST")) {

                if(!mCasts_Nomes.contains(mAST.getNome())){
                    mCasts_Nomes.add(mAST.getNome());
                }else{
                    getErros().add("Cast Duplicado : " + mAST.getTipo());
                }
            } else if (mAST.mesmoTipo("STRUCT")) {

                if(!mStructs_Nomes.contains(mAST.getNome())){
                    mStructs_Nomes.add(mAST.getNome());
                }else{
                    getErros().add("Struct Duplicado : " + mAST.getTipo());
                }


            }

        }


        for (AST mAST : ASTPai.getASTS()) {

            if (mAST.mesmoTipo("DEFINE")) {

                analisarAlocacao(mAST, mAlocados);

                analisandoDefines(mAST);
                analisarValoracao(ASTPai, mAlocadosAntes);


            } else if (mAST.mesmoTipo("MOCKIZ")) {

                analisarAlocacao(mAST, mAlocados);


                analisandoDefines(mAST);
                analisarValoracao(ASTPai, mAlocadosAntes);


            }

        }


        for (AST mAST : ASTPai.getASTS()) {

            if (mAST.mesmoTipo("ACTION")) {

                analisarAction(mAST, mAlocados);

            } else if (mAST.mesmoTipo("FUNCTION")) {

                analisarFunction(mAST, mAlocados);

            } else if (mAST.mesmoTipo("CALL")) {
            } else if (mAST.mesmoTipo("INVOKE")) {

            } else if (mAST.mesmoTipo("DEFINE")) {
            } else if (mAST.mesmoTipo("MOCKIZ")) {

            } else if (mAST.mesmoTipo("OPERATION")) {
            } else if (mAST.mesmoTipo("CAST")) {
            } else if (mAST.mesmoTipo("STRUCT")) {

            } else {

                mErros.add("AST x : " + mAST.getTipo());


            }


        }

    }


    public void analisarFunction(AST ASTPai, ArrayList<String> mAlocadosAntes) {


        ArrayList<String> mAlocados = copiarAlocados(mAlocadosAntes);


        analisandoDefines(ASTPai);


        String mParametrizando = "";

        for (AST mAST : ASTPai.getBranch("ARGUMENTS").getASTS()) {
            if (mAST.mesmoTipo("ARGUMENT")) {

                // mAnalisador.analisarAlocacao(mAST,mAlocadosAntes);

                if (!mAlocados.contains(mAST.getNome())) {
                    mAlocados.add(mAST.getNome());
                } else {
                    getErros().add("Argumento Duplicado : " + mAST.getNome());
                }

                mParametrizando += "<" + mAST.getValor() + "> ";

                analisandoDefines(mAST);

            } else {
                getErros().add("Tipo Desconhecido : " + mAST.getTipo());
            }
        }

        String mParametragem = ASTPai.getNome() + " ( " + mAnalisar_Argumentos.analisarArguments(ASTPai.getBranch("ARGUMENTS"), mAlocados) + ") ";


        if (!mFunctions.contains(mParametragem)) {
            mFunctions.add(mParametragem);
        } else {
            mErros.add("Function Duplicada : " + mParametragem);
        }


        boolean retornou = false;

        for (AST mAST : ASTPai.getBranch("BODY").getASTS()) {
            boolean ret = analisarDentroFunction(mAST, mAlocados, false);
            if (ret) {
                retornou = true;
            }
        }

        if (retornou == false) {
            mErros.add("Function " + ASTPai.getNome() + " : Nao possui retorno !");
        }

    }

    public boolean analisarDentroFunction(AST ASTPai, ArrayList<String> mAlocadosAntes, boolean laco) {

        boolean retornou = false;

        if (ASTPai.mesmoTipo("DEF")) {

            analisandoDefines(ASTPai);
            analisarValoracao(ASTPai, mAlocadosAntes);

        } else if (ASTPai.mesmoTipo("MOC")) {

            analisandoDefines(ASTPai);
            analisarValoracao(ASTPai, mAlocadosAntes);


        } else if (ASTPai.mesmoTipo("INVOKE")) {

        } else if (ASTPai.mesmoTipo("RETURN")) {
            retornou = true;
        } else if (ASTPai.mesmoTipo("APPLY")) {

            mAnalisar_Apply.analisar_Apply(ASTPai, mAlocadosAntes);


        } else if (ASTPai.mesmoTipo("EXECUTE")) {
        } else if (ASTPai.mesmoTipo("WHEN")) {

            mAnalisar_When.analisar_When(ASTPai, mAlocadosAntes, true);


        } else if (ASTPai.mesmoTipo("ALL")) {

            mAnalisar_All.analisar_All(ASTPai, mAlocadosAntes, true);

        } else if (ASTPai.mesmoTipo("IF")) {
            mAnalisar_Condition.analisarCondicao(ASTPai, mAlocadosAntes, true, laco);
        } else if (ASTPai.mesmoTipo("WHILE")) {
            mAnalisar_While.analisarWhile(ASTPai, mAlocadosAntes, true);
        } else if (ASTPai.mesmoTipo("STEP")) {
            mAnalisar_Step.analisarStep(ASTPai, mAlocadosAntes, true);
        } else if (ASTPai.mesmoTipo("STEPDEF")) {
            mAnalisar_Step.analisarStepDef(ASTPai, mAlocadosAntes, true);
        } else if (ASTPai.mesmoTipo("CANCEL")) {
            if (!laco) {
                mErros.add("CANCEL so pode existir dentro de um laco !");
            }
        } else if (ASTPai.mesmoTipo("CONTINUE")) {
            if (!laco) {
                mErros.add("CONTINUE so pode existir dentro de um laco !");
            }
        } else {
            mErros.add("AST : " + ASTPai.getTipo());
        }

        return retornou;
    }

    public void analisarDentroAction(AST ASTPai, ArrayList<String> mAlocadosAntes, boolean laco) {

        if (ASTPai.mesmoTipo("DEF")) {

            analisarAlocacao(ASTPai, mAlocadosAntes);
            analisarValoracao(ASTPai, mAlocadosAntes);

            analisandoDefines(ASTPai);
        } else if (ASTPai.mesmoTipo("MOC")) {

            analisarAlocacao(ASTPai, mAlocadosAntes);
            analisarValoracao(ASTPai, mAlocadosAntes);

            analisandoDefines(ASTPai);
        } else if (ASTPai.mesmoTipo("INVOKE")) {
        } else if (ASTPai.mesmoTipo("APPLY")) {

            mAnalisar_Apply.analisar_Apply(ASTPai, mAlocadosAntes);

        } else if (ASTPai.mesmoTipo("EXECUTE")) {
        } else if (ASTPai.mesmoTipo("WHEN")) {

            mAnalisar_When.analisar_When(ASTPai, mAlocadosAntes, false);


        } else if (ASTPai.mesmoTipo("ALL")) {

            mAnalisar_All.analisar_All(ASTPai, mAlocadosAntes, false);


        } else if (ASTPai.mesmoTipo("RETURN")) {
            mErros.add("Action " + ASTPai.getNome() + " : Nao pode ter RETORNO !");
        } else if (ASTPai.mesmoTipo("IF")) {
            mAnalisar_Condition.analisarCondicao(ASTPai, mAlocadosAntes, false, laco);
        } else if (ASTPai.mesmoTipo("WHILE")) {
            mAnalisar_While.analisarWhile(ASTPai, mAlocadosAntes, false);
        } else if (ASTPai.mesmoTipo("STEP")) {
            mAnalisar_Step.analisarStep(ASTPai, mAlocadosAntes, false);
        } else if (ASTPai.mesmoTipo("STEPDEF")) {
            mAnalisar_Step.analisarStepDef(ASTPai, mAlocadosAntes, false);
        } else if (ASTPai.mesmoTipo("CANCEL")) {
            if (!laco) {
                mErros.add("CANCEL so pode existir dentro de um laco !");
            }
        } else if (ASTPai.mesmoTipo("CONTINUE")) {
            if (!laco) {
                mErros.add("CONTINUE so pode existir dentro de um laco !");
            }
        } else {
            mErros.add("AST : " + ASTPai.getTipo());
        }

    }

    public void analisarAction(AST ASTPai, ArrayList<String> mAlocadosAntes) {

        ArrayList<String> mAlocados = copiarAlocados(mAlocadosAntes);


        String mParametrizando = "";

        for (AST mAST : ASTPai.getBranch("ARGUMENTS").getASTS()) {
            if (mAST.mesmoTipo("ARGUMENT")) {


                if (!mAlocados.contains(mAST.getNome())) {
                    mAlocados.add(mAST.getNome());
                } else {
                    getErros().add("Argumento Duplicado : " + mAST.getNome());
                }

                mParametrizando += "<" + mAST.getValor() + "> ";

                analisandoDefines(mAST);

            } else {
                getErros().add("Tipo Desconhecido : " + mAST.getTipo());
            }
        }

        for (AST mAST : ASTPai.getBranch("BODY").getASTS()) {

            analisarDentroAction(mAST, mAlocados, false);


        }

    }


    public void analisandoDefines(AST ASTPai) {

        if (mPrimitivos.contains(ASTPai.getValor())) {
        } else if (mCasts_Nomes.contains(ASTPai.getValor())) {
        } else if (mStructs_Nomes.contains(ASTPai.getValor())) {

        } else {
            mErros.add("Tipo deconhecido : " + ASTPai.getValor());
        }

    }

}
