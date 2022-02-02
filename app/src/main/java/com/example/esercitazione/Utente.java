package com.example.esercitazione;

import java.io.Serializable;

import javax.xml.parsers.FactoryConfigurationError;

public class Utente implements Serializable {
    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getDataDiNascita() {
        return dataDiNascita;
    }

    public void setDataDiNascita(String dataDiNascita) {
        this.dataDiNascita = dataDiNascita;
    }

    private String Username,password,citta,dataDiNascita;
    boolean admin;

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return admin;
    }
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }


    public Utente(String username,String password,String citta,String dataDiNascita){
        this(username,password,citta,dataDiNascita,false);
    }
    public Utente(String nome,String password,String citta,String dataDiNascita,boolean admin){
        this.Username=nome;
        this.password=password;
        this.citta=citta;
        this.dataDiNascita=dataDiNascita;
        this.admin=admin;
    }
}
