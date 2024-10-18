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


    # Função para a seleção de botão de rádio pelo ID.
    def select_radio(self, radio_id, value):
        radio_button = self.driver.find_element(By.ID, radio_id)
        if radio_button.get_attribute("value") == value and not radio_button.is_selected():
            radio_button.click()


    def test_fill_product_form(self):

        # CLica no botão para criar cliente na página "index".
        element = self.driver.find_element(By.ID, "createClientButton")
        self.driver.execute_script("arguments[0].click();", element)
        
        # Preenche os campos do formulário.
        self.driver.find_element(By.ID, "name").send_keys("Renan Luiz")
        time.sleep(1)
        self.driver.find_element(By.ID, "email").send_keys("renan@protonmail.com")
        time.sleep(1)
        self.driver.find_element(By.ID, "password").send_keys("senhaForte123@")
        time.sleep(1)
        self.driver.find_element(By.ID, "confirmPassword").send_keys("senhaForte123@")
        time.sleep(1)
        self.driver.find_element(By.ID, "birthday").send_keys("07-10-2003")
        time.sleep(1)
        self.driver.find_element(By.ID, "cpf").send_keys("521.193.488-06")
        time.sleep(1)

        # Checa se a caixa de seleção já está selecionada, senão a seleciona.
        if not self.driver.find_element(By.ID, "enabled").is_selected():
            self.driver.find_element(By.ID, "enabled").click()
            time.sleep(1)
        
        # Selecionando o botão de rádio de gênero e de tipo de telefone.
        self.select_radio("gender", "1")
        time.sleep(1)
        self.select_radio("phoneFalse", "false")
        time.sleep(1)
                                                                
        self.driver.find_element(By.ID, "ddd").send_keys("11")
        time.sleep(1)

        self.driver.find_element(By.ID, "phoneNumber").send_keys("95121234")
        time.sleep(1)

        if not self.driver.find_element(By.ID, "deliveryAddress").is_selected():
            element = self.driver.find_element(By.ID, "deliveryAddress")
            self.driver.execute_script("arguments[0].click();", element)
            time.sleep(1)
        if not self.driver.find_element(By.ID, "chargingAddress").is_selected():
            element = self.driver.find_element(By.ID, "chargingAddress")
            self.driver.execute_script("arguments[0].click();", element)
            time.sleep(1)
        #if not self.driver.find_element(By.ID, "chargingAddress").is_selected():
         #   self.driver.find_element(By.ID, "chargingAddress").click()

        self.driver.find_element(By.ID, "nickname").send_keys("Casa")
        time.sleep(1)
        self.driver.find_element(By.ID, "typeOfResidence").send_keys("Casa")
        time.sleep(1)
        self.driver.find_element(By.ID, "addressingType").send_keys("Rua")
        time.sleep(1)
        self.driver.find_element(By.ID, "addressing").send_keys("Jardelina")
        time.sleep(1)                                                              
        self.driver.find_element(By.ID, "houseNumber").send_keys("750")
        time.sleep(1)
        self.driver.find_element(By.ID, "cep").send_keys("08730-300")
        time.sleep(1)
        self.driver.find_element(By.ID, "neighbourhood").send_keys("Parque Santana")
        time.sleep(1) 
        self.driver.find_element(By.ID, "city").send_keys("Mogi das Cruzes")
        time.sleep(1) 
        
        Select(self.driver.find_element(By.ID, "stateSelect")).select_by_visible_text("São Paulo")
        time.sleep(1)
        Select(self.driver.find_element(By.ID, "countrySelect")).select_by_visible_text("Brasil")
        time.sleep(1)

        self.driver.find_element(By.ID, "observation").send_keys("próximo do supermercado Nagumo")
        time.sleep(1)
        
        # Clica no botão de criar cliente(gambiarra)
        element = self.driver.find_element(By.ID, "createButton")
        self.driver.execute_script("arguments[0].click();", element)

        # Espera alguns segundos para o resultado.
        time.sleep(10)

    def tearDown(self):
        # Fecha o browser
        self.driver.quit()

if __name__ == "__main__":
    unittest.main()
