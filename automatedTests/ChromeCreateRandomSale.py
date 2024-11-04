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
        
        # Clica no botão "Ver Loja".
        click_button(self, "seeShop")

        # CONJUNTO DE PRODUTOS (por nome)
        products = {"Hu Tao", "Paimon", "Asuka", "Hatsune Miku", "Mari (Omori)"}

        # Cria uma cópia do conjunto de produtos pra ser usado no loop.
        remaining_products = set(products)

        # Número aleatório de iterações (Entre 1 e a quantidade de produtos no conjunto).
        iteration = random.randint(1, len(products))
        print("PRODUTOS A SEREM COMPRADOS: " + str(iteration))

        for i in range(iteration):
            product_name = random.choice(list(remaining_products))  # Seleciona um produto aleatório.
            quantity = random.randint(1, 10)  # Determina uma quantidade de produtos aleatória.
            
            # Função para comprar um produto.
            print("Produto: " + str(product_name) + " x " + str(quantity))
            buy_product(self, product_name, quantity)

            # Remove o produto escolhido no conjunto cópia.
            remaining_products.remove(product_name)
            print("Produtos restantes: " + str(remaining_products))

        # Entrando no offcanvas do carrinho.
        click_button(self, "cart")

        click_button(self, "proceedToCheckout")

        # PÁGINA DE CARRINHO
        # Selecionando cartões de crédito e endereço.

        select_option_by_value(self, "salesCardsIds", "1")

        select_option(self, "address-dropdown", "Minha casa")

        click_button(self, "btn-finalizar")

        # PÁGINA DE FINALIZAR COMPRA
        #atribuindo o preço total da compra com frete para a variável "final_price".
        final_price = self.driver.find_element(By.ID, "saleFinalPrice").text
        
        input_string(self, "amountPaid1", final_price)

        click_button(self, "btn-finalizar")

    def tearDown(self):
        # Fecha o browser
        self.driver.quit()

if __name__ == "__main__":
    unittest.main()