package com.example.demo;

import java.sql.*;
import java.util.ArrayList;

public class DBManager {
    private static Connection connection;

    static{
        try {
            Class.forName("org.postgresql.Driver");

            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/front",
                    "postgres", "postgres");

        } catch (Exception e){
            e.printStackTrace();
        }


    }

    public static boolean addItem(Item item){
        int rows = 0;

        try {
            PreparedStatement statement = connection.prepareStatement("" +
                    "INSERT INTO public.item (text, done, date) " +
                            " VALUES (?, ?, ?) ");

            statement.setString(1, item.getText());
            statement.setBoolean(2, item.isDone());
            statement.setDate(3, item.getDate());

            rows = statement.executeUpdate();

            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rows > 0;
    }

    public static ArrayList<Item> getItems(){
        ArrayList<Item> items = new ArrayList<>();

        try{
            PreparedStatement statement = connection.prepareStatement("" +
                    "SELECT * FROM item " +
                    "ORDER BY date ASC ");

            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                items.add(new Item(
                        rs.getInt("id"),
                        rs.getString("text"),
                        rs.getBoolean("done"),
                        rs.getDate("date")
                ));
            }

            statement.close();

        }catch (Exception e){
            e.printStackTrace();
        }

        return items;
    }

    public static ArrayList<Item> getActiveItems(){
        ArrayList<Item> items = new ArrayList<>();

        try{
            PreparedStatement statement = connection.prepareStatement("" +
                    "SELECT * FROM item " +
                    "WHERE item.done = false " +
                    "ORDER BY date ASC ");

            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                items.add(new Item(
                        rs.getInt("id"),
                        rs.getString("text"),
                        rs.getBoolean("done"),
                        rs.getDate("date")
                ));
            }

            statement.close();

        }catch (Exception e){
            e.printStackTrace();
        }

        return items;
    }

    public static ArrayList<Item> getDoneItems(){
        ArrayList<Item> items = new ArrayList<>();

        try{
            PreparedStatement statement = connection.prepareStatement("" +
                    "SELECT * FROM item " +
                    "WHERE item.done = true " +
                    "ORDER BY date ASC ");

            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                items.add(new Item(
                        rs.getInt("id"),
                        rs.getString("text"),
                        rs.getBoolean("done"),
                        rs.getDate("date")
                ));
            }

            statement.close();

        }catch (Exception e){
            e.printStackTrace();
        }

        return items;
    }

    public static Item getItem(Integer id){
        Item item = null;
        try{
            PreparedStatement statement = connection.prepareStatement("" +
                    "SELECT * FROM item " +
                    "WHERE id = ? " );

            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if(rs.next()){
                item = new Item(
                        rs.getInt("id"),
                        rs.getString("text"),
                        rs.getBoolean("done"),
                        rs.getDate("date")
                );
            }

            statement.close();

        }catch (Exception e){
            e.printStackTrace();
        }

        return item;
    }

    public static boolean saveItem(Item item){

        int rows = 0;

        try {
            PreparedStatement statement = connection.prepareStatement("" +
                    "UPDATE item SET text = ?, done = ?, date = ? " +
                    "WHERE id = ?");

            statement.setString(1, item.getText());
            statement.setBoolean(2, item.isDone());
            statement.setDate(3, item.getDate());
            statement.setInt(4, item.getId());

            rows = statement.executeUpdate();

            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rows > 0;
    }

    public static boolean deleteItem(Integer id){

        int rows = 0;

        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM item " +
                    "WHERE id = ?");

            statement.setInt(1, id);

            rows = statement.executeUpdate();

            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rows > 0;
    }

    public static ArrayList<Item> getActive(){
        ArrayList<Item> items = new ArrayList<>();

        try{
            PreparedStatement statement = connection.prepareStatement("" +
                    "SELECT * FROM item " +
                    "WHERE done = true ");

            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                items.add(new Item(
                        rs.getInt("id"),
                        rs.getString("text"),
                        rs.getBoolean("done"),
                        rs.getDate("date")
                ));
            }

            statement.close();

        }catch (Exception e){
            e.printStackTrace();
        }

        return items;
    }

    public static ArrayList<Item> getDone(){
        ArrayList<Item> items = new ArrayList<>();

        try{
            PreparedStatement statement = connection.prepareStatement("" +
                    "SELECT * FROM item " +
                    "WHERE done = false ");

            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                items.add(new Item(
                        rs.getInt("id"),
                        rs.getString("text"),
                        rs.getBoolean("done"),
                        rs.getDate("date")
                ));
            }

            statement.close();

        }catch (Exception e){
            e.printStackTrace();
        }

        return items;
    }
}
