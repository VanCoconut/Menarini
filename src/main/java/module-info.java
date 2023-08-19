module com.ogc.menarini {
    requires javafx.controls;
    requires javafx.fxml;
    requires itextpdf;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
//    requires java.mail;
    requires java.net.http;
    requires java.sql;
    requires org.apache.logging.log4j;
    requires org.mariadb.jdbc;
    requires java.desktop;


    exports com.ogc.menarini.Entity;
    opens com.ogc.menarini to javafx.fxml;
    exports com.ogc.menarini;
    opens com.ogc.menarini.GestioneFarmaci to javafx.fxml;
    opens com.ogc.menarini.GestioneProduzione to javafx.fxml;
    opens com.ogc.menarini.GestioneConsegna to javafx.fxml;
    exports com.ogc.menarini.Utils;
    opens com.ogc.menarini.Utils to javafx.fxml;
    opens com.ogc.menarini.GestioneOrdini to javafx.fxml;
    opens com.ogc.menarini.Pannelli to javafx.fxml;
    exports com.ogc.menarini.Common;
    opens com.ogc.menarini.Common to javafx.fxml;
    exports com.ogc.menarini.GestioneAzienda.Interface;
    opens com.ogc.menarini.GestioneAzienda.Interface to javafx.fxml;
    exports com.ogc.menarini.GestioneAzienda.Control;
    opens com.ogc.menarini.GestioneAzienda.Control to javafx.fxml;
    exports com.ogc.menarini.GestioneFarmaci.Interface;
    opens com.ogc.menarini.GestioneFarmaci.Interface to javafx.fxml;
    exports com.ogc.menarini.GestioneFarmaci.Control;
    opens com.ogc.menarini.GestioneFarmaci.Control to javafx.fxml;
    exports com.ogc.menarini.GestioneConsegna.Interface;
    opens com.ogc.menarini.GestioneConsegna.Interface to javafx.fxml;
    exports com.ogc.menarini.GestioneConsegna.Control;
    opens com.ogc.menarini.GestioneConsegna.Control to javafx.fxml;
    exports com.ogc.menarini.GestioneUtente.Interface;
    opens com.ogc.menarini.GestioneUtente.Interface to javafx.fxml;
    exports com.ogc.menarini.GestioneUtente.Control;
    opens com.ogc.menarini.GestioneUtente.Control to javafx.fxml;
}