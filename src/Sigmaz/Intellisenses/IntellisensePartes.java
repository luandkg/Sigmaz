package Sigmaz.Intellisenses;

import Sigmaz.S00_Utilitarios.Visualizador.*;

import java.awt.*;

public class IntellisensePartes {

    private Intellisense mIntellisense;
    private IntellisenseTheme mIntellisenseTheme;

    public IntellisensePartes(Intellisense eIntellisense, IntellisenseTheme eIntellisenseTheme) {
        mIntellisense = eIntellisense;
        mIntellisenseTheme = eIntellisenseTheme;
    }

    public int cast_continuar(Graphics g, SigmazCast eSigmazCast, int x, int mais, int eLargura, int altura) {

        mIntellisense.criarFundo(g, mIntellisenseTheme.getFundo(), x, mais, mIntellisense.getLargura(), altura - mais);

        mais = mIntellisense.criarCaixaTitulo(g, x, mais, mIntellisenseTheme.getCast());

        mais = mIntellisense.titulo(g, x, mais, eLargura, eSigmazCast.getNome());


        for (SigmazGetter Sub2 : eSigmazCast.getGetters()) {
            mais = mIntellisense.leftString_Normal(g, x + 30, mais, eLargura, Sub2.getDefinicao(), mIntellisense.IMG_GETTER);
        }


        for (SigmazSetter Sub2 : eSigmazCast.getSetters()) {
            mais = mIntellisense.leftString_Normal(g, x + 30, mais, eLargura, Sub2.getDefinicao(), mIntellisense.IMG_SETTER);
        }

        mais += 50;

        return mais;
    }


    public int stages_continuar(Graphics g, SigmazStages eSigmazStage, int x, int mais, int eLargura, int altura) {

        mIntellisense.criarFundo(g, mIntellisenseTheme.getFundo(), x, mais, mIntellisense.getLargura(), altura - mais);

        mais = mIntellisense.criarCaixaTitulo(g, x, mais, mIntellisenseTheme.getStage());

        g.setColor(Color.BLACK);

        mais = mIntellisense.titulo(g, x, mais, eLargura, eSigmazStage.getNome());


        for (String mStage : eSigmazStage.getStages()) {

            mais = mIntellisense.leftString_Normal(g, x + 30, mais, eLargura, mStage, mIntellisense.IMG_STAGE);

        }


        mais = mIntellisense.criarBox(g, mais, x);


        g.setColor(Color.BLACK);


        for (SigmazAction Sub2 : eSigmazStage.getAction()) {
            mais = mIntellisense.algum(g, x + 30, mais, Sub2.getDefinicao(), mIntellisense.IMG_ACTION_EXPLICIT);
        }

        for (SigmazFunction Sub2 : eSigmazStage.getFunctions()) {
            mais = mIntellisense.algum(g, x + 30, mais, Sub2.getDefinicao(), mIntellisense.IMG_FUNCTION_EXPLICIT);
        }

        if (eSigmazStage.temActionsFunctions()) {
            mais = mIntellisense.criarBox(g, mais, x);
        }


        for (SigmazOperator Sub2 : eSigmazStage.getOperators()) {
            mais = mIntellisense.algum(g, x + 30, mais, Sub2.getDefinicao(), mIntellisense.IMG_OPERATOR);
        }


        mais += 50;


        return mais;

    }

    public int type_continuar(Graphics g, SigmazType eSigmazType, int x, int mais, int eLargura, int altura) {

        mIntellisense.criarFundo(g, mIntellisenseTheme.getFundo(), x, mais, mIntellisense.getLargura(), altura - mais);

        mais = mIntellisense.criarCaixaTitulo(g, x, mais, mIntellisenseTheme.getType());

        g.setColor(Color.BLACK);

        mais = mIntellisense.titulo(g, x, mais, eLargura, eSigmazType.getNome());


        if (eSigmazType.isGenerica()) {

            for (String eGenerico : eSigmazType.getGenericos()) {
                mais = mIntellisense.leftString_Normal(g, x + 30, mais, eLargura, eGenerico, mIntellisense.IMG_GENERIC_TYPE);
            }

            mais = mIntellisense.criarBox(g, mais, x);


        }


        if (eSigmazType.temBase()) {

            for (String eBase : eSigmazType.getBases()) {
                mais = mIntellisense.leftString_Normal(g, x + 30, mais, eLargura, eBase, mIntellisense.IMG_BASE);
            }

            mais = mIntellisense.criarBox(g, mais, x);


        }


        for (SigmazDefine eDefine : eSigmazType.getDefines()) {
            mais = mIntellisense.algum(g, x + 30, mais, eDefine.getDefinicao(), mIntellisense.IMG_DEFINE_TYPE);
        }


        for (SigmazMockiz eMockiz : eSigmazType.getMockizes()) {
            mais = mIntellisense.algum(g, x + 30, mais, eMockiz.getDefinicao(), mIntellisense.IMG_MOCKIZ_TYPE);
        }

        mais += 50;

        return mais;

    }

