package com.ogc.menarini.GestioneUtente.Control;

import com.ogc.menarini.Main;
import com.ogc.menarini.GestioneUtente.Interface.ModuloRegistrazione;
import com.ogc.menarini.Utils.DBMSDaemon;
import com.ogc.menarini.Utils.Utils;
import javafx.stage.Stage;

public class GestoreRegistrazione {
    // private String otp;
    private String nome, cognome, email, password;
    private Stage stage;

    public GestoreRegistrazione() {
        this.stage = new Stage();
        Utils.cambiaInterfaccia("GestioneAccount/Registrazione.fxml", stage, c -> new ModuloRegistrazione(this));
    }

    /*
     * public void inviaMailOTP(String mail) {
     * otp = String.format("%06d", (int) (Math.random() * 1000000));
     * MailUtils.inviaMail("Il tuo codice OTP è: " + otp, mail, "Codice OTP");
     * }
     */

    public void registrazione(String e, String p) {     
        if (DBMSDaemon.queryRegistraUtente(e, p)) {
            Utils.creaPannelloConferma("Utente registrato correttamente"); 
            stage.close();
        }else {     
            Utils.creaPannelloErrore("Impossibile creare questo account");
        }
    }
    /*
     * public boolean controllaValiditaOTP(String otp) {
     * if (this.otp.equals(otp)) {
     * Main.log.info("otpCorretto");
     * return true;
     * }
     * return false;
     * }
     */

    /**
     * Vedi {@link GestoreModificaPassword#controllaPassword(String, String)}
     * <p>
     * In più, questo controlla la validità della mail presente in
     * {@link GestoreRegistrazione#email}
     * <p>
     */
    private boolean controllaValiditaPwd(String password, String re_pwd) {
        /*
         * boolean isValid = password.length() <= 20 && password.length() >= 8;
         * String upperCaseChars = "(.*[A-Z].*)";
         * if (!password.matches(upperCaseChars)) isValid = false;
         * String lowerCaseChars = "(.*[a-z].*)";
         * if (!password.matches(lowerCaseChars)) isValid = false;
         * String numbers = "(.*[0-9].*)";
         * if (!password.matches(numbers)) isValid = false;
         * String rfcCompliantMailPattern =
         * "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
         * if (!email.matches(rfcCompliantMailPattern)) {
         * isValid = false;
         * }
         * return isValid && password.equals(re_pwd);
         */
        return password.equals(re_pwd);
    }

    /**
     * Metodo della control
     * <p>
     * Registra un utente, controllando la validità dei dati con
     * {@link GestoreRegistrazione#controllaValiditaPwd(String, String)
     * controllaValiditaPwd}
     *
     * @param nome     nome dell'utente
     * @param cognome  cognome dell'utente
     * @param email    email dell'utente
     * @param password password (in chiaro) dell'utente
     * @param re_pwd   conferma della password dell'utente
     * @return true o false se la registrazione va a buon fine o meno
     */
    public boolean registraAccount(String email, String password, String re_pwd) {
        if (email.isBlank() || password.isBlank()) {
            Utils.creaPannelloErrore("Inserisci tutti i dati");
            return false;
        }
        /*
         * this.nome = nome;
         * this.cognome = cognome;
         */
        this.email = email;
        if (!controllaValiditaPwd(password, re_pwd)) {
            Utils.creaPannelloErrore("I dati inseriti non sono validi");
            return false;
        }
        if (DBMSDaemon.queryVerificaEsistenzaMail(email)) {
            Utils.creaPannelloErrore("Esiste già un account collegato a questa e-mail");
            return false;
        }
        this.password = password;
        
        registrazione(this.email, this.password);
        return true;
    }
}
