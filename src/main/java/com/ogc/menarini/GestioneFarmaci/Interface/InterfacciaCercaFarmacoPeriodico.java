package com.ogc.menarini.GestioneFarmaci.Interface;

import com.ogc.menarini.Common.RecordFarmaco;
import com.ogc.menarini.GestioneFarmaci.Control.GestoreCercaFarmaco;
import com.ogc.menarini.GestioneFarmaci.Control.GestoreCercaFarmacoPeriodico;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class InterfacciaCercaFarmacoPeriodico {
    private final GestoreCercaFarmacoPeriodico gestoreCercaFarmaco;
    @FXML
    private TextField nomeFarmaco;
    @FXML
    private TextField princAttivo;
    @FXML
    private TableView<RecordFarmaco> listaFarmaci;

    public InterfacciaCercaFarmacoPeriodico(GestoreCercaFarmacoPeriodico gestoreCercaFarmaco) {
        this.gestoreCercaFarmaco = gestoreCercaFarmaco;
    }

    @FXML
    protected void initialize() {
//        listaFarmaci.setFixedCellSize(55);
    }

    @FXML
    protected void conferma() {
        gestoreCercaFarmaco.cercaFarmaci(nomeFarmaco.getText(), princAttivo.getText());
        
    }

    public void aggiornaFarmaci(ArrayList<RecordFarmaco> ol) {
        listaFarmaci.setItems(FXCollections.observableArrayList(ol));
    }
}
