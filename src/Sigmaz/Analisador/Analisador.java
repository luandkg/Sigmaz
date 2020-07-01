package Sigmaz.Analisador;

import Sigmaz.Executor.RunTime;
import Sigmaz.Utils.AST;

import java.io.File;
import java.util.ArrayList;

public class Analisador {

    private ArrayList<AST> mASTS;

    private ArrayList<String> mErros;
    private ArrayList<String> mProibidos;

    private ArrayList<String> mPrimitivos;
    private ArrayList<String> mTipados;

    private Analisar_Function mAnalisar_Function;
    private Analisar_Action mAnalisar_Action;
    private Analisar_Cast mAnalisar_Cast;

    private Analisar_Daz mAnalisar_Daz;
    private Analisar_When mAnalisar_When;
    private Analisar_Step mAnalisar_Step;
    private Analisar_Condition mAnalisar_Condition;
    private Analisar_While mAnalisar_While;
    private Analisar_Argumentos mAnalisar_Argumentos;
    private Analisar_Apply mAnalisar_Apply;
    private Analisar_Execute mAnalisar_Execute;
    private Analisar_Stage mAnalisar_Stage;
    private Analisar_Try mAnalisar_Try;

    private Analisar_Outros mAnalisar_Outros;

    private Heranca mHeranca;
    private Estagiador mEstagiador;
    private Modelagem mModelagem;

    private boolean mExterno;

    public Analisador() {

        mASTS = new ArrayList<>();


        mErros = new ArrayList<>();
        mProibidos = new ArrayList<>();
        mTipados = new ArrayList<>();

        mAnalisar_Function = new Analisar_Function(this);
        mAnalisar_Action = new Analisar_Action(this);
        mAnalisar_Cast = new Analisar_Cast(this);

        mAnalisar_Daz = new Analisar_Daz(this);
        mAnalisar_When = new Analisar_When(this);
        mAnalisar_Step = new Analisar_Step(this);
        mAnalisar_Condition = new Analisar_Condition(this);
        mAnalisar_While = new Analisar_While(this);
        mAnalisar_Argumentos = new Analisar_Argumentos(this);
        mAnalisar_Apply = new Analisar_Apply(this);
        mAnalisar_Execute = new Analisar_Execute(this);
        mAnalisar_Stage = new Analisar_Stage(this);
        mAnalisar_Try = new Analisar_Try(this);

        mAnalisar_Outros = new Analisar_Outros(this);

        mHeranca = new Heranca(this);
        mModelagem = new Modelagem(this);
        mEstagiador = new Estagiador(this);


        mExterno = true;


        mProibidos.add("this");
        mProibidos.add("if");
        mProibidos.add("cast");
        mProibidos.add("struct");
        mProibidos.add("stages");
        mProibidos.add("match");
        mProibidos.add("function");
        mProibidos.add("action");
        mProibidos.add("operator");
        mProibidos.add("operation");

        mProibidos.add("when");
        mProibidos.add("all");
        mProibidos.add("step");

        mTipados.add("num");
        mTipados.add("string");
        mTipados.add("bool");

        mPrimitivos = new ArrayList<String>();
        mPrimitivos.add("num");
        mPrimitivos.add("string");
        mPrimitivos.add("bool");
    }


    public ArrayList<String> getPrimitivos() {
        return mPrimitivos;
    }

    public Analisar_Function getAnalisar_Function() {
        return mAnalisar_Function;
    }

    public Analisar_Action getAnalisar_Action() {
        return mAnalisar_Action;
    }


    public Analisar_Daz getAnalisar_All() {
        return mAnalisar_Daz;
    }

    public Analisar_When getAnalisar_When() {
        return mAnalisar_When;
    }

    public Analisar_Step getAnalisar_Step() {
        return mAnalisar_Step;
    }

    public Analisar_Condition getAnalisar_Condition() {
        return mAnalisar_Condition;
    }

    public Analisar_While getAnalisar_While() {
        return mAnalisar_While;
    }

