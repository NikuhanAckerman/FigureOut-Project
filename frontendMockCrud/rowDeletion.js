
function deleteRow() {
    const table = document.getElementById('client-table');
    table.addEventListener('click', function(event) {
        if (event.target && event.target.classList.contains('btn-delete')) {
            const row = event.target.closest('tr');
            row.remove();
        }
    });
}

document.addEventListener('DOMContentLoaded', deleteRow);