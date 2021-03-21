package Sigmaz.S01_Compilador.C03_Parser.Fluxo;

import Sigmaz.S08_Utilitarios.AST;
import Sigmaz.S01_Compilador.C02_Lexer.Token;
import Sigmaz.S01_Compilador.C02_Lexer.TokenTipo;
import Sigmaz.S01_Compilador.C03_Parser.Parser;

public class AST_ExecuteLocal {


    private Parser mCompiler;

    public AST_ExecuteLocal(Parser eCompiler) {
        mCompiler = eCompiler;
    }


    public void init(AST ASTPai) {


        AST ASTCorrente = new AST("EXECUTE_LOCAL");

        AST_ValueTypes mAVA = new AST_ValueTypes(mCompiler);
        mAVA.ReceberArgumentos(ASTCorrente, false, null);

        ASTPai.getASTS().add(ASTCorrente);

        Token TokenP3 = mCompiler.getTokenAvanteStatus(TokenTipo.PONTOVIRGULA, "Era esperado PONTO E VIRGULA !");

    }

}
