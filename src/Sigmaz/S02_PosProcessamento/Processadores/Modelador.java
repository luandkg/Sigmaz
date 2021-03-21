package Sigmaz.S02_PosProcessamento.Processadores;

import Sigmaz.S08_Utilitarios.Alterador.SigmazModel;
import Sigmaz.S08_Utilitarios.Alterador.SigmazPackage;
import Sigmaz.S08_Utilitarios.Alterador.SigmazRaiz;
import Sigmaz.S08_Utilitarios.Alterador.SigmazStruct;
import Sigmaz.S08_Utilitarios.Procurador;
import Sigmaz.S08_Utilitarios.ObjetoProcurado;
import Sigmaz.S05_Executor.Alterador;

import java.util.ArrayList;

import Sigmaz.S02_PosProcessamento.PosProcessador;
import Sigmaz.S08_Utilitarios.AST;

public class Modelador {

    private PosProcessador mAnalisador;


    private String CRIADO = "2020 09 10";
    private String ATUALIZADO = "2020 10 28";
    private String VERSAO = "2.0";

    public Modelador(PosProcessador eAnalisador) {

        mAnalisador = eAnalisador;

    }



    public void mensagem(String e) {

        if (mAnalisador.getDebug_Modelador()) {
            mAnalisador.mensagem(e);
        }
    }

    public void errar(String e) {
        mAnalisador.errar(e);
    }

    public void init(ArrayList<AST> mTodos) {

        mensagem("");
        mensagem(" ------------------ FASE MODELAGEM ----------------------- ");
        mensagem("");

        ArrayList<SigmazPackage> mPacotes = new ArrayList<SigmazPackage>();

        ArrayList<AST> mPacotesRequisitados = new ArrayList<AST>();
        ArrayList<AST> mModelosRequisitados = new ArrayList<AST>();


        for (AST mAST : mTodos) {

            if (mAST.mesmoTipo("SIGMAZ")) {

                SigmazRaiz mSigmazRaiz = new SigmazRaiz(mAST);

                for (AST Struct_AST : mAST.getASTS()) {
                    if (Struct_AST.mesmoTipo("PACKAGE")) {
                        mPacotes.add(new SigmazPackage(Struct_AST));
                    }
                }


                processarSigmaz(mSigmazRaiz, mPacotes);

                for (SigmazPackage ePacote : mPacotes) {
                    processarPacote(mSigmazRaiz, ePacote, mPacotes);
                }

            }

        }


    }


    public void processarSigmaz(SigmazRaiz mSigmazRaiz, ArrayList<SigmazPackage> mPacotes) {


        mensagem("");

        for (SigmazStruct mStruct : mSigmazRaiz.getStructs()) {

            mensagem("STRUCT : " + mStruct.getNome());

            if (mStruct.isModelada()) {

                mensagem("\t - MODELO : " + mStruct.getModelo());

                Procurador mProcurador = new Procurador();
                ObjetoProcurado<SigmazModel> mProcurandoModelo = mProcurador.procurarModelo_Sigmaz(mSigmazRaiz, mPacotes, mStruct.getModelo());

                if (mProcurandoModelo.getEncontrado()) {

                    mensagem("\t - ORIGEM : " + mProcurandoModelo.getOrigem());

                    modelar(mStruct.getLeitura(), mProcurandoModelo.getObjeto().getLeitura());

                } else {
                    errar("Modelo nao encontrado : " + mStruct.getModelo());
                }


            }


        }


    }

    public void processarPacote(SigmazRaiz mSigmazRaiz, SigmazPackage mSigmazPackage, ArrayList<SigmazPackage> mPacotes) {


        mensagem("Pacote : " + mSigmazPackage.getNome());

        for (SigmazStruct mStruct : mSigmazPackage.getStructs()) {

            mensagem("\tSTRUCT : " + mStruct.getNome());

            if (mStruct.isModelada()) {

                mensagem("\t\t - MODELO : " + mStruct.getModelo());

                Procurador mProcurador = new Procurador();
                ObjetoProcurado<SigmazModel> mProcurandoModelo = mProcurador.procurarModelo_Package(mStruct.getModelo(), mSigmazRaiz, mSigmazPackage, mPacotes);

                if (mProcurandoModelo.getEncontrado()) {

                    mensagem("\t\t - ORIGEM : " + mProcurandoModelo.getOrigem());

                    modelar(mStruct.getLeitura(), mProcurandoModelo.getObjeto().getLeitura());

                } else {
                    errar("Modelo nao encontrado : " + mStruct.getModelo());
                }


            }


        }

    }


