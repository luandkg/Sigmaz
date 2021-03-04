package Sigmaz.S01_Compilador.C03_Parser.Testes;

import Sigmaz.S01_Compilador.C03_Parser.Fluxo.AST_Corpo;
import Sigmaz.S01_Compilador.C02_Lexer.Token;
import Sigmaz.S01_Compilador.C02_Lexer.TokenTipo;
import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S01_Compilador.C03_Parser.Parser;
import Sigmaz.S01_Compilador.Orquestrantes;

public class AST_Test {

    private Parser mCompiler;

    public AST_Test(Parser eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {

        AST AST_Corrente = ASTPai.criarBranch(Orquestrantes.TEST);

        Token TokenGrupo = mCompiler.getTokenAvanteStatus(TokenTipo.ID, "Era esperado o nome do grupo do Teste !");

        Token TokenP2 = mCompiler.getTokenAvanteStatus(TokenTipo.SETA, "Era esperado uma SETA !");

        Token TokenTeste = mCompiler.getTokenAvanteStatus(TokenTipo.ID, "Era esperado o nome do Teste !");

        AST_Corrente.criarBranch(Orquestrantes.GROUP).setNome(TokenGrupo.getConteudo());
        AST_Corrente.criarBranch(Orquestrantes.TEST).setNome(TokenTeste.getConteudo());


        AST AST_BODY = AST_Corrente.criarBranch(Orquestrantes.BODY);
        AST_Corpo mCorpo = new AST_Corpo(mCompiler);
        mCorpo.init(AST_BODY);




    }
}
