import os
import unittest
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.support.ui import Select
from webdriver_manager.chrome import ChromeDriverManager
from selenium.webdriver.chrome.options import Options

# Função para a seleção de botão de rádio pelo ID.
def select_radio(self, radio_id, value):
    radio_button = self.driver.find_element(By.ID, radio_id)
    if radio_button.get_attribute("value") == value and not radio_button.is_selected():
        radio_button.click()
        
# Clica em um botão da página utilizando javascript.
def click_button(self, element_id):
    element = self.driver.find_element(By.ID, element_id)
    self.driver.execute_script("arguments[0].click();", element)

# Envia uma string em uma caixa de texto.
def input_string(self, element_id, value):
    self.driver.find_element(By.ID, element_id).send_keys(value)
    
# Checa uma checkbox (com javascript) e verifica se já está selecionada.
def check_checkbox(self, element_id):
    if not self.driver.find_element(By.ID, element_id).is_selected():
        element = self.driver.find_element(By.ID, element_id)
        self.driver.execute_script("arguments[0].click();", element)

# Seleciona uma opção visível de um menu dropdown.
def select_option(self, element_id, value):
    Select(self.driver.find_element(By.ID, element_id)).select_by_visible_text(value)

# Busca por uma imagem e manda ela.
def send_image(self, element_id, folder, image_file):
    # Pega o caminho absoluto do diretório do script
    script_dir = os.path.dirname(os.path.abspath(__file__))

    # Construindo o caminho absoluto para o arquivo de imagem dentro da pasta de Imagens.
    file_path = os.path.join(script_dir, folder, image_file)

    # Garantir que o caminho do arquivo está certo.
    print("Caminho do arquivo: ", file_path)

    # Checar se o arquivo existe.
    if not os.path.exists(file_path):
        print("O arquivo não existe!")
    else:
        # Localizar o <input> do tipo file por seu ID e fazer o upload do arquivo.
        self.driver.find_element(By.ID, element_id).send_keys(file_path)


#if __name__ == "__main__":
#   unittest.main()
