package com.ogc.menarini.Utils;

import com.ogc.menarini.Entity.*;
import com.ogc.menarini.GestioneConsegna.Control.GestoreVisualizzaConsegne;
import com.ogc.menarini.Main;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.HashMap;

@SuppressWarnings({ "unused", "UnusedReturnValue", "DuplicatedCode", "SqlResolve" })
public class DBMSDaemon {
    
    private static Connection conn = null;

    /**
     * Metodo che mostra il pannello di errore e logga l'errore.
     *
     * @param e errore da loggare
     */
    private static void erroreComunicazioneDBMS(Exception e) {
        Main.log.error("Errore durante comunicazione con DBMS", e);
        Utils.creaPannelloErrore("C'è stato un problema durante la comunicazione con la base di dati, riprova");
    }

    /**
     * @hidden
     */
    private static String buildConnectionUrl() {
        return "jdbc:mariadb://localhost:3307/progetto?user=root&password=ciao";

    }

    /**
     * Metodo che permette di connettersi ad entrambi i Databases.
     * <p>
     * Vedi {@link DBMSDaemon#connect()} e
     * {@link DBMSDaemon#connect()}
     */
    public static void connect() {
        try {
            if (conn == null || conn.isClosed()) {
                Main.log.debug("Connettendo con Azienda...");
                DBMSDaemon.conn = DriverManager.getConnection(buildConnectionUrl());
                Main.log.info("Connesso con Azienda");
            }
        } catch (java.sql.SQLException e) {
            erroreComunicazioneDBMS(e);
        }
    }

    /**
     * Metodo utilizzato per connettersi al DBMS Farmacie.
     * Se la connessione è già stata stabilita in precedenza, viene utilizzata
     * quest'ultima.
     */


    /**
     * Metodo utilizzato per connettersi al DBMS Azienda.
     * Se la connessione è già stata stabilita in precedenza, viene utilizzata
     * quest'ultima.
     */

    /**
     * Query per controllare le credenziali di un utente in base alla variabile
     * {@code sistema} di {@link Main}
     * <br>
     * <br>
     * Se {@code Main.sistema == 0} allora
     * {@link #queryControllaCredenzialiFarmacista(String, String)
     * ControllaCredenzialiFarmacista()}<br>
     * Se {@code Main.sistema == 1} allora
     * {@link #queryControllaCredenzialiImpiegato(String, String)
     * ControllaCredenzialiImpiegato()} <br>
     * Se {@code Main.sistema == 2} allora
     * {@link #queryControllaCredenzialiCorriere(String, String)
     * ControllaCredenzialiCorriere()}<br>
     *
     * @param mail     mail da controllare
     * @param password password da controllare
     * @return Oggetto di tipo {@link Utente}. Fai il cast a {@link Farmacista},
     *         {@link Impiegato} o {@link Corriere} a seconda del sistema in cui ti
     *         trovi
     */
    public static Utente queryControllaCredenziali(String mail, String password) {
        if (Main.sistema == 0)
            return queryControllaCredenzialiFarmacista(mail, password);
        if (Main.sistema == 1)
            return queryControllaCredenzialiCorriere(mail, password);
        if (Main.sistema == 2)
            return queryControllaCredenzialiImpiegato(mail, password);
        System.out.println("Errore main.sistema imprevisto");
        return null;// non dovrebbe succedere mai?
    }

    /**
     * Query per registare un utente in base alla variabile {@code sistema} di
     * {@link Main}.
     * <br>
     * <br>
     * Se {@code Main.sistema == 0} allora
     * {@link #queryRegistraFarmacista(int, String, String, String, String)
     * queryRegistraFarmacista(Main.idFarmacia)} <br>
     * Se {@code Main.sistema == 1} allora
     * {@link #queryRegistraCorriere(String, String, String, String)
     * queryRegistraCorriere()}<br>
     * Se {@code Main.sistema == 2} allora
     * {@link #queryRegistraImpiegato(String, String, String, String)
     * queryRegistraImpiegato()}<br>
     *
     * @param nome     nome utente
     * @param cognome  cognome utente
     * @param email    email utente
     * @param password password utente hashata
     * @return {@code true} se la registrazione ha avuto successo, {@code false} se
     *         non ha avuto successo
     */
    public static boolean queryRegistraUtente(String email, String password) {
        if (Main.sistema == 0)
            return queryRegistraFarmacista(Main.idFarmacia, email, password);
        if (Main.sistema == 1)
            return queryRegistraCorriere(email, password);
        if (Main.sistema == 2)
            return queryRegistraImpiegato(email, password);
        return false;
    }

    /**
     * Query per verificare 'esistenza di una mail in base alla variabile
     * {@code sistema} di {@link Main}.
     * <br>
     * <br>
     * Se {@code Main.sistema == 0} allora
     * {@link #queryVerificaEsistenzaMailFarmacista(String)
     * queryVerificaEsistenzaMailFarmacista()} <br>
     * Se {@code Main.sistema == 1} allora
     * {@link #queryVerificaEsistenzaMailCorriere(String)
     * queryVerificaEsistenzaMailCorriere()} <br>
     * Se {@code Main.sistema == 2} allora
     * {@link #queryVerificaEsistenzaMailImpiegato(String)
     * queryVerificaEsistenzaMailImpiegato()} <br>
     *
     * @param mail mail da verificare
     * @return true o false se esiste o meno
     */
    public static boolean queryVerificaEsistenzaMail(String mail) {
        if (Main.sistema == 0)
            return queryVerificaEsistenzaMailFarmacista(mail);
        if (Main.sistema == 1)
            return queryVerificaEsistenzaMailCorriere(mail);
        if (Main.sistema == 2)
            return queryVerificaEsistenzaMailImpiegato(mail);
        return false;
    }

    /**
     * Query per modificare la password di un utente conoscendo quella vecchia in
     * base alla variabile {@code sistema} di {@link Main}.
     * <br>
     * <br>
     * Se {@code Main.sistema == 0} allora
     * {@link #queryModificaPasswordFarmacista(String, String, String)
     * queryModificaPasswordFarmacista()} <br>
     * Se {@code Main.sistema == 1} allora
     * {@link #queryModificaPasswordCorriere(String, String, String)
     * queryModificaPasswordCorriere()} <br>
     * Se {@code Main.sistema == 2} allora
     * {@link #queryModificaPasswordImpiegato(String, String, String)
     * queryModificaPasswordImpiegato()} <br>
     *
     * @param mail            mail dell'utente
     * @param password        nuova password
     * @param vecchiaPassword vecchia password
     * @return True se la password è stata modificata con successo
     */
    public static boolean queryModificaPassword(String mail, String password, String vecchiaPassword) {
        if (Main.sistema == 0)
            return queryModificaPasswordFarmacista(mail, password, vecchiaPassword);
        if (Main.sistema == 1)
            return queryModificaPasswordCorriere(mail, password, vecchiaPassword);
        if (Main.sistema == 2)
            return queryModificaPasswordImpiegato(mail, password, vecchiaPassword);
        return false;// non dovrebbe succedere mai?
    }

    /**
     * Aggiorna la password di {@code mail} senza sapere la password vecchia, usato
     * nel caso d'uso recupera credenziali.
     * <br>
     * <br>
     * Se {@code Main.sistema == 0} allora
     * {@link #queryAggiornaPasswordFarmacista(String, String)
     * queryAggiornaPasswordFarmacista()} <br>
     * Se {@code Main.sistema == 1} allora
     * {@link #queryAggiornaPasswordCorriere(String, String)
     * queryAggiornaPasswordCorriere()} <br>
     * Se {@code Main.sistema == 2} allora
     * {@link #queryAggiornaPasswordImpiegato(String, String)
     * queryAggiornaPasswordImpiegato()} <br>
     *
     * @param mail    mail dell'account
     * @param new_pwd nuova password generata dal sistema
     * @return True se la password è stata aggiornata con successo
     */
    public static boolean queryAggiornaPassword(String mail, String new_pwd) {
        if (Main.sistema == 0)
            return queryAggiornaPasswordFarmacista(mail, new_pwd);
        if (Main.sistema == 1)
            return queryAggiornaPasswordCorriere(mail, new_pwd);
        if (Main.sistema == 2)
            return queryAggiornaPasswordImpiegato(mail, new_pwd);
        return false; // non dovrebbe mai succedere
    }

    // -- Query Gestione Account

