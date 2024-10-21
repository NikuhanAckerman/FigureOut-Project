import unittest
from selenium import webdriver
from webdriver_manager.chrome import ChromeDriverManager
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.chrome.service import Service
import time
from ChromeSeleniumFunctions import *

class ProductFormTest(unittest.TestCase):
    def setUp(self):
        # Configura as opções do Chrome
        chrome_options = Options()
        chrome_options.add_argument("--no-sandbox")
        chrome_options.add_argument("--disable-dev-shm-usage")

        # Iniciliza o WebDriver
        self.driver = webdriver.Chrome(service=Service(ChromeDriverManager().install()), options=chrome_options)
        self.driver.get("http://localhost:8080/products/createProduct")

    def test_fill_product_form(self):

        # -- FUNÇÕES CRIADAS PARA TESTES --
        # click_button(self, id): Clica em botão da página.
        # input_string(self, id, valor): Insere texto.
        # select_radio(self, id, valor): Seleciona botão de rádio.
        # check_checkbox(self, id): Checha checkbox.
        # select_option(self, id, valor): Seleciona opção de um menu dropdown.
        # send_image(self, id, pasta, arquivo): Envia uma imagem.
        
        # Criação do produto "Hu tao".
        input_string(self, "name", "Hu Tao")

        input_string(self, "description", "Action figure colecionável da personagem Hu Tao de Genshin Impact.")
        
        input_string(self, "height", "10.5")
        
        input_string(self, "width", "7.5")
        
        input_string(self, "length", "5.6")
        
        input_string(self, "weight", "31.5")
        
        input_string(self, "purchaseAmount", "195.56")
        
        select_option(self, "categorySelect", "Figura de ação")
        
        select_option(self, "pricingGroupSelect", "Ferro (5.00%)")

        input_string(self, "price", "210.89")

        input_string(self, "productQuantityAvailable", "15")

        input_string(self, "entryInStockDate", "06-10-2023")
        
        select_option(self, "supplier", "Fornecedor A")
        
        select_option(self, "manufacturer", "Bandai")
        
        select_option(self, "size", "1/8")

        send_image(self, "fileInput", "Images", "hutao.jpg")
        
        click_button(self, "createButton")

        # Criação do produto "Paimon"
        click_button(self, "createProduct")
        
        input_string(self, "name", "Paimon")

        input_string(self, "description", "Action Figure da personagem principal de Genshin Impact Paimon.")
        
        input_string(self, "height", "8.5")
        
        input_string(self, "width", "5.5")
        
        input_string(self, "length", "5.0")
        
        input_string(self, "weight", "22.0")
        
        input_string(self, "purchaseAmount", "110.16")
        
        select_option(self, "categorySelect", "Figura de ação")
        
        select_option(self, "pricingGroupSelect", "Ferro (5.00%)")

        input_string(self, "price", "129.90")

        input_string(self, "productQuantityAvailable", "25")

        input_string(self, "entryInStockDate", "06-10-2023")
        
        select_option(self, "supplier", "Fornecedor A")
        
        select_option(self, "manufacturer", "Bandai")
        
        select_option(self, "size", "1/8")

        send_image(self, "fileInput", "Images", "paimon.jpg")
        
        click_button(self, "createButton")

        # Espera alguns segundos para antes de fechar o browser.
        time.sleep(10)

    def tearDown(self):
        # Fecha o browser
        self.driver.quit()

if __name__ == "__main__":
    unittest.main()
