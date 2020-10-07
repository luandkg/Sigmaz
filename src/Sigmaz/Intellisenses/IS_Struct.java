package Sigmaz.Intellisenses;

import Sigmaz.S06_Executor.Debuggers.Simplificador;

import java.awt.*;

import Sigmaz.S00_Utilitarios.AST;

public class IS_Struct {

    private Intellisense mIntellisense;
    private IntellisenseTheme mIntellisenseTheme;

    public IS_Struct(Intellisense eIntellisense, IntellisenseTheme eIntellisenseTheme) {
        mIntellisense = eIntellisense;
        mIntellisenseTheme = eIntellisenseTheme;
    }

    public int getContagem(AST eTudo) {

        int contador = mIntellisense.getContagem(eTudo.getBranch("BODY"));

        if (eTudo.getBranch("EXTENDED").mesmoNome("STAGES")) {

            for (AST Sub2 : eTudo.getBranch("STAGES").getASTS()) {
                contador += 1;
            }


        } else if (eTudo.getBranch("EXTENDED").mesmoNome("STRUCT")) {

            for (AST Sub2 : eTudo.getBranch("INITS").getASTS()) {
                if (Sub2.mesmoNome(eTudo.getNome())) {
                    contador += 1;
                }

            }

            contador += mIntellisense.getContagemGenericos(eTudo.getBranch("GENERIC"));
            contador += mIntellisense.getContagemGenericos(eTudo.getBranch("BASES"));

            if (eTudo.getBranch("MODEL").mesmoValor("TRUE")) {
                contador += 1;
            }

        } else if (eTudo.getBranch("EXTENDED").mesmoNome("TYPE")) {

            contador += mIntellisense.getContagemGenericos(eTudo.getBranch("GENERIC"));
            contador += mIntellisense.getContagemGenericos(eTudo.getBranch("BASES"));


        }

        return contador;
    }


    public int continuar(Graphics g, AST eTudo, String eTitulo, int x, int mais, int eLargura, int altura) {


        Color eBarra = Color.RED;

        Color eTexto = mIntellisenseTheme.getTexto();
        Color eBox = mIntellisenseTheme.getBox();

        Simplificador mSimplificador = new Simplificador();

        if (eTudo.getBranch("EXTENDED").mesmoNome("STAGES")) {


            eBarra = new Color(255, 110, 64);
            eBarra = mIntellisenseTheme.getStage();


        } else if (eTudo.getBranch("EXTENDED").mesmoNome("TYPE")) {

            eBarra = new Color(64, 196, 255);
            eBarra = mIntellisenseTheme.getType();


        } else if (eTudo.getBranch("EXTENDED").mesmoNome("STRUCT")) {

            eBarra = new Color(124, 179, 66);
            eBarra = mIntellisenseTheme.getStruct();


        } else if (eTudo.getBranch("EXTENDED").mesmoNome("EXTERNAL")) {

            eBarra = new Color(22, 160, 133);
            eBarra = mIntellisenseTheme.getExternal();


        } else {


        }


        g.setColor(mIntellisenseTheme.getFundo());

        g.fillRect(x, mais, mIntellisense.getLargura(), altura - mais);


        g.setColor(eBarra);
        g.fillRect(x, mais, mIntellisense.getLargura(), 100);


        g.setColor(Color.BLACK);
        mIntellisense.centerString(g, new Rectangle(x, mais, eLargura, 100), eTitulo, new Font("TimesRoman", Font.BOLD, 50));


        mais += 110;


        if (eTudo.getBranch("EXTENDED").mesmoNome("STAGES")) {

            for (AST Sub2 : eTudo.getBranch("STAGES").getASTS()) {
                String eConteudo = Sub2.getNome();

                g.setColor(eTexto);
                mIntellisense.leftString(g, new Rectangle(x + 30, mais, eLargura, 100), eConteudo, new Font("TimesRoman", Font.BOLD, 20), mIntellisense.IMG_STAGE);

                mais += 30;

            }

            mais += 50;

            g.setColor(eBox);
            g.fillRect(x + 50, mais, eLargura / 2, 15);

            g.setColor(Color.BLACK);

            mais = mIntellisense.colocarGlobal2(x, mais, g, eTudo.getBranch("BODY"));

            mais += 50;

        } else if (eTudo.getBranch("EXTENDED").mesmoNome("TYPE")) {


            mais = Struct_Type(eTudo, x, mais, eLargura, eTexto, eBox, g);

        } else {


            if (eTudo.getBranch("MODEL").mesmoValor("TRUE")) {

                String eConteudo = eTudo.getBranch("MODEL").getNome();

                g.setColor(eTexto);
                mIntellisense.leftString(g, new Rectangle(x + 30, mais, eLargura, 100), eConteudo, new Font("TimesRoman", Font.BOLD, 20), mIntellisense.IMG_MODEL);

                mais += 30;

                mais += 70;

                g.setColor(eBox);
                g.fillRect(x + 50, mais, eLargura / 2, 15);


            }

            if (eTudo.getBranch("WITH").mesmoValor("TRUE")) {

                for (AST Sub2 : eTudo.getBranch("BASES").getASTS()) {

                    String eConteudo = Sub2.getNome();

                    g.setColor(eTexto);

                    mIntellisense.leftString(g, new Rectangle(x + 30, mais, eLargura, 100), eConteudo, new Font("TimesRoman", Font.BOLD, 20), mIntellisense.IMG_BASE);

                    mais += 30;

                }

                mais += 70;

                g.setColor(eBox);
                g.fillRect(x + 50, mais, eLargura / 2, 15);


            }

            if (eTudo.getBranch("GENERIC").mesmoNome("TRUE")) {


                for (AST Sub2 : eTudo.getBranch("GENERIC").getASTS()) {

                    String eConteudo = Sub2.getNome();

                    g.setColor(eTexto);

                    mIntellisense.leftString(g, new Rectangle(x + 30, mais, eLargura, 100), eConteudo, new Font("TimesRoman", Font.BOLD, 20), mIntellisense.IMG_GENERIC_TYPE);

                    mais += 30;

                }

                mais += 70;

                g.setColor(eBox);
                g.fillRect(x + 50, mais, eLargura / 2, 15);


            }

            int inits = 0;


            for (AST Sub2 : eTudo.getBranch("INITS").getASTS()) {

                if (Sub2.mesmoTipo("INIT")) {

                    if (Sub2.mesmoNome(eTudo.getNome())) {

                        String eConteudo = mSimplificador.getAction(Sub2);

                        g.setColor(eTexto);

                        mIntellisense.leftString(g, new Rectangle(x + 30, mais, eLargura, 100), eConteudo, new Font("TimesRoman", Font.BOLD, 20), mIntellisense.IMG_INIT);

                        mais += 30;
                        inits += 1;

                    }

                }

            }

            if (inits > 0) {
                mais += 70;

                g.setColor(eBox);
                g.fillRect(x + 50, mais, eLargura / 2, 15);

            }


            g.setColor(Color.BLACK);

            mais = mIntellisense.colocarGlobal(x, mais, g, eTudo.getBranch("BODY"));

        }

        return mais;

    }

