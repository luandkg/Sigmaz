package Sigmaz.S04_Compilador.Processador;

import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S03_Parser.Token;
import Sigmaz.S03_Parser.TokenTipo;
import Sigmaz.S04_Compilador.CompilerUnit;
import Sigmaz.S04_Compilador.Fluxo.AST_Value;

public class AST_Proc {

    private CompilerUnit mCompiler;

    public AST_Proc(CompilerUnit eCompiler) {
        mCompiler = eCompiler;
    }


    public void init(AST ASTPai) {

        Token TokenA = mCompiler.getTokenAvanteStatus(TokenTipo.SETA, "Era esperado uma SETA");
        Token TokenB = mCompiler.getTokenAvanteStatus(TokenTipo.CHAVE_ABRE, "Era esperado abrir chaves");


        AST AST_Corrente = new AST("PROC");
        ASTPai.getASTS().add(AST_Corrente);


        boolean saiu = false;

        while (mCompiler.Continuar()) {
            Token TokenD = mCompiler.getTokenAvante();
            if (TokenD.getTipo() == TokenTipo.CHAVE_FECHA) {
                saiu = true;
                break;

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo_SemCS("SET")) {

                AST_Set mAST = new AST_Set(mCompiler);
                mAST.init(AST_Corrente);
            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo_SemCS("MOV")) {

                AST_Instrucao mAST = new AST_Instrucao(mCompiler);
                mAST.init(AST_Corrente);

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo_SemCS("OPE")) {

                AST_Ope mAST = new AST_Ope(mCompiler);
                mAST.init(AST_Corrente);

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo_SemCS("PROC")) {

                AST_Instrucao mAST = new AST_Instrucao(mCompiler);
                mAST.init(AST_Corrente);

            } else {


                mCompiler.errarCompilacao("Instrucao de Processo Deconhecida : " + TokenD.getConteudo(), TokenD);


            }
        }

        if (!saiu) {
            mCompiler.errarCompilacao("Era esperado fechar chaves" + mCompiler.getTokenAvante().getConteudo(), mCompiler.getTokenAvante());
        }

    }
}
