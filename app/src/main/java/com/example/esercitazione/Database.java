package com.example.esercitazione;

import java.util.ArrayList;
import java.util.List;

public class Database {
    public ArrayList<Utente> getUtenti() {
        return utenti;
    }

    ArrayList<Utente> utenti=new ArrayList<>();
    static Database DB=new Database();
    public static Database getInstance(){
        return DB;
    }
    public void aggiungiUtente(Utente u){
        utenti.add(u);
    }
    public void aggiungiUtente(String username,String password,String citta,String datadiNascita,boolean admin){
        Utente nuovoUtente=new Utente(username,password,citta,datadiNascita,admin);
        utenti.add(nuovoUtente);
    }
    public void aggiungiUtente(String username,String password,String citta,String datadiNascita){
        aggiungiUtente(username,password,citta,datadiNascita,false);
    }
    public int cercaIdUtente(String nome){
        int i;
        for (i=0;i<utenti.size();i++){
            if(utenti.get(i).getUsername().equals(nome)){
                return i;
            }
        }
        return -1;
    }
    public Utente cercaUtente(String nome){
        int i=cercaIdUtente(nome);
        if(i>=0)
            return utenti.get(i);
        return null;
    }

}
