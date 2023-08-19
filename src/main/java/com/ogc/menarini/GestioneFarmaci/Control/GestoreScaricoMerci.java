package com.ogc.menarini.GestioneFarmaci.Control;

import com.ogc.menarini.GestioneFarmaci.Interface.InterfacciaScaricoMerci;
import com.ogc.menarini.Main;
import com.ogc.menarini.Utils.DBMSDaemon;
import com.ogc.menarini.Utils.Utils;
import javafx.stage.Stage;

public class GestoreScaricoMerci {
    private InterfacciaScaricoMerci i;
    private Stage stage;

    public GestoreScaricoMerci() {
        this.stage = new Stage();
        i = (InterfacciaScaricoMerci) Utils.cambiaInterfaccia("GestioneFarmaci/ScaricaMerci.fxml", this.stage, c -> {
            return new InterfacciaScaricoMerci(this);
        }, 600, 400);
    }

    /**
     * Scarico merci inserendo il codice lotto e la quantità venduta
     * Inoltre se le scorte sono inferiori ad un limite inferiore prestabilito (5),
     * notifica il farmacista
     *
     * @param codiceLotto codice del lotto
     * @param quantita    quantita venduta
     */
    public void scaricoMerci(String farmaco, int quantita) {
        // TODO: Doccia pensiero: Cosa succede se carica un farmaco che non aveva
        // ordinato?
        int s = DBMSDaemon.queryScaricaMerci(farmaco, Main.idFarmacia, quantita);
        if (s == -1) {
            Utils.creaPannelloErrore(
                    "Il farmaco non è stato trovato o si è tentato di scaricare più farmaci di quanti presenti");
        } else {
            Utils.creaPannelloConferma("Merce scaricata correttamente!", this.stage);
        }
        if (DBMSDaemon.queryRecuperaFarmacoMagazzino(farmaco, Main.idFarmacia) <= 5) {
            Utils.creaPannelloErrore(
                    "Merce in esaurimento");
        }

    }
}
