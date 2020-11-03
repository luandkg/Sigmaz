package Sigmaz.S00_Utilitarios;

import Sigmaz.S01_Compilador.C02_Lexer.Lexer;
import Sigmaz.S01_Compilador.C02_Lexer.Token;
import Sigmaz.S01_Compilador.C02_Lexer.TokenTipo;

import java.util.ArrayList;

public class Formatador2 {


    private Documento eDocumento;

    private int mQuantidade;
    private int mIndex;
    private ArrayList<Token> mTokens;

    public Formatador2() {

        mQuantidade = 0;
        mIndex = 0;
        eDocumento = new Documento();
        mTokens = new ArrayList<Token>();
    }


    public void identar(String eArquivoOrigem, String eArquivoDestino) {


        Lexer LexerC = new Lexer();
        LexerC.init(eArquivoOrigem);
        mTokens = LexerC.getTokens();
        mQuantidade = mTokens.size();
        mIndex = 0;

        eDocumento = new Documento();

        int mTab = 0;


        while (mIndex < mQuantidade) {
            Token TokenC = LexerC.getTokens().get(mIndex);

            if (TokenC.getTipo() == TokenTipo.COMENTARIO_BLOCO) {

                eDocumento.adicionarLinha(mTab, TokenC.getConteudo());


            } else if (TokenC.getTipo() == TokenTipo.COMENTARIO_LINHA) {

                eDocumento.adicionarLinha(mTab, TokenC.getConteudo());


            } else if (TokenC.getTipo() == TokenTipo.TEXTO) {

                eDocumento.adicionarLinha(mTab, getTexto(TokenC));

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("import")) {


                formate_Linha(mTab);
            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("require")) {


                formate_Linha(mTab);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo_SemCS("DEBUG")) {


                formate_Linha(mTab);


            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("call")) {


                formate_Call(mTab);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("define")) {


                formate_Linha(mTab);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("mockiz")) {


                formate_Linha(mTab);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("refer")) {


                formate_Linha(mTab);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("act")) {


                formate_AlgoComBloco(mTab);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("func")) {


                formate_AlgoComBloco(mTab);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("auto")) {


                formate_AlgoComBloco(mTab);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("functor")) {


                formate_AlgoComBloco(mTab);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("operator")) {


                formate_AlgoComBloco(mTab);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("director")) {

                formate_AlgoComBloco(mTab);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("default")) {


                formate_AlgoComBloco(mTab);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("cast")) {


                formate_Cast(mTab);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("stages")) {

                formate_Stages(mTab);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("type")) {


                formate_Type(mTab);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("model")) {


                formate_Model(mTab);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("external")) {


                formate_External(mTab);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("struct")) {


                formate_Struct(mTab);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("package")) {


                formate_Package(mTab);

            } else {

                eDocumento.adicionarLinha(mTab, TokenC.getConteudo());


            }

            mIndex += 1;
        }


        Texto.Escrever(eArquivoDestino, eDocumento.getConteudo());


        System.out.println(eDocumento.getConteudo());


    }


    public void formate_Call(int mTab) {

        eDocumento.pularLinhaTabulada(mTab);


        String eLinha = "";


        boolean isPontoEVirgula = false;
        boolean isBloco = false;


        while (mIndex < mQuantidade) {
            Token TokenC = mTokens.get(mIndex);

            if (TokenC.getTipo() == TokenTipo.COMENTARIO_BLOCO) {

                eLinha += "\n" + TokenC.getConteudo() + "\n";

            } else if (TokenC.getTipo() == TokenTipo.COMENTARIO_LINHA) {

                eLinha += " " + TokenC.getConteudo() + "\n";


            } else if (TokenC.getTipo() == TokenTipo.TEXTO) {

                eLinha += getTexto(TokenC) + " ";


            } else if (TokenC.getTipo() == TokenTipo.CHAVE_ABRE) {


                eLinha += " " + TokenC.getConteudo();
                isBloco = true;

                break;

            } else if (TokenC.getTipo() == TokenTipo.PONTOVIRGULA) {

                eLinha += " " + TokenC.getConteudo();
                isPontoEVirgula = true;

                break;

            } else {

                eLinha += TokenC.getConteudo() + " ";


            }

            mIndex += 1;

        }


        eDocumento.adicionarLinha(mTab, eLinha);

        if (isPontoEVirgula) {
            return;
        }


        if (isBloco) {
            formate_bloco(mTab);
        }

    }

