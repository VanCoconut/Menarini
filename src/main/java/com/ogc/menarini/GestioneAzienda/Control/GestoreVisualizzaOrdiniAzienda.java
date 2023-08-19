package com.ogc.menarini.GestioneAzienda.Control;

import com.ogc.menarini.Common.RecordOrdine;
import com.ogc.menarini.Entity.Ordine;
import com.ogc.menarini.GestioneAzienda.Interface.InterfacciaVisualizzaOrdiniAzienda;
import com.ogc.menarini.Main;
import com.ogc.menarini.Utils.DBMSDaemon;
import com.ogc.menarini.Utils.Utils;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;

public class GestoreVisualizzaOrdiniAzienda {
    private InterfacciaVisualizzaOrdiniAzienda i;
    private final static ArrayList<RecordOrdine> listaOrdiniAzienda = new ArrayList<>();

    public GestoreVisualizzaOrdiniAzienda() {
        listaOrdiniAzienda.clear();
        Ordine[] ordini = DBMSDaemon.queryVisualizzaOrdiniAzienda();
        if (ordini != null){
            for (int i = 0; i < ordini.length; i++) {
                Main.log.info("Sono entrato nel for");
                listaOrdiniAzienda.add(RecordOrdine.fromOrdine(ordini[i]));
                if(ordini[i].stato.equalsIgnoreCase("Da Verificare")){
                    Main.log.info("Sono entrato nell'id del for perche l'ordine era da verificare");
                    int nuovaqty= ordini[i].quantita-DBMSDaemon.queryNuovaQuantita(ordini[i].getId_ordine());
                    ordini[i].quantita=nuovaqty;
                } 
            }
        }   
        /* if (ordini != null) {
            //listaOrdiniAzienda.addAll(Arrays.stream(ordini).map(RecordOrdine::fromOrdine).toList());
            for (Ordine o : ordini) {
                //Main.log.info("sono entrato nel for ");
                         
                listaOrdiniAzienda.add(RecordOrdine.fromOrdine(o));              
            }
            Main.log.info("Sono nel NULL");
        } */
        this.i = (InterfacciaVisualizzaOrdiniAzienda) Utils.cambiaInterfaccia("GestioneOrdini/VisualizzaOrdiniAzienda.fxml", new Stage(), c -> new InterfacciaVisualizzaOrdiniAzienda(listaOrdiniAzienda));
    }

    /**
     * Aggiorna {@link GestoreVisualizzaOrdiniAzienda#listaOrdiniAzienda} rimuovendo (se presente un ordine con lo stesso id) ed inserendo nuovamente {@code aggiornato}
     * In questo modo la tabella viene aggiornata
     *
     * @param aggiornato l'ordine da rimuovere e inserire
     */
    protected static void aggiornaTabella(Ordine aggiornato) {
        listaOrdiniAzienda.removeIf(recordOrdine -> recordOrdine.getId_ordine() == aggiornato.getId_ordine());
        listaOrdiniAzienda.add(0, RecordOrdine.fromOrdine(aggiornato));
    }
}
