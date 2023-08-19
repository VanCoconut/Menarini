package com.ogc.menarini.GestioneFarmaci.Interface;

import com.ogc.menarini.GestioneFarmaci.Control.GestoreScaricoMerci;
import com.ogc.menarini.Main;
import com.ogc.menarini.Utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class InterfacciaScaricoMerci {

    private GestoreScaricoMerci gestoreScaricoMerci;

    @FXML
    private TextField farmaco;

    @FXML
    private TextField quantita;

    @FXML
    public void initialize() {
        this.quantita.setTextFormatter(new TextFormatter<>(Utils.integerFilter));        
    }

    public InterfacciaScaricoMerci(GestoreScaricoMerci gestoreScaricoMerci) {
        this.gestoreScaricoMerci = gestoreScaricoMerci;
    }

    public void conferma(ActionEvent actionEvent) {
        int  qty;
        String n_farmaco;
        try {
            qty = Integer.parseInt(quantita.getText());
            n_farmaco = farmaco.getText();
        } catch (NumberFormatException e) {
            Main.log.warn("Si è tentati di convertire una stringa in un numero ed è finita male..", e);
            return;
        }
        this.gestoreScaricoMerci.scaricoMerci(n_farmaco, qty);
    }
}
