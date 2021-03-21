package AppSigmaz;

import Sigmaz.S08_Utilitarios.Documento;

public class CodigoSigmaz {


    public String getCodigo() {

        Documento eCodigo = new Documento();

        eCodigo.adicionarLinha(" require lib;");

        eCodigo.adicionarLinha("call inicio ->  {");
        eCodigo.adicionarLinha("    def valor : int = 11; ");
        eCodigo.adicionarLinha("    DEBUG -> LOCAL :: STACK  ;");
        eCodigo.adicionarLinha("    passando(valor); ");

        eCodigo.adicionarLinha("    def m5 : int = v5(); ");
        eCodigo.adicionarLinha("    def c5 : Caixa = c5(); ");

        eCodigo.adicionarLinha("    vaz30(Caixa->A); ");

        eCodigo.adicionarLinha("    def r5 : int = c5.getA(); ");


        eCodigo.adicionarLinha("    DEBUG -> LOCAL :: STACK  ;");
        eCodigo.adicionarLinha(" }");


        eCodigo.adicionarLinha("act passando ( arg : int) {");

        eCodigo.adicionarLinha("    def a : int = arg ++ 2; ");
        eCodigo.adicionarLinha("    def e : int = arg -- 2; ");
        eCodigo.adicionarLinha("    def v : int = arg ** 2; ");
        eCodigo.adicionarLinha("    def d : int = arg // 2; ");

        eCodigo.adicionarLinha("    DEBUG -> LOCAL :: STACK  ;");

        eCodigo.adicionarLinha("    arg = 5; ");


        eCodigo.adicionarLinha(" }");

        eCodigo.adicionarLinha("func v5 () : int  {");
        eCodigo.adicionarLinha("   return Caixa->A; ");
        eCodigo.adicionarLinha(" }");

        eCodigo.adicionarLinha("func c5 () : Caixa  {");
        eCodigo.adicionarLinha("   return init Caixa(); ");
        eCodigo.adicionarLinha(" }");

        eCodigo.adicionarLinha("struct Caixa  {");
        eCodigo.adicionarLinha("   explicit: ");
        eCodigo.adicionarLinha("        define A : int = 5; ");
        eCodigo.adicionarLinha("   all: ");
        eCodigo.adicionarLinha("        func getA () : int { return Caixa->A;}");

        eCodigo.adicionarLinha(" }");

        eCodigo.adicionarLinha("act vaz30 ( ref arg : int) { arg = 30; } ");


        return eCodigo.getConteudo();
    }


}
