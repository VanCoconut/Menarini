package com.ogc.menarini.GestioneFarmaci.Interface;

import com.ogc.menarini.GestioneFarmaci.Control.GestoreDiSistemaFarmacista;
import com.ogc.menarini.GestioneAzienda.Control.GestoreDiSistemaImpiegato;
import com.ogc.menarini.Main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class BoundaryDiSistemaFarmacista {
    GestoreDiSistemaFarmacista gF = new GestoreDiSistemaFarmacista();
    GestoreDiSistemaImpiegato gI = new GestoreDiSistemaImpiegato();

    public BoundaryDiSistemaFarmacista() {
        try (ObjectInputStream o = new ObjectInputStream(new FileInputStream("gds.time"))) {
                gF = (GestoreDiSistemaFarmacista) o.readObject();
        } catch (FileNotFoundException e) {
//            System.out.println("Gestore da creare");
        } catch (IOException e) {
//            System.err.println("Errore deserializzazione");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void chiediOrario() {
        gF.chiediOrario();
    }
}
