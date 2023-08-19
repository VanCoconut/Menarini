package com.ogc.menarini.GestioneFarmaci.Control;

import com.ogc.menarini.Entity.Ordine;
import com.ogc.menarini.GestioneFarmaci.Interface.InterfacciaModificaOrdinePeriodico;
import com.ogc.menarini.Main;
import com.ogc.menarini.Utils.DBMSDaemon;
import com.ogc.menarini.Utils.Utils;
import javafx.stage.Stage;

import java.time.LocalDate;

public class GestoreModificaOrdinePeriodico {
    private final Ordine ordine;

    private final Stage stage;

    public GestoreModificaOrdinePeriodico(Ordine ordine) {
        this.ordine = ordine;
        this.stage = new Stage();
        Utils.cambiaInterfaccia("GestioneFarmaci/ModificaOrdinePeriodico.fxml", this.stage,
                c -> new InterfacciaModificaOrdinePeriodico(this, ordine), 600, 400);
    }

    public void modificaOrdine(int nuovaQuantita, LocalDate data) {
        
        if (nuovaQuantita != ordine.getQuantita()) {
            ordine.setQuantita(nuovaQuantita);
            Main.log.info("id ordine" + ordine.id_ordine);
            Main.log.info("Modificando la quantità dell'ordine");
            if (DBMSDaemon.queryAggiornaQuantitaOrdinePeriodico(ordine, nuovaQuantita) == -1) {
                Utils.creaPannelloErrore("Errore durante la modifica della quantità dell'ordine");
                return;
            }
        }
        GestoreVisualizzaOrdiniPeriodici.aggiornaOrdine(this.ordine);
        Utils.creaPannelloConferma("Ordine Modificato Correttamente", this.stage);
    }
}
