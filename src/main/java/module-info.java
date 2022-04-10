module com.example.naloga03uv {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.naloga03uv to javafx.fxml;
    exports com.example.naloga03uv;
}