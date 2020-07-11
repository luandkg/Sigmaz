package Sigmaz.Analisador.ASTS;

import Sigmaz.Analisador.Analisador;
import Sigmaz.Analisador.Analisador_Bloco;
import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Analisar_Struct {

    private Analisador mAnalisador;
    private Analisar_Argumentos mAnalisar_Argumentos;
    private Analisador_Bloco mAnalisador_Bloco;

    public Analisar_Struct(Analisador eAnalisador,Analisador_Bloco eAnalisador_Bloco) {

        mAnalisador = eAnalisador;
        mAnalisar_Argumentos = new Analisar_Argumentos(mAnalisador,eAnalisador_Bloco);
        mAnalisador_Bloco=eAnalisador_Bloco;

    }

    public void init_Struct(AST ASTPai, ArrayList<String> mAlocadosAntes) {


        AST ASTInits = ASTPai.getBranch("INITS");
        ArrayList<String> mInitListagem = new ArrayList<String>();

        for (AST ASTInit : ASTInits.getASTS()) {

            String mParametragem = ASTPai.getNome() + " ( " + mAnalisar_Argumentos.analisarArguments(ASTInit.getBranch("ARGUMENTS")) + ") ";


            if (!mInitListagem.contains(mParametragem)) {
                mInitListagem.add(mParametragem);
            } else {
                mAnalisador.getErros().add("Init Duplicada : " + mParametragem);
            }

            if (!getModo(ASTInit).contentEquals("ALL")) {
                mAnalisador.getErros().add("Init Invalida : " + getModo(ASTInit) + " " + mParametragem);
            }
        }


        AST mCorpo = ASTPai.getBranch("BODY");

        ArrayList<String> mAct_Nomes = new ArrayList<String>();
        ArrayList<String> mFunc_Nomes = new ArrayList<String>();
        ArrayList<String> mDef_Nomes = new ArrayList<String>();
        ArrayList<String> mMoc_Nomes = new ArrayList<String>();


        for (AST mAST : mCorpo.getASTS()) {

            if (mAST.mesmoTipo("OPERATION")) {

                String mAssinatura = getModo(mAST) + " " + ASTPai.getNome() + " ( " + mAnalisar_Argumentos.analisarArguments(mAST.getBranch("ARGUMENTS")) + ") -> " + mAST.getValor();

            } else if (mAST.mesmoTipo("DEFINE")) {

                if (mAct_Nomes.contains(mAST.getNome())) {
                    mAnalisador.getErros().add("Define Invalida " + mAST.getNome() + " : Já existe uma ACTION com esse nome !");
                    return;
                } else if (mFunc_Nomes.contains(mAST.getNome())) {
                    mAnalisador.getErros().add("Define Invalida " + mAST.getNome() + " : Já existe uma FUNCTION com esse nome !");
                    return;
                } else if (mDef_Nomes.contains(mAST.getNome())) {
                    mAnalisador.getErros().add("Define Invalida " + mAST.getNome() + " : Já existe uma DEFINE com esse nome !");
                    return;
                } else if (mMoc_Nomes.contains(mAST.getNome())) {
                    mAnalisador.getErros().add("Define Invalida " + mAST.getNome() + " : Já existe uma MOCKIZ com esse nome !");
                    return;
                } else {
                    mDef_Nomes.add(mAST.getNome());
                }

            } else if (mAST.mesmoTipo("MOCKIZ")) {

                if (mAct_Nomes.contains(mAST.getNome())) {
                    mAnalisador.getErros().add("Mockiz Invalida " + mAST.getNome() + " : Já existe uma ACTION com esse nome !");
                    return;
                } else if (mFunc_Nomes.contains(mAST.getNome())) {
                    mAnalisador.getErros().add("Mockiz Invalida " + mAST.getNome() + " : Já existe uma FUNCTION com esse nome !");
                    return;
                } else if (mDef_Nomes.contains(mAST.getNome())) {
                    mAnalisador.getErros().add("Define Invalida " + mAST.getNome() + " : Já existe uma DEFINE com esse nome !");
                    return;
                } else if (mMoc_Nomes.contains(mAST.getNome())) {
                    mAnalisador.getErros().add("Define Invalida " + mAST.getNome() + " : Já existe uma MOCKIZ com esse nome !");
                    return;
                } else {
                    mMoc_Nomes.add(mAST.getNome());
                }


            } else if (mAST.mesmoTipo("ACTION")) {

                if (mDef_Nomes.contains(mAST.getNome())) {
                    mAnalisador.getErros().add("Action Invalida " + mAST.getNome() + " : Já existe uma DEFINE com esse nome !");
                    return;
                } else if (mMoc_Nomes.contains(mAST.getNome())) {
                    mAnalisador.getErros().add("Action Invalida " + mAST.getNome() + " : Já existe uma MOCKIZ com esse nome !");
                    return;
                }

                if (!mAct_Nomes.contains(mAST.getNome())) {
                    mAct_Nomes.add(mAST.getNome());
                }

            } else if (mAST.mesmoTipo("FUNCTION")) {

                if (mDef_Nomes.contains(mAST.getNome())) {
                    mAnalisador.getErros().add("Function Invalida " + mAST.getNome() + " : Já existe uma DEFINE com esse nome !");
                    return;
                } else if (mMoc_Nomes.contains(mAST.getNome())) {
                    mAnalisador.getErros().add("Function Invalida " + mAST.getNome() + " : Já existe uma MOCKIZ com esse nome !");
                    return;
                }

                if (!mFunc_Nomes.contains(mAST.getNome())) {
                    mFunc_Nomes.add(mAST.getNome());
                }

            }

        }

    }


    public String getModo(AST eAST) {
        return eAST.getBranch("VISIBILITY").getNome();
    }

}
