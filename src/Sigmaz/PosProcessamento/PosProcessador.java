package Sigmaz.PosProcessamento;

import Sigmaz.Executor.RunTime;

import java.io.File;
import java.util.ArrayList;
import Sigmaz.Utils.AST;

public class PosProcessador {

    private ArrayList<AST> mASTS;
    private ArrayList<String> mErros;
    private ArrayList<String> eMensagens;
    private ArrayList<AST> mPacotes;

    public PosProcessador(){

        mASTS = new ArrayList<>();

        eMensagens = new ArrayList<String>();

        mErros = new ArrayList<>();

        mPacotes = new ArrayList<AST>();

    }


    public void init(ArrayList<AST> eASTs, String mLocal){

        mASTS = eASTs;
        mErros.clear();
        mPacotes.clear();


        mensagem("");


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


            } else {

                mErros.add("Escopo Desconhecido : " + ASTCGlobal.getTipo());

            }

        }

        Alocador mAlocador = new Alocador(this);
        mAlocador.init(mASTS);


        Tipador mTipador = new Tipador(this);
        mTipador.init(mASTS);

        Heranca mHeranca = new Heranca(this);
        mHeranca.init(mASTS);

        Modelagem mModelagem = new Modelagem(this);
        mModelagem.init(mASTS);

        Estagiador mEstagiador = new Estagiador(this);
        mEstagiador.init(mASTS);

        Referenciador mReferenciador = new Referenciador(this);
        mReferenciador.init(mASTS);

        Opcionador mOpcionador = new Opcionador(this);
        mOpcionador.init(mASTS);

    }


    public ArrayList<String> getErros() {
        return mErros;
    }

    public void mensagem(String eMensagem) {
        eMensagens.add(eMensagem);
    }
    public void errar(String eErro) {
        mErros.add(eErro);
    }

    public ArrayList<String> getMensagens() {
        return eMensagens;
    }


    public void MostrarMensagens() {

        System.out.println("\n\t MENSAGENS DE POS-PROCESSAMENTO : ");

        for (String Erro : getMensagens()) {
            System.out.println("\t" + Erro);
        }

    }

}
