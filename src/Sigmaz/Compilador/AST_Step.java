package Sigmaz.Compilador;

import Sigmaz.Lexer.Token;
import Sigmaz.Lexer.TokenTipo;
import Sigmaz.Utils.AST;

public class AST_Step {

    private CompilerUnit mCompiler;

    public AST_Step(CompilerUnit eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {

        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.ID) {

            if (TokenC.mesmoConteudo("def")) {

                complexo(ASTPai);

            } else {
                simples(ASTPai);
            }


        } else {
            mCompiler.errarCompilacao("Era esperado o nome da VARIAVEL !", TokenC);
        }


    }

    public void complexo(AST ASTPai){

        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.ID) {


            AST AST_Corrente = new AST("STEPDEF");
            AST_Corrente.setNome(TokenC.getConteudo());
            ASTPai.getASTS().add(AST_Corrente);

            AST AST_BODY = AST_Corrente.criarBranch("BODY");

            Token TokenC2 = mCompiler.getTokenAvanteStatus(TokenTipo.DOISPONTOS, "Era esperado Dois Pontos");
            Token TokenC3 = mCompiler.getTokenAvanteStatus(TokenTipo.ID, "Era esperado uma Tipagem");

            AST_Corrente.setValor(TokenC3.getConteudo());


            Token TokenC4 = mCompiler.getTokenAvanteStatus(TokenTipo.QUAD, "Era esperado :: ");
            Token TokenC5 = mCompiler.getTokenAvanteStatus(TokenTipo.PARENTESES_ABRE, "Era esperado ( ");

          //  AST_Value mAST = new AST_Value(mCompiler);
          //  mAST.ReceberArgumentos(AST_Corrente);

            AST_ValueTypes mAVA = new AST_ValueTypes(mCompiler);
            mAVA.ReceberArgumentos(AST_Corrente,false,null);


            Token TokenC6 = mCompiler.getTokenAvanteStatus(TokenTipo.SETA, "Era esperado -> ");

            AST_Corpo mCorpo = new AST_Corpo(mCompiler);
            mCorpo.init(AST_BODY);



        }else{

            mCompiler.errarCompilacao("Era esperado o nome da VARIAVEL !", TokenC);

        }
    }

    public void simples(AST ASTPai) {

        Token TokenC = mCompiler.getTokenCorrente();

        AST AST_Corrente = new AST("STEP");
        AST_Corrente.setNome(TokenC.getConteudo());
        ASTPai.getASTS().add(AST_Corrente);

        AST AST_BODY = AST_Corrente.criarBranch("BODY");

        Token TokenC2 = mCompiler.getTokenAvanteStatus(TokenTipo.QUAD, "Era esperado :: ");
        Token TokenC3 = mCompiler.getTokenAvanteStatus(TokenTipo.PARENTESES_ABRE, "Era esperado ( ");

      //  AST_Value mAST = new AST_Value(mCompiler);
       // mAST.ReceberArgumentos(AST_Corrente);

        AST_ValueTypes mAVA = new AST_ValueTypes(mCompiler);
        mAVA.ReceberArgumentos(AST_Corrente,false,null);


        Token TokenC4 = mCompiler.getTokenAvanteStatus(TokenTipo.SETA, "Era esperado -> ");

        AST_Corpo mCorpo = new AST_Corpo(mCompiler);
        mCorpo.init(AST_BODY);

    }


}