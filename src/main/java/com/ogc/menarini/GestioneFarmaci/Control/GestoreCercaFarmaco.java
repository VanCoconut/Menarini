package com.ogc.menarini.GestioneFarmaci.Control;

import com.ogc.menarini.Common.RecordFarmaco;
import com.ogc.menarini.Entity.Farmaco;
import com.ogc.menarini.GestioneFarmaci.Interface.InterfacciaCercaFarmaco;
import com.ogc.menarini.Main;
import com.ogc.menarini.Utils.DBMSDaemon;
import com.ogc.menarini.Utils.Utils;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GestoreCercaFarmaco {
    private InterfacciaCercaFarmaco i;

    public GestoreCercaFarmaco() {
        // Main.log.debug(" cerca farmaco chiamato");

        i = (InterfacciaCercaFarmaco) Utils.cambiaInterfaccia("GestioneFarmaci/CercaFarmaco.fxml", new Stage(), c -> {
            return new InterfacciaCercaFarmaco(this);
        });
        cercaFarmaci("", "");
        cercaTuttiFarmaci();
    }

    /**
     * Cerca un farmaco nel database, con le rispettive quantità presenti già nel
     * magazzino della corrispondente farmacia
     *
     * @param nomeFarmaco Nome del farmaco
     * @param princAttivo Principio attivo
     */
    public void cercaFarmaci(String nomeFarmaco, String princAttivo) {
        // Main.log.info("cercaFarmaci");
        Farmaco[] farmaci = DBMSDaemon.queryCercaFarmaco(nomeFarmaco, princAttivo);
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
            listaFarmaci.add(RecordFarmaco.fromFarmaco(f, "Ordina", ordina -> new GestoreOrdinaFarmaco(f)));
        }
        i.aggiornaFarmaci(listaFarmaci);
    }

    public void cercaTuttiFarmaci() {
         Main.log.info("cerca Tutti Farmaci");
        Farmaco[] farmaci = DBMSDaemon.queryCercaTuttiFarmaco();
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
            listaFarmaci.add(RecordFarmaco.fromFarmaco(f, "Ordina", ordina -> new GestoreOrdinaFarmaco(f)));
        }
        i.aggiornaFarmaci(listaFarmaci);
    }
}
