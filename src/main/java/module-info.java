module edu.example.thogakade {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires static lombok;
    requires java.sql;


    opens edu.example.thogakade to javafx.fxml;
    exports edu.example.thogakade;
    exports edu.example.thogakade.Controller;
    opens edu.example.thogakade.Controller to javafx.fxml;
    exports edu.example.thogakade.model;
    opens edu.example.thogakade.model to javafx.base, javafx.fxml;
}