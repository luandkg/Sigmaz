package Sigmaz.Compilador.Complexo;

import Sigmaz.Compilador.Alocador.AST_Alocador;
import Sigmaz.Compilador.Alocador.AST_Def;
import Sigmaz.Compilador.Bloco.AST_Action;
import Sigmaz.Compilador.Bloco.AST_Director;
import Sigmaz.Compilador.Bloco.AST_Function;
import Sigmaz.Compilador.Bloco.AST_Operation;
import Sigmaz.Compilador.CompilerUnit;
import Sigmaz.Lexer.Token;
import Sigmaz.Lexer.TokenTipo;
import Sigmaz.Utils.AST;

public class AST_StructCorpo {

    private CompilerUnit mCompiler;

    public AST_StructCorpo(CompilerUnit eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST AST_Corrente, AST AST_Inits, String NomeStruct) {


        AST AST_Corpo = AST_Corrente.criarBranch("BODY");

        String VISIBILIDADE = "ALL";


        Token TokenD = mCompiler.getTokenAvanteStatus(TokenTipo.CHAVE_ABRE, "Era esperado abrir chaves");

        if (TokenD.getTipo() != TokenTipo.CHAVE_ABRE) {
            return;
        }

        boolean saiu = false;

        while (mCompiler.Continuar()) {
            Token TokenC = mCompiler.getTokenAvante();
            if (TokenC.getTipo() == TokenTipo.CHAVE_FECHA) {
                saiu = true;
                break;
            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("init")) {

                AST_StructInit mAST = new AST_StructInit(mCompiler);
                mAST.init(AST_Inits, NomeStruct,VISIBILIDADE);


            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("act")) {

                AST_Action mAST = new AST_Action(mCompiler);
                mAST.init(AST_Corpo,VISIBILIDADE);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("func")) {

                AST_Function mAST = new AST_Function(mCompiler);
                mAST.init(AST_Corpo,VISIBILIDADE);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("mockiz")) {

                AST_Alocador mAST = new AST_Alocador(mCompiler);
                mAST.init("MOCKIZ",AST_Corpo,VISIBILIDADE);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("define")) {


                AST_Def mAST = new AST_Def(mCompiler);
                mAST.init_Define(AST_Corpo,VISIBILIDADE);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("operator")) {

                AST_Operation mAST = new AST_Operation(mCompiler);
                mAST.init(AST_Corpo);
            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("director")) {

                AST_Director mAST = new AST_Director(mCompiler);
                mAST.init(AST_Corpo,"ALL");

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("all")) {

                Token TokenC2 = mCompiler.getTokenAvanteStatus(TokenTipo.DOISPONTOS, "Era esperado :");

                VISIBILIDADE = "ALL";


            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("extern")) {

                Token TokenC2 = mCompiler.getTokenAvanteStatus(TokenTipo.DOISPONTOS, "Era esperado :");

                VISIBILIDADE = "EXTERN";
            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("implicit")) {

                Token TokenC2 = mCompiler.getTokenAvanteStatus(TokenTipo.DOISPONTOS, "Era esperado :");

                VISIBILIDADE = "IMPLICIT";

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("restrict")) {

                Token TokenC2 = mCompiler.getTokenAvanteStatus(TokenTipo.DOISPONTOS, "Era esperado :");

                VISIBILIDADE = "RESTRICT";
            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("allow")) {

                Token TokenC2 = mCompiler.getTokenAvanteStatus(TokenTipo.DOISPONTOS, "Era esperado :");

                VISIBILIDADE = "ALLOW";


            } else {


                mCompiler.errarCompilacao("Comando Deconhecido : " + TokenC.getConteudo(), TokenC);

            }
        }

        if (!saiu) {
            mCompiler.errarCompilacao("Era esperado fechar chaves" + mCompiler.getTokenAvante().getConteudo(), mCompiler.getTokenAvante());
        }
    }

    public void initExternal(AST AST_Corrente) {


        AST AST_Corpo = AST_Corrente.criarBranch("BODY");

        String VISIBILIDADE = "EXTERN";


        Token TokenD = mCompiler.getTokenAvanteStatus(TokenTipo.CHAVE_ABRE, "Era esperado abrir chaves");

        if (TokenD.getTipo() != TokenTipo.CHAVE_ABRE) {
            return;
        }

        boolean saiu = false;

        while (mCompiler.Continuar()) {
            Token TokenC = mCompiler.getTokenAvante();
            if (TokenC.getTipo() == TokenTipo.CHAVE_FECHA) {
                saiu = true;
                break;


            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("act")) {

                AST_Action mAST = new AST_Action(mCompiler);
                mAST.init(AST_Corpo,VISIBILIDADE);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("func")) {

                AST_Function mAST = new AST_Function(mCompiler);
                mAST.init(AST_Corpo,VISIBILIDADE);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("mockiz")) {

                AST_Alocador mAST = new AST_Alocador(mCompiler);
                mAST.init("MOCKIZ",AST_Corpo,VISIBILIDADE);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("define")) {

                AST_Alocador mAST = new AST_Alocador(mCompiler);
                mAST.init("DEFINE",AST_Corpo,VISIBILIDADE);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("operator")) {

                AST_Operation mAST = new AST_Operation(mCompiler);
                mAST.init(AST_Corpo);


            } else {


                mCompiler.errarCompilacao("Comando Deconhecido : " + TokenC.getConteudo(), TokenC);

            }
        }

        if (!saiu) {
            mCompiler.errarCompilacao("Era esperado fechar chaves" + mCompiler.getTokenAvante().getConteudo(), mCompiler.getTokenAvante());
        }
    }


}
