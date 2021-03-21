package Make.Run;

import Sigmaz.Intellisenses.IntellisenseTheme;
import Sigmaz.S08_Utilitarios.AST;
import Sigmaz.S06_Ferramentas.SyntaxTheme;
import Sigmaz.S08_Utilitarios.Tempo;

import java.util.ArrayList;

public class RunMake {

    private AST mAST;
    private ArrayList<String> mErros;
    private String mLocal;

    private String eBuild;
    private String eSource;
    private String eIntellisenses;
    private String eHighLights;

    private IntellisenseTheme mIntellisenseTheme;
    private SyntaxTheme mSyntaxTheme;

    private boolean mObject;
    private boolean mPosProcess;
    private boolean mStackProcess;
    private boolean mAnaliseProcess;
    private boolean mIdentProcess;

    private ArrayList<String> mAutores;
    private String mVersao;
    private String mCompanhia;


    public RunMake() {
        mAST = null;
        mErros = new ArrayList<>();
        mLocal = "";

        eBuild = "";
        eSource = "";
        eIntellisenses = "";
        eHighLights = "";

        mIntellisenseTheme = new IntellisenseTheme();

        mAutores = new ArrayList<String>();
        mVersao = "";
        mCompanhia = "";

        padrao();

    }

    public void init(AST eAST) {
        mAST = eAST;

        limpar();
        padrao();

    }


    public void padrao() {
        mObject = true;
        mPosProcess = true;
        mStackProcess = true;
        mAnaliseProcess = true;
        mIdentProcess = true;
    }

    public void autor_adicionar(String eAutor) {

        mAutores.add(eAutor);

    }

    public void setVersao(String eVersao) {

        mVersao = eVersao;

    }

    public void setCompanhia(String eCompanhia) {

        mCompanhia = eCompanhia;

    }

    public ArrayList<String> getAutores() {
        return mAutores;
    }

    public String getVersao() {
        return mVersao;
    }

    public String getCompanhia() {
        return mCompanhia;
    }


    public void setObject(boolean e) {
        mObject = e;
    }

    public boolean getObject() {
        return mObject;
    }

    public void setPosProcess(boolean e) {
        mPosProcess = e;
    }

    public boolean getPosProcess() {
        return mPosProcess;
    }

    public void setStackProcess(boolean e) {
        mStackProcess = e;
    }

    public boolean getStackProcess() {
        return mStackProcess;
    }

    public void setAnalysisProcess(boolean e) {
        mAnaliseProcess = e;
    }

    public boolean getAnalysisProcess() {
        return mAnaliseProcess;
    }

    public void setIdentProcess(boolean e) {
        mIdentProcess = e;
    }

    public boolean getIdentProcess() {
        return mIdentProcess;
    }


    public String getSource() {
        return eSource;
    }

    public String getBuild() {
        return eBuild;
    }

    public String getIntellisenses() {
        return eIntellisenses;
    }

    public String getHighLights() {
        return eHighLights;
    }

    public String getLocal() {
        return mLocal;
    }

    public IntellisenseTheme getIntellienseTheme() {
        return mIntellisenseTheme;
    }

    public SyntaxTheme getSyntaxTheme() {
        return mSyntaxTheme;
    }

    public void run(String eLocal) {
        mLocal = eLocal;

        eBuild = "";
        eSource = "";
        eIntellisenses = "";

        mErros.clear();

        mIntellisenseTheme = new IntellisenseTheme();
        mSyntaxTheme = new SyntaxTheme();


        for (AST ASTCGlobal : mAST.getASTS()) {


            if (ASTCGlobal.mesmoTipo("BUILD")) {

                eBuild = ASTCGlobal.getValor();

            } else if (ASTCGlobal.mesmoTipo("SOURCE")) {

                eSource = ASTCGlobal.getValor();

            } else if (ASTCGlobal.mesmoTipo("HIGH_LIGHT")) {

                eHighLights = ASTCGlobal.getValor();

            } else if (ASTCGlobal.mesmoTipo("INTELLISENSES")) {

                eIntellisenses = ASTCGlobal.getValor();


            } else if (ASTCGlobal.mesmoTipo("STATUS")) {


                Runner_Status eRunner = new Runner_Status(this);
                eRunner.init(ASTCGlobal);

            } else if (ASTCGlobal.mesmoTipo("MAKE")) {

                Runner_Make eRunner = new Runner_Make(this);
                eRunner.init(ASTCGlobal);

            } else if (ASTCGlobal.mesmoTipo("GENERATE")) {

                Runner_Generate eRunner = new Runner_Generate(this);
                eRunner.init(ASTCGlobal);

            } else if (ASTCGlobal.mesmoTipo("OPTIONS")) {


                Runner_Options mRunner_Options = new Runner_Options(this);
                mRunner_Options.init(ASTCGlobal);

            } else if (ASTCGlobal.mesmoTipo("IDENT")) {


                Runner_Ident mRunner_Ident = new Runner_Ident(this);
                mRunner_Ident.init(ASTCGlobal);

            } else if (ASTCGlobal.mesmoTipo("TESTS")) {

                Runner_Tests mRunner_Tests = new Runner_Tests(this);
                mRunner_Tests.init(ASTCGlobal);

            } else if (ASTCGlobal.mesmoTipo("AST")) {


                Runner_AST mRunner_AST = new Runner_AST(this);
                mRunner_AST.init(ASTCGlobal);
            } else if (ASTCGlobal.mesmoTipo("SET")) {


                Runner_Set mRunner_AST = new Runner_Set(this);
                mRunner_AST.init(ASTCGlobal);

            } else if (ASTCGlobal.mesmoTipo("REMOVE")) {


                Runner_Remove mRunner_AST = new Runner_Remove(this);
                mRunner_AST.init(ASTCGlobal);


            } else {
                errar(eLocal, "Comando Desconhecido : " + ASTCGlobal.getTipo());
            }

            if (mErros.size() > 0) {
                break;
            }

        }


    }


    public void limpar() {
        mErros.clear();
        mAutores.clear();
        mVersao = "";
        mCompanhia = "";

    }

    public int getInstrucoes() {

        int ret = 0;

        ret += mAST.getInstrucoes();


        return ret;
    }

    public String getData() {

        return Tempo.getData();


    }

    public ArrayList<String> getCaminhos(AST ASTCGlobal) {

        ArrayList<String> mRet = new ArrayList<String>();

        AST AST_MODE = ASTCGlobal.getBranch("MODE");

        if (AST_MODE.mesmoValor("UNIQUE")) {

            String TempSource = getSource() + ASTCGlobal.getBranch("UNIQUE").getNome();

            mRet.add(TempSource);


        } else if (AST_MODE.mesmoValor("LIST")) {

            AST eLista = ASTCGlobal.getBranch("LIST");

            for (AST eItem : eLista.getASTS()) {

                String TempSource_Item = getSource() + eItem.getValor();

                mRet.add(TempSource_Item);

            }

        } else {

            errar(getLocal(), "Generate comando desconhecido : " + AST_MODE.getValor());

        }

        return mRet;
    }


    public void errar(String eLocal, String eMensagem) {
        getErros().add(eLocal + " -->> " + eMensagem);
    }

    public ArrayList<String> getErros() {
        return mErros;
    }

}
