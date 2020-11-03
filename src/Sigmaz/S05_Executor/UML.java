package Sigmaz.S05_Executor;

import java.util.ArrayList;
import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S00_Utilitarios.Documento;
import Sigmaz.S00_Utilitarios.Texto;

public class UML {

    public void run(ArrayList<AST> mASTS, String eLocal) {



        Documento DocumentoC = new Documento();

        DocumentoC.adicionarLinha("@startuml");
        DocumentoC.adicionarLinha("skinparam class {");
        DocumentoC.adicionarLinha("BackgroundColor White");
        DocumentoC.adicionarLinha("BorderColor Black");
        DocumentoC.adicionarLinha("HeaderBackgroundColor White");
        DocumentoC.adicionarLinha(" }");
        DocumentoC.adicionarLinha("skinparam stereotypeCBackgroundColor White");
        DocumentoC.adicionarLinha("skinparam minClassWidth 200");

        for (AST ASTCGlobal : mASTS) {

            if (ASTCGlobal.mesmoTipo("SIGMAZ")) {


                colocarGlobal(DocumentoC, ASTCGlobal);

                for (AST ASTC : ASTCGlobal.getASTS()) {


                    if (ASTC.mesmoTipo("FUNCTION")) {

                    } else if (ASTC.mesmoTipo("ACTION")) {

                    } else if (ASTC.mesmoTipo("DIRECTOR")) {

                    } else if (ASTC.mesmoTipo("OPERATOR")) {

                    } else if (ASTC.mesmoTipo("CAST")) {

                    } else if (ASTC.mesmoTipo("TYPE")) {

                        colocarType(DocumentoC, "SIGMAZ", ASTC);

                    } else if (ASTC.mesmoTipo("STRUCT")) {

                        colocarStruct(DocumentoC, "SIGMAZ", ASTC);

                    } else if (ASTC.mesmoTipo("CALL")) {

                    } else if (ASTC.mesmoTipo("DEFINE")) {

                    } else if (ASTC.mesmoTipo("MOCKIZ")) {

                    } else if (ASTC.mesmoTipo("PACKAGE")) {

                        for (AST Sub : ASTC.getASTS()) {

                            if (Sub.mesmoTipo("STRUCT")) {

                                //     colocarStruct(DocumentoC, ASTC.getNome(), Sub);

                            }


                        }


                    }

                }


            }
        }


        DocumentoC.adicionarLinha("@enduml");


        Texto.Escrever(eLocal, DocumentoC.getConteudo());


    }


    public void colocarGlobal(Documento DocumentoC, AST Sub) {

        DocumentoC.adicionarLinha("class SIGMAZ.SIGMAZ <<(S,Red) >> {");

        Utils mUtils = new Utils();


        for (AST Sub2 : Sub.getASTS()) {

            if (Sub2.mesmoTipo("DEFINE")) {
                DocumentoC.adicionarLinha("<img:https://i.stack.imgur.com/p76Bx.gif>" + Sub2.getNome() + " : " + mUtils.getTipagem(Sub2.getBranch("TYPE")));
            }
            if (Sub2.mesmoTipo("MOCKIZ")) {
                DocumentoC.adicionarLinha("<img:https://i.stack.imgur.com/p76Bx.gif>" + Sub2.getNome() + " : " + mUtils.getTipagem(Sub2.getBranch("TYPE")));
            }

        }


        for (AST Sub2 : Sub.getASTS()) {

            if (Sub2.mesmoTipo("ACTION")) {
                DocumentoC.adicionarLinha("#" + Sub2.getNome() + " (" + mUtils.getParametragem(Sub2) + ")");
            }

        }
        for (AST Sub2 : Sub.getASTS()) {

            if (Sub2.mesmoTipo("FUNCTION")) {
                DocumentoC.adicionarLinha("+" + Sub2.getNome() + " (" + mUtils.getParametragem(Sub2) + ") : " + mUtils.getTipagem(Sub2.getBranch("TYPE")));
            }
        }

        for (AST Sub2 : Sub.getASTS()) {

            if (Sub2.mesmoTipo("OPERATOR")) {
                DocumentoC.adicionarLinha("~" + Sub2.getNome() + " (" + mUtils.getParametragem(Sub2) + ") : " + mUtils.getTipagem(Sub2.getBranch("TYPE")));
            }
        }

        DocumentoC.adicionarLinha("}");


    }