    public int external_continuar(Graphics g, SigmazExternal eSigmazExternal, int x, int mais, int eLargura, int altura) {


        Color eBarra = mIntellisenseTheme.getExternal();

        mIntellisense.criarFundo(g, mIntellisenseTheme.getFundo(), x, mais, mIntellisense.getLargura(), altura - mais);

        mais = mIntellisense.criarCaixaTitulo(g, x, mais, eBarra);

        g.setColor(Color.BLACK);

        mais = mIntellisense.titulo(g, x, mais, eLargura, eSigmazExternal.getNome());

        g.setColor(Color.BLACK);


        for (SigmazDefine eDefine : eSigmazExternal.getDefines()) {

            if (eDefine.isExplicit()) {
                mais = mIntellisense.algum(g, x + 30, mais, eDefine.getDefinicao(), mIntellisense.IMG_DEFINE_EXPLICIT);
            }

        }

        for (SigmazDefine eDefine : eSigmazExternal.getDefines()) {

            if (eDefine.isImplicit()) {
                mais = mIntellisense.algum(g, x + 30, mais, eDefine.getDefinicao(), mIntellisense.IMG_DEFINE_IMPLICIT);
            }

        }


        for (SigmazMockiz eMockiz : eSigmazExternal.getMockizes()) {

            if (eMockiz.isExplicit()) {
                mais = mIntellisense.algum(g, x + 30, mais, eMockiz.getDefinicao(), mIntellisense.IMG_MOCKIZ_EXPLICIT);
            }

        }

        for (SigmazMockiz eMockiz : eSigmazExternal.getMockizes()) {

            if (eMockiz.isImplicit()) {
                mais = mIntellisense.algum(g, x + 30, mais, eMockiz.getDefinicao(), mIntellisense.IMG_MOCKIZ_IMPLICIT);
            }

        }


        if (mIntellisense.deveSeparar(eSigmazExternal.temActionsOuFunctions(), eSigmazExternal.temDefinesOuMockizes())) {

            mais = mIntellisense.criarBox(g, mais, x);

        }


        for (SigmazAction eAction : eSigmazExternal.getAction()) {

            if (eAction.isExplicit()) {
                mais = mIntellisense.algum(g, x + 30, mais, eAction.getDefinicao(), mIntellisense.IMG_ACTION_EXPLICIT);
            }

        }

        for (SigmazAction eAction : eSigmazExternal.getAction()) {

            if (eAction.isImplicit()) {
                mais = mIntellisense.algum(g, x + 30, mais, eAction.getDefinicao(), mIntellisense.IMG_ACTION_IMPLICIT);
            }

        }


        for (SigmazFunction eFunction : eSigmazExternal.getFunctions()) {

            if (eFunction.isExplicit()) {
                mais = mIntellisense.algum(g, x + 30, mais, eFunction.getDefinicao(), mIntellisense.IMG_FUNCTION_EXPLICIT);
            }

        }
        for (SigmazFunction eFunction : eSigmazExternal.getFunctions()) {

            if (eFunction.isImplicit()) {
                mais = mIntellisense.algum(g, x + 30, mais, eFunction.getDefinicao(), mIntellisense.IMG_FUNCTION_IMPLICIT);
            }

        }


        return mais;

    }

