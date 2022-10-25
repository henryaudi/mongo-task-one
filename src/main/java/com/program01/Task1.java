package com.program01;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Task1 {
    public static void main(String[] args) throws IOException {

        // Creating a Mongo client
        MongoClient mongo = new MongoClient("localhost", 27017);

        // Creating credentials
        MongoCredential credential;
        credential = MongoCredential.createCredential("sampleUser", "myDb",
                "password".toCharArray());
        System.out.println("Connected to the database successfully");

        // Accessing newDB database for program 1
        MongoDatabase newDB = mongo.getDatabase("newDB");

        // Accessing restaurant collections
        MongoCollection<Document> restaurant = newDB.getCollection("restaurant");

        // Open reader
        BufferedReader reader = new BufferedReader(new FileReader("restaurant.csv"));

        // Read from .csv file
        String line = reader.readLine();
        while (line != null) {
            // Split the line into different cell values each time "," occurs
            String[] item = line.split(",");
            // Add to HashMap
            Map<String, Object> map = new HashMap<>();
            map.put("restaurant", item[1]);
            map.put("city", item[2]);
            map.put("state", item[3]);
            Document document = new Document(map);
            restaurant.insertOne(document);
            line = reader.readLine();
        }

        mongo.close();
        reader.close();
    }
}
