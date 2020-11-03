package Sigmaz.S01_Compilador.C03_Parser.Bloco;

import Sigmaz.S01_Compilador.C03_Parser.AST_TYPE;
import Sigmaz.S01_Compilador.C03_Parser.Organizador.AST_Argumentos;
import Sigmaz.S01_Compilador.C03_Parser.Fluxo.AST_Corpo;
import Sigmaz.S01_Compilador.C03_Parser.Parser;
import Sigmaz.S01_Compilador.C02_Lexer.Token;
import Sigmaz.S01_Compilador.C02_Lexer.TokenTipo;
import Sigmaz.S00_Utilitarios.AST;

public class AST_Local {

    private Parser mCompiler;

    public AST_Local(Parser eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai ) {

        Token TokenP3 = mCompiler.getTokenAvanteStatus(TokenTipo.SETA, "Era esperado uma SETA !");


        AST AST_Corrente = new AST("LOCAL");
        ASTPai.getASTS().add(AST_Corrente);


        AST AST_Arguments = AST_Corrente.criarBranch("ARGUMENTS");
        AST AST_BODY = AST_Corrente.criarBranch("BODY");

        AST_Argumentos mArgumentos = new AST_Argumentos(mCompiler);
        mArgumentos.init(AST_Arguments);


        if (mCompiler.getTokenFuturo().getTipo() == TokenTipo.DOISPONTOS){

            AST_Corrente.setValor("FUNCTION");


            AST_TYPE mType = new AST_TYPE(mCompiler);
            mType.init(AST_Corrente);


        }else{

            AST_Corrente.setValor("ACTION");

        }

        AST_Corpo mCorpo = new AST_Corpo(mCompiler);
        mCorpo.init(AST_BODY);


    }


}