package Sigmaz.S01_Compilador.C03_Parser.Complexo;

import Sigmaz.S01_Compilador.C03_Parser.Alocador.AST_Alocador;
import Sigmaz.S01_Compilador.C03_Parser.Bloco.AST_Action;
import Sigmaz.S01_Compilador.C03_Parser.Bloco.AST_Function;
import Sigmaz.S01_Compilador.C03_Parser.Parser;
import Sigmaz.S01_Compilador.C02_Lexer.Token;
import Sigmaz.S01_Compilador.C02_Lexer.TokenTipo;
import Sigmaz.S00_Utilitarios.AST;

public class AST_External {

    private Parser mCompiler;

    public AST_External(Parser eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {

        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.ID) {

            AST AST_Corrente = new AST("STRUCT");
            AST_Corrente.setNome(TokenC.getConteudo());
            ASTPai.getASTS().add(AST_Corrente);

            AST AST_Generico = AST_Corrente.criarBranch("GENERIC");
            AST_Generico.setNome("FALSE");

           AST AST_With =  AST_Corrente.criarBranch("WITH");
            AST_With.setValor("FALSE");

            AST AST_Model=  AST_Corrente.criarBranch("MODEL");
            AST_Model.setValor("FALSE");

            AST AST_Stages =  AST_Corrente.criarBranch("STAGES");
            AST_Stages.setValor("FALSE");

            AST mExtended = AST_Corrente.criarBranch("EXTENDED");
            mExtended.setNome("EXTERNAL");

            AST mBases = AST_Corrente.criarBranch("BASES");

            AST mRefers = AST_Corrente.criarBranch("REFERS");


            AST AST_Inits =  AST_Corrente.criarBranch("INITS");


            corpo(AST_Corrente);


        } else {
            mCompiler.errarCompilacao("Era esperado o nome para uma STRUCT !", TokenC);
        }


    }

    public void corpo(AST AST_Corrente) {


        AST AST_Corpo = AST_Corrente.criarBranch("BODY");

        String VISIBILIDADE = "EXPLICIT";


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


            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("explicit")) {

                VISIBILIDADE = mudarEscopo("EXPLICIT");

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("implicit")) {

                VISIBILIDADE = mudarEscopo("IMPLICIT");

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("all")) {

                mCompiler.errarCompilacao("Externals nao possuem escopo : ALL", TokenC);

                VISIBILIDADE = mudarEscopo("ALL");
            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("restrict")) {

                mCompiler.errarCompilacao("Externals nao possuem escopo : RESTRICT", TokenC);

                VISIBILIDADE = mudarEscopo("RESTRICT");
            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("extra")) {

                mCompiler.errarCompilacao("Externals nao possuem escopo : EXTRA", TokenC);

                VISIBILIDADE = mudarEscopo("EXTRA");
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