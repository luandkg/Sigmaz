package Sigmaz.S01_Compilador.C03_Parser.Bloco;

import Sigmaz.S01_Compilador.C03_Parser.Organizador.AST_Argumentos;
import Sigmaz.S01_Compilador.C03_Parser.Fluxo.AST_Corpo;
import Sigmaz.S01_Compilador.C03_Parser.Alocador.AST_TYPE;
import Sigmaz.S01_Compilador.C03_Parser.Parser;
import Sigmaz.S01_Compilador.C02_Lexer.Token;
import Sigmaz.S01_Compilador.C02_Lexer.TokenTipo;
import Sigmaz.S01_Compilador.Orquestrantes;
import Sigmaz.S08_Utilitarios.AST;

public class AST_Director {

    private Parser mCompiler;

    public AST_Director(Parser eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {

        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.NEGADOR) {

            definir(ASTPai, Orquestrantes.ALL, Orquestrantes.INVERSE);

        } else {
            mCompiler.errarCompilacao("Era esperado o operador da DIRECTOR !", TokenC);
        }


    }

    public void definir(AST ASTPai, String Visibilidade, String eOperator) {

        AST AST_Corrente = new AST(Orquestrantes.DIRECTOR);
        AST_Corrente.setNome(eOperator);
        ASTPai.getASTS().add(AST_Corrente);

        AST AST_Visibilidade = AST_Corrente.criarBranch(Orquestrantes.VISIBILITY);
        AST_Visibilidade.setNome(Visibilidade);

        AST AST_Arguments = AST_Corrente.criarBranch(Orquestrantes.ARGUMENTS);


        AST_Argumentos mArgumentos = new AST_Argumentos(mCompiler);
        mArgumentos.init(AST_Arguments);

        AST_TYPE mType = new AST_TYPE(mCompiler);
        mType.init(AST_Corrente);


        AST AST_BODY = AST_Corrente.criarBranch(Orquestrantes.BODY);
        AST_Corpo mCorpo = new AST_Corpo(mCompiler);
        mCorpo.init(AST_BODY);


    }


}