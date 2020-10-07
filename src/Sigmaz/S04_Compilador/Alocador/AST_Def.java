package Sigmaz.S04_Compilador.Alocador;

import Sigmaz.S04_Compilador.AST_TYPE;
import Sigmaz.S04_Compilador.Fluxo.AST_Value;
import Sigmaz.S04_Compilador.CompilerUnit;
import Sigmaz.S03_Parser.Token;
import Sigmaz.S03_Parser.TokenTipo;
import Sigmaz.S00_Utilitarios.AST;

public class AST_Def {

    private CompilerUnit mCompiler;

    public AST_Def(CompilerUnit eCompiler) {
        mCompiler = eCompiler;
    }



    public void init_Def(  AST ASTPai) {


        String erro = "Variável";


        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.ID) {



        } else {
            mCompiler.errarCompilacao("Era esperado o nome da " + erro + " !", TokenC);
            return;
        }



        AST AST_Corrente = new AST("DEF");
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
            mAST.sePrecisarTipar(AST_Corrente.getBranch("TYPE"));
            mAST.init(AST_Valor);

        } else {

            mCompiler.errarCompilacao("Era esperado o valor da " + erro + " !", TokenC);

        }


    }

    public void init_Define(  AST ASTPai,String Visibilidade) {


        String erro = "Variável";


        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.ID) {



        } else {
            mCompiler.errarCompilacao("Era esperado o nome da " + erro + " !", TokenC);
            return;
        }



        AST AST_Corrente = new AST("DEFINE");
        AST_Corrente.setNome(TokenC.getConteudo());
        ASTPai.getASTS().add(AST_Corrente);

        AST AST_Visibilidade = AST_Corrente.criarBranch("VISIBILITY");
        AST_Visibilidade.setNome(Visibilidade);

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

            mCompiler.errarCompilacao("Era esperado o valor da " + erro + " !", TokenC);

        }


    }


}
