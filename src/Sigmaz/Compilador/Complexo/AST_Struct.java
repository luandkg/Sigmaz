package Sigmaz.Compilador.Complexo;

import Sigmaz.Compilador.Organizador.AST_Generic;
import Sigmaz.Compilador.CompilerUnit;
import Sigmaz.Lexer.Token;
import Sigmaz.Lexer.TokenTipo;
import Sigmaz.Utils.AST;

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

            AST_StructCorpo mAST = new AST_StructCorpo(mCompiler);
            mAST.init(AST_Corrente,AST_Inits,TokenC.getConteudo());


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


}