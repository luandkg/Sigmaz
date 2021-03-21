package Sigmaz.S01_Compilador.C03_Parser.Bloco;

import Sigmaz.S01_Compilador.C03_Parser.Fluxo.AST_Corpo;
import Sigmaz.S01_Compilador.C03_Parser.Parser;
import Sigmaz.S01_Compilador.C02_Lexer.Token;
import Sigmaz.S01_Compilador.C02_Lexer.TokenTipo;
import Sigmaz.S08_Utilitarios.AST;

public class AST_Destruct {

    private Parser mCompiler;

    public AST_Destruct(Parser eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {

        Token TokenC = mCompiler.getTokenAvanteStatus(TokenTipo.SETA, "Era esperado uma seta !");

        AST_Corpo mCorpo = new AST_Corpo(mCompiler);
        mCorpo.init(ASTPai);


    }


}