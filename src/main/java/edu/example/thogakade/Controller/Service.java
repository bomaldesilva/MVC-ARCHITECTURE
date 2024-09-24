package edu.example.thogakade.Controller;

import edu.example.thogakade.CRUD.CrudUtil;
import edu.example.thogakade.DB.DBConnection;
import edu.example.thogakade.model.Customer;
import edu.example.thogakade.model.Item;
import edu.example.thogakade.model.OrderDetails;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Service implements ServiceImpl{


    @Override
    public boolean addCustomer(Customer customer) {
        try {
            String SQL = "insert into Customer Values(?,?,?,?,?,?,?,?,?)";
            CrudUtil.execute(SQL,customer.getId(),customer.getTitle(),customer.getName(),customer.getDob(),customer.getSalary(),customer.getAddress(),customer.getCity()
            ,customer.getProvince(),customer.getProvince());
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObservableList<Customer> allData() {
        ObservableList<Customer> customerObservableList = FXCollections.observableArrayList();
        String SQL = "SELECT * FROM Customer";
        try {
            ResultSet resultSet = CrudUtil.execute(SQL);
            while (resultSet.next()){
                Customer customer =new Customer(
                        resultSet.getString("CustID"),
                        resultSet.getString("CustTitle"),
                        resultSet.getString("CustName"),
                        resultSet.getString("CustAddress"),
                        resultSet.getDate("DOB").toLocalDate(),
                        resultSet.getDouble("salary"),
                        resultSet.getString("City"),
                        resultSet.getString("Province"),
                        resultSet.getString("PostalCode")
                );
                customerObservableList.add(customer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customerObservableList;
    }

    @Override
    public boolean deleteCustomer(String id) {
        String SQL = "Delete from Customer where CustID='"+id+"'";
        try {
            Object execute = CrudUtil.execute(SQL);
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResultSet searchCustomer(String id) {
        String SQL = "select * from Customer where CustID='"+id+"'";
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            ResultSet result=connection.prepareStatement(SQL).executeQuery();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean addItem(Item item) {
        String SQL = "insert into Item Values(?,?,?,?,?)";
        Object execute = null;
        try {
            execute = CrudUtil.execute(SQL, item.getItemCode(),item.getDescription(),item.getPackSize(),item.getUnitPrice(),item.getQtyOnHand());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public List<Item> itemData() {
        return List.of();
    }


    @Override
    public boolean deleteItem(String id) {
        String SQL="delete from Item where ItemCode='"+id+"'";
        try {
            Object execute=CrudUtil.execute(SQL);
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateItem(Item item) {
        try {
            String SQL = "update Item set ItemCode=?,Description=?,PackSize=?,UnitPrice=?,QtyOnHand=? where ItemCode='"+item.getItemCode()+"'";
            Object execute=CrudUtil.execute(SQL,item.getItemCode(),item.getDescription(),item.getPackSize(),item.getUnitPrice(),item.getQtyOnHand());
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResultSet searchItem(String id) {
        String SQL = "select * from Item where ItemCode='"+id+"'";
        try {
            Object execute = CrudUtil.execute(SQL);
            return (ResultSet) execute;
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Item> ItemList() {
        ObservableList<Item> obsList = FXCollections.observableArrayList();
        String SQL = "SELECT * FROM Item";
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            ResultSet resultSet = psTm.executeQuery();
            while (resultSet.next()){
                Item item =new Item(
                        resultSet.getString("ItemCode"),
                        resultSet.getString("Description"),
                        resultSet.getString("PackSize"),
                        resultSet.getString("UnitPrice"),
                        resultSet.getString("QtyOnHand")
                );
                obsList.add(item);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obsList;
    }

}
