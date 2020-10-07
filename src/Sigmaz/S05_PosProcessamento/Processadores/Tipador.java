package Sigmaz.S05_PosProcessamento.Processadores;

import java.util.ArrayList;

import Sigmaz.S06_Executor.Debuggers.Simplificador;
import Sigmaz.S05_PosProcessamento.PosProcessador;
import Sigmaz.S05_PosProcessamento.Pronoco.Pronoco;
import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S00_Utilitarios.Utilitario;

public class Tipador {

    private PosProcessador mPosProcessador;
    private ArrayList<String> mPrimitivos;

    private Simplificador mSimplificador;

    public Tipador(PosProcessador eAnalisador) {

        mSimplificador = new Simplificador();
        mPosProcessador = eAnalisador;


        mPrimitivos = new ArrayList<String>();
        mPrimitivos.add("num");
        mPrimitivos.add("string");
        mPrimitivos.add("bool");
        mPrimitivos.add("int");
        mPrimitivos.add("any");


    }

    public void init(ArrayList<AST> mASTS) {


        mensagem("");
        mensagem(" ------------------ FASE TIPADOR ----------------------- ");
        mensagem("");

        Pronoco mPronoco = new Pronoco("SIGMAZ");


        for (String e : mPrimitivos) {
            mensagem("\t - TIPO PRIMITIVO :  " + e);

            mPronoco.adicionarPrimitivo(e);
        }

        mensagem("");

        for (AST ASTCGlobal : mASTS) {

            if (ASTCGlobal.mesmoTipo("SIGMAZ")) {

                ArrayList<AST> mPacotes = new ArrayList<AST>();

                for (AST mAST : ASTCGlobal.getASTS()) {
                    if (mAST.mesmoTipo("PACKAGE")) {
                        mPacotes.add(mAST);
                    }
                }


                // chegarTipagens("\t", mAncestrais, ASTCGlobal);

                chegarTipagens_novo("\t", mPacotes, mPronoco, ASTCGlobal);

                for (AST mAST : mPacotes) {
                    mensagem("\t" + "Sigmaz Package :  " + mAST.getNome());

                    Pronoco mPronocoPacote = new Pronoco(mAST.getNome());
                    mPronocoPacote.setSuperior(mPronoco);

                    chegarTipagens_novo("\t\t", mPacotes, mPronocoPacote, mAST);
                }

            } else {

                errar("Escopo Desconhecido : " + ASTCGlobal.getTipo());

            }

        }


    }

    private void errar(String eErro) {
        mPosProcessador.getErros().add(eErro);
    }

    private void mensagem(String eMensagem) {
        mPosProcessador.mensagem(eMensagem);
    }


