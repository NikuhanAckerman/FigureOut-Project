import unittest
from selenium import webdriver
from webdriver_manager.chrome import ChromeDriverManager
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.chrome.service import Service
import time
from ChromeSeleniumFunctions import *

class ProductFormTest(unittest.TestCase):
    def setUp(self):
        # Configura as opções do Chrome.
        chrome_options = Options()
        chrome_options.add_argument("--no-sandbox")
        chrome_options.add_argument("--disable-dev-shm-usage")

        # Iniciliza o WebDriver.
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
        # create_client(self, nome, email, senha, nascimento, cpf,
        #               ativo=1, M=1;F=2;?=3)
        # create_phone(self, fixo=1, ddd, numero)
        # create_address(self, entrega=1, cobrança=1, apelido, casa, rua, logradouro,
        #                numero, cep, bairro, cidade, estado, observação="")
        
        # Informações gerais.
        create_client(self, "Otávio Monteiro", "otavio@gmail.com", "OJt1IjXcJp#", "05-01-1981", "861.841.887-04", "1", "1")
        create_phone(self, "1", "68", "37127030")
        create_address(self, "1", "1", "Minha casa", "Casa", "Rua", "Antonio Ribeiro", "506", "69908-886", "Santo Afonso", "Rio Branco", "Acre", "")

        create_client(self, "Tereza da Costa", "tereza99@yahoo.com", "mw@guLPdRB6", "07-02-1999", "334.989.192-61", "1", "2")
        create_phone(self, "1", "24", "39140372")
        create_address(self, "1", "1", "Minha casa", "Casa", "Alamada", "Oito", "585", "27337-040", "São Sebastião", "Barra Mansa", "Rio de Janeiro", "")

        create_client(self, "Sophia Gomes", "sophia02@outlook.com", "w54$bqGHZh4", "09-10-2002", "661.678.776-10", "0", "2")
        create_phone(self, "1", "85", "35232380")
        create_address(self, "1", "1", "Minha casa", "Casa", "Rua", "Madre Joana Angélica", "874", "60810-120", "Patriolino Ribeiro", "Fortaleza", "Ceará", "")

        create_client(self, "Alex Cavalcanti", "alex661@gmail.com", "6k!Y9HPangh", "06-30-2000", "603.711.592-37", "1", "3")
        create_phone(self, "0", "45", "983251589")
        create_address(self, "1", "1", "Minha casa", "Casa", "Rua", "Cristo Redentor", "314", "85805-380", "Pioneiros Catarinenses", "Cascavel", "Paraná", "")

        create_client(self, "Anderson Ferreira", "anderson16@gmail.com", "57C%XfF2Hbc", "06-30-2000", "473.342.770-07", "1", "1")
        create_phone(self, "1", "65", "35836938")
        create_address(self, "1", "1", "Meu apê", "Apartamento", "Rua", "Projeta 24", "279", "78075-587", "Jardim Universitário", "Cuiabá", "Mato Grosso", "Bloco Ametista, apt. 36")
        
        create_client(self, "Priscila Isis", "prisis@protonmail.com", "aIY#i9rCbRxQTw!pf32&Mxy", "05-01-2002", "114.091.557-65", "1", "2")
        create_phone(self, "0", "95", "989270516")
        create_address(self, "1", "1", "Minha casa", "Casa", "Rua", "Sorocaima", "823", "69303-400", "São Vincente", "Boa Vista", "Roraima", "")

        create_client(self, "Fábio Castro", "fabio635@gmail.com", "Qy91cp@HCwa", "03-18-1995", "173.311.261-80", "1", "1")
        create_phone(self, "1", "51", "35156919")
        create_address(self, "1", "1", "Minha casa", "Casa", "Rua", "dos Tupis", "662", "94150-430", "Barnabé", "Gravatai", "Rio Grande do Sul", "")

        create_client(self, "Joaquim Campos", "jocampos@outlook.com", "QTwpf32!Mxy", "09-05-1998", "617.333.601-40", "1", "1")
        create_phone(self, "0", "11", "989945578")
        create_address(self, "1", "1", "Minha casa", "Casa", "Praça", "Del-Duomo", "199", "13329-216", "João Jabour", "Salto", "São Paulo", "")

        create_client(self, "Marcos Abreu", "marcos123@gmail.com", "$Enha123", "05-13-1995", "024.125.848-00", "0", "1")
        create_phone(self, "0", "14", "982457580")
        create_address(self, "1", "1", "Meu apartamento", "Apartamento", "Rua", "Helcias Kerr Nogueira", "221", "17021-170", "Parque Residencial Castelo", "Bauru", "São Paulo", "Bloco 1, apt. 51")

        # Obs: Por algum motivo, a data de nascimento da Vanessa é bugada no Selenium e ele adiciona um "1" antes do "2003" sempre, então o dia tem que ter um caractere a menos.
        create_client(self, "Vanessa Mota", "vanessa55@yahoo.com", "nMqo7#njupi", "01-2-2003", "796.678.528-90", "1", "2")
        create_phone(self, "1", "19", "25887544")
        create_address(self, "1", "1", "Minha casa", "Casa", "Rua", "Itália", "939", "13411-508", "Santana", "Piracicaba", "São Paulo", "")


    def tearDown(self):
        # Fecha o browser
        self.driver.quit()

if __name__ == "__main__":
    unittest.main()
