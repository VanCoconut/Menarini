package com.ogc.menarini.GestioneFarmaci.Control;

import com.ogc.menarini.Common.RecordOrdine;
import com.ogc.menarini.Entity.Ordine;
import com.ogc.menarini.GestioneFarmaci.Interface.InterfacciaVisualizzaOrdini;
import com.ogc.menarini.GestioneFarmaci.Interface.InterfacciaVisualizzaOrdiniPeriodici;
import com.ogc.menarini.Main;
import com.ogc.menarini.Utils.DBMSDaemon;
import com.ogc.menarini.Utils.Utils;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.*;
import java.util.HashMap;

public class GestoreVisualizzaOrdiniPeriodici {

    private static ArrayList<RecordOrdine> listaOrdini = new ArrayList<>();

    public GestoreVisualizzaOrdiniPeriodici() {
        Main.log.debug(" visualizza ordini chiamato");

        listaOrdini.clear();
        Utils.cambiaInterfaccia("GestioneFarmaci/VisualizzaOrdiniPeriodici.fxml", new Stage(),
                c -> new InterfacciaVisualizzaOrdiniPeriodici(this));
    }

    public ArrayList<RecordOrdine> chiediOrdini() {
        Main.log.debug(" chiedi ordini");

        Ordine[] ordini = DBMSDaemon.queryVisualizzaOrdiniFarmaciaPeriodici(Main.idFarmacia);              
        if (ordini.length > 0) {            
            for (Ordine o : ordini) {                                              
                listaOrdini.add(RecordOrdine.fromOrdinePeriodico(o));                                
            }        }
        return listaOrdini;
    }

    protected static void aggiornaOrdine(Ordine aggiornato) {
         listaOrdini.removeIf(recordOrdine -> recordOrdine.getId_ordine() == aggiornato.getId_ordine());
        if (aggiornato.getQuantita() != 0){
            listaOrdini.add(0, RecordOrdine.fromOrdinePeriodico(aggiornato));
        }
    } 
}
