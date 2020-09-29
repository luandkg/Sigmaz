package Sigmaz.Compilador.Bloco;

import Sigmaz.Compilador.Organizador.AST_Argumentos;
import Sigmaz.Compilador.Fluxo.AST_Corpo;
import Sigmaz.Compilador.AST_TYPE;
import Sigmaz.Compilador.CompilerUnit;
import Sigmaz.Lexer.Token;
import Sigmaz.Lexer.TokenTipo;
import Sigmaz.Utils.AST;

public class AST_Director {

    private CompilerUnit mCompiler;

    public AST_Director(CompilerUnit eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai, String Visibilidade) {

        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.NEGADOR) {

            definir(ASTPai, Visibilidade, "INVERSE");

        } else {
            mCompiler.errarCompilacao("Era esperado o operador da UNARY !", TokenC);
        }


    }

    public void definir(AST ASTPai, String Visibilidade, String eOperator) {

        AST AST_Corrente = new AST("DIRECTOR");
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