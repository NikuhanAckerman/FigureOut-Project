function validateEmail(input) {
    const email = input.value;
    const errorMessage = document.getElementById('error-message');

    // Expressão regular básica para validar email
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    // Verifica se o email corresponde ao padrão
    if (emailPattern.test(email)) {
        errorMessage.style.display = 'none';
    } else {
        errorMessage.style.display = 'block';
    }
}

function password() {
    document.querySelector('form').addEventListener('submit', function(event) {
        var password = document.getElementById('password').value;
        var passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;

        if (!passwordRegex.test(password)) {
            alert('Senha inválida. A senha deve ter pelo menos 8 caracteres, incluindo letras maiúsculas, minúsculas, números e caracteres especiais.');
            //event.preventDefault(); // Evita o envio do formulário
        }
    });
}

function cpfMask() {
    document.getElementById('cpf-input').addEventListener('input', function() {
        let value = this.value.replace(/\D/g, ''); //Remove caracteres que não sejam dígitos.
        if (value.length > 11) value = value.slice(0, 11); // Limita para 11 dígitos no máximo.

        // Formata o CPF
        this.value = value.replace(/(\d{3})(\d{3})?(\d{3})?(\d{2})?/, function(match, p1, p2, p3, p4) {
            let result = p1;
            if (p2) result += '.' + p2;
            if (p3) result += '.' + p3;
            if (p4) result += '-' + p4;
            return result;
        });
    });
}

function applyDDDMask(input) {
    // Remove qualquer caractere que não seja número
    let value = input.value.replace(/\D/g, '');

    // Adiciona a máscara no formato (XX)
    if (value.length > 2) {
        value = '(' + value.slice(0, 2) + ') ' + value.slice(2, 4);
    } else {
        value = '(' + value + ')';
    }
    // Atualiza o valor do campo de entrada
    input.value = value;
}


function applyPhoneMask(input) {
    // Remove qualquer caractere que não seja número
    let value = input.value.replace(/\D/g, '');

    // Aplica a máscara no formato XXXX-XXXX ou XXXXX-XXXX
    if (value.length <= 8) {
    // Formato XXXX-XXXX
        value = value.slice(0, 4) + (value.length > 4 ? '-' + value.slice(4) : '');
    } else {
    // Formato XXXXX-XXXX
        value = value.slice(0, 5) + '-' + value.slice(5);
    }
    // Atualiza o valor do campo de entrada
    input.value = value;
}


function applyResidenceMask(input) {
    // Remove qualquer caractere que não seja número
    let value = input.value.replace(/\D/g, '');

    // Limita o valor a 5 dígitos
    if (value.length > 5) {
        value = value.slice(0, 5);
    }

    // Atualiza o valor do campo de entrada
    input.value = value;

    // Valida o valor
    validateResidenceNumber(value);
}

function validateResidenceNumber(value) {
    const errorMessage = document.getElementById('error-message');

    // Exibe mensagem de erro se o valor for "0" ou vazio
    if (value === '' || value === '0') {
        errorMessage.style.display = 'block';
    } else {
        errorMessage.style.display = 'none';
    }
}

function applyCepMask(input) {
    // Remove qualquer caractere que não seja número
    let value = input.value.replace(/\D/g, '');

    // Adiciona a máscara no formato XXXXX-XXX
    if (value.length > 5) {
        value = value.slice(0, 5) + '-' + value.slice(5, 8);
    }

    // Atualiza o valor do campo de entrada
    input.value = value;
}