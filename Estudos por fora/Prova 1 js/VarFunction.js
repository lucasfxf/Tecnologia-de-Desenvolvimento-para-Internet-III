/*const saudacao = function() {
    console.log("Olá, mundo!");
};

//saudacao();
console.log(saudacao); */

// --------------------------------------

/*let despedida = function(){
    console.log("Até mais!");
}

despedida(); */

//--------------------------------------------
/*
function executar(acao) {
    acao();
}

function dizerOi() {
    console.log("Oi!");
}

executar(dizerOi);*/

// -------------------------------

/*function processar(mostrarNum){
    mostrarNum();
}

function mostrarNum(){
    console.log(42);
}

processar(mostrarNum); */

// --------------------------------
function executar(funcao) {
    console.log("A");
    funcao();
    console.log("B");
}

function teste() {
    console.log("C");
}

executar(teste);
teste();

// --------------------------------------

function A() {
    console.log("1");
    B();
    console.log("2");
}

function B() {
    console.log("3");
    C();
    console.log("4");
}

function C() {
    console.log("5");
}

A();
console.log("6");