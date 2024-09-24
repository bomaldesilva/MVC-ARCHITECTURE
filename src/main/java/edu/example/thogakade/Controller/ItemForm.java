package edu.example.thogakade.Controller;
import edu.example.thogakade.model.Item;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import edu.example.thogakade.DB.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ItemForm implements Initializable{
    public TableView tableView;
    public TableColumn itemCode;
    public TableColumn description;
    public TableColumn packSize;
    public TableColumn unitPrize;
    public TableColumn qnt;
    public JFXTextArea txtDesc;
    public JFXTextField txtQoh;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtPackSize;
    public JFXTextField txtItem;
    Service service = new Service();
    public void reloadOnClick(ActionEvent actionEvent) {
        load();
    }
    void load(){
        itemCode.setCellValueFactory(new PropertyValueFactory<>("ItemCode"));
        description.setCellValueFactory(new PropertyValueFactory<>("Description"));
        packSize.setCellValueFactory(new PropertyValueFactory<>("PackSize"));
        unitPrize.setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));
        qnt.setCellValueFactory(new PropertyValueFactory<>("QtyOnHand"));
        tableView.setItems((ObservableList) service.ItemList());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableView.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            setTextToValues((Item) newValue);
        }));
    }

    private void setTextToValues(Item newValue) {
        if (newValue != null) {
            txtItem.setText(newValue.getItemCode());
            txtDesc.setText(newValue.getDescription());
            txtPackSize.setText(newValue.getPackSize());
            txtUnitPrice.setText(newValue.getUnitPrice());
            txtQoh.setText(newValue.getQtyOnHand());
        } else {
            System.out.println("Customer is null");
        }
    }

    public void addOnAction(ActionEvent actionEvent) {
        Item item = new Item(txtItem.getText(),txtDesc.getText(),txtPackSize.getText(),txtUnitPrice.getText(),txtQoh.getText());
        if(service.addItem(item)){
            new Alert(Alert.AlertType.INFORMATION,"Item added successfully:)").show();
            load();
        }
        else{
            new Alert(Alert.AlertType.INFORMATION,"Item added Unsuccessfully:)").show();
        }
    }

    public void deleteOnAction(ActionEvent actionEvent) {
        if(service.deleteItem(txtItem.getText())){
            new Alert(Alert.AlertType.INFORMATION,"Item Deleted Successfully").show();
            load();
        }
    }

    public void updateOnAction(ActionEvent actionEvent) {
        Item item = new Item(txtItem.getText(),txtDesc.getText(),txtPackSize.getText(),txtUnitPrice.getText(),txtQoh.getText());
        if(service.updateItem(item)){
            new Alert(Alert.AlertType.INFORMATION,"Item Updated successfully:)").show();
            load();
        }
    }

    public void searchOnAction(ActionEvent actionEvent) {
        ResultSet result = service.searchItem(txtItem.getText());
        try {
            if(result.next()){
                try {
                    txtDesc.setText(result.getString("Description"));
                    txtPackSize.setText(result.getString("PackSize"));
                    txtUnitPrice.setText(result.getString("UnitPrice"));
                    txtQoh.setText(result.getString("QtyOnHand"));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
