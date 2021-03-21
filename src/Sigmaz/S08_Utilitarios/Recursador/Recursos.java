package Sigmaz.S08_Utilitarios.Recursador;

import java.awt.*;

public class Recursos {

    public Color getColorHexa(String colorStr) {
        return new Color(
                Integer.valueOf(colorStr.substring(1, 3), 16),
                Integer.valueOf(colorStr.substring(3, 5), 16),
                Integer.valueOf(colorStr.substring(5, 7), 16));
    }

    public void process_compiler(String eLocal) {


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
        int so = mRecursoSequenciador.criarSubSessao("Enfileiramento", eCorPequeno);


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
        mRecursoSequenciador.criarSubSessao("Proteção", eCorPequeno);
        mRecursoSequenciador.criarSubSessao("Arquivamento", eCorPequeno);

        mRecursoSequenciador.criarDescida(150, pi, po, eCorCaixa);

        mRecursoSequenciador.exportar(eLocal);

    }


    public void escopo_01(String eLocal) {


        RecursoEscopador mRecursoEscopador = new RecursoEscopador(2000, 900);

        Color eCorVerdeEscuro = getColorHexa("#6b9b37");
        Color ecorVerdeClaro = getColorHexa("#cfff95");

        Color eCorVermelhoEscuro = getColorHexa("#b61827");
        Color ecorVermelhoClaro = getColorHexa("#ff867c");

        Color eCorAzulEscuro = getColorHexa("#0069c0");
        Color ecorAzulClaro = getColorHexa("#6ec6ff");

        Color eCorAmareloEscuro = getColorHexa("#c8b900");
        Color ecorAmareloClaro = getColorHexa("#ffff72");


        Color eCorRoxoEscuro = getColorHexa("#6a0080");
        Color ecorRoxoClaro = getColorHexa("#d05ce3");

        Color eCorCinza = getColorHexa("#8eacbb");
        Color eCorCinzaEscuro = getColorHexa("#1c313a");

        Color eCorVerdeEscuro2 = getColorHexa("#5a9216");
        Color eCorVerdeClaro2 = getColorHexa("#bef67a");

        Color eCorLaranjadoEscuro = getColorHexa("#c67100");
        Color eCorLaranjadoClaro = getColorHexa("#ffa000");


        Ponto e1 = mRecursoEscopador.criarEscopo("GLOBAL", eCorVerdeEscuro, ecorVerdeClaro);

        mRecursoEscopador.criarCaixa("Action CORRER", e1, eCorLaranjadoEscuro, eCorLaranjadoClaro);

        mRecursoEscopador.criarCaixa("Def X : int = 5", e1, eCorVermelhoEscuro, ecorVermelhoClaro);
        mRecursoEscopador.criarCaixa("Def Y : int = 2", e1, eCorVermelhoEscuro, ecorVermelhoClaro);
        Ponto ponto_s = mRecursoEscopador.criarCaixa("CORRER ( X , Y )", e1, eCorAmareloEscuro, ecorAmareloClaro);


        Ponto e2 = mRecursoEscopador.criarEscopo("Action CORRER", eCorLaranjadoEscuro, eCorLaranjadoClaro);
        mRecursoEscopador.criarCaixa("Arg A : int = 5", e2, eCorCinzaEscuro, eCorCinza);
        mRecursoEscopador.criarCaixa("Arg B : int = 2", e2, eCorCinzaEscuro, eCorCinza);
        mRecursoEscopador.criarDivisorCaixa(e2, eCorLaranjadoEscuro);

        mRecursoEscopador.associar(ponto_s, e2, eCorCinza);



        mRecursoEscopador.exportar(eLocal);

    }