    public void chegarTipagens_novo(String ePrefixo, ArrayList<AST> mPacotes, Pronoco ePronoco, AST ASTPai) {


        // USAR PACKAGES
        for (AST ASTC : ASTPai.getASTS()) {
            if (ASTC.mesmoTipo("REFER")) {
                mensagem(ePrefixo + "REFERENCIANDO PACOTE " + ASTC.getNome());
                Usar(mPacotes, ePrefixo + "\t", ASTC.getNome(), ePronoco);
            }
        }

        for (AST mAST : ASTPai.getASTS()) {
            if (mAST.mesmoTipo("CAST")) {


                ePronoco.adicionarCast(mAST.getNome());

                mensagem(ePrefixo + "- Tipo CAST : " + mAST.getNome());

            } else if (mAST.mesmoTipo("STRUCT")) {


                AST mExtended = mAST.getBranch("EXTENDED");

                if (mExtended.mesmoNome("STRUCT")) {
                    mensagem(ePrefixo + "- Tipo STRUCT : " + mAST.getNome());

                    ePronoco.adicionarStruct(mAST.getNome());

                } else if (mExtended.mesmoNome("STAGES")) {
                    mensagem(ePrefixo + "- Tipo STAGE : " + mAST.getNome());

                    ePronoco.adicionarStage(mAST.getNome());
                } else if (mExtended.mesmoNome("TYPE")) {
                    mensagem(ePrefixo + "- Tipo TYPE : " + mAST.getNome());

                    ePronoco.adicionarType(mAST.getNome());

                }

            }
        }


        for (AST mAST : ASTPai.getASTS()) {


            if (mAST.mesmoTipo("DEFINE")) {

                alocacao_Define(ePrefixo, mAST, ePronoco);

            } else if (mAST.mesmoTipo("MOCKIZ")) {

                alocacao_Mockiz(ePrefixo, mAST, ePronoco);

            } else if (mAST.mesmoTipo("ACTION")) {

                alocacao_Action(ePrefixo, mAST, ePronoco);
            } else if (mAST.mesmoTipo("CALL")) {

                alocacao_Call(ePrefixo, mAST, ePronoco);
            } else if (mAST.mesmoTipo("PROTOTYPE_AUTO")) {

                alocacao_Auto(ePrefixo, mAST, ePronoco);

            } else if (mAST.mesmoTipo("FUNCTION")) {

                alocacao_Function(ePrefixo, mAST, ePronoco);

            } else if (mAST.mesmoTipo("PROTOTYPE_FUNCTOR")) {

                alocacao_Functor(ePrefixo, mAST, ePronoco);

            } else if (mAST.mesmoTipo("OPERATOR")) {

                alocacao_Operator(ePrefixo, mAST, ePronoco);

            } else if (mAST.mesmoTipo("DIRECTOR")) {

                alocacao_Director(ePrefixo, mAST, ePronoco);


            } else if (mAST.mesmoTipo("CAST")) {

            } else if (mAST.mesmoTipo("STRUCT")) {

                mensagem(ePrefixo + "STRUCT " + mAST.getNome());

                AST mExtended = mAST.getBranch("EXTENDED");

                if (mExtended.mesmoNome("STRUCT")) {

                    Pronoco mStructPronoco = new Pronoco("STRUCT " + mAST.getNome());
                    mStructPronoco.setSuperior(ePronoco);


                    if (mAST.getBranch("GENERIC").mesmoNome("TRUE")) {

                        ArrayList<String> genericos_ok = conferirGenericos(mAST.getBranch("GENERIC"), mStructPronoco);

                        if (genericos_ok.size() > 0) {
                            mensagem(ePrefixo + mAST.getNome() + " -->> TIPAGEM DE GENERICOS INVALIDA ");
                            errar(mAST.getNome() + " -->> TIPAGEM DE GENERICOS INVALIDA ");
                            for (String e : genericos_ok) {
                                mensagem(ePrefixo + "\t" + e);
                                errar(mAST.getNome() + " -->> " + e);
                            }
                        }

                    }

                    conferirCorpoStruct(ePrefixo + "\t", mAST.getBranch("BODY"), mStructPronoco);

                }


            }

        }


    }


    private void alocacao_Define(String ePrefixo, AST mAST, Pronoco mEscopo) {


        String eNome = mSimplificador.getDefine(mAST);

        Pronoco mNovoEscopo = new Pronoco(eNome);
        mNovoEscopo.setSuperior(mEscopo);

        boolean retorno_ok = conferirTipo(mAST.getBranch("TYPE"), mEscopo);

        if (retorno_ok) {

            mensagem(ePrefixo + "DEFINE " + eNome + " -->> OK ");

        } else {

            String mErro = "DEFINE " + eNome + " -->> TIPAGEM INVALIDA ";

            mensagem(ePrefixo + mErro);
            errar(mErro);
        }


    }

    private void alocacao_Mockiz(String ePrefixo, AST mAST, Pronoco mEscopo) {


        String eNome = mSimplificador.getDefine(mAST);

        Pronoco mNovoEscopo = new Pronoco(eNome);
        mNovoEscopo.setSuperior(mEscopo);

        boolean retorno_ok = conferirTipo(mAST.getBranch("TYPE"), mEscopo);

        if (retorno_ok) {

            mensagem(ePrefixo + "MOCKIZ " + eNome + " -->> OK ");

        } else {

            String mErro = "MOCKIZ " + eNome + " -->> TIPAGEM INVALIDA ";

            mensagem(ePrefixo + mErro);
            errar(mErro);
        }


    }

    public void alocacao_Call(String ePrefixo, AST mAST, Pronoco mEscopo) {

        String eNome = "CALL " + mSimplificador.getCall(mAST);

        Pronoco mNovoEscopo = new Pronoco(eNome);
        mNovoEscopo.setSuperior(mEscopo);

        mensagem(ePrefixo + eNome + " -->> OK ");
        if (mAST.mesmoValor("AUTO")) {
            conferirCorpo(ePrefixo + "\t", mAST.getBranch("BODY"), mNovoEscopo);
        }


    }

