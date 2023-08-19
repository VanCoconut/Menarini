package com.ogc.menarini.GestioneConsegna.Interface;

import com.ogc.menarini.Entity.Ordine;
import com.ogc.menarini.GestioneConsegna.Control.GestoreFirmaConsegne;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class InterfacciaFirmaConsegne {
    
    GestoreFirmaConsegne gestoreFirmaConsegne;  

    public InterfacciaFirmaConsegne(GestoreFirmaConsegne gestoreFirmaConsegne) {
        this.gestoreFirmaConsegne = gestoreFirmaConsegne;
    }    
    
    @FXML
    protected void initialize() {

    }

    public void firmaConsegna() {
        //String firma = this.nomeFarmacista.getText() + " " + this.cognomeFarmacista.getText();
        //this.gestoreFirmaConsegne.firmaCollo(firma);
        this.gestoreFirmaConsegne.firmaCollo();
    }

    public void annulla() {
        //String firma = this.nomeFarmacista.getText() + " " + this.cognomeFarmacista.getText();
        //this.gestoreFirmaConsegne.firmaCollo(firma);
        this.gestoreFirmaConsegne.annulla();
    }  

    
}
