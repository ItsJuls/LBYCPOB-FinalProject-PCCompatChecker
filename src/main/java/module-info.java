module com.pccompatchecker {

    requires javafx.controls;
    requires javafx.fxml;

    opens com.pccompatchecker.controller to javafx.fxml;

    exports com.pccompatchecker;
}