    public int Struct_Type(AST eTudo, int x, int mais, int eLargura, Color eTexto, Color eBox, Graphics g) {

        Simplificador mSimplificador = new Simplificador();

        if (eTudo.getBranch("GENERIC").mesmoNome("TRUE")) {


            for (AST Sub2 : eTudo.getBranch("GENERIC").getASTS()) {

                String eConteudo = Sub2.getNome();

                g.setColor(eTexto);
                mIntellisense.leftString(g, new Rectangle(x + 30, mais, eLargura, 100), eConteudo, new Font("TimesRoman", Font.BOLD, 20), mIntellisense.IMG_GENERIC_TYPE);

                mais += 30;

            }

            mais += 70;

            g.setColor(eBox);
            g.fillRect(x + 50, mais, eLargura / 2, 15);


        }


        if (eTudo.getBranch("BASES").getASTS().size()>0) {

            for (AST Sub2 : eTudo.getBranch("BASES").getASTS()) {

                String eConteudo = Sub2.getNome();

                if (Sub2.getBranch("GENERIC").getASTS().size()>0){

                    eConteudo+=" < ";

                    for(AST iG : Sub2.getBranch("GENERIC").getASTS()){
                        eConteudo+=" " + iG.getNome();
                    }

                    eConteudo+=" > ";

                }

                g.setColor(eTexto);

                mIntellisense.leftString(g, new Rectangle(x + 30, mais, eLargura, 100), eConteudo, new Font("TimesRoman", Font.BOLD, 20), mIntellisense.IMG_BASE);

                mais += 30;

            }

            mais += 70;

            g.setColor(eBox);
            g.fillRect(x + 50, mais, eLargura / 2, 15);


        }


        for (AST Sub2 : eTudo.getBranch("BODY").getASTS()) {

            if (Sub2.mesmoTipo("DEFINE")) {

                g.setColor(eTexto);
                String eConteudo = mSimplificador.getDefine(Sub2);
                mais = mIntellisense.algum(Sub2, g, x, mais, "ALL", eConteudo, mIntellisense.IMG_DEFINE_TYPE);
                //  p += 1;
            }

        }


        for (AST Sub2 : eTudo.getBranch("BODY").getASTS()) {

            if (Sub2.mesmoTipo("MOCKIZ")) {
                g.setColor(eTexto);

                String eConteudo = mSimplificador.getMockiz(Sub2);
                mais = mIntellisense.algum(Sub2, g, x, mais, "ALL", eConteudo, mIntellisense.IMG_MOCKIZ_TYPE);
                //p += 1;
            }

        }

        mais += 50;


        return mais;
    }

}
