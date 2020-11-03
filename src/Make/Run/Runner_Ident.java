package Make.Run;

import Sigmaz.S06_Ferramentas.Dependenciador;
import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S00_Utilitarios.Erro;
import Sigmaz.S00_Utilitarios.GrupoDeErro;
import Sigmaz.S06_Ferramentas.Identador;

import java.util.ArrayList;

public class Runner_Ident {

    private RunMake mRunMake;

    public Runner_Ident(RunMake eRunMake) {
        mRunMake = eRunMake;
    }

    public void init(AST ASTCGlobal) {


        ArrayList<String> mCaminhos = mRunMake.getCaminhos(ASTCGlobal);

        if (mRunMake.getIdentProcess()){
            System.out.println("################ IDENTADOR ################");
        }

        if (mCaminhos.size() > 0) {


            for (String TempSource_Item : mCaminhos) {


                Dependenciador mDependenciador = new Dependenciador();
                mDependenciador.init(TempSource_Item,mRunMake.getBuild());

                if (!mDependenciador.getTemErros()) {


                    Identador mIdentador = new Identador();


                    boolean conseguiu = mIdentador.initSimples(TempSource_Item, TempSource_Item);

                    if (mRunMake.getIdentProcess()) {
                        if (conseguiu) {
                            System.out.println("\t - IDENTAR -->> " + TempSource_Item + " :: SUCESSO ");
                        } else {
                            System.out.println("\t - IDENTAR -->> " + TempSource_Item + " :: FALHOU ");
                        }

                    }


                    for (String eDependencia : mDependenciador.getDependencias()) {
                        if (!eDependencia.contentEquals(TempSource_Item)) {


                            conseguiu = mIdentador.initSimples(eDependencia, eDependencia);

                            if (mRunMake.getIdentProcess()) {

                                if (conseguiu) {
                                    System.out.println("\t\t - SUB IDENTAR  -->> " + eDependencia + " :: SUCESSO ");
                                } else {
                                    System.out.println("\t\t - SUB IDENTAR  -->> " + eDependencia + " :: FALHOU ");
                                }
                            }



                        }

                    }


                } else {

                    System.out.println("\tIDENTADOR -->> " + TempSource_Item + " :: PROBLEMAS ");

                    if (mDependenciador.getErros_Lexer().size() > 0) {

                        System.out.println("\n\t ERROS DE LEXER : ");
                        for (GrupoDeErro eGE : mDependenciador.getErros_Lexer()) {
                            System.out.println("\t\t" + eGE.getArquivo());
                            for (Erro eErro : eGE.getErros()) {
                                System.out.println("\t\t    ->> " + eErro.getLinha() + ":" + eErro.getPosicao() + " -> " + eErro.getMensagem());
                            }
                        }

                    }

                    if (mDependenciador.getErros_Compiler().size() > 0) {

                        System.out.println("\n\t ERROS DE COMPILER : ");
                        for (GrupoDeErro eGE : mDependenciador.getErros_Compiler()) {
                            System.out.println("\t\t" + eGE.getArquivo());
                            for (Erro eErro : eGE.getErros()) {
                                System.out.println("\t\t    ->> " + eErro.getLinha() + ":" + eErro.getPosicao() + " -> " + eErro.getMensagem());
                            }
                        }

                    }

                }


            }

        } else {

            mRunMake.errar(mRunMake.getLocal(), "Identador source vazio ! ");

        }


    }
}
