package Sigmaz.S00_Utilitarios;

public class SemanticaPortugues {

    public static String getConcordancia(int e,String eSingular,String ePlural){
        if (e==0){
            return ePlural;
        }else if(e==1){
            return eSingular;
        }else{
            return ePlural;
        }
    }

}
