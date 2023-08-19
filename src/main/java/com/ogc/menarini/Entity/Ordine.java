package com.ogc.menarini.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import com.ogc.menarini.Main;

public class Ordine {
    public int id_ordine;
    public int id_farmaco;
    public String nome_farmaco;
    public int id_farmacia;
    public LocalDate data_consegna;
    public String stato;
    public String nomeFarmacia;
    public int quantita;
    public String firma;
    public String indirizzo;

    public Ordine(int id_ordine, int id_farmaco, String nome_farmaco, int id_farmacia, LocalDate data_consegna,
            String stato, int quantita) {
        this.id_ordine = id_ordine;
        this.id_farmaco = id_farmaco;
        this.nome_farmaco = nome_farmaco;
        this.id_farmacia = id_farmacia;
        this.data_consegna = data_consegna;
        this.stato = stato;
        this.quantita = quantita;
    }

    public Ordine(int id_ordine, Ordine ordine) {
        this.id_ordine = id_ordine;
        this.id_farmaco = ordine.getId_farmaco();
        this.nome_farmaco = ordine.getNome_farmaco();
        this.id_farmacia = ordine.getId_farmacia();
        this.data_consegna = ordine.getData_consegna();
        this.quantita = ordine.getQuantita();
        this.stato = ordine.getStato();
        this.nomeFarmacia = ordine.getNomeFarmacia();
    }

    public Ordine(Ordine ordine) {
        this.id_ordine = ordine.getId_ordine();
        this.id_farmaco = ordine.getId_farmaco();
        this.nome_farmaco = ordine.getNome_farmaco();
        this.id_farmacia = ordine.getId_farmacia();
        this.data_consegna = ordine.getData_consegna();
        this.quantita = ordine.getQuantita();
        this.stato = ordine.getStato();
        this.nomeFarmacia = ordine.getNomeFarmacia();
    }

    //   Ordine

    public Ordine(int id_ordine, String nome_farmaco, int quantita, LocalDate data_consegna, String stato) {
        this.id_ordine = id_ordine;
        this.nome_farmaco = nome_farmaco;
        this.data_consegna = data_consegna;
        this.stato = stato;
        this.quantita = quantita;
    }

    public Ordine(int id,String nome, int id_farmaco, int quantita, String stato) {
        this.id_farmaco = id_farmaco;
        this.nome_farmaco = nome;        
        this.stato = stato;
        this.quantita = quantita;
        this.id_ordine = id;
    }

    //  
    public Ordine(String nome_farmaco, int id_farmacia, int quantita, LocalDate data_consegna, String stato) {
        this.data_consegna = data_consegna;
        this.id_farmacia = id_farmacia;
        this.nome_farmaco = nome_farmaco;
        this.stato = stato;
        this.quantita = quantita;
    }
    
    public Ordine(String nome_farmaco, int id_farmacia,int id_farmaco, int quantita, LocalDate data_consegna, String stato) {
        this.data_consegna = data_consegna;
        this.id_farmacia = id_farmacia;
        this.id_farmaco=id_farmaco;
        this.nome_farmaco = nome_farmaco;
        this.stato = stato;
        this.quantita = quantita;
    }
    
    public Ordine(int id_ordine, int id_farmaco,String nome_farmaco, int id_farmacia, LocalDate data_consegna,   String stato,
            int quantita, String firma) {
        this.data_consegna = data_consegna;
        this.id_farmacia = id_farmacia;
        this.id_farmaco = id_farmaco;
        this.nome_farmaco = nome_farmaco;
        this.stato = stato;
        this.quantita = quantita;
        this.firma = firma;
        this.id_ordine = id_ordine;

    }

    public Ordine(String nome_farmaco, int id_farmaco, int quantita) {
        this.id_farmaco = id_farmaco;
        this.nome_farmaco = nome_farmaco;
        this.quantita = quantita;
    }

    public Ordine(String firma) {

        this.firma = firma;
    }

    public Ordine(int id_ordine, String nome_farmacia, String indirizzo, String firma) {
        this.id_ordine=id_ordine;
        this.nomeFarmacia = nome_farmacia;
        this.indirizzo = indirizzo;
        this.firma = firma;
    }

    public Ordine(String nome_farmacia, String indirizzo, String firma) {
        this.nomeFarmacia = nome_farmacia;
        this.indirizzo = indirizzo;
        this.firma = firma;
    }

    public int getQuantita() {
        return quantita;
    }

    public int getId_ordine() {
        return id_ordine;
    }

    public int getId_farmaco() {
        return id_farmaco;
    }

