package Sigmaz.S07_Executor.Runners;

import Sigmaz.S07_Executor.Escopo;
import Sigmaz.S07_Executor.Item;
import Sigmaz.S07_Executor.RunTime;
import Sigmaz.S07_Executor.RunValueTypes.*;
import Sigmaz.S00_Utilitarios.AST;

public class Run_Value {

    private RunTime mRunTime;
    private Escopo mEscopo;

    private boolean mIsNulo;
    private boolean mIsPrimitivo;
    private boolean mIsEstrutura;

    private String mConteudo;
    private Escopo mObjeto;

    private String mRetornoTipo;

    private boolean mIsReferenciavel;
    private Item mReferencia;
    private String mLocal;

    public Run_Value(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mLocal = "Run_Value";

        mIsNulo = false;
        mIsPrimitivo = false;
        mIsEstrutura = false;

        mObjeto = null;
        mConteudo = null;
        mRetornoTipo = null;

        mIsReferenciavel = false;
        mReferencia = null;
    }


    public String getConteudo() {
        return mConteudo;
    }


    public boolean getIsNulo() {
        return mIsNulo;
    }

    public boolean getIsPrimitivo() {
        return mIsPrimitivo;
    }

    public boolean getIsStruct() {
        return !mIsPrimitivo;
    }

    public String getRetornoTipo() {
        return mRetornoTipo;
    }

    public void setNulo(boolean eNulo) {
        mIsNulo = eNulo;
    }

    public void setPrimitivo(boolean ePrimitivo) {
        mIsPrimitivo = ePrimitivo;
    }

    public void setEstrutura(boolean eEstrutura) {
        mIsEstrutura = eEstrutura;
    }


    public void setRetornoTipo(String eRetornoTipo) {
        mRetornoTipo = eRetornoTipo;
    }

    public void setConteudo(String eConteudo) {
        mConteudo = eConteudo;
    }

    public boolean getIsReferenciavel() {
        return mIsReferenciavel;
    }

    public Item getReferencia() {
        return mReferencia;
    }


    public void setReferencia(Item eReferencia) {
        mReferencia = eReferencia;
    }

    public void setIsReferenciavel(boolean eReferenciavel) {
        mIsReferenciavel = eReferenciavel;
    }


