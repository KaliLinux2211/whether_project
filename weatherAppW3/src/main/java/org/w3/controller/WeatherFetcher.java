package org.w3.controller;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;

// to take record from db
public class WeatherFetcher {
    private static final String dbURL="jdbc:postgresql://localhost:5432/weather_app";
    private static final String USER="postgres";
    private static final String password="root";

   public static JSONArray getRecordFromDB() throws SQLException {
       JSONArray recordList=new JSONArray();
       Connection connectionDB= DriverManager.getConnection(dbURL, USER, password);
       String sqlQuery="select * from weather_data";
       PreparedStatement preparedStatement=connectionDB.prepareStatement(sqlQuery);
       ResultSet resultSet=preparedStatement.executeQuery();
       while (resultSet.next()){
           JSONObject record=new JSONObject();
           record.put("city", resultSet.getString("city"));
           record.put("temperature", resultSet.getDouble("temp"));
           record.put("humidity", resultSet.getDouble("humidity"));
           recordList.put(record);
       }
        return recordList;
    }
    public static void main(String[] args) throws SQLException {
        System.out.println(getRecordFromDB().toString());
    }

}
