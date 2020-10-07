package Make.Compiler;

import Sigmaz.S03_Parser.Token;
import Sigmaz.S03_Parser.TokenTipo;
import Sigmaz.S00_Utilitarios.AST;

public class AST_AST {

    private Compiler mCompiler;

    public AST_AST(Compiler eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {



        AST AST_Corrente = new AST("AST");
        ASTPai.getASTS().add(AST_Corrente);



            Token TokenSeta = mCompiler.getTokenAvanteStatus(TokenTipo.SETA, "Era esperado uma SETA !");
        Token TokenArquivo = mCompiler.getTokenAvanteStatus(TokenTipo.TEXTO, "Era esperado o local do arquivo !");


        AST_Corrente.setValor(TokenArquivo.getConteudo());



        Token TokenVirgula = mCompiler.getTokenAvanteStatus(TokenTipo.PONTOVIRGULA, "Era esperado um ponto e virgula !");




    }




}