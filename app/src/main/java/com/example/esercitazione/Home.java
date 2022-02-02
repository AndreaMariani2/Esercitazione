package com.example.esercitazione;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {
    TextView testoUsername, testoPassword, testoCitta, testoData,modPassword;
    Button logout;
    Utente user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent intent = getIntent();
        user=OttieniUtente(intent);

        TextView amministratore= findViewById(R.id.admin);
        testoUsername = findViewById(R.id.testonome);
        testoPassword = findViewById(R.id.testopassword);
        testoCitta = findViewById(R.id.testocitta);
        testoData = findViewById(R.id.testodata);

        testoUsername.setText("Bentornato "+user.getUsername()+"!");
        testoPassword.setText(user.getPassword());
        testoCitta.setText(user.getCitta());
        testoData.setText(user.getDataDiNascita());

        modPassword = findViewById(R.id.modificapassword);
        if (user.getUsername().equals("admin")) {
            modPassword.setEnabled(false);
            modPassword.setVisibility(View.GONE);
        } else
            modPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent utente = new Intent(Home.this, ModificaPassword.class);
                    utente.putExtra(Login.PATH, user);
                    startActivity(utente);
                    finish();
                }
            });
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent indietro = new Intent(Home.this, Login.class);

                startActivity(indietro);
                finish();
            }
        });
        Button gestione=findViewById(R.id.gestione);
        if(user.isAdmin()){
            findViewById(R.id.schermata).setBackgroundColor(getResources().getColor(R.color.bluchiaro));
        gestione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gestisci=new Intent(Home.this,GestioneUtenti.class);
                gestisci.putExtra(Login.PATH, user);
                startActivity(gestisci);
                finish();
            }
        });
        }else {

            amministratore.setVisibility(View.INVISIBLE);
            gestione.setEnabled(false);
            gestione.setVisibility(View.GONE);
        }

    }
    public static Utente OttieniUtente(Intent intent){
        Utente u=null;
        Serializable obj = intent.getSerializableExtra(Login.PATH);
        if(obj instanceof Utente)
            u=(Utente)obj;
        else return null;
        ArrayList<Utente> l=Database.getInstance().getUtenti();
        for(int i=0;i<l.size();i++){
            if(l.get(i).getUsername().equals(u.getUsername()))
                return l.get(i);
        }
        return null;
    }
}