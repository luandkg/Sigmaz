package Sigmaz.Intellisenses;

import java.awt.*;

public class IntellisenseTheme {

    private Color mSigmaz;
    private Color mPackage;

    private Color mFundo;
    private Color mStruct;
    private Color mModel;
    private Color mStage;
    private Color mCast;
    private Color mType;
    private Color mExternal;

    private Color mTexto;
    private Color mBox;

    public IntellisenseTheme() {

        padrao();

    }

    public void padrao() {

        mSigmaz = new Color(96, 125, 139);
        mPackage =new Color(96, 125, 139);


        mStruct = new Color(76, 175, 80);
        mModel =new Color(156, 39, 176);
        mStage =new Color(255, 193, 7);
        mCast = new Color(255, 235, 59);
        mType =new Color(0, 188, 212);
        mExternal = new Color(244, 67, 54);

        mTexto = Color.BLACK;
        mBox = Color.BLACK;
        mFundo = Color.WHITE;

    }

    public Color getFundo() { return mFundo; }
    public void setFundo(Color eCor){ mFundo=eCor; }

    public Color getPackage() { return mPackage; }
    public void setPackage(Color eCor){ mPackage=eCor; }

    public Color getStruct() { return mStruct; }
    public void setStruct(Color eCor){ mStruct=eCor; }

    public Color getModel() { return mModel; }
    public void setModel(Color eCor){ mModel=eCor; }



    public Color getStage() { return mStage; }
    public void setStage(Color eCor){ mStage=eCor; }


    public Color getCast() { return mCast; }
    public void setCast(Color eCor){ mCast=eCor; }


    public Color getType() { return mType; }
    public void setType(Color eCor){ mType=eCor; }


    public Color getExternal() { return mExternal; }
    public void setExternal(Color eCor){ mExternal=eCor; }

    public Color getTexto() { return mTexto; }
    public void setTexto(Color eCor){ mTexto=eCor; }

    public Color getBox() { return mBox; }
    public void setBox(Color eCor){ mBox=eCor; }

    public Color getSigmaz() { return mSigmaz; }
    public void setSigmaz(Color eCor){ mSigmaz=eCor; }

}
