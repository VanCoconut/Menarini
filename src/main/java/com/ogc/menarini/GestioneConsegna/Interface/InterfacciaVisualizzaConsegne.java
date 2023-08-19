package com.ogc.menarini.GestioneConsegna.Interface;

import com.ogc.menarini.Common.RecordOrdine;
import com.ogc.menarini.GestioneConsegna.Control.GestoreVisualizzaConsegne;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import com.ogc.menarini.Main;

public class InterfacciaVisualizzaConsegne {
    GestoreVisualizzaConsegne gestoreVisualizzaConsegne;

    @FXML
    public TableView<RecordOrdine> tableColli;
    public ObservableList<RecordOrdine> listaColli;

    

    public InterfacciaVisualizzaConsegne(GestoreVisualizzaConsegne gestoreVisualizzaConsegne) {
        this.gestoreVisualizzaConsegne = gestoreVisualizzaConsegne;
        this.listaColli = FXCollections.observableList(gestoreVisualizzaConsegne.chiediConsegne());
        /* Main.log.info("interfaccia  firma "+listaColli.get(1).firma);
        Main.log.info("interfaccia indirizzo "+listaColli.get(1).indirizzo); */
    }

    public void initialize() {        
        this.tableColli.setItems(this.listaColli);

    }

}
