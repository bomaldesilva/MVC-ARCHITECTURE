package edu.example.thogakade.Controller;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import edu.example.thogakade.CRUD.CrudUtil;
import edu.example.thogakade.model.Customer;
import edu.example.thogakade.model.Item;
import edu.example.thogakade.model.OrderDetails;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class OrderForm implements Initializable {
    public JFXTextField txtOrderId;
    public JFXTextField txtDiscount;
    public JFXTextField txtCustId;
    public JFXTextField txtItemCode;
    public JFXTextField txtQty;
    public DatePicker dateDob;
    public TableView <OrderDetails>tableView;
    public TableColumn <?,?> tblCustId;
    public TableColumn <?,?> tblOrderId;
    public TableColumn <?,?> tblItemCode;
    public TableColumn<?,?> tblItemName;
    public TableColumn <?,?>tblQTY;
    public TableColumn <?,?>tblDiscount;
    public TableColumn <?,?>tblOrderDate;
    public JFXTextArea txtItemName;
    public void AddOnAction(ActionEvent actionEvent) {
        String SQL1="insert into OrderDetail Values(?,?,?,?)";
        String SQL2="insert into Orders Values(?,?,?)";
        String SQL3="select * from Item where ItemCode ='"+txtItemCode.getText()+"'";
        try {
            Object exe = CrudUtil.execute(SQL3);
            ResultSet result = (ResultSet) exe;
            String unitPrice = "";
            if(result.next()){
                unitPrice = result.getString("unitPrice");
            }
            CrudUtil.execute(SQL2, txtOrderId.getText(),dateDob.getValue(),txtCustId.getText());
            CrudUtil.execute(SQL1,txtOrderId.getText(),txtItemCode.getText(),txtQty.getText()
                    ,unitPrice);
            load();
        } catch (SQLException e) {
            System.out.println("msg:"+ e.getMessage());
            throw new RuntimeException(e);
        }

    }
    public void deleteOnAction(ActionEvent actionEvent) {
        String SQL ="Delete from OrderDetail where OrderID=? AND ItemCode=?";
        try {
           Boolean Result= CrudUtil.execute(SQL,txtOrderId.getText(),txtItemCode.getText());
           load();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateOnAction(ActionEvent actionEvent) {
        String SQL = "update OrderDetail set OrderQTY=?,Discount=? where OrderID='"+txtOrderId.getText()+"' AND ItemCode='"+
                txtItemCode.getText()+"'";
        String SQL1= "update Orders set CustID=?,OrderDate=? where OrderID='"+txtOrderId.getText()+"'";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = dateDob.getValue();
        String strDate = date.format(formatter);
        try {
            CrudUtil.execute(SQL1,txtCustId.getText(),strDate);
            CrudUtil.execute(SQL,txtQty.getText(),txtDiscount.getText());
            load();
        } catch (SQLException e) {
            System.out.println("msg"+e.getMessage());
            throw new RuntimeException(e);
        }

    }
    public void SearchOnAction(ActionEvent actionEvent) {
        tblOrderId.setCellValueFactory((new PropertyValueFactory<>("orderId")));
        tblItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        tblQTY.setCellValueFactory(new PropertyValueFactory<>("qty"));
        tblDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        tblOrderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        tblCustId.setCellValueFactory(new PropertyValueFactory<>("custId"));
        tblItemName.setCellValueFactory(new PropertyValueFactory<>("ItemName"));
        ObservableList<OrderDetails> obs = FXCollections.observableArrayList();
        String SQL="select * from OrderDetail where OrderID=? AND ItemCode=?";
        String SQL1="select CustID,OrderDate from Orders where OrderID=?";
        String SQL2="select * from OrderDetail where OrderID=?";
        String SQL3="select Description from Item where ItemCode=?";
        String Date = "";
        String CustId="";
        String ItemCode="";
        try {
            ResultSet resultSet=CrudUtil.execute(SQL,txtOrderId.getText(),txtItemCode.getText());
            ResultSet resultSet1=CrudUtil.execute(SQL1,txtOrderId.getText());
            resultSet1.next();
            CustId=resultSet1.getString("CustID");
            Date = resultSet1.getString("OrderDate");
            if(resultSet.next()){
                ResultSet resultSet3= CrudUtil.execute(SQL3,txtItemCode.getText());
                resultSet3.next();
                txtItemCode.setText(resultSet.getString("ItemCode"));
                txtQty.setText(resultSet.getString("OrderQTY"));
                txtDiscount.setText(resultSet.getString("Discount"));
                txtCustId.setText(CustId);
                txtItemName.setText(resultSet3.getString("Description"));
            }
            ResultSet resultSet2 = CrudUtil.execute(SQL2, txtOrderId.getText());
            while(resultSet2.next()){
                    ItemCode = resultSet2.getString("ItemCode");
                    ResultSet resultSet3= CrudUtil.execute(SQL3,ItemCode);
                    resultSet3.next();
                    OrderDetails orderDetails = new OrderDetails(
                            resultSet2.getString("OrderID"),
                            ItemCode,
                            resultSet3.getString("Description"),
                            resultSet2.getString("OrderQTY"),
                            resultSet2.getString("Discount"),
                            Date,
                            CustId
                    );
                    obs.add(orderDetails);
                }
            tableView.setItems(obs);
            load();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void reloadOnAction(ActionEvent actionEvent) {
        load();
    }
    void load(){
        tblOrderId.setCellValueFactory((new PropertyValueFactory<>("orderId")));
        tblItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        tblQTY.setCellValueFactory(new PropertyValueFactory<>("qty"));
        tblDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        tblOrderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        tblCustId.setCellValueFactory(new PropertyValueFactory<>("custId"));
        tblItemName.setCellValueFactory(new PropertyValueFactory<>("ItemName"));
        ObservableList<OrderDetails> obs = FXCollections.observableArrayList();
        String SQLORDER = "select * from Orders";
        String SQL3="select Description from Item where ItemCode=?";
        String ItemCode;
        try {
            ResultSet resultSet1=CrudUtil.execute(SQLORDER);
            while(resultSet1.next()){
                String orderID = resultSet1.getString("OrderID");
                String SQLORDERDETAILS = "select * from OrderDetail where OrderID='"+orderID+"'";
                ResultSet resultSet2=CrudUtil.execute(SQLORDERDETAILS);
                while (resultSet2.next()){
                    ItemCode=resultSet2.getString("ItemCode");
                    ResultSet resultSet3=CrudUtil.execute(SQL3,ItemCode);
                    resultSet3.next();
                    OrderDetails orderDetails = new OrderDetails(
                            resultSet2.getString("OrderID"),
                            ItemCode,
                            resultSet3.getString("Description"),
                            resultSet2.getString("OrderQTY"),
                            resultSet2.getString("Discount"),
                            resultSet1.getString("OrderDate"),
                            resultSet1.getString("CustID")
                    );
                    obs.add(orderDetails);
                }
            }
            tableView.setItems(obs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableView.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            setTextToValues((OrderDetails) newValue);
        }));
    }

    private void setTextToValues(OrderDetails newValue) {
        if (newValue != null) {
            txtOrderId.setText(newValue.getOrderId());
            txtDiscount.setText(newValue.getDiscount());
            txtCustId.setText(newValue.getCustId());
            txtItemCode.setText(newValue.getItemCode());
            txtQty.setText(newValue.getQty());
            txtItemName.setText(newValue.getItemName());
            LocalDate localDate = LocalDate.parse(newValue.getOrderDate());
            dateDob.setValue(localDate);
        } else {
            System.out.println("Order is null");
        }
    }
}
