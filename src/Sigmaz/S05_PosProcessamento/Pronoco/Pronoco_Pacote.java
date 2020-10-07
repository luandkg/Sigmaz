package Sigmaz.S05_PosProcessamento.Pronoco;

import Sigmaz.S00_Utilitarios.AST;

import java.util.ArrayList;

public class Pronoco_Pacote {

    private AST mASTPai;

    private ArrayList<Pronoco_Cast> mCasts;

    private ArrayList<Pronoco_Struct> mStructs;
    private ArrayList<Pronoco_Type> mTypes;

    private ArrayList<Pronoco_Stages> mStages;
    private ArrayList<Pronoco_Extern> mExterns;


    public Pronoco_Pacote(AST eASTPai) {

        mASTPai = eASTPai;

        mCasts = new ArrayList<Pronoco_Cast>();

        mStructs = new ArrayList<Pronoco_Struct>();
        mTypes = new ArrayList<Pronoco_Type>();

        mStages = new ArrayList<Pronoco_Stages>();
        mExterns = new ArrayList<Pronoco_Extern>();


        for (AST mAST : mASTPai.getASTS()) {

            if (mAST.mesmoTipo("REFER")) {

            } else if (mAST.mesmoTipo("CAST")) {

                Pronoco_Cast mS = new Pronoco_Cast(mAST);
                mCasts.add(mS);


            } else if (mAST.mesmoTipo("STRUCT")) {

                if (mAST.getBranch("EXTENDED").mesmoNome("TYPE")) {

                    Pronoco_Type mS = new Pronoco_Type(mAST);
                    mTypes.add(mS);



                } else if (mAST.getBranch("EXTENDED").mesmoNome("STRUCT")) {

                    Pronoco_Struct mS = new Pronoco_Struct(mAST);
                    mStructs.add(mS);


                    Pronoco_Extern mE = new Pronoco_Extern(mAST);
                    mExterns.add(mE);

                } else if (mAST.getBranch("EXTENDED").mesmoNome("STAGES")) {

                    Pronoco_Stages ms = new Pronoco_Stages(mAST);




                    mStages.add(ms);

                } else if (mAST.getBranch("EXTENDED").mesmoNome("EXTERNAL")) {

                    Pronoco_Extern mE = new Pronoco_Extern(mAST);
                    mExterns.add(mE);

                }

            }
        }

    }

    public String getNome() {
        return mASTPai.getNome();
    }

    public ArrayList<AST> getASTS() {
        return mASTPai.getASTS();
    }

    public AST getAST() {
        return mASTPai;
    }

    public ArrayList<Pronoco_Cast> getCasts() {
        return mCasts;
    }

    public ArrayList<Pronoco_Struct> getStructs() {
        return mStructs;
    }

    public ArrayList<Pronoco_Type> getTypes() {
        return mTypes;
    }

    public ArrayList<Pronoco_Stages> getStages() {
        return mStages;
    }

    public ArrayList<Pronoco_Extern> getExterns() {
        return mExterns;
    }
}
