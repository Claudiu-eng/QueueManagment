module com.example.tema2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires org.apache.logging.log4j;

    opens com.example.tema2 to javafx.fxml;
    exports com.example.tema2;
    exports com.example.tema2.model;
    exports com.example.tema2.viewController;
    opens com.example.tema2.viewController to javafx.fxml;
}