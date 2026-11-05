// TESTE
/*
class Personagem {
    constructor(nome, nivel){
        this.nome;
        this.nivel;
    }

    apresentar(){
        console.log("Sou " + this.nome + " e Estou no nivel " + this.nivel);
    }
}

const p1 = new Personagem();
const p2 = new Personagem();

p1.nome = "Rex";
p2.nome = "Mia";

p1.nivel = 10;
p2.nivel = 10;

console.log(p1.apresentar() === p2.apresentar());  */

// --------------------------------------------------------------------------------

//CORREÇÃO
class Personagem {
    constructor(nome, nivel) {
        this.nome = nome;
        this.nivel = nivel;
    }

    apresentar() {
        console.log("Sou " + this.nome + " e estou no nível " + this.nivel);
    }
}

const p1 = new Personagem("Rex", 10);
const p2 = new Personagem("Mia", 15);

p1.apresentar();
p2.apresentar();

console.log(p1.apresentar === p2.apresentar);