    public int model_continuar(Graphics g, SigmazModel eSigmazModel, int x, int mais, int eLargura, int altura) {

        mIntellisense.criarFundo(g, mIntellisenseTheme.getFundo(), x, mais, mIntellisense.getLargura(), altura - mais);

        mais = mIntellisense.criarCaixaTitulo(g, x, mais, mIntellisenseTheme.getModel());
        mais = mIntellisense.titulo(g, x, mais, eLargura, eSigmazModel.getNome());


        if (eSigmazModel.temGenericos()) {

            for (String eGenerico : eSigmazModel.getGenericos()) {
                mIntellisense.leftString_Normal(g, x+30, mais, eLargura, eGenerico, mIntellisense.IMG_GENERIC_TYPE);
            }

            if (eSigmazModel.temDefinesMockizes()) {
                mais = mIntellisense.criarBox(g, mais, x);
            } else if (eSigmazModel.temActionsFunctions()) {
                mais = mIntellisense.criarBox(g, mais, x);
            }

        }


        if (eSigmazModel.temDefinesMockizes()) {

            for (SigmazDefine Sub2 : eSigmazModel.getDefines()) {
                mais = mIntellisense.algum(g, x + 30, mais, Sub2.getDefinicao(), mIntellisense.IMG_DEFINE_ALL);
            }

            for (SigmazMockiz Sub2 : eSigmazModel.getMockizes()) {
                mais = mIntellisense.algum(g, x + 30, mais, Sub2.getDefinicao(), mIntellisense.IMG_MOCKIZ_ALL);
            }

        }


        if (eSigmazModel.temActionsFunctions()) {
            mais = mIntellisense.criarBox(g, mais, x);
        }


        for (SigmazAction Sub2 : eSigmazModel.getActions()) {
            mais = mIntellisense.algum(g, x + 30, mais, Sub2.getDefinicao(), mIntellisense.IMG_ACTION_ALL);
        }


        for (SigmazFunction Sub2 : eSigmazModel.getFunctions()) {
            mais = mIntellisense.algum(g, x + 30, mais, Sub2.getDefinicao(), mIntellisense.IMG_FUNCTION_ALL);
        }


        mais += 50;

        return mais;
    }


    public int struct_continuar(Graphics g, SigmazStruct eSigmazStruct, int x, int mais, int eLargura, int altura) {

        mIntellisense.criarFundo(g, mIntellisenseTheme.getFundo(), x, mais, mIntellisense.getLargura(), altura - mais);

        mais = mIntellisense.criarCaixaTitulo(g, x, mais, mIntellisenseTheme.getStruct());

        mais = mIntellisense.titulo(g, x, mais, eLargura, eSigmazStruct.getNome());


        if (eSigmazStruct.temModelo()) {

            mais = mIntellisense.leftString_Normal(g, x + 30, mais, eLargura, eSigmazStruct.getModelo(), mIntellisense.IMG_MODEL);
            mais = mIntellisense.criarBox(g, mais, x);

        }

        if (eSigmazStruct.temHeranca()) {

            for (String eBase : eSigmazStruct.getBases()) {
                mais = mIntellisense.leftString_Normal(g, x + 30, mais, eLargura, eBase, mIntellisense.IMG_BASE);
            }

            mais = mIntellisense.criarBox(g, mais, x);

        }

        if (eSigmazStruct.temGenericos()) {

            for (String eGenerico : eSigmazStruct.getGenericos()) {
                mais = mIntellisense.leftString_Normal(g, x + 30, mais, eLargura, eGenerico, mIntellisense.IMG_GENERIC_TYPE);
            }

            mais = mIntellisense.criarBox(g, mais, x);

        }


        for (SigmazInit Sub2 : eSigmazStruct.getInits()) {
            mais = mIntellisense.leftString_Normal(g, x + 30, mais, eLargura, Sub2.getDefinicao(), mIntellisense.IMG_INIT);
        }

        if (eSigmazStruct.temInits() && eSigmazStruct.temCorpo()) {
            mais = mIntellisense.criarBox(g, mais, x);
        }


        mais = definesMockizes_internos(g, x, mais, eLargura, eSigmazStruct);


        if (mIntellisense.deveSeparar(eSigmazStruct.temDefinesOuMockizes_Internos(), eSigmazStruct.temActionsOuFunctions_Internos())) {

            mais = mIntellisense.criarBox(g, mais, x);

        } else if (mIntellisense.deveSeparar(eSigmazStruct.temDefinesOuMockizes_Internos(), eSigmazStruct.temDefinesOuMockizes_Externos())) {

            mais = mIntellisense.criarBox(g, mais, x);

        } else if (mIntellisense.deveSeparar(eSigmazStruct.temDefinesOuMockizes_Internos(), eSigmazStruct.temActionsOuFunctions_Externos())) {

            mais = mIntellisense.criarBox(g, mais, x);

        } else if (mIntellisense.deveSeparar(eSigmazStruct.temDefinesOuMockizes_Internos(), eSigmazStruct.temDirectorsOuOperators())) {

            mais = mIntellisense.criarBox(g, mais, x);

        }

        mais = actionsFunctions_internos(g, x, mais, eLargura, eSigmazStruct);


        if (mIntellisense.deveSeparar(eSigmazStruct.temActionsOuFunctions_Internos(), eSigmazStruct.temDefinesOuMockizes_Externos())) {

            mais = mIntellisense.criarBox(g, mais, x);

        } else if (mIntellisense.deveSeparar(eSigmazStruct.temActionsOuFunctions_Internos(), eSigmazStruct.temActionsOuFunctions_Externos())) {

            mais = mIntellisense.criarBox(g, mais, x);

        } else if (mIntellisense.deveSeparar(eSigmazStruct.temActionsOuFunctions_Internos(), eSigmazStruct.temDirectorsOuOperators())) {

            mais = mIntellisense.criarBox(g, mais, x);

        }

        mais = definesMockizes_externs(g, x, mais, eLargura, eSigmazStruct);


        if (mIntellisense.deveSeparar(eSigmazStruct.temDefinesOuMockizes_Externos(), eSigmazStruct.temActionsOuFunctions_Externos())) {
            mais = mIntellisense.criarBox(g, mais, x);
        } else if (mIntellisense.deveSeparar(eSigmazStruct.temDefinesOuMockizes_Externos(), eSigmazStruct.temDirectorsOuOperators())) {
            mais = mIntellisense.criarBox(g, mais, x);
        } else if (mIntellisense.deveSeparar(eSigmazStruct.temDefinesOuMockizes_Externos(), eSigmazStruct.temGettersOuSetters())) {
            mais = mIntellisense.criarBox(g, mais, x);
        }


        mais = actionsFunctions_externs(g, x, mais, eLargura, eSigmazStruct);


        if (mIntellisense.deveSeparar(eSigmazStruct.temActionsOuFunctions_Externos(), eSigmazStruct.temDirectorsOuOperators())) {
            mais = mIntellisense.criarBox(g, mais, x);
        } else if (mIntellisense.deveSeparar(eSigmazStruct.temActionsOuFunctions_Externos(), eSigmazStruct.temGettersOuSetters())) {
            mais = mIntellisense.criarBox(g, mais, x);
        }

        mais = extras(g, x, mais, eLargura, eSigmazStruct);

        mais = blocos(g, x, mais, eLargura, eSigmazStruct);

        mais += 50;

        return mais;

    }

