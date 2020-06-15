package Sigmaz.Compilador;

import Sigmaz.Lexer.Token;
import Sigmaz.Lexer.TokenTipo;
import Sigmaz.Utils.AST;

import java.io.File;

public class AST_Requisicao {

    private Compiler mCompiler;
    public AST_Requisicao(Compiler eCompiler){
        mCompiler=eCompiler;
    }

    public void init(AST ASTPai) {

        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.TEXTO) {



            Token TokenP3 = mCompiler.getTokenAvanteStatus(TokenTipo.PONTOVIRGULA,"Era esperado PONTO E VIRGULA !");


            String mLocalRequisicao = mCompiler.getLocal() + TokenC.getConteudo();

            File arq = new File(mLocalRequisicao);
            if (arq.exists()) {

                if (!mCompiler.getRequisitados().contains(mLocalRequisicao)) {
                    mCompiler.getRequisitados().add(mLocalRequisicao);


                    Compiler CompilerC = new Compiler();
                    CompilerC.requisitando(mLocalRequisicao, mCompiler.getRequisitados());

                    mCompiler.getErros_Lexer().addAll(CompilerC.getErros_Lexer());
                    mCompiler.getErros_Compiler().addAll(CompilerC.getErros_Compiler());

                    for(AST ASTCorrente : CompilerC.getAST("SIGMAZ").getASTS()){
                        ASTPai.getASTS().add(ASTCorrente);
                    }


                }


            } else {
                mCompiler.errarCompilacao("Requisicao nao encontrada : " + mLocalRequisicao, TokenC.getInicio());
            }


        } else {
            mCompiler. errarCompilacao("Era esperado o caminho de uma requisicao !", TokenC.getInicio());
        }

    }

}
