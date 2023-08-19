package com.ogc.menarini.GestioneFarmaci.Control;

import com.ogc.menarini.Entity.Farmaco;
import com.ogc.menarini.Entity.Ordine;
import com.ogc.menarini.GestioneFarmaci.Interface.InterfacciaOrdinaFarmacoPeriodico;
import com.ogc.menarini.GestioneFarmaci.Interface.PannelloAvvisoDisponibilita;
import com.ogc.menarini.Main;
import com.ogc.menarini.Utils.DBMSDaemon;
import com.ogc.menarini.Utils.Utils;
import javafx.stage.Stage;

import java.time.LocalDate;

//TODO: Javadocs, non so come scriverli
public class GestoreOrdinaFarmacoPeriodico {
    InterfacciaOrdinaFarmacoPeriodico i;
    Stage s;
    Farmaco f;

    public GestoreOrdinaFarmacoPeriodico(Farmaco f) {
        s = new Stage();
        this.f = f;
        i = (InterfacciaOrdinaFarmacoPeriodico) Utils.cambiaInterfaccia("GestioneFarmaci/OrdinaFarmaco.fxml", s, c -> new InterfacciaOrdinaFarmacoPeriodico(f, this));
    }

    public void creaOrdinePeriodico(int quantita, LocalDate d, boolean accettaInScadenza) {
        this.creaOrdinePeriodico(quantita, d, accettaInScadenza, false);
    }

    public void creaOrdinePeriodico(int quantita, LocalDate d, boolean accettaInScadenza, boolean confermato) {         
       
        Ordine o = new Ordine(f.nome,Main.idFarmacia,
                f.id_farmaco,quantita, d, "Periodico");     
        DBMSDaemon.queryCreaOrdinePeriodico(o);      
        Utils.creaPannelloConferma("Ordine Periodico Creato Correttamente");
        s.close(); 
    }
}