    public void formate_AlgoComBloco(int mTab) {

        eDocumento.pularLinhaTabulada(mTab);


        String eLinha = "";


        boolean isBloco = false;


        while (mIndex < mQuantidade) {
            Token TokenC = mTokens.get(mIndex);

            if (TokenC.getTipo() == TokenTipo.COMENTARIO_BLOCO) {

                eLinha += "\n" + TokenC.getConteudo() + "\n";

            } else if (TokenC.getTipo() == TokenTipo.COMENTARIO_LINHA) {

                eLinha += " " + TokenC.getConteudo() + "\n";


            } else if (TokenC.getTipo() == TokenTipo.TEXTO) {

                eLinha += getTexto(TokenC) + " ";


            } else if (TokenC.getTipo() == TokenTipo.CHAVE_ABRE) {


                eLinha += " " + TokenC.getConteudo();
                isBloco = true;

                break;

            } else {

                eLinha += TokenC.getConteudo() + " ";


            }

            mIndex += 1;

        }


        eDocumento.adicionarLinha(mTab, eLinha);


        if (isBloco) {
            formate_bloco(mTab);
        }

    }

    public void formate_BlocoIf(int mTab) {

        eDocumento.pularLinhaTabulada(mTab);


        String eLinha = "";


        boolean isBloco = false;


        while (mIndex < mQuantidade) {
            Token TokenC = mTokens.get(mIndex);

            if (TokenC.getTipo() == TokenTipo.COMENTARIO_BLOCO) {

                eLinha += "\n" + TokenC.getConteudo() + "\n";

            } else if (TokenC.getTipo() == TokenTipo.COMENTARIO_LINHA) {

                eLinha += " " + TokenC.getConteudo() + "\n";


            } else if (TokenC.getTipo() == TokenTipo.TEXTO) {

                eLinha += getTexto(TokenC) + " ";


            } else if (TokenC.getTipo() == TokenTipo.CHAVE_ABRE) {


                eLinha += " " + TokenC.getConteudo();
                isBloco = true;

                break;

            } else {

                eLinha += TokenC.getConteudo() + " ";


            }

            mIndex += 1;

        }


        eDocumento.adicionarLinha(mTab, eLinha);


        if (isBloco) {
            formate_dentroIf(mTab);
        }

    }

    public void formate_BlocoIfOthers(int mTab) {


        String eLinha = "";


        boolean isBloco = false;


        while (mIndex < mQuantidade) {
            Token TokenC = mTokens.get(mIndex);

            if (TokenC.getTipo() == TokenTipo.COMENTARIO_BLOCO) {

                eLinha += "\n" + TokenC.getConteudo() + "\n";

            } else if (TokenC.getTipo() == TokenTipo.COMENTARIO_LINHA) {

                eLinha += " " + TokenC.getConteudo() + "\n";


            } else if (TokenC.getTipo() == TokenTipo.TEXTO) {

                eLinha += getTexto(TokenC) + " ";


            } else if (TokenC.getTipo() == TokenTipo.CHAVE_ABRE) {


                eLinha += " " + TokenC.getConteudo();
                isBloco = true;

                break;

            } else {

                eLinha += TokenC.getConteudo() + " ";


            }

            mIndex += 1;

        }


        eDocumento.adicionar(eLinha);


        if (isBloco) {
            formate_dentroIf(mTab);
        }

    }

    public void formate_dentroIf(int mTab) {


        int mDentro = mTab + 1;


        mIndex += 1;


        while (mIndex < mQuantidade) {
            Token TokenC = mTokens.get(mIndex);

            if (TokenC.getTipo() == TokenTipo.COMENTARIO_BLOCO) {

                eDocumento.adicionarLinha(mDentro, TokenC.getConteudo());

            } else if (TokenC.getTipo() == TokenTipo.COMENTARIO_LINHA) {

                eDocumento.adicionarLinha(mDentro, TokenC.getConteudo());


            } else if (TokenC.getTipo() == TokenTipo.TEXTO) {

                eDocumento.adicionarLinha(mDentro, getTexto(TokenC) + " ");


            } else if (TokenC.getTipo() == TokenTipo.CHAVE_ABRE) {


                eDocumento.adicionarLinha(mDentro, TokenC.getConteudo());

                formate_bloco(mDentro);


            } else if (TokenC.getTipo() == TokenTipo.CHAVE_FECHA) {


                if (isFuturoID("other")) {

                    eDocumento.pularLinha();
                    eDocumento.adicionarTab(mTab);
                    eDocumento.adicionar("} ");

                    mIndex += 1;

                    formate_BlocoIfOthers(mTab);


                    break;
                } else if (isFuturoID("others")) {

                    eDocumento.pularLinha();
                    eDocumento.adicionarTab(mTab);
                    eDocumento.adicionar("} ");

                    mIndex += 1;

                    formate_BlocoIfOthers(mTab);

                    break;
                } else {

                    eDocumento.adicionarLinha(mTab, TokenC.getConteudo());
                    break;

                }


            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo_SemCS("PROC")) {

                formate_AlgoComBloco(mDentro);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("if")) {

                formate_BlocoIf(mDentro);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("while")) {

                formate_AlgoComBloco(mDentro);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("daz")) {

                formate_AlgoComBloco(mDentro);


            } else {

                formate_Linha(mDentro);


            }

            mIndex += 1;

        }


    }


