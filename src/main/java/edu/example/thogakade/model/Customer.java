package edu.example.thogakade.model;



import lombok.*;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Customer {
    private String id;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public LocalDate getDob() {
        return dob;
    }

    public Double getSalary() {
        return salary;
    }

    public String getCity() {
        return city;
    }

    public String getProvince() {
        return province;
    }

    public String getPostalCode() {
        return postalCode;
    }

    private String  title;
    private String  name;
    private String address;
    private LocalDate dob;
    private Double salary;
    private String city;
    private String province;
    private String postalCode;


    public Customer(String custID, String custTitle, String custName, String custAddress, LocalDate dob, double salary, String city, String province, String postalCode) {
        this.id=custID;
        this.title=custTitle;
        this.name=custName;
        this.address=custAddress;
        this.dob=dob;
        this.salary=salary;
        this.city=city;
        this.province=province;
        this.postalCode=postalCode;
    }
}
