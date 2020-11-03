![ChangeList - Sigmaz](https://raw.githubusercontent.com/luandkg/Sigmaz/master/res/imagens/change.png)


Linguagem de Programação Estruturada - Alterações


		2020 06 13 -->> Lexer
		2020 06 13 -->> Parser
		2020 06 13 -->> Compiler


		2020 06 28 -->> TYPE


		2020 06 29 -->> REESTRUTURAÇÃO DO LEXER
		2020 06 29 -->> DIVISAO DA REQUISICAO : AST_IMPORT eAST_REQUIRE


		2020 06 30 -->> UNARY para DIRECTOR
		2020 06 30 -->> OPERATION para OPERATOR


		2020 07 02 -->> AST_CALL : AUTO


		2020 07 03 -->> INIT STRUCT GLOBAL
		2020 07 03 -->> START TYPE GLOBAL


		2020 07 05 -->> REESTRUTURAÇÃO DO SISTEMA DE ANALISE
		2020 07 05 -->> ANALISADOR_BLOCO


		2020 07 14 -->> ALTERANDO AST_TYPE para AST_STRUCT EXTENDED TYPE


		2020 07 15 -->> ChangeList automatizado


		2020 07 19 -->> Divisão do Compiler : Compiler e CompilerUnit
		2020 07 19 -->> Processamento de Compilação em Fila


		2020 07 20 -->> Trilhador Horizontal Vertical


		2020 07 23 -->> Unificação em Run_Any
		2020 07 23 -->> Inclusao do Run_Action
		2020 07 23 -->> Inclusao do Run_Function
		2020 07 23 -->> Inclusao do Run_ActionFunction
		2020 07 23 -->> Inclusao do Run_Operator
		2020 07 23 -->> Inclusao do Run_Director
		2020 07 23 -->> Exclusão do Run_Func


		2020 09 13 -->> Novo Token no Lexer : NUMERO_FLOAT
		2020 09 13 -->> Novo Tipo Primitivo : int
		2020 09 13 -->> Adequação do sistema de testes


		2020 09 16 -->> Função Anônima


		2020 09 22 -->> Pre Processamento


		2020 09 25 -->> Operador Ternário de "->" para "if"
		2020 09 25 -->> Exclusão : AST_Value_ItemVector
		2020 09 25 -->> Exclusão : AST_Value_Argument
		2020 09 25 -->> Exclusão : AST_Value_Parenteses
		2020 09 25 -->> Exclusao : AST_Value_Operator


		2020 09 26 -->> Alteração do Sistema de CAST : Getter
		2020 09 26 -->> Alteração do Sistema de CAST : Setter


		2020 09 30 -->> Imagens Fixas para BioGerador de Insignias


		2020 10 01 -->> Alteração : Extern -> Explicit
		2020 10 01 -->> Analisador Atribuidor -> PosProcessador Atribuidor


		2020 10 04 -->> Remoção do Sistema de Analise


		2020 10 11 -->> Alteração de Invocadores para Processadores
		2020 10 11 -->> Remoção do Invocador MATH
		2020 10 11 -->> Remoção do Invocador UTILS


		2020 10 14 -->> Remoção do Invocador STAGES
		2020 10 14 -->> Divisão do Lexer : Lexer e Tokenizador


		2020 10 16 -->> Remoção do Invocador COMPILER
		2020 10 16 -->> Alteração de Invoke Compiler para DEBUG


		2020 10 19 -->> ReOrganização das Mensagens de Debug
		2020 10 19 -->> Remoção do Invock CAST
		2020 10 19 -->> Remoção do Invock COMPILER
		2020 10 19 -->> Remoção do Run_Invock


		2020 10 20 -->> Run_Any - Inicializador de Struct


		2020 10 26 -->> Separação do Return do Run_Body


		2020 10 28 -->> Run_Execute
		2020 10 28 -->> Run_External


		2020 11 01 -->> PosProcessador Tipador : Refatoramento
		2020 11 01 -->> Pos Processador Valorador -> Fase Integralizador


		2020 11 02 -->> AST Value Num -> INT
		2020 11 02 -->> AST Value Text -> STRING
		2020 11 02 -->> AST Value Float -> NUM
		2020 11 02 -->> Remoção AST_Invock

