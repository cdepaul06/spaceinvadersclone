module com.spaceinvaders {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens com.spaceinvaders to javafx.fxml;

    exports com.spaceinvaders;
}