    public void formate_Cast(int mTab) {


        String eLinha = "";


        boolean isBloco = false;


        while (mIndex < mQuantidade) {
            Token TokenC = mTokens.get(mIndex);

            if (TokenC.getTipo() == TokenTipo.COMENTARIO_BLOCO) {

                eLinha += "\n" + TokenC.getConteudo() + "\n";

            } else if (TokenC.getTipo() == TokenTipo.COMENTARIO_LINHA) {

                eLinha += " " + TokenC.getConteudo() + "\n";


            } else if (TokenC.getTipo() == TokenTipo.TEXTO) {

                eLinha += getTexto(TokenC) + " ";


            } else if (TokenC.getTipo() == TokenTipo.CHAVE_ABRE) {


                eLinha += " " + TokenC.getConteudo();
                isBloco = true;

                break;

            } else {

                eLinha += TokenC.getConteudo() + " ";


            }

            mIndex += 1;

        }


        eDocumento.adicionarLinha(mTab, eLinha);


        if (isBloco) {


            int mDentro = mTab + 1;


            mIndex += 1;


            while (mIndex < mQuantidade) {
                Token TokenC = mTokens.get(mIndex);

                if (TokenC.getTipo() == TokenTipo.COMENTARIO_BLOCO) {

                    eDocumento.adicionarLinha(mDentro, TokenC.getConteudo());

                } else if (TokenC.getTipo() == TokenTipo.COMENTARIO_LINHA) {

                    eDocumento.adicionarLinha(mDentro, TokenC.getConteudo());


                } else if (TokenC.getTipo() == TokenTipo.TEXTO) {

                    eDocumento.adicionarLinha(mDentro, getTexto(TokenC) + " ");


                } else if (TokenC.getTipo() == TokenTipo.CHAVE_ABRE) {


                    eDocumento.adicionarLinha(mDentro, TokenC.getConteudo());

                    formate_bloco(mDentro);


                } else if (TokenC.getTipo() == TokenTipo.CHAVE_FECHA) {

                    eDocumento.adicionarLinha(mTab, TokenC.getConteudo());

                    break;

                } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("getter")) {

                    formate_AlgoComBloco(mDentro);

                } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("setter")) {

                    formate_AlgoComBloco(mDentro);

                } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("default")) {

                    formate_AlgoComBloco(mDentro);


                } else {

                    formate_Linha(mDentro);


                }

