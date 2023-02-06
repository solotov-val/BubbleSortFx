module com.example.bubblesortfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.example.bubblesortfx to javafx.fxml;
    exports com.example.bubblesortfx;
}