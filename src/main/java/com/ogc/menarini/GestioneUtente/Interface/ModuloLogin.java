package com.ogc.menarini.GestioneUtente.Interface;

import com.ogc.menarini.GestioneUtente.Control.GestoreAutenticazione;
import com.ogc.menarini.GestioneUtente.Control.GestoreRegistrazione;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModuloLogin {
    GestoreAutenticazione gestoreAutenticazione;
    Stage s;

    public ModuloLogin(GestoreAutenticazione gestoreAutenticazione) {
        this.gestoreAutenticazione = gestoreAutenticazione;        // System.out.println("cliccaAccedi "+email.getText()+ " "+password.getText());

    }

    @FXML
    private TextField email;
    @FXML
    private PasswordField password;

    @FXML
    protected void cliccaAccedi() {
        gestoreAutenticazione.controlloCredenziali(email.getText(), password.getText(), (Stage) email.getScene().getWindow());
    }


    @FXML
    protected void cliccaRegistrati() {
        new GestoreRegistrazione();
    }
}
