package Sigmaz.S01_Compilador.C03_Parser.Processador;

import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S01_Compilador.C02_Lexer.Token;
import Sigmaz.S01_Compilador.C02_Lexer.TokenTipo;
import Sigmaz.S01_Compilador.C03_Parser.Parser;

public class AST_Proc {

    private Parser mCompiler;

    public AST_Proc(Parser eCompiler) {
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

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo_SemCS("SIZE")) {

                AST_Size mAST = new AST_Size(mCompiler);
                mAST.init(AST_Corrente);

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo_SemCS("APPEND")) {

                AST_Append mAST = new AST_Append(mCompiler);
                mAST.init(AST_Corrente);

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo_SemCS("INT")) {

                AST_Int mAST = new AST_Int(mCompiler);
                mAST.init(AST_Corrente);

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo_SemCS("REAL")) {

                AST_Real mAST = new AST_Real(mCompiler);
                mAST.init(AST_Corrente);


            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo_SemCS("INVOKE")) {

                AST_Instrucao mAST = new AST_Instrucao(mCompiler);
                mAST.init(AST_Corrente);

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo_SemCS("INT_STRING")) {

                AST_Instrucao mAST = new AST_Instrucao(mCompiler);
                mAST.init(AST_Corrente);

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo_SemCS("NUM_STRING")) {

                AST_Instrucao mAST = new AST_Instrucao(mCompiler);
                mAST.init(AST_Corrente);

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo_SemCS("BOOL_STRING")) {

                AST_Instrucao mAST = new AST_Instrucao(mCompiler);
                mAST.init(AST_Corrente);

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo_SemCS("NUM_INT")) {

                AST_Instrucao mAST = new AST_Instrucao(mCompiler);
                mAST.init(AST_Corrente);

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo_SemCS("NUM_DEC")) {

                AST_Instrucao mAST = new AST_Instrucao(mCompiler);
                mAST.init(AST_Corrente);


            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo_SemCS("STAGE")) {

                AST_Instrucao mAST = new AST_Instrucao(mCompiler);
                mAST.init(AST_Corrente);


            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo_SemCS("PRINT")) {

                AST_Instrucao mAST = new AST_Instrucao(mCompiler);
                mAST.init(AST_Corrente);


            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo_SemCS("BOOL_INVERSE")) {

                AST_Instrucao mAST = new AST_Instrucao(mCompiler);
                mAST.init(AST_Corrente);

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo_SemCS("OPEN")) {

                AST_Instrucao mAST = new AST_Instrucao(mCompiler);
                mAST.init(AST_Corrente);

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo_SemCS("OPEN_OR_CREATE")) {

                AST_Instrucao mAST = new AST_Instrucao(mCompiler);
                mAST.init(AST_Corrente);


            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo_SemCS("CLOSE")) {

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
