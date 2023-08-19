package com.ogc.menarini.GestioneFarmaci.Control;

import com.ogc.menarini.Entity.Ordine;
import com.ogc.menarini.GestioneFarmaci.Interface.InterfacciaCaricoMerci;
import com.ogc.menarini.Main;
import com.ogc.menarini.Utils.DBMSDaemon;
import com.ogc.menarini.Utils.Utils;
import javafx.stage.Stage;

public class GestoreCaricoMerci {
    private InterfacciaCaricoMerci i;
    private Ordine ordine;
    private Stage stage;

    public GestoreCaricoMerci(Ordine ordine) {
        this.stage = new Stage();
        this.ordine = ordine;
        i = (InterfacciaCaricoMerci) Utils.cambiaInterfaccia("GestioneFarmaci/CaricaMerci.fxml", this.stage, c -> {
            return new InterfacciaCaricoMerci(this);
        }, 600, 400);
    }

    /**
     * Carica il lotto
     *
     * @param codiceLotto codice del lotto da caricare
     * @param quantita    quantità da caricare
     */
    public void caricaFarmaco(String nomeFarmaco, int quantita) {
     Main.log.info("ORDINE ID FARMACO "+ ordine.id_farmaco);

        if (ordine.quantita > quantita) {
            if (DBMSDaemon.queryRecuperaOrdine(nomeFarmaco, Main.idFarmacia, ordine.id_ordine)) {
                //Main.log.info("Sono entrato nell'id quantita minore");
                DBMSDaemon.queryCaricaFarmaco(ordine.id_farmaco,nomeFarmaco, Main.idFarmacia, quantita);
                DBMSDaemon.queryAggiornaStatoOrdine(ordine, "Da Verificare");
                //Main.log.info("Ho finito queryAggiornaStato");
                DBMSDaemon.queryErroreConsegna(ordine, quantita, Main.idFarmacia);
                Main.log.info("dopo erroe consegna"); 
                Utils.creaPannelloErrore("E' stata inserita una quantità di farmaci minore da quella presente nell'ordine");               
                stage.close();
            } else {
                Main.log.info("Errore nella query recupera ordine");
                Utils.creaPannelloErrore("Errore");
            }            
            return;
        }

        else if (ordine.quantita < quantita) {
            Utils.creaPannelloErrore(
                    "E' stata inserita una quantità di farmaci maggiore da quella presente nell'ordine");
            return;
        } else if (ordine.quantita == quantita) {
            if (DBMSDaemon.queryRecuperaOrdine(nomeFarmaco, Main.idFarmacia, ordine.id_ordine)) {

                DBMSDaemon.queryCaricaFarmaco(ordine.id_farmaco,nomeFarmaco, Main.idFarmacia, quantita);
                // Main.log.info("fine funzione carica farmaco");
                Utils.creaPannelloConferma("Merce Caricata Correttamente", this.stage);
                DBMSDaemon.queryAggiornaStatoOrdine(ordine, "Caricato");
                stage.close();

            } else {
                Main.log.info("Errore nella query recupera ordine");
                Utils.creaPannelloErrore("Errore");
            }
            return;
        } else {
            Utils.creaPannelloErrore(
                    "NON ME LO ASPETTAVO");
            Main.log.info("non me lo aspettavo");
        }

    }

}
