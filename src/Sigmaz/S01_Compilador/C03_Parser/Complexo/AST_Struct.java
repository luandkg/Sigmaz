package Sigmaz.S01_Compilador.C03_Parser.Complexo;

import Sigmaz.S01_Compilador.C03_Parser.Alocador.AST_Alocador;
import Sigmaz.S01_Compilador.C03_Parser.Alocador.AST_Def;
import Sigmaz.S01_Compilador.C03_Parser.Bloco.*;
import Sigmaz.S01_Compilador.C03_Parser.Bloco.AST_Acessadores;
import Sigmaz.S01_Compilador.C03_Parser.Fluxo.AST_Corpo;
import Sigmaz.S01_Compilador.C03_Parser.Organizador.AST_Argumentos;
import Sigmaz.S01_Compilador.C03_Parser.Organizador.AST_Generic;
import Sigmaz.S01_Compilador.C03_Parser.Parser;
import Sigmaz.S01_Compilador.C02_Lexer.Token;
import Sigmaz.S01_Compilador.C02_Lexer.TokenTipo;
import Sigmaz.S01_Compilador.Orquestrantes;
import Sigmaz.S08_Utilitarios.AST;

public class AST_Struct {

    private Parser mCompiler;

    public AST_Struct(Parser eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(String eNomePacote, AST ASTPai) {

        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.ID) {

            AST AST_Corrente = new AST(Orquestrantes.COMPLEX);
            AST_Corrente.setNome(TokenC.getConteudo());
            AST_Corrente.setValor("");

            if (eNomePacote.length() == 0) {
                AST_Corrente.setValor(TokenC.getConteudo());
            } else {
                AST_Corrente.setValor(eNomePacote + "<>" + TokenC.getConteudo());
            }

            ASTPai.getASTS().add(AST_Corrente);

            AST AST_Generico = AST_Corrente.criarBranch("GENERIC");
            AST_Generico.setNome("FALSE");

            AST AST_With = AST_Corrente.criarBranch("WITH");
            AST_With.setValor("FALSE");

            AST AST_WithGeneric = AST_With.criarBranch("GENERIC");
            AST_WithGeneric.setValor("FALSE");


            AST AST_Model = AST_Corrente.criarBranch("MODEL");
            AST_Model.setValor("FALSE");

            AST AST_Stages = AST_Corrente.criarBranch("STAGES");
            AST_Stages.setValor("FALSE");

            AST mExtended = AST_Corrente.criarBranch("EXTENDED");
            mExtended.setNome("STRUCT");

            AST mBases = AST_Corrente.criarBranch("BASES");
            AST mModels = AST_Corrente.criarBranch("MODELS");
            AST mRefers = AST_Corrente.criarBranch("REFERS");

            AST mComplement = AST_Corrente.criarBranch("COMPLEMENT");
            mComplement.setNome("NONE");


            AST AST_Inits = AST_Corrente.criarBranch("INITS");

            AST AST_Corpo = AST_Corrente.criarBranch("BODY");

            AST AST_Destruct = AST_Corrente.criarBranch("DESTRUCT");


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

                if (TokenP.getTipo() == TokenTipo.ID) {

                    AST_With.setNome(TokenP.getConteudo());
                    AST_With.setValor("TRUE");

                }

                Token Futuro2 = mCompiler.getTokenFuturo();
                if (Futuro2.getTipo() == TokenTipo.ENVIAR) {

                    AST_WithGeneric.setValor("TRUE");

                    AST_Generic mg = new AST_Generic(mCompiler);
                    mg.init(AST_WithGeneric);


                }

            }

            Token Futuro_AT = mCompiler.getTokenFuturo();
            if (Futuro_AT.getTipo() == TokenTipo.ID && Futuro_AT.mesmoConteudo("at")) {
                mCompiler.Proximo();

                //  System.out.println("vamos comecar a modelar...");

                Token TokenP = mCompiler.getTokenAvante();

                if (TokenP.getTipo() == TokenTipo.ID) {

                    AST_Model.setNome(TokenP.getConteudo());
                    AST_Model.setValor("TRUE");


                    AST eModelo = mModels.criarBranch("MODEL");
                    eModelo.setNome(TokenP.getConteudo());


                    AST AST_Gen = AST_Model.criarBranch("GENERIC");
                    AST_Gen.setNome("FALSE");

                    Token Futuro_AT2 = mCompiler.getTokenFuturo();
                    if (Futuro_AT2.getTipo() == TokenTipo.ENVIAR) {

                        AST_Gen.setNome("TRUE");


                        AST_Generic mg = new AST_Generic(mCompiler);
                        mg.init(AST_Gen);


                    }


                }
            }

            Token Futuro_Extra = mCompiler.getTokenFuturo();

            if (Futuro_Extra.getTipo() == TokenTipo.QUAD) {
                mCompiler.Proximo();

                Token TokenP = mCompiler.getTokenAvante();


                if (TokenP.getTipo() == TokenTipo.ID) {

                    if (TokenP.mesmoConteudo("final")) {

                        mComplement.setNome("FINAL");

                    } else if (TokenP.mesmoConteudo("inhentable")) {

                        mComplement.setNome("INHENTABLE");


                    } else {
                        mCompiler.errarCompilacao("Era esperado FINAL ou INHENTABLE !", TokenP);
                    }

                } else {
                    mCompiler.errarCompilacao("Era esperado FINAL ou INHENTABLE !", TokenP);
                }


            }


            corpo(AST_Corpo, AST_Inits, AST_Destruct, TokenC.getConteudo());


            if (AST_Inits.getASTS().size() == 0) {

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


    public boolean podeTodos(String VISIBILIDADE) {

        boolean ret = false;

        if (VISIBILIDADE.contentEquals("ALL")) {
            ret = true;
        } else if (VISIBILIDADE.contentEquals("RESTRICT")) {
            ret = true;
        } else if (VISIBILIDADE.contentEquals("EXPLICIT")) {
            ret = true;
        } else if (VISIBILIDADE.contentEquals("IMPLICIT")) {
            ret = true;
        } else if (VISIBILIDADE.contentEquals("ALLOW")) {
            ret = true;
        }

        return ret;

    }

    public boolean escopo_ForaARIE(String VISIBILIDADE) {

        boolean ret = true;

        if (VISIBILIDADE.contentEquals("ALL")) {
            ret = false;
        } else if (VISIBILIDADE.contentEquals("RESTRICT")) {
            ret = false;
        } else if (VISIBILIDADE.contentEquals("EXPLICIT")) {
            ret = false;
        } else if (VISIBILIDADE.contentEquals("IMPLICIT")) {
            ret = false;
        } else if (VISIBILIDADE.contentEquals("ALLOW")) {
            ret = false;
        }

        return ret;

    }

    public boolean podeAlgum(String VISIBILIDADE) {

        boolean ret = false;

        if (VISIBILIDADE.contentEquals("ALL")) {
            ret = true;
        } else if (VISIBILIDADE.contentEquals("RESTRICT")) {
            ret = true;
        }

        return ret;

    }


    public boolean podeExtra(String VISIBILIDADE) {

        boolean ret = false;

        if (VISIBILIDADE.contentEquals("EXTRA")) {
            ret = true;
        }

        return ret;

    }

    public boolean podeAll(String VISIBILIDADE) {

        boolean ret = false;

        if (VISIBILIDADE.contentEquals("ALL")) {
            ret = true;
        }

        return ret;

    }

    public void corpo(AST AST_Corpo, AST AST_Inits, AST AST_Destruct, String NomeStruct) {


        String VISIBILIDADE = "INIT";


        Token TokenD = mCompiler.getTokenAvanteStatus(TokenTipo.CHAVE_ABRE, "Era esperado abrir chaves");

        if (TokenD.getTipo() != TokenTipo.CHAVE_ABRE) {
            return;
        }


        int mInicializadores = 0;
        int mDestructs = 0;


        boolean saiu = false;

        while (mCompiler.Continuar()) {
            Token TokenC = mCompiler.getTokenAvante();
            if (TokenC.getTipo() == TokenTipo.CHAVE_FECHA) {
                saiu = true;
                break;

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("init")) {

                verificar_EscopoInit(VISIBILIDADE, TokenC);

                inicializador(AST_Inits, NomeStruct, VISIBILIDADE);

                mInicializadores += 1;


            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("act")) {

                verificar_EscopoComum("Actions", VISIBILIDADE, TokenC);

                AST_Action mAST = new AST_Action(mCompiler);
                mAST.init(AST_Corpo, VISIBILIDADE);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("func")) {

                verificar_EscopoComum("Functions", VISIBILIDADE, TokenC);

                AST_Function mAST = new AST_Function(mCompiler);
                mAST.init(AST_Corpo, VISIBILIDADE);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("block")) {

                verificar_EscopoBloco(VISIBILIDADE, TokenC);

                AST_Acessadores mAST = new AST_Acessadores(mCompiler);
                mAST.init(AST_Corpo, VISIBILIDADE);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("mockiz")) {

                verificar_EscopoComum("Mockizes", VISIBILIDADE, TokenC);

                AST_Alocador mAST = new AST_Alocador(mCompiler);
                mAST.init("MOCKIZ", AST_Corpo, VISIBILIDADE);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("define")) {

                verificar_EscopoComum("Defines", VISIBILIDADE, TokenC);

                AST_Def mAST = new AST_Def(mCompiler);
                mAST.init_Define(AST_Corpo, VISIBILIDADE);


            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("operator")) {

                verificar_EscopoExtra("Operadores", VISIBILIDADE, TokenC);

                AST_Operator mAST = new AST_Operator(mCompiler);
                mAST.init(AST_Corpo);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("director")) {

                verificar_EscopoExtra("Diretores", VISIBILIDADE, TokenC);

                AST_Director mAST = new AST_Director(mCompiler);
                mAST.init(AST_Corpo);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("destruct")) {

                verificar_EscopoDestrutor(VISIBILIDADE, TokenC);


                AST_Destruct mAST = new AST_Destruct(mCompiler);
                mAST.init(AST_Destruct);

                mDestructs += 1;

                if (mDestructs > 1) {
                    mCompiler.errarCompilacao("A Struct " + NomeStruct + " so pode ter um DESTRUCT !", TokenC);
                }

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("all")) {

                VISIBILIDADE = mudarEscopo("ALL");

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("explicit")) {

                VISIBILIDADE = mudarEscopo("EXPLICIT");

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("implicit")) {

                VISIBILIDADE = mudarEscopo("IMPLICIT");

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("restrict")) {

                VISIBILIDADE = mudarEscopo("RESTRICT");

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("allow")) {

                VISIBILIDADE = mudarEscopo("ALLOW");

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("extra")) {

                VISIBILIDADE = mudarEscopo("EXTRA");


            } else {


                mCompiler.errarCompilacao("Comando Deconhecido : " + TokenC.getConteudo(), TokenC);

            }
        }

        if (mInicializadores == 0) {


            AST eInit = AST_Inits.criarBranch("INIT");
            eInit.setNome(NomeStruct);

            eInit.criarBranch("ARGUMENTS");
            eInit.criarBranch("BODY");
            eInit.criarBranch("VISIBILITY").setNome("INIT");
            eInit.criarBranch("CALL").setValor("FALSE");

        }


        if (!saiu) {
            mCompiler.errarCompilacao("Era esperado fechar chaves" + mCompiler.getTokenAvante().getConteudo(), mCompiler.getTokenAvante());
        }


    }

    public void inicializador(AST ASTPai, String NomeStruct, String Visibilidade) {

        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.ID) {

            AST AST_Corrente = new AST("INIT");
            AST_Corrente.setNome(TokenC.getConteudo());
            ASTPai.getASTS().add(AST_Corrente);


            AST AST_Visibilidade = AST_Corrente.criarBranch("VISIBILITY");
            AST_Visibilidade.setNome(Visibilidade);


            AST AST_Call = AST_Corrente.criarBranch("CALL");
            AST_Call.setValor("FALSE");

            if (!TokenC.mesmoConteudo(NomeStruct)) {
                mCompiler.errarCompilacao("O nome do INIT deve ser igual ao nome da Struct : " + NomeStruct + " -> INIT " + TokenC.getConteudo(), TokenC);
            }

            AST AST_Arguments = AST_Corrente.criarBranch("ARGUMENTS");
            AST AST_BODY = AST_Corrente.criarBranch("BODY");

            AST_Argumentos mArgumentos = new AST_Argumentos(mCompiler);
            mArgumentos.init(AST_Arguments);

            Token Futuro = mCompiler.getTokenFuturo();
            if (Futuro.getTipo() == TokenTipo.SETA) {
                mCompiler.Proximo();


                Token TokenP = mCompiler.getTokenAvante();
                if (TokenP.getTipo() == TokenTipo.ID) {

                    AST_Call.setNome(TokenP.getConteudo());
                    AST_Call.setValor("TRUE");

                } else {
                    mCompiler.errarCompilacao("Era esperado o nome do INIT da Struct Pai ", TokenP);
                }


                Token TokenI = mCompiler.getTokenAvante();
                if (TokenI.getTipo() == TokenTipo.PARENTESES_ABRE) {


                } else {
                    mCompiler.errarCompilacao("Era esperado abrir parenteses", TokenI);
                }

                AST_Recebimentos mRA = new AST_Recebimentos(mCompiler);
                mRA.init(AST_Call.criarBranch("ARGUMENTS"));


            }


            AST_Corpo mCorpo = new AST_Corpo(mCompiler);
            mCorpo.init(AST_BODY);


        } else {
            mCompiler.errarCompilacao("Era esperado o nome para uma INIT !", TokenC);
        }


    }


    public String mudarEscopo(String eEscopo) {

        Token TokenC2 = mCompiler.getTokenAvanteStatus(TokenTipo.DOISPONTOS, "Era esperado :");

        return eEscopo;

    }

    public void verificar_EscopoInit(String VISIBILIDADE, Token TokenC) {
        if (VISIBILIDADE.contentEquals("INIT")) {

        } else {
            mCompiler.errarCompilacao("Inicializadores so podem ser declarados fora de Escopos", TokenC);
        }
    }

    public void verificar_EscopoDestrutor(String VISIBILIDADE, Token TokenC) {

        if (!podeAll(VISIBILIDADE)) {
            mCompiler.errarCompilacao("Destrutores so podem existir no escopo : ALL", TokenC);
        }

    }

    public void verificar_EscopoComum(String eNome, String VISIBILIDADE, Token TokenC) {

        if (VISIBILIDADE.contentEquals("INIT")) {
            mCompiler.errarCompilacao(eNome + " precisam ter um escopo : ALL, RESTRICT, IMPLICIT ou EXPLICIT", TokenC);
        } else {
            if (escopo_ForaARIE(VISIBILIDADE)) {
                mCompiler.errarCompilacao(eNome + " so podem existir nos escopos : ALL, RESTRICT, IMPLICIT ou EXPLICIT", TokenC);
            }
        }

    }

    public void verificar_EscopoBloco(String VISIBILIDADE, Token TokenC) {

        if (VISIBILIDADE.contentEquals("INIT")) {
            mCompiler.errarCompilacao("Blocos precisam ter um escopo : ALL", TokenC);
        } else {
            if (!VISIBILIDADE.contentEquals("ALL")) {
                mCompiler.errarCompilacao("Blocos so podem existir no escopo : ALL", TokenC);
            }
        }

    }

    public void verificar_EscopoExtra(String eNome, String VISIBILIDADE, Token TokenC) {

        if (!podeExtra(VISIBILIDADE)) {
            mCompiler.errarCompilacao(eNome + " so podem existir no escopo : EXTRA", TokenC);
        }

    }

}