package edu.example.thogakade.Controller;


import com.jfoenix.controls.JFXTextField;
import edu.example.thogakade.DB.DBConnection;
import edu.example.thogakade.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.ResourceBundle;

public class AddCustomer implements Initializable {
    public JFXTextField txtAddress;
    public JFXTextField txtID;
    public JFXTextField txtSalary;
    public JFXTextField txtName;
    public JFXTextField txtCity;
    public JFXTextField txtProvince;
    public JFXTextField txtPostal;
    public TableColumn <?,?>  Id;
    public TableColumn <?,?>  Title;
    public TableColumn <?,?>  Name;
    public TableColumn  <?,?> Salary;
    public TableColumn  <?,?> Address;
    public TableColumn  <?,?>  City;
    public TableColumn  <?,?>  Dob;
    public TableColumn  <?,?> Province;
    public TableColumn <?,?> PostalCode;
    @FXML
    public TableView <Customer> tableView;
    public ComboBox txtTitle;
    public DatePicker DtDob;
    Service newService = new Service();
    public void addOnClick(ActionEvent actionEvent) {
        Customer customer = new Customer(txtID.getText(),txtTitle.getValue().toString(),txtName.getText(),txtAddress.getText(),DtDob.getValue(),
                Double.parseDouble(txtSalary.getText()),txtCity.getText(),txtProvince.getText(),txtPostal.getText());
        //System.out.println(customer);
        if(newService.addCustomer(customer)){
        new Alert(Alert.AlertType.INFORMATION,"Successfully added!").show();
        load();}
    }

    public void searchOnClick(ActionEvent actionEvent) {

        try {
            ResultSet result = newService.searchCustomer(txtID.getText());
            if(result.next()){
                txtID.setText(result.getString("CustID"));
                txtName.setText(result.getString("CustName"));
                txtAddress.setText(result.getString("CustAddress"));
                txtCity.setText(result.getString("City"));
                txtProvince.setText(result.getString("Province"));
                txtPostal.setText(result.getString("PostalCode"));
                txtSalary.setText(result.getString("salary"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteOnClick(ActionEvent actionEvent) {
        if(newService.deleteCustomer(txtID.getText())){
            new Alert(Alert.AlertType.INFORMATION,"Deleted Successfully").show();
            load();
        }
    }

    public void reloadOnClick(ActionEvent actionEvent) {
        load(); }
    void load(){
        Id.setCellValueFactory(new PropertyValueFactory<>("id"));
        Title.setCellValueFactory(new PropertyValueFactory<>("title"));
        Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        Salary.setCellValueFactory(new PropertyValueFactory<>("address"));
        Address.setCellValueFactory(new PropertyValueFactory<>("dob"));
        City.setCellValueFactory(new PropertyValueFactory<>("salary"));
        Dob.setCellValueFactory(new PropertyValueFactory<>("city"));
        Province.setCellValueFactory(new PropertyValueFactory<>("province"));
        PostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        tableView.setItems(newService.allData());
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList <String> titles = FXCollections.observableArrayList();
        titles.add("Miss");
        titles.add("Mr");
        titles.add("Ms");
        txtTitle.setItems(titles);
        tableView.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            setTextToValues(newValue);
        }));
    }

    private void setTextToValues(Customer newValue) {
        if (newValue != null) {
            txtID.setText(newValue.getId());
            txtName.setText(newValue.getName());
            txtAddress.setText(newValue.getAddress());
        } else {
            System.out.println("Customer is null");
        }
    }
}
