package AppSigmaz;

import Sigmaz.Sigmaz;

public class AppSigmaz {

    public static void main(String[] args) {

       // String arquivo = "res/01 - init.sigmaz";
       // String arquivo = "res/02 - arguments.sigmaz";
       // String arquivo = "res/03 - define.sigmaz";
       // String arquivo = "res/04 - require.sigmaz";
      // String arquivo = "res/05 - function.sigmaz";
       // String arquivo = "res/06 - functions2.sigmaz";
       // String arquivo = "res/07 - mockiz.sigmaz";
       // String arquivo = "res/08 - matches.sigmaz";
       //String arquivo = "res/09 - condition.sigmaz";
        //String arquivo = "res/10 - while.sigmaz";
       // String arquivo = "res/11 - cancel.sigmaz";
       // String arquivo = "res/12 - continue.sigmaz";
      //  String arquivo = "res/13 - step.sigmaz";
        String arquivo = "res/14 - step2.sigmaz";

        String saida = "res/Sigmaz.sigmad";

        Sigmaz SigmazC = new Sigmaz();

        SigmazC.init(arquivo,saida);


    }
}
