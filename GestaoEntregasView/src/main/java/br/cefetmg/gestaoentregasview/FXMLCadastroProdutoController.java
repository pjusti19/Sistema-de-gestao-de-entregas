package br.cefetmg.gestaoentregasview;

import br.cefetmg.GestaoEntregasController.ProdutoController;
import br.cefetmg.GestaoEntregasEntidades.Produto;
import br.cefetmg.gestaoentregasview.helper.VerificadorDeCampos;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class FXMLCadastroProdutoController{

    @FXML
    private TextField textFieldNome;

    @FXML
    private TextField textFieldLocalizacao;

    @FXML
    private Button cadastrarProduto;

    @FXML
    public void cadastrarProduto() {
        String nome = textFieldNome.getText();
        String localizacao = textFieldLocalizacao.getText();

        Map<String, String> campos = Map.of(
            "Nome", nome,
            "Localização", localizacao
        );

        VerificadorDeCampos verificador = new VerificadorDeCampos();
        if (!verificador.estaCompleto(campos)) {
            showAlert(Alert.AlertType.ERROR, "Erro", "Preencha todos os campos obrigatórios.");
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


    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
