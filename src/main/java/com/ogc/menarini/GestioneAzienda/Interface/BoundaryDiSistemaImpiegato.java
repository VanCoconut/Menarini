package com.ogc.menarini.GestioneAzienda.Interface;

import com.ogc.menarini.GestioneAzienda.Control.GestoreDiSistemaImpiegato;
import com.ogc.menarini.Main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class BoundaryDiSistemaImpiegato {
    GestoreDiSistemaImpiegato gI = new GestoreDiSistemaImpiegato();

    public BoundaryDiSistemaImpiegato() {
        try (ObjectInputStream o = new ObjectInputStream(new FileInputStream("gds.time"))) {
            gI = (GestoreDiSistemaImpiegato) o.readObject();
        } catch (FileNotFoundException e) {
//            System.out.println("Gestore da creare");
        } catch (IOException e) {
//            System.err.println("Errore deserializzazione");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void chiediData() {
        gI.chiediData();
    }
}
