module com.petshop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.base;
    requires javafx.graphics;

    opens com.petshop to javafx.fxml;
    exports com.petshop;

    opens com.petshop.controller to javafx.fxml;
    opens com.petshop.model to javafx.fxml;

    exports com.petshop.controller;
    exports com.petshop.model;
}