    public void alocacao_Action(String ePrefixo, AST mAST, Pronoco mEscopo) {

        String eNome = "ACTION " + mSimplificador.getAction(mAST);

        Pronoco mNovoEscopo = new Pronoco(eNome);
        mNovoEscopo.setSuperior(mEscopo);


        ArrayList<String> argumentos_ok = conferirArgumentos_novo(mAST.getBranch("ARGUMENTS"), mNovoEscopo);


        if (argumentos_ok.size() == 0) {

            mensagem(ePrefixo + eNome + " -->> OK ");

            conferirCorpo(ePrefixo + "\t", mAST.getBranch("BODY"), mNovoEscopo);

        } else {

            status_Argumento(ePrefixo, eNome, argumentos_ok);

        }


    }

    public void alocacao_Auto(String ePrefixo, AST mAST, Pronoco mEscopo) {

        String eNome = "AUTO " + mSimplificador.getAction(mAST);

        Pronoco mNovoEscopo = new Pronoco(eNome);
        mNovoEscopo.setSuperior(mEscopo);


        ArrayList<String> genericos_ok = conferirGenericos(mAST.getBranch("GENERICS"), mNovoEscopo);
        ArrayList<String> argumentos_ok = conferirArgumentos_novo(mAST.getBranch("ARGUMENTS"), mNovoEscopo);


        if (genericos_ok.size() == 0 && argumentos_ok.size() == 0) {

            mensagem(ePrefixo + eNome + " -->> OK ");

            conferirCorpo(ePrefixo + "\t", mAST.getBranch("BODY"), mNovoEscopo);

        } else {

            status_GenericoArgumento(ePrefixo, eNome, genericos_ok, argumentos_ok);

        }

    }

    public void alocacao_Function(String ePrefixo, AST mAST, Pronoco mEscopo) {

        String eNome = "FUNCTION " + mSimplificador.getFuction(mAST);

        Pronoco mNovoEscopo = new Pronoco(eNome);
        mNovoEscopo.setSuperior(mEscopo);


        ArrayList<String> argumentos_ok = conferirArgumentos_novo(mAST.getBranch("ARGUMENTS"), mNovoEscopo);
        boolean retorno_ok = conferirTipo(mAST.getBranch("TYPE"), mNovoEscopo);

        if (argumentos_ok.size() == 0 && retorno_ok) {

            mensagem(ePrefixo + eNome + " -->> OK ");

            conferirCorpo(ePrefixo + "\t", mAST.getBranch("BODY"), mNovoEscopo);

        } else {

            status_ArgumentoRetorno(ePrefixo, eNome, argumentos_ok, retorno_ok);

        }


    }

    public void alocacao_Functor(String ePrefixo, AST mAST, Pronoco mEscopo) {

        String eNome = "FUNCTOR " + mSimplificador.getFunctor(mAST);

        Pronoco mNovoEscopo = new Pronoco(eNome);
        mNovoEscopo.setSuperior(mEscopo);


        ArrayList<String> genericos_ok = conferirGenericos(mAST.getBranch("GENERICS"), mNovoEscopo);
        ArrayList<String> argumentos_ok = conferirArgumentos_novo(mAST.getBranch("ARGUMENTS"), mNovoEscopo);

        boolean retorno_ok = conferirTipo(mAST.getBranch("TYPE"), mNovoEscopo);

        if (genericos_ok.size() == 0 && argumentos_ok.size() == 0 && retorno_ok) {

            mensagem(ePrefixo + eNome + " -->> OK ");

            conferirCorpo(ePrefixo + "\t", mAST.getBranch("BODY"), mNovoEscopo);

        } else {

            status_GenericoArgumentoRetorno(ePrefixo, eNome, genericos_ok, argumentos_ok, retorno_ok);

        }


    }

    public void alocacao_Director(String ePrefixo, AST mAST, Pronoco mEscopo) {

        String eNome = "DIRECTOR " + mSimplificador.getDirector(mAST);

        Pronoco mNovoEscopo = new Pronoco(eNome);
        mNovoEscopo.setSuperior(mEscopo);


        ArrayList<String> argumentos_ok = conferirArgumentos_novo(mAST.getBranch("ARGUMENTS"), mNovoEscopo);
        boolean retorno_ok = conferirTipo(mAST.getBranch("TYPE"), mNovoEscopo);

        if (argumentos_ok.size() == 0 && retorno_ok) {

            mensagem(ePrefixo + eNome + " -->> OK ");

            conferirCorpo(ePrefixo + "\t", mAST.getBranch("BODY"), mNovoEscopo);

        } else {

            status_ArgumentoRetorno(ePrefixo, eNome, argumentos_ok, retorno_ok);

        }

    }

