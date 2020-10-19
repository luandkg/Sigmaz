package Gerador;

import LuanDKG.Texto;
import Sigmaz.S00_Utilitarios.Documento;

import java.util.ArrayList;

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
        criarRepteis();

    }

    public void criarBio() {

        Documento mDocumento = new Documento();

        mDocumento.adicionarLinha(0, "");
        mDocumento.adicionarLinha(0, "import \"operadores.sigmaz\";");
        mDocumento.adicionarLinha(0, "import \"mamiferos.sigmaz\";");
        mDocumento.adicionarLinha(0, "import \"repteis.sigmaz\";");

        mDocumento.adicionarLinha(0, "");

        mDocumento.adicionarLinha(0, "define HIPER : int = 2020;");
        mDocumento.adicionarLinha(0, "define SUPER : int = 5050;");

        mDocumento.adicionarLinha(0, "");
        mDocumento.adicionarLinha(0, "refer Mamiferos;");
        mDocumento.adicionarLinha(0, "refer Repteis;");

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
        mDocumento.adicionarLinha(1, "def Lobinho_Fuc_especie : string = Lobinho_Fuc.getEspecie();");

        mDocumento.adicionarLinha(1, "");
        mDocumento.adicionarLinha(1, "Gatinho_Ana.envelhecer(3);");

        mDocumento.adicionarLinha(1, "def Gatinho_Ana_idade : int = Gatinho_Ana.getIdade();");
        mDocumento.adicionarLinha(1, "def Gatinho_Ana_especie : string = Gatinho_Ana.getEspecie();");


        mDocumento.adicionarLinha(1, "def mamifero_1 : Mamifero = Gatinho_Ana;");
        mDocumento.adicionarLinha(1, "def mamifero_2 : Mamifero = Lobinho_Fuc;");

        mDocumento.adicionarLinha(1, "def Tartaruga_Carlos : Tartaruga = init Tartaruga('Carlos',112);");
        mDocumento.adicionarLinha(1, "def reptil_1 : Reptil = Tartaruga_Carlos;");


        mDocumento.adicionarLinha(0, "");

        mDocumento.adicionarLinha(1, "DEBUG -> LOCAL :: STACK;");
        mDocumento.adicionarLinha(1, "DEBUG -> REGRESSIVE :: STACK;");

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

        mDocumento.adicionarLinha(3, "define especie : string;");
        mDocumento.adicionarLinha(3, "define nome : string;");
        mDocumento.adicionarLinha(3, "define idade : int;");


        mDocumento.adicionarLinha(2, "all: ");
        mDocumento.adicionarLinha(3, "func getIdade() : int { return idade;}");
        mDocumento.adicionarLinha(3, "act envelhecer(eMais : int) { idade = idade++eMais;}");
        mDocumento.adicionarLinha(3, "func getEspecie() : string { return especie;}");


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

        ArrayList<String> mAnimais = new ArrayList<String>();
        mAnimais.add("Gato");
        mAnimais.add("Cachorro");
        mAnimais.add("Lobo");
        mAnimais.add("Rato");
        mAnimais.add("Rinoceronte");
        mAnimais.add("Golfinho");
        mAnimais.add("Baleia");
        mAnimais.add("Ramster");

        for(String mAnimal : mAnimais){

            mDocumento.adicionarLinha("");

            mDocumento.adicionarLinha(1, "struct " + mAnimal + " with Mamifero {");
            mDocumento.adicionarLinha(2, "init " + mAnimal + " (eNome:string,eIdade:int) -> Mamifero (eNome,eIdade) { ");


            mDocumento.adicionarLinha(3, "especie = " + "\"" + mAnimal + "\";");

            mDocumento.adicionarLinha(2, "} ");



            mDocumento.adicionarLinha(1, "}");



        }

        mDocumento.adicionarLinha(0, "}");


        Texto.Escrever(mLocalPasta + "mamiferos.sigmaz", mDocumento.getConteudo());

    }

    public void criarRepteis() {

        Documento mDocumento = new Documento();

        mDocumento.adicionarLinha(0, "import \"organismos.sigmaz\";");

        mDocumento.adicionarLinha(0, "package Repteis {");
        mDocumento.adicionarLinha(1, "refer Vida;");

        mDocumento.adicionarLinha(1, "struct Reptil with Organismo {");
        mDocumento.adicionarLinha(2, "init Reptil (eNome:string,eIdade:int) -> Organismo (eNome,eIdade) {} ");
        mDocumento.adicionarLinha(1, "}");

        mDocumento.adicionarLinha(0, "");

        ArrayList<String> mAnimais = new ArrayList<String>();
        mAnimais.add("Tartaruga");
        mAnimais.add("Cobra");
        mAnimais.add("Crocodilho");
        mAnimais.add("Dinossauro");


        for(String mAnimal : mAnimais){

            mDocumento.adicionarLinha("");

            mDocumento.adicionarLinha(1, "struct " + mAnimal + " with Reptil {");
            mDocumento.adicionarLinha(2, "init " + mAnimal + " (eNome:string,eIdade:int) -> Reptil (eNome,eIdade) { ");


            mDocumento.adicionarLinha(3, "especie = " + "\"" + mAnimal + "\";");

            mDocumento.adicionarLinha(2, "} ");



            mDocumento.adicionarLinha(1, "}");



        }

        mDocumento.adicionarLinha(0, "}");


        Texto.Escrever(mLocalPasta + "repteis.sigmaz", mDocumento.getConteudo());

    }


}
