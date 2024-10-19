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
        
        # Preenche os campos do formulário.
        input_string(self, "name", "Test Product")
        time.sleep(1)

        input_string(self, "description", "Descrição teste.")
        time.sleep(1)
        
        input_string(self, "height", "10.5")
        time.sleep(1)
        
        input_string(self, "width", "5.5")
        time.sleep(1)
        
        input_string(self, "length", "15.0")
        time.sleep(1)
        
        input_string(self, "weight", "2.0")
        time.sleep(1)
        
        input_string(self, "purchaseAmount", "20.0")
        time.sleep(1)
        
        select_option(self, "categorySelect", "Chaveiro")
        time.sleep(1)
        
        select_option(self, "pricingGroupSelect", "Ferro (5.00%)")
        time.sleep(1)

        input_string(self, "price", "80.0")
        time.sleep(1)

        input_string(self, "productQuantityAvailable", "5")
        time.sleep(1)

        input_string(self, "entryInStockDate", "06-10-2023")
        time.sleep(1)
        
        select_option(self, "supplier", "Fornecedor A")
        time.sleep(1)
        
        select_option(self, "manufacturer", "Bandai")
        time.sleep(1)
        
        select_option(self, "size", "1/6")
        time.sleep(1)

        send_image(self, "fileInput", "Images", "image.png")
        time.sleep(1)
        
        click_button(self, "createButton")
        time.sleep(1)

        # Espera alguns segundos para antes de fechar o browser.
        time.sleep(10)

    def tearDown(self):
        # Fecha o browser
        self.driver.quit()

if __name__ == "__main__":
    unittest.main()
