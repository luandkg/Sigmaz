package Sigmaz.S06_Ferramentas;

import java.util.ArrayList;

import Sigmaz.S08_Utilitarios.Documento;
import Sigmaz.S08_Utilitarios.Texto;

public class Gerador {

    private ArrayList<String> eTipos;

    private Documento mDocumento;

    public Gerador() {

        eTipos = new ArrayList<String>();
        eTipos.add("string");
        eTipos.add("num");
        eTipos.add("bool");
        eTipos.add("int");

    }

    public void gerarPrint(String eArquivo) {

        mDocumento = new Documento();

        mDocumento.adicionarLinha("");
        mDocumento.adicionarLinha(" act print_empty ( ) {");
        mDocumento.adicionarLinha("    PROC -> {");
        mDocumento.adicionarLinha("        PROC CHANGE_LINE;");
        mDocumento.adicionarLinha("    }");
        mDocumento.adicionarLinha("   }");
        mDocumento.adicionarLinha("");

        mDocumento.adicionarLinha(" # PRINT - 1 ARGUMENTO");
        print_1();
        mDocumento.adicionarLinha(" # PRINTLN - 1 ARGUMENTO");
        println_1();
        mDocumento.adicionarLinha(" # PRINTLN - 2 ARGUMENTOS");
        println_2();
        mDocumento.adicionarLinha(" # PRINTLN - 3 ARGUMENTOS");
        println_3();
        mDocumento.adicionarLinha(" # PRINTLN - 4 ARGUMENTOS");
        println_4();

        Texto.Escrever(eArquivo, mDocumento.getConteudo());
    }

    public void print_1() {

        for (String a : eTipos) {

            mDocumento.adicionarLinha("act print ( a : " + a + " ) {");

            if (a.contentEquals("string")) {

                mDocumento.adicionarLinha("\treg @R13 -> a;");
                mDocumento.adicionarLinha("\tPROC -> {");
                mDocumento.adicionarLinha("\t\tPRINT R13;");
                mDocumento.adicionarLinha("\t}");

            } else if (a.contentEquals("bool")) {

                mDocumento.adicionarLinha("\treg @R1 -> a;");
                mDocumento.adicionarLinha("\tPROC -> {");
                mDocumento.adicionarLinha("\t\tSET R13;");
                mDocumento.adicionarLinha("\t\tMOV \"\";");
                mDocumento.adicionarLinha("\t\tBOOL_STRING R1;");
                mDocumento.adicionarLinha("\t\tPRINT R13;");
                mDocumento.adicionarLinha("\t}");

            } else if (a.contentEquals("int")) {

                mDocumento.adicionarLinha("\treg @R5 -> a;");
                mDocumento.adicionarLinha("\tPROC -> {");
                mDocumento.adicionarLinha("\t\tSET R13;");
                mDocumento.adicionarLinha("\t\tMOV \"\";");
                mDocumento.adicionarLinha("\t\tINT_STRING R5;");
                mDocumento.adicionarLinha("\t\tPRINT R13;");
                mDocumento.adicionarLinha("\t}");

            } else if (a.contentEquals("num")) {

                mDocumento.adicionarLinha("\treg @R9 -> a;");
                mDocumento.adicionarLinha("\tPROC -> {");
                mDocumento.adicionarLinha("\t\tSET R13;");
                mDocumento.adicionarLinha("\t\tMOV \"\";");
                mDocumento.adicionarLinha("\t\tNUM_STRING R9;");
                mDocumento.adicionarLinha("\t\tPRINT R13;");
                mDocumento.adicionarLinha("\t}");

            }


            mDocumento.adicionarLinha("}");


        }

    }


    public void println_1() {

        for (String a : eTipos) {

            mDocumento.adicionarLinha("act println ( a : " + a + " ) {");


            if (a.contentEquals("string")) {

                mDocumento.adicionarLinha("\treg @R13 -> a;");
                mDocumento.adicionarLinha("\tPROC -> {");
                mDocumento.adicionarLinha("\t\tPROC CHANGE_LINE;");
                mDocumento.adicionarLinha("\t\tPRINT R13;");
                mDocumento.adicionarLinha("\t}");

            } else if (a.contentEquals("bool")) {

                mDocumento.adicionarLinha("\treg @R1 -> a;");
                mDocumento.adicionarLinha("\tPROC -> {");
                mDocumento.adicionarLinha("\t\tPROC CHANGE_LINE;");

                mDocumento.adicionarLinha("\t\tSET R13;");
                mDocumento.adicionarLinha("\t\tMOV \"\";");
                mDocumento.adicionarLinha("\t\tBOOL_STRING R1;");
                mDocumento.adicionarLinha("\t\tPRINT R13;");
                mDocumento.adicionarLinha("\t}");

            } else if (a.contentEquals("int")) {

                mDocumento.adicionarLinha("\treg @R5 -> a;");
                mDocumento.adicionarLinha("\tPROC -> {");
                mDocumento.adicionarLinha("\t\tPROC CHANGE_LINE;");

                mDocumento.adicionarLinha("\t\tSET R13;");
                mDocumento.adicionarLinha("\t\tMOV \"\";");
                mDocumento.adicionarLinha("\t\tINT_STRING R5;");
                mDocumento.adicionarLinha("\t\tPRINT R13;");
                mDocumento.adicionarLinha("\t}");

            } else if (a.contentEquals("num")) {

                mDocumento.adicionarLinha("\treg @R9 -> a;");
                mDocumento.adicionarLinha("\tPROC -> {");
                mDocumento.adicionarLinha("\t\tPROC CHANGE_LINE;");

                mDocumento.adicionarLinha("\t\tSET R13;");
                mDocumento.adicionarLinha("\t\tMOV \"\";");
                mDocumento.adicionarLinha("\t\tNUM_STRING R9;");
                mDocumento.adicionarLinha("\t\tPRINT R13;");
                mDocumento.adicionarLinha("\t}");

            }


            mDocumento.adicionarLinha("}");


        }

    }

