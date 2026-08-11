module com.pccompatchecker {
    requires javafx.controls;
    requires com.fasterxml.jackson.annotation;

    opens com.pccompatchecker to javafx.fxml;

    exports com.pccompatchecker;
}

