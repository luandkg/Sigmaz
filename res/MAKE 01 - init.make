
status -> "Executando MAKE pela primeira vez";

build -> "build";
source -> "";

#make executable "refered.a" with -> { "79 - refered.sigmaz" };

make executable "ref.a" with -> "79 - refered.sigmaz";

make library "lib.a" with -> "libs/lib.sigmaz";

make library "neg.a" with -> "libs/neg.sigmaz";