    public void init(AST ASTCorrente, String eRetorno) {

        mRetornoTipo = eRetorno;

        //  System.out.println("INIT VALUE " + ASTCorrente.getNome());

        if (mRunTime.getErros().size() > 0) {
            return;
        }

        if (ASTCorrente.mesmoValor("NULL")) {

            mIsNulo = true;
            mRetornoTipo = eRetorno;
            mIsPrimitivo = true;
            mIsEstrutura = false;


        } else if (ASTCorrente.mesmoValor("Text")) {

            mIsNulo = false;
            mIsPrimitivo = true;
            mRetornoTipo = "string";
            mConteudo = ASTCorrente.getNome();

        } else if (ASTCorrente.mesmoValor("Num")) {

            mIsNulo = false;
            mIsPrimitivo = true;
            mRetornoTipo = "int";
            mConteudo = ASTCorrente.getNome();

        } else if (ASTCorrente.mesmoValor("Float")) {

            mIsNulo = false;
            mIsPrimitivo = true;
            mRetornoTipo = "num";
            mConteudo = ASTCorrente.getNome();

        } else if (ASTCorrente.mesmoValor("ID")) {


            RunValueType_ID mRunValueType_ID = new RunValueType_ID(mRunTime,mEscopo);
            mRunValueType_ID.init(this,ASTCorrente, eRetorno);


        } else if (ASTCorrente.mesmoValor("BLOCK")) {

            init(ASTCorrente.getBranch("VALUE"), eRetorno);

        } else if (ASTCorrente.mesmoValor("OPERATOR")) {


            RunValueType_Operator mRunValueType_Operator = new RunValueType_Operator(mRunTime,mEscopo);
            mRunValueType_Operator.init(this,ASTCorrente, eRetorno);


        } else if (ASTCorrente.mesmoValor("DIRECTOR")) {

            RunValueType_Director mRunValueType_Director = new RunValueType_Director(mRunTime,mEscopo);
            mRunValueType_Director.init(this,ASTCorrente, eRetorno);


        } else if (ASTCorrente.getValor().contentEquals("FUNCT")) {


            RunValueType_Funct mRunValueType_Funct = new RunValueType_Funct(mRunTime,mEscopo);
            mRunValueType_Funct.init(this,ASTCorrente, eRetorno);


        } else if (ASTCorrente.getValor().contentEquals("TERNAL")) {

            RunValueType_Ternal mRunValueType_Ternal = new RunValueType_Ternal(mRunTime,mEscopo);
            mRunValueType_Ternal.init(this,ASTCorrente, eRetorno);


        } else if (ASTCorrente.getValor().contentEquals("STAGE")) {


            RunValueType_Stage mRunValueType_Stage = new RunValueType_Stage(mRunTime,mEscopo);
            mRunValueType_Stage.init(this,ASTCorrente, eRetorno);

        } else if (ASTCorrente.getValor().contentEquals("INIT")) {


            RunValueType_Struct mRunValueType_Struct = new RunValueType_Struct(mRunTime,mEscopo);
            mRunValueType_Struct.init(this,ASTCorrente,eRetorno);


        } else if (ASTCorrente.getValor().contentEquals("START")) {

            RunValueType_Type mRunValueType_Type = new RunValueType_Type(mRunTime,mEscopo);
            mRunValueType_Type.init(this,ASTCorrente, eRetorno);


        } else if (ASTCorrente.getValor().contentEquals("STRUCT")) {

            RunValueType_Dot mRunValueType_Dot = new RunValueType_Dot(mRunTime,mEscopo);
            mRunValueType_Dot.init(this,ASTCorrente,eRetorno);

        } else if (ASTCorrente.getValor().contentEquals("THIS")) {


            RunValueType_This mRunValueType_This = new RunValueType_This(mRunTime,mEscopo);
            mRunValueType_This.init(this,ASTCorrente,eRetorno);


        } else if (ASTCorrente.getValor().contentEquals("STRUCT_EXTERN")) {

            RunValueType_Extern mRunValueType_Extern = new RunValueType_Extern(mRunTime,mEscopo);
            mRunValueType_Extern.init(this,ASTCorrente,eRetorno);



        } else if (ASTCorrente.getValor().contentEquals("FUNCTOR")) {

            RunValueType_Functor mRunValueType_Functor = new RunValueType_Functor(mRunTime,mEscopo);
            mRunValueType_Functor.init(this,ASTCorrente,eRetorno);


        } else if (ASTCorrente.getValor().contentEquals("VECTOR")) {


            RunValueType_Vector mRunValueType_Vector = new RunValueType_Vector(mRunTime,mEscopo);
            mRunValueType_Vector.init(this,ASTCorrente,eRetorno);


        } else if (ASTCorrente.getValor().contentEquals("DEFAULT")) {


            RunValueType_Default mRunValueType_Default = new RunValueType_Default(mRunTime,mEscopo);
            mRunValueType_Default.init(this,ASTCorrente, eRetorno);


        } else if (ASTCorrente.getValor().contentEquals("EXECUTE_LOCAL")) {



            RunValueType_Local mRunValueType_Local = new RunValueType_Local(mRunTime,mEscopo);
            mRunValueType_Local.init(this,ASTCorrente, eRetorno);


        } else if (ASTCorrente.getValor().contentEquals("EXECUTE_FUNCTOR")) {

            RunValueType_Functor mRunValueType_Functor = new RunValueType_Functor(mRunTime,mEscopo);
            mRunValueType_Functor.init(this,ASTCorrente,eRetorno);


        } else if (ASTCorrente.getValor().contentEquals("MARKER")) {

            RunValueType_Marker mRunValueType_Marker = new RunValueType_Marker(mRunTime,mEscopo);
            mRunValueType_Marker.init(this,ASTCorrente,eRetorno);

        } else if (ASTCorrente.getValor().contentEquals("REG")) {

            RunValueType_Reg mRunValueType_Reg = new RunValueType_Reg(mRunTime,mEscopo);
            mRunValueType_Reg.init(this,ASTCorrente,eRetorno);


        } else {


            mRunTime.errar(mLocal, "AST_Value --> FAILED STRUCTURED  : " + ASTCorrente.getValor());


        }


        if (mRunTime.getErros().size() > 0) {
            return;
        }

      //  if (!getRetornoTipo().contains("<>")) {

         //   Run_GetType mRun_GetType = new Run_GetType(mRunTime, mEscopo);
        //    mRun_GetType.adicionarRefers(mEscopo.getRefersOcultas());
       //     mRetornoTipo = mRun_GetType.getTipagemSimples(mRetornoTipo);

      // }




    }




}
