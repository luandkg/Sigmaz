package AppSigmaz;

import Sigmaz.Sigmaz;
import Sigmaz.CSigmaz;

public class AppSigmaz {

    public static void main(String[] args) {
        //String arquivo = "res/01 - init.sigmaz";
        // String arquivo = "res/02 - arguments.sigmaz";
        // String arquivo = "res/03 - define.sigmaz";
        // String arquivo = "res/04 - require.sigmaz";
        //String arquivo = "res/05 - function.sigmaz";
        //  String arquivo = "res/06 - functions2.sigmaz";
        // String arquivo = "res/07 - mockiz.sigmaz";
        //   String arquivo = "res/08 - matches.sigmaz";
        //    String arquivo = "res/09 - condition.sigmaz";
        //   String arquivo = "res/10 - while.sigmaz";
        // String arquivo = "res/11 - cancel.sigmaz";
        // String arquivo = "res/12 - continue.sigmaz";
        //  String arquivo = "res/13 - step.sigmaz";
        //String arquivo = "res/14 - stepdef.sigmaz";
        //String arquivo = "res/15 - when.sigmaz";
        //  String arquivo = "res/16 - all.sigmaz";
        //String arquivo = "res/17 - test.sigmaz";
        // String arquivo = "res/18 - operations.sigmaz";
        // String arquivo = "res/19 - cast.sigmaz";
        // String arquivo = "res/20 - cast2.sigmaz";
        // String arquivo = "res/21 - struct.sigmaz";
        //  String arquivo = "res/22 - internal.sigmaz";
        String arquivo = "res/23 - oo.sigmaz";

        String saida = "res/Sigmaz.sigmad";

        Sigmaz SigmazC = new Sigmaz();

        SigmazC.init(arquivo, saida);

        TESTE_GERAL();

    }


    public static void TESTE_GERAL() {

        CSigmaz mCSigmaz = new CSigmaz();

        mCSigmaz.init();

    }

}
