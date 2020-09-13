package Sigmaz.Analisador.ASTS;

import Sigmaz.Analisador.Analisador;
import Sigmaz.Analisador.Complementar.Analisador_Bloco;
import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Analisar_Apply {

    private Analisador mAnalisador;
    private Analisador_Bloco mAnalisador_Bloco;

    public Analisar_Apply(Analisador eAnalisador,Analisador_Bloco eAnalisador_Bloco) {

        mAnalisador = eAnalisador;
        mAnalisador_Bloco=eAnalisador_Bloco;

    }

    public void analisar_Apply(AST ASTPai, ArrayList<String> mAlocadosAntes) {

        if (ASTPai.existeBranch("SETTABLE")) {

            AST mSETTABLE = ASTPai.getBranch("SETTABLE");


            if (mSETTABLE.mesmoValor("ID")) {


                if (!mAlocadosAntes.contains(mSETTABLE.getNome())) {

                    //  mAnalisador.getErros().add("Variavel nao existente : " + mSETTABLE.getNome());
                }


            }

        }

        analisar_valorizar(ASTPai, mAlocadosAntes);


    }

    public void analisar_valorizar(AST ASTPai, ArrayList<String> mAlocadosAntes) {

        if (ASTPai.existeBranch("VALUE")) {

            AST mValue = ASTPai.getBranch("VALUE");

            valore(mValue, mAlocadosAntes);

        }


    }

    public void valore(AST mValue, ArrayList<String> mAlocadosAntes) {

        if (mValue.mesmoValor("ID")) {

            if (mValue.mesmoNome("true") || mValue.mesmoNome("false") || mValue.mesmoNome("null")) {


            } else {

                if (!mAlocadosAntes.contains(mValue.getNome())) {
                    mAnalisador.getErros().add(mAlocadosAntes.toString());

                    mAnalisador.getErros().add("Variavel nao existente  : " + mValue.getNome());
                }

            }

        } else if (mValue.mesmoValor("FUNCT")) {


            if (!mAnalisador_Bloco.getAnalisar_Outros().getFunctions_ApenasNomes().contains(mValue.getNome())) {

                mAnalisador.getErros().add("Global Functions : " + mAnalisador_Bloco.getAnalisar_Outros().getFunctions_ApenasNomes().toString());

                mAnalisador.getErros().add("Funcao nao existente : " + mValue.getNome());

            }

            for (AST mAST : mValue.getASTS()) {
                if (mAST.mesmoTipo("ARGUMENT") && mAST.mesmoValor("FUNCT")) {

                    valore(mAST, mAlocadosAntes);


                }
            }

        } else if (mValue.mesmoValor("TERNAL")) {


            valore(mValue.getBranch("CONDITION"), mAlocadosAntes);
            if (mValue.existeBranch("TRUE")) {
                valore(mValue.getBranch("TRUE"), mAlocadosAntes);
            }
            if (mValue.existeBranch("FALSE")) {
                valore(mValue.getBranch("FALSE"), mAlocadosAntes);
            }
        } else if (mValue.mesmoValor("Num")) {

        } else if (mValue.mesmoValor("Text")) {

        } else if (mValue.mesmoValor("OPERATOR")) {

        } else if (mValue.mesmoValor("INIT")) {

        } else if (mValue.mesmoValor("STRUCT_EXTERN")) {

        } else {

            //mAnalisador.getErros().add("Verificando Apply : " +mValue.getNome() + " : " + mValue.getValor() );


        }

    }
}
