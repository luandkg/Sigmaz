package AppSigmaz;

import java.util.ArrayList;

public class Carregadores {

    public static ArrayList<String> Carregar_Bibliotecas() {

        ArrayList<String> mArquivos = new ArrayList<String>();

        mArquivos.add("res/libs/cast.sigmaz");
        mArquivos.add("res/libs/lib.sigmaz");
        mArquivos.add("res/libs/neg.sigmaz");
        mArquivos.add("res/libs/operadores.sigmaz");
        mArquivos.add("res/libs/pos.sigmaz");
        mArquivos.add("res/libs/strings.sigmaz");
        mArquivos.add("res/libs/tempo.sigmaz");
        mArquivos.add("res/libs/terminal.sigmaz");


        return mArquivos;

    }

    public static ArrayList<String> Carregar_Arquivos() {

        ArrayList<String> mArquivos = new ArrayList<String>();

        // INICIANTE

        mArquivos.add("res/01 - init.sigmaz");
        mArquivos.add("res/02 - arguments.sigmaz");
        mArquivos.add("res/03 - define.sigmaz");
        mArquivos.add("res/04 - require.sigmaz");
        mArquivos.add("res/05 - function.sigmaz");
        mArquivos.add("res/06 - functions2.sigmaz");
        mArquivos.add("res/07 - mockiz.sigmaz");
        mArquivos.add("res/08 - operation.sigmaz");

        // STATEMENTS

        mArquivos.add("res/09 - condition.sigmaz");
        mArquivos.add("res/10 - while.sigmaz");
        mArquivos.add("res/11 - cancel.sigmaz");
        mArquivos.add("res/12 - continue.sigmaz");
        mArquivos.add("res/13 - step.sigmaz");
        mArquivos.add("res/14 - stepdef.sigmaz");
        mArquivos.add("res/15 - when.sigmaz");
        mArquivos.add("res/16 - daz.sigmaz");
        mArquivos.add("res/17 - test.sigmaz");
        mArquivos.add("res/18 - operations.sigmaz");

        // ESTRUTURANTES

        mArquivos.add("res/19 - cast.sigmaz");
        mArquivos.add("res/20 - cast2.sigmaz");
        mArquivos.add("res/21 - struct.sigmaz");
        mArquivos.add("res/22 - internal.sigmaz");
        mArquivos.add("res/23 - oo.sigmaz");
        mArquivos.add("res/24 - oo2.sigmaz");
        mArquivos.add("res/25 - recursao.sigmaz");
        mArquivos.add("res/26 - oorecursao.sigmaz");
        mArquivos.add("res/27 - construtor.sigmaz");

        // HERANCA

        mArquivos.add("res/28 - heranca.sigmaz");
        mArquivos.add("res/29 - initheranca.sigmaz");
        mArquivos.add("res/30 - initheranca2.sigmaz");
        mArquivos.add("res/31 - stages.sigmaz");

        // OUTROS

        mArquivos.add("res/32 - stages2.sigmaz");
        mArquivos.add("res/33 - oo.sigmaz");
        mArquivos.add("res/34 - visibility.sigmaz");
        mArquivos.add("res/35 - extern.sigmaz");
        mArquivos.add("res/36 - externs.sigmaz");
        mArquivos.add("res/37 - this.sigmaz");
        mArquivos.add("res/38 - exception.sigmaz");
        mArquivos.add("res/39 - time.sigmaz");

        // COMPLEXOS

        mArquivos.add("res/40 - ternal.sigmaz");
        mArquivos.add("res/41 - tempo.sigmaz");
        mArquivos.add("res/42 - generic.sigmaz");
        mArquivos.add("res/43 - list.sigmaz");
        mArquivos.add("res/44 - initlib.sigmaz");
        mArquivos.add("res/45 - external.sigmaz");
        mArquivos.add("res/46 - model.sigmaz");
        mArquivos.add("res/47 - generic.sigmaz");
        mArquivos.add("res/48 - values.sigmaz");
        mArquivos.add("res/49 - type.sigmaz");

        return mArquivos;
    }

}
