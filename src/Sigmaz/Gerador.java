package Sigmaz;

import java.util.ArrayList;
import Sigmaz.Utils.Documento;
import Sigmaz.Utils.Texto;

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

    public void gerarPrint(String eArquivo){

        mDocumento = new Documento();

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

        Texto.Escrever(eArquivo,   mDocumento.getConteudo());
    }

    public void print_1() {

        for (String a : eTipos) {
           // for (String b : eTipos) {
               // for (String c : eTipos) {

                     mDocumento.adicionarLinha("act print ( a : " + a +  " ) {");


                     mDocumento.adicionarLinha("def retorno: int = 0;");
                     mDocumento.adicionarLinha("invoke terminal -> print ( a ) ::  retorno;");


                     mDocumento.adicionarLinha("}");


           //     }
           // }


        }

    }


    public void println_1() {

        for (String a : eTipos) {
           // for (String b : eTipos) {
               // for (String c : eTipos) {

                     mDocumento.adicionarLinha("act println ( a : " + a +  " ) {");


                     mDocumento.adicionarLinha("def retorno: num;");
                     mDocumento.adicionarLinha("invoke terminal -> change ( ) :: retorno;");
                     mDocumento.adicionarLinha("invoke terminal -> print ( a ) ::  retorno;");


                     mDocumento.adicionarLinha("}");


             //   }
          //  }


        }

    }

    public void println_2() {

        for (String a : eTipos) {
            for (String b : eTipos) {
               // for (String c : eTipos) {

                     mDocumento.adicionarLinha("act println ( a : " + a + " , b : " + b + " ) {");


                     mDocumento.adicionarLinha("def retorno: num;");
                     mDocumento.adicionarLinha("invoke terminal -> change ( ) :: retorno;");
                     mDocumento.adicionarLinha("invoke terminal -> print ( a ) ::  retorno;");
                     mDocumento.adicionarLinha("invoke terminal -> print ( b ) :: retorno;");


                     mDocumento.adicionarLinha("}");


              //  }
            }


        }

    }

    public void println_3() {

        for (String a : eTipos) {
            for (String b : eTipos) {
                for (String c : eTipos) {

                         mDocumento.adicionarLinha("act println ( a : " + a + " , b : " + b + " ,c : " + c + " ) {");


                         mDocumento.adicionarLinha("def retorno: num;");
                         mDocumento.adicionarLinha("invoke terminal -> change ( ) :: retorno;");
                         mDocumento.adicionarLinha("invoke terminal -> print ( a ) ::  retorno;");
                         mDocumento.adicionarLinha("invoke terminal -> print ( b ) :: retorno;");
                         mDocumento.adicionarLinha("invoke terminal -> print ( c ) ::  retorno;");

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


                         mDocumento.adicionarLinha("def retorno: num;");
                         mDocumento.adicionarLinha("invoke terminal -> change ( ) :: retorno;");
                         mDocumento.adicionarLinha("invoke terminal -> print ( a ) ::  retorno;");
                         mDocumento.adicionarLinha("invoke terminal -> print ( b ) :: retorno;");
                         mDocumento.adicionarLinha("invoke terminal -> print ( c ) ::  retorno;");
                         mDocumento.adicionarLinha("invoke terminal -> print ( d ) ::  retorno;");

                         mDocumento.adicionarLinha("}");

                    }
                }
            }


        }

    }
}
