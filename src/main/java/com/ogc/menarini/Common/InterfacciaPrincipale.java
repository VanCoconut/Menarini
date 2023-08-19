package com.ogc.menarini.Common;

import com.ogc.menarini.GestioneFarmaci.Interface.BoundaryDiSistemaFarmacista;
import com.ogc.menarini.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class InterfacciaPrincipale {
    String nomeMostrato;
    @FXML
    protected Label nomeUtente;
    @FXML
protected Label orologio;

    public InterfacciaPrincipale(String nomeUtente) {
        nomeMostrato = nomeUtente;
    }

    @FXML
    protected void initialize() {
        nomeUtente.setText(nomeMostrato);
        Main.orologio.setOrologio(e -> {
            orologio.setText(Main.orologio.chiediOrarioFormattato());
            //b.chiediOrario();
        });
    }

}
