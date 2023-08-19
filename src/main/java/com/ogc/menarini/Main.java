package com.ogc.menarini;

import com.ogc.menarini.GestioneUtente.Control.GestoreAutenticazione;
import com.ogc.menarini.Utils.Orologio;
import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static java.lang.System.exit;

public class Main extends Application {

    public static final Logger log = LogManager.getLogger(Main.class);
    public static Stage mainStage = null;
    public static Orologio orologio = new Orologio();
    public static int sistema, idFarmacia;

    public static String nomeFarmacia;
    public static boolean debug;

    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        stage.setTitle("PharmaGC");
        log.info("App partita");
        stage.setOnCloseRequest(c -> {
            System.exit(0);
        });
        new GestoreAutenticazione(stage);
    }

    /* private static void risolviSistema(String args) {
        //Definisce quale tipo di sistema si sta usando (0 Farmacia, 1 Corriere, 2 Azienda)
        //e definisce un ID Farmacia (utilizzato solo se il sistema è destinato a una farmacia)
        try {
            if (args.length >= 1) {
                sistema = Integer.parseInt(args);
                if (sistema < 0 || sistema > 3) {
                    throw new NumberFormatException("Tipo di sistema non valido");
                }
            }
            if (args.length >= 3) {
                nomeFarmacia=args[1];
                idFarmacia = Integer.parseInt(args[2]);
            }
            if (args.length >= 4) {
                debug = Integer.parseInt(args[3]) == 1;
            }
        } catch (NumberFormatException e) {
            System.err.println(e.getMessage());
            exit(1);
        }
    } */

    public static void main(String[] args) {
        if ("0".equals(System.getProperty("bar"))) {
            sistema=0;
            System.out.println("Accesso come Farmacista");
            orologio.start();
            launch();
            try {
                orologio.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else if ("1".equals(System.getProperty("bar"))) {
            sistema=1;   
            System.out.println("Accesso come Corriere");
            orologio.start();
            launch();
            try {
                orologio.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }     
        }
        else if ("2".equals(System.getProperty("bar"))) {
            sistema=2;
            System.out.println("Accesso come Operatore");
            orologio.start();
            launch();
            try {
                orologio.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        else {
            System.out.println("Numero invalido");
        }
        
    }
}