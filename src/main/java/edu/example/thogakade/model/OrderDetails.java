package edu.example.thogakade.model;

import lombok.*;

import java.time.LocalDate;


@NoArgsConstructor
@ToString
@Getter
@Setter
public class OrderDetails {
    private String orderId;

    public String getOrderDate() {
        return orderDate;
    }

    public String getItemName() {
        return ItemName;
    }

    public String getCustId() {
        return custId;
    }

    public String getDiscount() {
        return discount;
    }

    public String getQty() {
        return qty;
    }

    private String itemCode;

    public String getItemCode() {
        return itemCode;
    }

    private String qty;
    private String discount;
    private String orderDate;
    private String custId;
    private String ItemName;

    public String getOrderId() {
        return orderId;
    }

    public OrderDetails(String orderID, String itemCode, String ItemName, String orderQTY, String discount, String orderDate, String custID) {
        this.orderId=orderID;
        this.itemCode=itemCode;
        this.ItemName=ItemName;
        this.qty=orderQTY;
        this.discount=discount;
        this.orderDate=orderDate;
        this.custId=custID;
    }
}
