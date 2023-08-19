 package com.ogc.menarini.GestioneFarmaci.Control;
import com.ogc.menarini.Utils.DBMSDaemon;
import com.ogc.menarini.Utils.Utils;

import javafx.stage.Stage;
import com.ogc.menarini.GestioneFarmaci.Interface.InterfacciaIdFarmacista;

public class GestoreIdFarmacia {
    public String email;
   private InterfacciaIdFarmacista i;
    private Stage stage;

    public GestoreIdFarmacia(String email) {
        this.email=email;
         this.stage= new Stage();
        i = (InterfacciaIdFarmacista) Utils.cambiaInterfaccia("GestioneFarmaci/ModificaIdFarmacia.fxml", this.stage, c -> {
            return new InterfacciaIdFarmacista(this);
        }, 600, 400);  
    }

    /**
     * Scarico merci inserendo il codice lotto e la quantità venduta
     * Inoltre se le scorte sono inferiori ad un limite inferiore prestabilito (5), notifica il farmacista
     *
     * @param codiceLotto codice del lotto
     * @param quantita quantita venduta
     */
    public void modificaIdFarmacia(int id_f) {
        System.out.println("sei arrivato qui, complimenti");       
        if (DBMSDaemon.queryRecuperaIdFarmacia(email).getId_farmacia()!=0) {            
            Utils.creaPannelloErrore("Hai gia' inserito il tuo ID Farmacia!");
            return;
        }        
        if(DBMSDaemon.aggiornaIdFarmacia(id_f,email)){
            Utils.creaPannelloConferma("ID modificato con successo!", this.stage);
            return;
        }else{
            Utils.creaPannelloErrore("Errore inserimento ID");
            return;
        }  
    }
}
  