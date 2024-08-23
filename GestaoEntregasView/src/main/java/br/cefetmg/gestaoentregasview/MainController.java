package br.cefetmg.gestaoentregasview;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainController {

    @FXML
    private Label saudacoes;

    @FXML
    private VBox opcoes;

    public void setPerfil(String perfil) {
        saudacoes.setText("Bem-vindo, " + perfil + "!");
        opcoes.getChildren().clear();

        if ("Administrador".equals(perfil)) {
            addButton("Cadastrar Pedido", "loadPedidoScene");
            addButton("Cadastrar Produto", "cadastrarProduto");
            addButton("Cadastrar Funcionário", "cadastrarFuncionario");
            addButton("Cadastrar Cliente", "cadastrarCliente");
            addButton("Cadastrar Empresa", "cadastrarEmpresa");
        } else if ("Entregador".equals(perfil)) {
            addButton("Ver Pedidos", "verPedidos");
        }
    }

    private void addButton(String text, String action) {
        Button button = new Button(text);
        button.setOnAction(event -> handleButtonAction(action));
        opcoes.getChildren().add(button);
    }

    private void handleButtonAction(String action) {
        System.out.println("Action: " + action);
    }
    
    private void loadPedidoScene(String perfil) throws Exception {
        Stage stage = (Stage) saudacoes.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLCadastroPedido.fxml"));
        Parent root = loader.load();
        FXMLCadastroPedidoController controller = loader.getController();
        stage.setScene(new Scene(root));
    }
}
