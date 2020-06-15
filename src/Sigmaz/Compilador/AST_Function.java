package Sigmaz.Compilador;

import Sigmaz.Lexer.Token;
import Sigmaz.Lexer.TokenTipo;
import Sigmaz.Utils.AST;

public class AST_Function {

    private Compiler mCompiler;

    public AST_Function(Compiler eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {

        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.ID) {

            AST AST_Corrente = new AST("FUNCTION");
            AST_Corrente.setNome(TokenC.getConteudo());
            ASTPai.getASTS().add(AST_Corrente);

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
            mCompiler.errarCompilacao("Era esperado o nome para uma FUNCTION !", TokenC.getInicio());
        }


    }




}