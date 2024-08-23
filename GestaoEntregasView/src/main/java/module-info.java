module br.cefetmg.gestaoentregasview {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens br.cefetmg.gestaoentregasview to javafx.fxml;
    exports br.cefetmg.gestaoentregasview;
    requires GestaoEntregasController;
    requires GestaoEntregasEntidades; 
}