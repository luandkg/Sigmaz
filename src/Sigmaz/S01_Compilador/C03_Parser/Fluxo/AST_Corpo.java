package Sigmaz.S01_Compilador.C03_Parser.Fluxo;

import Sigmaz.S01_Compilador.C03_Parser.*;
import Sigmaz.S01_Compilador.C03_Parser.Alocador.AST_Alocador;
import Sigmaz.S01_Compilador.C03_Parser.Alocador.AST_Def;
import Sigmaz.S01_Compilador.C03_Parser.Alocador.AST_Mut;
import Sigmaz.S01_Compilador.C03_Parser.Processador.AST_Reg;
import Sigmaz.S01_Compilador.C03_Parser.Bloco.AST_Local;
import Sigmaz.S01_Compilador.C02_Lexer.Token;
import Sigmaz.S01_Compilador.C02_Lexer.TokenTipo;
import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S01_Compilador.C03_Parser.Processador.AST_Proc;

public class AST_Corpo {

    private Parser mCompiler;

    public AST_Corpo(Parser eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {


        Token TokenC = mCompiler.getTokenAvanteStatus(TokenTipo.CHAVE_ABRE, "Era esperado abrir chaves");

        if (TokenC.getTipo() != TokenTipo.CHAVE_ABRE) {
            return;
        }

        dentro(ASTPai);

    }

    public void dentro(AST ASTPai) {

        boolean saiu = false;

        while (mCompiler.Continuar()) {
            Token TokenD = mCompiler.getTokenAvante();
            if (TokenD.getTipo() == TokenTipo.CHAVE_FECHA) {
                saiu = true;
                break;

            } else if (TokenD.getTipo() == TokenTipo.CHAVE_ABRE) {

                AST_Escopo mAST_Escopo = new AST_Escopo(mCompiler);
                mAST_Escopo.init(ASTPai);

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("def")) {


                AST_Def mAST = new AST_Def(mCompiler);
                mAST.init_Def(ASTPai);

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("moc")) {

                AST_Alocador mAST = new AST_Alocador(mCompiler);
                mAST.init("MOC", ASTPai);

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("let")) {

                AST_Alocador mAST = new AST_Alocador(mCompiler);
                mAST.initSemTipagem("LET", ASTPai);
            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("voz")) {

                AST_Alocador mAST = new AST_Alocador(mCompiler);
                mAST.initSemTipagem("VOZ", ASTPai);

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("mut")) {

                AST_Mut mAST = new AST_Mut(mCompiler);
                mAST.init_mut(ASTPai);


            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("return")) {

                AST_Return mAST = new AST_Return(mCompiler);
                mAST.init(ASTPai);

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("if")) {

                AST_Condition mAST = new AST_Condition(mCompiler);
                mAST.init(ASTPai);

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("while")) {

                AST_While mAST = new AST_While(mCompiler);
                mAST.init(ASTPai);

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("loop")) {

                AST_Loop mAST = new AST_Loop(mCompiler);
                mAST.init(ASTPai);

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("step")) {

                AST_Step mAST = new AST_Step(mCompiler);
                mAST.init(ASTPai);
            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("when")) {

                AST_When mAST = new AST_When(mCompiler);
                mAST.init(ASTPai);
            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("daz")) {

                AST_Daz mAST = new AST_Daz(mCompiler);
                mAST.init(ASTPai);

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("try")) {

                AST_Try mAST = new AST_Try(mCompiler);
                mAST.init(ASTPai);
            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("each")) {

                AST_Each mAST = new AST_Each(mCompiler);
                mAST.init(ASTPai);

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("cancel")) {

                AST mCancel = ASTPai.criarBranch("CANCEL");
                Token TokenC2 = mCompiler.getTokenAvanteStatus(TokenTipo.PONTOVIRGULA, "Era esperado abrir ponto e virgula");

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("continue")) {

                AST mCancel = ASTPai.criarBranch("CONTINUE");
                Token TokenC2 = mCompiler.getTokenAvanteStatus(TokenTipo.PONTOVIRGULA, "Era esperado abrir ponto e virgula");


            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("exception")) {

                AST_Exception mAST = new AST_Exception(mCompiler);
                mAST.init(ASTPai);

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("init")) {


                mCompiler.voltar();

                AST_Value mASTValue = new AST_Value(mCompiler);
                mASTValue.init(ASTPai.criarBranch("VALUE"));

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("start")) {


                mCompiler.voltar();

                AST_Value mASTValue = new AST_Value(mCompiler);
                mASTValue.init(ASTPai.criarBranch("VALUE"));

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("delete")) {

                AST_Delete mAST = new AST_Delete(mCompiler);
                mAST.init(ASTPai);


            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("extern_refered")) {

                AST_ExternRefered AST_ER = new AST_ExternRefered(mCompiler);
                AST_ER.init(ASTPai);

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("local")) {

                AST_Local mLocal = new AST_Local(mCompiler);
                mLocal.init(ASTPai);

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("auto")) {

                AST_ExecuteAuto mAST = new AST_ExecuteAuto(mCompiler);
                mAST.init(ASTPai);
            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("functor")) {

                AST_ExecuteAuto mAST = new AST_ExecuteAuto(mCompiler);
                mAST.init(ASTPai);

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("change")) {

                AST_Change mAST = new AST_Change(mCompiler);
                mAST.init(ASTPai);

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("using")) {

                AST_Using mAST = new AST_Using(mCompiler);
                mAST.init(ASTPai);

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("reg")) {

                AST_Reg mAST = new AST_Reg(mCompiler);
                mAST.init(ASTPai);
            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo_SemCS("PROC")) {

                AST_Proc mAST = new AST_Proc(mCompiler);
                mAST.init(ASTPai);

            } else if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo_SemCS("DEBUG")) {

                AST_Debug mAST = new AST_Debug(mCompiler);
                mAST.init(ASTPai);

            } else if (TokenD.getTipo() == TokenTipo.ID) {


                AST_Comando mAST = new AST_Comando(mCompiler);
                mAST.init(ASTPai);

            } else if (TokenD.getTipo() == TokenTipo.ARROBA) {

                Token TokenP2 = mCompiler.getTokenAvanteStatus(TokenTipo.PARENTESES_ABRE, "Era esperado ABRIR PARESENTESES !");


                AST ASTCorrente = new AST("EXECUTE_LOCAL");

                AST_ValueTypes mAVA = new AST_ValueTypes(mCompiler);
                mAVA.ReceberArgumentos(ASTCorrente, false, null);

                ASTPai.getASTS().add(ASTCorrente);

                Token TokenP3 = mCompiler.getTokenAvanteStatus(TokenTipo.PONTOVIRGULA, "Era esperado PONTO E VIRGULA !");


            } else {


                mCompiler.errarCompilacao("Comando Deconhecido : " + TokenD.getConteudo(), TokenD);


            }
        }

        if (!saiu) {
            mCompiler.errarCompilacao("Era esperado fechar chaves" + mCompiler.getTokenAvante().getConteudo(), mCompiler.getTokenAvante());
        }

    }
}
