package Sigmaz.Analisador.ASTS;

import Sigmaz.Analisador.Analisador;
import Sigmaz.Analisador.Analisador_Bloco;
import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Analisar_Action {

    private Analisador mAnalisador;

    private ArrayList<String> mAlocadoAqui ;

    private Analisador_Bloco mAnalisador_Bloco;

    public Analisar_Action(Analisador eAnalisador, Analisador_Bloco eAnalisador_Bloco) {

        mAnalisador = eAnalisador;

        mAlocadoAqui= new ArrayList<String>();
        mAnalisador_Bloco = eAnalisador_Bloco;

    }


    public void analisarDentroAction(AST ASTPai, ArrayList<String> mAlocadosAntes, boolean laco) {


        if (ASTPai.mesmoTipo("DEF")) {

            if (mAlocadoAqui.contains(ASTPai.getNome())) {
                mAnalisador.getErros().add("Alocacao : " + ASTPai.getNome() + " : Duplicada !");
            }else{
                mAlocadoAqui.add(ASTPai.getNome());
            }


            mAlocadosAntes.add(ASTPai.getNome());

            if (mAnalisador.getProibidos().contains(ASTPai.getNome())) {
                mAnalisador.getErros().add("Def : " + ASTPai.getNome() + " : Nome Proibido !");
            }

            if (ASTPai.getBranch("TYPE").mesmoNome("auto")) {
                mAnalisador_Bloco.getAnalisar_Outros().getActions_ApenasNomes().add(ASTPai.getNome());
            } else if (ASTPai.getBranch("TYPE").mesmoNome("functor")) {
                mAnalisador_Bloco.getAnalisar_Outros().getFunctions_ApenasNomes().add(ASTPai.getNome());
            }

            mAnalisador.analisarAlocacao(ASTPai, mAlocadosAntes);
            mAnalisador_Bloco.analisarValoracao(ASTPai, mAlocadosAntes);

            mAnalisador_Bloco.getAnalisar_Outros().analisarTipagem(ASTPai);
        } else if (ASTPai.mesmoTipo("MOC")) {

            if (mAlocadoAqui.contains(ASTPai.getNome())) {
                mAnalisador.getErros().add("Alocacao : " + ASTPai.getNome() + " : Duplicada !");
            } else {
                mAlocadoAqui.add(ASTPai.getNome());
            }

            mAlocadosAntes.add(ASTPai.getNome());

            if (mAnalisador.getProibidos().contains(ASTPai.getNome())) {
                mAnalisador.getErros().add("Moc : " + ASTPai.getNome() + " : Nome Proibido !");
            }

            if (ASTPai.getBranch("TYPE").mesmoNome("auto")) {
                mAnalisador_Bloco.getAnalisar_Outros().getActions_ApenasNomes().add(ASTPai.getNome());
            } else if (ASTPai.getBranch("TYPE").mesmoNome("functor")) {
                mAnalisador_Bloco.getAnalisar_Outros().getFunctions_ApenasNomes().add(ASTPai.getNome());
            }

            mAnalisador.analisarAlocacao(ASTPai, mAlocadosAntes);
            mAnalisador_Bloco.analisarValoracao(ASTPai, mAlocadosAntes);

            mAnalisador_Bloco.getAnalisar_Outros().analisarTipagem(ASTPai);

        } else    if (ASTPai.mesmoTipo("LET")) {

            if (mAlocadoAqui.contains(ASTPai.getNome())) {
                mAnalisador.getErros().add("Alocacao : " + ASTPai.getNome() + " : Duplicada !");
            }else{
                mAlocadoAqui.add(ASTPai.getNome());
            }


            mAlocadosAntes.add(ASTPai.getNome());

            if (mAnalisador.getProibidos().contains(ASTPai.getNome())) {
                mAnalisador.getErros().add("Let : " + ASTPai.getNome() + " : Nome Proibido !");
            }


            mAnalisador.analisarAlocacao(ASTPai, mAlocadosAntes);
            mAnalisador_Bloco.analisarValoracao(ASTPai, mAlocadosAntes);

            mAnalisador_Bloco.getAnalisar_Outros().analisarTipagem(ASTPai);
        } else    if (ASTPai.mesmoTipo("VOZ")) {

            if (mAlocadoAqui.contains(ASTPai.getNome())) {
                mAnalisador.getErros().add("Alocacao : " + ASTPai.getNome() + " : Duplicada !");
            }else{
                mAlocadoAqui.add(ASTPai.getNome());
            }


            mAlocadosAntes.add(ASTPai.getNome());

            if (mAnalisador.getProibidos().contains(ASTPai.getNome())) {
                mAnalisador.getErros().add("Let : " + ASTPai.getNome() + " : Nome Proibido !");
            }


            mAnalisador.analisarAlocacao(ASTPai, mAlocadosAntes);
            mAnalisador_Bloco.analisarValoracao(ASTPai, mAlocadosAntes);

            mAnalisador_Bloco.getAnalisar_Outros().analisarTipagem(ASTPai);
        } else if (ASTPai.mesmoTipo("INVOKE")) {
        } else if (ASTPai.mesmoTipo("APPLY")) {

            mAnalisador_Bloco.getAnalisar_Apply().analisar_Apply(ASTPai, mAlocadosAntes);

        } else if (ASTPai.mesmoTipo("EXECUTE")) {

            mAnalisador_Bloco.getAnalisar_Execute().analisar_Execute(ASTPai);

        } else if (ASTPai.mesmoTipo("WHEN")) {

            mAnalisador_Bloco.getAnalisar_When().analisar_When(ASTPai, mAlocadosAntes, false);


        } else if (ASTPai.mesmoTipo("DAZ")) {

            mAnalisador_Bloco.getAnalisar_All().analisar_All(ASTPai, mAlocadosAntes, false);


        } else if (ASTPai.mesmoTipo("RETURN")) {
            mAnalisador.getErros().add("Action " + ASTPai.getNome() + " : Nao pode ter RETORNO !");
        } else if (ASTPai.mesmoTipo("IF")) {
            mAnalisador_Bloco.getAnalisar_Condition().analisarCondicao(ASTPai, mAlocadosAntes, false, laco);
        } else if (ASTPai.mesmoTipo("WHILE")) {
            mAnalisador_Bloco.getAnalisar_While().analisarWhile(ASTPai, mAlocadosAntes, false);
        } else if (ASTPai.mesmoTipo("STEP")) {
            mAnalisador_Bloco.getAnalisar_Step().analisarStep(ASTPai, mAlocadosAntes, false);
        } else if (ASTPai.mesmoTipo("STEPDEF")) {
            mAnalisador_Bloco.getAnalisar_Step().analisarStepDef(ASTPai, mAlocadosAntes, false);
        } else if (ASTPai.mesmoTipo("TRY")) {
            mAnalisador_Bloco.getAnalisar_Try().init(ASTPai, mAlocadosAntes, false);
        } else if (ASTPai.mesmoTipo("CANCEL")) {
            if (!laco) {
                mAnalisador.getErros().add("CANCEL so pode existir dentro de um laco !");
            }
        } else if (ASTPai.mesmoTipo("CONTINUE")) {
            if (!laco) {
                mAnalisador.getErros().add("CONTINUE so pode existir dentro de um laco !");
            }
        } else if (ASTPai.mesmoTipo("EXCEPTION")) {
        } else if (ASTPai.mesmoTipo("EACH")) {

        } else {
            mAnalisador.getErros().add("AST : " + ASTPai.getTipo());
        }

    }


    public void analisarAction(AST ASTPai, ArrayList<String> mAlocadosAntes) {

        ArrayList<String> mAlocados = mAnalisador.copiarAlocados(mAlocadosAntes);


        String mParametragem = ASTPai.getNome() + " ( " + mAnalisador_Bloco.getAnalisar_Argumentos().analisarArguments(ASTPai.getBranch("ARGUMENTS"), mAlocados) + ") ";


        for (AST mAST : ASTPai.getBranch("BODY").getASTS()) {

            analisarDentroAction(mAST, mAlocados, false);


        }


    }

    public int getOpcionais(AST eArgumentos) {
        int o = 0;

        for (AST mAST : eArgumentos.getASTS()) {

            if (mAST.mesmoValor("OPT")) {
                o += 1;
            } else if (mAST.mesmoValor("OPTREF")) {
                o += 1;
            }

        }

        return o;
    }

    public void removerOpcionais(AST eArgumentos) {


        for (AST mAST : eArgumentos.getASTS()) {

            if (mAST.mesmoValor("OPT")) {
                mAST.setValor("VALUE");
            } else if (mAST.mesmoValor("OPTREF")) {
                mAST.setValor("REF");
            }

        }


    }

    public void desopcionarCom(AST eAction, AST eCom, ArrayList<AST> mArgumentar, ArrayList<AST> mInserirActions) {


        AST nAction = eAction;
        AST segAction = nAction.copiar();


        for (AST mAST : segAction.getBranch("ARGUMENTS").getASTS()) {
            if (mAST.mesmoValor("OPT")) {

                mAST.setValor("VALUE");

                if (getOpcionais(segAction.getBranch("ARGUMENTS")) > 0) {
                    desopcionarCom(segAction, mAST, mArgumentar, mInserirActions);
                }

                break;
            } else if (mAST.mesmoValor("OPTREF")) {

                mAST.setValor("REF");

                if (getOpcionais(segAction.getBranch("ARGUMENTS")) > 0) {
                    desopcionarCom(segAction, mAST, mArgumentar, mInserirActions);
                }

                break;
            }
        }

        for (AST mAST : nAction.getBranch("ARGUMENTS").getASTS()) {
            if (mAST.mesmoValor("OPT")) {
                mAST.setValor("VALUE");
            } else if (mAST.mesmoValor("OPTREF")) {
                mAST.setValor("REF");
            }

            if (mAST.existeBranch("VALUE")) {
                mAST.getASTS().remove(mAST.getBranch("VALUE"));
            }
        }

        for (AST mAST : nAction.getBranch("ARGUMENTS").getASTS()) {
            if (mAST.mesmoNome(eCom.getNome())) {
                nAction.getBranch("ARGUMENTS").getASTS().remove(mAST);
                break;
            }
        }

        ArrayList<String> mExiste = new ArrayList<String>();
        for (AST mAST : nAction.getBranch("ARGUMENTS").getASTS()) {
            mExiste.add(mAST.getNome());
        }


        mAnalisador.mensagem("Criando Opcional Action : " + eAction.getNome() + " por " + eCom.getNome());

        nAction.getBranch("BODY").getASTS().clear();

        //  for (AST mAST : mArgumentar) {
        //    mAnalisador.mensagem("ARGUMENTO " + mAST.getNome() + " : " + mAST.getValor());
        // }


        for (AST mAST : mArgumentar) {


            if (!mExiste.contains(mAST.getNome())) {

                AST eDef = nAction.getBranch("BODY").criarBranch("DEF");
                eDef.setNome(mAST.getNome());

                for (AST gAST : segAction.getBranch("ARGUMENTS").getASTS()) {
                    if (gAST.mesmoNome(mAST.getNome())) {
                        eDef.getASTS().add(gAST.getBranch("TYPE").copiar());
                        break;
                    }
                }

                eDef.getASTS().add(mAST.getBranch("VALUE").copiar());

            }
        }


        AST eExecute = nAction.getBranch("BODY").criarBranch("EXECUTE");
        eExecute.setNome(nAction.getNome());
        eExecute.setValor("FUNCT");

        AST eExecuteArguments = eExecute.criarBranch("ARGUMENTS");
        for (AST mAST : mArgumentar) {
            AST eArg = eExecuteArguments.criarBranch("ARGUMENT");
            eArg.setNome(mAST.getNome());
            // if(mExiste.contains(mAST.getNome())){
            eArg.setValor("ID");
            // }
        }


        // System.out.println(nAction.ImprimirArvoreDeInstrucoes());
        mInserirActions.add(nAction);

    }

    public void desopcionar(AST eAction, ArrayList<AST> mInserirActions) {

        AST nAction = eAction;
        AST segAction = nAction.copiar();
        AST oAction = nAction.copiar();

        ArrayList<AST> mArgumentar = new ArrayList<AST>();

        ArrayList<String> mExiste = new ArrayList<String>();


        for (AST mAST : nAction.getBranch("ARGUMENTS").getASTS()) {
            if (mAST.mesmoValor("OPT")) {

                AST eArg = mAST.copiar();
                mArgumentar.add(eArg);
            } else if (mAST.mesmoValor("OPTREF")) {

                AST eArg = mAST.copiar();
                mArgumentar.add(eArg);
            } else {
                AST eArg = mAST.copiar();

                mArgumentar.add(eArg);
                mExiste.add(mAST.getNome());
            }
        }


        for (AST mAST : segAction.getBranch("ARGUMENTS").getASTS()) {
            if (mAST.mesmoValor("OPT")) {

                mAST.setValor("VALUE");

                if (getOpcionais(segAction.getBranch("ARGUMENTS")) > 0) {
                    desopcionarCom(segAction, mAST, mArgumentar, mInserirActions);
                }

                break;
            } else if (mAST.mesmoValor("OPTREF")) {

                mAST.setValor("REF");

                if (getOpcionais(segAction.getBranch("ARGUMENTS")) > 0) {
                    desopcionarCom(segAction, mAST, mArgumentar, mInserirActions);
                }

                break;
            }
        }

        int opt = 0;

        AST tAction = eAction.copiar();


        for (AST mAST : tAction.getBranch("ARGUMENTS").getASTS()) {
            if (mAST.mesmoValor("OPT")) {
                opt += 1;
            }else   if (mAST.mesmoValor("OPTREF")) {
                opt += 1;
            }
        }


        while (opt > 0) {

            for (AST mAST : tAction.getBranch("ARGUMENTS").getASTS()) {
                if (mAST.mesmoValor("OPT")) {
                    tAction.getBranch("ARGUMENTS").getASTS().remove(mAST);
                    break;
                } else if (mAST.mesmoValor("OPTREF")) {
                    tAction.getBranch("ARGUMENTS").getASTS().remove(mAST);
                    break;
                }
            }

            opt = 0;
            for (AST mAST : tAction.getBranch("ARGUMENTS").getASTS()) {
                if (mAST.mesmoValor("OPT")) {
                    opt += 1;
                } else if (mAST.mesmoValor("OPTREF")) {
                    opt += 1;
                }
            }
        }

        mAnalisador.mensagem("Criando Opcional Action : " + eAction.getNome() + " Sem opcionais");


        tAction.getBranch("BODY").getASTS().clear();

        for (AST mAST : mArgumentar) {


            if (!mExiste.contains(mAST.getNome())) {

                AST eDef = tAction.getBranch("BODY").criarBranch("DEF");
                eDef.setNome(mAST.getNome());

                for (AST gAST : oAction.getBranch("ARGUMENTS").getASTS()) {
                    if (gAST.mesmoNome(mAST.getNome())) {
                        eDef.getASTS().add(gAST.getBranch("TYPE").copiar());
                        break;
                    }
                }

                eDef.getASTS().add(mAST.getBranch("VALUE").copiar());

            }
        }


        AST eExecute = tAction.getBranch("BODY").criarBranch("EXECUTE");
        eExecute.setNome(nAction.getNome());
        eExecute.setValor("FUNCT");

        AST eExecuteArguments = eExecute.criarBranch("ARGUMENTS");
        for (AST mAST : mArgumentar) {
            AST eArg = eExecuteArguments.criarBranch("ARGUMENT");
            eArg.setNome(mAST.getNome());
            // if(mExiste.contains(mAST.getNome())){
            eArg.setValor("ID");
            // }
        }

      //  System.out.println(tAction.ImprimirArvoreDeInstrucoes());

        mInserirActions.add(tAction);
    }

}
