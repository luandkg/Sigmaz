package Gerador;

import LuanDKG.Texto;
import Sigmaz.S00_Utilitarios.Documento;

public class BioGerador {

    String mLocalPasta;

    public BioGerador(String eLocalPasta) {

        mLocalPasta = eLocalPasta;

    }

    public void gerar() {

        criarBio();
        criarOperadores();
        criarOrganismo();
        criarMamiferos();

    }

    public void criarBio() {

        Documento mDocumento = new Documento();

        mDocumento.adicionarLinha(0, "");
        mDocumento.adicionarLinha(0, "import \"operadores.sigmaz\";");
        mDocumento.adicionarLinha(0, "import \"mamiferos.sigmaz\";");

        mDocumento.adicionarLinha(0, "");

        mDocumento.adicionarLinha(0, "define HIPER : int = 2020;");
        mDocumento.adicionarLinha(0, "define SUPER : int = 5050;");

        mDocumento.adicionarLinha(0, "");
        mDocumento.adicionarLinha(0, "refer Mamiferos;");

        mDocumento.adicionarLinha(0, "");
        mDocumento.adicionarLinha(0, "call iniciar -> { ");
        mDocumento.adicionarLinha(1, "");
        mDocumento.adicionarLinha(1, "s_1();");
        mDocumento.adicionarLinha(1, "s_2();");
        mDocumento.adicionarLinha(1, "def ALFA : int = f_1();");
        mDocumento.adicionarLinha(1, "def BETA : int = p_1(10);");

        mDocumento.adicionarLinha(1, "def Gatinho_Ana : Gato = init Gato('Ana',5);");
        mDocumento.adicionarLinha(1, "def Lobinho_Fuc : Lobo = init Lobo('Fuc',12);");
        mDocumento.adicionarLinha(1, "");
        mDocumento.adicionarLinha(1, "def Lobinho_Fuc_idade : int = Lobinho_Fuc.getIdade();");

        mDocumento.adicionarLinha(1, "");
        mDocumento.adicionarLinha(1, "Gatinho_Ana.envelhecer(3);");

        mDocumento.adicionarLinha(1, "def Gatinho_Ana_idade : int = Gatinho_Ana.getIdade();");

        mDocumento.adicionarLinha(0, "");

        mDocumento.adicionarLinha(1, "invoke __COMPILER__ -> SHOW_SCOPE ( ) :: STACK;");
        mDocumento.adicionarLinha(1, "invoke __COMPILER__ -> SHOW_REGRESSIVE ( ) :: STACK;");

        mDocumento.adicionarLinha(0, "}");



        mDocumento.adicionarLinha(0, "");


        for (int a = 1; a < 10; a++) {
            mDocumento.adicionarLinha(0, "act s_" + a + " ( ) { } ");
        }

        mDocumento.adicionarLinha(0, "");

        for (int a = 1; a < 10; a++) {

            mDocumento.adicionarLinha(0, "func f_" + a + " ( ) : int { return 12 ++ " + a + ";  } ");

        }

        mDocumento.adicionarLinha(0, "");

        for (int a = 1; a < 10; a++) {

            mDocumento.adicionarLinha(0, "func p_" + a + " ( a : int ) : int { return 12 ++ ( " + a + " ++ a );  } ");

        }

        Texto.Escrever(mLocalPasta + "bio.sigmaz", mDocumento.getConteudo());

    }

    public void criarOperadores() {

        Documento mDocumento = new Documento();

        mDocumento.adicionarLinha(0, "operator ++ ( a : int , b : int ) : int {");

        mDocumento.adicionarLinha(1, "reg @R5 -> a;");
        mDocumento.adicionarLinha(1, "reg @R6 -> b;");

        mDocumento.adicionarLinha(1, "PROC -> {");

        mDocumento.adicionarLinha(2, "SET R7;");
        mDocumento.adicionarLinha(2, "MOV 0;");
        mDocumento.adicionarLinha(2, "OPE R5 ADD R6;");

        mDocumento.adicionarLinha(1, "}");

        mDocumento.adicionarLinha(1, "return reg @R7;");
        mDocumento.adicionarLinha(0, "}");

        Texto.Escrever(mLocalPasta + "operadores.sigmaz", mDocumento.getConteudo());

    }


    public void criarOrganismo() {

        Documento mDocumento = new Documento();

        mDocumento.adicionarLinha(0, "package Vida {");

        mDocumento.adicionarLinha(1, "struct Organismo {");

        mDocumento.adicionarLinha(2, "init Organismo ( eNome : string, eIdade : int ) {");
        mDocumento.adicionarLinha(3, " nome = eNome; ");
        mDocumento.adicionarLinha(3, " idade = eIdade; ");

        mDocumento.adicionarLinha(2, "} ");

        mDocumento.adicionarLinha(2, "restrict: ");
        mDocumento.adicionarLinha(3, "define nome : string;");
        mDocumento.adicionarLinha(3, "define idade : int;");


        mDocumento.adicionarLinha(2, "all: ");
        mDocumento.adicionarLinha(3, "func getIdade() : int { return idade;}");
        mDocumento.adicionarLinha(3, "act envelhecer(eMais : int) { idade = idade++eMais;}");


        mDocumento.adicionarLinha(1, "}");


        mDocumento.adicionarLinha(0, "}");

        Texto.Escrever(mLocalPasta + "organismos.sigmaz", mDocumento.getConteudo());

    }

    public void criarMamiferos() {

        Documento mDocumento = new Documento();

        mDocumento.adicionarLinha(0, "import \"organismos.sigmaz\";");

        mDocumento.adicionarLinha(0, "package Mamiferos {");
        mDocumento.adicionarLinha(1, "refer Vida;");

        mDocumento.adicionarLinha(1, "struct Mamifero with Organismo {");
        mDocumento.adicionarLinha(2, "init Mamifero (eNome:string,eIdade:int) -> Organismo (eNome,eIdade) {} ");
        mDocumento.adicionarLinha(1, "}");

        mDocumento.adicionarLinha(0, "");

        mDocumento.adicionarLinha(1, "struct Gato with Mamifero {");
        mDocumento.adicionarLinha(2, "init Gato (eNome:string,eIdade:int) -> Mamifero (eNome,eIdade) {} ");
        mDocumento.adicionarLinha(1, "}");

        mDocumento.adicionarLinha(0, "");

        mDocumento.adicionarLinha(1, "struct Lobo with Mamifero {");
        mDocumento.adicionarLinha(2, "init Lobo (eNome:string,eIdade:int) -> Mamifero (eNome,eIdade) {} ");
        mDocumento.adicionarLinha(1, "}");

        mDocumento.adicionarLinha(0, "}");

        Texto.Escrever(mLocalPasta + "mamiferos.sigmaz", mDocumento.getConteudo());

    }

}
