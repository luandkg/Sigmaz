package Sigmaz.S02_PosProcessamento.Processadores;

import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S00_Utilitarios.Visualizador.*;
import Sigmaz.S02_PosProcessamento.PosProcessador;


import java.util.ArrayList;

public class Unicidade {

    private PosProcessador mAnalisador;


    private String CRIADO = "2020 10 28";
    private String ATUALIZADO = "2020 10 28";
    private String VERSAO = "1.0";

    public Unicidade(PosProcessador eAnalisador) {

        mAnalisador = eAnalisador;

    }


    public void mensagem(String e) {

        if (mAnalisador.getDebug_Unicidade()) {
            mAnalisador.mensagem(e);
        }
    }

    public void errar(String e) {
        mAnalisador.errar(e);
    }

    public void init(ArrayList<AST> mTodos) {

        mensagem("");
        mensagem(" ------------------ FASE UNICIDADE ----------------------- ");
        mensagem("");

        ArrayList<SigmazPackage> mPacotes = new ArrayList<SigmazPackage>();


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


        mensagem("-->> SIGMAZ");

        for (SigmazCast mCorrente : mSigmazRaiz.getCasts()) {

            int v1 = getSigmaz(mSigmazRaiz, mCorrente.getNome());
            int v2 = getRefers(mSigmazRaiz.getRefers(), mPacotes, mCorrente.getNome());

            int v = v1 + v2;

            if (v == 1) {
                mensagem("\tCast : " + mCorrente.getNome() + " -->> OK");
            } else {
                mensagem("\tCast : " + mCorrente.getNome() + " -->> Problema");

                if (v1 > 1) {
                    errar("Conflito de ambiguididade local com a Cast " + mCorrente.getNome() + " de forma SIGMAZ !");
                }

                if (v2 > 0) {
                    errar("Conflito de ambiguididade local com a Cast " + mCorrente.getNome() + " de forma REFER !");
                }

            }

        }

        for (SigmazStruct mCorrente : mSigmazRaiz.getStructs()) {

            int v1 = getSigmaz(mSigmazRaiz, mCorrente.getNome());
            int v2 = getRefers(mSigmazRaiz.getRefers(), mPacotes, mCorrente.getNome());
            int v = v1 + v2;

            if (v == 1) {
                mensagem("\tStruct : " + mCorrente.getNome() + " -->> OK");
            } else {
                mensagem("\tStruct : " + mCorrente.getNome() + " -->> Problema");

                if (v1 > 1) {
                    errar("Conflito de ambiguididade local com a Struct " + mCorrente.getNome() + " de forma SIGMAZ !");
                }

                if (v2 > 0) {
                    errar("Conflito de ambiguididade local com a Struct " + mCorrente.getNome() + " de forma REFER !");
                }

            }


        }


        for (SigmazType mCorrente : mSigmazRaiz.getTypes()) {

            int v1 = getSigmaz(mSigmazRaiz, mCorrente.getNome());
            int v2 = getRefers(mSigmazRaiz.getRefers(), mPacotes, mCorrente.getNome());
            int v = v1 + v2;

            if (v == 1) {
                mensagem("\tType : " + mCorrente.getNome() + " -->> OK");
            } else {
                mensagem("\tType : " + mCorrente.getNome() + " -->> Problema");

                if (v1 > 1) {
                    errar("Conflito de ambiguididade local com a Type " + mCorrente.getNome() + " de forma SIGMAZ !");
                }

                if (v2 > 0) {
                    errar("Conflito de ambiguididade local com a Type " + mCorrente.getNome() + " de forma REFER !");
                }

            }
        }

        for (SigmazModel mCorrente : mSigmazRaiz.getModelos()) {

            int v1 = getSigmaz(mSigmazRaiz, mCorrente.getNome());
            int v2 = getRefers(mSigmazRaiz.getRefers(), mPacotes, mCorrente.getNome());
            int v = v1 + v2;

            if (v == 1) {
                mensagem("\tModelo : " + mCorrente.getNome() + " -->> OK");
            } else {
                mensagem("\tModelo : " + mCorrente.getNome() + " -->> Problema");

                if (v1 > 1) {
                    errar("Conflito de ambiguididade local com o Modelo " + mCorrente.getNome() + " de forma SIGMAZ !");
                }

                if (v2 > 0) {
                    errar("Conflito de ambiguididade local com o Modelo " + mCorrente.getNome() + " de forma REFER !");
                }


            }
        }

        for (SigmazExternal mCorrente : mSigmazRaiz.getExternals()) {

            int v1 = getSigmaz(mSigmazRaiz, mCorrente.getNome());
            int v2 = getRefers(mSigmazRaiz.getRefers(), mPacotes, mCorrente.getNome());
            int v = v1 + v2;

            if (v == 1) {
                mensagem("\tExternal : " + mCorrente.getNome() + " -->> OK");
            } else {
                mensagem("\tExternal : " + mCorrente.getNome() + " -->> Problema");


                if (v1 > 1) {
                    errar("Conflito de ambiguididade local com a External " + mCorrente.getNome() + " de forma SIGMAZ !");
                }

                if (v2 > 0) {
                    errar("Conflito de ambiguididade local com a External " + mCorrente.getNome() + " de forma REFER !");
                }
            }
        }

    }

