package com.ogc.menarini.GestioneConsegna.Interface;

import com.ogc.menarini.Common.InterfacciaPrincipale;
import com.ogc.menarini.GestioneConsegna.Control.GestoreVisualizzaConsegne;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class InterfacciaPrincipaleCorriere extends InterfacciaPrincipale {

    public InterfacciaPrincipaleCorriere(String nome) {
        super( nome );
    }

    @FXML
    public void cliccaVisualizzaConsegne() {
        new GestoreVisualizzaConsegne();
    }
}