    public void modelar(AST Struct, AST Modelo) {


        AST mStruct_Generic = Struct.getBranch("MODEL").getBranch("GENERIC");


        AST mModelo_Generic = Modelo.getBranch("GENERIC");


        if (mModelo_Generic.mesmoNome("TRUE") && mStruct_Generic.mesmoNome("TRUE")) {

            int initContagem = 0;
            int StructContagem = 0;

            for (AST ASTC : mModelo_Generic.getASTS()) {
                initContagem += 1;
            }
            for (AST ASTC : mStruct_Generic.getASTS()) {
                StructContagem += 1;
            }


            if (StructContagem == initContagem) {

                Alterador mAlterador = new Alterador();


                //  System.out.println("ALTERADOR " + eNome);

                int i = 0;
                //      for (AST eSub : mStructGeneric.getASTS()) {
                for (AST eSub : mModelo_Generic.getASTS()) {

                    AST sInit = mStruct_Generic.getASTS().get(i);

                    //    mAnalisador.mensagem("Alterando " + eSub.getNome() + " -> " + sInit.getNome());

                    mAlterador.adicionar(eSub.getNome(), sInit);


                    i += 1;
                }

                mAlterador.alterar(Modelo);


            } else {

                mAnalisador.errar("Modelagem de " + Struct.getNome() + " com " + Modelo.getNome() + " : Precisa de " + initContagem + " Tipos Abstratos   !");

            }


        } else if (mModelo_Generic.mesmoNome("FALSE") && mStruct_Generic.mesmoNome("FALSE")) {

        } else if (mModelo_Generic.mesmoNome("FALSE") && mStruct_Generic.mesmoNome("TRUE")) {
            mAnalisador.errar("O Modelo  " + Modelo.getNome() + " : Nao e generico !");
        } else if (mModelo_Generic.mesmoNome("TRUE") && mStruct_Generic.mesmoNome("FALSE")) {


            if (mModelo_Generic.getASTS().size() == 0) {

            } else {
                mAnalisador.errar("O Modelo  " + Modelo.getNome() + " : precisa de Tipos Abstratos na Struct " + Struct.getNome());
            }


        } else {
            mAnalisador.errar("Struct " + Struct.getNome() + " : Nao e Generica !");
        }

        if (mAnalisador.getErros().size() > 0) {
            return;
        }


        if (mAnalisador.getErros().size() > 0) {
            return;
        }


        for (AST Parte : Modelo.getBranch("BODY").getASTS()) {


            if (Parte.mesmoTipo("DEFINE")) {

                Verificar_Define(Struct, Parte);
            } else if (Parte.mesmoTipo("MOCKIZ")) {

                Verificar_Mockiz(Struct, Parte);
            } else if (Parte.mesmoTipo("ACTION")) {

                Verificar_Action(Struct, Parte);

            } else if (Parte.mesmoTipo("FUNCTION")) {

                Verificar_Function(Struct, Parte);


            }

        }

    }

    public void Verificar_Define(AST Struct, AST Modelo) {

        boolean enc = false;

        for (AST DentroStruct : Struct.getBranch("BODY").getASTS()) {

            if (DentroStruct.mesmoTipo("DEFINE")) {
                if (DentroStruct.mesmoNome(Modelo.getNome())) {

                    String eModelo_Tipo = getTipagem(Modelo);
                    String eStruct_Tipo = getTipagem(DentroStruct);
                    String eVisibilidade = getVisibilidade(DentroStruct);

                    if (eVisibilidade.contentEquals("ALL")) {

                        if (eModelo_Tipo.contentEquals(eStruct_Tipo)) {

                        } else {
                            mAnalisador.getErros().add(" Struct " + Struct.getNome() + " Precisa ter um DEFINE : " + DentroStruct.getNome() + " : " + eModelo_Tipo);
                        }

                    } else if (eVisibilidade.contentEquals("RESTRICT")) {
                        mAnalisador.getErros().add(" Struct " + Struct.getNome() + " Precisa alterar a visibilidade da DEFINE : " + DentroStruct.getNome() + " : " + eModelo_Tipo);
                    } else if (eVisibilidade.contentEquals("IMPLICIT")) {
                        mAnalisador.getErros().add(" Struct " + Struct.getNome() + " Precisa alterar a visibilidade da DEFINE : " + DentroStruct.getNome() + " : " + eModelo_Tipo);
                    } else if (eVisibilidade.contentEquals("EXPLICIT")) {
                        mAnalisador.getErros().add(" Struct " + Struct.getNome() + " Precisa alterar a visibilidade da DEFINE : " + DentroStruct.getNome() + " : " + eModelo_Tipo);
                    }


                    enc = true;
                    break;
                }
            }

        }

        if (!enc) {
            mAnalisador.getErros().add(" Struct " + Struct.getNome() + " Precisa ter um DEFINE : " + Modelo.getNome() + " : " + getTipagem(Modelo));
        }


    }

    public String getVisibilidade(AST Struct) {
        return Struct.getBranch("VISIBILITY").getNome();
    }

