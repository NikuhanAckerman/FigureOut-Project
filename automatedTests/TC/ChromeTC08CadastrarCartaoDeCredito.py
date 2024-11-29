import sys
import os

sys.path.insert(0, os.path.dirname(os.getcwd()))

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
        self.driver.get("http://localhost:8080/showAllClients")

    def test_fill_product_form(self):

        # -- FUNÇÕES CRIADAS PARA TESTES --
        # click_button(self, id): Clica em botão da página.
        # input_string(self, id, valor): Insere texto.
        # select_radio(self, id, valor): Seleciona botão de rádio.
        # check_checkbox(self, id): Checha checkbox.
        # select_option(self, id, valor): Seleciona opção de um menu dropdown.
        # send_image(self, id, pasta, arquivo): Envia uma imagem.
        

        ## CRIAÇÃO DE CARTÃO DE CRÉDITO
        # Clica no botão de "Ver cartões"
        click_button(self, "seeCreditCards-1")
        time.sleep(1)
        
        # Clica no botão "Adicionar cartão"
        click_button(self, "addCreditCardButton")
        time.sleep(1)

        # Preenche os campos do formulário de criar cartão de crédito.

        input_string(self, "cardNumber", "1234567891012")
        time.sleep(1)

        input_string(self, "nickname", "Cartao Roxo")
        time.sleep(1)
        
        input_string(self, "printedName", "O Monteiro")
        time.sleep(1)
        
        select_option(self, "brand", "MasterCard")
        time.sleep(1)
        
        input_string(self, "securityCode", "123")
        time.sleep(1)

        click_button(self, "createButton")
        time.sleep(1)
        
        # Espera alguns segundos para o resultado.
        time.sleep(4)

    def tearDown(self):
        # Fecha o browser
        self.driver.quit()

if __name__ == "__main__":
    unittest.main()
