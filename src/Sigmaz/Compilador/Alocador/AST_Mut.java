package Sigmaz.Compilador.Alocador;

import Sigmaz.Compilador.Fluxo.AST_Value;
import Sigmaz.Compilador.CompilerUnit;
import Sigmaz.Lexer.Token;
import Sigmaz.Lexer.TokenTipo;
import Sigmaz.Utils.AST;

public class AST_Mut {

    private CompilerUnit mCompiler;

    public AST_Mut(CompilerUnit eCompiler) {
        mCompiler = eCompiler;
    }


    public void init_mut(AST ASTPai) {


        String erro = "Mutavel";


        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.ID) {


        } else {
            mCompiler.errarCompilacao("Era esperado o nome da " + erro + " !", TokenC);
            return;
        }


        AST AST_Corrente = new AST("MUT");
        AST_Corrente.setNome(TokenC.getConteudo());
        ASTPai.getASTS().add(AST_Corrente);


        //  System.out.println("To : " + mCompiler.getTokenCorrente().getConteudo());
        AST AST_Valor = AST_Corrente.criarBranch("VALUE");


        Token TokenP3 = mCompiler.getTokenAvante();

        if (TokenP3.getTipo() == TokenTipo.PONTOVIRGULA) {

            mCompiler.errarCompilacao("A definicao de uma " + erro + " com inferencia de tipo dinamico precisa receber um valor de atribuicao !", TokenC);

            return;
        } else if (TokenP3.getTipo() == TokenTipo.IGUAL) {

            AST_Value mAST = new AST_Value(mCompiler);

            mAST.init(AST_Valor);

            if (AST_Valor.mesmoNome("null") && AST_Valor.mesmoValor("ID")) {

                mCompiler.errarCompilacao("A definicao de uma " + erro + " com inferencia de tipo de tipo dinamico nao pode ter o valor de atribuicao NULO !", TokenC);

            }

        } else {

            mCompiler.errarCompilacao("Era esperado o valor da " + erro + " !", TokenC);

        }


    }


}
