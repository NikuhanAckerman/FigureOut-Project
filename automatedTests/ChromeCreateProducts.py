import unittest
from selenium import webdriver
from webdriver_manager.chrome import ChromeDriverManager
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.chrome.service import Service
import time
import csv
from ChromeSeleniumFunctions import *

class ProductFormTest(unittest.TestCase):
    def setUp(self):
        # Configura as opções do Chrome
        chrome_options = Options()
        chrome_options.add_argument("--no-sandbox")
        chrome_options.add_argument("--disable-dev-shm-usage")

        # Iniciliza o WebDriver
        self.driver = webdriver.Chrome(service=Service(ChromeDriverManager().install()), options=chrome_options)
        self.driver.get("http://localhost:8080/products/seeProducts")

    def test_fill_product_form(self):
        '''
        # -- FUNÇÕES CRIADAS PARA TESTES --
        # click_button(self, id): Clica em botão da página.
        # input_string(self, id, valor): Insere texto.
        # select_radio(self, id, valor): Seleciona botão de rádio.
        # check_checkbox(self, id): Checha checkbox.
        # select_option(self, id, valor): Seleciona opção de um menu dropdown.
        # send_image(self, id, pasta, arquivo): Envia uma imagem.
        # create_product(self, nome, descrição, altura, largura, comprimento, peso
                        preçoDeCompra, categoria, grupoDePrecificação, preço, QuantidadeDisponível
                        dataDeEntrada, fornecedor, fabricante, tamanho, foto)
        '''
        #create_product(self, "Hu Tao", "Action figure colecionável da personagem Hu Tao de Genshin Impact.", "10.5", "7.5", "5.6", "31.5", "195.56", "Figura de ação", "Ferro (5.00%)", "210.89", "15", "06-10-2023", "Fornecedor A", "Bandai", "1/8", "hutao.jpg")
        #create_product(self, "Paimon", "Action Figure da personagem principal de Genshin Impact Paimon.", "8.5", "5.5", "5.0", "22.0", "110.16", "Figura de ação", "Ferro (5.00%)", "129.90", "25", "06-10-2023", "Fornecedor A", "Bandai", "1/8", "paimon.jpg")

        with open('products.csv', mode='r', encoding='utf-8') as csvfile:
            reader = csv.DictReader(csvfile)

            for row in reader:
                create_product(self, row['Nome'], row['Descricao'], row['Altura'], row['Largura'], row['Comprimento'], row['Peso'], row['PrecoDeCompra'], row['Categoria'], row['GrupoDePrecificacao'], row['Preco'], row['QuantidadeDisponivel'], row['DataDeEntrada'], row['Fornecedor'], row['Fabricante'], row['Tamanho'], row['Foto'])
        
        # Espera alguns segundos para antes de fechar o browser.
        time.sleep(10)

    def tearDown(self):
        # Fecha o browser
        self.driver.quit()

if __name__ == "__main__":
    unittest.main()
