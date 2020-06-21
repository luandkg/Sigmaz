package AppSigmaz;

import Sigmaz.Sigmaz;
import Sigmaz.CSigmaz;

public class AppSigmaz {

    public static void main(String[] args) {

        UM();

      //  TESTE_GERAL();

    }



    public static void UM(){

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
        //String arquivo = "res/23 - oo.sigmaz";
        //   String arquivo = "res/24 - oo2.sigmaz";
        //String arquivo = "res/25 - recursao.sigmaz";
         String arquivo = "res/26 - oorecursao.sigmaz";
        // String arquivo = "res/27 - heranca.sigmaz";

        String saida = "res/Sigmaz.sigmad";

        Sigmaz SigmazC = new Sigmaz();

        SigmazC.init(arquivo, saida);

    }

    public static void TESTE_GERAL() {

        CSigmaz mCSigmaz = new CSigmaz();


        mCSigmaz.adicionar("res/01 - init.sigmaz");
        mCSigmaz.adicionar("res/02 - arguments.sigmaz");
        mCSigmaz.adicionar("res/03 - define.sigmaz");
        mCSigmaz.adicionar("res/04 - require.sigmaz");
        mCSigmaz.adicionar("res/05 - function.sigmaz");
        mCSigmaz.adicionar("res/06 - functions2.sigmaz");
        mCSigmaz.adicionar("res/07 - mockiz.sigmaz");
        mCSigmaz.adicionar("res/08 - matches.sigmaz");
        mCSigmaz.adicionar("res/09 - condition.sigmaz");
        mCSigmaz.adicionar("res/10 - while.sigmaz");
        mCSigmaz.adicionar("res/11 - cancel.sigmaz");
        mCSigmaz.adicionar("res/12 - continue.sigmaz");
        mCSigmaz.adicionar("res/13 - step.sigmaz");
        mCSigmaz.adicionar("res/14 - stepdef.sigmaz");
        mCSigmaz.adicionar("res/15 - when.sigmaz");
        mCSigmaz.adicionar("res/16 - all.sigmaz");
        mCSigmaz.adicionar("res/17 - test.sigmaz");
        mCSigmaz.adicionar("res/18 - operations.sigmaz");
        mCSigmaz.adicionar("res/19 - cast.sigmaz");
        mCSigmaz.adicionar("res/20 - cast2.sigmaz");
        mCSigmaz.adicionar("res/21 - struct.sigmaz");
        mCSigmaz.adicionar("res/22 - internal.sigmaz");
        mCSigmaz.adicionar("res/23 - oo.sigmaz");
        mCSigmaz.adicionar("res/24 - oo2.sigmaz");
        mCSigmaz.adicionar("res/25 - recursao.sigmaz");
        mCSigmaz.adicionar("res/26 - oorecursao.sigmaz");
        mCSigmaz.adicionar("res/27 - heranca.sigmaz");



        mCSigmaz.init();

    }

}
