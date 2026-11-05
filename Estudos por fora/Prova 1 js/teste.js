console.log("A");

setTimeout(() => {
    console.log("B");
}, 0);

function executar() {
    console.log("C");

    setTimeout(() => {
        console.log("D");
    }, 0);

    console.log("E");
}

executar();

console.log("F");