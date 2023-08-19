package com.ogc.menarini.Common;

import com.itextpdf.text.DocumentException;
import com.ogc.menarini.Entity.Ordine;
import com.ogc.menarini.GestioneAzienda.Control.GestoreCorrezioneOrdine;
import com.ogc.menarini.GestioneFarmaci.Control.GestoreCaricoMerci;
import com.ogc.menarini.GestioneFarmaci.Control.GestoreModificaOrdine;
import com.ogc.menarini.GestioneFarmaci.Control.GestoreModificaOrdinePeriodico;
import com.ogc.menarini.GestioneConsegna.Control.GestoreFirmaConsegne;
import com.ogc.menarini.Main;
import com.ogc.menarini.Utils.DBMSDaemon;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;

/**
 * Questa classe serve a poter visualizzare un {@link Collo} all'interno di una {@link javafx.scene.control.TableView tabella JavaFX}
 * <p>
 * In particolare è possibile aggiungere un bottone con un {@link RecordCollo#nomeBottone suo testo} ed una {@link RecordCollo#callback sua funzione}
 */
public class RecordOrdine extends Ordine {
    private final String nomeBottone;
    private final EventHandler<ActionEvent> callback;
    

    private Button bottone = null;

    public RecordOrdine(int id_ordine, int id_farmaco, String nome_farmaco, int id_farmacia, LocalDate data_consegna, String stato, int quantita, String nomeFarmacia, String nomeBottone, EventHandler<ActionEvent> callback) {
        super(id_ordine, id_farmaco, nome_farmaco, id_farmacia, data_consegna, stato, quantita);
        this.setNomeFarmacia(nomeFarmacia);
        //Main.log.info("SONO DENTRO IL RECORD E STAMPO NOME"+ nomeFarmacia+" INDIRIZZO "+ indirizzo);
        this.nomeBottone = nomeBottone;
        this.callback = callback;
        if (nomeBottone != null && !nomeBottone.isBlank()) {
            bottone = new Button(nomeBottone);
            bottone.setOnAction(callback);
            bottone.getStyleClass().add("btnlist");
            bottone.setMaxHeight(10);
        }
    }
    public RecordOrdine(String nomeFarmacia, String indirizzo,String firma, String nomeBottone, EventHandler<ActionEvent> callback) {
        super(nomeFarmacia, indirizzo, firma);
        this.nomeBottone = nomeBottone;
        this.callback = callback;
        if (nomeBottone != null && !nomeBottone.isBlank()) {
            bottone = new Button(nomeBottone);
            bottone.setOnAction(callback);
            bottone.getStyleClass().add("btnlist");
            bottone.setMaxHeight(10);
        }
    }   
    

    public RecordOrdine(Ordine ordine, Button bottone){
        super(ordine.getId_ordine(), ordine);
        this.bottone = bottone;
        this.bottone.getStyleClass().add("btnlist");
        this.callback = null;
        this.nomeBottone=null;
    }


    //  

    public RecordOrdine(String nome_farmaco, int id_farmacia, int quantita, LocalDate data_consegna, String stato, String nomeBottone, EventHandler<ActionEvent> callback) {
        super(nome_farmaco, id_farmacia, quantita, data_consegna, stato);
        this.nomeBottone = nomeBottone;
        this.callback = callback;
        if (nomeBottone != null && !nomeBottone.isBlank()) {
            bottone = new Button(nomeBottone);
            bottone.setOnAction(callback);
            bottone.getStyleClass().add("btnlist");
            bottone.setMaxHeight(10);
        }
    }

    public String getNomeBottone() {
        return nomeBottone;
    }

    public EventHandler<ActionEvent> getCallback() {
        return callback;
    }

    public Button getBottone() {
        return bottone;
    }

    public static RecordOrdine fromOrdine(Ordine ordine, String nomeBottone, EventHandler<ActionEvent> callback) { // TODO: Usare l'altro che aggiunge i bottoni in automatico
        return new RecordOrdine(ordine.getId_ordine(), ordine.getId_farmaco(), ordine.getNome_farmaco(), ordine.getId_farmacia(), ordine.getData_consegna(), ordine.getStato(), ordine.getQuantita(), ordine.getNomeFarmacia(), nomeBottone, callback);
    }

