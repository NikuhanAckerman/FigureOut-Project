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
        self.driver.get("http://localhost:8080/index")

    def test_fill_product_form(self):
        
        ## CRIAÇÃO DE ENDEREÇO
        # Clica no botão de "Ver endereços"
        element = self.driver.find_element(By.ID, "seeAddresses")
        self.driver.execute_script("arguments[0].click();", element)
        time.sleep(1)
        
        # Clica no botão "Adicionar endereço"
        element = self.driver.find_element(By.ID, "createAddress")
        self.driver.execute_script("arguments[0].click();", element)
        time.sleep(1)
        
        # Preenche os campos do formulário de criar endereço.
        if not self.driver.find_element(By.ID, "deliveryAddress").is_selected():
            element = self.driver.find_element(By.ID, "deliveryAddress")
            self.driver.execute_script("arguments[0].click();", element)
            time.sleep(1)
        
        self.driver.find_element(By.ID, "nickname").send_keys("Casa de Férias")
        time.sleep(1)
        self.driver.find_element(By.ID, "typeOfResidence").send_keys("Casa")
        time.sleep(1)
        self.driver.find_element(By.ID, "addressingType").send_keys("Avenida")
        time.sleep(1)
        self.driver.find_element(By.ID, "addressing").send_keys("das Orquideas")
        time.sleep(1)                                                              
        self.driver.find_element(By.ID, "houseNumber").send_keys("765")
        time.sleep(1)
        self.driver.find_element(By.ID, "cep").send_keys("04567-305")
        time.sleep(1)
        self.driver.find_element(By.ID, "neighbourhood").send_keys("Centro")
        time.sleep(1) 
        self.driver.find_element(By.ID, "city").send_keys("Poa")
        time.sleep(1) 
        
        Select(self.driver.find_element(By.ID, "stateSelect")).select_by_visible_text("São Paulo")
        time.sleep(1)
        Select(self.driver.find_element(By.ID, "countrySelect")).select_by_visible_text("Brasil")
        time.sleep(1)

        self.driver.find_element(By.ID, "observation").send_keys("proximo do balneario")
        time.sleep(1)
        
        # Clica no botão de criar produto(gambiarra)
        element = self.driver.find_element(By.ID, "createButton")
        self.driver.execute_script("arguments[0].click();", element)

        ## CRIAÇÃO DE CARTÃO DE CRÉDITO
        # Clica no botão de "Ver cartões"
        element = self.driver.find_element(By.ID, "seeCreditCards")
        self.driver.execute_script("arguments[0].click();", element)
        time.sleep(1)
        
        # Clica no botão "Adicionar cartão"
        element = self.driver.find_element(By.ID, "addCreditCardButton")
        self.driver.execute_script("arguments[0].click();", element)
        time.sleep(1)

        # Preenche os campos do formulário de criar cartão de crédito.
        if not self.driver.find_element(By.ID, "preferential").is_selected():
            element = self.driver.find_element(By.ID, "preferential")
            self.driver.execute_script("arguments[0].click();", element)
            time.sleep(1)
        
        self.driver.find_element(By.ID, "cardNumber").send_keys("1234567891012")
        time.sleep(1)
        self.driver.find_element(By.ID, "printedName").send_keys("Renan L")
        time.sleep(1)
        
        Select(self.driver.find_element(By.ID, "brand")).select_by_visible_text("MasterCard")
        time.sleep(1)

        self.driver.find_element(By.ID, "securityCode").send_keys("123")
        time.sleep(1)

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
