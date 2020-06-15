package Sigmaz.Compilador;

import Sigmaz.Lexer.Token;
import Sigmaz.Lexer.TokenTipo;
import Sigmaz.Utils.AST;

public class AST_Corpo {

    private Compiler mCompiler;

    public AST_Corpo(Compiler eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {


        Token TokenC= mCompiler.getTokenAvanteStatus(TokenTipo.CHAVE_ABRE,"Era esperado abrir chaves" );

        if (TokenC.getTipo() != TokenTipo.CHAVE_ABRE) {
            return;
        }

        boolean saiu = false;

        while (mCompiler.Continuar()) {
            Token TokenD = mCompiler.getTokenAvante();
            if (TokenD.getTipo() == TokenTipo.CHAVE_FECHA) {
                saiu = true;
                break;
            }else if (TokenD.getTipo()==TokenTipo.ID && TokenD.mesmoConteudo("def")){

                AST_Def mAST = new AST_Def(mCompiler);
                mAST.init(ASTPai);

            }else if (TokenD.getTipo()==TokenTipo.ID && TokenD.mesmoConteudo("moc")){

                AST_Moc mAST = new AST_Moc(mCompiler);
                mAST.init(ASTPai);

            }else if (TokenD.getTipo()==TokenTipo.ID && TokenD.mesmoConteudo("return")){

                AST_Return mAST = new AST_Return(mCompiler);
                mAST.init(ASTPai);

            }else if (TokenD.getTipo()==TokenTipo.ID && TokenD.mesmoConteudo("invoke")){

                AST_Invoke mAST = new AST_Invoke(mCompiler);
                mAST.init(ASTPai);

            }else if (TokenD.getTipo()==TokenTipo.ID ){

                AST ASTC = ASTPai.criarBranch("ACTION");

                AST_Comando mAST = new AST_Comando(mCompiler);
                mAST.init(ASTC);

            }else{



                mCompiler.errarCompilacao("Comando Deconhecido : " + TokenD.getConteudo(), TokenD.getInicio());


            }
        }

        if (!saiu) {
            mCompiler.errarCompilacao("Era esperado fechar chaves" + mCompiler.getTokenAvante().getConteudo(), mCompiler.getTokenAvante().getInicio());
        }
    }

}
