package com.ogc.menarini.GestioneAzienda.Interface;

import com.itextpdf.text.DocumentException;
import com.ogc.menarini.Common.RecordOrdine;
import com.ogc.menarini.Entity.Ordine;
import com.ogc.menarini.GestioneAzienda.Control.GestoreCorrezioneOrdine;
import com.ogc.menarini.Main;
import com.ogc.menarini.Utils.DBMSDaemon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InterfacciaVisualizzaOrdiniAzienda {


    public TableView<RecordOrdine> tableViewOrdini;
    private final ObservableList<RecordOrdine> observableOrdini;

    public InterfacciaVisualizzaOrdiniAzienda(ArrayList<RecordOrdine> listaOrdiniAzienda) {
        this.observableOrdini = FXCollections.observableList(listaOrdiniAzienda);
    }

    @FXML
    public void initialize() {
        this.tableViewOrdini.setItems(this.observableOrdini);
    }
}
