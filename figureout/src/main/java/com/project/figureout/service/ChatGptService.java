package com.project.figureout.service;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.figureout.model.Category;
import com.project.figureout.model.Product;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatGptService {

    @Value("${openai.api.key}")
    private String apiKey;

    //@Value("${productsJSONFile.filepath}")
    //private String productsJSONFilepath;

    public String getResponseFromChatGpt(String prompt) {
        String url = "https://api.openai.com/v1/chat/completions";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");

        String requestBody = "{ \"model\": \"gpt-3.5-turbo\", \"messages\": [{\"role\": \"user\", \"content\": \"" + prompt + "\"}]}";

        System.out.println(prompt);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        JSONObject jsonResponse = new JSONObject(response.getBody());
        String content = jsonResponse
                .getJSONArray("choices")
                .getJSONObject(0)
                .getJSONObject("message")
                .getString("content");

        return content;
    }

    /*
    public Path getFilePath() {
        return Paths.get(productsJSONFilepath);
    }

    public void ensureFileExists() throws IOException {
        Path filePath = getFilePath();

        File file = filePath.toFile();
        if (!file.exists()) {
            Files.createFile(filePath);
            Files.writeString(filePath, "[]");
        }

    }

    public JSONObject constructProductJSON(Product product) {

        JSONObject productJSON = new JSONObject();
        productJSON.put("id", product.getId());
        productJSON.put("nome", product.getName());
        productJSON.put("descrição", product.getDescription());
        productJSON.put("preço", product.getPrice());
        productJSON.put("quantidade disponível em estoque", product.getStocks().getLast().getProductQuantityAvailable());
        productJSON.put("altura (cm)", product.getHeight());
        productJSON.put("comprimento (cm)", product.getLength());
        productJSON.put("largura (cm)", product.getWidth());
        productJSON.put("peso (g)", product.getWeight());

        List<String> categoryNames = product.getCategories()
                .stream()
                .map(Category::getName)
                .collect(Collectors.toList());
        productJSON.put("categorias", String.join(", ", categoryNames));

        productJSON.put("categoria(s)", categoryNames);
        productJSON.put("escala da action figure", product.getSize().getName());
        productJSON.put("fabricante da action figure", product.getManufacturer().getName());

        return productJSON;

    }

    public JSONArray readProductsFromFile() throws IOException {
        Path filePath = getFilePath();
        String content = Files.readString(filePath);
        return new JSONArray(content); // Parse content as JSONArray
    }

    public void writeProductsToFile(JSONArray productsArray) throws IOException {
        Path filePath = getFilePath();
        Files.writeString(filePath, productsArray.toString(2)); // Write with indentation for readability
    }

    public void addProduct(Product product) throws IOException {
        ensureFileExists();
        JSONArray productsArray = readProductsFromFile();
        JSONObject productJSON = constructProductJSON(product);
        productsArray.put(productJSON); // Add new product
        writeProductsToFile(productsArray);
    }

    public void updateProduct(Long productId, Product updatedProduct) throws IOException {
        ensureFileExists();
        JSONArray productsArray = readProductsFromFile();
        JSONObject updatedProductJSON = constructProductJSON(updatedProduct);

        for (int i = 0; i < productsArray.length(); i++) {
            JSONObject product = productsArray.getJSONObject(i);
            if (product.getLong("id") == productId) {
                productsArray.put(i, updatedProductJSON); // Replace with updated product
                break;
            }
        }
        writeProductsToFile(productsArray);
    }

    public void deleteProduct(Long productId) throws IOException {
        ensureFileExists();
        JSONArray productsArray = readProductsFromFile();

        for (int i = 0; i < productsArray.length(); i++) {
            JSONObject product = productsArray.getJSONObject(i);
            if (product.getLong("id") == productId) {
                productsArray.remove(i); // Remove product
                break;
            }
        }
        writeProductsToFile(productsArray);
    } */

}
