module edu.usd {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
            requires com.dlsc.formsfx;
                requires org.kordamp.ikonli.javafx;
    requires java.sql;

    opens edu.usd to javafx.fxml;
    exports edu.usd;
    exports edu.usd.models;
    opens edu.usd.models to javafx.fxml;
    exports edu.usd.utils;
    opens edu.usd.utils to javafx.fxml;
    exports edu.usd.controllers;
    opens edu.usd.controllers to javafx.fxml;
}