package Sigmaz.S00_Utilitarios;

import Sigmaz.S05_PosProcessamento.Processadores.Cabecalho;

import java.util.ArrayList;

public class SigmazCab {

    public void gravarCabecalho(Cabecalho eCabecalho, ArrayList<AST> mASTS) {

        UUID mUUID = new UUID();

        mASTS.clear();




        AST ma = new AST("AUTHORES");
        mASTS.add(ma);

        for (String eAutor : eCabecalho.getAutores()) {

            AST tmpA = ma.criarBranch("AUTHOR");
            tmpA.setValor(eAutor);

        }

        AST AVersao = new AST("VERSION");
        AVersao.setValor(eCabecalho.getVersao());
        mASTS.add(AVersao);

        AST AC = new AST("COMPANY");
        AC.setValor(eCabecalho.getCompanhia());
        mASTS.add(AC);

        AST ePrivado = new AST("PRIVATE");
        ePrivado.setValor(mUUID.getUUID());
        mASTS.add(ePrivado);

        AST ePublico = new AST("PUBLIC");
        ePublico.setValor(mUUID.getUUID());
        mASTS.add(ePublico);

        AST eShared = new AST("SHARED");
        eShared.setValor(mUUID.getUUID());
        mASTS.add(eShared);

        AST eDevelopment = new AST("DEVELOPMENT");
        mASTS.add(eDevelopment);

        AST ePre = eDevelopment.criarBranch("PREPROCESSOR");
        ePre.setNome(eCabecalho.getPreProcessor());
        ePre.setValor(eCabecalho.getPreProcessor_UUID());


        AST eLexer = eDevelopment.criarBranch("LEXER");
        eLexer.setNome(eCabecalho.getLexer());
        eLexer.setValor(eCabecalho.getLexer_UUID());

        AST eParser = eDevelopment.criarBranch("PARSER");
        eParser.setNome(eCabecalho.getParser());
        eParser.setValor(eCabecalho.getParser_UUID());

        AST eCompiler = eDevelopment.criarBranch("COMPILER");
        eCompiler.setNome(eCabecalho.getCompiler());
        eCompiler.setValor(eCabecalho.getCompiler_UUID());

        AST ePosProcessor = eDevelopment.criarBranch("POSPROCESSOR");
        ePosProcessor.setNome(eCabecalho.getPosProcessor());
        ePosProcessor.setValor(eCabecalho.getPosProcessor_UUID());


    }


}
