package org.w3.api;

import org.json.JSONArray;
import org.w3.controller.WeatherFetcher; // Importing WeatherFetcher class
import spark.Request;
import spark.Response;

import java.sql.SQLException;

import static spark.Spark.*; // Importing Spark static methods

public class WeatherDBAPI {

    // Method to handle HTTP GET request for weather data
    private static String getJSONdata(Request request, Response response) throws SQLException {
        // Creating a JSONArray to store weather data
        JSONArray recordList = new JSONArray();

        // Fetching weather data from the WeatherFetcher class
        recordList = WeatherFetcher.getRecordFromDB(); // Assuming this method retrieves data from a database

        // Setting response type to JSON
        response.type("application/json");

        // Returning the weather data as a JSON string
        return recordList.toString();
    }

    public static void main(String[] args) {

        port(8080); // Set port to 8080

        // Enable CORS for all origins, methods, and headers
        before((req, res) -> {
            res.header("Access-Control-Allow-Origin", "*");
            res.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            res.header("Access-Control-Allow-Headers", "Content-Type, Authorization, Accept");
            res.header("Access-Control-Allow-Credentials", "true");
        });

        // Define route to fetch weather data
        get("/weather", ((request, response) -> getJSONdata(request, response))); // Define a GET route for /weather
    }
}