    public void colocarType(Documento DocumentoC, String ePacote, AST Sub) {
        DocumentoC.adicionarLinha("class " + ePacote + "." + Sub.getNome() + " <<(S,Blue) >> {");

        Utils mUtils = new Utils();


        for (AST Sub2 : Sub.getASTS()) {

            if (Sub2.mesmoTipo("DEFINE")) {
                DocumentoC.adicionarLinha("<img:https://i.stack.imgur.com/p76Bx.gif>" + Sub2.getNome() + " : " + mUtils.getTipagem(Sub2.getBranch("TYPE")));
            }
            if (Sub2.mesmoTipo("MOCKIZ")) {
                DocumentoC.adicionarLinha("<img:https://i.stack.imgur.com/p76Bx.gif>" + Sub2.getNome() + " : " + mUtils.getTipagem(Sub2.getBranch("TYPE")));
            }

        }
        DocumentoC.adicionarLinha("}");

    }


    public void colocarStruct(Documento DocumentoC, String ePacote, AST Sub) {

        Utils mUtils = new Utils();

        if (Sub.getBranch("EXTENDED").mesmoNome("STAGES")) {
            DocumentoC.adicionarLinha("class " + ePacote + "." + Sub.getNome() + " <<(S,Orange) >> {");

            for (AST Sub2 : Sub.getBranch("STAGES").getASTS()) {
                DocumentoC.adicionarLinha("<img:https://i.stack.imgur.com/p76Bx.gif>" + Sub2.getNome() + "");
            }

        } else if (Sub.getBranch("EXTENDED").mesmoNome("TYPE")) {
            DocumentoC.adicionarLinha("class " + ePacote + "." + Sub.getNome() + " <<(S,Blue) >> {");
        } else {
            DocumentoC.adicionarLinha("class " + ePacote + "." + Sub.getNome() + " <<(S,Green) >> {");
        }


        if (Sub.existeBranch("INITS")) {
            for (AST Sub2 : Sub.getBranch("INITS").getASTS()) {
                if (Sub2.mesmoNome(Sub.getNome())) {
                    DocumentoC.adicionarLinha("~" + Sub2.getNome() + " (" + mUtils.getParametragem(Sub2) + ")");
                }
            }
        }

        for (AST Sub2 : Sub.getBranch("BODY").getASTS()) {

            if (Sub2.mesmoTipo("DEFINE")) {


                String eModo = Sub2.getBranch("VISIBILITY").getNome();

                if (eModo.contentEquals("RESTRICT")) {
                    DocumentoC.adicionarLinha("<img:https://raw.githubusercontent.com/luandkg/Sigmaz/master/res/imagens/define_restrict.png>" + Sub2.getNome() + " : " + mUtils.getTipagem(Sub2.getBranch("TYPE")));
                } else if (eModo.contentEquals("EXTERN")) {
                    DocumentoC.adicionarLinha("<img:https://raw.githubusercontent.com/luandkg/Sigmaz/master/res/imagens/define_extern.png>" + Sub2.getNome() + " : " + mUtils.getTipagem(Sub2.getBranch("TYPE")));
                } else {
                    DocumentoC.adicionarLinha("<img:" +Imagens.Define_All + "=={scale=3}>" + Sub2.getNome() + " : " + mUtils.getTipagem(Sub2.getBranch("TYPE")));
                }

            }
            if (Sub2.mesmoTipo("MOCKIZ")) {
                DocumentoC.adicionarLinha("<img:https://i.stack.imgur.com/p76Bx.gif>" + Sub2.getNome() + " : " + mUtils.getTipagem(Sub2.getBranch("TYPE")));
            }

        }

        for (AST Sub2 : Sub.getBranch("BODY").getASTS()) {

            if (Sub2.mesmoTipo("ACTION")) {
                DocumentoC.adicionarLinha("#" + Sub2.getNome() + " (" + mUtils.getParametragem(Sub2) + ")");
            }

        }
        for (AST Sub2 : Sub.getBranch("BODY").getASTS()) {


            if (Sub2.mesmoTipo("FUNCTION")) {
                DocumentoC.adicionarLinha("+" + Sub2.getNome() + " (" + mUtils.getParametragem(Sub2) + ") : " + mUtils.getTipagem(Sub2.getBranch("TYPE")));
            }
        }

        for (AST Sub2 : Sub.getBranch("BODY").getASTS()) {

            if (Sub2.mesmoTipo("OPERATOR")) {
                DocumentoC.adicionarLinha("~" + Sub2.getNome() + " (" + mUtils.getParametragem(Sub2) + ") : " + mUtils.getTipagem(Sub2.getBranch("TYPE")));
            }
        }


        DocumentoC.adicionarLinha("}");

    }

}
