package Sigmaz.Compilador;

import Sigmaz.Lexer.Token;
import Sigmaz.Lexer.TokenTipo;
import Sigmaz.Utils.AST;

public class AST_Vector {

    private CompilerUnit mCompiler;

    public AST_Vector(CompilerUnit eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {


        boolean saiu = false;

        boolean mais = false;


        while (mCompiler.Continuar()) {
            Token TokenD = mCompiler.getTokenAvante();
            if (TokenD.getTipo() == TokenTipo.CHAVE_FECHA) {

                if (mais) {
                    mCompiler.errarCompilacao("Era esperado outro argumento para o vetor", TokenD);
                }

                saiu = true;
                break;
            } else {

                mais = false;

                Token P1 = mCompiler.getTokenCorrente();

                //System.out.println("CORRENTE INICIAR :: " + P1.getConteudo()+ " :: " + P1.getLinha());

                mCompiler.voltar();


                AST_Value_ItemVector mAST = new AST_Value_ItemVector(mCompiler);
                mAST.initArgumento(ASTPai.criarBranch("VALUE"));

                Token P2 = mCompiler.getTokenCorrente();

              //  System.out.println("CORRENTE SAIR :: " + P2.getConteudo() + " :: " + P2.getLinha());


                if (P2.getTipo() == TokenTipo.VIRGULA) {
                    mais = true;
                } else if (P2.getTipo() == TokenTipo.CHAVE_FECHA) {
                    saiu = true;
                    break;
                } else {
                    mCompiler.errarCompilacao("Era esperado um argumento 3 : " + P2.getConteudo(), P2);
                }

            }
        }

        if (!saiu) {
            mCompiler.errarCompilacao("Era esperado fechar chaves" + mCompiler.getTokenAvante().getConteudo(), mCompiler.getTokenAvante());
        }
    }


}
