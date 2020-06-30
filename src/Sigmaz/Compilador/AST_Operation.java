package Sigmaz.Compilador;

import Sigmaz.Lexer.Token;
import Sigmaz.Lexer.TokenTipo;
import Sigmaz.Utils.AST;

public class AST_Operation {

    private Compiler mCompiler;

    public AST_Operation(Compiler eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai, String Visibilidade) {

        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.COMPARADOR_IGUALDADE) {

            definir(ASTPai, Visibilidade, "MATCH");

        } else if (TokenC.getTipo() == TokenTipo.COMPARADOR_DIFERENTE) {

            definir(ASTPai, Visibilidade, "UNMATCH");

        } else if (TokenC.getTipo() == TokenTipo.SOMADOR) {

            definir(ASTPai, Visibilidade, "SUM");
        } else if (TokenC.getTipo() == TokenTipo.DIMINUIDOR) {

            definir(ASTPai, Visibilidade, "SUB");
        } else if (TokenC.getTipo() == TokenTipo.MULTIPLICADOR) {

            definir(ASTPai, Visibilidade, "MUX");
        } else if (TokenC.getTipo() == TokenTipo.DIVISOR) {

            definir(ASTPai, Visibilidade, "DIV");

        } else {
            mCompiler.errarCompilacao("Era esperado o operador da OPERATION !", TokenC);
        }


    }

    public void definir(AST ASTPai, String Visibilidade, String eOperator) {

        AST AST_Corrente = new AST("OPERATOR");
        AST_Corrente.setNome(eOperator);
        ASTPai.getASTS().add(AST_Corrente);

        AST AST_Visibilidade = AST_Corrente.criarBranch("VISIBILITY");
        AST_Visibilidade.setNome(Visibilidade);

        AST AST_Arguments = AST_Corrente.criarBranch("ARGUMENTS");




        AST_Argumentos mArgumentos = new AST_Argumentos(mCompiler);
        mArgumentos.init(AST_Arguments);

        AST_TYPE mType = new AST_TYPE(mCompiler);
        mType.init(AST_Corrente);


        AST AST_BODY = AST_Corrente.criarBranch("BODY");
        AST_Corpo mCorpo = new AST_Corpo(mCompiler);
        mCorpo.init(AST_BODY);


    }


}