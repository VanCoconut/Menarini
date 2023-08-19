package com.ogc.menarini.GestioneConsegna.Control;

import com.ogc.menarini.Common.RecordOrdine;
import com.ogc.menarini.Entity.Ordine;
import com.ogc.menarini.GestioneConsegna.Interface.InterfacciaFirmaConsegne;
import com.ogc.menarini.Main;
import com.ogc.menarini.Utils.DBMSDaemon;
import com.ogc.menarini.Utils.Utils;
import javafx.stage.Stage;

public class GestoreFirmaConsegne {
    public Ordine daFirmare;
    private final Stage stage;

    public GestoreFirmaConsegne(Ordine colloDaFirmare) {
        this.stage = new Stage();
        this.daFirmare = colloDaFirmare;
        Utils.cambiaInterfaccia("GestioneConsegna/FirmaConsegne.fxml", this.stage, c -> new InterfacciaFirmaConsegne(this),
                600, 400);
    }

    public void firmaCollo() {
        
        Main.log.info("Sto firmando il collo");
        if (DBMSDaemon.queryFirmaCollo(daFirmare)==1)
            GestoreVisualizzaConsegne.aggiornaTabella(RecordOrdine.fromOrdine(daFirmare));
        this.stage.close();
    }

    public void annulla() {

        Main.log.info("annulla");        
        this.stage.close();

    }
}
