module com.example.tap2024 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.tap2024 to javafx.fxml;
    exports com.example.tap2024;

    requires  java.sql;
    requires mysql.connector.j;

    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires kernel;
    requires layout;
    requires jdk.accessibility;

    opens com.example.tap2024.modelos;
}