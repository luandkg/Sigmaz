package Sigmaz.S05_PosProcessamento.Processadores;

import Sigmaz.S07_Executor.Alterador;

import java.util.ArrayList;

import Sigmaz.S05_PosProcessamento.PosProcessador;
import Sigmaz.S00_Utilitarios.AST;

public class Modelador {

    private PosProcessador mAnalisador;

    private boolean mExterno;

    public Modelador(PosProcessador eAnalisador) {

        mAnalisador = eAnalisador;
        mExterno = true;

    }


    public ArrayList<String> getRefers(AST eAST) {

        ArrayList<String> mRefers = new ArrayList<String>();

        for (AST PackageStruct : eAST.getASTS()) {
            if (PackageStruct.mesmoTipo("REFER")) {
                mRefers.add(PackageStruct.getNome());
            }
        }
        return mRefers;
    }


    public void init(ArrayList<AST> mTodos) {

        mAnalisador.mensagem("");
        mAnalisador.mensagem(" ------------------ FASE MODELAGEM ----------------------- ");
        mAnalisador.mensagem("");

        ArrayList<AST> mPacotes = new ArrayList<AST>();

        ArrayList<AST> mPacotesRequisitados = new ArrayList<AST>();
        ArrayList<AST> mModelosRequisitados = new ArrayList<AST>();

        for (AST mRequisicao : mAnalisador.getRequisicoes()) {
            for (AST mAST : mRequisicao.getASTS()) {

                if (mAST.mesmoTipo("PACKAGE")) {

                    mPacotesRequisitados.add(mAST);

                    mAnalisador.mensagem(" Pacote Externo :: " + mAST.getNome());

                } else if (mAST.mesmoTipo("MODEL")) {

                    mAnalisador.mensagem(" Modelo Externo :: " + mAST.getNome());

                    mModelosRequisitados.add(mAST);

                }
            }

        }


        for (AST mAST : mTodos) {

            if (mAST.mesmoTipo("SIGMAZ")) {

                ArrayList<AST> mPacotes_Completo = new ArrayList<AST>();
                ArrayList<AST> mModelos_Completo = new ArrayList<AST>();

                ArrayList<AST> mModelosRaiz = new ArrayList<AST>();

                for (AST Struct_AST : mPacotesRequisitados) {
                    mPacotes_Completo.add(Struct_AST);
                }

                for (AST Struct_AST : mModelosRequisitados) {
                    mModelos_Completo.add(Struct_AST);
                }

                for (AST Struct_AST : mAST.getASTS()) {

                    if (Struct_AST.mesmoTipo("PACKAGE")) {

                        mPacotes.add(Struct_AST.copiar());
                        mPacotes_Completo.add(Struct_AST.copiar());

                    } else if (Struct_AST.mesmoTipo("MODEL")) {

                        mModelosRaiz.add(Struct_AST);
                        mModelos_Completo.add(Struct_AST);

                    }

                }

                ArrayList<String> mRefers = getRefers(mAST);

                ArrayList<AST> mModelosSigmaz = new ArrayList<AST>();

                for (AST Struct_AST : mModelosRequisitados) {
                    mModelosSigmaz.add(Struct_AST);
                }

                for (AST eModelo : mModelosRaiz) {
                    mModelosSigmaz.add(eModelo);
                }
                for (AST eModelo : getModelos(mRefers, mPacotes_Completo)) {
                    mModelosSigmaz.add(eModelo);
                }


                modelador(true, "SIGMAZ", mAST, mModelosSigmaz);


                for (AST ePacote : mPacotes) {

                    pacote_modelador(ePacote, mModelos_Completo, mPacotes_Completo);

                }


            }
        }


    }


