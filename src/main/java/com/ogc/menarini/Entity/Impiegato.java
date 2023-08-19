package com.ogc.menarini.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Impiegato extends Utente {

    private final int id_impiegato;

    public Impiegato(int id_impiegato,String email, String password) {
        super(email, password);
        this.id_impiegato = id_impiegato;
    }

    public int getId_impiegato() {
        return id_impiegato;
    }

    /**
     * Converte i risultati di una query
     * {@code SELECT Impiegato.*}
     *
     * @param row risultati della query
     * @return Impiegato corrispondente
     * @throws SQLException se la row non proviene da una select come specificata in precedenza
     */
    public static Impiegato createFromDB(ResultSet row) throws SQLException {
        return new Impiegato(
                row.getInt(1),
                row.getString(2),
                row.getString(3)
            );
    }
}
