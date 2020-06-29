package Sigmaz.Compilador;

import Sigmaz.Lexer.Token;
import Sigmaz.Lexer.TokenTipo;
import Sigmaz.Utils.AST;

public class AST_Start {

    private Compiler mCompiler;

    public AST_Start(Compiler eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {

        AST AST_Corpo = ASTPai.criarBranch("STARTED");

        Token TokenC = mCompiler.getTokenAvanteStatus(TokenTipo.CHAVE_ABRE, "Era esperado abrir chaves");

        if (TokenC.getTipo() != TokenTipo.CHAVE_ABRE) {
            return;
        }

        boolean saiu = false;

        while (mCompiler.Continuar()) {
            Token TokenD = mCompiler.getTokenAvante();
            if (TokenD.getTipo() == TokenTipo.CHAVE_FECHA) {
                saiu = true;
                break;
            }else if (TokenD.getTipo()==TokenTipo.ID ){


                AST ASTCorrente = AST_Corpo.criarBranch("APPLY");
                AST AST_Set = ASTCorrente.criarBranch("SETTABLE");


                AST_Set.setNome(TokenD.getConteudo());
                AST_Set.setValor("ID");

                Token P2 = mCompiler.getTokenAvante();

                if (P2.getTipo() == TokenTipo.IGUAL) {


                    AST ASTC = ASTCorrente.criarBranch("VALUE");

                    AST_Value mAST = new AST_Value(mCompiler);

                    mAST.init(ASTC);
                }else{

                    mCompiler.errarCompilacao("Era esperado = " + P2.getConteudo(), P2);

                }


            } else{
                mCompiler.errarCompilacao("Comando Deconhecido : " + TokenD.getConteudo(), TokenD);
            }

        }

        if (!saiu) {
            mCompiler.errarCompilacao("Era esperado fechar chaves" + mCompiler.getTokenAvante().getConteudo(), mCompiler.getTokenAvante());
        }

    }

}