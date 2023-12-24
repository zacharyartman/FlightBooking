module com.zachartman {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
            requires com.dlsc.formsfx;
                requires org.kordamp.ikonli.javafx;
    requires java.sql;

    opens com.zachartman to javafx.fxml;
    exports com.zachartman;
    exports com.zachartman.models;
    opens com.zachartman.models to javafx.fxml;
    exports com.zachartman.utils;
    opens com.zachartman.utils to javafx.fxml;
    exports com.zachartman.controllers;
    opens com.zachartman.controllers to javafx.fxml;
}