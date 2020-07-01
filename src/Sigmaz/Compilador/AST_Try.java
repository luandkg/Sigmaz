package Sigmaz.Compilador;

import Sigmaz.Lexer.Token;
import Sigmaz.Lexer.TokenTipo;
import Sigmaz.Utils.AST;

public class AST_Try {

    private Compiler mCompiler;

    public AST_Try(Compiler eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {


        AST AST_Corrente = new AST("TRY");
        ASTPai.getASTS().add(AST_Corrente);

        Token TokenC2 = mCompiler.getTokenAvanteStatus(TokenTipo.ID, "Era esperado a variavel logica de tentativa");
        Token TokenC3 = mCompiler.getTokenAvanteIDStatus("with", "Era esperado with");
        Token TokenC4= mCompiler.getTokenAvanteStatus(TokenTipo.ID, "Era esperado a variavel de destino da mensagem de erro");
        Token TokenC5= mCompiler.getTokenAvanteStatus(TokenTipo.SETA, "Era esperado ->");

        AST_Corrente.criarBranch("LOGIC").setNome(TokenC2.getConteudo());
        AST_Corrente.criarBranch("MESSAGE").setNome(TokenC4.getConteudo());

        AST_Corpo cAST = new AST_Corpo(mCompiler);
        cAST.init(AST_Corrente.criarBranch("BODY"));


    }

}
