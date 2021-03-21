package Sigmaz.S01_Compilador.C03_Parser.Fluxo;

import Sigmaz.S01_Compilador.C03_Parser.Parser;
import Sigmaz.S01_Compilador.C02_Lexer.Token;
import Sigmaz.S01_Compilador.C02_Lexer.TokenTipo;
import Sigmaz.S08_Utilitarios.AST;

public class AST_Using {

    private Parser mCompiler;

    public AST_Using(Parser eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {


      //  Token TokenC = mCompiler.getTokenAvante();
      //  if (TokenC.getTipo() == TokenTipo.ID) {
     //   } else {
    //        mCompiler.errarCompilacao("Era esperado uma variavel STRUCT para uma USING !", TokenC);
    //    }


        Token TokenC = mCompiler.getTokenAvanteStatus(TokenTipo.PARENTESES_ABRE, "Era esperado Abrir Parenteses");

        AST AST_Corrente = new AST("USING");


        AST AST_STRUCT = AST_Corrente.criarBranch("STRUCT");
        AST AST_STRUCTURED = AST_Corrente.criarBranch("STRUCTURED");


        AST_Value mASTType = new AST_Value(mCompiler);
        mASTType.setBloco();
        mASTType.init(AST_STRUCT);
        AST_STRUCT.setTipo("STRUCT");


        AST_Corrente.setNome(TokenC.getConteudo());
        ASTPai.getASTS().add(AST_Corrente);


        Token TokenC1 = mCompiler.getTokenAvanteStatus(TokenTipo.SETA, "Era esperado -> ");

        corpo(AST_STRUCTURED);


        if (AST_STRUCT.getValor().length()==0){
            mCompiler.errarCompilacao("O Using precisa ser inicializado !",TokenC);
        }

    }

    public void corpo(AST ASTPai) {


        Token TokenC = mCompiler.getTokenAvanteStatus(TokenTipo.CHAVE_ABRE, "Era esperado abrir chaves");

        if (TokenC.getTipo() != TokenTipo.CHAVE_ABRE) {
            return;
        }

        boolean saiu = false;

        while (mCompiler.Continuar()) {
            Token TokenD = mCompiler.getTokenAvante();
            if (TokenD.getTipo() == TokenTipo.CHAVE_FECHA) {
                saiu = true;
                break;

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("def")) {


                mCompiler.errarCompilacao("Comando Proibido dentro de Using Corpo : " + TokenD.getConteudo(), TokenD);

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("moc")) {

                mCompiler.errarCompilacao("Comando Proibido dentro de Using Corpo : " + TokenD.getConteudo(), TokenD);


            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("let")) {

                mCompiler.errarCompilacao("Comando Proibido dentro de Using Corpo : " + TokenD.getConteudo(), TokenD);

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("voz")) {

                mCompiler.errarCompilacao("Comando Proibido dentro de Using Corpo : " + TokenD.getConteudo(), TokenD);

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("mut")) {

                mCompiler.errarCompilacao("Comando Proibido dentro de Using Corpo : " + TokenD.getConteudo(), TokenD);


            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("return")) {

                mCompiler.errarCompilacao("Comando Proibido dentro de Using Corpo : " + TokenD.getConteudo(), TokenD);

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("invoke")) {

                mCompiler.errarCompilacao("Comando Proibido dentro de Using Corpo : " + TokenD.getConteudo(), TokenD);


            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("if")) {

                mCompiler.errarCompilacao("Comando Proibido dentro de Using Corpo : " + TokenD.getConteudo(), TokenD);


            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("while")) {

                mCompiler.errarCompilacao("Comando Proibido dentro de Using Corpo : " + TokenD.getConteudo(), TokenD);


            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("loop")) {

                mCompiler.errarCompilacao("Comando Proibido dentro de Using Corpo : " + TokenD.getConteudo(), TokenD);


            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("step")) {

                mCompiler.errarCompilacao("Comando Proibido dentro de Using Corpo : " + TokenD.getConteudo(), TokenD);

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("when")) {

                mCompiler.errarCompilacao("Comando Proibido dentro de Using Corpo : " + TokenD.getConteudo(), TokenD);

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("daz")) {

                mCompiler.errarCompilacao("Comando Proibido dentro de Using Corpo : " + TokenD.getConteudo(), TokenD);


            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("try")) {

                mCompiler.errarCompilacao("Comando Proibido dentro de Using Corpo : " + TokenD.getConteudo(), TokenD);


            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("each")) {

                mCompiler.errarCompilacao("Comando Proibido dentro de Using Corpo : " + TokenD.getConteudo(), TokenD);


            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("cancel")) {

                mCompiler.errarCompilacao("Comando Proibido dentro de Using Corpo : " + TokenD.getConteudo(), TokenD);

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("continue")) {

                mCompiler.errarCompilacao("Comando Proibido dentro de Using Corpo : " + TokenD.getConteudo(), TokenD);


            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("exception")) {

                mCompiler.errarCompilacao("Comando Proibido dentro de Using Corpo : " + TokenD.getConteudo(), TokenD);


            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("init")) {


                mCompiler.errarCompilacao("Comando Proibido dentro de Using Corpo : " + TokenD.getConteudo(), TokenD);


            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("start")) {


                mCompiler.errarCompilacao("Comando Proibido dentro de Using Corpo : " + TokenD.getConteudo(), TokenD);



            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("extern_refered")) {

                mCompiler.errarCompilacao("Comando Proibido dentro de Using Corpo : " + TokenD.getConteudo(), TokenD);


            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("local")) {

                mCompiler.errarCompilacao("Comando Proibido dentro de Using Corpo : " + TokenD.getConteudo(), TokenD);


            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("auto")) {

                mCompiler.errarCompilacao("Comando Proibido dentro de Using Corpo : " + TokenD.getConteudo(), TokenD);

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("functor")) {

                mCompiler.errarCompilacao("Comando Proibido dentro de Using Corpo : " + TokenD.getConteudo(), TokenD);


            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("change")) {

                AST_Change mAST = new AST_Change(mCompiler);
                mAST.init(ASTPai);

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("using")) {

                mCompiler.errarCompilacao("Comando Proibido dentro de Using Corpo : " + TokenD.getConteudo(), TokenD);


            } else if (TokenD.getTipo() == TokenTipo.ID) {


                AST_Comando mAST = new AST_Comando(mCompiler);
                mAST.init(ASTPai);

            } else if (TokenD.getTipo() == TokenTipo.ARROBA) {

                mCompiler.errarCompilacao("Comando Proibido dentro de Using Corpo : " + TokenD.getConteudo(), TokenD);


            } else {


                mCompiler.errarCompilacao("Comando Deconhecido : " + TokenD.getConteudo(), TokenD);


            }
        }

        if (!saiu) {
            mCompiler.errarCompilacao("Era esperado fechar chaves" + mCompiler.getTokenAvante().getConteudo(), mCompiler.getTokenAvante());
        }
    }

}
