package br.cefetmg.gestaoentregasview;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import br.cefetmg.GestaoEntregasEntidades.Funcionario;
import javax.persistence.TypedQuery;
import javax.persistence.NoResultException;

public class LoginController {

    @FXML
    private PasswordField passwordField;
    
    private EntityManagerFactory emf;
    private EntityManager em;

    public LoginController() {
        emf = Persistence.createEntityManagerFactory("br.cefetmg_GestaoEntregasDAO_jar_1.0-SNAPSHOTPU");
        em = emf.createEntityManager();
    }

    @FXML
    private void fazerLogin() {
        String password = passwordField.getText();
        
        try {
            Funcionario funcionario = buscarFuncionarioPorPerfil("ENTREGADOR");
            if ("admin".equals(password)) {
                UserSession.setPerfil("Administrador");
                loadMainScene("Administrador");
            }
            else if (funcionario != null && funcionario.getSenha().equals(password)) {
                    UserSession.setPerfil("Entregador");
                    loadMainScene("Entregador");
                }
            else {
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
    private Funcionario buscarFuncionarioPorPerfil(String perfilString) {
        try {
            Funcionario.Perfil perfil = Funcionario.Perfil.valueOf(perfilString);

            TypedQuery<Funcionario> query = em.createQuery(
                    "SELECT f FROM Funcionario f WHERE f.perfil = :perfil", Funcionario.class);
            query.setParameter("perfil", perfil);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (IllegalArgumentException e) {
            showAlert(AlertType.ERROR, "Erro", "Perfil inválido.");
            return null;
        }
    }
}
