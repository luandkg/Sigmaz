package Sigmaz.S04_Montador;

public class Chaves {

    private final int A = 0;
    private final int B = 10;
    private final int C = 20;
    private final int D = 30;
    private final int E = 40;
    private final int F = 50;
    private final int G = 60;

    public Chaves(){

    }


    public Chaveador getChave_Codigo() {
        Chaveador mChave = new Chaveador(64);

        mChave.setVarios(A, 5,200,62,85,23,6,50,2,19,70);
        mChave.setVarios(B, 150,40,120,50,23,47,13,26,19,60);
        mChave.setVarios(C, 45,130,30,100,5,43,50,7,30,75);
        mChave.setVarios(D, 13,86,14,85,54,14,80,2,19,8);
        mChave.setVarios(E, 200,10,72,85,23,62,30,20,19,200);
        mChave.setVarios(F, 64,130,49,15,13,76,32,56,90,140);
        mChave.setVarios(G, 78,50,20,16);


        return mChave;
    }

    public Chaveador getChave_Cabecalho() {

        Chaveador mChave = new Chaveador(64);

        mChave.setVarios(A, 10,50,34,85,19,96,150,200,54,20);
        mChave.setVarios(B, 5,130,62,85,23,6,50,2,19,80);
        mChave.setVarios(C, 5,130,62,85,23,6,50,2,19,80);
        mChave.setVarios(D, 5,130,62,85,23,6,50,2,19,80);
        mChave.setVarios(E, 5,130,62,85,23,6,50,2,19,80);
        mChave.setVarios(F, 5,130,62,85,23,6,50,2,19,80);
        mChave.setVarios(G, 5,130,62,85);


        return mChave;
    }

}