    public int definesMockizes_internos(Graphics g, int x, int mais, int eLargura, SigmazStruct eSigmazStruct) {

        int p = 0;


        for (SigmazDefine eDefine : eSigmazStruct.getDefines()) {

            if (eDefine.isAll()) {
                mais = mIntellisense.algum(g, x + 30, mais, eDefine.getDefinicao(), mIntellisense.IMG_DEFINE_ALL);
                p += 1;
            }

        }

        for (SigmazDefine eDefine : eSigmazStruct.getDefines()) {

            if (eDefine.isRestrict()) {
                mais = mIntellisense.algum(g, x + 30, mais, eDefine.getDefinicao(), mIntellisense.IMG_DEFINE_RESTRICT);
                p += 1;
            }

        }


        for (SigmazMockiz eMockiz : eSigmazStruct.getMockizes()) {

            if (eMockiz.isAll()) {
                mais = mIntellisense.algum(g, x + 30, mais, eMockiz.getDefinicao(), mIntellisense.IMG_MOCKIZ_ALL);
                p += 1;
            }

        }

        for (SigmazMockiz eMockiz : eSigmazStruct.getMockizes()) {

            if (eMockiz.isRestrict()) {
                mais = mIntellisense.algum(g, x + 30, mais, eMockiz.getDefinicao(), mIntellisense.IMG_MOCKIZ_RESTRICT);

                p += 1;
            }

        }

        return mais;
    }

    public int actionsFunctions_internos(Graphics g, int x, int mais, int eLargura, SigmazStruct eSigmazStruct) {


        for (SigmazAction eAction : eSigmazStruct.getAction()) {

            if (eAction.isAll()) {
                mais = mIntellisense.algum(g, x + 30, mais, eAction.getDefinicao(), mIntellisense.IMG_ACTION_ALL);
            }

        }

        for (SigmazAction eAction : eSigmazStruct.getAction()) {

            if (eAction.isRestrict()) {
                mais = mIntellisense.algum(g, x + 30, mais, eAction.getDefinicao(), mIntellisense.IMG_ACTION_RESTRICT);
            }

        }


        for (SigmazFunction eFunction : eSigmazStruct.getFunctions()) {

            if (eFunction.isAll()) {
                mais = mIntellisense.algum(g, x + 30, mais, eFunction.getDefinicao(), mIntellisense.IMG_FUNCTION_ALL);
            }

        }
        for (SigmazFunction eFunction : eSigmazStruct.getFunctions()) {

            if (eFunction.isRestrict()) {
                mais = mIntellisense.algum(g, x + 30, mais, eFunction.getDefinicao(), mIntellisense.IMG_FUNCTION_RESTRICT);
            }

        }

        return mais;
    }

