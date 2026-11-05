/* function criarMensagem(){
    return function outraMensagem(){
        console.log("Estudando js");
    }
}

let mensagem = criarMensagem();

mensagem(); */

// ----------------------------------------

const multiplicarPor3 = criarMultiplicador(3);

console.log(multiplicarPor3(5));  // 15
console.log(multiplicarPor3(10)); // 30

function criarMultiplicador(multiplicarPor3){
    criarMultiplicador = multiplicarPor3() * 3;
}

