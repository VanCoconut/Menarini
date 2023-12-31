package com.ogc.menarini.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Farmaco {
    public  int id_farmaco;
    public  String nome, scadenza;
    public  String principio_attivo;
    public  int da_banco;
    public int quantitaFarmaci;
    public  boolean da_banco2;

    public Farmaco(int id_farmaco, String nome, String principio_attivo, boolean da_banco2, int quantitaFarmaci) {
        this.id_farmaco = id_farmaco;
        this.nome = nome;
        this.principio_attivo = principio_attivo;
        this.da_banco2 = da_banco2;
        this.quantitaFarmaci = quantitaFarmaci;

    }

    public Farmaco(int id_farmaco, String nome, String principio_attivo, int da_banco) {
        this.id_farmaco = id_farmaco;
        this.nome = nome;
        this.principio_attivo = principio_attivo;
        this.da_banco = da_banco;
        this.quantitaFarmaci = 0;
    }

// 
    public Farmaco(String nome, String principio_attivo) {        
        this.nome = nome;
        this.principio_attivo = principio_attivo;        
        this.quantitaFarmaci = 0;
    }

    public Farmaco(int id_farmaco, String nome, int da_banco, String principio_attivo, String scadenza) {
        this.id_farmaco = id_farmaco;
        this.nome = nome;
        this.principio_attivo = principio_attivo;
        this.da_banco = da_banco;
        this.scadenza=scadenza;
    }

    public void riempiQuantita(int qta) {
        this.quantitaFarmaci = qta;
    }

    public int getId_farmaco() {
        return id_farmaco;
    }

    public String getNome() {
        return nome;
    }

    public String getPrincipio_attivo() {
        return principio_attivo;
    }

    public boolean isDa_banco() {
        return da_banco2;
    }

    public int getQuantitaFarmaci() {
        return quantitaFarmaci;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Farmaco) obj;
        return this.id_farmaco == that.id_farmaco;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_farmaco, nome, principio_attivo, da_banco, quantitaFarmaci);
    }

    @Override
    public String toString() {
        return "Farmaco[" + "id_farmaco=" + id_farmaco + ", " + "nome=" + nome + ", " + "principio_attivo=" + principio_attivo + ", " + "da_banco=" + da_banco + ", " + "quantitaFarmaci=" + quantitaFarmaci + ']';
    }

    /**
     * Converte i risultati di una query
     * {@code SELECT Farmaco.*}
     *
     * @param row risultati della query
     * @return Farmaco corrispondente
     * @throws SQLException se la row non proviene da una select come specificata in precedenza
     */
     /* public static Farmaco createFromDB(ResultSet row) throws SQLException {
        int id_farmaco = row.getInt(1);
        String nome = row.getString(2);
        String principio = row.getString(3);
        boolean da_banco = row.getBoolean(4);
        return new Farmaco(id_farmaco, nome, principio, da_banco);
    }  */
    public static Farmaco createFromDB(ResultSet row) throws SQLException {
        int id_farmaco = row.getInt(1);
        String nome = row.getString(2);
        String principio_attivo = row.getString(4);
        int da_banco = row.getInt(3);        
        String scadenza = row.getString(5);
        return new Farmaco(id_farmaco, nome, principio_attivo, da_banco);
    }
    
    public static Farmaco createFromDB2(ResultSet row) throws SQLException {
        int id_farmaco = row.getInt(1);
        String nome = row.getString(2);
        String principio_attivo = row.getString(4);
        return new Farmaco(nome, principio_attivo);
    }

}
