package br.cefetmg.gestaoentregasview;

import br.cefetmg.GestaoEntregasController.ProdutoController;
import br.cefetmg.gestaoentregasview.helper.VerificadorDeCampos;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FXMLCadastroProdutoController {

    @FXML
    private TextField textFieldNome;

    @FXML
    private TextField textFieldLocalizacao;

    @FXML
    private Button cadastrarProduto;

    @FXML
    private Button voltarButton;

    @FXML
    public void cadastrarProduto() {
        String nome = textFieldNome.getText();
        String localizacao = textFieldLocalizacao.getText();

        Map<String, String> campos = Map.of(
            "Nome", nome,
            "Localização", localizacao
        );

        VerificadorDeCampos verificador = new VerificadorDeCampos();
        if (verificador.estaCompleto(campos)) {
            return;
        }


        Alert alert = new Alert(Alert.AlertType.NONE);
        try {
            ProdutoController controller = new ProdutoController();
            controller.cadastrarProduto(nome, localizacao);
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("Produto cadastrado com sucesso!");
        } catch (Exception ex) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Erro: " + ex.getMessage());
        }
        alert.show();
    }
    public void voltarMain() {
        try {
            Stage stage = (Stage) voltarButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
            Parent root = loader.load();
            String perfil = UserSession.getPerfil();
            UserSession.setPerfil(perfil);
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erro", "Não foi possível carregar a tela principal.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