    public void alocacao_Operator(String ePrefixo, AST mAST, Pronoco mEscopo) {

        String eNome = "OPERATOR " + mSimplificador.getOperator(mAST);

        Pronoco mNovoEscopo = new Pronoco(eNome);
        mNovoEscopo.setSuperior(mEscopo);


        ArrayList<String> argumentos_ok = conferirArgumentos_novo(mAST.getBranch("ARGUMENTS"), mNovoEscopo);
        boolean retorno_ok = conferirTipo(mAST.getBranch("TYPE"), mNovoEscopo);

        if (argumentos_ok.size() == 0 && retorno_ok) {

            mensagem(ePrefixo + eNome + " -->> OK ");

            conferirCorpo(ePrefixo + "\t", mAST.getBranch("BODY"), mNovoEscopo);

        } else {

            status_ArgumentoRetorno(ePrefixo, eNome, argumentos_ok, retorno_ok);

        }


    }


    public void status_GenericoArgumentoRetorno(String ePrefixo, String eNome, ArrayList<String> genericos_ok, ArrayList<String> argumentos_ok, boolean retorno_ok) {

        if (genericos_ok.size() > 0) {
            mensagem(ePrefixo + eNome + " -->> TIPAGEM DE GENERICOS INVALIDA ");
            errar(eNome + " -->> TIPAGEM DE GENERICOS INVALIDA ");
            for (String e : genericos_ok) {
                mensagem(ePrefixo + "\t" + e);
                errar(eNome + " -->> " + e);
            }
        }

        if (argumentos_ok.size() > 0) {
            mensagem(ePrefixo + eNome + " -->> TIPAGEM DE ALGUMENTOS INVALIDA ");
            errar(eNome + " -->> TIPAGEM DE ALGUMENTOS INVALIDA ");
            for (String e : argumentos_ok) {
                mensagem(ePrefixo + "\t" + e);
                errar(eNome + " -->> " + e);
            }
        }


        if (!retorno_ok) {
            mensagem(ePrefixo + eNome + " -->> TIPAGEM DE RETORNO INVALIDA ");
            errar(eNome + " -->> TIPAGEM DE RETORNO INVALIDA ");
        }

    }

    public void status_GenericoArgumento(String ePrefixo, String eNome, ArrayList<String> genericos_ok, ArrayList<String> argumentos_ok) {

        if (genericos_ok.size() > 0) {
            mensagem(ePrefixo + eNome + " -->> TIPAGEM DE GENERICOS INVALIDA ");
            errar(eNome + " -->> TIPAGEM DE GENERICOS INVALIDA ");
            for (String e : genericos_ok) {
                mensagem(ePrefixo + "\t" + e);
                errar(eNome + " -->> " + e);
            }
        }

        if (argumentos_ok.size() > 0) {
            mensagem(ePrefixo + eNome + " -->> TIPAGEM DE ALGUMENTOS INVALIDA ");
            errar(eNome + " -->> TIPAGEM DE ALGUMENTOS INVALIDA ");
            for (String e : argumentos_ok) {
                mensagem(ePrefixo + "\t" + e);
                errar(eNome + " -->> " + e);
            }
        }


    }

    public void status_Argumento(String ePrefixo, String eNome, ArrayList<String> argumentos_ok) {

        if (argumentos_ok.size() > 0) {
            mensagem(ePrefixo + eNome + " -->> TIPAGEM DE ALGUMENTOS INVALIDA ");
            errar(eNome + " -->> TIPAGEM DE ALGUMENTOS INVALIDA ");
            for (String e : argumentos_ok) {
                mensagem(ePrefixo + "\t" + e);
                errar(eNome + " -->> " + e);
            }
        }

    }

    public void status_ArgumentoRetorno(String ePrefixo, String eNome, ArrayList<String> argumentos_ok, boolean retorno_ok) {


        if (argumentos_ok.size() > 0) {
            mensagem(ePrefixo + eNome + " -->> TIPAGEM DE ALGUMENTOS INVALIDA ");
            errar(eNome + " -->> TIPAGEM DE ALGUMENTOS INVALIDA ");
            for (String e : argumentos_ok) {
                mensagem(ePrefixo + "\t" + e);
                errar(eNome + " -->> " + e);
            }
        }


        if (!retorno_ok) {
            mensagem(ePrefixo + eNome + " -->> TIPAGEM DE RETORNO INVALIDA ");
            errar(eNome + " -->> TIPAGEM DE RETORNO INVALIDA ");
        }

    }


