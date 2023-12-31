package com.ogc.menarini.GestioneAzienda.Control;

import com.ogc.menarini.Entity.Ordine;
import com.ogc.menarini.Entity.OrdinePeriodico;
import com.ogc.menarini.Main;
import com.ogc.menarini.Utils.DBMSDaemon;
import com.ogc.menarini.Utils.Utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class GestoreDiSistemaImpiegato {
    private int giornoUltimaChiamata = 0;

    public void chiediData() {
        int h = Main.orologio.chiediOrario().getHour();
        //if (h == 20 && giornoUltimaChiamata != Main.orologio.chiediOrario().getDayOfWeek().getValue()) {
            //giornoUltimaChiamata = Main.orologio.chiediOrario().getDayOfWeek().getValue();
            /* Main.log.info("gUC " +giornoUltimaChiamata);
            Main.log.info("local date " + Main.orologio.chiediOrario().toLocalDate());
            Main.log.info("DENTRO CHEIDI DATA PROSSIMO AD AGGIORNA "); */

            //serializza();
            aggiornaScorte();
            //evadiOrdiniInAttesa();
        //}
    }

    private void aggiornaScorte() {
        Main.log.info("DENTRO AGGIONA SCORTE");
        DBMSDaemon.queryAggiornaScorte(Main.orologio.chiediOrario().toLocalDate());
        Utils.creaPannelloConferma("Scorte Aggiornate Correttamente");
    }

    public void serializza() {
        try {
            FileOutputStream fout = new FileOutputStream("gds.time");
            ObjectOutputStream out = new ObjectOutputStream(fout);
            out.writeObject(this);
        } catch (FileNotFoundException e) {
            System.out.println("File non esistente");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void evadiOrdiniInAttesa() {
        OrdinePeriodico[] ordiniPeriodici = DBMSDaemon.queryOrdiniPeriodici();
        Ordine[] ordiniInAttesa = DBMSDaemon.queryOrdiniInAttesa();
        for (Ordine o : ordiniInAttesa) {
            evadiOrdineInAttesa(o, ordiniPeriodici);
        }
    }

    private void evadiOrdineInAttesa(Ordine o, OrdinePeriodico[] ordiniPeriodici) {
        int totaleFarmaco = DBMSDaemon.queryQuantitaFarmaco(o.getNome_farmaco());
        Main.log.info("Totale Farmaco: "+totaleFarmaco);
        for (OrdinePeriodico op : ordiniPeriodici) {
            if (op.getId_farmaco() == o.getId_farmaco()) {
                totaleFarmaco -= op.getQuantita();
            }
        }
        if (totaleFarmaco > 0) {
            DBMSDaemon.queryAggiornaData(o, Main.orologio.chiediOrario().toLocalDate().plusDays(2));
            int qty = Math.min(o.getQuantita(), totaleFarmaco);
            DBMSDaemon.queryAggiornaQuantitaOrdine(o, qty);
            o.setStato("In Lavorazione");
            DBMSDaemon.queryAggiornaStatoOrdine(o, "ciao");
            //Ordine temp=new Ordine(-1,o.getId_farmaco(),o.getNome_farmaco(),o.getId_farmacia(),Main.orologio.chiediOrario().toLocalDate().plusDays(2),"In Lavorazione",qty);
            //DBMSDaemon.queryCreaOrdine(temp,"In Lavorazione",false);
            if (o.getQuantita() > totaleFarmaco) {
                Ordine temp2 = new Ordine(-1, o.getId_farmaco(), o.getNome_farmaco(), o.getId_farmacia(), o.getData_consegna(), "In Attesa Di Disponibilita", o.getQuantita() - qty);
                DBMSDaemon.queryCreaOrdine(temp2, "In Attesa Di Disponibilita", false);
            }
        }
    }
}
