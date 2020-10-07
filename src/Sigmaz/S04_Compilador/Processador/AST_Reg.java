package Sigmaz.S04_Compilador.Processador;

import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S03_Parser.Token;
import Sigmaz.S03_Parser.TokenTipo;
import Sigmaz.S04_Compilador.CompilerUnit;
import Sigmaz.S04_Compilador.Fluxo.AST_Value;

public class AST_Reg {

    private CompilerUnit mCompiler;

    public AST_Reg(CompilerUnit eCompiler) {
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