    public void Usar(ArrayList<AST> mPacotes, String ePrefixo, String eNome, Pronoco mPronoco) {


        boolean enc = false;


        for (AST ASTPackage : mPacotes) {

            if (ASTPackage.mesmoNome(eNome)) {

                for (AST mAST : ASTPackage.getASTS()) {

                    if (mAST.mesmoTipo("CAST")) {

                        mPronoco.adicionarCast(mAST.getNome());

                        mensagem(ePrefixo + "- Tipo CAST : " + ASTPackage.getNome() + "<>" + mAST.getNome());

                    } else if (mAST.mesmoTipo("STRUCT")) {


                        AST mExtended = mAST.getBranch("EXTENDED");

                        if (mExtended.mesmoNome("STRUCT")) {

                            mensagem(ePrefixo + "- Tipo STRUCT : " + ASTPackage.getNome() + "<>" + mAST.getNome());
                            mPronoco.adicionarStruct(mAST.getNome());

                        } else if (mExtended.mesmoNome("STAGES")) {
                            mensagem(ePrefixo + "- Tipo STAGE : " + ASTPackage.getNome() + "<>" + mAST.getNome());
                            mPronoco.adicionarStage(mAST.getNome());

                        } else if (mExtended.mesmoNome("TYPE")) {
                            mensagem(ePrefixo + "- Tipo TYPE : " + ASTPackage.getNome() + "<>" + mAST.getNome());
                            mPronoco.adicionarType(mAST.getNome());

                        } else if (mExtended.mesmoNome("MODEL")) {
                            mensagem(ePrefixo + "- Tipo MODEL : " + ASTPackage.getNome() + "<>" + mAST.getNome());
                            mPronoco.adicionarModel(mAST.getNome());

                        }

                    }

                }

                enc = true;
                break;
            }

        }

        if (!enc) {
            errar("Package " + eNome + " : Nao encontrado !");
        }


    }


    public ArrayList<String> conferirGenericos(AST mGenericos, Pronoco mEscopo) {

        ArrayList<String> gErros = new ArrayList<String>();

        int i = 0;
        int o = mGenericos.getASTS().size();

        ArrayList<String> mIncluir = new ArrayList<String>();

        for (AST oAST : mGenericos.getASTS()) {
            if (conferirTipo(oAST, mEscopo)) {
                gErros.add("Tipo generico " + oAST.getNome() + " : Nome invalido !");
            } else {
                i += 1;
                mIncluir.add(oAST.getNome());
            }
        }

        for (String oAST : mIncluir) {
            mEscopo.adicionarGenerico(oAST);
        }

        return gErros;
    }

    public ArrayList<String> conferirArgumentos_novo(AST mArgumetos, Pronoco mEscopo) {

        ArrayList<String> gErros = new ArrayList<String>();

        for (AST eArgumento : mArgumetos.getASTS()) {

            if (conferirTipo(eArgumento.getBranch("TYPE"), mEscopo)) {

            } else {
                gErros.add("Argumento Tipo " + eArgumento.getBranch("TYPE").getNome() + " : Deconhecido !");
                break;
            }

        }
        return gErros;
    }


