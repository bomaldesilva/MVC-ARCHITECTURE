package edu.example.thogakade.Controller;

import edu.example.thogakade.model.Customer;
import edu.example.thogakade.model.Item;
import edu.example.thogakade.model.OrderDetails;

import java.sql.ResultSet;
import java.util.List;

public interface ServiceImpl {
    boolean addCustomer(Customer customer);
    List<Customer> allData();
    boolean deleteCustomer(String id);
    ResultSet searchCustomer(String id);
    boolean addItem(Item item);
    List<Item> itemData();
    boolean deleteItem(String id);
    boolean updateItem(Item item);
    ResultSet searchItem(String id);
    List<Item> ItemList();
}
