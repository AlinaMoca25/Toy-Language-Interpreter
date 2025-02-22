module com.example.map_toylanguage_gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.compiler;
    requires java.desktop;


    opens com.example.map_toylanguage_gui to javafx.fxml;
    opens view to javafx.fxml;

    exports com.example.map_toylanguage_gui;
    exports view;
}