package org.w3;


import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class DataToDatabase
{
    private static final String API_KEY="6846a4c25fd98a8726752a391a1efe05";

    private static final String DB_URL="jdbc:postgresql://localhost:5432/weather_app";
    private static final String USER="postgres";
    private static final String PASSWORD="root";





    // fetch
    private static JSONObject fetchData(String city) throws IOException {
        String apiURL= "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + API_KEY;
        URL url=new URL(apiURL);
        HttpURLConnection connection= (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader reader=new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response=new StringBuilder();
        String line;

        while ((line=reader.readLine())!=null){
            response.append(line);
        }
        reader.close();

        return new JSONObject(response.toString());
    }

    // insertData
    private static void insertData(JSONObject object) throws SQLException {
        Connection connection= DriverManager.getConnection(DB_URL, USER, PASSWORD);
        String sql="insert into weather_data (city, temp, humidity) values(?, ?, ?)";

        PreparedStatement statement=connection.prepareStatement(sql);
//        statement.setInt(1, object.getInt("id"));
        statement.setString(1,object.getString("name"));
        statement.setDouble(2, object.getJSONObject("main").getDouble("temp"));
        statement.setDouble(3, object.getJSONObject("main").getDouble("humidity"));
        statement.execute();
        connection.close();
    }
    public static void main( String[] args ) throws IOException, SQLException {
        String city="Bukhara";
        JSONObject object=fetchData(city);
        // System.out.println(object.toString());
        insertData(object);
    }

}