    public void escopo_02(String eLocal) {


        RecursoEscopador mRecursoEscopador = new RecursoEscopador(2000, 900);

        Color eCorVerdeEscuro = getColorHexa("#6b9b37");
        Color ecorVerdeClaro = getColorHexa("#cfff95");

        Color eCorVermelhoEscuro = getColorHexa("#b61827");
        Color ecorVermelhoClaro = getColorHexa("#ff867c");

        Color eCorAzulEscuro = getColorHexa("#0069c0");
        Color ecorAzulClaro = getColorHexa("#6ec6ff");

        Color eCorAmareloEscuro = getColorHexa("#c8b900");
        Color ecorAmareloClaro = getColorHexa("#ffff72");


        Color eCorRoxoEscuro = getColorHexa("#6a0080");
        Color ecorRoxoClaro = getColorHexa("#d05ce3");

        Color eCorCinza = getColorHexa("#8eacbb");
        Color eCorCinzaEscuro = getColorHexa("#1c313a");

        Color eCorVerdeEscuro2 = getColorHexa("#5a9216");
        Color eCorVerdeClaro2 = getColorHexa("#bef67a");



        Ponto e1 = mRecursoEscopador.criarEscopo("GLOBAL", eCorVerdeEscuro, ecorVerdeClaro);

        mRecursoEscopador.criarCaixa("Function SOMAR", e1, eCorAzulEscuro, ecorAzulClaro);
        mRecursoEscopador.criarCaixa("Operator ++", e1, eCorRoxoEscuro, ecorRoxoClaro);

        mRecursoEscopador.criarCaixa("Def X : int = 5", e1, eCorVermelhoEscuro, ecorVermelhoClaro);
        mRecursoEscopador.criarCaixa("Def Y : int = 2", e1, eCorVermelhoEscuro, ecorVermelhoClaro);
        Ponto ponto_s = mRecursoEscopador.criarCaixa("Def S : int = SOMAR ( X , Y )", e1, eCorVermelhoEscuro, ecorVermelhoClaro);


        Ponto e2 = mRecursoEscopador.criarEscopo("Function SOMAR", eCorAzulEscuro, ecorAzulClaro);
        mRecursoEscopador.criarCaixa("Arg A : int = 5", e2, eCorCinzaEscuro, eCorCinza);
        mRecursoEscopador.criarCaixa("Arg B : int = 2", e2, eCorCinzaEscuro, eCorCinza);
        mRecursoEscopador.criarDivisorCaixa(e2, eCorAzulEscuro);
        Ponto l1 = mRecursoEscopador.criarCaixa("Def C : int = A ++ B", e2, eCorVermelhoEscuro, ecorVermelhoClaro);
        mRecursoEscopador.escopoPular(280);
        mRecursoEscopador.criarDivisorCaixa(e2, eCorAzulEscuro);
        mRecursoEscopador.criarCaixa("return C", e2, eCorAzulEscuro, eCorAzulEscuro);
        mRecursoEscopador.associar(ponto_s, e2, eCorCinza);

        Ponto e3 = mRecursoEscopador.criarEscopo("Operator ++", eCorRoxoEscuro, ecorRoxoClaro);
        mRecursoEscopador.criarCaixa("Arg A : int = 5", e3, eCorCinzaEscuro, eCorCinza);
        mRecursoEscopador.criarCaixa("Arg B : int = 2", e3, eCorCinzaEscuro, eCorCinza);
        mRecursoEscopador.criarDivisorCaixa(e3, eCorRoxoEscuro);

        mRecursoEscopador.criarCaixa("Reg R4 -> A", e3, eCorVerdeEscuro2, eCorVerdeClaro2);
        mRecursoEscopador.criarCaixa("Reg R5 -> B", e3, eCorVerdeEscuro2, eCorVerdeClaro2);

        mRecursoEscopador.criarCaixa("PROC", e3, eCorVerdeEscuro2, eCorVerdeClaro2);
        mRecursoEscopador.criarCaixa("Def C : int = reg R6", e3, eCorVermelhoEscuro, ecorVermelhoClaro);
        mRecursoEscopador.escopoPular(40);
        mRecursoEscopador.criarDivisorCaixa(e3, eCorRoxoEscuro);
        mRecursoEscopador.criarCaixa("return C", e3, eCorRoxoEscuro, eCorRoxoEscuro);
        mRecursoEscopador.associar(l1, e3, eCorCinza);


        mRecursoEscopador.exportar(eLocal);

    }

}
