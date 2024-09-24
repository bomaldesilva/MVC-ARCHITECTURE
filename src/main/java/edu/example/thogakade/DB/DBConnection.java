package edu.example.thogakade.DB;

import edu.example.thogakade.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBConnection {
    public static DBConnection instance;
    private Connection connection;
    private DBConnection(){
        try {
            connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/Thogakade", "root", "rootpassword");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Connection getConnection(){
        return connection;
    }
    public static DBConnection getInstance() {
        return null==instance?instance=new DBConnection():instance;
    }

}
