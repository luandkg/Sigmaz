package Sigmaz.Compilador;

import Sigmaz.Lexer.Token;
import Sigmaz.Lexer.TokenTipo;
import Sigmaz.Utils.AST;

public class AST_Struct {

    private Compiler mCompiler;

    public AST_Struct(Compiler eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {

        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.ID) {

            AST AST_Corrente = new AST("STRUCT");
            AST_Corrente.setNome(TokenC.getConteudo());
            ASTPai.getASTS().add(AST_Corrente);

            AST AST_Generico = AST_Corrente.criarBranch("GENERIC");
            AST_Generico.setNome("FALSE");

           AST AST_With =  AST_Corrente.criarBranch("WITH");
            AST_With.setValor("FALSE");

            AST AST_Stages =  AST_Corrente.criarBranch("STAGES");
            AST_Stages.setValor("FALSE");

            AST mExtended = AST_Corrente.criarBranch("EXTENDED");
            mExtended.setValor("FALSE");


            AST AST_Inits =  AST_Corrente.criarBranch("INITS");


            Token TokenFuturo = mCompiler.getTokenFuturo();
            if (TokenFuturo.getTipo() == TokenTipo.ID && TokenFuturo.mesmoConteudo("in")) {

                mCompiler.Proximo();
                AST_Generico.setNome("TRUE");

                AST_Generic mg = new AST_Generic(mCompiler);
                mg.init_receber(AST_Generico);


            }


            Token Futuro = mCompiler.getTokenFuturo();
            if (Futuro.getTipo() == TokenTipo.ID && Futuro.mesmoConteudo("with")) {
                mCompiler.Proximo();

                //System.out.println("vamos comecar a heranca...");

                Token TokenP = mCompiler.getTokenAvante();

                if (TokenP.getTipo() == TokenTipo.ID ) {

                    AST_With.setNome(TokenP.getConteudo());
                    AST_With.setValor("TRUE");


                }


            }


            AST_StructCorpo mAST = new AST_StructCorpo(mCompiler);
            mAST.init(AST_Corrente,AST_Inits,TokenC.getConteudo());


        } else {
            mCompiler.errarCompilacao("Era esperado o nome para uma STRUCT !", TokenC.getInicio());
        }


    }


}