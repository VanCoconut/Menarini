package com.ogc.menarini.GestioneFarmaci.Control;

import com.ogc.menarini.Entity.Farmaco;
import com.ogc.menarini.Entity.Ordine;
import com.ogc.menarini.GestioneFarmaci.Interface.InterfacciaOrdinaFarmaco;
import com.ogc.menarini.GestioneFarmaci.Interface.PannelloAvvisoDisponibilita;
import com.ogc.menarini.Main;
import com.ogc.menarini.Utils.DBMSDaemon;
import com.ogc.menarini.Utils.Utils;
import javafx.stage.Stage;

import java.time.LocalDate;

//TODO: Javadocs, non so come scriverli
public class GestoreOrdinaFarmaco {
    InterfacciaOrdinaFarmaco i;
    Stage s;
    Farmaco f;

    public GestoreOrdinaFarmaco(Farmaco f) {
        s = new Stage();
        this.f = f;
        i = (InterfacciaOrdinaFarmaco) Utils.cambiaInterfaccia("GestioneFarmaci/OrdinaFarmaco.fxml", s, c -> new InterfacciaOrdinaFarmaco(f, this));
    }

    public void creaOrdine(int quantita, LocalDate d, boolean accettaInScadenza) {
        this.creaOrdine(quantita, d, accettaInScadenza, false);
    }

    public void creaOrdine(int quantita, LocalDate d, boolean accettaInScadenza, boolean confermato) {
       // Main.log.info("Sono  in crea ordine");
        if (accettaInScadenza && !confermato) {
            //Main.log.info("Sono  nel primo if in crea ordine");
           // Main.log.info("Dando l'ultima chance al king");
            Utils.creaPannelloAvvisoScadenza(crea -> {
                //Main.log.info("Il king ha deciso di proseguire con l'ordine in scadenza");
                creaOrdine(quantita, d, accettaInScadenza, true);
            }, chiudi -> {
               // Main.log.info("Il king ha annullato il pannello avviso scadenza");
            });
            return;
        }
        //Main.log.info("Sono fuori il primo if in crea ordine");
        Ordine o = new Ordine(f.nome,Main.idFarmacia,
                f.id_farmaco,quantita, d, "In Lavorazione");
        //int quantitaRisposta = DBMSDaemon.queryCreaOrdine(o, accettaInScadenza);
        int quantitaRisposta = DBMSDaemon.queryControlloDisponibilita(o, accettaInScadenza);
        Main.log.info("quantitarisposta : " + quantitaRisposta);
        if (quantitaRisposta > 0) {
            DBMSDaemon.queryCreaOrdine2(o, accettaInScadenza);
            DBMSDaemon.queryRimuoviScorteMagazzino(o, accettaInScadenza);
            Utils.creaPannelloConferma("Ordine Creato Correttamente");
            s.close();
        } else if (quantitaRisposta < 0) {
            Main.log.info("quantitarisposta negativa" );
            Stage s = new Stage();
            Utils.cambiaInterfaccia("GestioneFarmaci/ModalitaConsegnaPopup.fxml", s, c -> {
                return new PannelloAvvisoDisponibilita(stessaData -> {
                    ordinaStessaData(o, accettaInScadenza, quantita);
                    s.close();
                },
                        dateDiverse -> {
                            ordinaDateSeparate(o, accettaInScadenza, quantita, quantitaRisposta);
                            s.close();
                        });
            }, 600, 400);
        }
    }

    /**
     * Consente di ordinare tutta la quantità non disponibile per
     *
     * @param o
     * @param accettaInScadenza
     * @param quantita
     */
    private void ordinaStessaData(Ordine o, boolean accettaInScadenza, int quantita) {
        o.setQuantita(quantita);        
        DBMSDaemon.queryCreaOrdine(o, "In Attesa Di Disponibilita", false);
    }

    /**
     * Consente di dividere l'ordine in due ordini separati, uno con le quantità attualmente ordinabili e uno in attesa di disponibilità
     *
     * @param o
     * @param accettaInScadenza
     * @param quantita
     * @param quantitaRisposta
     */
    private void ordinaDateSeparate(Ordine o, boolean accettaInScadenza, int quantita, int quantitaRisposta) {
        o.setQuantita(quantita + quantitaRisposta);
        Main.log.info("set quantita " + o.getQuantita());
        if(o.getQuantita() > 0) {
            o.quantita= 0 - quantitaRisposta;
            Main.log.info("Ordine 1");
            DBMSDaemon.queryCreaOrdine(o, "In Lavorazione", accettaInScadenza);
            DBMSDaemon.queryRimuoviScorteMagazzino(o, accettaInScadenza);
            Utils.creaPannelloConferma("Ordine Creato Correttamente");
            s.close();
        }
        Main.log.info("Ordine 2");   
        o.setQuantita(quantita + quantitaRisposta);       
        Main.log.info("ordine 2 quantita = "+quantita+" quantita risposta = "+ quantitaRisposta); 
        DBMSDaemon.queryCreaOrdine(o, "In Attesa Di Disponibilita", false);        
        Utils.creaPannelloConferma("Ordine Creato Correttamente");
        s.close();
    }


}
