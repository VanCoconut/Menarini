package com.ogc.menarini.GestioneAzienda.Interface;

import com.ogc.menarini.Common.InterfacciaPrincipale;
import com.ogc.menarini.GestioneAzienda.Control.GestoreModificaProduzione;
import com.ogc.menarini.GestioneAzienda.Control.GestoreDiSistemaImpiegato;
import com.ogc.menarini.GestioneAzienda.Control.GestoreVisualizzaOrdiniAzienda;
import com.ogc.menarini.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class InterfacciaPrincipaleImpiegato extends InterfacciaPrincipale {
    BoundaryDiSistemaImpiegato b=new BoundaryDiSistemaImpiegato();
    public InterfacciaPrincipaleImpiegato(String nome) {
        super(nome );
        new GestoreDiSistemaImpiegato().chiediData();
    }

    @FXML
    public void initialize(){
        super.initialize();
        Main.orologio.setOrologio(e->{           
            //b.chiediData();
        });
    }
    @FXML
    public void cliccaVisualizzaOrdini() {
        Main.log.debug("Impiegato ha cliccato su visualizza ordini azienda");
        new GestoreVisualizzaOrdiniAzienda();
    }

    public void cliccaModificaProduzione() {
        Main.log.debug("Impiegato ha cliccato su modifica produzione");
        new GestoreModificaProduzione();
    }
}
