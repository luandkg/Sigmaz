package Sigmaz.S01_Compilador.C03_Parser.Complexo;

import Sigmaz.S01_Compilador.C03_Parser.Alocador.AST_Alocador;
import Sigmaz.S01_Compilador.C03_Parser.Bloco.AST_Action;
import Sigmaz.S01_Compilador.C03_Parser.Bloco.AST_Function;
import Sigmaz.S01_Compilador.C03_Parser.Parser;
import Sigmaz.S01_Compilador.C02_Lexer.Token;
import Sigmaz.S01_Compilador.C02_Lexer.TokenTipo;
import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S01_Compilador.Orquestrantes;
import Sigmaz.S01_Compilador.Termos;

public class AST_External {

    private Parser mCompiler;

    public AST_External(Parser eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {

        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.ID) {

            AST AST_Corrente = new AST(Orquestrantes.STRUCT);
            AST_Corrente.setNome(TokenC.getConteudo());
            ASTPai.getASTS().add(AST_Corrente);

            AST AST_Generico = AST_Corrente.criarBranch(Orquestrantes.GENERIC);
            AST_Generico.setNome(Orquestrantes.FALSE);

            AST AST_With = AST_Corrente.criarBranch(Orquestrantes.WITH);
            AST_With.setValor(Orquestrantes.FALSE);

            AST AST_Model = AST_Corrente.criarBranch(Orquestrantes.MODEL);
            AST_Model.setValor(Orquestrantes.FALSE);

            AST AST_Stages = AST_Corrente.criarBranch(Orquestrantes.STAGES);
            AST_Stages.setValor(Orquestrantes.FALSE);

            AST mExtended = AST_Corrente.criarBranch(Orquestrantes.EXTENDED);
            mExtended.setNome(Orquestrantes.EXTERNAL);

            AST mBases = AST_Corrente.criarBranch(Orquestrantes.BASES);

            AST mRefers = AST_Corrente.criarBranch(Orquestrantes.REFERS);

            AST AST_Inits = AST_Corrente.criarBranch(Orquestrantes.INITS);


            corpo(AST_Corrente);


        } else {
            mCompiler.errarCompilacao("Era esperado o nome para uma STRUCT !", TokenC);
        }


    }

    public void corpo(AST AST_Corrente) {


        AST AST_Corpo = AST_Corrente.criarBranch(Orquestrantes.BODY);

        String VISIBILIDADE = Orquestrantes.EXPLICIT;


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


            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo(Termos.ACT)) {

                AST_Action mAST = new AST_Action(mCompiler);
                mAST.init(AST_Corpo, VISIBILIDADE);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo(Termos.FUNC)) {

                AST_Function mAST = new AST_Function(mCompiler);
                mAST.init(AST_Corpo, VISIBILIDADE);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo(Termos.MOCKIZ)) {

                AST_Alocador mAST = new AST_Alocador(mCompiler);
                mAST.initMockiz( AST_Corpo, VISIBILIDADE);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo(Termos.DEFINE)) {

                AST_Alocador mAST = new AST_Alocador(mCompiler);
                mAST.initDefine( AST_Corpo, VISIBILIDADE);


            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo(Termos.EXPLICIT)) {

                VISIBILIDADE = mudarEscopo(Orquestrantes.EXPLICIT);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo(Termos.IMPLICIT)) {

                VISIBILIDADE = mudarEscopo(Orquestrantes.IMPLICIT);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo(Termos.ALL)) {

                mCompiler.errarCompilacao("Externals nao possuem escopo : ALL", TokenC);

                VISIBILIDADE = mudarEscopo(Orquestrantes.ALL);
            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo(Termos.RESTRICT)) {

                mCompiler.errarCompilacao("Externals nao possuem escopo : RESTRICT", TokenC);

                VISIBILIDADE = mudarEscopo(Orquestrantes.RESTRICT);
            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo(Termos.EXTRA)) {

                mCompiler.errarCompilacao("Externals nao possuem escopo : EXTRA", TokenC);

                VISIBILIDADE = mudarEscopo(Orquestrantes.EXTRA);
            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("BLOCK")) {

                mCompiler.errarCompilacao("Externals nao possuem escopo : BLOCK", TokenC);

                VISIBILIDADE = mudarEscopo("EXTRA");

            } else {


                mCompiler.errarCompilacao("Comando Deconhecido : " + TokenC.getConteudo(), TokenC);

            }
        }

        if (!saiu) {
            mCompiler.errarCompilacao("Era esperado fechar chaves" + mCompiler.getTokenAvante().getConteudo(), mCompiler.getTokenAvante());
        }
    }


    public String mudarEscopo(String eEscopo) {

        Token TokenC2 = mCompiler.getTokenAvanteStatus(TokenTipo.DOISPONTOS, "Era esperado :");

        return eEscopo;

    }

}