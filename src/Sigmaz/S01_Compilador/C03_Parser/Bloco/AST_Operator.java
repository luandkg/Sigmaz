package Sigmaz.S01_Compilador.C03_Parser.Bloco;

import Sigmaz.S01_Compilador.C03_Parser.Organizador.AST_Argumentos;
import Sigmaz.S01_Compilador.C03_Parser.Fluxo.AST_Corpo;
import Sigmaz.S01_Compilador.C03_Parser.Alocador.AST_TYPE;
import Sigmaz.S01_Compilador.C03_Parser.Parser;
import Sigmaz.S01_Compilador.C02_Lexer.Token;
import Sigmaz.S01_Compilador.C02_Lexer.TokenTipo;
import Sigmaz.S00_Utilitarios.AST;

public class AST_Operator {

    private Parser mCompiler;

    public AST_Operator(Parser eCompiler) {
        mCompiler = eCompiler;
    }

    //public void init(AST ASTPai, String Visibilidade) {
    public void init(AST ASTPai) {

        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.COMPARADOR_IGUALDADE) {

            definir(ASTPai, "MATCH");

        } else if (TokenC.getTipo() == TokenTipo.COMPARADOR_DIFERENTE) {

            definir(ASTPai, "UNMATCH");

        } else if (TokenC.getTipo() == TokenTipo.SOMADOR) {

            definir(ASTPai, "SUM");
        } else if (TokenC.getTipo() == TokenTipo.DIMINUIDOR) {

            definir(ASTPai, "SUB");
        } else if (TokenC.getTipo() == TokenTipo.MULTIPLICADOR) {

            definir(ASTPai, "MUX");
        } else if (TokenC.getTipo() == TokenTipo.DIVISOR) {

            definir(ASTPai, "DIV");
        } else if (TokenC.getTipo() == TokenTipo.ENVIAR) {

            definir(ASTPai, "GREAT");
        } else if (TokenC.getTipo() == TokenTipo.RECEBER) {

            definir(ASTPai, "LESS");
        } else if (TokenC.getTipo() == TokenTipo.ANEXADOR) {

            definir(ASTPai, "APPEND");

        } else {
            mCompiler.errarCompilacao("Era esperado o operador da OPERATION !", TokenC);
        }


    }

    public void definir(AST ASTPai,  String eOperator) {

    //public void definir(AST ASTPai, String Visibilidade, String eOperator) {

        AST AST_Corrente = new AST("OPERATOR");
        AST_Corrente.setNome(eOperator);
        ASTPai.getASTS().add(AST_Corrente);

        AST AST_Visibilidade = AST_Corrente.criarBranch("VISIBILITY");
      //  AST_Visibilidade.setNome(Visibilidade);

        AST AST_Arguments = AST_Corrente.criarBranch("ARGUMENTS");




        AST_Argumentos mArgumentos = new AST_Argumentos(mCompiler);
        mArgumentos.init(AST_Arguments);

        AST_TYPE mType = new AST_TYPE(mCompiler);
        mType.init(AST_Corrente);


        AST AST_BODY = AST_Corrente.criarBranch("BODY");
        AST_Corpo mCorpo = new AST_Corpo(mCompiler);
        mCorpo.init(AST_BODY);


    }


}