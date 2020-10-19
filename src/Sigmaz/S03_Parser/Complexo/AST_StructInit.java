package Sigmaz.S03_Parser.Complexo;

import Sigmaz.S03_Parser.Organizador.AST_Argumentos;
import Sigmaz.S03_Parser.AST_Recebimentos;
import Sigmaz.S03_Parser.Parser;
import Sigmaz.S03_Parser.Fluxo.AST_Corpo;
import Sigmaz.S02_Lexer.Token;
import Sigmaz.S02_Lexer.TokenTipo;
import Sigmaz.S00_Utilitarios.AST;

public class AST_StructInit {

    private Parser mCompiler;

    public AST_StructInit(Parser eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai, String NomeStruct,String Visibilidade) {

        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.ID) {

            AST AST_Corrente = new AST("INIT");
            AST_Corrente.setNome(TokenC.getConteudo());
            ASTPai.getASTS().add(AST_Corrente);


            AST AST_Visibilidade = AST_Corrente.criarBranch("VISIBILITY");
            AST_Visibilidade.setNome(Visibilidade);


            AST AST_Call = AST_Corrente.criarBranch("CALL");
            AST_Call.setValor("FALSE");

            if (!TokenC.mesmoConteudo(NomeStruct)) {
                mCompiler.errarCompilacao("O nome do INIT deve ser igual ao nome da Struct : " + NomeStruct + " -> INIT " + TokenC.getConteudo(), TokenC);
            }

            AST AST_Arguments = AST_Corrente.criarBranch("ARGUMENTS");
            AST AST_BODY = AST_Corrente.criarBranch("BODY");

            AST_Argumentos mArgumentos = new AST_Argumentos(mCompiler);
            mArgumentos.init(AST_Arguments);

            Token Futuro = mCompiler.getTokenFuturo();
            if (Futuro.getTipo() == TokenTipo.SETA) {
                mCompiler.Proximo();


                Token TokenP = mCompiler.getTokenAvante();
                if (TokenP.getTipo() == TokenTipo.ID) {

                    AST_Call.setNome(TokenP.getConteudo());
                    AST_Call.setValor("TRUE");

                } else {
                    mCompiler.errarCompilacao("Era esperado o nome do INIT da Struct Pai ", TokenP);
                }



               // AST_Transferencia mRA = new AST_Transferencia(mCompiler);
               // mRA.init(AST_Call);

                Token TokenI = mCompiler.getTokenAvante();
                if (TokenI.getTipo() == TokenTipo.PARENTESES_ABRE) {


                }else{
                    mCompiler.errarCompilacao("Era esperado abrir parenteses",   TokenI);
                }

                AST_Recebimentos mRA = new AST_Recebimentos(mCompiler);
                mRA.init(AST_Call.criarBranch("ARGUMENTS"));


            }


            AST_Corpo mCorpo = new AST_Corpo(mCompiler);
            mCorpo.init(AST_BODY);



        } else {
            mCompiler.errarCompilacao("Era esperado o nome para uma INIT !", TokenC);
        }


    }


}