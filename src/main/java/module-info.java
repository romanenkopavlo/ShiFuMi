module org.example.ppcjeuxgraphique {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.ppcjeuxgraphique to javafx.fxml;
    exports org.example.ppcjeuxgraphique;
}