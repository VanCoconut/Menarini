package com.ogc.menarini.GestioneAzienda.Control;

import com.ogc.menarini.Entity.Ordine;
import com.ogc.menarini.GestioneAzienda.Interface.InterfacciaCorrezioneOrdine;
import com.ogc.menarini.Main;
import com.ogc.menarini.Utils.DBMSDaemon;
import com.ogc.menarini.Utils.Utils;
import javafx.stage.Stage;

public class GestoreCorrezioneOrdine {

    private final InterfacciaCorrezioneOrdine boundary;
    private final Ordine daCorreggere;

    public GestoreCorrezioneOrdine(Ordine daCorreggere) {
        Main.log.info("Tentando di correggere  l'ordine per " + daCorreggere.getData_consegna());
        this.boundary = (InterfacciaCorrezioneOrdine) Utils.cambiaInterfaccia("GestioneOrdini/CorrezioneOrdine.fxml", new Stage(), c -> new InterfacciaCorrezioneOrdine(this, daCorreggere), 600, 400);
        this.daCorreggere = daCorreggere;
    }

    /**
     * Correggi un ordine
     * <p>
     * <ul>
     *     <li>Chiedi al DB la quantità di quell'ordine già caricata dal farmacista</li>
     *     <li>Verifica che la quantità da integrare non sia maggiore di quella mancante</li>
     *     <li>Creo un ordine con la quantità inserita</li>
     * </ul>
     *
     * @param qtaDaAggiungere quantità da reintegrare in un nuovo ordine
     */
    public void correggiOrdine(int qtaDaAggiungere) {
       /*  int qtaGiaCaricata = DBMSDaemon.queryControllaQuantita(daCorreggere);
        if (qtaGiaCaricata == -1) {
            Main.log.error("Per qualche motivo la quantità caricata non è stata presa...");
            return;
        }
        Main.log.info("Quantità già caricata: " + qtaGiaCaricata);
        if (qtaDaAggiungere > (daCorreggere.getQuantita() - qtaGiaCaricata)) {
            // ho voluto aggiungere più farmaci di quanti ne mancano
            Utils.creaPannelloErrore("Non puoi correggere l'ordine inserendo più farmaci di quanti ne mancano");
            return;
        }*/
        daCorreggere.setStato("Corretto"); 
        DBMSDaemon.queryCorreggiOrdine(qtaDaAggiungere, daCorreggere);
        GestoreVisualizzaOrdiniAzienda.aggiornaTabella(daCorreggere);
        Main.log.info("Ordine corretto");
        Utils.creaPannelloConferma("Correzione effettuata con successo");
        ((Stage) boundary.qtaIntegrare.getScene().getWindow()).close();
    }
}


