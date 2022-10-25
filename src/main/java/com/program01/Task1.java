package com.program01;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
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

        // Create MongoDB client
        String connectionString = System.getenv("mongodb.uri");
        MongoClient mongo = MongoClients.create(connectionString);

        // Accessing newDB database for program 1
        MongoDatabase newDB = mongo.getDatabase("newDb");

        // Accessing restaurant collections
        MongoCollection<Document> restaurant = newDB.getCollection("restaurant");

        // Open reader
        BufferedReader reader = new BufferedReader(new FileReader("restaurant.csv"));

        // Read first line from .csv file
        String line = reader.readLine();
        while (line != null) {
            // Split the line into different cell values each time "," occurs
            String[] item = line.split(",");
            // Add to HashMap
            Map<String, Object> map = new HashMap<>();
            map.put("restaurant", item[0]);
            map.put("city", item[1]);
            map.put("state", item[2]);
            Document document = new Document(map);
            // Insert the document into Mongo database
            restaurant.insertOne(document);
            line = reader.readLine();
        }

        mongo.close();
        reader.close();
    }
}
