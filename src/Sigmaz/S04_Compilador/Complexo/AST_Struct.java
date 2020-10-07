package Sigmaz.S04_Compilador.Complexo;

import Sigmaz.S04_Compilador.Alocador.AST_Alocador;
import Sigmaz.S04_Compilador.Alocador.AST_Def;
import Sigmaz.S04_Compilador.Bloco.AST_Action;
import Sigmaz.S04_Compilador.Bloco.AST_Director;
import Sigmaz.S04_Compilador.Bloco.AST_Function;
import Sigmaz.S04_Compilador.Bloco.AST_Operation;
import Sigmaz.S04_Compilador.Organizador.AST_Generic;
import Sigmaz.S04_Compilador.CompilerUnit;
import Sigmaz.S03_Parser.Token;
import Sigmaz.S03_Parser.TokenTipo;
import Sigmaz.S00_Utilitarios.AST;

public class AST_Struct {

    private CompilerUnit mCompiler;

    public AST_Struct(CompilerUnit eCompiler) {
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
            mExtended.setNome("STRUCT");

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


            Token Futuro = mCompiler.getTokenFuturo();
            if (Futuro.getTipo() == TokenTipo.ID && Futuro.mesmoConteudo("with")) {
                mCompiler.Proximo();

                //System.out.println("vamos comecar a heranca...");

                Token TokenP = mCompiler.getTokenAvante();

                if (TokenP.getTipo() == TokenTipo.ID ) {

                    AST_With.setNome(TokenP.getConteudo());
                    AST_With.setValor("TRUE");

                }
            }

            Token Futuro_AT = mCompiler.getTokenFuturo();
            if (Futuro_AT.getTipo() == TokenTipo.ID && Futuro_AT.mesmoConteudo("at")) {
                mCompiler.Proximo();

              //  System.out.println("vamos comecar a modelar...");

                Token TokenP = mCompiler.getTokenAvante();

                if (TokenP.getTipo() == TokenTipo.ID ) {

                    AST_Model.setNome(TokenP.getConteudo());
                    AST_Model.setValor("TRUE");

                    AST AST_Gen = AST_Model.criarBranch("GENERIC");
                    AST_Gen.setNome("FALSE");

                    Token Futuro_AT2 = mCompiler.getTokenFuturo();
                    if (Futuro_AT2.getTipo() == TokenTipo.ENVIAR ) {

                        AST_Gen.setNome("TRUE");


                        AST_Generic mg = new AST_Generic(mCompiler);
                        mg.init(AST_Gen);


                    }



                }
            }

            corpo(AST_Corrente,AST_Inits,TokenC.getConteudo());


            if (AST_Inits.getASTS().size()==0){

                AST AST_IniPadrao = new AST("INIT");
                AST_IniPadrao.setNome(AST_Corrente.getNome());
                AST_Inits.getASTS().add(AST_IniPadrao);

                AST AST_Visibilidade = AST_IniPadrao.criarBranch("VISIBILITY");
                AST_Visibilidade.setNome("ALL");


                AST AST_Call = AST_IniPadrao.criarBranch("CALL");
                AST_Call.setValor("FALSE");

                AST_IniPadrao.criarBranch("ARGUMENTS");
                 AST_IniPadrao.criarBranch("BODY");
            }

        } else {
            mCompiler.errarCompilacao("Era esperado o nome para uma STRUCT !", TokenC);
        }


    }


    public void corpo(AST AST_Corrente, AST AST_Inits, String NomeStruct) {


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


            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("explicit")) {

                Token TokenC2 = mCompiler.getTokenAvanteStatus(TokenTipo.DOISPONTOS, "Era esperado :");

                VISIBILIDADE = "EXPLICIT";
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



}