    /**
     * @param mail     Mail del farmacista
     * @param password Password del farmacista
     * @return {@link Farmacista}
     * @hidden Controlla Credenziali utenza farmacista
     */
    private static Farmacista queryControllaCredenzialiFarmacista(String mail, String password) {
        connect();
        var query = "SELECT utenti.* FROM utenti WHERE username = ? and password = ? and tipo = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {            
            
            stmt.setString(1, mail);
            stmt.setString(2, password);
            stmt.setInt(3, 0);
            var r = stmt.executeQuery();
            if (r.next()) {
                return Farmacista.createFromDB(r);
            }
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    /**
     * @param mail     mail dell'impiegato
     * @param password password dell'impiegato
     * @return {@link Impiegato}
     * @hidden Controlla Credenziali utenza impiegato
     */
    private static Impiegato queryControllaCredenzialiImpiegato(String mail, String password) {
        connect();
        var query = "SELECT utenti.* FROM utenti WHERE username = ? AND password = ? AND tipo=?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, mail);
            stmt.setString(2, password);
            stmt.setInt(3,2);
            var r = stmt.executeQuery();
            if (r.next()) {
                return Impiegato.createFromDB(r);
            }
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    /**
     * @param mail     mail del corriere
     * @param password password del corriere
     * @return {@link Corriere}
     * @hidden Controlla Credenziali utenza corriere
     */
    private static Corriere queryControllaCredenzialiCorriere(String mail, String password) {
        connect();
        var query = "SELECT utenti.* FROM utenti WHERE username = ? and password = ? and tipo=?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, mail);
            stmt.setString(2, password);
            stmt.setInt(3, 1);
            var r = stmt.executeQuery();
            if (r.next()) {
                return Corriere.createFromDB(r);
            }
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    /**
     * @param mail       mail dell'utente
     * @param password   password dell'utente
     * @param vecchiaPsw psw da modificare
     * @return true se la password è stata aggiornata
     * @hidden Aggiorna la password di un Farmacista quella vecchia
     */
    private static boolean queryModificaPasswordFarmacista(String mail, String password, String vecchiaPsw) {
        connect();
        String query = "UPDATE Farmacista SET Farmacista.password = ? WHERE email = ? AND password=?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, password);
            stmt.setString(2, mail);
            stmt.setString(3, vecchiaPsw);
            var r = stmt.executeUpdate();
            return r == 1;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return false;
    }

    /**
     * @param mail       mail dell'utente
     * @param password   password dell'utente
     * @param vecchiaPsw psw da modificare
     * @return 1 if password aggiornata, -1 altrimenti
     * @hidden Aggiorna la password di un Impiegato conoscendo quella vecchia
     */
    private static boolean queryModificaPasswordImpiegato(String mail, String password, String vecchiaPsw) {
        connect();
        String query = "UPDATE Impiegato SET Impiegato.password = ? WHERE email = ? AND password=?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, password);
            stmt.setString(2, mail);
            stmt.setString(3, vecchiaPsw);
            var r = stmt.executeUpdate();
            return r == 1;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return false;
    }

    /**
     * @param mail       mail dell'utente
     * @param password   password dell'utente
     * @param vecchiaPsw psw da modificare
     * @return 1 if password aggiornata, -1 altrimenti
     * @hidden Aggiorna la password di un Corriere conoscendo quella vecchia
     */
    private static boolean queryModificaPasswordCorriere(String mail, String password, String vecchiaPsw) {
        connect();
        String query = "UPDATE Corriere SET Corriere.password = ? WHERE email = ? AND password=?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, password);
            stmt.setString(2, mail);
            stmt.setString(3, vecchiaPsw);
            var r = stmt.executeUpdate();
            return r == 1;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return false;
    }

    /**
     * @param mail    mail dell'utente
     * @param new_pwd nuova password generata dal sistema
     * @return 1 if success, -1 if error
     * @hidden Query richiamata dentro Recupera credenziali, non necessita della
     *         vecchia psw per modificarla
     */
    private static boolean queryAggiornaPasswordCorriere(String mail, String new_pwd) {
        connect();
        String query = "UPDATE Corriere SET Corriere.password = ? WHERE email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, new_pwd);
            stmt.setString(2, mail);
            var r = stmt.executeUpdate();

            return r == 1;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return false;
    }

    /**
     * @param mail    mail dell'utente
     * @param new_pwd nuova password generata dal sistema
     * @return 1 if success, -1 if error
     * @hidden Query richiamata dentro Recupera credenziali, non necessita della
     *         vecchia psw per modificarla
     */
    private static boolean queryAggiornaPasswordFarmacista(String mail, String new_pwd) {
        connect();
        String query = "UPDATE Farmacista SET Farmacista.password = ? WHERE email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, new_pwd);
            stmt.setString(2, mail);
            var r = stmt.executeUpdate();

            return r == 1;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return false;
    }

    /**
     * @param mail    mail dell'utente
     * @param new_pwd nuova password generata dal sistema
     * @return 1 if success, -1 if error
     * @hidden Query richiamata dentro Recupera credenziali, non necessita della
     *         vecchia psw per modificarla
     */
    private static boolean queryAggiornaPasswordImpiegato(String mail, String new_pwd) {
        connect();
        String query = "UPDATE Impiegato SET Impiegato.password = ? WHERE email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, new_pwd);
            stmt.setString(2, mail);
            var r = stmt.executeUpdate();

            return r == 1;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return false;
    }

    /**
     * @param id_farmacia id farmacia
     * @param nome        nome farmacista
     * @param cognome     cognome farmacista
     * @param email       mail farmacista
     * @param password    password hashata
     * @return true if success, false if error
     * @hidden Registra un nuovo farmacista all'interno del DB
     */
    private static boolean queryRegistraFarmacista(int id_farmacia, String email, String password) {
        connect();
        String query = "INSERT INTO utenti (username,password,tipo) VALUES (?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            // stmt.setInt(1, id_farmacia);
            /*
             * stmt.setString(2, nome);
             * stmt.setString(3, cognome);
             */
            String valore = String.valueOf(Main.sistema);
            stmt.setString(1, email);
            stmt.setString(2, password);
            stmt.setString(3, valore);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return false;
    }

    /**
     * @param nome     nome impiegato
     * @param cognome  cognome impiegato
     * @param email    mail impiegato
     * @param password password hashata
     * @return true if success, false if error
     * @hidden Registra un impiegato nel db
     */
    private static boolean queryRegistraImpiegato(String email, String password) {
        connect();
        String query = "INSERT INTO utenti (username,password,tipo) VALUES (?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            /*
             * stmt.setString(1, nome);
             * stmt.setString(2, cognome);
             */
            String valore = String.valueOf(Main.sistema);
            stmt.setString(1, email);
            stmt.setString(2, password);
            stmt.setString(3, valore);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return false;
    }

    /**
     * @param nome     nome corriere
     * @param cognome  cognome corriere
     * @param email    mail corriere
     * @param password password hashata
     * @return true o false se la registrazione va a buon fine o meno
     * @hidden Registra un corriere all'interno del db
     */
    private static boolean queryRegistraCorriere(String email, String password) {
        connect();
        String query = "INSERT INTO utenti (username,password,tipo) VALUES (?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            /*
             * stmt.setString(1, nome);
             * stmt.setString(2, cognome);
             */
            String valore = String.valueOf(Main.sistema);
            stmt.setString(1, email);
            stmt.setString(2, password);
            stmt.setString(3, valore);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return false;
    }

    /**
     * @param mail email del farmacista
     * @return true if email in DB, false if not
     * @hidden Verifica l'esistenza della mail di un farmacista all'interno del db
     */
    public static boolean queryVerificaEsistenzaMailFarmacista(String mail) {
        connect();
        String query = "SELECT utenti.username FROM utenti WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, mail);
            var r = stmt.executeQuery();
            if (r.next()) {
                return true;
            }
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return false;
    }

    /**
     * @param mail mail da verificare
     * @return true if mail in db, false if not
     * @hidden Verifica l'esistenza l'esistenza della mail di un impiegato nel db in
     *         fase di registrazione e di recupero credenziali
     */
    private static boolean queryVerificaEsistenzaMailImpiegato(String mail) {
        connect();
        String query = "SELECT utenti.username FROM utenti WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, mail);
            var r = stmt.executeQuery();
            if (r.next()) {
                return true;
            }
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return false;
    }

    /**
     * @param mail mail da verificare
     * @return true if mail in db, false if not
     * @hidden Verifica l'esistenza della mail di un corriere nel db in fase di
     *         registrazione e di recupero credenziali
     */
    private static boolean queryVerificaEsistenzaMailCorriere(String mail) {
        connect();
        String query = "SELECT utenti.username FROM utenti WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, mail);
            var r = stmt.executeQuery();
            if (r.next()) {
                Main.log.debug("Esiste un account Corriere con la mail " + mail + " - " + r.getString(1));
                return true;
            }
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        Main.log.debug("Non esiste un account Corriere con la mail " + mail);
        return false;
    }

    // -- Query Gestione Farmacia

    /**
     * Consente di effettuare il caricamento merci, carica la merce nel DB Farmacie
     * 
     * @param id_lotto    id del lotto
     * @param id_farmacia id della farmacia
     * @param qty         quantita
     */

    public static boolean aggiornaIdFarmacia(int id_farmacia, String email) {
        connect();
        String query = "UPDATE utenti SET idFarmacia= ? WHERE username= ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id_farmacia);
            stmt.setString(2, email);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return false;
    }

    public static Farmacista queryRecuperaIdFarmacia(String email) {
        connect();
        String query = "SELECT utenti.* FROM utenti WHERE username= ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            var r = stmt.executeQuery();
            if (r.next()) {
                //Main.log.info(Farmacista.createFromDB(r).getId_farmacia());
               // Main.log.info(Farmacista.createFromDB(r).getId_farmacista());
                return Farmacista.createFromDB(r);
            }

        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    /*
     * public static void queryCaricaFarmaco(int id_lotto, int id_farmacia, int qty,
     * int id_farmaco) {
     * connect();
     * try (PreparedStatement call = conn.
     * prepareStatement("INSERT INTO Lotto(quantita, id_lotto, id_farmacia, id_farmaco) VALUES (?,?,?,?) ON DUPLICATE KEY UPDATE quantita=quantita+VALUES(quantita)"
     * );
     * PreparedStatement stmt2=conn.
     * prepareStatement("INSERT  INTO Caricamenti(quantita, id_lotto, id_farmacia, data_caricamento) VALUES (?,?,?,?) ON DUPLICATE KEY UPDATE quantita=quantita+VALUES(quantita)"
     * )) {
     * call.setInt(1, qty);
     * stmt2.setInt(1,qty);
     * call.setInt(2, id_lotto);
     * stmt2.setInt(2, id_lotto);
     * call.setInt(3, id_farmacia);
     * stmt2.setInt(3, id_farmacia);
     * call.setInt(4,id_farmaco);
     * stmt2.setDate(4,Date.valueOf(Main.orologio.chiediOrario().toLocalDate()));
     * call.executeUpdate();
     * stmt2.executeUpdate();
     * 
     * } catch (SQLException e) {
     * erroreComunicazioneDBMS(e);
     * }
     * }
     */

    //  
    public static boolean queryCaricaFarmaco(int id_farmaco, String nomefarmaco, int id_farmacia, int qty) {
        connect();
        Main.log.info("dentro querycaricafarmaco prima dell'if");
        if (queryVerificaMagazzino(nomefarmaco, id_farmacia)) {
            Main.log.info("Sono dopo l'if per l'update");
            String query1 = "UPDATE magazzinofarmacia SET quantitafarmaco=quantitafarmaco+? WHERE idfarmacia=? AND nomefarmaco=?";
            try (PreparedStatement stmt = conn.prepareStatement(query1)) {
                stmt.setInt(1, qty);
                stmt.setInt(2, id_farmacia);
                stmt.setString(3, nomefarmaco);
                // Main.log.info("prima di execute");
                stmt.executeUpdate();
                return true;

            } catch (SQLException e) {
                erroreComunicazioneDBMS(e);
                return false;
            }
        } else {
            Main.log.info("Sono in querycaricafarmaco dopo l'if, qui per l'insert, idfarmaco vale= "+id_farmaco);
            String query2 = "INSERT INTO magazzinofarmacia(idfarmaco, idfarmacia, nomefarmaco, quantitafarmaco) VALUES (?, ?, ?, ?)";
            try (PreparedStatement call = conn.prepareStatement(query2)) {
                call.setInt(1, id_farmaco);
                call.setInt(2, id_farmacia);
                call.setString(3, nomefarmaco);
                call.setInt(4, qty);
                call.executeUpdate();
                Main.log.info("Dopo l'insert into magazzinofarmacia");
                return true;

            } catch (SQLException e) {
                erroreComunicazioneDBMS(e);
                return false;
            }
        }
    }

    public static boolean queryErroreConsegna(Ordine ordine, int quantitaNuova, int idfarmacia) {
        connect();
        Main.log.info("Sono in queryerroreconsegna l'ordine è " + ordine);
        String query = "INSERT INTO erroreConsegne(idOrdine, quantitavecchia, quantitanuova, idfarmacia) VALUES (?, ?, ?, ?)";
        try (PreparedStatement call = conn.prepareStatement(query)) {
            call.setInt(1, ordine.getId_ordine());
            call.setInt(2, ordine.getQuantita());
            call.setInt(3, quantitaNuova);
            call.setInt(4, idfarmacia);
            Main.log.info("prima di execute");
            call.executeUpdate();
            return true;

        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
            return false;
        }
    }

    public static boolean queryRecuperaOrdine(String nomefarmaco, int idfarmacia, int idOrdine) {
        connect();
        Main.log.info("sono il query recupera ordine e l'id ordine è " + idOrdine);
        String query = "SELECT * FROM ordini WHERE nomefarmaco= ? AND id=? AND idfarmacia=? limit 1";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nomefarmaco);
            stmt.setInt(2, idOrdine);
            stmt.setInt(3, idfarmacia);
            var r = stmt.executeQuery();
            if (r.next()) {
                return true;
            }

        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return false;
    }

    public static int queryNuovaQuantita(int idordine) {
        connect();
        Main.log.info("Sono nella query nuovaquantita");
        String query = "SELECT quantitaNuova FROM erroreconsegne WHERE idordine=?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idordine);
            var r = stmt.executeQuery();
            Main.log.info("Prima del get int");
            if (r.next()) {
                int qty = r.getInt(1);
                return qty;
            }

        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return 0;
    }

    public static boolean queryVerificaMagazzino(String nomefarmaco, int idfarmacia) {
        connect();
        Main.log.info("In queryverifica magazzino con nomefarmaco= "+nomefarmaco);
        String query = "SELECT * FROM magazzinofarmacia WHERE nomefarmaco= ? AND idfarmacia=?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nomefarmaco);
            stmt.setInt(2, idfarmacia);
            var r = stmt.executeQuery();
            if (!r.isBeforeFirst()) {
                Main.log.info("in query verificamagazzino dentro r.next =false");
                return false;
            } else {
                Main.log.info("in query verificamagazzino dentro r.next =true");
                return true;
            }
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return false;
    }

    /**
     * Carica la quantità consegnata nel DB Azienda
     *
     * @param id_ordine id dell'ordine appena caricato
     * @param id_lotto  id del lotto appena caricato
     * @param qty       quantità caricata
     * @return true se aggiorna almeno un record
     */
    public static boolean aggiornaQuantitaConsegnataOrdine(int id_ordine, int id_lotto, int qty) {
        connect();
        String query = "UPDATE DB_Azienda.ComposizioneOrdine SET ComposizioneOrdine.quantita_consegnata=ComposizioneOrdine.quantita_consegnata + ? WHERE ComposizioneOrdine.id_ordine=? AND ComposizioneOrdine.id_lotto = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, qty);
            stmt.setInt(2, id_ordine);
            stmt.setInt(3, id_lotto);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return false;
    }

    /**
     * Consente di effettuare lo scarico di un farmaco appena venduto
     * All'effettivo, decrementa la quantità del lotto in farmacia.
     * Ritorna la quantità rimasta in magazzino
     *
     * @param id_lotto    id del lotto che viene scaricato
     * @param id_farmacia id della farmacia che effettua lo scarico
     * @param qty         quantità di farmaco che viene scaricato
     * @return quantità di farmaco ancora disponibile in quella farmacia, -1 se ha
     *         errorato
     */
    public static int queryScaricaMerci(String farmaco, int id_farmacia, int qty) {
        connect();
        String query = "UPDATE magazzinoFarmacia SET quantitafarmaco=quantitafarmaco-? WHERE idFarmacia=? AND nomeFarmaco= ? AND quantitafarmaco>=?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, qty);
            stmt.setInt(2, id_farmacia);
            stmt.setString(3, farmaco);
            stmt.setInt(4, qty);
            if (stmt.executeUpdate() <= 0)
                return -1; // Non aggiornato
            /*
             * var squery =
             * "SELECT Lotto.id_Farmaco,sum(quantita) FROM DB_Farmacie.Lotto WHERE Lotto.id_farmaco=(SELECT Lotto.id_farmaco FROM Lotto WHERE Lotto.id_lotto=? ) GROUP BY Lotto.id_farmaco"
             * ;
             * try (PreparedStatement sstmt = conn.prepareStatement(squery)) {
             * sstmt.setString(1, farmaco);
             * var risultato = sstmt.executeQuery();
             * if (risultato.next()) {
             * return risultato.getInt(2);
             * }
             * } catch (SQLException e) {
             * erroreComunicazioneDBMS(e);
             * }
             */
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return -2;
    }

    public static int queryRecuperaFarmacoMagazzino(String nomefarmaco, int idfarmacia) {
        connect();
        String query = "SELECT quantitafarmaco FROM magazzinofarmacia WHERE nomefarmaco= ? AND idfarmacia=? limit 1";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nomefarmaco);
            stmt.setInt(2, idfarmacia);
            var r = stmt.executeQuery();
            if (r.next()) {
                return r.getInt(1);
            }

        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return -1;
    }



    /**
     * Query per chiedere la merce caricata in data odierna per l'avviso delle 20:00
     *
     * @param id_farmacia      id della farmacia da cui parte la richiesta
     * @param data_caricamento {@link LocalDate} data in cui parte la richiesta
     * @return {@link HashMap} che contiene come chiave {@code id_ordine}
     *         ({@link Integer}) e come valore la rispettiva {@code quantita}
     *         ({@link Integer}) caricata
     */
    public static HashMap<Integer, Integer> queryMerceCaricata(int id_farmacia, LocalDate data_caricamento) {
        connect();
        HashMap<Integer, Integer> foo = new HashMap<>();
        String query = "SELECT id_farmaco, sum(Caricamenti.quantita) FROM Caricamenti, Lotto WHERE Caricamenti.data_caricamento=? AND Caricamenti.id_farmacia=? AND Caricamenti.id_lotto=Lotto.id_lotto GROUP BY Lotto.id_farmaco";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDate(1, Date.valueOf(data_caricamento));
            stmt.setInt(2, id_farmacia);
            var r = stmt.executeQuery();
            while (r.next()) {
                foo.put(r.getInt(1), r.getInt(2));
            }
            return foo;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    /**
     * Per ogni farmaco viene recuperata la quantità presente nel magazzino della
     * singola farmacia identificata da {@code id_farmacia}
     *
     * @param listaFarmaci lista di farmaci di cui aggiornare la quantità
     * @param id_farmacia  id della farmacia che fa la richiesta
     */
    public static boolean queryQuantitaFarmaci(Farmaco[] listaFarmaci, int id_farmacia) {
        connect();
        /*
         * for (int index = 0; index < listaFarmaci.length; index++) {
         * Main.log.info("array di lista Farmaci dentro il db "+listaFarmaci[index]);
         * }
         * Main.log.info("dentro dbms queryquantita farmaco");
         * Main.log.info(id_farmacia);
         */
        String query = "select * from magazzinofarmacia where idFarmacia = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id_farmacia);
            var r = stmt.executeQuery();
            HashMap<Integer, Farmaco> listaTemp = new HashMap<>();
            for (Farmaco farmaco : listaFarmaci) {
                listaTemp.put(farmaco.getId_farmaco(), farmaco);
            }
            while (r.next()) {
                int id_farmaco = r.getInt(1);
                // Main.log.info("id dopo la query "+id_farmaco);
                int qta = r.getInt(4);
                // Main.log.info("qta dopo la query " + qta);

                if (listaTemp.containsKey(id_farmaco))
                    listaTemp.get(id_farmaco).riempiQuantita(qta);
            }
            /*
             * listaTemp.entrySet().forEach(entry -> {
             * System.out.println("la llista e' "+entry.getKey() + " " + entry.getValue());
             * });
             */
            return true;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
            return false;
        }
    }

    // Query DB Azienda

    /**
     * Controlla se un farmaco esiste nel db per poterne modificare la produzione
     *
     * @param nome_farmaco nome del farmaco
     * @return id del farmaco corrispondente. -1 se non è stato trovato
     */
    public static String queryControlloEsistenzaFarmaco(String nome_farmaco) {
        // Main.log.info("dentro la query controllo esistenza farmaco");
        connect();
        String query = "SELECT ProduzionePeriodica.nomefarmaco FROM produzioneperiodica WHERE nomefarmaco=?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nome_farmaco);
            var r = stmt.executeQuery();
            if (r.next())
                return r.getString(1);
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    /**
     * Modifica la produzione di un farmaco in Azienda
     *
     * @param id_farmaco id del farmaco di cui modificare la produzione
     * @param quantita   nuova quantita da impostare
     * @return true o false se con successo o meno
     */
    public static boolean queryModificaProduzione(String nomeFarmaco, int quantita) {
        // Main.log.info("dentro la query modifica produzione");
        connect();
        String query = "UPDATE ProduzionePeriodica SET ProduzionePeriodica.quantitaFarmaco=? WHERE ProduzionePeriodica.nomeFarmaco=?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, quantita);
            stmt.setString(2, nomeFarmaco);
            return stmt.executeUpdate() != 0;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return false;
    }

    /**
     * Recupera tutti gli ordini con stato in attesa da parte dell'azienda
     *
     * @return {@link Ordine}[] tutti gli ordini in attesa. {@code null} se non
     *         sono stati trovati risultati o erroreComunicazioneDBMS
     */
    public static Ordine[] queryOrdiniInAttesa() {
        connect();
        String query = "SELECT * FROM ordini WHERE LOWER(stato)='in attesa di disponibilita' order by data";
        ArrayList<Ordine> ordini = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            var r = stmt.executeQuery();
            while (r.next()) {
                ordini.add(Ordine.createFromDB(r));
            }
            return ordini.toArray(new Ordine[0]);
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    /**
     * Aggiorna la composizione degli ordini in seguito a una modifica della
     * quantita dell'ordine
     *
     * @param ordine    ordine da aggiornare
     * @param nuova_qty nuova quantità dell'ordine
     */
    private static void queryAggiornaComposizioneOrdini(Ordine ordine, int nuova_qty) {
        int delta = nuova_qty - ordine.getQuantita();
        if (delta > 0) {
            queryCreaComposizioneOrdini(ordine.nome_farmaco, delta, false, ordine);
            delta = 0;
            return;
        }
        String query = "SELECT ComposizioneOrdine.* FROM ComposizioneOrdine WHERE ComposizioneOrdine.id_ordine=?";
        String query2 = "UPDATE ComposizioneOrdine SET quantita=? WHERE id_ordine=? AND id_lotto=?";
        String query3 = "UPDATE Lotto SET quantita=quantita+? WHERE id_lotto=?";
        try (PreparedStatement stmt = conn.prepareStatement(query);
                PreparedStatement stmt2 = conn.prepareStatement(query2);
                PreparedStatement stmt3 = conn.prepareStatement(query3)) {
            stmt.setInt(1, ordine.getId_ordine());
            ResultSet r = stmt.executeQuery();
            stmt2.setInt(2, ordine.getId_ordine());
            if (delta < 0) {
                while (r.next()) {
                    stmt2.clearParameters();
                    stmt3.clearParameters();
                    stmt3.setInt(2, r.getInt("id_lotto"));
                    stmt2.setInt(2, ordine.getId_ordine());
                    stmt2.setInt(3, r.getInt("id_lotto"));
                    int qty = r.getInt("quantita");
                    Main.log.info("Nuova Iterazione");
                    /* Fa l'update del lotto */
                    if (-delta >= qty) { // -delta=quantita da rimuovere, se è maggiore della quantita di lotto
                                         // nell'ordine, rimuove totalmente il lotto dall'ordine e continua
                        stmt2.setInt(1, 0);
                        delta = delta + qty;
                        stmt2.addBatch(); // aggiunge alla batch la query di update della composizione
                        stmt3.setInt(1, qty);
                        stmt3.addBatch(); // aggiunge alla batch la query di update della quantita del lotto
                        Main.log.info("Delta dopo l'operazione: " + delta + " qty " + qty + " con -delta>=qty");
                    } else { // altrimenti la quantita da rimuovere viene settata a 0 e può uscire dal loop
                        stmt2.setInt(1, qty + delta);
                        stmt2.addBatch(); // aggiunge alla batch la query di update della composizione
                        stmt3.setInt(1, -delta);
                        stmt3.addBatch(); // aggiunge alla batch la query di update della quantita del lotto
                        Main.log.info("Delta dopo l'operazione: " + delta + " qty " + qty + " con -delta<qty");
                        delta = 0;
                        break;
                    }
                }
                stmt2.executeBatch();
                stmt3.executeBatch();
                // conn.commit();
            }

        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
    }

    /**
     * Aggiorna la quantità di un ordine, se la quantità è settata a 0 elimina
     * l'ordine
     *
     * @param ordine {@link Ordine} ordine di cui modificare la quantità
     * @return 1 if success, -1 if error
     */
    public static int queryAggiornaQuantitaOrdine(Ordine ordine, int nuova_qty) {
        connect();
        if (nuova_qty == 0) {
            String query = "DELETE FROM Ordini WHERE id=?";

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                conn.setAutoCommit(false);
                if (ordine.getStato().equalsIgnoreCase("In Lavorazione"))
                    queryAggiornaComposizioneOrdini(ordine, 0);
                stmt.setInt(1, ordine.getId_ordine());
                var r = stmt.executeUpdate();
                if (r != 0) {
                    conn.commit();
                    conn.setAutoCommit(true);
                    return r;
                } else {
                    conn.rollback();
                    conn.setAutoCommit(true);
                }
            } catch (SQLException e) {

                erroreComunicazioneDBMS(e);
            }
            return -1;
        } else {
            // Main.log.info("ID ORNINEEEEEEEE:"+ ordine.getId_ordine());
            String query = "UPDATE Ordini SET quantitaFarmaco=? WHERE id=?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                conn.setAutoCommit(false);
                if (ordine.getStato().equalsIgnoreCase("In Lavorazione"))
                    queryAggiornaComposizioneOrdini(ordine, nuova_qty);
                stmt.setInt(1, nuova_qty);
                stmt.setInt(2, ordine.getId_ordine());
                var r = stmt.executeUpdate();
                if (r != 0) {
                    conn.commit();
                    conn.setAutoCommit(true);
                    return r;
                } else {
                    conn.rollback();
                    conn.setAutoCommit(true);
                }
            } catch (SQLException e) {
                erroreComunicazioneDBMS(e);
            }
            return -1;
        }
    }

    public static int queryAggiornaQuantitaOrdinePeriodico(Ordine ordine, int nuova_qty) {
        connect();
        if (nuova_qty == 0) {
            String query = "DELETE FROM Ordiniperiodici WHERE id=?";

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                conn.setAutoCommit(false);
                if (ordine.getStato().equalsIgnoreCase("In Lavorazione"))
                    queryAggiornaComposizioneOrdini(ordine, 0);
                stmt.setInt(1, ordine.getId_ordine());
                var r = stmt.executeUpdate();
                if (r != 0) {
                    conn.commit();
                    conn.setAutoCommit(true);
                    return r;
                } else {
                    conn.rollback();
                    conn.setAutoCommit(true);
                }
            } catch (SQLException e) {

                erroreComunicazioneDBMS(e);
            }
            return -1;
        } else {
            // Main.log.info("ID ORNINEEEEEEEE:"+ ordine.getId_ordine());
            String query = "UPDATE Ordiniperiodici SET quantitaFarmaco=? WHERE id=?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                conn.setAutoCommit(false);
                if (ordine.getStato().equalsIgnoreCase("In Lavorazione"))
                    queryAggiornaComposizioneOrdini(ordine, nuova_qty);
                stmt.setInt(1, nuova_qty);
                stmt.setInt(2, ordine.getId_ordine());
                Main.log.info("ORIDNE ID DENTRO LA WUERUY " + ordine.getId_ordine());

                var r = stmt.executeUpdate();
                if (r != 0) {
                    Main.log.info("R UGUALE A " + r);

                    conn.commit();
                    conn.setAutoCommit(true);
                    return r;
                } else {
                    conn.rollback();
                    conn.setAutoCommit(true);
                }
            } catch (SQLException e) {
                erroreComunicazioneDBMS(e);
            }
            return -1;
        }
    }

    /**
     * Aggiornare lo stato di un ordine
     *
     * @param ordine ordine da aggiornare, con il nuovo stato
     * @return 1 if update success, -1 if update failed
     */
    public static int queryAggiornaStatoOrdine(Ordine ordine, String stato) {
        connect();
        String query = "UPDATE ordini SET stato=? WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, stato);
            stmt.setInt(2, ordine.getId_ordine());
            var r = stmt.executeUpdate();
            if (r != 0) {
                return 1;
            }
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return -1;
    }

    /**
     * Recupera tutti gli ordini effettuati da tutte le farmacie. Utilizzato
     * esclusivamente dall'Impiegato per effettuare
     * {@link com.ogc.menarini.GestioneAzienda.Control.GestoreVisualizzaOrdiniAzienda
     * VisualizzaOrdiniAzienda}
     *
     * @return {@link Ordine}[] lista contenente tutti gli ordini
     */
    public static Ordine[] queryVisualizzaOrdiniAzienda() {
        connect();
        String query = "SELECT * FROM Ordini ORDER BY data DESC";
        ArrayList<Ordine> ordini = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            var r = stmt.executeQuery();
            while (r.next()) {
                //Main.log.info(r.getDate(6));
                ordini.add(Ordine.createFromDB3(r));                
            }
            
              Iterator<Ordine> i = ordini.iterator();
              while (i.hasNext()) {
              Ordine o = i.next();
              Main.log.info("oridne");
              Main.log.info(o);
              }
            
            return ordini.toArray(new Ordine[0]);
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    /**
     * Recupera tutti i lotti di un relativo farmaco con una data di scadenza
     * successiva alla {@code dataScadenza} (+2 mesi se {@code accettaInScadenza})
     *
     * @param id_farmaco        id del farmaco
     * @param accettaInScadenza se accetta farmaci in scadenza o meno
     * @param dataScadenza      data di scadenza
     * @return {@link Lotto}[] lista di lotti che soddisfano i requisiti
     */
    private static Ordine[] queryLotti(String nomefarmaco, boolean accettaInScadenza, LocalDate dataScadenza) {
        connect();
        String query = "SELECT * FROM magazzinoazienda WHERE nomefarmaco=? AND scadenza>?  ORDER BY scadenza";
        ArrayList<Ordine> lotti = new ArrayList<>();
        if (!accettaInScadenza) {
            dataScadenza = dataScadenza.plusMonths(2);
        }
        Date data = Date.valueOf(dataScadenza);
        // Main.log.debug("querylotti nomefarmaco " + nomefarmaco);
        // Main.log.debug("querylotti data" + data);
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nomefarmaco);
            stmt.setDate(2, data);
            ResultSet r = stmt.executeQuery();
            while (r.next()) {
                // Main.log.info("ORDINE QUESY LOTTI" + new Ordine(r.getString(4), r.getInt(1),
                // r.getInt(2)));
                lotti.add(new Ordine(r.getString(4), r.getInt(1), r.getInt(2)));
            }
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        // Main.log.debug("Trovati " + lotti.size() + " lotti");
        return lotti.toArray(new Ordine[0]);
    }

    // TODO: @Vincenzo scrivi qua la javadoc che non so che scrivere
    private static int queryScegliLotti(Ordine[] lotti, int quantita, ArrayList<Ordine> composizione) {
        connect();
        String query = "UPDATE Ordini SET quantitaFarmaco=quantitaFarmaco-? WHERE id=?";
        ArrayList<Ordine> temp = new ArrayList<>();
        for (Ordine l : lotti) {
            if (l.getQuantita() == 0)
                continue;
            if (quantita > l.getQuantita()) {
                quantita -= l.getQuantita();
                temp.add(new Ordine(l));
                l.setQuantita(0);
            } else {
                Ordine l1 = new Ordine(l);
                l1.setQuantita(quantita);
                temp.add(l1);
                l.setQuantita(l.getQuantita() - quantita);
                quantita = 0;
                break;
            }
        }
        // Main.log.info("WUANTITA IN QUERY SCEGLI LOTTI " + quantita);
        if (quantita > 0) {
            return quantita;
        }
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            for (Ordine l : temp) {
                stmt.setInt(1, l.getQuantita());
                stmt.setInt(2, l.getId_ordine());
                stmt.addBatch();
            }
            stmt.executeBatch();
            composizione.addAll(temp);
            return 0;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return -1;
    }

    /**
     * @param id_farmaco
     * @param quantita
     * @param accettaScadenza
     * @param d
     * @param composizione
     * @return
     */
    private static int creaComposizioneOrdini(String nome_farmaco, int quantita, boolean accettaScadenza, LocalDate d,
            ArrayList<Ordine> composizione) {
        return queryScegliLotti(queryLotti(nome_farmaco, accettaScadenza, d), quantita, composizione);
    }

    /**
     * Transazione Atomica per comporre l'ordine dai lotti disponibili
     *
     * @param id_farmaco      id del farmaco
     * @param quantita        quantità da comporre
     * @param accettaScadenza se accetta farmaci in scadenza
     * @param ordine          ordine da comporre
     * @return Se l'operazione va a buon fine, ritorna {@code 0}.
     *         Se non riesce a soddisfare l'ordine, ritorna un intero rappresentante
     *         la quantità rimanente che non può essere soddisfatta
     */
    private static int queryCreaComposizioneOrdini(String nome_farmaco, int quantita, boolean accettaScadenza,
            Ordine ordine) {
        connect();
        String query = "INSERT INTO ComposizioneOrdine(id_ordine,id_lotto,quantita) VALUES (?,?,?) ON DUPLICATE KEY UPDATE quantita=quantita+VALUES(quantita)";

        ArrayList<Ordine> composizione = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            conn.setAutoCommit(false);
            int quantitaRimanente = creaComposizioneOrdini(
                    nome_farmaco, quantita, accettaScadenza, ordine.getData_consegna(), composizione);
            Main.log.info("QuantitaRimanente nella query " + quantitaRimanente);
            if (quantitaRimanente > 0) {
                conn.rollback();
                conn.setAutoCommit(true);
                return quantitaRimanente;
            }
            for (Ordine l : composizione) {
                stmt.setInt(1, ordine.getId_ordine());
                stmt.setInt(2, l.getId_ordine());
                stmt.setInt(3, l.getQuantita());
                stmt.addBatch();
            }
            stmt.executeBatch();
            conn.commit();
            conn.setAutoCommit(true); // not sure
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
            return -1;
        }
        return 0;
    }

