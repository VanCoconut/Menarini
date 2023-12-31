package com.ogc.menarini.Common;

import com.ogc.menarini.Entity.Farmaco;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

/**
 * Questa classe serve a poter visualizzare un {@link Farmaco} all'interno di una {@link javafx.scene.control.TableView tabella JavaFX}
 * <p>
 * In particolare è possibile aggiungere un bottone con un {@link RecordCollo#nomeBottone suo testo} ed una {@link RecordCollo#callback sua funzione}
 */
public class RecordFarmaco extends Farmaco {
    private final String nomeBottone;
    private final EventHandler<ActionEvent> callback;
    private Button bottone;

    public RecordFarmaco(int id_farmaco, String nome, String principio_attivo, boolean da_banco, int quantitaFarmaci, String nomeBottone, EventHandler<ActionEvent> callback) {
        super(id_farmaco, nome, principio_attivo, da_banco, quantitaFarmaci);
        this.nomeBottone = nomeBottone;
        this.callback = callback;
        this.initializeButton();
    }

    public RecordFarmaco(int id_farmaco, String nome, String principio_attivo, int da_banco, String nomeBottone, EventHandler<ActionEvent> callback) {
        super(id_farmaco, nome, principio_attivo, da_banco);
        this.nomeBottone = nomeBottone;
        this.callback = callback;
        this.initializeButton();
    }

    private void initializeButton() {
        if (nomeBottone != null && !nomeBottone.isBlank()) {
            bottone = new Button(nomeBottone);
            bottone.setOnAction(callback);
            bottone.getStyleClass().add("btnlist");
            bottone.setMaxHeight(10);
        }
    }

    public static RecordFarmaco fromFarmaco(Farmaco farmaco, String nomeBottone, EventHandler<ActionEvent> callback) {
        return new RecordFarmaco(
                farmaco.getId_farmaco(),
                farmaco.getNome(),
                farmaco.getPrincipio_attivo(),
                farmaco.isDa_banco(),
                farmaco.getQuantitaFarmaci(),
                nomeBottone,
                callback
        );
    }

    public static RecordFarmaco fromFarmaco(Farmaco farmaco) {
        return new RecordFarmaco(
                farmaco.getId_farmaco(),
                farmaco.getNome(),
                farmaco.getPrincipio_attivo(),
                farmaco.isDa_banco(),
                farmaco.getQuantitaFarmaci(),
                null,
                null
        );
    }

    public String getNomeBottone() {
        return nomeBottone;
    }

    public EventHandler<ActionEvent> getCallback() {
        return callback;
    }

    public Button getBottone() {
        return bottone;
    }

}
