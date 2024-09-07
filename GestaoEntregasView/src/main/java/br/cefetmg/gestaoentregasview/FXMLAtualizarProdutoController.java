package br.cefetmg.gestaoentregasview;

import br.cefetmg.GestaoEntregasController.ProdutoController;
import br.cefetmg.gestaoentregasview.helper.VerificadorDeCampos;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Map;

public class FXMLAtualizarProdutoController {

    @FXML
    private TextField textFieldNome;

    @FXML
    private TextField textFieldLocalizacao;

    @FXML
    private Button atualizarButton;

    @FXML
    private Button voltarButton;

    private ProdutoController produtoController;
    private int produtoId;

    @FXML
    public void initialize() {
        produtoController = new ProdutoController();
    }

    public void configurarCampos(String nome, String localizacao, int produtoId) {
        textFieldNome.setText(nome);
        textFieldLocalizacao.setText(localizacao);
       
        this.produtoId = produtoId;
    }

    @FXML
    public void atualizarProduto() {
        String nome = textFieldNome.getText();
        String localizacao = textFieldLocalizacao.getText();

        Map<String, String> campos = Map.of(
            "Nome", nome,
            "Localização", localizacao
        );

        VerificadorDeCampos verificador = new VerificadorDeCampos();
        if (!verificador.estaCompleto(campos)) {
            Alert alert = new Alert(Alert.AlertType.NONE);
            try {
                produtoController.atualizarProduto(produtoId, nome, localizacao);
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("Produto atualizado com sucesso!");
            } catch (Exception ex) {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Erro: " + ex.getMessage());
            }
            alert.show();
        } else {
            showAlert(Alert.AlertType.WARNING, "Campos incompletos", "Por favor, preencha todos os campos obrigatórios.");
        }
    }

    @FXML
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
