const form = document.getElementById('form')
const descImput = document.querySelector('#descricao')
const valorImput = document.getElementById('montante')
const balancoH1 = document.getElementById('balanco')
const receitaP = document.getElementById('din-positivo')
const despesaP = document.getElementById('din-negativo')
const transacoesUL = document.getElementById('transacoes')

// ls == Local Storage
const chave_transacoes_ls = 'transacoes'

let transacoesSalvas;

try {
    transacoesSalvas = JSON.parse(
        localStorage.getItem(chave_transacoes_ls)
    );
} catch (error) {
    transacoesSalvas = null;
}

if (transacoesSalvas == null || transacoesSalvas == undefined) {
    transacoesSalvas = []
}


form.addEventListener('submit', (e) => {
    e.preventDefault();

    const descTransacao = descImput.value.trim();
    const valorTransacao = valorImput.value.trim();

    // Descobre qual radio está selecionado AGORA
    const tipoInput = document.querySelector('input[name="tipo"]:checked');

    // Fail fast - falhar cedo
    if ((descTransacao == "") || (valorTransacao == "")) {
        alert('Descrição e valor não podem ser vazios.')
        return;
    }

    descImput.value = "";
    valorImput.value = "";

    // Gera o próximo ID
    let proximoId = 0;

    if (transacoesSalvas.length > 0) {
        proximoId = Math.max(
            ...transacoesSalvas.map(transacao => transacao.id)
        ) + 1;
    }

    const valor = parseFloat(valorTransacao);

    const transacao = {
        id: proximoId,
        descricao: descTransacao,
        valor: tipoInput.value === "despesa" ? -valor : valor
    };

    console.log(transacao)

    somaAoSaldo(transacao)
    somaReceitaDespesa(transacao)
    addTransacaoAoDOM(transacao)

    transacoesSalvas.push(transacao)

    localStorage.setItem(
        chave_transacoes_ls,
        JSON.stringify(transacoesSalvas)
    )
});


function addTransacaoAoDOM(transacao) {

    const classeCSS = transacao.valor >= 0 ? 'positivo' : 'negativo'

    const li = document.createElement('li')

    li.classList.add(classeCSS)

    // Guarda o ID da transação no próprio li
    li.dataset.id = transacao.id

    li.innerHTML = `
        ${transacao.descricao}
        <span>R$${transacao.valor}</span>
        <button 
            onclick="excluiTransacao(${transacao.id})"
            class="delete-btn">
            X
        </button>
    `

    transacoesUL.append(li)
}


function somaReceitaDespesa(transacao) {

    const elemento = transacao.valor > 0 ? receitaP : despesaP

    const substituir = transacao.valor > 0 ? "+ R$" : "- R$"

    let valorAtual = elemento.innerHTML.replace(substituir, "")

    valorAtual = parseFloat(valorAtual)

    valorAtual += Math.abs(transacao.valor)

    elemento.innerHTML =
        `${substituir}${valorAtual.toFixed(2)}`
}


function somaAoSaldo(transacao) {

    const valorTransacao = transacao.valor;

    let total = balancoH1.innerHTML.replace('R$', '');

    total = parseFloat(total)

    total += valorTransacao;

    balancoH1.innerHTML = `R$${total.toFixed(2)}`
}


function carregarDados() {

    transacoesUL.innerHTML = ''

    balancoH1.innerHTML = 'R$0.00'

    receitaP.innerHTML = '+ R$0.00'

    despesaP.innerHTML = '- R$0.00'

    for (let i = 0; i < transacoesSalvas.length; i++) {

        somaAoSaldo(transacoesSalvas[i])

        somaReceitaDespesa(transacoesSalvas[i])

        addTransacaoAoDOM(transacoesSalvas[i])
    }
}


carregarDados();


function excluiTransacao(id) {

    // Descobre a posição da transação no array
    const transacaoIndex = transacoesSalvas.findIndex(
        (transacao) => transacao.id === id
    );

    // Se não encontrou, não faz nada
    if (transacaoIndex === -1) {
        return;
    }

    // Guarda a transação antes de removê-la
    const transacao = transacoesSalvas[transacaoIndex];


    // 1. REMOVE O <li> DO DOM
    const li = document.querySelector(
        `li[data-id="${id}"]`
    );

    if (li) {
        li.remove();
    }


    // 2. REMOVE DO ARRAY
    transacoesSalvas.splice(transacaoIndex, 1);


    // 3. ATUALIZA O LOCAL STORAGE
    localStorage.setItem(
        chave_transacoes_ls,
        JSON.stringify(transacoesSalvas)
    );


    // 4. ATUALIZA O SALDO
    let saldoAtual = parseFloat(
        balancoH1.innerHTML.replace('R$', '')
    );

    saldoAtual -= transacao.valor;

    balancoH1.innerHTML =
        `R$${saldoAtual.toFixed(2)}`;


    // 5. ATUALIZA RECEITAS / DESPESAS
    if (transacao.valor > 0) {

        let receitaAtual = parseFloat(
            receitaP.innerHTML.replace('+ R$', '')
        );

        receitaAtual -= transacao.valor;

        receitaP.innerHTML =
            `+ R$${receitaAtual.toFixed(2)}`;

    } else {

        let despesaAtual = parseFloat(
            despesaP.innerHTML.replace('- R$', '')
        );

        despesaAtual -= Math.abs(transacao.valor);

        despesaP.innerHTML =
            `- R$${despesaAtual.toFixed(2)}`;
    }
}