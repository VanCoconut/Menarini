package com.ogc.menarini.GestioneConsegna.Control;

import com.ogc.menarini.Common.RecordOrdine;
import com.ogc.menarini.Entity.Ordine;
import com.ogc.menarini.GestioneConsegna.Interface.InterfacciaVisualizzaConsegne;
import java.util.*;
import com.ogc.menarini.Main;
import com.ogc.menarini.Utils.DBMSDaemon;
import com.ogc.menarini.Utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GestoreVisualizzaConsegne {

    public static ArrayList<RecordOrdine> listaOrdini = new ArrayList<>();

    public GestoreVisualizzaConsegne() {
        Stage stage = new Stage();
        chiediConsegne();
        Utils.cambiaInterfaccia("GestioneConsegna/VisualizzaConsegne.fxml", stage, c -> new InterfacciaVisualizzaConsegne(this));
    }


    public ArrayList<RecordOrdine> chiediConsegne() {
        listaOrdini = new ArrayList<RecordOrdine>();
        Ordine[] ordini = DBMSDaemon.queryVisualizzaConsegne(Main.orologio.chiediOrario().toLocalDate());
        if (ordini != null) {
            Main.log.info(Main.orologio.chiediOrario().toLocalDate());
            for (Ordine ordine : ordini) {
                //Main.log.info("Gli ordini in gestore sono: "+ordine);
                listaOrdini.add(RecordOrdine.fromOrdine(ordine));
            }
            Iterator<RecordOrdine> i = listaOrdini.iterator();
            while (i.hasNext()) {
                RecordOrdine o = i.next();
                //Main.log.info("dentro il gestore : " + o);
            }            
            return listaOrdini;
        }
        return null;
    }

    /**
     * Questo metodo serve solamente a poter aggiornare la lista dopo aver firmato, senza dover contattare il DB
     * @param aggiornato RecordCollo del collo aggiornato, senza nessun bottone e con la nuova firma
     */
    protected static void aggiornaTabella(RecordOrdine aggiornato){
        listaOrdini.removeIf(recordOrdini -> recordOrdini.id_ordine == aggiornato.id_ordine);
        listaOrdini.add(aggiornato);
    }
}
