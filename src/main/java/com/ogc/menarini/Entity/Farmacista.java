package com.ogc.menarini.Entity;

import com.ogc.menarini.Main;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Farmacista extends Utente {
    private final int id_farmacista;
    private final int id_farmacia;

    public Farmacista(int id_farmacista, int id_farmacia, String nome, String cognome, String email, String password) {
        super(email, password);
        this.id_farmacia = id_farmacia;
        this.id_farmacista = id_farmacista;
    }

    public Farmacista(int id_farmacista, int id_farmacia, String nome, String cognome, String email) {
        super( email);
        this.id_farmacia = id_farmacia;
        this.id_farmacista = id_farmacista;
    }
    public Farmacista(int id_farmacista, String email, String password, int id_farmacia) {
        super(email, password);
        this.id_farmacia = id_farmacia;
        this.id_farmacista = id_farmacista;
    }

    public int getId_farmacista() {
        return id_farmacista;
    }

    public int getId_farmacia() {
        return id_farmacia;
    }

    /**
     * Converte i risultati di una query
     * {@code SELECT Farmacista.*}
     *
     * @param row risultati della query
     * @return Farmacista corrispondente
     * @throws SQLException se la row non proviene da una select come specificata in precedenza
     */
    public static Farmacista createFromDB(ResultSet row) {
        try {
            return new Farmacista(
                    row.getInt(1),
                    row.getString(2),
                    row.getString(3),
                    row.getInt(5)
            );
        } catch (SQLException e) {
            Main.log.error("Errore durante conversione DB->Utente", e);
        }
        return null;
    }
}
