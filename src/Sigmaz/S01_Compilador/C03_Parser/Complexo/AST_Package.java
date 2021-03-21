package Sigmaz.S01_Compilador.C03_Parser.Complexo;

import Sigmaz.S01_Compilador.C03_Parser.*;
import Sigmaz.S01_Compilador.C03_Parser.Organizador.AST_Refer;
import Sigmaz.S01_Compilador.C02_Lexer.Token;
import Sigmaz.S01_Compilador.C02_Lexer.TokenTipo;
import Sigmaz.S01_Compilador.Orquestrantes;
import Sigmaz.S08_Utilitarios.AST;

public class AST_Package {

    private Parser mCompiler;

    public AST_Package(Parser eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {

        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.ID) {


            AST AST_Corrente = null;
            boolean enc = false;

            for (AST eAST : ASTPai.getASTS()) {
                if (eAST.mesmoTipo(Orquestrantes.PACKAGE) && eAST.mesmoNome(TokenC.getConteudo())) {
                    AST_Corrente = eAST;
                    enc = true;
                    break;
                }
            }

            if (!enc) {
                AST_Corrente = new AST(Orquestrantes.PACKAGE);
                AST_Corrente.setNome(TokenC.getConteudo());
                ASTPai.getASTS().add(AST_Corrente);
            }


            corpo(AST_Corrente);


        } else {
            mCompiler.errarCompilacao("Era esperado o nome para um PACKAGE !", TokenC);
        }


    }



    public void corpo(AST AST_Raiz) {


        Token TokenD = mCompiler.getTokenAvanteStatus(TokenTipo.CHAVE_ABRE, "Era esperado abrir chaves");

        if (TokenD.getTipo() != TokenTipo.CHAVE_ABRE) {
            return;
        }

        boolean saiu = false;

        while (mCompiler.Continuar()) {
            Token TokenC = mCompiler.getTokenAvante();
            if (TokenC.getTipo() == TokenTipo.CHAVE_FECHA) {
                saiu = true;
                break;
            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("refer")) {

                AST_Refer mAST = new AST_Refer(mCompiler);
                mAST.init(AST_Raiz);

            //} else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("act")) {

             //   AST_Action mAST = new AST_Action(mCompiler);
             //   mAST.init(AST_Raiz, "ALL");

          //  } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("func")) {

           //     AST_Function mAST = new AST_Function(mCompiler);
           //     mAST.init(AST_Raiz, "ALL");
         //   } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("mockiz")) {

           //     AST_Alocador mAST = new AST_Alocador(mCompiler);
           //     mAST.init("MOCKIZ", AST_Raiz, "ALL");

           // } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("define")) {

           //     AST_Alocador mAST = new AST_Alocador(mCompiler);
           //     mAST.init("DEFINE", AST_Raiz, "ALL");

         //   } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("operator")) {

          //      AST_Operation mAST = new AST_Operation(mCompiler);
          //      mAST.init(AST_Raiz, "ALL");

         //   } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("director")) {

         //       AST_Director mAST = new AST_Director(mCompiler);
          //      mAST.init(AST_Raiz, "ALL");


            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("cast")) {

                AST_Cast mAST = new AST_Cast(mCompiler);
                mAST.init(AST_Raiz.getNome(),AST_Raiz);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("struct")) {

                AST_Struct mAST = new AST_Struct(mCompiler);
                mAST.init(AST_Raiz.getNome(),AST_Raiz);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("type")) {

                AST_TypeStruct mAST = new AST_TypeStruct(mCompiler);
                mAST.init(AST_Raiz.getNome(),AST_Raiz);


            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("external")) {

                AST_External mAST = new AST_External(mCompiler);
                mAST.init(AST_Raiz);


            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("stages")) {

                AST_Stages mAST = new AST_Stages(mCompiler);
                mAST.init(AST_Raiz.getNome(),AST_Raiz);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("model")) {

                AST_Model mAST = new AST_Model(mCompiler);
                mAST.init(AST_Raiz.getNome(),AST_Raiz);

            } else {


                mCompiler.errarCompilacao("Comando Deconhecido : " + TokenC.getConteudo(), TokenC);

            }
        }

        if (!saiu) {
            mCompiler.errarCompilacao("Era esperado fechar chaves" + mCompiler.getTokenAvante().getConteudo(), mCompiler.getTokenAvante());
        }
    }

}