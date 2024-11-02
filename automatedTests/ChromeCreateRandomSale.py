import unittest
from selenium import webdriver
from webdriver_manager.chrome import ChromeDriverManager
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.chrome.service import Service
import time
import random
from ChromeSeleniumFunctions import *

class ProductFormTest(unittest.TestCase):
    def setUp(self):
        # Configura as opções do Chrome.
        chrome_options = Options()
        chrome_options.add_argument("--no-sandbox")
        chrome_options.add_argument("--disable-dev-shm-usage")

        # Iniciliza o WebDriver.
        self.driver = webdriver.Chrome(service=Service(ChromeDriverManager().install()), options=chrome_options)
        self.driver.get("http://localhost:8080/index")

    def test_fill_product_form(self):
        '''
        -- FUNÇÕES CRIADAS PARA TESTES --
        click_button(self, id): Clica em botão da página.
        input_string(self, id, valor): Insere texto.
        select_radio(self, id, valor): Seleciona botão de rádio.
        check_checkbox(self, id): Checha checkbox.
        select_option(self, id, valor): Seleciona opção de um menu dropdown pelo texto visível.
        select_option_by_value(self, id, value): Seleciona opção de menu dropdown pelo valor.
        select_product(self, produto): Seleciona um produto da loja pelo nome dele.
        send_image(self, id, pasta, arquivo): Envia uma imagem.
        '''
        
        # Selecionando um cliente para navegar a loja.
        select_option_by_value(self, "clientNavigate", "1")
        time.sleep(2)

        # CONJUNTO DE PRODUTOS (por nome)
        products = {"Hu Tao", "Paimon", "Asuka", "Hatsune Miku", "Mari (Omori)"}

        # Number of iterations (up to the number of unique products)
        iteration = min(1, len(products))  # Adjust this as needed

        # Keep track of already chosen products
        chosen_products = set()

        for i in range(iteration):
            # Select a random product that hasn't been chosen yet
            remaining_products = list(products - chosen_products)  # Get products that haven't been chosen
            if not remaining_products:  # Break if there are no remaining products
                print("No more unique products to choose from.")
                break
                
            product_name = random.choice(remaining_products)
            quantity = random.randint(1, 10)

            # Função modular para comprar um produto aleatório em uma quantia aleatória.
            buy_product(product_name, quantity)
            
            # Add the chosen product to the set of chosen products
            chosen_products.add(product_name)

        # Entrando no offcanvas do carrinho.
        click_button(self, "cart")
        time.sleep(2)

        click_button(self, "proceedToCheckout")
        time.sleep(2)

        # PÁGINA DE CARRINHO
        # Selecionando cartões de crédito e endereço.
        select_option_by_value(self, "salesCardsIds", "1")
        time.sleep(1)

        select_option(self, "address-dropdown", "Minha casa")
        time.sleep(1)

        click_button(self, "btn-finalizar")
        time.sleep(1)

        # Página de finalizar compra.
        input_string(self, "amountPaid1", "200")
        time.sleep(1)
        input_string(self, "amountPaid2", "109.78")
        time.sleep(1)

        click_button(self, "btn-finalizar")
        time.sleep(1)

    def tearDown(self):
        # Fecha o browser
        self.driver.quit()

if __name__ == "__main__":
    unittest.main()
