package br.cefetmg.gestaoentregasview;

import br.cefetmg.GestaoEntregasController.PedidoController;
import br.cefetmg.GestaoEntregasEntidades.Pedido;
import br.cefetmg.gestaoentregasview.helper.VerificadorDeCampos;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.time.*;
import java.util.Date;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FXMLCadastroPedidoController implements Initializable {

    @FXML
    private TextField textFieldProduto;

    @FXML
    private TextField textFieldQuantidade;

    @FXML
    private TextField textFieldValorUnitario;

    @FXML
    private TextField textFieldValorTotal;

    @FXML
    private TextField textFieldMarca;

    @FXML
    private TextField textFieldFormaPagamento;

    @FXML
    private ComboBox<String> comboBoxStatus;

    @FXML
    private DatePicker datePickerData;

    @FXML
    private Button cadastrarPedido;

    @FXML
    private Button voltarButton;
    
    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    
    @FXML
    public void cadastrarPedido() {
        String produto = textFieldProduto.getText();
        String quantidadeStr = textFieldQuantidade.getText();
        String valorUnitarioStr = textFieldValorUnitario.getText();
        String valorTotalStr = textFieldValorTotal.getText();
        String marca = textFieldMarca.getText();
        String formaPagamento = textFieldFormaPagamento.getText();
        String statusStr = comboBoxStatus.getSelectionModel().getSelectedItem();
        Date data = convertToDate(datePickerData.getValue());

        Pedido.Status status;
        try {
            status = Pedido.Status.valueOf(statusStr.replace(" ", "_").toUpperCase());
        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.ERROR, "Erro", "Status inválido.");
            return;
        }

        Map<String, String> campos = Map.of(
            "Produto", produto,
            "Quantidade", quantidadeStr,
            "Valor Unitário", valorUnitarioStr,
            "Valor Total", valorTotalStr,
            "Marca", marca,
            "Forma de Pagamento", formaPagamento,
            "Status", statusStr
        );

        VerificadorDeCampos verificador = new VerificadorDeCampos();
        if (verificador.estaCompleto(campos)) {
            return;
        }

        Alert alert = new Alert(Alert.AlertType.NONE);
        try {
            int quantidade = Integer.parseInt(quantidadeStr);
            double valorUnitario = Double.parseDouble(valorUnitarioStr);
            double valorTotal = Double.parseDouble(valorTotalStr);

            PedidoController controller = new PedidoController();
            controller.cadastrarPedido(produto, quantidade, valorUnitario, valorTotal, marca, formaPagamento, status, data);
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("Pedido cadastrado com sucesso!");
        } catch (NumberFormatException ex) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Erro: Dados numéricos inválidos.");
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboBoxStatus.setItems(FXCollections.observableArrayList(
            "Em Preparação",
            "Saiu para Entrega",
            "Entregue"
        ));
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private Date convertToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
    
}