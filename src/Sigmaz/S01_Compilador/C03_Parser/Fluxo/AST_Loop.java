package Sigmaz.S01_Compilador.C03_Parser.Fluxo;

import Sigmaz.S01_Compilador.C03_Parser.Parser;
import Sigmaz.S01_Compilador.C02_Lexer.Token;
import Sigmaz.S01_Compilador.C02_Lexer.TokenTipo;
import Sigmaz.S08_Utilitarios.AST;
import Sigmaz.S01_Compilador.Orquestrantes;

public class AST_Loop {

    private Parser mCompiler;

    public AST_Loop(Parser eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {



        AST AST_Corrente = new AST(Orquestrantes.LOOP);
        ASTPai.getASTS().add(AST_Corrente);


        Token TokenSeta = mCompiler.getTokenAvanteStatus(TokenTipo.SETA, "Era esperado uma SETA");


        AST_Corpo cAST = new AST_Corpo(mCompiler);
        cAST.init(AST_Corrente.criarBranch(Orquestrantes.BODY));




    }


}