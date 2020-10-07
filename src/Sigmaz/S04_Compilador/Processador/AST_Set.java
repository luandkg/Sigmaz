package Sigmaz.S04_Compilador.Processador;

import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S03_Parser.Token;
import Sigmaz.S03_Parser.TokenTipo;
import Sigmaz.S04_Compilador.CompilerUnit;

public class AST_Set {

    private CompilerUnit mCompiler;

    public AST_Set(CompilerUnit eCompiler) {
        mCompiler = eCompiler;
    }


    public void init(AST ASTPai) {

        Token TokenC = mCompiler.getTokenAvante();

        AST AST_Corrente = new AST("SET");
        ASTPai.getASTS().add(AST_Corrente);

        if (TokenC.getTipo() == TokenTipo.ID) {

            AST_Corrente.setValor("ID");
            AST_Corrente.setNome(TokenC.getConteudo().toUpperCase());

        } else {
            mCompiler.errarCompilacao("Era esperado o argumento de SET = { R1 a R19 } !", TokenC);
            return;
        }


        Token TokenD = mCompiler.getTokenAvanteStatus(TokenTipo.PONTOVIRGULA, "Era esperado um ponto e virgula !");



    }
}
