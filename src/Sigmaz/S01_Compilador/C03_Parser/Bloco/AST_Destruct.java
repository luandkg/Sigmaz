package Sigmaz.S01_Compilador.C03_Parser.Bloco;

import Sigmaz.S01_Compilador.C03_Parser.Fluxo.AST_Corpo;
import Sigmaz.S01_Compilador.C03_Parser.Parser;
import Sigmaz.S01_Compilador.C02_Lexer.Token;
import Sigmaz.S01_Compilador.C02_Lexer.TokenTipo;
import Sigmaz.S00_Utilitarios.AST;

public class AST_Destruct {

    private Parser mCompiler;

    public AST_Destruct(Parser eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {

        Token TokenC = mCompiler.getTokenAvanteStatus(TokenTipo.SETA, "Era esperado uma seta !");


      //  AST AST_Corrente = new AST("DESTRUCT");
     //   AST_Corrente.setNome(TokenC.getConteudo());
    //    ASTPai.getASTS().add(AST_Corrente);

     //   AST AST_BODY = AST_Corrente.criarBranch("BODY");

        AST_Corpo mCorpo = new AST_Corpo(mCompiler);
        mCorpo.init(ASTPai);


    }


}