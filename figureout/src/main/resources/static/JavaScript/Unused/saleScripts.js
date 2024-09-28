
        let desconto = 0;
        let cartoes = []; // Array para armazenar os cartões de crédito

        function removerLinha(button) {
        const linha = button.closest('tr');
        linha.remove();
        atualizarTotalGeral(); // Atualiza o total geral após a remoção
    }

        function atualizarTotalProduto(linha) {
        const preco = parseFloat(linha.querySelector('.preco').textContent.replace('R$ ', '').replace(',', '.'));
        const quantidade = parseInt(linha.querySelector('.quantidade').value);
        const totalProduto = preco * quantidade;
        linha.querySelector('.total').textContent = `R$ ${totalProduto.toFixed(2).replace('.', ',')}`;
        return totalProduto; // Retorna o total do produto
    }

        function atualizarTotalGeral() {
        const tabela = document.querySelector('table tbody');
        const linhas = tabela.querySelectorAll('tr');
        let totalGeral = 0;

        // Itera sobre as linhas, exceto a última (total da compra)
        linhas.forEach((linha, index) => {
        if (index < linhas.length - 1) { // Ignora a linha do total
        totalGeral += atualizarTotalProduto(linha); // Soma o total do produto
    }
    });

        // Aplica o desconto
        const totalComDesconto = totalGeral - (totalGeral * (desconto / 100));

        // Atualiza total na última linha
        const totalLinha = tabela.querySelector('tr:last-child .total');
        totalLinha.textContent = `R$ ${totalComDesconto.toFixed(2).replace('.', ',')}`;
        atualizarBotaoFinalizar(totalComDesconto); // Atualiza o estado do botão "Finalizar Compra"
    }

        function atualizarQuantidade(input) {
        const linha = input.closest('tr');
        atualizarTotalProduto(linha); // Atualiza o total do produto
        atualizarTotalGeral(); // Atualiza o total geral
    }

        function atualizarBotaoFinalizar(totalGeral) {
        const botaoFinalizar = document.getElementById('btn-finalizar');
        if (totalGeral === 0) {
        botaoFinalizar.classList.add('btn-secondary'); // Adiciona a classe cinza
        botaoFinalizar.classList.remove('btn-primary'); // Remove a classe padrão
        botaoFinalizar.disabled = true; // Desabilita o botão
    } else {
        botaoFinalizar.classList.remove('btn-secondary'); // Remove a classe cinza
        botaoFinalizar.classList.add('btn-primary'); // Adiciona a classe padrão
        botaoFinalizar.disabled = false; // Habilita o botão
    }
    }

        // Cupom hardcoded pra testar ;)
        function aplicarCupom() {
        const cupomInput = document.getElementById('cupom-input').value;
        if (cupomInput === 'SCATIL10') { // Exemplo de cupom
        desconto = 10; // Aplica 10% de desconto
        alert('Cupom aplicado com sucesso!');
    } else if(cupomInput === 'RENANBEBEDORDEMIJO99'){
        desconto = 99;
        alert('Cummy tummy aplicado com cumsesso!')
    } else {
        desconto = 0;
        alert('Cupom inválido.');
    }
        atualizarTotalGeral(); // Atualiza o total após aplicar o cupom
    }

        function adicionarCartao() {
        const numeroCartao = document.getElementById('numero-cartao').value;
        if (numeroCartao) {
        cartoes.push(numeroCartao);
        atualizarDropdownCartoes();
        document.getElementById('numero-cartao').value = ''; // Limpa o campo após adicionar
    } else {
        alert('Digite um número de cartão válido.');
    }
    }

        function adicionarCartaoDropdown() {
        const dropdownContainer = document.getElementById('cartao-dropdown-container');
        const novoDropdown = document.createElement('div');
        novoDropdown.classList.add('mb-3');

        const select = document.createElement('select');
        select.classList.add('form-control');
        select.innerHTML = `<option value="" disabled selected>Selecione um cartão</option>
                <option value="cartao1">Cartão azul</option>
                <option value="cartao2">Cartão marrom</option>
                <option value="cartao3">Cartão roxo</option>`;

        novoDropdown.appendChild(select);
        dropdownContainer.appendChild(novoDropdown);
    }

        // Chama atualizarTotalGeral ao carregar a página
        document.addEventListener('DOMContentLoaded', () => {
        atualizarTotalGeral();
    });