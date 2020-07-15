package OA;

import java.awt.*;

public class Anotacao {

    private String mReal;
    private String mNome;
    private Color mCor;

    public Anotacao(String eReal,String eNome){
        mReal= eReal;
        mNome=eNome;
        mCor=Color.BLACK;
    }

    public Anotacao(String eReal,String eNome,Color eCor){
        mReal= eReal;
        mNome=eNome;
        mCor=eCor;
    }

    public String getReal(){
        return mReal;
    }

    public String getNome(){
        return mNome;
    }

    public Color getCor(){
        return mCor;
    }

}
