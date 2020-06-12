package AppSigmaz;

import Sigmaz.Sigmaz;

public class AppSigmaz {

    public static void main(String[] args) {

        String arquivo = "res/01 - init.sigmaz";
        String saida = "res/Sigmaz.sigmad";

        Sigmaz SigmazC = new Sigmaz();

        SigmazC.init(arquivo,saida);


    }
}