    public boolean conferirTipo(AST mTipo, Pronoco mEscopo) {

        boolean ret = false;

        if (mTipo.mesmoValor("CONCRETE")) {

            if (mEscopo.getTipos_Primitivo().contains(mTipo.getNome())) {
                ret = true;
            } else if (mEscopo.getTipos_Cast().contains(mTipo.getNome())) {
                ret = true;
            } else if (mEscopo.getTipos_Stage().contains(mTipo.getNome())) {
                ret = true;
            } else if (mEscopo.getTipos_Struct().contains(mTipo.getNome())) {
                ret = true;
            } else if (mEscopo.getTipos_Type().contains(mTipo.getNome())) {
                ret = true;
            } else if (mEscopo.getTipos_Model().contains(mTipo.getNome())) {
                ret = true;
            } else if (mEscopo.getTipos_Genericos().contains(mTipo.getNome())) {
                ret = true;
            } else {
                ret = false;
            }
        } else if (mTipo.mesmoValor("GENERIC")) {


            if (mEscopo.getTipos_Primitivo().contains(mTipo.getNome())) {
                ret = true;
            } else if (mEscopo.getTipos_Cast().contains(mTipo.getNome())) {
                ret = true;
            } else if (mEscopo.getTipos_Stage().contains(mTipo.getNome())) {
                ret = true;
            } else if (mEscopo.getTipos_Struct().contains(mTipo.getNome())) {
                ret = true;
            } else if (mEscopo.getTipos_Type().contains(mTipo.getNome())) {
                ret = true;
            } else if (mEscopo.getTipos_Model().contains(mTipo.getNome())) {
                ret = true;
            } else if (mEscopo.getTipos_Genericos().contains(mTipo.getNome())) {
                ret = true;
            } else {
                ret = false;
            }

            for (AST eAST : mTipo.getASTS()) {

                if (eAST.mesmoTipo("TYPE")) {
                    ret = conferirTipo(eAST, mEscopo);
                    if (!ret) {
                        break;
                    }
                }
            }

        } else {
            ret = false;
        }

        return ret;
    }


    public void conferirCorpo(String ePrefixo, AST mCorpo, Pronoco mEscopo) {

        Utilitario mUtilitario = new Utilitario();

        for (AST mAST : mCorpo.getASTS()) {

            if (mAST.mesmoTipo("MOC")) {

                boolean retorno_ok = conferirTipo(mAST.getBranch("TYPE"), mEscopo);

                if (retorno_ok) {
                    mensagem(ePrefixo + "MOC " + mUtilitario.getDefine_Definicao(mAST) + " -->> OK ");
                } else {
                    mensagem(ePrefixo + "MOC " + mUtilitario.getDefine_Definicao(mAST) + " -->> TIPAGEM INVALIDA ");
                    errar("MOC " + mUtilitario.getDefine_Definicao(mAST) + " -->> TIPAGEM INVALIDA ");
                }

            } else if (mAST.mesmoTipo("DEF")) {

                boolean retorno_ok = conferirTipo(mAST.getBranch("TYPE"), mEscopo);

                if (retorno_ok) {
                    mensagem(ePrefixo + "DEF " + mUtilitario.getDefine_Definicao(mAST) + " -->> OK ");
                } else {
                    mensagem(ePrefixo + "DEF " + mUtilitario.getDefine_Definicao(mAST) + " -->> TIPAGEM INVALIDA ");
                    errar("DEF " + mUtilitario.getDefine_Definicao(mAST) + " -->> TIPAGEM INVALIDA ");
                }

            } else if (mAST.mesmoTipo("IF")) {

                conferirCorpo(ePrefixo, mAST.getBranch("BODY"), mEscopo);

                for (AST mSubIF : mAST.getASTS()) {
                    if (mSubIF.mesmoTipo("OTHER")) {

                        conferirCorpo(ePrefixo, mSubIF.getBranch("BODY"), mEscopo);

                    } else if (mSubIF.mesmoTipo("OTHERS")) {

                        conferirCorpo(ePrefixo, mSubIF, mEscopo);

                    }

                }


            } else if (mAST.mesmoTipo("WHILE")) {

                conferirCorpo(ePrefixo, mAST.getBranch("BODY"), mEscopo);
            } else if (mAST.mesmoTipo("STEP")) {

                conferirCorpo(ePrefixo, mAST.getBranch("BODY"), mEscopo);

            }

        }

    }

    public void conferirCorpoStruct(String ePrefixo, AST ASTPai, Pronoco mEscopo) {


        for (AST mAST : ASTPai.getASTS()) {


            if (mAST.mesmoTipo("DEFINE")) {

                alocacao_Define(ePrefixo, mAST, mEscopo);

            } else if (mAST.mesmoTipo("MOCKIZ")) {

                alocacao_Mockiz(ePrefixo, mAST, mEscopo);

            } else if (mAST.mesmoTipo("ACTION")) {

                alocacao_Action(ePrefixo, mAST, mEscopo);

            } else if (mAST.mesmoTipo("FUNCTION")) {

                alocacao_Function(ePrefixo, mAST, mEscopo);

            } else if (mAST.mesmoTipo("OPERATOR")) {

                alocacao_Operator(ePrefixo, mAST, mEscopo);

            } else if (mAST.mesmoTipo("DIRECTOR")) {

                alocacao_Director(ePrefixo, mAST, mEscopo);

            }

        }


    }

}
