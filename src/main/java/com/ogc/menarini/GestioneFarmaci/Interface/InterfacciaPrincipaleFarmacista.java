package com.ogc.menarini.GestioneFarmaci.Interface;

import com.ogc.menarini.Common.InterfacciaPrincipale;
import com.ogc.menarini.GestioneFarmaci.Control.GestoreCercaFarmaco;
import com.ogc.menarini.GestioneFarmaci.Control.GestoreVisualizzaOrdiniPeriodici;
import com.ogc.menarini.GestioneFarmaci.Control.GestoreCercaFarmacoPeriodico;
import com.ogc.menarini.GestioneFarmaci.Control.GestoreScaricoMerci;
import com.ogc.menarini.GestioneFarmaci.Control.GestoreVisualizzaOrdini;
import com.ogc.menarini.GestioneFarmaci.Control.GestoreIdFarmacia;
import com.ogc.menarini.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.util.*;
import com.ogc.menarini.Utils.DBMSDaemon;
import com.ogc.menarini.Entity.*;
import javafx.stage.Stage;
import com.ogc.menarini.Utils.Utils;

public class InterfacciaPrincipaleFarmacista extends InterfacciaPrincipale {
    public String email;
    BoundaryDiSistemaFarmacista b = new BoundaryDiSistemaFarmacista();

    public InterfacciaPrincipaleFarmacista(String nome) {
        super(nome + "  " + Main.idFarmacia);
        this.email = nome;
        Main.log.info("Gestione Farmaci");
    }

    @FXML
    public void initialize() {
        super.initialize();
        Main.orologio.setOrologio(e -> {
            b.chiediOrario();
        });
        Main.log.info("INITIALIZE");

        Ordine[] ordini = DBMSDaemon.queryRecuperaOrdiniPeriodici();
        Calendar cal = Calendar.getInstance();
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR);
        if (ordini.length == 0)
            return;
        if (dayOfMonth == 1) {
            Stage s = new Stage();
            Utils.creaPannelloConferma("Ordini Periodici controllatie inviati se possibile");
            s.close();
            for (int index = 0; index < ordini.length; index++) {

                Main.log.info("Seiamo nel primo giorno, allora controllo gliordini peridoici");
                DBMSDaemon.queryControlloOrdinePeriodico(ordini[index]);

            }
        }
        if (dayOfMonth == 2) {
            for (int index = 0; index < ordini.length; index++) {

                Main.log.info("Seiamo nel primo giorno, allora controllo gliordini peridoici");
                DBMSDaemon.queryAggiornaStatoPeriodico(ordini[index].id_ordine, "Periodico");

            }
        }
        if(hour==20){
            Utils.creaPannelloErrore("Assicurati ti aver caricato i farmaci consegnati oggi!");
        }
    }

    @FXML
    protected void cliccaCercaFarmaco() {
        // Main.log.debug(" ha cliccato su cerca farmaci");
        new GestoreCercaFarmaco();
    }

    @FXML
    private void cliccaVisualizzaOrdini() {
        // Main.log.debug(" ha cliccato su visualizza ordini faramcia");
        new GestoreVisualizzaOrdini();
    }

    @FXML
    protected void cliccaScaricoMerci() {
        new GestoreScaricoMerci();
    }

    @FXML
    protected void cliccaCercaFarmacoPeriodico() {
        // Main.log.debug(" ha cliccato su cerca farmaci");
        new GestoreCercaFarmacoPeriodico();
    }

    @FXML
    private void cliccaVisualizzaOrdiniPeriodici() {
        // Main.log.debug(" ha cliccato su visualizza ordini faramcia");
        new GestoreVisualizzaOrdiniPeriodici();
    }    

    @FXML
    public void cliccaModificaIdFarmacia() {
        new GestoreIdFarmacia(email);
    }
}
