package com.ogc.menarini.GestioneFarmaci.Interface;

import com.ogc.menarini.Entity.Farmaco;
import com.ogc.menarini.GestioneFarmaci.Control.GestoreOrdinaFarmaco;
import com.ogc.menarini.Main;
import com.ogc.menarini.Utils.Utils;
import javafx.fxml.FXML;
import com.ogc.menarini.Main;
import javafx.scene.control.*;

public class InterfacciaOrdinaFarmaco {
    Farmaco f;
    @FXML
    private Label nomeFarmaco, princAttivo;
    @FXML
    private TextField quantita;
    @FXML
    private DatePicker dataDiConsegna;
    @FXML
    private CheckBox checkboxScadenza;

    private final GestoreOrdinaFarmaco gof;

    public InterfacciaOrdinaFarmaco(Farmaco f, GestoreOrdinaFarmaco gof) {
        this.gof = gof;
        this.f = f;
    }

    @FXML
    protected void initialize() {
        quantita.setTextFormatter(new TextFormatter<>(Utils.nonZeroPositiveIntegerFilter));
        nomeFarmaco.setText(f.getNome());
        princAttivo.setText(f.getPrincipio_attivo());
        dataDiConsegna.setValue(Main.orologio.chiediOrario().toLocalDate().plusDays(3));
    }

    @FXML
    protected void conferma() {
        if(quantita.getText().isBlank() || dataDiConsegna.getValue().isBefore(Main.orologio.chiediOrario().toLocalDate().plusDays(3))){
            Utils.creaPannelloErrore("Inserisci dati validi");
            return;
        }
        Main.log.info("Sono in conferma Prima di entrare in crea ordine");
        gof.creaOrdine(Integer.parseInt(quantita.getText()), dataDiConsegna.getValue(), checkboxScadenza.isSelected());
    }
}
