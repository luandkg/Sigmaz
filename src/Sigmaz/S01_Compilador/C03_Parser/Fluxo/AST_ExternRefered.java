package Sigmaz.S01_Compilador.C03_Parser.Fluxo;

import Sigmaz.S01_Compilador.C03_Parser.Parser;
import Sigmaz.S01_Compilador.C02_Lexer.Token;
import Sigmaz.S01_Compilador.C02_Lexer.TokenTipo;
import Sigmaz.S08_Utilitarios.AST;

public class AST_ExternRefered {

    private Parser mCompiler;

    public AST_ExternRefered(Parser eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {

        Token TokenC5 = mCompiler.getTokenAvanteStatus(TokenTipo.ID, "Era esperado o nome para o campo REFERED");
        Token TokenC6 = mCompiler.getTokenAvanteStatus(TokenTipo.QUAD, "Era esperado um QUAD");
        Token TokenC3 = mCompiler.getTokenAvanteStatus(TokenTipo.ID, "Era esperado o nome de uma STRUCT");

        Token TokenC2 = mCompiler.getTokenAvanteStatus(TokenTipo.SETA, "Era esperado uma SETA");
        Token TokenC7 = mCompiler.getTokenAvanteStatus(TokenTipo.ID, "Era esperado o nome de uma campo explicit da STRUCT " + TokenC3.getConteudo());
        Token TokenC8 = mCompiler.getTokenAvanteStatus(TokenTipo.PONTOVIRGULA, "Era esperado ponto e virgula");


        AST AST_Corrente = new AST("EXTERN_REFERED");
        AST_Corrente.setNome(TokenC5.getConteudo());

        AST_Corrente.criarBranch("STRUCT").setNome(TokenC3.getConteudo());
        AST_Corrente.criarBranch("REFERED").setNome(TokenC7.getConteudo());

        ASTPai.getASTS().add(AST_Corrente);


    }




}