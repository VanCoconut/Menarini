package com.ogc.menarini.GestioneFarmaci.Interface;

import com.ogc.menarini.Entity.Ordine;
import com.ogc.menarini.GestioneFarmaci.Control.GestoreModificaOrdinePeriodico;
import com.ogc.menarini.Utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import com.ogc.menarini.Main;

public class InterfacciaModificaOrdinePeriodico {
    @FXML
    private DatePicker dataconsegna;
    GestoreModificaOrdinePeriodico gestoreModificaOrdinePeriodico;

    @FXML
    private TextField quantita;

    private final Ordine ordine;

    public InterfacciaModificaOrdinePeriodico(GestoreModificaOrdinePeriodico gestoreModificaOrdinePeriodico, Ordine ordine) {
        this.gestoreModificaOrdinePeriodico= gestoreModificaOrdinePeriodico;
        this.ordine = ordine;
    }

    @FXML
    public void initialize() {
        this.quantita.setTextFormatter(new TextFormatter<>(Utils.positiveIntegerFilter));
        quantita.setText(ordine.getQuantita() + "");
        dataconsegna.setValue(ordine.getData_consegna());

    }

    public void conferma() {
        Main.log.info("sono in conferma");
        gestoreModificaOrdinePeriodico.modificaOrdine(Integer.parseInt(quantita.getText()), dataconsegna.getValue());
    }
}