    public int actionsFunctions_externs(Graphics g, int x, int mais, int eLargura, SigmazStruct eSigmazStruct) {

        int p = 0;

        if (p > 0) {
            mais += 70;

            g.setColor(Color.BLACK);
            g.fillRect(x + 50, mais, eLargura / 2, 15);


        }

        p = 0;

        for (SigmazAction eAction : eSigmazStruct.getAction()) {

            if (eAction.isExplicit()) {
                mais = mIntellisense.algum(g, x + 30, mais, eAction.getDefinicao(), mIntellisense.IMG_ACTION_EXPLICIT);
                p += 1;
            }

        }

        for (SigmazAction eAction : eSigmazStruct.getAction()) {

            if (eAction.isImplicit()) {
                mais = mIntellisense.algum(g, x + 30, mais, eAction.getDefinicao(), mIntellisense.IMG_ACTION_IMPLICIT);
                p += 1;
            }

        }

        for (SigmazFunction eFunction : eSigmazStruct.getFunctions()) {

            if (eFunction.isExplicit()) {
                mais = mIntellisense.algum(g, x + 30, mais, eFunction.getDefinicao(), mIntellisense.IMG_FUNCTION_EXPLICIT);
                p += 1;
            }

        }

        for (SigmazFunction eFunction : eSigmazStruct.getFunctions()) {

            if (eFunction.isImplicit()) {
                mais = mIntellisense.algum(g, x + 30, mais, eFunction.getDefinicao(), mIntellisense.IMG_FUNCTION_IMPLICIT);
                p += 1;
            }

        }

        return mais;
    }

    public int definesMockizes_externs(Graphics g, int x, int mais, int eLargura, SigmazStruct eSigmazStruct) {

        int p = 0;

        for (SigmazDefine eDefine : eSigmazStruct.getDefines()) {

            if (eDefine.isImplicit()) {
                mais = mIntellisense.algum(g, x + 30, mais, eDefine.getDefinicao(), mIntellisense.IMG_DEFINE_IMPLICIT);
                p += 1;
            }

        }

        for (SigmazDefine eDefine : eSigmazStruct.getDefines()) {

            if (eDefine.isExplicit()) {
                mais = mIntellisense.algum(g, x + 30, mais, eDefine.getDefinicao(), mIntellisense.IMG_DEFINE_EXPLICIT);
                p += 1;
            }

        }


        for (SigmazMockiz eMockiz : eSigmazStruct.getMockizes()) {

            if (eMockiz.isImplicit()) {
                mais = mIntellisense.algum(g, x + 30, mais, eMockiz.getDefinicao(), mIntellisense.IMG_MOCKIZ_IMPLICIT);
                p += 1;
            }

        }

        for (SigmazMockiz eMockiz : eSigmazStruct.getMockizes()) {

            if (eMockiz.isExplicit()) {
                mais = mIntellisense.algum(g, x + 30, mais, eMockiz.getDefinicao(), mIntellisense.IMG_MOCKIZ_EXPLICIT);
                p += 1;
            }

        }


        return mais;
    }

    public int extras(Graphics g, int x, int mais, int eLargura, SigmazStruct eSigmazStruct) {

        int p = 0;


        for (SigmazDirector eDirector : eSigmazStruct.getDiretores()) {

            mais = mIntellisense.algum(g, x + 30, mais, eDirector.getDefinicao(), mIntellisense.IMG_DIRECTOR);
            p += 1;


        }

        for (SigmazOperator eOperator : eSigmazStruct.getOperadores()) {

            mais = mIntellisense.algum(g, x + 30, mais, eOperator.getDefinicao(), mIntellisense.IMG_OPERATOR);
            p += 1;


        }

        return mais;

    }

    public int blocos(Graphics g, int x, int mais, int eLargura, SigmazStruct eSigmazStruct) {

        int p = 0;


        for (SigmazBlocoGetter eGetter : eSigmazStruct.getBlocoGetters()) {

            mais = mIntellisense.algum(g, x + 30, mais, eGetter.getDefinicao(), mIntellisense.IMG_BLOCO_GETTER);
            p += 1;


        }


        for (SigmazBlocoSetter eSetter : eSigmazStruct.getBlocoSetters()) {

            mais = mIntellisense.algum(g, x + 30, mais, eSetter.getDefinicao(), mIntellisense.IMG_BLOCO_SETTER);
            p += 1;


        }

        return mais;

    }

}
