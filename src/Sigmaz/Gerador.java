package Sigmaz;

import java.util.ArrayList;

public class Gerador {

    private ArrayList<String> eTipos;

    public Gerador() {

        eTipos = new ArrayList<String>();
        eTipos.add("string");
        eTipos.add("num");
        eTipos.add("bool");


    }

    public void gerarPrint(){

        System.out.println(" # PRINT - 1 ARGUMENTO");
        print_1();
        System.out.println(" # PRINTLN - 1 ARGUMENTO");
        println_1();
        System.out.println(" # PRINTLN - 2 ARGUMENTOS");
        println_2();
        System.out.println(" # PRINTLN - 3 ARGUMENTOS");
        println_3();
        System.out.println(" # PRINTLN - 4 ARGUMENTOS");
        println_4();
    }

    public void print_1() {

        for (String a : eTipos) {
           // for (String b : eTipos) {
               // for (String c : eTipos) {

                    System.out.println("act print ( a : " + a +  " ) {");


                    System.out.println("def retorno: num;");
                    System.out.println("invoke terminal -> print ( a ) ::  retorno;");


                    System.out.println("}");


           //     }
           // }


        }

    }


    public void println_1() {

        for (String a : eTipos) {
           // for (String b : eTipos) {
               // for (String c : eTipos) {

                    System.out.println("act println ( a : " + a +  " ) {");


                    System.out.println("def retorno: num;");
                    System.out.println("invoke terminal -> change ( ) :: retorno;");
                    System.out.println("invoke terminal -> print ( a ) ::  retorno;");


                    System.out.println("}");


             //   }
          //  }


        }

    }

    public void println_2() {

        for (String a : eTipos) {
            for (String b : eTipos) {
               // for (String c : eTipos) {

                    System.out.println("act println ( a : " + a + " , b : " + b + " ) {");


                    System.out.println("def retorno: num;");
                    System.out.println("invoke terminal -> change ( ) :: retorno;");
                    System.out.println("invoke terminal -> print ( a ) ::  retorno;");
                    System.out.println("invoke terminal -> print ( b ) :: retorno;");


                    System.out.println("}");


              //  }
            }


        }

    }

    public void println_3() {

        for (String a : eTipos) {
            for (String b : eTipos) {
                for (String c : eTipos) {

                        System.out.println("act println ( a : " + a + " , b : " + b + " ,c : " + c + " ) {");


                        System.out.println("def retorno: num;");
                        System.out.println("invoke terminal -> change ( ) :: retorno;");
                        System.out.println("invoke terminal -> print ( a ) ::  retorno;");
                        System.out.println("invoke terminal -> print ( b ) :: retorno;");
                        System.out.println("invoke terminal -> print ( c ) ::  retorno;");

                        System.out.println("}");


                }
            }


        }

    }

    public void println_4() {

        for (String a : eTipos) {
            for (String b : eTipos) {
                for (String c : eTipos) {
                    for (String d : eTipos) {

                        System.out.println("act println ( a : " + a + " , b : " + b + " ,c : " + c + " , d : " + d + " ) {");


                        System.out.println("def retorno: num;");
                        System.out.println("invoke terminal -> change ( ) :: retorno;");
                        System.out.println("invoke terminal -> print ( a ) ::  retorno;");
                        System.out.println("invoke terminal -> print ( b ) :: retorno;");
                        System.out.println("invoke terminal -> print ( c ) ::  retorno;");
                        System.out.println("invoke terminal -> print ( d ) ::  retorno;");

                        System.out.println("}");

                    }
                }
            }


        }

    }
}
