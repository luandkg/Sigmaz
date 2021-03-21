package Sigmaz.S01_Compilador.C03_Parser;

public class Errador {


    public static String eraEsperadoUmaSeta() { return "Era esperado uma SETA"; }

    public static String eraEsperadoDoisPontos() {
        return "Era esperado Dois Pontos";
    }

    public static String eraEsperadoPontoEVirgula() {
        return "Era esperado PONTO E VIRGULA !";
    }

    public static String eraEsperadoTipagem() {
        return "Era esperado uma Tipagem";
    }

    public static String eraEsperadoGetESet() {
        return "Era esperado GET ou SET";
    }

    public static String eraEsperadoNomeVariavel() {
        return "Era esperado o nome da Variável !";
    }

    public static String eraEsperadoValorVariavel() {
        return "Era esperado o valor da Variável !";
    }

    public static String eraEsperadoNomeMutavel() {
        return "Era esperado o nome da Mutavel !";
    }

    public static String eraEsperadoValorMutavel() {
        return "Era esperado o valor da Mutavel !";
    }

    public static String eraEsperadoValorMutavelInferencia() { return "A definicao de uma Mutavel com inferencia de tipo dinamico precisa receber um valor de atribuicao !";}

    public static String eraEsperadoValorMutavelNaoNulo() { return "A definicao de uma Mutavel com inferencia de tipo dinamico nao pode ter o valor de atribuicao NULO !"; }

    public static String eraEsperadoNomeDaAction() {
        return "Era esperado o nome para uma ACTION !";
    }

    public static String eraEsperadoUmaActionOuFunction(){return "Era esperado o nome de uma ACTION ou FUNCTION !";}


    public static String eraEsperadoAbrirChaves() {
        return "Era esperado abrir chaves";
    }

    public static String eraEsperadoFecharChaves() {
        return "Era esperado fechar chaves ";
    }
}