    /**
     * Aggiunge automaticamente un bottone in base all'ordine passato
     * <ul>
     *     <li>In Farmacia
     *         <ul>
     *             <li>Data di consegna > 2 giorni da oggi: {@link GestoreModificaOrdine#modificaOrdine(int, LocalDate)  Modifica}</li>
     *             <li>Data di consegna è oggi && Stato = Consegnato: {@link GestoreCaricoMerci#caricaFarmaco(int, int)  Carica}</li>
     *         </ul>
     *     </li>
     *     <li>In Azienda
     *          <ul>
     *              <li>Stato = Consegnato: {@link PDFCreator#creaPDF(Collo) Ricevuta}</li>
     *              <li>Stato = Da verificare: {@link GestoreCorrezioneOrdine#correggiOrdine(int) Correggi}</li>
     *          </ul>
     *      </li>
     * </ul>
     *
     * @param ordine Ordine da inserire in una tabella
     * @return RecordOrdine con il pulsante opportuno
     */
    public static RecordOrdine fromOrdine(Ordine ordine) {
        //Main.log.info("sono nel from Ordine " + Main.sistema +" " +ordine.getStato());
        String nomeBottone = "";
        EventHandler<ActionEvent> callback = null;
        if (Main.sistema == 0) { // Lato Farmacia i bottoni sono Modifica o Carica
            LocalDate d = Main.orologio.chiediOrario().toLocalDate();
            if (Duration.between(d.atTime(0, 0, 1), ordine.getData_consegna().atTime(0, 0, 1)).toDays() > 2) {
                nomeBottone = "Modifica";
                callback = modifica -> new GestoreModificaOrdine(ordine);
            } else if (ordine.getStato().equalsIgnoreCase("consegnato")) {
                nomeBottone = "Carica";
                callback = carica -> new GestoreCaricoMerci(ordine);
            }
            
        } 
        else if (Main.sistema == 1) { // Lato Corriere i bottoni è Firma
            nomeBottone = "";
            callback = null;
            if(ordine.firma.equalsIgnoreCase("non firmato")){
                nomeBottone="Firma";
                callback = firma -> new GestoreFirmaConsegne(ordine);
                }
                Main.log.info("dentro il from nomeF : "+ ordine.nomeFarmacia+" indirizzo: "+ ordine.indirizzo);
            return new RecordOrdine(ordine.nomeFarmacia, ordine.indirizzo, ordine.firma, nomeBottone, callback);
            }
        
        else if (Main.sistema == 2) { // Lato azienda i bottoni sono: Correggi e Ricevuta
            Main.log.info("sono nel from Ordine e stato: " +ordine.getStato());
            if (ordine.getStato().equalsIgnoreCase("consegnato") || ordine.getStato().equalsIgnoreCase("corretto") 
                    || ordine.getStato().equalsIgnoreCase("caricato")) {
                
                };
            } if (ordine.getStato().equalsIgnoreCase("da verificare")) {
                Main.log.info("sono nel bottone da verificare");
                nomeBottone = "Correggi";
                callback = correggi -> {
                    Main.log.info("Cliccato sul bottone correggi");
                    new GestoreCorrezioneOrdine(ordine);
                };
            }
        
        //return new RecordOrdine(ordine.getId_ordine(), ordine.getId_farmaco(), ordine.getNome_farmaco(), ordine.getId_farmacia(), ordine.getData_consegna(), ordine.getStato(), ordine.getQuantita(),ordine.getNomeFarmacia(), nomeBottone, callback);
        return new RecordOrdine(ordine.getNome_farmaco(), ordine. getId_farmacia(), ordine.getQuantita(), ordine.getData_consegna(), ordine.getStato(),nomeBottone, callback);

    }
    
    public static RecordOrdine fromOrdinePeriodico(Ordine ordine) {
        String nomeBottone = "";
        EventHandler<ActionEvent> callback = null;
        if (Main.sistema == 0) { // Lato Farmacia i bottoni sono Modifica 
            LocalDate d = Main.orologio.chiediOrario().toLocalDate();            
                nomeBottone = "Modifica";
                callback = modifica -> new GestoreModificaOrdinePeriodico(ordine);         

        } else if (Main.sistema == 1) { // Lato Corriere i bottoni sono ma io che ne so, ma io penso che non sono utulizzati
            nomeBottone = "";
            callback = null;
            if (ordine.firma.equalsIgnoreCase("non firmato")) {
                nomeBottone = "Firma";
                callback = firma -> new GestoreFirmaConsegne(ordine);
            }
            Main.log.info("dentro il from nomeF : " + ordine.nomeFarmacia + " indirizzo: " + ordine.indirizzo);
            return new RecordOrdine(ordine.nomeFarmacia, ordine.indirizzo,ordine.firma, nomeBottone, callback);
        }

        else if (Main.sistema == 2) { // Lato azienda i bottoni sono: Correggi e Ricevuta
            if (ordine.getStato().equalsIgnoreCase("consegnato") || ordine.getStato().equalsIgnoreCase("corretto")) {
            } else if (ordine.getStato().equalsIgnoreCase("da verificare")) {
                nomeBottone = "Correggi";
                callback = correggi -> {
                    Main.log.info("Cliccato sul bottone correggi");
                    new GestoreCorrezioneOrdine(ordine);
                };
            }
        }        
        return new RecordOrdine(ordine.getNome_farmaco(), ordine.getId_farmacia(), ordine.getQuantita(),
                ordine.getData_consegna(), ordine.getStato(), nomeBottone, callback);

    }
    
}
