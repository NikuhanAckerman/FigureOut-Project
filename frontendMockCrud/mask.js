const cpf = document.querySelector('#cpf-input');
const telefone = document.querySelector('#phonenumber-input')

cpf.addEventListener('input', function() {
    let cpfValue = cpf.value;

    cpf.value = cpfValue
        .replace(/[^0-9.-]/g, '')
        .replace(/(\d{3})(\d)/, '$1.')
        .replace(/(\d{3})\.(\d{3})(\d)/, '$1.$2.') 
        .replace(/(\d{3})\.(\d{3})\.(\d{3})(\d)/, '$1.$2.$3-$4');
});


// (81) 2506-1752
telefone.addEventListener('keydown', function() {
    telefoneValue = telefone.value

    telefone.value = telefoneValue
    .replace(/[^\d()\- ]/g, '')  // Remove anything that's not a number, parentheses, space, or dash
        .replace(/^(\d{2})(\d)/, '($1) $2')  // Add parentheses around the area code
        .replace(/(\d{4})(\d)/, '$1-$2')  // Add the dash after the first 4 digits
        .replace(/(\d{5})(\d)/, '$1-$2')  // Ensure dash formatting for the remaining digits
        
})