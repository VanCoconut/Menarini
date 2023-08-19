package com.ogc.menarini.GestioneFarmaci.Control;

import com.ogc.menarini.Common.RecordOrdine;
import com.ogc.menarini.Entity.Ordine;
import com.ogc.menarini.GestioneFarmaci.Interface.InterfacciaVisualizzaOrdini;
import com.ogc.menarini.Main;
import com.ogc.menarini.Utils.DBMSDaemon;
import com.ogc.menarini.Utils.Utils;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.*;
import java.util.HashMap;

public class GestoreVisualizzaOrdini {

    private static ArrayList<RecordOrdine> listaOrdini = new ArrayList<>();

    public GestoreVisualizzaOrdini() {
        Main.log.debug(" visualizza ordini chiamato");

        listaOrdini.clear();
        Utils.cambiaInterfaccia("GestioneFarmaci/VisualizzaOrdiniFarmacia.fxml", new Stage(),
                c -> new InterfacciaVisualizzaOrdini(this));
    }

    public ArrayList<RecordOrdine> chiediOrdini() {
        Main.log.debug(" chiedi ordini");

        Ordine[] ordini = DBMSDaemon.queryVisualizzaOrdiniFarmacia(Main.idFarmacia);
        /* Main.log.info("Lunghezza array " + ordini.length);
        Main.log.info("nome farmaco " + ordini[0].getNome_farmaco()); */

        // var merceCaricata = DBMSDaemon.queryMerceCaricata(Main.idFarmacia,
        // Main.orologio.chiediOrario().toLocalDate());
        // if (merceCaricata == null) merceCaricata = new HashMap<>();
        if (ordini.length > 0) {
            //Main.log.info("sono entrato nel 1 if ");
            for (Ordine o : ordini) {
                //Main.log.info("sono entrato nel for ");
                if (o.getData_consegna().isEqual(Main.orologio.chiediOrario().toLocalDate())
                        && (o.getStato().equalsIgnoreCase("consegnato"))) {
                    //Main.log.info("sono entrato nel 2 if ");
                }               
                listaOrdini.add(RecordOrdine.fromOrdine(o));              
            }
        }

        return listaOrdini;
    }

    protected static void aggiornaOrdine(Ordine aggiornato) {
         listaOrdini.removeIf(recordOrdine -> recordOrdine.getId_ordine() == aggiornato.getId_ordine());
        if (aggiornato.getQuantita() != 0){
            listaOrdini.add(0, RecordOrdine.fromOrdine(aggiornato));
        }
    } 
}
