package Sigmaz.S01_Compilador.C03_Parser.Complexo;

import Sigmaz.S01_Compilador.C03_Parser.Organizador.AST_Generic;
import Sigmaz.S01_Compilador.C03_Parser.Alocador.AST_Alocador;
import Sigmaz.S01_Compilador.C03_Parser.Parser;
import Sigmaz.S01_Compilador.C02_Lexer.Token;
import Sigmaz.S01_Compilador.C02_Lexer.TokenTipo;
import Sigmaz.S01_Compilador.Orquestrantes;
import Sigmaz.S08_Utilitarios.AST;

public class AST_TypeStruct {

    private Parser mCompiler;

    public AST_TypeStruct(Parser eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(String eNomePacote,AST ASTPai) {

        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.ID) {


            AST AST_Corrente = new AST(Orquestrantes.COMPLEX);
            AST_Corrente.setNome(TokenC.getConteudo());
            AST_Corrente.setValor("");

            if (eNomePacote.length()==0) {
                AST_Corrente.setValor( TokenC.getConteudo());
            } else{
                AST_Corrente.setValor(eNomePacote + "<>" + TokenC.getConteudo());
            }

            ASTPai.getASTS().add(AST_Corrente);

            AST AST_Generico = AST_Corrente.criarBranch(Orquestrantes.GENERIC);
            AST_Generico.setNome(Orquestrantes.FALSE);

            AST AST_With =  AST_Corrente.criarBranch(Orquestrantes.WITH);
            AST_With.setValor(Orquestrantes.FALSE);

            AST AST_Model =  AST_Corrente.criarBranch(Orquestrantes.MODEL);
            AST_Model.setValor(Orquestrantes.FALSE);

            AST AST_Stages =  AST_Corrente.criarBranch(Orquestrantes.STAGES);
            AST_Stages.setValor(Orquestrantes.FALSE);

            AST mExtended = AST_Corrente.criarBranch(Orquestrantes.EXTENDED);
            mExtended.setNome(Orquestrantes.TYPE);

            AST mBases = AST_Corrente.criarBranch(Orquestrantes.BASES);

            AST mRefers = AST_Corrente.criarBranch(Orquestrantes.REFERS);

            AST AST_Inits =  AST_Corrente.criarBranch(Orquestrantes.INITS);

            Token TokenFuturo = mCompiler.getTokenFuturo();
            if (TokenFuturo.getTipo() == TokenTipo.ID && TokenFuturo.mesmoConteudo("in")) {

                mCompiler.Proximo();
                AST_Generico.setNome(Orquestrantes.TRUE);

                AST_Generic mg = new AST_Generic(mCompiler);
                mg.init_receber(AST_Generico);


            }

            AST AST_Corpo = AST_Corrente.criarBranch(Orquestrantes.BODY);


            Token TokenFuturoC = mCompiler.getTokenFuturo();
            if (TokenFuturoC.getTipo() == TokenTipo.ID && TokenFuturoC.mesmoConteudo("union")){

                AST AST_Base_01 = mBases.criarBranch(Orquestrantes.BASE);
                AST AST_Base_02 = mBases.criarBranch(Orquestrantes.BASE);


                mCompiler.proximo();

                Token TokenP_1 = mCompiler.getTokenAvanteStatus(TokenTipo.ID, "Era esperado o primeiro TYPE ");

                AST_Base_01.setNome(TokenP_1.getConteudo());

                AST AST_BASE_01_Generico = AST_Base_01.criarBranch(Orquestrantes.GENERIC);
                AST_BASE_01_Generico.setNome(Orquestrantes.FALSE);

                Token TokenFuturo_Base_01 = mCompiler.getTokenFuturo();
                if (TokenFuturo_Base_01.getTipo() == TokenTipo.ENVIAR) {

                    AST_BASE_01_Generico.setNome(Orquestrantes.TRUE);

                    AST_Generic mg = new AST_Generic(mCompiler);
                    mg.init(AST_BASE_01_Generico);

                }


                Token TokenC8 = mCompiler.getTokenAvanteStatus(TokenTipo.QUAD, "Era esperado QUAD ");


                Token TokenP_2 = mCompiler.getTokenAvanteStatus(TokenTipo.ID, "Era esperado o segundo TYPE ");


                AST_Base_02.setNome(TokenP_2.getConteudo());

                AST AST_BASE_02_Generico = AST_Base_02.criarBranch(Orquestrantes.GENERIC);
                AST_BASE_02_Generico.setNome(Orquestrantes.FALSE);

                Token TokenFuturo_Base_02 = mCompiler.getTokenFuturo();
                if (TokenFuturo_Base_02.getTipo() == TokenTipo.ENVIAR) {

                    AST_BASE_02_Generico.setNome(Orquestrantes.TRUE);

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



        String VISIBILIDADE = Orquestrantes.ALL;


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
                mAST.init(Orquestrantes.MOCKIZ,AST_Corrente,VISIBILIDADE);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("define")) {

                AST_Alocador mAST = new AST_Alocador(mCompiler);
                mAST.init(Orquestrantes.DEFINE,AST_Corrente,VISIBILIDADE);


            } else {


                mCompiler.errarCompilacao("Comando Deconhecido : " + TokenC.getConteudo(), TokenC);

            }
        }

        if (!saiu) {
            mCompiler.errarCompilacao("Era esperado fechar chaves" + mCompiler.getTokenAvante().getConteudo(), mCompiler.getTokenAvante());
        }
    }



}