    public void modelador(boolean eRaiz, String eNome, AST ASTPai, ArrayList<AST> modelos) {


        ArrayList<String> mNomes = new ArrayList<String>();

        ArrayList<AST> mEstruturasComModelos = new ArrayList<AST>();

        for (AST Struct_AST : ASTPai.getASTS()) {

            if (Struct_AST.mesmoTipo("STRUCT")) {

                AST AST_Model = Struct_AST.getBranch("MODEL");
                if (AST_Model.mesmoValor("TRUE")) {
                    mEstruturasComModelos.add(Struct_AST);
                }
            }


        }
        for (AST Struct_AST : modelos) {
            mNomes.add(Struct_AST.getNome());
        }


        if (modelos.size() == 0 && mEstruturasComModelos.size() == 0) {


        } else {

            if (eRaiz) {
                mAnalisador.mensagem("------------------------- MODEL " + eNome + " --------------------------");
            } else {
                mAnalisador.mensagem("------------------------- MODEL - PACKAGE : " + eNome + " --------------------------");
            }

            mAnalisador.mensagem(" MODELS : " + modelos.size());

            for (AST Struct_AST : modelos) {
                mAnalisador.mensagem("\t - " + Struct_AST.getNome());
            }

            mAnalisador.mensagem(" STRUCTS : " + mEstruturasComModelos.size());
            for (AST Struct_AST : mEstruturasComModelos) {
                mAnalisador.mensagem("\t - " + Struct_AST.getNome() + " -> " + Struct_AST.getBranch("MODEL").getNome());
            }

            mAnalisador.mensagem("");

            for (AST Struct_AST : mEstruturasComModelos) {

                String eModel_Nome = Struct_AST.getBranch("MODEL").getNome();

                mAnalisador.mensagem(" MODELANDO : " + Struct_AST.getNome());
                mAnalisador.mensagem("\t - " + Struct_AST.getNome());

                if (mNomes.contains(eModel_Nome)) {

                    modelar(Struct_AST, eModel_Nome, modelos);

                } else {
                    mAnalisador.getErros().add("  - Model " + eModel_Nome + " : Nao encontrado ");
                }


            }

            mAnalisador.mensagem("---------------------------------------------------------------------------");


        }


    }


    public void pacote_modelador(AST ePacote, ArrayList<AST> mModelosRaiz, ArrayList<AST> mPacotes) {


        ArrayList<AST> mModelosPacote = new ArrayList<AST>();

        for (AST Struct_AST : ePacote.getASTS()) {

            if (Struct_AST.mesmoTipo("MODEL")) {
                mModelosPacote.add(Struct_AST);
            }
        }
        for (AST eModelo : getModelos(getRefers(ePacote), mPacotes)) {
            mModelosPacote.add(eModelo);
        }

        for (AST eModelo : mModelosRaiz) {
            mModelosPacote.add(eModelo);
        }

        modelador(false, ePacote.getNome(), ePacote, mModelosPacote);


    }

    public ArrayList<AST> getModelos(ArrayList<String> mRefers, ArrayList<AST> mPacotes) {

        ArrayList<AST> modelos = new ArrayList<AST>();

        for (String eRefer : mRefers) {

            AST mPacote = null;
            boolean PacoteEnc = false;

            for (AST ePacote : mPacotes) {
                if (ePacote.mesmoNome(eRefer)) {
                    mPacote = ePacote;
                    PacoteEnc = true;
                    break;
                }
            }

            if (PacoteEnc) {

                for (AST eAST : mPacote.getASTS()) {
                    if (eAST.mesmoTipo("MODEL")) {
                        modelos.add(eAST);
                    }

                }

            } else {


                mAnalisador.getErros().add("Pacote " + eRefer + " : Nao encontrado !");
                break;
            }

        }

        return modelos;
    }


    public void modelar(AST Struct, String eModelo, ArrayList<AST> mModelos) {


        AST Modelo = ProcurarModelo(eModelo, mModelos).copiar();


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

        for (AST Parte : Modelo.getASTS()) {


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

                    if (eModelo_Tipo.contentEquals(eStruct_Tipo)) {

                    } else {
                        mAnalisador.getErros().add(" Struct " + Struct.getNome() + " Precisa ter um DEFINE : " + DentroStruct.getNome() + " : " + eModelo_Tipo);
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
