package br.cefetmg.gestaoentregasview.helper;

import javafx.scene.control.Alert;

import java.util.Map;

public class VerificadorDeCampos {

    private StringBuilder faltantes;
    private boolean verificacao;

    public boolean estaCompleto(Map<String, String> campos) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        this.faltantes = new StringBuilder();
        this.verificacao = false;

        for (Map.Entry<String, String> entrada : campos.entrySet()) {
            if (entrada.getValue() == null || entrada.getValue().trim().isEmpty()) {
                if (this.faltantes.length() > 0) {
                    this.faltantes.append("; ");
                }
                this.faltantes.append(entrada.getKey());
                this.verificacao = true;
            }
        }

        if (verificacao) {
            alert.setTitle("Cadastro incompleto");
            alert.setHeaderText("Preencha todos os campos!");
            alert.setContentText("Campos restantes: " + faltantes.toString());
            alert.show();
        }

        return verificacao;
    }
}
