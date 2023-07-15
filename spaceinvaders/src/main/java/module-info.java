module com.cdepaul1 {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens com.cdepaul1 to javafx.fxml;

    exports com.cdepaul1;
}
