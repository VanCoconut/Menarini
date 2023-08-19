package com.ogc.menarini.GestioneFarmaci.Interface;

import com.ogc.menarini.GestioneFarmaci.Control.GestoreCaricoMerci;
import com.ogc.menarini.Main;
import com.ogc.menarini.Utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class InterfacciaCaricoMerci {

    private GestoreCaricoMerci gestoreCaricoMerci;

    @FXML
    TextField nomeFarmaco;

    @FXML
    TextField quantita;

    @FXML
    public void initialize() {
        
        this.quantita.setTextFormatter(new TextFormatter<>(Utils.integerFilter));
    }

    public InterfacciaCaricoMerci(GestoreCaricoMerci gestoreCaricoMerci) {
        this.gestoreCaricoMerci = gestoreCaricoMerci;
    }

    public void conferma(ActionEvent actionEvent) {
        int  int_quantita;
        String nF;
 
        try {
            int_quantita = Integer.parseInt(quantita.getText());
            nF =nomeFarmaco.getText();
        } catch (NumberFormatException e) {
            Main.log.warn("Si è tentati di convertire una stringa in un numero ed è finita male..", e);
            return;
        }
        this.gestoreCaricoMerci.caricaFarmaco(nF, int_quantita);
    }
}
