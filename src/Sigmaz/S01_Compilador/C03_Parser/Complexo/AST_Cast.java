package Sigmaz.S01_Compilador.C03_Parser.Complexo;

import Sigmaz.S01_Compilador.C03_Parser.Fluxo.AST_Corpo;
import Sigmaz.S01_Compilador.C03_Parser.Parser;
import Sigmaz.S01_Compilador.C02_Lexer.Token;
import Sigmaz.S01_Compilador.C02_Lexer.TokenTipo;
import Sigmaz.S00_Utilitarios.AST;

public class AST_Cast {

    private Parser mCompiler;

    public AST_Cast(Parser eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(String eNomePacote, AST ASTPai) {

        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.ID) {

            AST AST_Corrente = new AST("CAST");
            AST_Corrente.setNome(TokenC.getConteudo());
            ASTPai.getASTS().add(AST_Corrente);


            if (eNomePacote.length() == 0) {
                AST_Corrente.setValor(TokenC.getConteudo());
            } else {
                AST_Corrente.setValor(eNomePacote + "<>" + TokenC.getConteudo());
            }


            Token TokenP = mCompiler.getTokenAvanteStatus(TokenTipo.CHAVE_ABRE, "Era esperado abrir chaves");

            if (TokenP.getTipo() != TokenTipo.CHAVE_ABRE) {
                return;
            }

            boolean saiu = false;

            while (mCompiler.Continuar()) {
                Token TokenD = mCompiler.getTokenAvante();
                if (TokenD.getTipo() == TokenTipo.CHAVE_FECHA) {
                    saiu = true;
                    break;

                } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("getter")) {

                    getter(TokenC.getConteudo(), AST_Corrente, ASTPai);

                } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("setter")) {

                    setter(TokenC.getConteudo(), AST_Corrente, ASTPai);

                } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("default")) {

                    defaultx(AST_Corrente, ASTPai);

                } else {
                    mCompiler.errarCompilacao("Comando Deconhecido : " + TokenD.getConteudo(), TokenD);
                }
            }

            if (!saiu) {
                mCompiler.errarCompilacao("Era esperado fechar parenteses !", TokenC);
            }

        } else {
            mCompiler.errarCompilacao("Era esperado o nome para uma CAST !", TokenC);
        }


    }





    public void getter(String eNome, AST ASTPai, AST ASTAvo) {

        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.ID) {

            AST AST_Corrente = new AST("GETTER");
            AST_Corrente.setNome(TokenC.getConteudo());
            ASTPai.getASTS().add(AST_Corrente);

            String eNomeVar = TokenC.getConteudo();


            Token TokenC2 = mCompiler.getTokenAvanteStatus(TokenTipo.DOISPONTOS, "Era esperado Dois Pontos");
            Token TokenC3 = mCompiler.getTokenAvanteStatus(TokenTipo.ID, "Era esperado uma Tipagem");
            Token TokenC4 = mCompiler.getTokenAvanteStatus(TokenTipo.SETA, "Era esperado -> ");

            AST_Corrente.setValor(TokenC3.getConteudo());


            AST_Corpo mCorpo = new AST_Corpo(mCompiler);
            mCorpo.init(AST_Corrente);



        } else {
            mCompiler.errarCompilacao("Era esperado o nome da GETTER !", TokenC);
        }

    }


    public void setter(String eNome, AST ASTPai, AST ASTAvo) {

        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.ID) {

            AST AST_Corrente = new AST("SETTER");
            AST_Corrente.setNome(TokenC.getConteudo());
            ASTPai.getASTS().add(AST_Corrente);


            Token TokenC2 = mCompiler.getTokenAvanteStatus(TokenTipo.DOISPONTOS, "Era esperado Dois Pontos");
            Token TokenC3 = mCompiler.getTokenAvanteStatus(TokenTipo.ID, "Era esperado uma Tipagem");
            Token TokenC4 = mCompiler.getTokenAvanteStatus(TokenTipo.SETA, "Era esperado -> ");

            String aTipo = TokenC3.getConteudo();
            AST_Corrente.setValor(aTipo);


            String eNomeVar = TokenC.getConteudo();
            String aNome = TokenC.getConteudo();

            AST_Corpo mCorpo = new AST_Corpo(mCompiler);
            mCorpo.init(AST_Corrente);

        } else {
            mCompiler.errarCompilacao("Era esperado o nome da SETTER !", TokenC);
        }

    }

    public void defaultx(AST ASTPai, AST ASTAvo) {


        AST AST_Corrente = new AST("DEFAULT");
        ASTPai.getASTS().add(AST_Corrente);


        Token TokenC4 = mCompiler.getTokenAvanteStatus(TokenTipo.SETA, "Era esperado -> ");


        AST_Corpo mCorpo = new AST_Corpo(mCompiler);
        mCorpo.init(AST_Corrente);


    }

}