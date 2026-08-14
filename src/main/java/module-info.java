module com.pccompatchecker {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires org.controlsfx.controls;

    opens com.pccompatchecker.Components to com.fasterxml.jackson.databind;
    opens com.pccompatchecker to javafx.fxml;
    opens com.pccompatchecker.controller to javafx.fxml;

    exports com.pccompatchecker;
}