package Sigmaz.S03_Parser.Fluxo;

import Sigmaz.S03_Parser.AST_Recebimentos;
import Sigmaz.S03_Parser.Parser;
import Sigmaz.S02_Lexer.Token;
import Sigmaz.S02_Lexer.TokenTipo;
import Sigmaz.S00_Utilitarios.AST;

public class AST_Invoke {

    private Parser mCompiler;

    public AST_Invoke(Parser eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {

        AST AST_Corrente = new AST("INVOKE");
        ASTPai.getASTS().add(AST_Corrente);


        AST AST_Saida= AST_Corrente.criarBranch("EXIT");
        AST AST_Argumentos = AST_Corrente.criarBranch("ARGUMENTS");


        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.ID) {

            AST_Corrente.setNome(TokenC.getConteudo());

        } else {
            mCompiler.errarCompilacao("Era esperado o nome de uma BIBLIOTECA de INVOCACAO !", TokenC);
           return;
        }



        Token TokenC2 = mCompiler.getTokenAvante();

        if (TokenC2.getTipo() == TokenTipo.SETA) {



        } else {
            mCompiler.errarCompilacao("Era esperado uma SETA ", TokenC2);
            return;
        }


        Token TokenC3 = mCompiler.getTokenAvante();

        if (TokenC3.getTipo() == TokenTipo.ID) {

            AST_Corrente.setValor(TokenC3.getConteudo());


        } else {
            mCompiler.errarCompilacao("Era esperado uma FUNCAO BIBLIOTECARIA ", TokenC);
            return;
        }


        Token TokenC4 = mCompiler.getTokenAvante();

        if (TokenC4.getTipo() == TokenTipo.PARENTESES_ABRE) {


            AST_Recebimentos mAST = new AST_Recebimentos(mCompiler);
            mAST.init(AST_Argumentos);


        } else {
            mCompiler.errarCompilacao("Era esperado abrir paresenteses ! ", TokenC4);
            return;
        }


        Token TokenC5= mCompiler.getTokenAvante();

        if (TokenC5.getTipo() == TokenTipo.QUAD) {


        } else {
            mCompiler.errarCompilacao("Era esperado :: ", TokenC5);
            return;
        }


        Token TokenC6 = mCompiler.getTokenAvante();

        if (TokenC6.getTipo() == TokenTipo.ID) {

            AST_Saida.setNome(TokenC6.getConteudo());

        } else {
            mCompiler.errarCompilacao("Era esperado o nome de uma SAIDA para a INVOCACAO !", TokenC6);
            return;
        }

        Token TokenP3 = mCompiler.getTokenAvanteStatus(TokenTipo.PONTOVIRGULA,"Era esperado PONTO E VIRGULA !");



    }



}