    /**
     * Recupera la quantità disponibile nel magazzino azienda, avendo
     * l'identificativo del farmaco
     *
     * @param id_farmaco identificativo del farmaco
     * @return quantità presente nel magazzino dell'azienda, -1 se qualcosa va male
     */
    public static int queryQuantitaFarmaco(String nomefarmaco) {
        connect();
        String query = "SELECT quantita FROM magazzinoazienda WHERE nomefarmaco=? order by scadenza";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nomefarmaco);
            ResultSet r = stmt.executeQuery();
            if (r.next()) {
                return r.getInt(1);
            }
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return -1;
    }

    /**
     * Prova a creare un ordine, se l'ordine è da contrassegnare in lavorazione e
     * non sono disponibili le quantità richieste
     * l'ordine non viene effettuato e viene ritornata la quantita che non è stato
     * possbile ordinare
     *
     * @param ordine          ordine da creare (o meglio, provarci)
     * @param accettaScadenza se si accettano farmaci in scadenza
     * @return Quantita eccedente
     */

    public static int queryControlloDisponibilita(Ordine ordine, boolean accettaScadenza) {
        connect();
        if (accettaScadenza) {
            String query = "SELECT sum(quantita), nomefarmaco FROM magazzinoazienda where nomefarmaco=? group by nomefarmaco";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, ordine.nome_farmaco);
                ResultSet r = stmt.executeQuery();
                if (r.next()) {
                    if (r.getInt("sum(quantita)") >= ordine.quantita) {
                        return r.getInt("sum(quantita)") - ordine.quantita;
                    }
                    return 0 - r.getInt("sum(quantita)");
                }
            } catch (SQLException e) {
                erroreComunicazioneDBMS(e);
            }
        } else {
            String query = "SELECT sum(quantita), nomefarmaco FROM magazzinoazienda where nomefarmaco=? and scadenza>=? group by nomefarmaco";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                Date date = java.sql.Date.valueOf(Main.orologio.chiediOrario().toLocalDate().plusMonths(2));
                stmt.setString(1, ordine.nome_farmaco);
                stmt.setDate(2, date);
                ResultSet r = stmt.executeQuery();
                if (r.next()) {
                    if (r.getInt("sum(quantita)") >= ordine.quantita) {
                        return r.getInt("sum(quantita)") - ordine.quantita;
                    }
                    return 0 - r.getInt("sum(quantita)");
                }
            } catch (SQLException e) {
                erroreComunicazioneDBMS(e);
            }
        }
        return -1;

    }

    public static int queryCreaOrdine(Ordine ordine, boolean accettaScadenza) {

        // Deve: chiedere i lotti, scegliere quali lotti verranno scelti
        // per quell'ordine, rimuovere i farmaci
        // e caricare ordine e composizione ordini
        // SE la quantità totale è inferiore alla quantità richiesta, ritorna false
        connect();
        String query = "INSERT INTO Ordini(nomefarmaco, idfarmaco, idfarmacia, data, stato, quantitafarmaco) VALUES (?,?,?,?,?,?)";

        try (PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            conn.setAutoCommit(false);
            stmt.setString(1, ordine.getNome_farmaco());
            stmt.setInt(2, ordine.getId_farmaco());
            stmt.setInt(3, ordine.id_farmacia);
            stmt.setDate(4, Date.valueOf(ordine.getData_consegna()));
            stmt.setString(5, ordine.getStato());
            stmt.setInt(6, ordine.getQuantita());
            int r = stmt.executeUpdate();
            if (ordine.getStato().equalsIgnoreCase("In Attesa di disponibilita")) {
                conn.commit();
                conn.setAutoCommit(true);
                Main.log.info("Creato ordine In Attesa");
                return 0;
            }
            ResultSet id_ordine = stmt.getGeneratedKeys();
            if (id_ordine.next()) {
                int quantitaRimanente = queryCreaComposizioneOrdini(ordine.nome_farmaco, ordine.getQuantita(),
                        accettaScadenza, new Ordine(id_ordine.getInt("insert_id"), ordine));
                if (quantitaRimanente == 0) {
                    conn.commit();
                    conn.setAutoCommit(true);
                    Main.log.info("Creato ordine In Lavorazione");
                    return 0;
                } else {
                    conn.rollback();
                    return quantitaRimanente;
                }
            }
            conn.rollback();
            return -1;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
            return -1;
        }
    }

    public static void queryCreaOrdine2(Ordine ordine, boolean accettaScadenza) {

        connect();
        String query = "INSERT INTO Ordini(nomefarmaco, idfarmaco, idfarmacia, data, stato, quantitafarmaco) VALUES (?,?,?,?,?,?)";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, ordine.getNome_farmaco());
            stmt.setInt(2, ordine.getId_farmaco());
            stmt.setInt(3, ordine.id_farmacia);
            stmt.setDate(4, Date.valueOf(ordine.getData_consegna()));
            stmt.setString(5, ordine.getStato());
            stmt.setInt(6, ordine.getQuantita());
            int r = stmt.executeUpdate();
            Main.log.info("ordine creato con successo in creaOrdine");
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
    }

    public static void queryRimuoviScorteMagazzino(Ordine ordine, boolean accettaScadenza) {
        connect();
        Main.log.info("Siamo in query rimuovi scorte magazzino");
        int n = ordine.quantita;
        Date scadenza = java.sql.Date.valueOf(Main.orologio.chiediOrario().toLocalDate());
        Main.log.info("Data se accetti la scadenza " + scadenza);
        if (!accettaScadenza)
            scadenza = java.sql.Date.valueOf(Main.orologio.chiediOrario().toLocalDate().plusMonths(2));
        Main.log.info("Data se non accetti la scadenza " + scadenza);
        while (n > 0) {
            String query = "select * from magazzinoazienda where nomefarmaco=? and scadenza >=? order by scadenza limit 1";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, ordine.getNome_farmaco());
                stmt.setDate(2, scadenza);
                ResultSet r = stmt.executeQuery();
                if (r.next()) {
                    int qta = r.getInt("quantita");

                    if (qta > n) {
                        Main.log.info("scorte in magazzino sufficenti da in un solo lotto");
                        String query2 = "UPDATE magazzinoazienda set quantita=quantita-? where nomefarmaco=? and scadenza >=? order by scadenza limit 1";
                        try (PreparedStatement stmt2 = conn.prepareStatement(query2)) {
                            stmt2.setInt(1, n);
                            stmt2.setString(2, ordine.nome_farmaco);
                            stmt2.setDate(3, scadenza);
                            ResultSet r2 = stmt2.executeQuery();
                        } catch (SQLException e) {
                            erroreComunicazioneDBMS(e);
                        }
                        n = 0;
                        return;
                    } else {
                        Main.log.info("scorte in magazzino NON sufficenti da in un solo lotto");

                        String query3 = "DELETE from magazzinoazienda where nomefarmaco=? and scadenza >=? order by scadenza limit 1";
                        try (PreparedStatement stmt3 = conn.prepareStatement(query3)) {
                            stmt3.setString(1, ordine.nome_farmaco);
                            stmt3.setDate(2, scadenza);
                            ResultSet r2 = stmt3.executeQuery();
                        } catch (SQLException e) {
                            erroreComunicazioneDBMS(e);
                        }
                        n = n - qta;
                        Main.log.info("ho effettuto il delete ora la n vale " + n + " mentre qta vale " + qta);

                    }
                }
                Main.log.info("oridne creato con successo in creaOrdine");
            } catch (SQLException e) {
                erroreComunicazioneDBMS(e);
            }
        }
    }

    /**
     * Crea l'ordine componendo, se necessario, i lotti per l'ordine, settando lo
     * stato passato come argomento
     *
     * @param ordine            ordine da creare
     * @param statoOrdine       stato dell'ordine
     * @param accettaInScadenza se l'ordine può accettare farmaci in scadenza
     * @return
     */
    public static void queryCreaOrdine(Ordine ordine, String statoOrdine, boolean accettaInScadenza) {
        ordine.setStato(statoOrdine);
        queryCreaOrdine2(ordine, accettaInScadenza);
        return;
    }

    public static void queryCreaOrdinePeriodico(Ordine ordine) {

        connect();
        String query = "INSERT INTO OrdiniPeriodici(nomefarmaco, idfarmaco, idfarmacia, data, stato, quantitafarmaco) VALUES (?,?,?,?,?,?)";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, ordine.getNome_farmaco());
            stmt.setInt(2, ordine.getId_farmaco());
            stmt.setInt(3, ordine.id_farmacia);
            stmt.setDate(4, Date.valueOf(ordine.getData_consegna()));
            stmt.setString(5, ordine.getStato());
            stmt.setInt(6, ordine.getQuantita());
            int r = stmt.executeUpdate();
            Main.log.info("ordine periodico creato con successo in creaOrdine");
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
    }

    // Viene richiamata solo se è il primo del mese.
    public static void queryControlloOrdinePeriodico(Ordine ordine) {

        int quantitaRisposta = DBMSDaemon.queryControlloDisponibilita(ordine, true);
        Main.log.info("Sono nel controllo del pimo del mese e stampo la quntita  disponibile dei farmaci : "
                + quantitaRisposta);
        if (quantitaRisposta > 0 && !ordine.stato.equalsIgnoreCase("spedito")) {
            // DBMSDaemon.queryCreaOrdinePeriodico(ordine);
            DBMSDaemon.queryRimuoviScorteMagazzino(ordine, true);
            queryAggiornaStatoPeriodico(ordine.id_ordine, "Spedito");
            Main.log.info("ho rimosso dal magazzino la quantita dell'ordine peridico");
            // Main.log.info("id 2" + ordine.id_ordine);

        }
        return;
    }

    public static void queryAggiornaStatoPeriodico(int id, String stato) {

        connect();
        String query = "UPDATE ordiniperiodici SET stato=? WHERE id=?";
        ArrayList<Ordine> ordini = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, stato);
            stmt.setInt(2, id);
            ResultSet r = stmt.executeQuery();
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);

        }

    }

    public static Ordine[] queryRecuperaOrdiniPeriodici() {

        connect();
        String query = "SELECT * FROM ordiniperiodici";
        ArrayList<Ordine> ordini = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet r = stmt.executeQuery();
            while (r.next()) {
                // Main.log.info("ORDINE QUESY LOTTI" + new Ordine(r.getString(4), r.getInt(1),
                // r.getInt(2)));
                ordini.add(new Ordine(1, r.getString(2), r.getInt(3), r.getInt(5), r.getString(7)));
                // Main.log.info("id 1" + r.getInt(1));

            }
            /*
             * Iterator<Ordine> i = ordini.iterator();
             * while (i.hasNext()) {
             * Ordine o = i.next();
             * Main.log.info("QUERY RECUPERA ORDINI PERIODICI"+o);
             * }
             */
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
            return null;
        }
        return ordini.toArray(new Ordine[0]);
    }

    /**
     * Query che consente a un impiegato di correggere un ordine
     * Nel particolare questo metodo fa:
     * <ol>
     * <li>Modifica lo stato di {@code ordine} in {@code "Corretto"}</li>
     * <li>Modifica la quantità di {@code ordine} in {@code qta_consegnata}</li>
     * <li>Modifica la quantità di ogni lotto che compone {@code ordine},
     * reintegrando la quantità non consegnata</li>
     * <li>Se {@code qty_da_integrare} è maggiore di 0, viene creato un ordine
     * integrativo con suddetta quantità, con consegna prevista per il giorno
     * successivo</li>
     * </ol>
     *
     * @param qty_da_integrare quantità che il farmacista vuole inviata per
     *                         integrare
     * @param qta_consegnata   quantità che il farmacista vuole inviata per
     *                         integrare
     * @param ordine           ordine da correggere
     * @return true if success, false if error
     */
    public static boolean queryCorreggiOrdine(int qty_da_integrare, Ordine ordinevecchio) {
        connect();
        // SUGG: Ma qua posso fare una transazione unica? così da abbassare i tempi
        queryAggiornaStatoOrdine(ordinevecchio, "Corretto");
        // queryAggiornaQuantitaOrdine(ordine, qta_consegnata);
        /*
         * String query = "UPDATE Lotto L " +
         * "SET L.quantita=L.quantita+(SELECT CO.quantita-CO.quantita_consegnata FROM ComposizioneOrdine CO WHERE CO.id_lotto = L.id_lotto AND CO.id_ordine=?) "
         * +
         * "WHERE L.id_lotto IN (SELECT CO.id_lotto FROM ComposizioneOrdine CO WHERE id_ordine = ?)"
         * ;
         * try (PreparedStatement stmt = conn.prepareStatement(query)) {
         * stmt.setInt(1, ordine.getId_ordine());
         * stmt.setInt(2, ordine.getId_ordine());
         * stmt.executeUpdate();
         * } catch (Exception err) {
         * erroreComunicazioneDBMS(err);
         * return false;
         * }
         */
        /* 4, ma senza specificare che è dello stesso lotto... impossibile */
        if (qty_da_integrare > 0) {
            Ordine nuovoOrdine = new Ordine(
                    -1,
                    ordinevecchio.getId_farmaco(), // Lo stesso farmaco
                    ordinevecchio.getNome_farmaco(), // Parametro inutile per creare l'ordine ma a che c'ero...
                    ordinevecchio.getId_farmacia(), // Nella stessa farmacia
                    Main.orologio.chiediOrario().toLocalDate().plusDays(1), // l'ordine integrativo viene consegnato
                                                                            // l'indomani
                    "In Lavorazione",
                    qty_da_integrare);
            queryCreaOrdine(nuovoOrdine, "In Lavorazione", false);
        }
        return true;
    }

    /**
     * Recupera tutti gli ordini effettuati da una farmacia, ordinati per data di
     * consegna
     *
     * @param id_farmacia id della farmacia che fa la richiesta
     * @return {@link Ordine}[] contenente gli ordini effettuati,
     *         {@code null} se non sono stati trovati risultati o errore
     */
    public static Ordine[] queryVisualizzaOrdiniFarmacia(int id_farmacia) {
        connect();
        String query = "SELECT * FROM ordini WHERE idFarmacia= ? ";
        ArrayList<Ordine> ordini = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id_farmacia);
            var r = stmt.executeQuery();
            while (r.next()) {
                ordini.add(Ordine.createFromDB2(r));
            }
            return ordini.toArray(new Ordine[0]);
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    public static Ordine[] queryVisualizzaOrdiniFarmaciaPeriodici(int id_farmacia) {
        connect();
        String query = "SELECT * FROM ordiniperiodici WHERE idFarmacia= ?";
        ArrayList<Ordine> ordini = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id_farmacia);
            var r = stmt.executeQuery();
            while (r.next()) {
                ordini.add(Ordine.createFromDB2(r));

            }
            return ordini.toArray(new Ordine[0]);
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    /**
     * Modifica la data di consegna di un ordine da parte di un farmacista
     *
     * @param ordine     ordine di cui modificare la data di consegna
     * @param nuova_data nuova data di consegna scelta dal farmacista
     * @return 1 if success, -1 if error
     */
    public static int queryAggiornaData(Ordine ordine, LocalDate nuova_data) {
        connect();
        String query = "UPDATE Ordini SET data=? WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDate(1, Date.valueOf(nuova_data));
            stmt.setInt(2, ordine.getId_ordine());
            var r = stmt.executeUpdate();
            if (r != 0)
                return r;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return -1;
    }

    /**
     * Recupera tutti gli ordini periodici di tutte le farmacie di tutte le giornate
     *
     * @return {@link OrdinePeriodico}[] contenente tutti gli ordini periodici.
     *         Ritorna null se ci sono stati errori o non sono stati trovati
     *         risultati.
     */
    public static OrdinePeriodico[] queryOrdiniPeriodici() {
        connect();
        String query = "SELECT OrdinePeriodico.*, F.nome, F2.nome FROM OrdinePeriodico, Farmacia F2 , Farmaco F WHERE F.id_farmaco = OrdinePeriodico.id_farmaco AND OrdinePeriodico.id_farmacia = F2.id_farmacia";
        ArrayList<OrdinePeriodico> ordini = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            var r = stmt.executeQuery();
            while (r.next()) {
                ordini.add(OrdinePeriodico.createFromDB(r));
            }
            Main.log.debug("Trovati " + ordini.size() + " ordini periodici tra tutte le farmacie in tutti i giorni");
            return ordini.toArray(new OrdinePeriodico[0]);
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    /**
     * Recupera tutti gli ordini periodici di una determinata farmacia di tutti i
     * giorni
     *
     * @param id_farmacia id della farmacia da cui parte la richiesta
     * @return {@link OrdinePeriodico}[] gli ordini periodici, {@code null} se non
     *         sono presenti o c'è stato errore
     */
    public static OrdinePeriodico[] queryOrdiniPeriodici(int id_farmacia) {
        connect();
        String query = "SELECT OrdinePeriodico.*, F.nome, F2.nome FROM OrdinePeriodico, Farmacia F2 , Farmaco F WHERE F.id_farmaco = OrdinePeriodico.id_farmaco AND OrdinePeriodico.id_farmacia = F2.id_farmacia AND OrdinePeriodico.id_farmacia=?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id_farmacia);
            var r = stmt.executeQuery();
            ArrayList<OrdinePeriodico> foo = new ArrayList<>();
            while (r.next()) {
                foo.add(OrdinePeriodico.createFromDB(r));
            }
            Main.log.debug("Trovati " + foo.size() + " ordini periodici per la farmacia " + id_farmacia);
            return foo.toArray(new OrdinePeriodico[0]);
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    /**
     * Recupera tutti gli ordini periodici di una determinata farmacia in un
     * determinato giorno
     *
     * @param id_farmacia          id della farmacia da cui parte la richiesta
     * @param giornoDellaSettimana intero rappresentante il giorno della settimana 1
     *                             LUN, ..., 7 DOM
     * @return {@link OrdinePeriodico}[] gli ordini periodici, {@code null} se non
     *         sono presenti o c'è stato errore
     */
    public static OrdinePeriodico[] queryOrdiniPeriodici(int id_farmacia, int giornoDellaSettimana) {
        connect();
        String query = "SELECT OrdinePeriodico.*, F.nome, F2.nome FROM OrdinePeriodico, Farmacia F2 , Farmaco F WHERE F.id_farmaco = OrdinePeriodico.id_farmaco AND OrdinePeriodico.id_farmacia = F2.id_farmacia AND OrdinePeriodico.id_farmacia=? AND OrdinePeriodico.periodicita=?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id_farmacia);
            stmt.setInt(2, giornoDellaSettimana);
            var r = stmt.executeQuery();
            ArrayList<OrdinePeriodico> foo = new ArrayList<>();
            while (r.next()) {
                foo.add(OrdinePeriodico.createFromDB(r));
            }
            Main.log.debug("Trovati " + foo.size() + " ordini periodici per la farmacia " + id_farmacia
                    + " nel giorno della settimana " + giornoDellaSettimana);
            return foo.toArray(new OrdinePeriodico[0]);
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    /**
     * Cerca un farmaco avendo o il nome, o il principio attivo o entrambi
     *
     * @param nome             nome farmaco
     * @param principio_attivo principio attivo farmaco
     * @return {@link Farmaco}[] array di farmaci trovati, null se nessun risultato
     *         oppure è avvenuto erroreComunicazioneDBMS;
     */
    public static Farmaco[] queryCercaFarmaco(String nome, String principio_attivo) {
        connect();
        // Main.log.info("prima del try");

        String query = "SELECT * FROM farmaci WHERE (nome = ? OR principioAttivo = ?) AND dabanco=0";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            // Main.log.info("nel try");

            stmt.setString(1, nome);
            stmt.setString(2, principio_attivo);
            var r = stmt.executeQuery();
            // Main.log.info("query cerca farmaco");
            ArrayList<Farmaco> farmaci = new ArrayList<>();
            while (r.next()) {
                // Main.log.info("array list dei farmaci");
                farmaci.add(Farmaco.createFromDB(r));
                /*
                 * Iterator<Farmaco> i = farmaci.iterator();
                 * while (i.hasNext()) {
                 * Farmaco f = i.next();
                 * Main.log.info("to string :" +f);
                 * Main.log.info("id : "+ f.id_farmaco+" nome f: " + f.nome+ " p_a: "
                 * +f.principio_attivo+" da banco : "+f.da_banco);
                 * }
                 */
            }
            return farmaci.toArray(new Farmaco[0]);
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    public static Farmaco[] queryCercaTuttiFarmaco() {
        connect();
        // Main.log.info("prima del try");

        String query = "SELECT * FROM farmaci WHERE dabanco=0";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            // Main.log.info("nel try");
            var r = stmt.executeQuery();
            // Main.log.info("query cerca farmaco");
            ArrayList<Farmaco> farmaci = new ArrayList<>();
            while (r.next()) {
                // Main.log.info("array list dei farmaci");
                farmaci.add(Farmaco.createFromDB(r));

                Iterator<Farmaco> i = farmaci.iterator();
                while (i.hasNext()) {
                    Farmaco f = i.next();
                    Main.log.info("to string :" + f);
                    Main.log.info("id : " + f.id_farmaco + " nome f: " + f.nome + " p_a: "
                            + f.principio_attivo + " da banco : " + f.da_banco);
                }

            }
            return farmaci.toArray(new Farmaco[0]);
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    public static Farmaco[] queryCercaFarmacoPeriodico(String nome, String principio_attivo) {
        connect();
        // Main.log.info("prima del try");

        String query = "SELECT * FROM farmaci WHERE nome = ? OR principioAttivo = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            // Main.log.info("nel try");

            stmt.setString(1, nome);
            stmt.setString(2, principio_attivo);
            var r = stmt.executeQuery();
            // Main.log.info("query cerca farmaco");
            ArrayList<Farmaco> farmaci = new ArrayList<>();
            while (r.next()) {
                // Main.log.info("array list dei farmaci");
                farmaci.add(Farmaco.createFromDB(r));
                /*
                 * Iterator<Farmaco> i = farmaci.iterator();
                 * while (i.hasNext()) {
                 * Farmaco f = i.next();
                 * Main.log.info("to string :" +f);
                 * Main.log.info("id : "+ f.id_farmaco+" nome f: " + f.nome+ " p_a: "
                 * +f.principio_attivo+" da banco : "+f.da_banco);
                 * }
                 */
            }
            return farmaci.toArray(new Farmaco[0]);
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    public static Farmaco[] queryCercaTuttiFarmacoPeriodico() {
        connect();
        // Main.log.info("prima del try");

        String query = "SELECT * FROM farmaci ";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            // Main.log.info("nel try");

            var r = stmt.executeQuery();
            // Main.log.info("query cerca farmaco");
            ArrayList<Farmaco> farmaci = new ArrayList<>();
            while (r.next()) {
                // Main.log.info("array list dei farmaci");
                farmaci.add(Farmaco.createFromDB(r));
                /*
                 * Iterator<Farmaco> i = farmaci.iterator();
                 * while (i.hasNext()) {
                 * Farmaco f = i.next();
                 * Main.log.info("to string :" +f);
                 * Main.log.info("id : "+ f.id_farmaco+" nome f: " + f.nome+ " p_a: "
                 * +f.principio_attivo+" da banco : "+f.da_banco);
                 * }
                 */
            }
            return farmaci.toArray(new Farmaco[0]);
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    /**
     * query per modificare la quantità di un ordine periodico da parte di un
     * farmacista
     *
     * @param ordine    ordine periodico da modificare
     * @param nuova_qty nuova quantità da settare
     * @return 1 if success, -1 if error
     */
    public static int queryModificaOrdinePeriodico(Ordine ordine, int nuova_qty) {
        connect();
        String query = "UPDATE OrdinePeriodico SET OrdinePeriodico.quantita=? WHERE OrdinePeriodico.id_farmacia=? AND OrdinePeriodico.id_farmaco=?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, nuova_qty);
            stmt.setInt(2, ordine.getId_farmacia());
            stmt.setInt(3, ordine.getId_farmaco());
            var r = stmt.executeUpdate();
            if (r != 0)
                return r;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return -1;
    }

    /**
     * Recupera le consegne da effettuare {@code today} da parte del corriere
     * Utilizzato in {@link GestoreVisualizzaConsegne Corriere - Visualizza
     * Consegne}
     *
     * @param data {@link LocalDate} data odierna
     * @return {@link Collo}[] tutti i colli, consegnati e non, di {@code today}
     */
    public static Ordine[] queryVisualizzaConsegne(LocalDate data) {
        connect();
        Main.log.info(Date.valueOf(data));
        ArrayList<Ordine> info = new ArrayList<>();

        String query1 = "SELECT * FROM ordini WHERE data=?";
        String query2 = "SELECT nomeFarmacia, indirizzo FROM farmacie where idfarmacia=?";
        try (PreparedStatement stmt = conn.prepareStatement(query1)) {
            stmt.setDate(1,Date.valueOf(data));
            var r = stmt.executeQuery();

            ArrayList<Ordine> ordini = new ArrayList<>();
            while (r.next()) {
                // Main.log.info("array list dei farmaci");
                ordini.add(Ordine.createFromDB6(r));
                Main.log.info("sixe"+ordini.size());
                Main.log.info(Ordine.createFromDB6(r));
            }

            // int val =((Number) r.getObject(1)).intValue();
            // int val = r.getInt(4);

            for (int i = 0; i < ordini.size(); i++) {
                Main.log.info("inizio for ");
                Ordine ordine = ordini.get(i);
                int idfarmacia = ordine.getId_farmacia();
                String firma = ordine.getFirma();
                int id_ordine = ordine.getId_ordine();
                PreparedStatement stmt2 = conn.prepareStatement(query2);
                /*
                 * Ordine c = Ordine.createFromDB(r);
                 * info.add(c);
                 * info.add(Ordine.createFromDB5(r));
                 */
                stmt2.setInt(1, idfarmacia);
                Main.log.info("id farmacia = " +idfarmacia);
                var r2 = stmt2.executeQuery();
                while (r2.next()) {
                    info.add(Ordine.createFromDB4(r2, firma, id_ordine));
                    Iterator<Ordine> it = info.iterator();
                    while (it.hasNext()) {
                        Ordine o = it.next();
                        Main.log.info("create dentro il dbmsDemon : " + o);
                    }
                }
            }
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
            return null;
        }
        return info.toArray(new Ordine[0]);
    }

    /**
     * Firma un collo e aggiorna anche i relativi ordini contrassegnandoli con lo
     * stato "Consegnato"
     *
     * @param firma concatenazione di nome e cognome del firmante
     * @param collo collo da firmare
     * @return true if success, false if error
     */
    public static int queryFirmaCollo(Ordine ordine) {
        connect();
        String query = "UPDATE ordini SET firma=?, stato=? WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, "Firmato");
            stmt.setString(2, "Consegnato");
            stmt.setInt(3, ordine.getId_ordine());
            return stmt.executeUpdate() != 0 ? 1 : -1;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return -1;
    }

    /**
     * Aggiorna le scorte di un determinato giorno della settimana, nell'effettivo
     * quindi viene utilizzato per effettuare produzione periodica
     *
     * @param today {@link LocalDate}, serve per il giorno di produzione (1 LUN,
     *              ..., 7 DOM) e per capire quando scadrà il lotto prodotto
     */
    public static void queryAggiornaScorte(LocalDate today) {
        connect();
        String query_get_produzioni = "SELECT * FROM ProduzionePeriodica WHERE giornoproduzione = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query_get_produzioni)) {
            stmt.setInt(1, today.getDayOfWeek().getValue());
            Main.log.info("CERCO DI AGGIONARE IL MAGAZZINO INVIO LA DATA: " + today.getDayOfWeek().getValue());
            var produzioni = stmt.executeQuery();
            String query_insert = "INSERT INTO magazzinoAzienda (nomefarmaco, quantita, scadenza) VALUES (?, ?, ?)";
            var updateStmt = conn.prepareStatement(query_insert);
            while (produzioni.next()) {
                String nomeFarmaco = produzioni.getString(1);
                Main.log.info("NOME " + nomeFarmaco);
                int quantita = produzioni.getInt(2);
                Main.log.info("QUANTITA " + quantita);
                int scade_dopo_tot_giorni = 50;
                LocalDate scadenza = today.plusDays(scade_dopo_tot_giorni);
                Main.log.info("DATA AGGIORNATA SCADENZA : " + scadenza);

                updateStmt.setString(1, nomeFarmaco);
                updateStmt.setInt(2, quantita);
                updateStmt.setDate(3, Date.valueOf(scadenza));
                updateStmt.addBatch();
            }
            updateStmt.executeBatch();
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }

    }

    /**
     * Aggiorna la quantita di un ordine periodico
     *
     * @param quantita        nuova quantita
     * @param ordinePeriodico ordine periodico da modificare
     * @return 1 if success, -1 if error
     */
    public static int queryAggiornaOrdinePeriodico(int quantita, OrdinePeriodico ordinePeriodico) {
        connect();
        String query = "UPDATE OrdinePeriodico SET OrdinePeriodico.quantita=? WHERE OrdinePeriodico.id_farmacia=? AND OrdinePeriodico.id_farmaco=?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, quantita);
            stmt.setInt(2, ordinePeriodico.getId_farmacia());
            stmt.setInt(3, ordinePeriodico.getId_farmaco());
            return stmt.executeUpdate() != 0 ? 1 : -1;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return -1;
    }

    /**
     * Recupera tutti gli ordini di una farmacia consegnati in una particolare data
     *
     * @param id_farmacia id della farmacia che fa la richiesta
     * @param data        data di consegna
     * @return {@link Ordine}[]
     */
    public static Ordine[] queryOrdini(int id_farmacia, LocalDate data) {
        connect();
        String query = "SELECT Ordine.*, F.nome FROM Ordine INNER JOIN Farmaco F on Ordine.id_farmaco = F.id_farmaco WHERE Ordine.id_farmacia=? AND Ordine.data_consegna=?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id_farmacia);
            stmt.setDate(2, Date.valueOf(data));
            var r = stmt.executeQuery();
            ArrayList<Ordine> foo = new ArrayList<>();
            while (r.next()) {
                foo.add(Ordine.createFromDB(r));
            }
            return foo.toArray(new Ordine[0]);
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    /**
     * Crea gli ordini relativi alle ordinazioni periodiche di una farmacia
     *
     * @param ordini Ordini Periodici da evadere
     * @return se la funzione va a buon fine o meno (tecnicamente non dovrebbe mai
     *         andare male)
     */
    public static boolean queryCreaOrdini(OrdinePeriodico[] ordini) {
        LocalDate data = Main.orologio.chiediOrario().toLocalDate().plusDays(1);
        connect();
        try {
            conn.setAutoCommit(false);
            for (OrdinePeriodico op : ordini) {
                if (Main.orologio.chiediOrario().toLocalDate().getDayOfWeek().getValue() == op.getPeriodicita()) {
                    Ordine o = new Ordine(-1, op.getId_farmaco(), op.getNomeFarmaco(), op.getId_farmacia(), data,
                            "In Lavorazione", op.getQuantita());
                    DBMSDaemon.queryCreaOrdine(o, false);
                }
            }
            conn.commit();
            conn.setAutoCommit(true);
            return true;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
            return false;
        }
    }

    /**
     * Recupera la quantità effettivamente consegnata di un ordine
     *
     * @param ordine Ordine di cui si vuole recuperare la quantità consegnata
     * @return la quantità consegnata
     */
    public static int queryControllaQuantita(Ordine ordine) {
        String query = "SELECT CO.id_ordine, SUM(CO.quantita_consegnata) FROM ComposizioneOrdine CO WHERE CO.id_ordine=? GROUP BY CO.id_ordine";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, ordine.getId_ordine());
            var r = stmt.executeQuery();
            if (r.next()) {
                return r.getInt(2);
            }
        } catch (Exception e) {
            erroreComunicazioneDBMS(e);
        }
        return -1;
    }

    /**
     * Query utilizzata per poter {@link PDFCreator#creaPDF(Collo) stampare la
     * ricevuta} del Collo avendo un ordine
     *
     * @param ordine ordine di cui recuperare il collo associato
     * @return Collo a cui appartiene l'ordine consegnato
     */

}
