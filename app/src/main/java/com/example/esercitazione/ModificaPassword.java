package com.example.esercitazione;

import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;

public class ModificaPassword extends AppCompatActivity {
    Utente user;
    EditText nuovaPassword, confermaPassword;
    Button conferma, indietro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifica_password);

        Intent intent = getIntent();
        user=Home.OttieniUtente(intent);

        TextView passwordCorrente=findViewById(R.id.passwordcorrente);
        passwordCorrente.setText("Password corrente: "+user.getPassword());
        nuovaPassword = findViewById(R.id.testopassword);
        confermaPassword = findViewById(R.id.password2);
        indietro = findViewById(R.id.indietro);
        conferma = findViewById(R.id.confermapassword);
        conferma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (controlloPassword()) {
                    Utente utente = Database.getInstance().cercaUtente(user.getUsername());
                    utente.setPassword(nuovaPassword.getText().toString());
                    Intent cambiaPass = new Intent(ModificaPassword.this, Home.class);
                    cambiaPass.putExtra(Login.PATH, utente);
                    startActivity(cambiaPass);
                    finish();
                }
            }
        });
        indietro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent annulla = new Intent(ModificaPassword.this, Home.class);
                annulla.putExtra(Login.PATH, user);
                startActivity(annulla);
                finish();
            }
        });
    }

    boolean controlloPassword() {
        boolean corretto = true;
        if (nuovaPassword.getText().length() == 0 || nuovaPassword.getText() == null) {
            nuovaPassword.setError("Inserire Password");
            corretto = false;
        }
        if (!nuovaPassword.getText().toString().equals(confermaPassword.getText().toString())) {
            confermaPassword.setError("le due password devono essere uguali");
            corretto = false;
        }
        if (!Registrati.ControlloPassword(nuovaPassword, nuovaPassword.getText().toString()))
            corretto = false;

        return corretto;
    }
}