    public void println_2() {

        for (String a : eTipos) {
            for (String b : eTipos) {

                mDocumento.adicionarLinha("act println ( a : " + a + " , b : " + b + " ) {");


                mDocumento.adicionarLinha("\tPROC -> {");
                mDocumento.adicionarLinha("\t\tPROC CHANGE_LINE;");
                mDocumento.adicionarLinha("\t}");

                argumento(a, "a", mDocumento);
                mDocumento.adicionarLinha("");
                argumento(b, "b", mDocumento);


                mDocumento.adicionarLinha("}");


            }


        }

    }

    public void argumento(String a, String variavel, Documento mDocumento) {

        if (a.contentEquals("string")) {

            mDocumento.adicionarLinha("\treg @R13 -> " + variavel + ";");
            mDocumento.adicionarLinha("\tPROC -> {");
            mDocumento.adicionarLinha("\t\tPRINT R13;");
            mDocumento.adicionarLinha("\t}");

        } else if (a.contentEquals("bool")) {

            mDocumento.adicionarLinha("\treg @R1 -> " + variavel + ";");
            mDocumento.adicionarLinha("\tPROC -> {");
            mDocumento.adicionarLinha("\t\tSET R13;");
            mDocumento.adicionarLinha("\t\tMOV \"\";");
            mDocumento.adicionarLinha("\t\tBOOL_STRING R1;");
            mDocumento.adicionarLinha("\t\tPRINT R13;");
            mDocumento.adicionarLinha("\t}");

        } else if (a.contentEquals("int")) {

            mDocumento.adicionarLinha("\treg @R5 -> " + variavel + ";");
            mDocumento.adicionarLinha("\tPROC -> {");
            mDocumento.adicionarLinha("\t\tSET R13;");
            mDocumento.adicionarLinha("\t\tMOV \"\";");
            mDocumento.adicionarLinha("\t\tINT_STRING R5;");
            mDocumento.adicionarLinha("\t\tPRINT R13;");
            mDocumento.adicionarLinha("\t}");

        } else if (a.contentEquals("num")) {

            mDocumento.adicionarLinha("\treg @R9 -> " + variavel + ";");
            mDocumento.adicionarLinha("\tPROC -> {");
            mDocumento.adicionarLinha("\t\tSET R13;");
            mDocumento.adicionarLinha("\t\tMOV \"\";");
            mDocumento.adicionarLinha("\t\tNUM_STRING R9;");
            mDocumento.adicionarLinha("\t\tPRINT R13;");
            mDocumento.adicionarLinha("\t}");

        }


    }

    public void println_3() {

        for (String a : eTipos) {
            for (String b : eTipos) {
                for (String c : eTipos) {

                    mDocumento.adicionarLinha("act println ( a : " + a + " , b : " + b + " ,c : " + c + " ) {");


                    mDocumento.adicionarLinha("\tPROC -> {");
                    mDocumento.adicionarLinha("\t\tPROC CHANGE_LINE;");
                    mDocumento.adicionarLinha("\t}");

                    argumento(a, "a", mDocumento);
                    mDocumento.adicionarLinha("");
                    argumento(b, "b", mDocumento);
                    mDocumento.adicionarLinha("");
                    argumento(c, "c", mDocumento);


                    mDocumento.adicionarLinha("}");


                }
            }


        }

    }

    public void println_4() {

        for (String a : eTipos) {
            for (String b : eTipos) {
                for (String c : eTipos) {
                    for (String d : eTipos) {

                        mDocumento.adicionarLinha("act println ( a : " + a + " , b : " + b + " ,c : " + c + " , d : " + d + " ) {");

                        mDocumento.adicionarLinha("\tPROC -> {");
                        mDocumento.adicionarLinha("\t\tPROC CHANGE_LINE;");
                        mDocumento.adicionarLinha("\t}");

                        argumento(a, "a", mDocumento);
                        mDocumento.adicionarLinha("");
                        argumento(b, "b", mDocumento);
                        mDocumento.adicionarLinha("");
                        argumento(c, "c", mDocumento);
                        mDocumento.adicionarLinha("");
                        argumento(d, "d", mDocumento);


                        mDocumento.adicionarLinha("}");

                    }
                }
            }


        }

    }
}
