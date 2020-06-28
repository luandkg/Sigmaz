package Sigmaz.Compilador;

import Sigmaz.Lexer.Token;
import Sigmaz.Lexer.TokenTipo;
import Sigmaz.Utils.AST;

public class AST_Alocador {

    private Compiler mCompiler;

    public AST_Alocador(Compiler eCompiler) {
        mCompiler = eCompiler;
    }



    public void init(String eTipo,  AST ASTPai) {


        String erro = "";

        if (eTipo.contentEquals("DEF")) {

            erro = "Variável";

        } else if (eTipo.contentEquals("MOC")) {

            erro = "Constante";


        }


        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.ID) {



        } else {
            mCompiler.errarCompilacao("Era esperado o nome da " + erro + " !", TokenC.getInicio());
            return;
        }



        AST AST_Corrente = new AST(eTipo);
        AST_Corrente.setNome(TokenC.getConteudo());
        ASTPai.getASTS().add(AST_Corrente);



        AST_TYPE mType = new AST_TYPE(mCompiler);
        mType.init(AST_Corrente);

        //  System.out.println("To : " + mCompiler.getTokenCorrente().getConteudo());
        AST AST_Valor = AST_Corrente.criarBranch("VALUE");


        Token TokenP3 = mCompiler.getTokenAvante();

        if (TokenP3.getTipo() == TokenTipo.PONTOVIRGULA) {
            AST_Valor.setValor("NULL");
            return;
        } else if (TokenP3.getTipo() == TokenTipo.IGUAL) {

            AST_Value mAST = new AST_Value(mCompiler);

            mAST.init(AST_Valor);
        } else {

            mCompiler.errarCompilacao("Era esperado o valor da " + erro + " !", TokenC.getInicio());

        }


    }




    public void init(String eTipo,  AST ASTPai,String Visibilidade) {

        String erro = "";

        if (eTipo.contentEquals("DEFINE")) {

            erro = "DEFINE";

        } else if (eTipo.contentEquals("MOCKIZ")) {

            erro = "MOCKIZ";

        }

        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.ID) {



        } else {
            mCompiler.errarCompilacao("Era esperado o nome da " + erro + " !", TokenC.getInicio());
            return;
        }


        AST AST_Corrente = new AST(eTipo);
        AST_Corrente.setNome(TokenC.getConteudo());
        ASTPai.getASTS().add(AST_Corrente);

        AST AST_Visibilidade = AST_Corrente.criarBranch("VISIBILITY");
        AST_Visibilidade.setNome(Visibilidade);

        AST_TYPE mType = new AST_TYPE(mCompiler);
        mType.init(AST_Corrente);



        AST AST_Valor = AST_Corrente.criarBranch("VALUE");



        Token TokenP3 = mCompiler.getTokenAvante();

        if (TokenP3.getTipo() == TokenTipo.PONTOVIRGULA) {
            AST_Valor.setValor("NULL");
            return;
        } else if (TokenP3.getTipo() == TokenTipo.IGUAL) {

            AST_Value mAST = new AST_Value(mCompiler);
            mAST.init(AST_Valor);
        } else {

            mCompiler.errarCompilacao("Era esperado o valor da " + erro + " !", TokenC.getInicio());

        }


    }


    public void init_Definicao(String eTipo,  AST ASTPai) {

        String erro = "";

        if (eTipo.contentEquals("DEFINE")) {

            erro = "DEFINE";

        } else if (eTipo.contentEquals("MOCKIZ")) {

            erro = "MOCKIZ";

        }

        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.ID) {



        } else {
            mCompiler.errarCompilacao("Era esperado o nome da " + erro + " !", TokenC.getInicio());
            return;
        }


        AST AST_Corrente = new AST(eTipo);
        AST_Corrente.setNome(TokenC.getConteudo());
        ASTPai.getASTS().add(AST_Corrente);


        AST_TYPE mType = new AST_TYPE(mCompiler);
        mType.init(AST_Corrente);


        Token TokenP2 = mCompiler.getTokenAvanteStatus(TokenTipo.PONTOVIRGULA,"Era esperado PONTO E VIRGULA !");



    }


}