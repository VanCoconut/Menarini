package com.ogc.menarini.GestioneFarmaci.Interface;

import com.ogc.menarini.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AvvisoMancatoCaricamento {
    @FXML
    private Button conferma;

    @FXML
    protected void conferma() {
        Main.orologio.iniziaTimer();
        ((Stage) conferma.getScene().getWindow()).close();
    }
}