                mIndex += 1;

            }


        }

    }

    public void formate_Type(int mTab) {


        String eLinha = "";


        boolean isBloco = false;


        while (mIndex < mQuantidade) {
            Token TokenC = mTokens.get(mIndex);

            if (TokenC.getTipo() == TokenTipo.COMENTARIO_BLOCO) {

                eLinha += "\n" + TokenC.getConteudo() + "\n";

            } else if (TokenC.getTipo() == TokenTipo.COMENTARIO_LINHA) {

                eLinha += " " + TokenC.getConteudo() + "\n";


            } else if (TokenC.getTipo() == TokenTipo.TEXTO) {

                eLinha += getTexto(TokenC) + " ";


            } else if (TokenC.getTipo() == TokenTipo.CHAVE_ABRE) {


                eLinha += " " + TokenC.getConteudo();
                isBloco = true;

                break;

            } else {

                eLinha += TokenC.getConteudo() + " ";


            }

            mIndex += 1;

        }


        eDocumento.adicionarLinha(mTab, eLinha);


        if (isBloco) {


            int mDentro = mTab + 1;


            mIndex += 1;


            while (mIndex < mQuantidade) {
                Token TokenC = mTokens.get(mIndex);

                if (TokenC.getTipo() == TokenTipo.COMENTARIO_BLOCO) {

                    eDocumento.adicionarLinha(mDentro, TokenC.getConteudo());

                } else if (TokenC.getTipo() == TokenTipo.COMENTARIO_LINHA) {

                    eDocumento.adicionarLinha(mDentro, TokenC.getConteudo());


                } else if (TokenC.getTipo() == TokenTipo.TEXTO) {

                    eDocumento.adicionarLinha(mDentro, getTexto(TokenC) + " ");


                } else if (TokenC.getTipo() == TokenTipo.CHAVE_ABRE) {


                    eDocumento.adicionarLinha(mDentro, TokenC.getConteudo());

                    formate_bloco(mDentro);


                } else if (TokenC.getTipo() == TokenTipo.CHAVE_FECHA) {

                    eDocumento.adicionarLinha(mTab, TokenC.getConteudo());

                    break;

                } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("define")) {

                    formate_Linha(mDentro);

                } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("mockiz")) {

                    formate_Linha(mDentro);

                } else {

                    formate_Linha(mDentro);

                }

                mIndex += 1;

            }


        }

    }

    public void formate_Model(int mTab) {


        String eLinha = "";


        boolean isBloco = false;


        while (mIndex < mQuantidade) {
            Token TokenC = mTokens.get(mIndex);

            if (TokenC.getTipo() == TokenTipo.COMENTARIO_BLOCO) {

                eLinha += "\n" + TokenC.getConteudo() + "\n";

            } else if (TokenC.getTipo() == TokenTipo.COMENTARIO_LINHA) {

                eLinha += " " + TokenC.getConteudo() + "\n";


            } else if (TokenC.getTipo() == TokenTipo.TEXTO) {

                eLinha += getTexto(TokenC) + " ";


            } else if (TokenC.getTipo() == TokenTipo.CHAVE_ABRE) {


                eLinha += " " + TokenC.getConteudo();
                isBloco = true;

                break;

            } else {

                eLinha += TokenC.getConteudo() + " ";


            }

            mIndex += 1;

        }


        eDocumento.adicionarLinha(mTab, eLinha);


        if (isBloco) {


            int mDentro = mTab + 1;


            mIndex += 1;


            while (mIndex < mQuantidade) {
                Token TokenC = mTokens.get(mIndex);

                if (TokenC.getTipo() == TokenTipo.COMENTARIO_BLOCO) {

                    eDocumento.adicionarLinha(mDentro, TokenC.getConteudo());

                } else if (TokenC.getTipo() == TokenTipo.COMENTARIO_LINHA) {

                    eDocumento.adicionarLinha(mDentro, TokenC.getConteudo());


                } else if (TokenC.getTipo() == TokenTipo.TEXTO) {

                    eDocumento.adicionarLinha(mDentro, getTexto(TokenC) + " ");


                } else if (TokenC.getTipo() == TokenTipo.CHAVE_ABRE) {


                    eDocumento.adicionarLinha(mDentro, TokenC.getConteudo());

                    formate_bloco(mDentro);


                } else if (TokenC.getTipo() == TokenTipo.CHAVE_FECHA) {

                    eDocumento.adicionarLinha(mTab, TokenC.getConteudo());

                    break;

                } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("define")) {

                    formate_Linha(mDentro);

                } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("mockiz")) {

                    formate_Linha(mDentro);

                } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("act")) {

                    formate_Linha(mDentro);

                } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("func")) {

                    formate_Linha(mDentro);

                } else {

                    formate_Linha(mDentro);

                }

                mIndex += 1;

            }


        }

    }

    public void formate_External(int mTab) {


        String eLinha = "";


        boolean isBloco = false;


        while (mIndex < mQuantidade) {
            Token TokenC = mTokens.get(mIndex);

            if (TokenC.getTipo() == TokenTipo.COMENTARIO_BLOCO) {

                eLinha += "\n" + TokenC.getConteudo() + "\n";

            } else if (TokenC.getTipo() == TokenTipo.COMENTARIO_LINHA) {

                eLinha += " " + TokenC.getConteudo() + "\n";


            } else if (TokenC.getTipo() == TokenTipo.TEXTO) {

                eLinha += getTexto(TokenC) + " ";


            } else if (TokenC.getTipo() == TokenTipo.CHAVE_ABRE) {


                eLinha += " " + TokenC.getConteudo();
                isBloco = true;

                break;

            } else {

                eLinha += TokenC.getConteudo() + " ";


            }

            mIndex += 1;

        }


        eDocumento.adicionarLinha(mTab, eLinha);


        if (isBloco) {


            int mDentro = mTab + 1;


            mIndex += 1;


            while (mIndex < mQuantidade) {
                Token TokenC = mTokens.get(mIndex);

                if (TokenC.getTipo() == TokenTipo.COMENTARIO_BLOCO) {

                    eDocumento.adicionarLinha(mDentro, TokenC.getConteudo());

                } else if (TokenC.getTipo() == TokenTipo.COMENTARIO_LINHA) {

                    eDocumento.adicionarLinha(mDentro, TokenC.getConteudo());


                } else if (TokenC.getTipo() == TokenTipo.TEXTO) {

                    eDocumento.adicionarLinha(mDentro, getTexto(TokenC) + " ");


                } else if (TokenC.getTipo() == TokenTipo.CHAVE_ABRE) {


                    eDocumento.adicionarLinha(mDentro, TokenC.getConteudo());

                    formate_bloco(mDentro);


                } else if (TokenC.getTipo() == TokenTipo.CHAVE_FECHA) {

                    eDocumento.adicionarLinha(mTab, TokenC.getConteudo());

                    break;

                } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("define")) {

                    formate_Linha(mDentro);

                } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("mockiz")) {

                    formate_Linha(mDentro);

                } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("act")) {

                    formate_AlgoComBloco(mDentro);

                } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("func")) {

                    formate_AlgoComBloco(mDentro);

                } else {

                    formate_Linha(mDentro);

                }

                mIndex += 1;

            }


        }

    }


    public void formate_Struct(int mTab) {


        String eLinha = "";


        boolean isBloco = false;


        while (mIndex < mQuantidade) {
            Token TokenC = mTokens.get(mIndex);

            if (TokenC.getTipo() == TokenTipo.COMENTARIO_BLOCO) {

                eLinha += "\n" + TokenC.getConteudo() + "\n";

            } else if (TokenC.getTipo() == TokenTipo.COMENTARIO_LINHA) {

                eLinha += " " + TokenC.getConteudo() + "\n";


            } else if (TokenC.getTipo() == TokenTipo.TEXTO) {

                eLinha += getTexto(TokenC) + " ";


            } else if (TokenC.getTipo() == TokenTipo.CHAVE_ABRE) {


                eLinha += " " + TokenC.getConteudo();
                isBloco = true;

                break;

            } else {

                eLinha += TokenC.getConteudo() + " ";


            }

            mIndex += 1;

        }


        eDocumento.adicionarLinha(mTab, eLinha);


        if (isBloco) {


            int mDentro = mTab + 1;


            mIndex += 1;


            while (mIndex < mQuantidade) {
                Token TokenC = mTokens.get(mIndex);

                if (TokenC.getTipo() == TokenTipo.COMENTARIO_BLOCO) {

                    eDocumento.adicionarLinha(mDentro, TokenC.getConteudo());

                } else if (TokenC.getTipo() == TokenTipo.COMENTARIO_LINHA) {

                    eDocumento.adicionarLinha(mDentro, TokenC.getConteudo());


                } else if (TokenC.getTipo() == TokenTipo.TEXTO) {

                    eDocumento.adicionarLinha(mDentro, getTexto(TokenC) + " ");


                } else if (TokenC.getTipo() == TokenTipo.CHAVE_ABRE) {


                    eDocumento.adicionarLinha(mDentro, TokenC.getConteudo());

                    formate_bloco(mDentro);


                } else if (TokenC.getTipo() == TokenTipo.CHAVE_FECHA) {

                    //eDocumento.adicionarLinha("## SAINDO STRUCT");

                    eDocumento.adicionarLinha(mTab, TokenC.getConteudo());


                    break;

                } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("init")) {

                    formate_AlgoComBloco(mDentro);

                } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("destruct")) {

                    formate_AlgoComBloco(mDentro);

                } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("define")) {

                    formate_Linha(mDentro);

                } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("mockiz")) {

                    formate_Linha(mDentro);

                } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("all")) {

                    formate_StructSub(mDentro);

                } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("restrict")) {

                    formate_StructSub(mDentro);
                } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("extra")) {

                    formate_StructSub(mDentro);
                } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("block")) {

                    formate_StructSub(mDentro);
                } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("explicit")) {

                    formate_StructSub(mDentro);
                } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("implicit")) {

                    formate_StructSub(mDentro);

                } else {

                    formate_Linha(mDentro);

                }

                mIndex += 1;

            }


        }

    }

    public void formate_Stages(int mTab) {

        eDocumento.pularLinhaTabulada(mTab);


        String eLinha = "";


        boolean isBloco = false;


        while (mIndex < mQuantidade) {
            Token TokenC = mTokens.get(mIndex);

            if (TokenC.getTipo() == TokenTipo.COMENTARIO_BLOCO) {

                eLinha += "\n" + TokenC.getConteudo() + "\n";

            } else if (TokenC.getTipo() == TokenTipo.COMENTARIO_LINHA) {

                eLinha += " " + TokenC.getConteudo() + "\n";


            } else if (TokenC.getTipo() == TokenTipo.TEXTO) {

                eLinha += getTexto(TokenC) + " ";


            } else if (TokenC.getTipo() == TokenTipo.CHAVE_ABRE) {


                eLinha += " " + TokenC.getConteudo();
                isBloco = true;

                break;

            } else {

                eLinha += TokenC.getConteudo() + " ";


            }

            mIndex += 1;

        }


        eDocumento.adicionarLinha(mTab, eLinha);

        // eDocumento.adicionar("{@@@}");

        if (isBloco) {

            mIndex += 1;

            while (mIndex < mQuantidade) {
                Token TokenC = mTokens.get(mIndex);

                if (TokenC.getTipo() == TokenTipo.COMENTARIO_BLOCO) {

                    eDocumento.adicionarLinha(TokenC.getConteudo());

                } else if (TokenC.getTipo() == TokenTipo.COMENTARIO_LINHA) {

                    eDocumento.adicionarLinha(TokenC.getConteudo());


                } else if (TokenC.getTipo() == TokenTipo.TEXTO) {

                    eDocumento.adicionarLinha(getTexto(TokenC));


                } else if (TokenC.getTipo() == TokenTipo.CHAVE_FECHA) {


                    eDocumento.adicionar(" " + TokenC.getConteudo());
                    eDocumento.pularLinha();

                    break;

                } else {

                    eDocumento.adicionar(TokenC.getConteudo() + " ");


                }

                mIndex += 1;

            }

        }

    }


    public void formate_Package(int mTab) {


        String eLinha = "";


        boolean isBloco = false;


        while (mIndex < mQuantidade) {
            Token TokenC = mTokens.get(mIndex);

            if (TokenC.getTipo() == TokenTipo.COMENTARIO_BLOCO) {

                eLinha += "\n" + TokenC.getConteudo() + "\n";

            } else if (TokenC.getTipo() == TokenTipo.COMENTARIO_LINHA) {

                eLinha += " " + TokenC.getConteudo() + "\n";


            } else if (TokenC.getTipo() == TokenTipo.TEXTO) {

                eLinha += getTexto(TokenC) + " ";


            } else if (TokenC.getTipo() == TokenTipo.CHAVE_ABRE) {


                eLinha += " " + TokenC.getConteudo();
                isBloco = true;

                break;

            } else {

                eLinha += TokenC.getConteudo() + " ";


            }

            mIndex += 1;

        }


        eDocumento.adicionarLinha(mTab, eLinha);


        if (isBloco) {


            int mDentro = mTab + 1;


            mIndex += 1;


            while (mIndex < mQuantidade) {
                Token TokenC = mTokens.get(mIndex);

                if (TokenC.getTipo() == TokenTipo.COMENTARIO_BLOCO) {

                    eDocumento.adicionarLinha(mDentro, TokenC.getConteudo());

                } else if (TokenC.getTipo() == TokenTipo.COMENTARIO_LINHA) {

                    eDocumento.adicionarLinha(mDentro, TokenC.getConteudo());


                } else if (TokenC.getTipo() == TokenTipo.TEXTO) {

                    eDocumento.adicionarLinha(mDentro, getTexto(TokenC) + " ");


                } else if (TokenC.getTipo() == TokenTipo.CHAVE_FECHA) {

                    eDocumento.adicionarLinha(mTab, TokenC.getConteudo());

                    break;

                } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("refer")) {

                    formate_Linha(mDentro);

                } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("define")) {

                    formate_Linha(mDentro);

                } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("mockiz")) {

                    formate_Linha(mDentro);

                } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("default")) {

                    formate_AlgoComBloco(mDentro);
                } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("act")) {

                    formate_AlgoComBloco(mDentro);
                } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("func")) {

                    formate_AlgoComBloco(mDentro);
                } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("auto")) {

                    formate_AlgoComBloco(mDentro);
                } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("functor")) {

                    formate_AlgoComBloco(mDentro);
                } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("type")) {

                    formate_Type(mDentro);
                } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("struct")) {

                    formate_Struct(mDentro);
                } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("cast")) {

                    formate_Cast(mDentro);

                } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("stages")) {

                    formate_Stages(mDentro);

                } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("model")) {


                    formate_Model(mDentro);

                } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("external")) {


                    formate_External(mDentro);


                } else {

                    formate_Linha(mDentro);

                }

                mIndex += 1;

            }


        }

    }

    public void formate_bloco(int mTab) {


        int mDentro = mTab + 1;


        mIndex += 1;


        while (mIndex < mQuantidade) {
            Token TokenC = mTokens.get(mIndex);

            if (TokenC.getTipo() == TokenTipo.COMENTARIO_BLOCO) {

                eDocumento.adicionarLinha(mDentro, TokenC.getConteudo());

            } else if (TokenC.getTipo() == TokenTipo.COMENTARIO_LINHA) {

                eDocumento.adicionarLinha(mDentro, TokenC.getConteudo());


            } else if (TokenC.getTipo() == TokenTipo.TEXTO) {

                eDocumento.adicionarLinha(mDentro, getTexto(TokenC) + " ");


            } else if (TokenC.getTipo() == TokenTipo.CHAVE_ABRE) {


                eDocumento.adicionarLinha(mDentro, TokenC.getConteudo());

                formate_bloco(mDentro);
                eDocumento.pularLinha();


            } else if (TokenC.getTipo() == TokenTipo.CHAVE_FECHA) {

                eDocumento.adicionarLinha(mTab, TokenC.getConteudo());

                break;

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo_SemCS("PROC")) {

                formate_AlgoComBloco(mDentro);
                eDocumento.pularLinhaTabulada(mTab);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("if")) {

                formate_BlocoIf(mDentro);

                eDocumento.pularLinhaTabulada(mTab);


            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("while")) {

                formate_AlgoComBloco(mDentro);
                eDocumento.pularLinhaTabulada(mTab);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("daz")) {

                formate_AlgoComBloco(mDentro);
                eDocumento.pularLinhaTabulada(mTab);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("local")) {

                formate_AlgoComBloco(mDentro);
                eDocumento.pularLinhaTabulada(mTab);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("each")) {

                formate_AlgoComBloco(mDentro);
                eDocumento.pularLinhaTabulada(mTab);


            } else {

                formate_Linha(mDentro);


            }

            mIndex += 1;

        }


    }


    public void formate_Linha(int mTab) {

        eDocumento.pularLinhaTabulada(mTab);

        while (mIndex < mQuantidade) {
            Token TokenC = mTokens.get(mIndex);

            if (TokenC.getTipo() == TokenTipo.COMENTARIO_BLOCO) {

                eDocumento.adicionarLinha(mTab, TokenC.getConteudo());

            } else if (TokenC.getTipo() == TokenTipo.COMENTARIO_LINHA) {

                eDocumento.adicionarLinha(mTab, TokenC.getConteudo());


            } else if (TokenC.getTipo() == TokenTipo.TEXTO) {

                eDocumento.adicionar(getTexto(TokenC));

            } else if (TokenC.getTipo() == TokenTipo.CHAVE_ABRE) {


                eDocumento.adicionarLinha(mTab + 1, TokenC.getConteudo());

                formate_blocoLinear(mTab + 1);

            } else if (TokenC.getTipo() == TokenTipo.CHAVE_FECHA) {

                eDocumento.adicionar(" " + TokenC.getConteudo());
                break;

            } else if (TokenC.getTipo() == TokenTipo.PONTOVIRGULA) {

                eDocumento.adicionar(" " + TokenC.getConteudo());

                break;

            } else {

                eDocumento.adicionar(TokenC.getConteudo() + " ");


            }

            mIndex += 1;

        }


    }

    public void formate_blocoLinear(int mTab) {


        int mDentro = mTab + 1;


        mIndex += 1;


        while (mIndex < mQuantidade) {
            Token TokenC = mTokens.get(mIndex);

            if (TokenC.getTipo() == TokenTipo.COMENTARIO_BLOCO) {

                eDocumento.adicionarLinha(mDentro, TokenC.getConteudo());

            } else if (TokenC.getTipo() == TokenTipo.COMENTARIO_LINHA) {

                eDocumento.adicionarLinha(mDentro, TokenC.getConteudo());


            } else if (TokenC.getTipo() == TokenTipo.TEXTO) {

                eDocumento.adicionarLinha(mDentro, getTexto(TokenC) + " ");


            } else if (TokenC.getTipo() == TokenTipo.CHAVE_ABRE) {


                eDocumento.adicionarLinha(mDentro, TokenC.getConteudo());

                formate_blocoLinear(mDentro);

                eDocumento.pularLinha();
                eDocumento.adicionarTab(mTab);

            } else if (TokenC.getTipo() == TokenTipo.CHAVE_FECHA) {

                eDocumento.adicionarLinha(mTab, TokenC.getConteudo());

                break;

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo_SemCS("PROC")) {

                formate_AlgoComBloco(mDentro);
                eDocumento.pularLinhaTabulada(mTab);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("if")) {

                formate_BlocoIf(mDentro);

                eDocumento.pularLinhaTabulada(mTab);


            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("while")) {

                formate_AlgoComBloco(mDentro);
                eDocumento.pularLinhaTabulada(mTab);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("daz")) {

                formate_AlgoComBloco(mDentro);
                eDocumento.pularLinhaTabulada(mTab);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("local")) {

                formate_AlgoComBloco(mDentro);
                eDocumento.pularLinhaTabulada(mTab);


            } else {

                formate_Linha(mDentro);

                if (mTokens.get(mIndex).getTipo() == TokenTipo.CHAVE_FECHA) {
                    break;
                }

            }

            mIndex += 1;

        }


    }


    public void formate_StructSub(int mTab) {

        int mDentro = mTab;

        eDocumento.pularLinhaTabulada(mDentro);

        boolean isDuplado = false;

        while (mIndex < mQuantidade) {
            Token TokenC = mTokens.get(mIndex);

            if (TokenC.getTipo() == TokenTipo.COMENTARIO_BLOCO) {

                eDocumento.adicionarLinha(mDentro, TokenC.getConteudo());

            } else if (TokenC.getTipo() == TokenTipo.COMENTARIO_LINHA) {

                eDocumento.adicionarLinha(mDentro, TokenC.getConteudo());


            } else if (TokenC.getTipo() == TokenTipo.TEXTO) {

                eDocumento.adicionar(getTexto(TokenC));

            } else if (TokenC.getTipo() == TokenTipo.DOISPONTOS) {

                eDocumento.adicionar(TokenC.getConteudo());
                mIndex += 1;

                isDuplado = true;
                break;

            } else {

                eDocumento.adicionar(TokenC.getConteudo() + " ");


            }

            mIndex += 1;

        }

        if (isDuplado) {


            formate_StructSub2(mTab + 1);

        }


    }

    public void formate_StructSub2(int mTab) {

        int mDentro = mTab;


        while (mIndex < mQuantidade) {
            Token TokenC = mTokens.get(mIndex);

            if (TokenC.getTipo() == TokenTipo.COMENTARIO_BLOCO) {

                eDocumento.adicionarLinha(mDentro, TokenC.getConteudo());

            } else if (TokenC.getTipo() == TokenTipo.COMENTARIO_LINHA) {

                eDocumento.adicionarLinha(mDentro, TokenC.getConteudo());


            } else if (TokenC.getTipo() == TokenTipo.TEXTO) {

                eDocumento.adicionar(getTexto(TokenC));


            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("define")) {

                formate_Linha(mTab);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("mockiz")) {

                formate_Linha(mTab);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("act")) {

                formate_AlgoComBloco(mTab);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("func")) {

                formate_AlgoComBloco(mTab);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("destruct")) {

                formate_AlgoComBloco(mTab);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("all")) {
                mIndex -= 1;
                break;
            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("restrict")) {
                mIndex -= 1;
                break;
            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("implicit")) {
                mIndex -= 1;
                break;
            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("explicit")) {
                mIndex -= 1;
                break;
            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("block")) {
                mIndex -= 1;
                break;
            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("extra")) {
                mIndex -= 1;
                break;
            } else if (TokenC.getTipo() == TokenTipo.CHAVE_FECHA) {

                mIndex -= 1;
                break;

            } else {

                eDocumento.adicionar(TokenC.getConteudo() + " ");


            }

            mIndex += 1;

        }


    }

    public void log(String e) {
        System.out.println(e);
    }


    public String getComentario(Token TokenC) {

        String mRet = TokenC.getConteudo();

        return mRet;
    }


    public String getTexto(Token TokenC) {

        String mRet = "";

        if (TokenC.getConteudo().contains("\"")) {
            mRet = "'" + TokenC.getConteudo() + "'";
        } else {
            mRet = "\"" + TokenC.getConteudo() + "\"";
        }

        return mRet;
    }


    public String getTabulacao(int t) {
        String ret = "";

        if (t > 0) {
            for (int i = 0; i < t; i++) {
                ret += "\t";
            }
        }

        return ret;
    }

    public boolean isFuturoID(String eConteudo) {
        boolean e = false;

        if (mIndex + 1 < mQuantidade) {
            if (mTokens.get(mIndex + 1).getTipo() == TokenTipo.ID && mTokens.get(mIndex + 1).mesmoConteudo(eConteudo)) {
                e = true;
            }
        }

        return e;
    }

}
