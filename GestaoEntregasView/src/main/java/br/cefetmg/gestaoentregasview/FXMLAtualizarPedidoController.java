package br.cefetmg.gestaoentregasview;

import br.cefetmg.GestaoEntregasController.PedidoController;
import br.cefetmg.GestaoEntregasEntidades.Pedido;
import br.cefetmg.gestaoentregasview.helper.VerificadorDeCampos;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Map;

public class FXMLAtualizarPedidoController {

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
    private Button atualizarButton;

    @FXML
    private Button voltarButton;

    private PedidoController pedidoController;
    private int pedidoId;

    @FXML
    public void initialize() {
        pedidoController = new PedidoController();
    }

    public void configurarCampos(String produto, int quantidade, double valorUnitario, double valorTotal, String marca, String formaPagamento, Pedido.Status status, Date data, int pedidoId) {
        String statusStr = status.name();
        String quantidadeStr = String.valueOf(quantidade);
        String valorUnitarioStr = Double.toString(valorUnitario);
        String valorTotalStr = Double.toString(valorTotal);
        textFieldProduto.setText(produto);
        textFieldQuantidade.setText(quantidadeStr);
        textFieldValorUnitario.setText(valorUnitarioStr);
        textFieldValorTotal.setText(valorTotalStr);
        textFieldMarca.setText(marca);
        textFieldFormaPagamento.setText(formaPagamento);
        comboBoxStatus.setValue(statusStr);
        
        Instant instant = data.toInstant();
        LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        datePickerData.setValue(localDate);
        
        this.pedidoId = pedidoId;
    }

    @FXML
    public void atualizarPedido() {
        String produto = textFieldProduto.getText();
        String quantidadeStr = textFieldQuantidade.getText();
        String valorUnitarioStr = textFieldValorUnitario.getText();
        String valorTotalStr = textFieldValorTotal.getText();
        String marca = textFieldMarca.getText();
        String formaPagamento = textFieldFormaPagamento.getText();
        String statusStr = comboBoxStatus.getSelectionModel().getSelectedItem();
        Date data = convertToDate(datePickerData.getValue());

        Pedido.Status status;

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
        if (!verificador.estaCompleto(campos)) {
            Alert alert = new Alert(Alert.AlertType.NONE);
            try {
                int quantidade = Integer.parseInt(quantidadeStr);
                double valorUnitario = Double.parseDouble(valorUnitarioStr);
                double valorTotal = Double.parseDouble(valorTotalStr);
                status = Pedido.Status.valueOf(statusStr.replace(" ", "_").toUpperCase());
                pedidoController.atualizarPedido(pedidoId, produto, quantidade, valorUnitario, valorTotal, marca, formaPagamento, status, data);
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("Pedido atualizado com sucesso!");
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

    private Date convertToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
