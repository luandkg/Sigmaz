package Sigmaz.S04_Compilador.Fluxo;

import Sigmaz.S04_Compilador.CompilerUnit;
import Sigmaz.S03_Parser.Token;
import Sigmaz.S03_Parser.TokenTipo;
import Sigmaz.S00_Utilitarios.AST;

public class AST_Change {

    private CompilerUnit mCompiler;

    public AST_Change(CompilerUnit eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {


       // Token TokenC = mCompiler.getTokenAvante();
       // if (TokenC.getTipo() == TokenTipo.ID) {
      //  } else {
      //      mCompiler.errarCompilacao("Era esperado uma variavel TYPE para uma CHANGE !", TokenC);
      //  }

        Token TokenC = mCompiler.getTokenAvanteStatus(TokenTipo.PARENTESES_ABRE, "Era esperado Abrir Parenteses");

        AST AST_Corrente = new AST("CHANGE");


        AST AST_CType = AST_Corrente.criarBranch("TYPE");

        AST_Value mASTType = new AST_Value(mCompiler);
        mASTType.setBloco();
        mASTType.init(AST_CType);
        AST_CType.setTipo("TYPE");



        AST_Corrente.setNome(TokenC.getConteudo());
        ASTPai.getASTS().add(AST_Corrente);
        AST AST_Corpo = AST_Corrente.criarBranch("TYPED");

        Token TokenC1 = mCompiler.getTokenAvanteStatus(TokenTipo.SETA, "Era esperado -> ");

        Token TokenC2 = mCompiler.getTokenAvanteStatus(TokenTipo.CHAVE_ABRE, "Era esperado abrir chaves");



        boolean saiu = false;

        while (mCompiler.Continuar()) {
            Token TokenD = mCompiler.getTokenAvante();
            if (TokenD.getTipo() == TokenTipo.CHAVE_FECHA) {
                saiu = true;
                break;
            } else if (TokenD.getTipo() == TokenTipo.ID) {


                AST ASTCorrente = AST_Corpo.criarBranch("APPLY");
                AST AST_Set = ASTCorrente.criarBranch("SETTABLE");


                AST_Set.setNome(TokenD.getConteudo());
                AST_Set.setValor("ID");

                Token P2 = mCompiler.getTokenAvante();

                if (P2.getTipo() == TokenTipo.IGUAL) {


                    AST ASTC = ASTCorrente.criarBranch("VALUE");

                    AST_Value mAST = new AST_Value(mCompiler);

                    mAST.init(ASTC);
                } else {

                    mCompiler.errarCompilacao("Era esperado = " + P2.getConteudo(), P2);

                }


            } else {
                mCompiler.errarCompilacao("Comando Deconhecido : " + TokenD.getConteudo(), TokenD);
            }

        }

        if (!saiu) {
            mCompiler.errarCompilacao("Era esperado fechar chaves" + mCompiler.getTokenAvante().getConteudo(), mCompiler.getTokenAvante());
        }


    }
}
