package com.ogc.menarini.GestioneUtente.Control;

import com.ogc.menarini.Entity.Corriere;
import com.ogc.menarini.Entity.Impiegato;
import com.ogc.menarini.Entity.Utente;
import com.ogc.menarini.Entity.Farmacista;
import com.ogc.menarini.GestioneAzienda.Interface.InterfacciaPrincipaleImpiegato;
import com.ogc.menarini.GestioneConsegna.Interface.InterfacciaPrincipaleCorriere;
import com.ogc.menarini.GestioneFarmaci.Interface.InterfacciaPrincipaleFarmacista;
import com.ogc.menarini.GestioneUtente.Interface.ModuloLogin;
import com.ogc.menarini.Main;
import com.ogc.menarini.Utils.DBMSDaemon;
import com.ogc.menarini.Utils.Utils;
import javafx.stage.Stage;

public class GestoreAutenticazione {
    private static Utente utente;
    public static Farmacista farmacista;
    /*
     * private static Utente def = new Utente("Giovanni", "Muciaccia",
     * "test1@sium.it");
     * private static Utente def2 = new Corriere(11, "Giovanni", "Muciaccia",
     * "test1@sium.it", "PasswordTest");
     * private static Utente def3 = new Impiegato(22, "Giovanni", "Muciaccia",
     * "test1@sium.it", "PasswordTest");
     */

    public GestoreAutenticazione(Stage stage) {
        /*
         * if (Main.debug)
         * switch (Main.sistema) {
         * case 0 -> utente = def;
         * case 1 -> utente = def2;
         * case 2 -> utente = def3;
         * }
         */
        Utils.cambiaInterfaccia("GestioneAccount/Login.fxml", stage, c -> {
            return new ModuloLogin(this);
        });
    }

    /**
     * Controlla le credenziali ed effettua il Login
     *
     * <ul>
     * <li>Se {@systemProperty Main.debug} allora accetta anche credenziali
     * vuote</li>
     * <li>Altrimenti chiama
     * {@link DBMSDaemon#queryControllaCredenziali(String, String)
     * DBMSDaemon.queryControllaCredenziali(email, Hash(password)}</li>
     * </ul>
     * 
     * @param email    email dell'utente
     * @param password password in chiaro dell'utente, viene hashata in questo
     *                 metodo.
     * @param s        Stage in cui creare le
     *                 {@link com.ogc.menarini.Common.InterfacciaPrincipale
     *                 InterfacciaPrincipale[tipo="Main.sistema"]}
     */
    public void controlloCredenziali(String email, String password, Stage s) {

        if ((email.isBlank() || password.isBlank()) && !Main.debug) {
            Utils.creaPannelloErrore("Inserisci tutti i dati");
            return;
        }
        if (utente == null) { // Può non esserlo solo se siamo in debug
            utente = DBMSDaemon.queryControllaCredenziali(email, password);
            if (utente == null) {
                Utils.creaPannelloErrore("Credenziali inserite errate, ritenta");
                return;
            }
        }

        if (Main.sistema == 0) {

             if (utente != null) {
                farmacista = DBMSDaemon.queryRecuperaIdFarmacia(email);
                if (farmacista == null) {
                    Utils.creaPannelloErrore("Farmacista non trovato, ritenta");
                }
            } 
             Main.log.info("farmcista.id =" + farmacista.getId_farmacia());
            Main.idFarmacia=farmacista.getId_farmacia();
            Main.log.info("Main.id =" + Main.idFarmacia); 

            Utils.cambiaInterfaccia("GestioneFarmaci/InterfacciaFarmacista.fxml", s, c -> {
                return new InterfacciaPrincipaleFarmacista(GestoreAutenticazione.utente.email());
            });
        } else if (Main.sistema == 1) {
            Corriere corriere = (Corriere) utente;
            Utils.cambiaInterfaccia("GestioneConsegna/InterfacciaCorriere.fxml", s, c -> {
                return new InterfacciaPrincipaleCorriere(corriere.email());
            });
        } else if (Main.sistema == 2) {
            Impiegato impiegato = (Impiegato) utente;
            Utils.cambiaInterfaccia("GestioneOrdini/InterfacciaAzienda.fxml", s, c -> {
                return new InterfacciaPrincipaleImpiegato(impiegato.email());
            });
        }
    }

    /**
     * @return {@link Utente} autenticato
     */
    public static Utente getUtente() {
        return utente;
    }
}
