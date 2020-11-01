package Sigmaz.S09_Ferramentas;

import java.awt.*;

public class SyntaxTheme {

    private Color mSyntax_Fundo;
    private Color mSyntax_Comentario;
    private Color mSyntax_Numero;
    private Color mSyntax_Delimitadores;
    private Color mSyntax_Texto;
    private Color mSyntax_Comum;
    private Color mSyntax_Reservado;
    private Color mSyntax_Reservado_Grupo2;

    private Color mSyntax_Comando;
    private Color mSyntax_Primitivo;
    private Color mSyntax_Especial;
    private Color mSyntax_Invocadores;
    private Color mSyntax_Invocadores_Saida;

    public  SyntaxTheme(){
        light();
    }

    public void padrao(){
        light();
    }



    public Color getSyntax_Fundo(){ return mSyntax_Fundo; }

    public Color getSyntax_Comentario(){ return mSyntax_Comentario; }
    public Color getSyntax_Numero(){ return mSyntax_Numero; }
    public Color getSyntax_Delimitadores(){ return mSyntax_Delimitadores; }
    public Color getSyntax_Texto(){ return mSyntax_Texto; }
    public Color getSyntax_Comum(){ return mSyntax_Comum; }
    public Color getSyntax_Reservado(){ return mSyntax_Reservado; }
    public Color getSyntax_Reservado_Grupo2(){ return mSyntax_Reservado_Grupo2; }
    public Color getSyntax_Comando(){ return mSyntax_Comando; }
    public Color getSyntax_Primitivo(){ return mSyntax_Primitivo; }
    public Color getSyntax_Especial(){ return mSyntax_Especial; }
    public Color getSyntax_Invocadores(){ return mSyntax_Invocadores; }
    public Color getSyntax_Invocadores_Saida(){ return mSyntax_Invocadores_Saida; }


    public void dark() {


        mSyntax_Fundo = getColorHexa("#1E1E1E");

        mSyntax_Comentario = getColorHexa("#FFC600");
        mSyntax_Numero = getColorHexa("#80F6A7");
        mSyntax_Delimitadores = getColorHexa("#E6E6FA");
        mSyntax_Texto = getColorHexa("#9876AA");
        mSyntax_Invocadores = getColorHexa("#DD2867");


        mSyntax_Invocadores_Saida = getColorHexa("#1EB540");
        mSyntax_Comum = getColorHexa("#D8D8D8");

        mSyntax_Reservado = getColorHexa("#D25252");
        mSyntax_Reservado_Grupo2 = getColorHexa("#A7EC21");

        mSyntax_Comando = getColorHexa("#79ABFF");
        mSyntax_Primitivo = getColorHexa("#D9E577");
        mSyntax_Especial = getColorHexa("#1290C3");

    }

    public void light() {


        mSyntax_Fundo = getColorHexa("#F9FAF4");
        mSyntax_Comum = getColorHexa("#1E1E1E");
        mSyntax_Delimitadores = getColorHexa("#3E515D");

        mSyntax_Comentario = getColorHexa("#FFC600");
        mSyntax_Numero = getColorHexa("#80F6A7");
        mSyntax_Texto = getColorHexa("#9876AA");


        mSyntax_Reservado = getColorHexa("#D25252");
        mSyntax_Reservado_Grupo2 = getColorHexa("#A7EC21");

        mSyntax_Comando = getColorHexa("#79ABFF");
        mSyntax_Primitivo = getColorHexa("#D9E577");
        mSyntax_Especial = getColorHexa("#1290C3");

        mSyntax_Invocadores = getColorHexa("#DD2867");
        mSyntax_Invocadores_Saida = getColorHexa("#1EB540");

    }

    public Color getColorHexa(String colorStr) {
        return new Color(
                Integer.valueOf(colorStr.substring(1, 3), 16),
                Integer.valueOf(colorStr.substring(3, 5), 16),
                Integer.valueOf(colorStr.substring(5, 7), 16));
    }

}
