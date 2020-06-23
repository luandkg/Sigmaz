package Sigmaz.Compilador;

import Sigmaz.Lexer.Token;
import Sigmaz.Lexer.TokenTipo;
import Sigmaz.Utils.AST;

public class AST_Mockiz {

    private Compiler mCompiler;

    public AST_Mockiz(Compiler eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai,String Visibilidade) {

        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.ID) {

            AST AST_Corrente = new AST("MOCKIZ");
            AST_Corrente.setNome(TokenC.getConteudo());
            ASTPai.getASTS().add(AST_Corrente);

            AST AST_Visibilidade = AST_Corrente.criarBranch("VISIBILITY");
            AST_Visibilidade.setNome(Visibilidade);

            AST AST_Valor = AST_Corrente.criarBranch("VALUE");

            Token TokenC2 = mCompiler.getTokenAvanteStatus(TokenTipo.DOISPONTOS, "Era esperado Dois Pontos");
            Token TokenC3 = mCompiler.getTokenAvanteStatus(TokenTipo.ID, "Era esperado uma Tipagem");

            AST_Corrente.setValor(TokenC3.getConteudo());


            Token TokenP3 = mCompiler.getTokenAvante();

            if (TokenP3.getTipo() == TokenTipo.PONTOVIRGULA) {
                AST_Valor.setValor("NULL");
                return;
            } else  if (TokenP3.getTipo() == TokenTipo.IGUAL) {

                AST_Value mAST = new AST_Value(mCompiler);

                mAST.init(AST_Valor);
            }else{

                mCompiler.errarCompilacao("Era esperado o valor da CONSTANTE !", TokenC.getInicio());

            }


        } else {
            mCompiler.errarCompilacao("Era esperado o nome da CONSTANTE !", TokenC.getInicio());
        }


    }



}