    public String getNome_farmaco() {
        return nome_farmaco;
    }

    public int getId_farmacia() {
        return id_farmacia;
    }

    public LocalDate getData_consegna() {
        return data_consegna;
    }

    public void setData_consegna(LocalDate data_consegna) {
        this.data_consegna = data_consegna;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }    
    
    public String getNomeFarmacia() {
        return nomeFarmacia;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public String getFirma() {
        return firma;
    }

    public void setNomeFarmacia(String nomeFarmacia) {
        this.nomeFarmacia = nomeFarmacia;
    }

    /**
     * Converte i risultati di una query
     * {@code SELECT Ordine.*, Farmaco.nome}
     *
     * @param r risultati della query
     * @return Ordine corrispondente
     * @throws SQLException se la row non proviene da una select come specificata in
     *                      precedenza
     */
    public static Ordine createFromDB(ResultSet r) throws SQLException {
        int id_ordine = r.getInt(1);
        int id_farmaco = r.getInt(2);
        int id_farmacia = r.getInt(3);
        LocalDate data_consegna = r.getDate(4).toLocalDate();
        int quantita = r.getInt(5);
        String stato = r.getString(6);
        String nome_farmaco = r.getString(7);
        Ordine ord = new Ordine(id_ordine, id_farmaco, nome_farmaco, id_farmacia, data_consegna, stato, quantita);
        return ord;
    }

    public static Ordine createFromDB6(ResultSet r) throws SQLException {
        int id_ordine = r.getInt(1);
        int id_farmaco = r.getInt(3);
        int id_farmacia = r.getInt(4);
        LocalDate data_consegna = r.getDate(6).toLocalDate();
        int quantita = r.getInt(5);
        String stato = r.getString(7);
        String nome_farmaco = r.getString(2);
        String firma = r.getString(8);
        Ordine ord = new Ordine(id_ordine, id_farmaco, nome_farmaco, id_farmacia, data_consegna, stato, quantita,firma);
        return ord;
    }

    public static Ordine createFromDB3(ResultSet r) throws SQLException {
        int id_ordine = r.getInt(1);
        int id_farmaco = r.getInt(3);
        int id_farmacia = r.getInt(4);
        LocalDate data_consegna = r.getDate(6).toLocalDate();
        int quantita = r.getInt(5);
        String stato = r.getString(7);
        String nome_farmaco = r.getString(2);
        Ordine ord = new Ordine(id_ordine, id_farmaco, nome_farmaco, id_farmacia, data_consegna, stato, quantita);
        return ord;
    }

    public static Ordine createFromDB(ResultSet r, String nomeFarmacia) throws SQLException {
        int id_ordine = r.getInt(1);
        int id_farmaco = r.getInt(2);
        int id_farmacia = r.getInt(3);
        LocalDate data_consegna = r.getDate(4).toLocalDate();
        int quantita = r.getInt(5);
        String stato = r.getString(6);
        String nome_farmaco = r.getString(7);
        Ordine ord = new Ordine(id_ordine, id_farmaco, nome_farmaco, id_farmacia, data_consegna, stato, quantita);
        ord.setNomeFarmacia("cacca");
        return ord;
    }

    //   create
    public static Ordine createFromDB2(ResultSet r) throws SQLException {

        int id_ordine = r.getInt(1);
        int id_farmaco = r.getInt(3);
        int id_farmacia = r.getInt(4);
        LocalDate data_consegna = r.getDate(6).toLocalDate();
        int quantita = r.getInt(5);
        String stato = r.getString(7);
        String nome_farmaco = r.getString(2);
        Ordine ord = new Ordine(id_ordine,id_farmaco, nome_farmaco, id_farmacia,  data_consegna, stato,quantita);
        return ord;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return " id: " + id_ordine + " id farmacia: " + id_farmacia + " nome: " + nome_farmaco + "  qta: " + quantita
                + " stato : " + stato + " nomeFarmacia : " + nomeFarmacia + " indirizzo : " + indirizzo
                + " firma : " + firma;
    }

    public static Ordine createFromDB4(ResultSet r, String firma, int id_ordine) throws SQLException {
        Main.log.info("entro in create");
        String nome_farmacia = r.getString(1);
        String indirizzo = r.getString(2);        
        Ordine ord = new Ordine(id_ordine, nome_farmacia, indirizzo, firma);
        Main.log.info("create from DB4 " + ord);
        return ord;
    }

    public static Ordine createFromDB5(ResultSet r) throws SQLException {

        String stringa = r.getString(4);
        Ordine ord = new Ordine(stringa);
        return ord;
    }
}
