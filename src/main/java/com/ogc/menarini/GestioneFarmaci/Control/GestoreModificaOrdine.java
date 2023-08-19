package com.ogc.menarini.GestioneFarmaci.Control;

import com.ogc.menarini.Entity.Ordine;
import com.ogc.menarini.GestioneFarmaci.Interface.InterfacciaModificaOrdine;
import com.ogc.menarini.Main;
import com.ogc.menarini.Utils.DBMSDaemon;
import com.ogc.menarini.Utils.Utils;
import javafx.stage.Stage;

import java.time.LocalDate;

public class GestoreModificaOrdine {
    private final Ordine ordine;

    private final Stage stage;

    public GestoreModificaOrdine(Ordine ordine) {
        this.ordine = ordine;
        this.stage = new Stage();
        Utils.cambiaInterfaccia("GestioneFarmaci/ModificaOrdine.fxml", this.stage,
                c -> new InterfacciaModificaOrdine(this, ordine), 600, 400);
    }

    public void modificaOrdine(int nuovaQuantita, LocalDate data) {
        if (data.isBefore(Main.orologio.chiediOrario().toLocalDate().plusDays(3))) {
            Utils.creaPannelloErrore("La data di consegna non può essere prima di "
                    + Main.orologio.chiediOrario().toLocalDate().plusDays(3));
            return;
        }
        if (!data.isEqual(ordine.getData_consegna())) {
            ordine.setData_consegna(data);
            Main.log.info("Modificando la data dell'ordine");
            DBMSDaemon.queryAggiornaData(ordine, data);
        }
        if (nuovaQuantita != ordine.getQuantita()) {
            ordine.setQuantita(nuovaQuantita);
            Main.log.info("Modificando la quantità dell'ordine");
            if (DBMSDaemon.queryAggiornaQuantitaOrdine(ordine, nuovaQuantita) == -1) {
                Utils.creaPannelloErrore("Errore durante la modifica della quantità dell'ordine");
                return;
            }
        }
        GestoreVisualizzaOrdini.aggiornaOrdine(this.ordine);
        Utils.creaPannelloConferma("Ordine Modificato Correttamente", this.stage);
    }
}
