package Sigmaz.S04_Compilador;

import Sigmaz.S04_Compilador.Fluxo.AST_Corpo;
import Sigmaz.S04_Compilador.Organizador.AST_Argumentos;
import Sigmaz.S03_Parser.Token;
import Sigmaz.S03_Parser.TokenTipo;
import Sigmaz.S00_Utilitarios.AST;

public class AST_Auto {

    private CompilerUnit mCompiler;

    public AST_Auto(CompilerUnit eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {

        ASTPai.setValor("AUTO");

        AST AST_Arguments = ASTPai.criarBranch("ARGUMENTS");
        AST AST_BODY = ASTPai.criarBranch("BODY");


        ASTPai.criarBranch("VISIBILITY").setNome("ALL");


        AST_Argumentos mArgumentos = new AST_Argumentos(mCompiler);
        mArgumentos.init(AST_Arguments);

        Token TokenFuturo = mCompiler.getTokenFuturo();
        if (TokenFuturo.getTipo() == TokenTipo.DOISPONTOS) {

            ASTPai.setValor("FUNCTOR");


            AST_TYPE mType = new AST_TYPE(mCompiler);
            mType.init(ASTPai);

        }


        AST_Corpo mCorpo = new AST_Corpo(mCompiler);
        mCorpo.init(AST_BODY);


    }

}

