package Sigmaz.Compilador.Organizador;

import Sigmaz.Compilador.CompilerUnit;
import Sigmaz.Lexer.Token;
import Sigmaz.Lexer.TokenTipo;
import Sigmaz.Utils.AST;

public class AST_Refer {

    private CompilerUnit mCompiler;

    public AST_Refer(CompilerUnit eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {

        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.ID) {

            AST AST_Corrente = new AST("REFER");
            AST_Corrente.setNome(TokenC.getConteudo());
            ASTPai.getASTS().add(AST_Corrente);


            Token TokenP3 = mCompiler.getTokenAvanteStatus(TokenTipo.PONTOVIRGULA, "Era esperado PONTO E VIRGULA !");


        } else {
            mCompiler.errarCompilacao("Era esperado o nome para uma REFER !", TokenC);
        }


    }


}