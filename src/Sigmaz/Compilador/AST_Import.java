package Sigmaz.Compilador;

import Sigmaz.Lexer.Token;
import Sigmaz.Lexer.TokenTipo;

import java.io.File;
import Sigmaz.Utils.AST;
import Sigmaz.Utils.Importacao;

public class AST_Import {

    private CompilerUnit mCompiler;

    public AST_Import(CompilerUnit eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {

        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.TEXTO) {

            Token TokenP3 = mCompiler.getTokenAvanteStatus(TokenTipo.PONTOVIRGULA, "Era esperado PONTO E VIRGULA !");

            String mLocalRequisicao = mCompiler.getLocal() + TokenC.getConteudo();

            mCompiler.enfileirar(new Importacao(mCompiler.getArquivo(),mLocalRequisicao,TokenC.getLinha(),TokenC.getPosicao()));

          //  importarAntigo(ASTPai,mLocalRequisicao);

        } else {
            mCompiler.errarCompilacao("Era esperado o caminho de uma Importacao !", TokenC);
        }

    }


    public void importarAntigo(AST ASTPai,String mLocalRequisicao){

        File arq = new File(mLocalRequisicao);
        if (arq.exists()) {

            //System.out.println("Importando :: " + mLocalRequisicao);

            if (!mCompiler.getRequisitados().contains(mLocalRequisicao)) {
              //  mCompiler.getRequisitados().add(mLocalRequisicao);


                CompilerUnit CompilerC = new CompilerUnit();
              //  CompilerC.init(mLocalRequisicao,ASTPai, mCompiler.getRequisitados());

                mCompiler.getErros_Lexer().addAll(CompilerC.getErros_Lexer());
                mCompiler.getErros_Compiler().addAll(CompilerC.getErros_Compiler());

                mCompiler.getComentarios().addAll(CompilerC.getComentarios());

                for (AST ASTCorrente : CompilerC.getAST("SIGMAZ").getASTS()) {

                    if (ASTCorrente.mesmoTipo("PACKAGE")){
                        AST ASTPacote = mCompiler.getPackage(ASTPai,ASTCorrente.getNome());

                        for (AST ASTP : ASTCorrente.getASTS()) {
                            ASTPacote.getASTS().add(ASTP);
                        }
                    }else{
                        //   System.out.println("Importando " + ASTCorrente.getTipo());
                        ASTPai.getASTS().add(ASTCorrente);
                    }


                }



            }


        } else {
            mCompiler.errarCompilacao("Importacao nao encontrada : " + mLocalRequisicao, new Token(TokenTipo.ID,"",0,0,0));
        }
    }

}
