package Make.Compiler;

import Sigmaz.Lexer.Token;
import Sigmaz.Lexer.TokenTipo;
import Sigmaz.Utils.AST;

public class AST_Atribuicao {

    private Compiler mCompiler;

    public AST_Atribuicao(Compiler eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {


        AST AST_Corrente = ASTPai.criarBranch("VALUE");

        Token TokenC = mCompiler.getTokenAvante();
        if (TokenC.getTipo() == TokenTipo.COLCHETE_ABRE) {

            AST_Corrente.setValor("BLOCK");

            AST_Block eb = new AST_Block(mCompiler);
            eb.init(AST_Corrente);

        } else if (TokenC.getTipo() == TokenTipo.ID) {

            AST_Corrente.setValor("VALUE");
            AST_Corrente.setNome(TokenC.getConteudo());

        } else if (TokenC.getTipo() == TokenTipo.TEXTO) {

            AST_Corrente.setValor("VALUE");
            AST_Corrente.setNome(TokenC.getConteudo());

        } else {
            mCompiler.errarCompilacao("Atribuicao desconhecida : " + TokenC.getConteudo(), TokenC);
        }


        Token TokenIgual = mCompiler.getTokenAvanteStatus(TokenTipo.PONTOVIRGULA, "Era esperado o sinal de ponto e virgula !");


    }


}