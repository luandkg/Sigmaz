package Sigmaz.Compilador;

import Sigmaz.Lexer.Token;
import Sigmaz.Lexer.TokenTipo;
import Sigmaz.Utils.AST;

public class AST_Function {

    private Compiler mCompiler;

    public AST_Function(Compiler eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai,String Visibilidade) {

        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.ID) {

            AST AST_Corrente = new AST("FUNCTION");
            AST_Corrente.setNome(TokenC.getConteudo());
            ASTPai.getASTS().add(AST_Corrente);

            AST AST_Visibilidade = AST_Corrente.criarBranch("VISIBILITY");
            AST_Visibilidade.setNome(Visibilidade);

            AST AST_Arguments = AST_Corrente.criarBranch("ARGUMENTS");

            AST AST_Generico = AST_Corrente.criarBranch("GENERIC");
            AST_Generico.setNome("FALSE");

            AST AST_BODY = AST_Corrente.criarBranch("BODY");

            AST_Argumentos mArgumentos = new AST_Argumentos(mCompiler);
            mArgumentos.init(AST_Arguments);

            Token TokenC2= mCompiler.getTokenAvanteStatus(TokenTipo.DOISPONTOS,"Era esperado Dois Pontos" );
            Token TokenC3= mCompiler.getTokenAvanteStatus(TokenTipo.ID,"Era esperado uma Tipagem" );

            AST_Corrente.setValor(TokenC3.getConteudo());


            Token TokenFuturo = mCompiler.getTokenFuturo();
            if (TokenFuturo.getTipo() == TokenTipo.ENVIAR) {

                AST_Generico.setNome("TRUE");

                AST_Generic mg = new AST_Generic(mCompiler);
                mg.init(AST_Generico);


            }


            AST_Corpo mCorpo = new AST_Corpo(mCompiler);
            mCorpo.init(AST_BODY);


        } else {
            mCompiler.errarCompilacao("Era esperado o nome para uma FUNCTION !", TokenC.getInicio());
        }


    }

    public void init_Definicao(AST ASTPai) {

        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.ID) {

            AST AST_Corrente = new AST("FUNCTION");
            AST_Corrente.setNome(TokenC.getConteudo());
            ASTPai.getASTS().add(AST_Corrente);

            AST AST_Arguments = AST_Corrente.criarBranch("ARGUMENTS");


            AST_Argumentos mArgumentos = new AST_Argumentos(mCompiler);
            mArgumentos.init_Tipagem(AST_Arguments);

            Token TokenC2= mCompiler.getTokenAvanteStatus(TokenTipo.DOISPONTOS,"Era esperado Dois Pontos" );
            Token TokenC3= mCompiler.getTokenAvanteStatus(TokenTipo.ID,"Era esperado uma Tipagem" );

            AST_Corrente.setValor(TokenC3.getConteudo());


            AST AST_Generico = AST_Corrente.criarBranch("GENERIC");
            AST_Generico.setNome("FALSE");

            Token TokenFuturo = mCompiler.getTokenFuturo();
            if (TokenFuturo.getTipo() == TokenTipo.ENVIAR) {

                AST_Generico.setNome("TRUE");

                AST_Generic mg = new AST_Generic(mCompiler);
                mg.init(AST_Generico);


            }

            Token TokenP2 = mCompiler.getTokenAvanteStatus(TokenTipo.PONTOVIRGULA,"Era esperado PONTO E VIRGULA !");



        } else {
            mCompiler.errarCompilacao("Era esperado o nome para uma FUNCTION !", TokenC.getInicio());
        }


    }




}