    public void Verificar_Mockiz(AST Struct, AST Modelo) {

        boolean enc = false;

        for (AST DentroStruct : Struct.getBranch("BODY").getASTS()) {

            if (DentroStruct.mesmoTipo("MOCKIZ")) {
                if (DentroStruct.mesmoNome(Modelo.getNome())) {

                    String eModelo_Tipo = getTipagem(Modelo);
                    String eStruct_Tipo = getTipagem(DentroStruct);

                    if (eModelo_Tipo.contentEquals(eStruct_Tipo)) {

                    } else {
                        mAnalisador.getErros().add(" Struct " + Struct.getNome() + " Precisa ter um MOCKIZ : " + DentroStruct.getNome() + " : " + eModelo_Tipo);
                    }


                    enc = true;
                    break;
                }
            }

        }

        if (!enc) {
            mAnalisador.getErros().add(" Struct " + Struct.getNome() + " Precisa ter um MOCKIZ : " + Modelo.getNome() + " : " + getTipagem(Modelo));
        }


    }


    public void Verificar_Action(AST Struct, AST Modelo) {

        boolean enc = false;
        boolean alguma = false;


        String eModelo_Parametragem = getParametragem(Modelo);

        for (AST DentroStruct : Struct.getBranch("BODY").getASTS()) {

            if (DentroStruct.mesmoTipo("ACTION")) {
                if (DentroStruct.mesmoNome(Modelo.getNome())) {
                    enc = true;


                    String eStruct_Parametragem = getParametragemCom(DentroStruct);

                    if (eModelo_Parametragem.contentEquals(eStruct_Parametragem)) {
                        alguma = true;
                        break;

                    }

                }
            }

        }


        if (!enc) {
            mAnalisador.getErros().add(" Struct " + Struct.getNome() + " Precisa ter um ACTION : " + Modelo.getNome() + "(" + getParametragem(Modelo) + ")");
        } else {

            if (!alguma) {
                mAnalisador.getErros().add(" Struct " + Struct.getNome() + " Precisa ter um ACTION : " + Modelo.getNome() + "(" + getParametragem(Modelo) + ")");
            }

        }


    }

    public void Verificar_Function(AST Struct, AST Modelo) {

        boolean enc = false;
        boolean alguma = false;

        for (AST DentroStruct : Struct.getBranch("BODY").getASTS()) {

            if (DentroStruct.mesmoTipo("FUNCTION")) {
                if (DentroStruct.mesmoNome(Modelo.getNome())) {
                    enc = true;

                    String eModelo_Tipo = getTipagem(Modelo);
                    String eStruct_Tipo = getTipagem(DentroStruct);

                    if (eModelo_Tipo.contentEquals(eStruct_Tipo)) {


                        String eModelo_Parametragem = getParametragem(Modelo);
                        String eStruct_Parametragem = getParametragemCom(DentroStruct);

                        if (eModelo_Parametragem.contentEquals(eStruct_Parametragem)) {
                            alguma = true;
                            break;
                        }

                    }


                }
            }

        }


        if (!enc) {
            mAnalisador.getErros().add(" Struct " + Struct.getNome() + " Precisa ter um FUNCTION : " + Modelo.getNome() + " ( " + getParametragem(Modelo) + ") : " + getTipagem(Modelo));
        } else {

            if (!alguma) {
                mAnalisador.getErros().add(" Struct " + Struct.getNome() + " Precisa ter um FUNCTION : " + Modelo.getNome() + " ( " + getParametragem(Modelo) + ") : " + getTipagem(Modelo));
            }

        }


    }


    public String getParametragem(AST ASTpai) {
        AST mArguments = ASTpai.getBranch("ARGUMENTS");


        String eParam = "";

        for (AST eTipando : mArguments.getASTS()) {
            eParam += "<" + getTipagemDireta(eTipando) + ">";
        }


        return eParam;
    }

    public String getParametragemCom(AST ASTpai) {
        AST mArguments = ASTpai.getBranch("ARGUMENTS");


        String eParam = "";

        for (AST eTipando : mArguments.getASTS()) {
            eParam += "<" + getTipagem(eTipando) + ">";
        }


        return eParam;
    }

    public String getTipagem(AST eASTPai) {

        AST eAST = eASTPai.getBranch("TYPE");

        String mTipagem = "";

        if (eAST.mesmoValor("GENERIC")) {

            for (AST eTipando : eAST.getASTS()) {
                mTipagem += "<" + getTipagemDireta(eTipando) + ">";
            }

        } else if (eAST.mesmoValor("CONCRETE")) {
            mTipagem = eAST.getNome();
        }


        return mTipagem;

    }

    public String getTipagemDireta(AST eASTPai) {

        String mTipagem = "";

        if (eASTPai.mesmoValor("GENERIC")) {

            for (AST eTipando : eASTPai.getASTS()) {
                mTipagem += "<" + getTipagemDireta(eTipando) + ">";
            }

        } else if (eASTPai.mesmoValor("CONCRETE")) {
            mTipagem = eASTPai.getNome();
        }


        return mTipagem;

    }

    public AST ProcurarModelo(String eNome, ArrayList<AST> mModelos) {
        AST mRet = null;
        for (AST Procurando : mModelos) {
            if (Procurando.mesmoNome(eNome)) {
                mRet = Procurando;
            }
        }
        return mRet;
    }


}
