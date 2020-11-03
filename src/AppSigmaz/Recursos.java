package AppSigmaz;

import java.awt.*;


public class Recursos {


    public void init(String eLocal) {

        System.out.println("");
        System.out.println("################ RECURSOS ################");
        System.out.println("");
        System.out.println("\t - Local : " + eLocal);


        RecursoSequenciador mRecursoSequenciador = new RecursoSequenciador(1000, 2300);


        Color eCorTituloCaixa = getColorHexa("#FFC600");
        Color eCorCaixa = getColorHexa("#85bb5c");
        Color eCorPequeno = getColorHexa("#63ccff");
        Color eAmarelo = getColorHexa("#fdd835");

        mRecursoSequenciador.criarTitulo("SIGMAZ", eCorTituloCaixa);


        mRecursoSequenciador.espacar(200);

        int pi = mRecursoSequenciador.criarSessao("COMPILADOR", eCorCaixa);
        int si = mRecursoSequenciador.criarSubSessao("Lexer", eCorPequeno);
        mRecursoSequenciador.criarSubSessao("Tokenizer", eCorPequeno);
        mRecursoSequenciador.criarSubSessao("Parser", eCorPequeno);
        int so = mRecursoSequenciador.criarSubSessao("Enfileriamento", eCorPequeno);


        mRecursoSequenciador.criarConexao(400, si, so, eCorPequeno);


        mRecursoSequenciador.espacar(70);
        mRecursoSequenciador.criarSessao("POS - PROCESSADOR", eCorCaixa);
        mRecursoSequenciador.criarSubSessao("Requeridor", eCorPequeno);
        mRecursoSequenciador.criarSubSessao("Alocador", eCorPequeno);
        mRecursoSequenciador.criarSubSessao("Castificador", eCorPequeno);
        mRecursoSequenciador.criarSubSessao("Unificador", eCorPequeno);
        mRecursoSequenciador.criarSubSessao("Heranca", eCorPequeno);
        mRecursoSequenciador.criarSubSessao("Modelador", eCorPequeno);
        mRecursoSequenciador.criarSubSessao("Estagiador", eCorPequeno);
        mRecursoSequenciador.criarSubSessao("Tipador", eCorPequeno);
        mRecursoSequenciador.criarSubSessao("Referenciador", eCorPequeno);
        mRecursoSequenciador.criarSubSessao("Argumentador", eCorPequeno);
        mRecursoSequenciador.criarSubSessao("Opcionador", eCorPequeno);
        mRecursoSequenciador.criarSubSessao("Estruturador", eCorPequeno);
        mRecursoSequenciador.criarSubSessao("Uniciade", eCorPequeno);

        mRecursoSequenciador.espacar(70);
        mRecursoSequenciador.criarSessao("INTEGRADOR", eCorCaixa);

        mRecursoSequenciador.espacar(70);
        int po = mRecursoSequenciador.criarSessao("MONTADOR", eCorCaixa);
        mRecursoSequenciador.criarSubSessao("Montagem", eCorPequeno);
        mRecursoSequenciador.criarSubSessao("Cifragem", eCorPequeno);
        mRecursoSequenciador.criarSubSessao("Arquivamento", eCorPequeno);

        mRecursoSequenciador.criarDescida(150, pi, po, eCorCaixa);

        mRecursoSequenciador.exportar(eLocal + "compiler.png");


    }

    public Color getColorHexa(String colorStr) {
        return new Color(
                Integer.valueOf(colorStr.substring(1, 3), 16),
                Integer.valueOf(colorStr.substring(3, 5), 16),
                Integer.valueOf(colorStr.substring(5, 7), 16));
    }


}
