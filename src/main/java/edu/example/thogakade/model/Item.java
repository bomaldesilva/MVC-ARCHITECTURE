package edu.example.thogakade.model;

public class Item {
    private String ItemCode;
    private String Description;
    private String PackSize;
    private String UnitPrice;
    private String QtyOnHand;

    public String getItemCode() {
        return ItemCode;
    }

    public String getDescription() {
        return Description;
    }

    public String getPackSize() {
        return PackSize;
    }

    public String getUnitPrice() {
        return UnitPrice;
    }

    public String getQtyOnHand() {
        return QtyOnHand;
    }

    public Item(String code, String desc, String packsize, String untPrize, String qty){
       this.ItemCode=code;
       this.Description=desc;
       this.PackSize=packsize;
       this.UnitPrice=untPrize;
       this.QtyOnHand=qty;
   }
}
