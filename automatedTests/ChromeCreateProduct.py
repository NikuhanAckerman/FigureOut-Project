import os
import unittest
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.support.ui import Select
from webdriver_manager.chrome import ChromeDriverManager
from selenium.webdriver.chrome.options import Options
import time

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
        
        # Preenche os campos do formulário.
        self.driver.find_element(By.ID, "name").send_keys("Test Product")
        time.sleep(1)
        self.driver.find_element(By.ID, "height").send_keys("10.5")
        time.sleep(1)
        self.driver.find_element(By.ID, "width").send_keys("5.5")
        time.sleep(1)
        self.driver.find_element(By.ID, "length").send_keys("15.0")
        time.sleep(1)
        self.driver.find_element(By.ID, "weight").send_keys("2.0")
        time.sleep(1)
        self.driver.find_element(By.ID, "purchaseAmount").send_keys("20.0")
        time.sleep(1)
        
        Select(self.driver.find_element(By.ID, "categorySelect")).select_by_visible_text("Chaveiro")
        time.sleep(1)
        Select(self.driver.find_element(By.ID, "pricingGroupSelect")).select_by_visible_text("Ferro (5.00%)")
        time.sleep(1)
        Select(self.driver.find_element(By.ID, "supplier")).select_by_visible_text("Fornecedor A")
        time.sleep(1)
        
        self.driver.find_element(By.ID, "price").send_keys("80.0")
        time.sleep(1)
        self.driver.find_element(By.ID, "productQuantityAvailable").send_keys("5")
        time.sleep(1)
        self.driver.find_element(By.ID, "entryInStockDate").send_keys("06-10-2023")
        time.sleep(1)

        # Código imenso para ter certeza que ele vai enviar a imagem.
        # Pega o caminho absoluto do diretório do script
        script_dir = os.path.dirname(os.path.abspath(__file__))

        # Construindo o caminho absoluto para o arquivo de imagem dentro da pasta de Imagens.
        file_path = os.path.join(script_dir, "Images", "image.png")

        # Garantir que o caminho do arquivo está certo.
        print("Caminho do arquivo: ", file_path)

        # Checar se o arquivo existe.
        if not os.path.exists(file_path):
            print("O arquivo não existe!")
        else:
            # Localizar o <input> do tipo file por seu ID e fazer o upload do arquivo.
            self.driver.find_element(By.ID, "fileInput").send_keys(file_path)
        
        # Clica no botão de criar produto(gambiarra)
        element = self.driver.find_element(By.ID, "createButton")
        self.driver.execute_script("arguments[0].click();", element)

        # Espera alguns segundos para o resultado.
        time.sleep(10)

    def tearDown(self):
        # Fecha o browser
        self.driver.quit()

if __name__ == "__main__":
    unittest.main()