    public Analisar_Argumentos getAnalisar_Argumentos() {
        return mAnalisar_Argumentos;
    }

    public Analisar_Apply getAnalisar_Apply() {
        return mAnalisar_Apply;
    }

    public Analisar_Execute getAnalisar_Execute() {
        return mAnalisar_Execute;
    }

    public Analisar_Outros getAnalisar_Outros() {
        return mAnalisar_Outros;
    }

    public Analisar_Try getAnalisar_Try() {
        return mAnalisar_Try;
    }

    public void externarlizar() {
        mExterno = true;
    }

    public void internalizar() {
        mExterno = false;
    }

    public boolean getExterno() {
        return mExterno;
    }

    public void mensagem(String eMensagem) {

        if (mExterno) {

            System.out.println(eMensagem);

        }

    }

    public ArrayList<String> getTipados() {
        return mTipados;
    }


    public ArrayList<String> getErros() {
        return mErros;
    }

    public ArrayList<String> getActions_Nomes() {
        return mAnalisar_Outros.getActions_Nomes();
    }

    public ArrayList<String> getFunctions_Nomes() {
        return mAnalisar_Outros.getFunctions_Nomes();
    }

    public void init(ArrayList<AST> eASTs, String mLocal) {
        mASTS = eASTs;
        mErros.clear();

        getAnalisar_Outros().limpar();

        ArrayList<String> mAlocados = new ArrayList<String>();


        // IMPORTANDO BIBLIOTECAS EXTERNAS
        ArrayList<String> mRequiscoes = new ArrayList<>();
        for (AST ASTCGlobal : mASTS) {
            if (ASTCGlobal.mesmoTipo("SIGMAZ")) {

                for (AST ASTC : ASTCGlobal.getASTS()) {
                    if (ASTC.mesmoTipo("REQUIRED")) {

                        String mReq = mLocal + ASTC.getNome() + ".sigmad";
                        mRequiscoes.add(mReq);


                    }

                }

            }
        }

        for (String mReq : mRequiscoes) {

            File arq = new File(mReq);

            if (arq.exists()) {


                RunTime RunTimeC = new RunTime();

                try {
                    RunTimeC.init(mReq);

                    for (AST ASTR : RunTimeC.getBranch("SIGMAZ").getASTS()) {


                        if (ASTR.mesmoTipo("FUNCTION")) {

                            String mParametragem = ASTR.getNome();


                            if (!this.getFunctions_Nomes().contains(mParametragem)) {
                                this.getFunctions_Nomes().add(mParametragem);
                            } else {

                            }

                        }
                    }

                } catch (Exception e) {
                    mErros.add("Library " + mReq + " : Problema ao carregar !");
                }

            } else {
                mErros.add("Library " + mReq + " : Nao Encontrado !");
            }

        }


        for (AST ASTCGlobal : mASTS) {

            if (ASTCGlobal.mesmoTipo("SIGMAZ")) {

                analisarGlobal(ASTCGlobal, mAlocados);

            } else {

                mErros.add("Escopo Desconhecido : " + ASTCGlobal.getTipo());

            }

        }


        mHeranca.init(mASTS);

        mModelagem.init(mASTS);

        mEstagiador.init(mASTS);

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
          //  mErros.add("Definicao Duplicada : " + ASTPai.getNome());
        }
    }

    public void analisarValoracao(AST ASTPai, ArrayList<String> mAlocadosAntes) {

        mAnalisar_Apply.analisar_valorizar(ASTPai, mAlocadosAntes);

    }

    public ArrayList<String> getProibidos() {
        return mProibidos;
    }

    public void analisarGlobal(AST ASTPai, ArrayList<String> mAlocadosAntes) {

        ArrayList<String> mAlocados = copiarAlocados(mAlocadosAntes);


        for (AST mAST : ASTPai.getASTS()) {
            if (mAST.mesmoTipo("REQUIRED")) {

            } else if (mAST.mesmoTipo("STRUCT")) {

                mTipados.add(mAST.getNome());


            } else if (mAST.mesmoTipo("STAGES")) {

                mTipados.add(mAST.getNome());
            } else if (mAST.mesmoTipo("CAST")) {

                mTipados.add(mAST.getNome());
            } else if (mAST.mesmoTipo("TYPE")) {

                mTipados.add(mAST.getNome());

            }
        }

        mAnalisar_Outros.inclusao(ASTPai);

        for (AST mAST : ASTPai.getASTS()) {

            if (mAST.mesmoTipo("DEFINE")) {

                if (mProibidos.contains(mAST.getNome())) {
                    mErros.add("Define : " + mAST.getNome() + " : Nome Proibido !");
                }

                analisarAlocacao(mAST, mAlocados);

                mAnalisar_Outros.analisarTipagem(mAST);
                analisarValoracao(ASTPai, mAlocadosAntes);


            } else if (mAST.mesmoTipo("MOCKIZ")) {

                if (mProibidos.contains(mAST.getNome())) {
                    mErros.add("Mockiz : " + mAST.getNome() + " : Nome Proibido !");
                }

                analisarAlocacao(mAST, mAlocados);


                mAnalisar_Outros.analisarTipagem(mAST);
                analisarValoracao(ASTPai, mAlocadosAntes);


            }

        }


        for (AST mAST : ASTPai.getASTS()) {

            if (mAST.mesmoTipo("ACTION")) {

                if (mProibidos.contains(mAST.getNome())) {
                    mErros.add("Action : " + mAST.getNome() + " : Nome Proibido !");
                }

                mAnalisar_Action.analisarAction(mAST, mAlocados);

            } else if (mAST.mesmoTipo("FUNCTION")) {

                if (mProibidos.contains(mAST.getNome())) {
                    mErros.add("Function : " + mAST.getNome() + " : Nome Proibido !");
                }

                mAnalisar_Function.analisarFunction(mAST, mAlocados);

            } else if (mAST.mesmoTipo("CALL")) {
            } else if (mAST.mesmoTipo("INVOKE")) {

            } else if (mAST.mesmoTipo("DEFINE")) {

                if (mProibidos.contains(mAST.getNome())) {
                    mErros.add("Define : " + mAST.getNome() + " : Nome Proibido !");
                }


            } else if (mAST.mesmoTipo("MOCKIZ")) {

                if (mProibidos.contains(mAST.getNome())) {
                    mErros.add("Mockiz : " + mAST.getNome() + " : Nome Proibido !");
                }


            } else if (mAST.mesmoTipo("OPERATOR")) {

                mAnalisar_Function.analisarFunction(mAST, mAlocados);

            } else if (mAST.mesmoTipo("DIRECTOR")) {

                mAnalisar_Function.analisarFunction(mAST, mAlocados);

            } else if (mAST.mesmoTipo("CAST")) {

                if (mProibidos.contains(mAST.getNome())) {
                    mErros.add("Cast : " + mAST.getNome() + " : Nome Proibido !");
                }

                mAnalisar_Cast.init(mAST);

            } else if (mAST.mesmoTipo("STRUCT")) {

                if (mProibidos.contains(mAST.getNome())) {
                    mErros.add("Struct : " + mAST.getNome() + " : Nome Proibido !");
                }

                Analisar_Struct mAnalisar_Struct = new Analisar_Struct(this);
                mAnalisar_Struct.init_Struct(mAST, mAlocados);

            } else if (mAST.mesmoTipo("STAGES")) {

                if (mProibidos.contains(mAST.getNome())) {
                    mErros.add("Stage : " + mAST.getNome() + " : Nome Proibido !");
                }

                mAnalisar_Stage.analisar(mAST);

            } else if (mAST.mesmoTipo("REQUIRED")) {

            } else if (mAST.mesmoTipo("MODEL")) {

            } else if (mAST.mesmoTipo("TYPE")) {

            } else {

                mErros.add("AST x : " + mAST.getTipo());


            }


        }


        mAnalisar_Outros.exportarOperadores(ASTPai);

    }


}
