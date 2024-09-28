document.addEventListener('DOMContentLoaded', function() {
    // Find the cart button
    const cartButton = document.querySelector('.bi-cart');

    // Create the offcanvas HTML as a string
    const offcanvasHTML = `
    <!--
    
    -->
    
    <div class="offcanvas offcanvas-end" tabindex="-1" id="offcanvasCart" aria-labelledby="offcanvasCartLabel">
        <div class="offcanvas-header">
            <h5 id="offcanvasCartLabel">Carrinho</h5>
            <button type="button" class="btn-close text-reset" data-bs-dismiss="offcanvas" aria-label="Close"></button>
        </div>
        <div class="offcanvas-body">
            
        </div>
    </div>
    
    `;

    function insertOffcanvas() {
        const existingOffcanvas = document.getElementById('offcanvasCart');
        if (!existingOffcanvas) { // Check if offcanvas already exists
            document.body.insertAdjacentHTML('beforeend', offcanvasHTML);
            cartButton.setAttribute("data-bs-toggle", "offcanvas");
            cartButton.setAttribute("data-bs-target", "#offcanvasCart");
            cartButton.setAttribute("aria-controls", "offcanvasCart")





        }
    }

    cartButton.addEventListener('click', insertOffcanvas);
});