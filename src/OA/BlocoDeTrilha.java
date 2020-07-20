package OA;

import java.util.ArrayList;

public class BlocoDeTrilha {

    private ArrayList<EmTrila >mGrupos;

    private int mContador ;
    private int mMaximo;

    private EmTrila mCorrente;

    public BlocoDeTrilha(int eMaximo){

        mGrupos = new ArrayList<EmTrila>();
        mMaximo=eMaximo;

        mContador=0;
        mCorrente=new EmTrila();
        mGrupos.add(mCorrente);

    }

    public ArrayList<EmTrila> getTrilhas(){
        return mGrupos;
    }

    public void adicionar(Grupo<String> eItem){

        if(mContador>=mMaximo){
            mContador=0;

            mCorrente=new EmTrila();
            mGrupos.add(mCorrente);

            mCorrente.adicionar(eItem);


        }else{

            mCorrente.adicionar(eItem);

        }

        mContador+=1;
    }
}
