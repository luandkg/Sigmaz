package Sigmaz.S01_Compilador.C03_Parser.Testes;

import Sigmaz.S01_Compilador.C03_Parser.Fluxo.AST_ValueTypes;
import Sigmaz.S01_Compilador.C03_Parser.Parser;
import Sigmaz.S01_Compilador.C02_Lexer.Token;
import Sigmaz.S01_Compilador.C02_Lexer.TokenTipo;
import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S01_Compilador.Orquestrantes;

public class AST_Assertive {

    private Parser mCompiler;

    public AST_Assertive(Parser eCompiler) {
        mCompiler = eCompiler;
    }


    public void init(AST ASTPai) {


        AST ASTAssertive = ASTPai.criarBranch(Orquestrantes.ASSERTIVE);

        if (mCompiler.getIsDebug()) {
            mCompiler.debug(ASTAssertive);
        }


        Token TokenAA = mCompiler.getTokenAvanteStatus(TokenTipo.ID, "Era esperado um tipo de assertiva !");

        ASTAssertive.setNome(TokenAA.getConteudo());


        Token TokenD = mCompiler.getTokenAvante();

        if (TokenD.getTipo() == TokenTipo.PARENTESES_ABRE) {

            AST_ValueTypes mAVA = new AST_ValueTypes(mCompiler);
            mAVA.ReceberArgumentos(ASTAssertive, false, null);

            Token P3 = mCompiler.getTokenAvanteStatus(TokenTipo.PONTOVIRGULA, "Era esperado um ponto e virgula ! ");

        } else {
            mCompiler.errarCompilacao("Era esperado abrir paresenteses !", TokenD);
        }


    }


}
