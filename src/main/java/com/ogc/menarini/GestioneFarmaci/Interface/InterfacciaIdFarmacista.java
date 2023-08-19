 package com.ogc.menarini.GestioneFarmaci.Interface;

import com.ogc.menarini.GestioneFarmaci.Control.GestoreIdFarmacia;
import com.ogc.menarini.Main;
import com.ogc.menarini.Utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class InterfacciaIdFarmacista {

    private GestoreIdFarmacia gestoreIdFarmacia;

    @FXML
    private TextField id;

    @FXML
    public void initialize() {        
        this.id.setTextFormatter(new TextFormatter<>(Utils.integerFilter));
    }

    public InterfacciaIdFarmacista(GestoreIdFarmacia gestoreIdFarmacia) {
        this.gestoreIdFarmacia = gestoreIdFarmacia;
    }

    public void conferma(ActionEvent actionEvent) {
        int id_f;
        try {
            id_f = Integer.parseInt(id.getText());
        } catch (NumberFormatException e) {
            Main.log.warn("Si è tentati di convertire una stringa in un numero ed è finita male..", e);
            return;
        }
        this.gestoreIdFarmacia.modificaIdFarmacia(id_f);
    }
} 
