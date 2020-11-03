package Sigmaz.S01_Compilador.C03_Parser.Complexo;

import Sigmaz.S01_Compilador.C03_Parser.Organizador.AST_Generic;
import Sigmaz.S01_Compilador.C03_Parser.Alocador.AST_Alocador;
import Sigmaz.S01_Compilador.C03_Parser.Parser;
import Sigmaz.S01_Compilador.C02_Lexer.Token;
import Sigmaz.S01_Compilador.C02_Lexer.TokenTipo;
import Sigmaz.S00_Utilitarios.AST;

public class AST_TypeStruct {

    private Parser mCompiler;

    public AST_TypeStruct(Parser eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(String eNomePacote,AST ASTPai) {

        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.ID) {


            AST AST_Corrente = new AST("STRUCT");
            AST_Corrente.setNome(TokenC.getConteudo());
            AST_Corrente.setValor("");

            if (eNomePacote.length()==0) {
                AST_Corrente.setValor( TokenC.getConteudo());
            } else{
                AST_Corrente.setValor(eNomePacote + "<>" + TokenC.getConteudo());
            }

            ASTPai.getASTS().add(AST_Corrente);

            AST AST_Generico = AST_Corrente.criarBranch("GENERIC");
            AST_Generico.setNome("FALSE");

            AST AST_With =  AST_Corrente.criarBranch("WITH");
            AST_With.setValor("FALSE");

            AST AST_Model =  AST_Corrente.criarBranch("MODEL");
            AST_Model.setValor("FALSE");

            AST AST_Stages =  AST_Corrente.criarBranch("STAGES");
            AST_Stages.setValor("FALSE");

            AST mExtended = AST_Corrente.criarBranch("EXTENDED");
            mExtended.setNome("TYPE");

            AST mBases = AST_Corrente.criarBranch("BASES");

            AST mRefers = AST_Corrente.criarBranch("REFERS");


            AST AST_Inits =  AST_Corrente.criarBranch("INITS");

            Token TokenFuturo = mCompiler.getTokenFuturo();
            if (TokenFuturo.getTipo() == TokenTipo.ID && TokenFuturo.mesmoConteudo("in")) {

                mCompiler.Proximo();
                AST_Generico.setNome("TRUE");

                AST_Generic mg = new AST_Generic(mCompiler);
                mg.init_receber(AST_Generico);


            }

            AST AST_Corpo = AST_Corrente.criarBranch("BODY");


            Token TokenFuturoC = mCompiler.getTokenFuturo();
            if (TokenFuturoC.getTipo() == TokenTipo.ID && TokenFuturoC.mesmoConteudo("union")){

                AST AST_Base_01 = mBases.criarBranch("BASE");
                AST AST_Base_02 = mBases.criarBranch("BASE");


                mCompiler.proximo();

                Token TokenP_1 = mCompiler.getTokenAvanteStatus(TokenTipo.ID, "Era esperado o primeiro TYPE ");

                AST_Base_01.setNome(TokenP_1.getConteudo());

                AST AST_BASE_01_Generico = AST_Base_01.criarBranch("GENERIC");
                AST_BASE_01_Generico.setNome("FALSE");

                Token TokenFuturo_Base_01 = mCompiler.getTokenFuturo();
                if (TokenFuturo_Base_01.getTipo() == TokenTipo.ENVIAR) {

                    AST_BASE_01_Generico.setNome("TRUE");

                    AST_Generic mg = new AST_Generic(mCompiler);
                    mg.init(AST_BASE_01_Generico);

                }


                Token TokenC8 = mCompiler.getTokenAvanteStatus(TokenTipo.QUAD, "Era esperado QUAD ");


                Token TokenP_2 = mCompiler.getTokenAvanteStatus(TokenTipo.ID, "Era esperado o segundo TYPE ");


                AST_Base_02.setNome(TokenP_2.getConteudo());

                AST AST_BASE_02_Generico = AST_Base_02.criarBranch("GENERIC");
                AST_BASE_02_Generico.setNome("FALSE");

                Token TokenFuturo_Base_02 = mCompiler.getTokenFuturo();
                if (TokenFuturo_Base_02.getTipo() == TokenTipo.ENVIAR) {

                    AST_BASE_02_Generico.setNome("TRUE");

                    AST_Generic mg = new AST_Generic(mCompiler);
                    mg.init(AST_BASE_02_Generico);

                }

                Token TokenC9 = mCompiler.getTokenAvanteStatus(TokenTipo.PONTOVIRGULA, "Era esperado PONTO E VIRGULA ");

                mExtended.setNome("TYPE_UNION");






            }else{
                corpo(AST_Corpo);

            }


        } else {
            mCompiler.errarCompilacao("Era esperado o nome para uma TYPE !", TokenC);
        }


    }



    public void corpo(AST AST_Corrente) {



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

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("mockiz")) {

                AST_Alocador mAST = new AST_Alocador(mCompiler);
                mAST.init("MOCKIZ",AST_Corrente,VISIBILIDADE);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("define")) {

                AST_Alocador mAST = new AST_Alocador(mCompiler);
                mAST.init("DEFINE",AST_Corrente,VISIBILIDADE);


            } else {


                mCompiler.errarCompilacao("Comando Deconhecido : " + TokenC.getConteudo(), TokenC);

            }
        }

        if (!saiu) {
            mCompiler.errarCompilacao("Era esperado fechar chaves" + mCompiler.getTokenAvante().getConteudo(), mCompiler.getTokenAvante());
        }
    }



}