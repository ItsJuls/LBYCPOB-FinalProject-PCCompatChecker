module com.pccompatchecker {
    requires javafx.controls;
    requires com.fasterxml.jackson.annotation;
    requires javafx.fxml;

    opens com.pccompatchecker to javafx.fxml;
    opens com.pccompatchecker.controller to javafx.fxml;

    exports com.pccompatchecker;
}

