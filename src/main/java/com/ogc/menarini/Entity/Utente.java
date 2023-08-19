package com.ogc.menarini.Entity;

import java.util.Objects;

public class Utente {

    private final String email;
    private String password;

    public Utente(String email, String password) {
        this.email = email;
        this.password=password;
    }

    public Utente(String email) {
        this.email = email;
    }

    public String email() {
        return email;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Utente) obj;
        return this.email.compareToIgnoreCase(that.email()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash( email);
    }

    @Override
    public String toString() {
        return "Utente[" +
                "email=" + email + ", " + ']';
    }

}
