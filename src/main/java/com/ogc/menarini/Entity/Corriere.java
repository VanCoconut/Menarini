package com.ogc.menarini.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Corriere extends Utente {

    private final int id_corriere;

    public Corriere(int id_corriere,  String email, String password) {
        super(email, password);
        this.id_corriere = id_corriere;
    }

    public int getId_corriere() {
        return id_corriere;
    }

    /**
     * Converte i risultati di una query
     * {@code SELECT Corriere.*}
     *
     * @param row risultati della query
     * @return Corriere corrispondente
     * @throws SQLException se la row non proviene da una select come specificata in precedenza
     */
    public static Corriere createFromDB(ResultSet row) throws SQLException {
        return new Corriere(
                row.getInt(1),
                row.getString(2),
                row.getString(3)
        ) ;
    }
}
