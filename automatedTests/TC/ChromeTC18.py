import sys
import os

sys.path.insert(0, os.path.dirname(os.getcwd()))

import unittest
from selenium import webdriver
from webdriver_manager.chrome import ChromeDriverManager
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.chrome.service import Service
import time
import csv
from ChromeSeleniumFunctions import *

class CouponTest(unittest.TestCase):
    def setUp(self):
        # Configura as opções do Chrome
        chrome_options = Options()
        chrome_options.add_argument("--no-sandbox")
        chrome_options.add_argument("--disable-dev-shm-usage")

        # Iniciliza o WebDriver
        self.driver = webdriver.Chrome(service=Service(ChromeDriverManager().install()), options=chrome_options)
        self.driver.get("http://localhost:8080/coupons/")

    def test_update_coupon(self):

        input_string(self, "inputPromotionalCode-3", "PODPAH50")
        time.sleep(1)
        click_button(self, "changePromotionalCode-3")
        time.sleep(2)

        input_string(self, "inputPromotionalDiscount-3", "50")
        time.sleep(1)
        click_button(self, "changePromotionalDiscount-3")
        time.sleep(2)

        input_string(self, "inputPromotionalDate-3", "02-03-2025")
        time.sleep(1)
        click_button(self, "changePromotionalDate-3")

        # Espera alguns segundos para antes de fechar o browser.
        time.sleep(4)

    def tearDown(self):
        # Fecha o browser
        self.driver.quit()

if __name__ == "__main__":
    unittest.main()
