package br.cefetmg.gestaoentregasview;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private PasswordField passwordField;

    @FXML
    private void fazerLogin() {
        String password = passwordField.getText();
        
        try {
            if ("admin".equals(password)) {
                UserSession.setPerfil("Administrador");
                loadMainScene("Administrador");
            } else if ("entregador".equals(password)) {
                UserSession.setPerfil("Entregador");
                loadMainScene("Entregador");
            } else {
                showAlert(AlertType.ERROR, "Erro", "Senha inválida.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Erro", "Erro ao carregar a tela.");
        }
    }

    public void loadMainScene(String perfil) throws Exception {
        Stage stage = (Stage) passwordField.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        Parent root = loader.load();
        MainController controller = loader.getController();
        controller.setPerfil(perfil);
        stage.setScene(new Scene(root));
    }

    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