    public void processarPacote(SigmazRaiz mSigmazRaiz, SigmazPackage mSigmazPackage, ArrayList<SigmazPackage> mPacotes) {

        mensagem("-->> PACKAGE : " + mSigmazPackage.getNome());

        for (SigmazCast mCorrente : mSigmazPackage.getCasts()) {

            int v1 = getSigmaz(mSigmazRaiz, mCorrente.getNome());
            int v2 = getLocal(mSigmazPackage, mCorrente.getNome());
            int v3 = getRefers(mSigmazPackage.getRefers(), mPacotes, mCorrente.getNome());

            int v = v1 + v2 + v3;

            if (v == 1) {
                mensagem("\tCast : " + mCorrente.getNome() + " -->> OK");
            } else {
                mensagem("\tCast : " + mCorrente.getNome() + " -->> Problema");

                if (v1 > 0) {
                    errar("Conflito de ambiguididade no pacote " + mSigmazPackage.getNome() + " com a Cast " + mCorrente.getNome() + " de forma SIGMAZ !");
                }

                if (v2 > 1) {
                    errar("Conflito de ambiguididade no pacote " + mSigmazPackage.getNome() + " com a Cast " + mCorrente.getNome() + " de forma LOCAL !");
                }

                if (v3 > 0) {
                    errar("Conflito de ambiguididade no pacote " + mSigmazPackage.getNome() + " com a Cast " + mCorrente.getNome() + " de forma REFER !");
                }

            }


        }

        for (SigmazStruct mCorrente : mSigmazPackage.getStructs()) {

            int v1 = getSigmaz(mSigmazRaiz, mCorrente.getNome());
            int v2 = getLocal(mSigmazPackage, mCorrente.getNome());
            int v3 = getRefers(mSigmazPackage.getRefers(), mPacotes, mCorrente.getNome());

            int v = v1 + v2 + v3;


            if (v == 1) {
                mensagem("\tStruct : " + mCorrente.getNome() + " -->> OK");
            } else {
                mensagem("\tStruct : " + mCorrente.getNome() + " -->> Problema");
                if (v1 > 0) {
                    errar("Conflito de ambiguididade no pacote " + mSigmazPackage.getNome() + " com a Struct " + mCorrente.getNome() + " de forma SIGMAZ !");
                }

                if (v2 > 1) {
                    errar("Conflito de ambiguididade no pacote " + mSigmazPackage.getNome() + " com a Struct " + mCorrente.getNome() + " de forma LOCAL !");
                }

                if (v3 > 0) {
                    errar("Conflito de ambiguididade no pacote " + mSigmazPackage.getNome() + " com a Struct " + mCorrente.getNome() + " de forma REFER !");
                }
            }
        }


        for (SigmazType mCorrente : mSigmazPackage.getTypes()) {

            int v1 = getSigmaz(mSigmazRaiz, mCorrente.getNome());
            int v2 = getLocal(mSigmazPackage, mCorrente.getNome());
            int v3 = getRefers(mSigmazPackage.getRefers(), mPacotes, mCorrente.getNome());

            int v = v1 + v2 + v3;
            if (v == 1) {
                mensagem("\tType : " + mCorrente.getNome() + " -->> OK");
            } else {
                mensagem("\tType : " + mCorrente.getNome() + " -->> Problema");

                if (v1 > 0) {
                    errar("Conflito de ambiguididade no pacote " + mSigmazPackage.getNome() + " com a Type " + mCorrente.getNome() + " de forma SIGMAZ !");
                }

                if (v2 > 1) {
                    errar("Conflito de ambiguididade no pacote " + mSigmazPackage.getNome() + " com a Type " + mCorrente.getNome() + " de forma LOCAL !");
                }

                if (v3 > 0) {
                    errar("Conflito de ambiguididade no pacote " + mSigmazPackage.getNome() + " com a Type " + mCorrente.getNome() + " de forma REFER !");
                }


            }
        }

        for (SigmazModel mCorrente : mSigmazRaiz.getModelos()) {

            int v1 = getSigmaz(mSigmazRaiz, mCorrente.getNome());
            int v2 = getLocal(mSigmazPackage, mCorrente.getNome());
            int v3 = getRefers(mSigmazPackage.getRefers(), mPacotes, mCorrente.getNome());

            int v = v1 + v2 + v3;

            if (v == 1) {
                mensagem("\tModelo : " + mCorrente.getNome() + " -->> OK");
            } else {
                mensagem("\tModelo : " + mCorrente.getNome() + " -->> Problema");

                if (v1 > 0) {
                    errar("Conflito de ambiguididade no pacote " + mSigmazPackage.getNome() + " com o Modelo " + mCorrente.getNome() + " de forma SIGMAZ !");
                }

                if (v2 > 1) {
                    errar("Conflito de ambiguididade no pacote " + mSigmazPackage.getNome() + " com o Modelo " + mCorrente.getNome() + " de forma LOCAL !");
                }

                if (v3 > 0) {
                    errar("Conflito de ambiguididade no pacote " + mSigmazPackage.getNome() + " com o Modelo " + mCorrente.getNome() + " de forma REFER !");
                }

            }
        }

        for (SigmazExternal mCorrente : mSigmazPackage.getExternals()) {

            int v1 = getSigmaz(mSigmazRaiz, mCorrente.getNome());
            int v2 = getLocal(mSigmazPackage, mCorrente.getNome());
            int v3 = getRefers(mSigmazPackage.getRefers(), mPacotes, mCorrente.getNome());

            int v = v1 + v2 + v3;

            if (v == 1) {
                mensagem("\tExternal : " + mCorrente.getNome() + " -->> OK");
            } else {
                mensagem("\tExternal : " + mCorrente.getNome() + " -->> Problema");

                if (v1 > 0) {
                    errar("Conflito de ambiguididade no pacote " + mSigmazPackage.getNome() + " com a External " + mCorrente.getNome() + " de forma SIGMAZ !");
                }

                if (v2 > 1) {
                    errar("Conflito de ambiguididade no pacote " + mSigmazPackage.getNome() + " com a External " + mCorrente.getNome() + " de forma LOCAL !");
                }

                if (v3 > 0) {
                    errar("Conflito de ambiguididade no pacote " + mSigmazPackage.getNome() + " com a External " + mCorrente.getNome() + " de forma REFER !");
                }

            }
        }


    }

