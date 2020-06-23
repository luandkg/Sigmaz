package Sigmaz.Compilador;

import Sigmaz.Lexer.Token;
import Sigmaz.Lexer.TokenTipo;
import Sigmaz.Utils.AST;

public class AST_Operation {

    private Compiler mCompiler;

    public AST_Operation(Compiler eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai,String Visibilidade) {

        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.COMPARADOR_IGUALDADE) {

            AST AST_Corrente = new AST("OPERATION");
            AST_Corrente.setNome("MATCH");
            ASTPai.getASTS().add(AST_Corrente);

            AST AST_Visibilidade = AST_Corrente.criarBranch("VISIBILITY");
            AST_Visibilidade.setNome(Visibilidade);

            AST AST_Arguments = AST_Corrente.criarBranch("ARGUMENTS");


            AST AST_BODY = AST_Corrente.criarBranch("BODY");

            AST_Argumentos mArgumentos = new AST_Argumentos(mCompiler);
            mArgumentos.init(AST_Arguments);

            Token TokenC2 = mCompiler.getTokenAvanteStatus(TokenTipo.DOISPONTOS, "Era esperado Dois Pontos");
            Token TokenC3 = mCompiler.getTokenAvanteStatus(TokenTipo.ID, "Era esperado uma Tipagem");

            AST_Corrente.setValor(TokenC3.getConteudo());


            AST_Corpo mCorpo = new AST_Corpo(mCompiler);
            mCorpo.init(AST_BODY);

        } else  if (TokenC.getTipo() == TokenTipo.COMPARADOR_DIFERENTE) {

            AST AST_Corrente = new AST("OPERATION");
            AST_Corrente.setNome("UNMATCH");
            ASTPai.getASTS().add(AST_Corrente);

            AST AST_Visibilidade = AST_Corrente.criarBranch("VISIBILITY");
            AST_Visibilidade.setNome(Visibilidade);

            AST AST_Arguments = AST_Corrente.criarBranch("ARGUMENTS");



            AST AST_BODY = AST_Corrente.criarBranch("BODY");

            AST_Argumentos mArgumentos = new AST_Argumentos(mCompiler);
            mArgumentos.init(AST_Arguments);

            Token TokenC2= mCompiler.getTokenAvanteStatus(TokenTipo.DOISPONTOS,"Era esperado Dois Pontos" );
            Token TokenC3= mCompiler.getTokenAvanteStatus(TokenTipo.ID,"Era esperado uma Tipagem" );

            AST_Corrente.setValor(TokenC3.getConteudo());


            AST_Corpo mCorpo = new AST_Corpo(mCompiler);
            mCorpo.init(AST_BODY);
        } else {
            mCompiler.errarCompilacao("Era esperado o operador da OPERATION !", TokenC.getInicio());
        }


    }




}