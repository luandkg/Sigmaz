
status -> "Executando MAKE pela primeira vez";

# LOCAIS PADROES

    build -> "build";
    source -> "";
    intellisenses -> "intellisenses";

# CONFIGURACOES

    options build {

        stack_process = false;
        object = false;
        pos_process = false;
        analysis_process=false;

    };

# GERADORES

    make executable "ref.a" with -> "79 - refered.sigmaz";
    make library "lib.a" with -> "libs/lib.sigmaz";
    make library "neg.a" with -> "libs/neg.sigmaz";
    make executable "refered.a" with -> { "79 - refered.sigmaz" };



# INTELLISENSES

    status -> " --- INTELLISENSES --- ";

    options intellisense with default;
    generate intellisense "light_" with -> "libs/lib.sigmaz";


    options intellisense {

        # PERSONALIZACAO DAS CORES DO INTELLISENSE

        background = [255, 255, 255];
        box = [0, 0, 0];
        text = [0, 0, 0];

        package = [0, 150, 136];
        sigmaz = [0, 150, 136];

        struct = [244, 67, 54];
        model = [158, 158, 158];
        stage = [255, 152, 0];
        cast = [205, 220, 57];
        external = [103, 58, 183];
        type = [76, 175, 80];


    };

    generate intellisense "dark_" with -> "libs/lib.sigmaz";
    generate intellisense "i_" with -> { "libs/neg.sigmaz" , "libs/pos.sigmaz" };

# IDENTAR ARQUIVOS

    ident with -> "libs/lib.sigmaz";

# TESTES AUTOMATIZADOS

    tests "SEQUENCIA DE TESTES - ALFA" : "SIGMAZ_TEST.a" -> {

        "01 - init.sigmaz" ,
        "02 - arguments.sigmaz",
        "03 - define.sigmaz",
        "04 - require.sigmaz",
        "05 - function.sigmaz",
        "06 - functions2.sigmaz"

    };

    set author -> "Luan Freitas";
    set version -> "1.0.0";
    set company -> "DKG SOFTWARES";

    make library "num.a" with -> { "libs/neg.sigmaz" , "libs/pos.sigmaz" };

    ast -> "num.a";