    public int getSigmaz(SigmazRaiz mSigmazRaiz, String eNome) {
        int v = 0;

        for (SigmazCast mCast : mSigmazRaiz.getCasts()) {
            if (mCast.mesmoNome(eNome)) {
                v += 1;
            }
        }

        for (SigmazStruct mStruct : mSigmazRaiz.getStructs()) {
            if (mStruct.mesmoNome(eNome)) {
                v += 1;
            }
        }

        for (SigmazType mType : mSigmazRaiz.getTypes()) {
            if (mType.mesmoNome(eNome)) {
                v += 1;
            }
        }

        for (SigmazModel mModelo : mSigmazRaiz.getModelos()) {
            if (mModelo.mesmoNome(eNome)) {
                v += 1;
            }
        }

        for (SigmazExternal mExternal : mSigmazRaiz.getExternals()) {
            if (mExternal.mesmoNome(eNome)) {
                v += 1;
            }
        }

        return v;
    }

    public int getLocal(SigmazPackage mSigmazPackage, String eNome) {
        int v = 0;

        for (SigmazCast mCast : mSigmazPackage.getCasts()) {
            if (mCast.mesmoNome(eNome)) {
                v += 1;
            }
        }

        for (SigmazStruct mStruct : mSigmazPackage.getStructs()) {
            if (mStruct.mesmoNome(eNome)) {
                v += 1;
            }
        }

        for (SigmazType mType : mSigmazPackage.getTypes()) {
            if (mType.mesmoNome(eNome)) {
                v += 1;
            }
        }

        for (SigmazModel mModelo : mSigmazPackage.getModelos()) {
            if (mModelo.mesmoNome(eNome)) {
                v += 1;
            }
        }

        for (SigmazExternal mExternal : mSigmazPackage.getExternals()) {
            if (mExternal.mesmoNome(eNome)) {
                v += 1;
            }
        }

        return v;
    }

    public int getRefers(ArrayList<String> mRefers, ArrayList<SigmazPackage> mPacotes, String eNome) {

        int v = 0;


        for (String eRefer : mRefers) {

            boolean enc = false;

            for (SigmazPackage mPacote : mPacotes) {
                if (mPacote.mesmoNome(eRefer)) {


                    v += getLocal(mPacote, eNome);

                    enc = true;
                    break;
                }
            }

            if (!enc) {
                for (AST mRequisicao : mAnalisador.getRequisicoes()) {

                    for (AST mPackage : mRequisicao.getASTS()) {

                        SigmazPackage ePacote = new SigmazPackage(mPackage);

                        mensagem("passando por " + ePacote.getNome() + " atras de " + eNome);

                        if (ePacote.getNome().contentEquals(eNome)) {
                            v += getLocal(ePacote, eNome);
                            enc = true;
                            break;
                        }

                    }




                }

                if (!enc) {
                  //  errar("Pacote nao encontrado : " + eRefer);
                }
            }
        }


        return v;
    }

}
