
module Convertor{
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires javafx.media;
    requires java.sql;

    opens convertor.convertor to javafx.fxml;


    opens Music to javafx.fxml;
    exports Music;

    opens ATM to javafx.fxml;
    exports ATM;
}
