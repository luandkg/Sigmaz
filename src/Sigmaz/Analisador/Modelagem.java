package Sigmaz.Analisador;

import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Modelagem {

    private Analisador mAnalisador;

    private boolean mExterno;

    public Modelagem(Analisador eAnalisador) {

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


        ArrayList<AST> mPacotes = new ArrayList<AST>();

        for (AST mAST : mTodos) {

            if (mAST.mesmoTipo("SIGMAZ")) {

                ArrayList<AST> mModelosRaiz = new ArrayList<AST>();

                for (AST Struct_AST : mAST.getASTS()) {

                    if (Struct_AST.mesmoTipo("PACKAGE")) {
                        mPacotes.add(Struct_AST.copiar());
                    } else if (Struct_AST.mesmoTipo("MODEL")) {
                        mModelosRaiz.add(Struct_AST);
                    }

                }

                ArrayList<String> mRefers = getRefers(mAST);

                ArrayList<AST> mModelosSigmaz = new ArrayList<AST>();

                for (AST eModelo : mModelosRaiz) {
                    mModelosSigmaz.add(eModelo);
                }
                for (AST eModelo : getModelos(mRefers, mPacotes)) {
                    mModelosSigmaz.add(eModelo);
                }

                modelador(mAST, mModelosSigmaz);


                for (AST ePacote : mPacotes) {

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

                    modelador(ePacote, mModelosPacote);

                }


            }
        }


    }


    public void modelador(AST ASTPai, ArrayList<AST> modelos) {

        mAnalisador.mensagem("------------------------- MODEL --------------------------");

        mAnalisador.mensagem(" MODELS : ");

        ArrayList<String> mNomes = new ArrayList<String>();

        for (AST Struct_AST : modelos) {
            mAnalisador.mensagem("\t - " + Struct_AST.getNome());
            mNomes.add(Struct_AST.getNome());
        }

        ArrayList<AST> mEstruturasComModelos = new ArrayList<AST>();

        for (AST Struct_AST : ASTPai.getASTS()) {

            if (Struct_AST.mesmoTipo("STRUCT")) {

                AST AST_Model = Struct_AST.getBranch("MODEL");
                if (AST_Model.mesmoValor("TRUE")) {
                    mEstruturasComModelos.add(Struct_AST);
                }
            }


        }

        mAnalisador.mensagem(" STRUCTS : ");
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

        mAnalisador.mensagem("---------------------------------------------------");


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

        AST Modelo = ProcurarModelo(eModelo, mModelos);


        for (AST Parte : Modelo.getASTS()) {


            if (Parte.mesmoTipo("DEFINE")) {

                Verificar_Define(Struct, Parte);
            } else if (Parte.mesmoTipo("MOCKIZ")) {

                Verificar_Mockiz(Struct, Parte);
            } else if (Parte.mesmoTipo("ACTION")) {

                Verificar_Action(Struct, Parte);

            } else if (Parte.mesmoTipo("FUNCTION")) {

                Verificar_Function(Struct, Parte);

            } else {

                mAnalisador.mensagem("\t - Precisa : " + Parte.getTipo() + " " + Parte.getNome());


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
