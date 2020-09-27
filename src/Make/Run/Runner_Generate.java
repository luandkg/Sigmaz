package Make.Run;

import Sigmaz.Ferramentas.Dependenciador;
import Sigmaz.Intellisenses.Intellisense;
import Sigmaz.Intellisenses.IntellisenseTheme;
import Sigmaz.Sigmaz;
import Sigmaz.Utils.AST;
import Sigmaz.Utils.Erro;
import Sigmaz.Utils.GrupoDeErro;
import Sigmaz.Sigmaz_SemObjeto;
import Sigmaz.Utils.Syntax_HighLight;

import java.util.ArrayList;

public class Runner_Generate {

    private RunMake mRunMake;

    public Runner_Generate(RunMake eRunMake) {
        mRunMake = eRunMake;
    }

    public void init(AST ASTCGlobal) {


        if (ASTCGlobal.mesmoNome("INTELLISENSE")) {


            intellisense(ASTCGlobal);

        } else if (ASTCGlobal.mesmoNome("DEPENDENCY")) {

            dep(ASTCGlobal);

        } else if (ASTCGlobal.mesmoNome("HIGH_LIGHT")) {

            highlight(ASTCGlobal);


        } else {
            mRunMake.errar(mRunMake.getLocal(), "Comando Generate Desconhecido : " + ASTCGlobal.getNome());
        }

    }



    private void intellisense(AST ASTCGlobal) {

        Sigmaz_SemObjeto SigmazC = new Sigmaz_SemObjeto();

      //  SigmazC.setObject(mRunMake.getObject());
      //  SigmazC.setPosProcess(mRunMake.getPosProcess());
      //  SigmazC.setStackProcess(mRunMake.getStackProcess());


        ArrayList<String> mCaminhos = mRunMake.getCaminhos(ASTCGlobal);

        if (mCaminhos.size() > 0) {

            String TempIntellisenses = mRunMake.getIntellisenses() + ASTCGlobal.getValor();

            for (String TempSource_Item : mCaminhos) {


              //  System.out.println("\t - INTELLISENSE -->> " + TempSource_Item);

             //   SigmazC.intellisense(TempSource_Item, TempIntellisenses, false, mRunMake.getIntellienseTheme(), TempIntellisenses);

                SigmazC.init(TempSource_Item,TempIntellisenses,false);

                if (!SigmazC.temErros())
                {

                    Intellisense IntellisenseC = new Intellisense();
                    IntellisenseC.run(SigmazC.getASTS(),  mRunMake.getIntellienseTheme(), TempIntellisenses);


                }


            }

        } else {

            mRunMake.errar(mRunMake.getLocal(), "Generate intellisense source vazio ! ");

        }

    }

    private void dep(AST ASTCGlobal) {

        Sigmaz SigmazC = new Sigmaz();

        SigmazC.setObject(mRunMake.getObject());
        SigmazC.setPosProcess(mRunMake.getPosProcess());
        SigmazC.setStackProcess(mRunMake.getStackProcess());


        ArrayList<String> mCaminhos = mRunMake.getCaminhos(ASTCGlobal);

        System.out.println("################ " + ASTCGlobal.getValor() + " ################");



        if (mCaminhos.size() > 0) {


            for (String TempSource_Item : mCaminhos) {


                Dependenciador mDependenciador = new Dependenciador();
                mDependenciador.init(TempSource_Item);


                if (!mDependenciador.getTemErros()){

                    System.out.println("\tDEPENDENCIAS -->> " + TempSource_Item);

                    for(String eDependencia : mDependenciador.getDependencias()){

                        System.out.println("\t\t - " + eDependencia);

                    }

                }else{

                    System.out.println("\tDEPENDENCIAS -->> " + TempSource_Item + " :: PROBLEMAS ");

                    if (mDependenciador.getErros_Lexer().size()>0){

                        System.out.println("\n\t ERROS DE LEXER : ");
                        for (GrupoDeErro eGE : mDependenciador.getErros_Lexer()) {
                            System.out.println("\t\t" + eGE.getArquivo());
                            for (Erro eErro : eGE.getErros()) {
                                System.out.println("\t\t    ->> " + eErro.getLinha() + ":" + eErro.getPosicao() + " -> " + eErro.getMensagem());
                            }
                        }

                    }

                    if (mDependenciador.getErros_Compiler().size()>0){

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

            mRunMake.errar(mRunMake.getLocal(), "Generate dependency source vazio ! ");

        }

    }

    private void highlight(AST ASTCGlobal) {



        ArrayList<String> mCaminhos = mRunMake.getCaminhos(ASTCGlobal);

        if (mCaminhos.size() > 0) {

            String eLocal_Hight = mRunMake.getHighLights() + ASTCGlobal.getValor();

            for (String TempSource_Item : mCaminhos) {


                Syntax_HighLight mSyntax_HighLight = new Syntax_HighLight();
                mSyntax_HighLight.setSyntaxTheme(mRunMake.getSyntaxTheme());
                mSyntax_HighLight.highlight_simples(TempSource_Item,eLocal_Hight);



            }

        } else {

            mRunMake.errar(mRunMake.getLocal(), "Generate hightlight source vazio ! ");

        }

    }

}
