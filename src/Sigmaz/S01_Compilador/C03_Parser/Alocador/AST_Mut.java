package Sigmaz.S01_Compilador.C03_Parser.Alocador;

import Sigmaz.S01_Compilador.C03_Parser.Fluxo.AST_Value;
import Sigmaz.S01_Compilador.C03_Parser.Parser;
import Sigmaz.S01_Compilador.C02_Lexer.Token;
import Sigmaz.S01_Compilador.C02_Lexer.TokenTipo;
import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S01_Compilador.Orquestrantes;

public class AST_Mut {

    private Parser mCompiler;

    public AST_Mut(Parser eCompiler) {
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


        AST AST_Corrente = new AST(Orquestrantes.MUT);
        AST_Corrente.setNome(TokenC.getConteudo());
        ASTPai.getASTS().add(AST_Corrente);


        //  System.out.println("To : " + mCompiler.getTokenCorrente().getConteudo());
        AST AST_Valor = AST_Corrente.criarBranch(Orquestrantes.VALUE);


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
