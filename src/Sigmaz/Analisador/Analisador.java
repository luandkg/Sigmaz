package Sigmaz.Analisador;

import Sigmaz.Analisador.ASTS.Analisar_Global;
import Sigmaz.Analisador.ASTS.Analisar_Package;
import Sigmaz.Executor.RunTime;

import java.io.File;
import java.util.ArrayList;
import Sigmaz.Utils.AST;
import Sigmaz.Utils.Tempo;

public class Analisador {

    private ArrayList<AST> mASTS;

    private ArrayList<String> mErros;
    private ArrayList<String> mProibidos;

    private ArrayList<String> mPrimitivos;

    private Analisar_Global mAnalisar_Global;

    private Analisar_Package mAnalisar_Package;



    private boolean mExterno;

    private ArrayList<String> eMensagens;
    private ArrayList<AST> mPacotes;

    public Analisador() {

        mASTS = new ArrayList<>();

        eMensagens = new ArrayList<String>();

        mErros = new ArrayList<>();
        mProibidos = new ArrayList<>();


        mAnalisar_Global = new Analisar_Global(this);

        mPacotes = new ArrayList<AST>();

        mAnalisar_Package = new Analisar_Package(this);




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

        mProibidos.add("this");


        mPrimitivos = new ArrayList<String>();
        mPrimitivos.add("num");
        mPrimitivos.add("string");
        mPrimitivos.add("bool");
        mPrimitivos.add("int");


    }


    public ArrayList<String> getPrimitivos() {
        return mPrimitivos;
    }

    public Analisar_Global getAnalisar_Global() {
        return mAnalisar_Global;
    }


    public Analisar_Package getAnalisar_Package() {
        return mAnalisar_Package;
    }


    public void mensagem(String eMensagem) {
        eMensagens.add(eMensagem);
    }

    public ArrayList<String> getMensagens() {
        return eMensagens;
    }


    public ArrayList<String> getErros() {
        return mErros;
    }


    public ArrayList<AST> getPackages() {
        return mAnalisar_Package.getPackages();
    }


    public void init(ArrayList<AST> eASTs, String mLocal) {
        mASTS = eASTs;
        mErros.clear();
        mPacotes.clear();

        //  getAnalisar_Outros().limpar();


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

        ArrayList<AST> mReqAST = new ArrayList<AST>();

        for (String mReq : mRequiscoes) {

            File arq = new File(mReq);

            if (arq.exists()) {


                RunTime RunTimeC = new RunTime();

                try {
                    RunTimeC.init(mReq);

                    mReqAST.add(RunTimeC.getBranch("SIGMAZ"));


                } catch (Exception e) {
                    mErros.add("Library " + mReq + " : Problema ao carregar !");
                }

            } else {
                mErros.add("Library " + mReq + " : Nao Encontrado !");
            }

        }


        for (AST ASTCGlobal : mASTS) {

            if (ASTCGlobal.mesmoTipo("SIGMAZ")) {


                for (AST mAST : ASTCGlobal.getASTS()) {
                    if (mAST.mesmoTipo("PACKAGE")) {
                        mensagem("Sigmaz Package :  " + mAST.getNome());
                        mPacotes.add(mAST);
                    }
                }

                for (AST mAST : mPacotes) {

                    getAnalisar_Global().analisarGlobal(mAST, mReqAST);

                }


                getAnalisar_Global().analisarGlobal(ASTCGlobal, mReqAST);

            } else {

                mErros.add("Escopo Desconhecido : " + ASTCGlobal.getTipo());

            }

        }



    }

    public void MostrarMensagens() {

        System.out.println("\n\t MENSAGENS DE ANALISE : ");

        for (String Erro : getMensagens()) {
            System.out.println("\t" + Erro);
        }

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


    public ArrayList<String> getProibidos() {
        return mProibidos;
    }

    public ArrayList<AST> getPacotes() {
        return mPacotes;
    }

    public String getData() {
        return Tempo.getData();
    }

}
