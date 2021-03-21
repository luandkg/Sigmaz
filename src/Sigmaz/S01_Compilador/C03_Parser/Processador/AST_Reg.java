package Sigmaz.S01_Compilador.C03_Parser.Processador;

import Sigmaz.S08_Utilitarios.AST;
import Sigmaz.S01_Compilador.C02_Lexer.Token;
import Sigmaz.S01_Compilador.C02_Lexer.TokenTipo;
import Sigmaz.S01_Compilador.C03_Parser.Parser;
import Sigmaz.S01_Compilador.C03_Parser.Fluxo.AST_Value;

public class AST_Reg {

    private Parser mCompiler;

    public AST_Reg(Parser eCompiler) {
        mCompiler = eCompiler;
    }


    public void init(AST ASTPai) {

        Token Tokena = mCompiler.getTokenAvanteStatus(TokenTipo.ARROBA, "Era esperado um @ REGISTRADOR");

        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.ID) {


        } else {
            mCompiler.errarCompilacao("Era esperado o nome do Registrado !", TokenC);
            return;
        }


        AST AST_Corrente = new AST("REG");
        AST_Corrente.setNome(TokenC.getConteudo());
        ASTPai.getASTS().add(AST_Corrente);

        Token TokenD = mCompiler.getTokenAvanteStatus(TokenTipo.SETA, "Era esperado uma SETA");

        AST AST_Valor = AST_Corrente.criarBranch("VALUE");

        AST_Value mAST = new AST_Value(mCompiler);
        mAST.init(AST_Valor);


    }
}
