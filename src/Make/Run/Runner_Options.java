package Make.Run;

import Sigmaz.S08_Utilitarios.AST;

import java.awt.*;

public class Runner_Options {

    private RunMake mRunMake;

    public Runner_Options(RunMake eRunMake) {
        mRunMake = eRunMake;
    }

    public void init(AST ASTCGlobal) {

        if (ASTCGlobal.mesmoNome("INTELLISENSE")) {

            if (ASTCGlobal.getBranch("MODE").mesmoValor("WITH")) {
                if (ASTCGlobal.getBranch("MODE").mesmoNome("default")) {

                    mRunMake.getIntellienseTheme().padrao();
                    // System.out.println("INTELLISENSE THEME -->> PADRAO");

                }
            } else {

                //   System.out.println("INTELLISENSE THEME -->> PERSONALIZADO ");

                AST eBloco = ASTCGlobal.getBranch("BLOCK");

                for (AST eItem : eBloco.getASTS()) {

                    if (eItem.mesmoNome("background")) {

                        mRunMake.getIntellienseTheme().setFundo(getCor(eItem.getBranch("VALUE")));
                    } else if (eItem.mesmoNome("struct")) {

                        mRunMake.getIntellienseTheme().setStruct(getCor(eItem.getBranch("VALUE")));
                    } else if (eItem.mesmoNome("model")) {

                        mRunMake.getIntellienseTheme().setModel(getCor(eItem.getBranch("VALUE")));
                    } else if (eItem.mesmoNome("cast")) {

                        mRunMake.getIntellienseTheme().setCast(getCor(eItem.getBranch("VALUE")));
                    } else if (eItem.mesmoNome("stage")) {

                        mRunMake.getIntellienseTheme().setStage(getCor(eItem.getBranch("VALUE")));
                    } else if (eItem.mesmoNome("type")) {

                        mRunMake.getIntellienseTheme().setType(getCor(eItem.getBranch("VALUE")));
                    } else if (eItem.mesmoNome("external")) {

                        mRunMake.getIntellienseTheme().setExternal(getCor(eItem.getBranch("VALUE")));
                    } else if (eItem.mesmoNome("text")) {

                        mRunMake.getIntellienseTheme().setTexto(getCor(eItem.getBranch("VALUE")));
                    } else if (eItem.mesmoNome("box")) {

                        mRunMake.getIntellienseTheme().setBox(getCor(eItem.getBranch("VALUE")));
                    } else if (eItem.mesmoNome("package")) {

                        mRunMake.getIntellienseTheme().setPackage(getCor(eItem.getBranch("VALUE")));
                    } else if (eItem.mesmoNome("sigmaz")) {

                        mRunMake.getIntellienseTheme().setSigmaz(getCor(eItem.getBranch("VALUE")));

                    } else {

                        //     System.out.println("Tema para " + eItem.getNome());
                    }


                }

            }

        } else if (ASTCGlobal.mesmoNome("BUILD")) {

            if (ASTCGlobal.getBranch("MODE").mesmoValor("WITH")) {
                if (ASTCGlobal.getBranch("MODE").mesmoNome("default")) {

                    mRunMake.getIntellienseTheme().padrao();
                    //  System.out.println("BUILD -->> PADRAO");

                }
            } else {

                //  System.out.println("BUILD  -->> PERSONALIZADO ");

                AST eBloco = ASTCGlobal.getBranch("BLOCK");

                for (AST eItem : eBloco.getASTS()) {
                    //  System.out.println("BUILD CONFIG :: " + eItem.getNome());


                    if (eItem.mesmoNome("object")) {

                        if (eItem.mesmoValor("true")) {
                            mRunMake.setObject(true);
                        } else {
                            mRunMake.setObject(false);
                        }

                    } else if (eItem.mesmoNome("pos_process")) {

                        if (eItem.mesmoValor("true")) {
                            mRunMake.setPosProcess(true);
                        } else {
                            mRunMake.setPosProcess(false);
                        }
                    } else if (eItem.mesmoNome("stack_process")) {

                        if (eItem.mesmoValor("true")) {
                            mRunMake.setStackProcess(true);
                        } else {
                            mRunMake.setStackProcess(false);
                        }
                    } else if (eItem.mesmoNome("analysis_process")) {

                        if (eItem.mesmoValor("true")) {
                            mRunMake.setAnalysisProcess(true);
                        } else {
                            mRunMake.setAnalysisProcess(false);
                        }
                    } else if (eItem.mesmoNome("ident_process")) {

                        if (eItem.mesmoValor("true")) {
                            mRunMake.setIdentProcess(true);
                        } else {
                            mRunMake.setIdentProcess(false);
                        }

                    }


                }

            }


        } else if (ASTCGlobal.mesmoNome("HIGH_LIGHT")) {


            if (ASTCGlobal.getBranch("MODE").mesmoValor("WITH")) {
                if (ASTCGlobal.getBranch("MODE").mesmoNome("default")) {

                    mRunMake.getSyntaxTheme().padrao();


                }
            } else {

                AST eBloco = ASTCGlobal.getBranch("BLOCK");

                for (AST eItem : eBloco.getASTS()) {

                    if (eItem.mesmoNome("theme")) {


                        if (eItem.getBranch("VALUE").getNome().contentEquals("dark")){
                            mRunMake.getSyntaxTheme().dark();
                        }else  if (eItem.getBranch("VALUE").getNome().contentEquals("light")){
                            mRunMake.getSyntaxTheme().light();
                        }

                    }

                }

            }

        }


    }

    public Color getCor(AST eItem) {

        //     System.out.println(eItem.ImprimirArvoreDeInstrucoes());

        if (eItem.getASTS().size() == 3) {

            try {

                int e1 = Integer.parseInt(eItem.getASTS().get(0).getValor());
                int e2 = Integer.parseInt(eItem.getASTS().get(1).getValor());
                int e3 = Integer.parseInt(eItem.getASTS().get(2).getValor());

                return new Color(e1, e2, e3);

            } catch (Exception e) {
                mRunMake.errar(mRunMake.getLocal(), "Cor com problema de formatacao !");
            }

        } else {
            mRunMake.errar(mRunMake.getLocal(), "Cor com problema de formatacao !");
        }

        return Color.BLACK;

    }
}
