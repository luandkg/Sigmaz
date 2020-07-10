package Sigmaz.Compilador;

import Sigmaz.Lexer.Token;
import Sigmaz.Lexer.TokenTipo;
import Sigmaz.Utils.AST;

import java.io.File;

public class AST_Import {

    private Compiler mCompiler;

    public AST_Import(Compiler eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {

        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.TEXTO) {


            Token TokenP3 = mCompiler.getTokenAvanteStatus(TokenTipo.PONTOVIRGULA, "Era esperado PONTO E VIRGULA !");


            String mLocalRequisicao = mCompiler.getLocal() + TokenC.getConteudo();

            File arq = new File(mLocalRequisicao);
            if (arq.exists()) {

                if (!mCompiler.getRequisitados().contains(mLocalRequisicao)) {
                    mCompiler.getRequisitados().add(mLocalRequisicao);


                    Compiler CompilerC = new Compiler();
                    CompilerC.importando(mLocalRequisicao, mCompiler.getRequisitados());

                    mCompiler.getErros_Lexer().addAll(CompilerC.getErros_Lexer());
                    mCompiler.getErros_Compiler().addAll(CompilerC.getErros_Compiler());

                    mCompiler.getComentarios().addAll(CompilerC.getComentarios());


                    for (AST ASTCorrente : CompilerC.getAST("SIGMAZ").getASTS()) {
                        if (ASTCorrente.mesmoTipo("PACKAGE")){

                            AST AST_Corrente = getPackage(ASTPai,ASTCorrente.getNome());

                            for (AST ASTP : ASTCorrente.getASTS()) {
                                AST_Corrente.getASTS().add(ASTP);
                            }
                        }else{
                            ASTPai.getASTS().add(ASTCorrente);
                        }

                    }

                }


            } else {
                mCompiler.errarCompilacao("Importacao nao encontrada : " + mLocalRequisicao, TokenC);
            }


        } else {
            mCompiler.errarCompilacao("Era esperado o caminho de uma Importacao !", TokenC);
        }

    }

    public AST getPackage(AST ASTPai, String eNome) {

        AST AST_Corrente = null;
        boolean enc = false;

        for (AST eAST : ASTPai.getASTS()) {
            if (eAST.mesmoTipo("PACKAGE") && eAST.mesmoNome(eNome)) {
                AST_Corrente = eAST;
                enc = true;
                break;
            }
        }

        if (!enc) {
            AST_Corrente = new AST("PACKAGE");
            AST_Corrente.setNome(eNome);
            ASTPai.getASTS().add(AST_Corrente);
        }

        return AST_Corrente;
    }

}
