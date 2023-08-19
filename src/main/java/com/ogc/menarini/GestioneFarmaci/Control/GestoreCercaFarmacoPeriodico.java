package com.ogc.menarini.GestioneFarmaci.Control;

import com.ogc.menarini.Common.RecordFarmaco;
import com.ogc.menarini.Entity.Farmaco;
import com.ogc.menarini.GestioneFarmaci.Interface.InterfacciaCercaFarmacoPeriodico;
import com.ogc.menarini.Main;
import com.ogc.menarini.Utils.DBMSDaemon;
import com.ogc.menarini.Utils.Utils;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GestoreCercaFarmacoPeriodico {
    private InterfacciaCercaFarmacoPeriodico i;

    public GestoreCercaFarmacoPeriodico() {
       // Main.log.debug(" cerca farmaco chiamato");

        i = (InterfacciaCercaFarmacoPeriodico) Utils.cambiaInterfaccia("GestioneFarmaci/CercaFarmaco.fxml", new Stage(), c -> {
            return new InterfacciaCercaFarmacoPeriodico(this);
        });
        cercaFarmaci("", "");
        cercaTuttiFarmaci();
    }

    /**
     * Cerca un farmaco nel database, con le rispettive quantità presenti già nel magazzino della corrispondente farmacia
     *
     * @param nomeFarmaco Nome del farmaco
     * @param princAttivo Principio attivo
     */
    public void cercaFarmaci(String nomeFarmaco, String princAttivo) {
        //Main.log.info("cercaFarmaci");
        Farmaco[] farmaci = DBMSDaemon.queryCercaFarmacoPeriodico(nomeFarmaco, princAttivo);
        /* for (int index = 0; index < farmaci.length; index++) {
            Main.log.info("array di lista Farmaci dentro il gestore " + farmaci[index]);
        } */
        //Main.log.info("queryCercaFarmaci");
        if (farmaci == null) return;
        DBMSDaemon.queryQuantitaFarmaci(farmaci, Main.idFarmacia);
        //Main.log.info("queryQuantita nel gestore cerca farmaci");
        ArrayList<RecordFarmaco> listaFarmaci = new ArrayList<>();
        for (Farmaco f : farmaci) {
            listaFarmaci.add(RecordFarmaco.fromFarmaco(f, "Ordina", ordina -> new GestoreOrdinaFarmacoPeriodico(f)));
        }
        i.aggiornaFarmaci(listaFarmaci); 
    }
    
    public void cercaTuttiFarmaci() {
        // Main.log.info("cercaFarmaci");
        Farmaco[] farmaci = DBMSDaemon.queryCercaTuttiFarmacoPeriodico();
        /*
         * for (int index = 0; index < farmaci.length; index++) {
         * Main.log.info("array di lista Farmaci dentro il gestore " + farmaci[index]);
         * }
         */
        // Main.log.info("queryCercaFarmaci");
        if (farmaci == null)
            return;
        DBMSDaemon.queryQuantitaFarmaci(farmaci, Main.idFarmacia);
        // Main.log.info("queryQuantita nel gestore cerca farmaci");
        ArrayList<RecordFarmaco> listaFarmaci = new ArrayList<>();
        for (Farmaco f : farmaci) {
            listaFarmaci.add(RecordFarmaco.fromFarmaco(f, "Ordina", ordina -> new GestoreOrdinaFarmacoPeriodico(f)));
        }
        i.aggiornaFarmaci(listaFarmaci);
    }
}
