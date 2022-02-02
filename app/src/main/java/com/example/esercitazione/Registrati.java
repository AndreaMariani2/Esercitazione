package com.example.esercitazione;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registrati extends AppCompatActivity {
//: Username, password, password ripetuta, città di provenienza e data
//di nascita
    EditText testoUsername,testoPassword,confermaPassword,testoCitta,testoData;
    Calendar dataDiNascita;
    TextView erroreData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrati);

        testoUsername=findViewById(R.id.username);
        testoPassword=findViewById(R.id.password);
        confermaPassword=findViewById(R.id.confpassword);
        testoCitta=findViewById(R.id.citta);
        testoData=findViewById(R.id.data);
        erroreData=findViewById(R.id.erroredata);

        Button conferma=findViewById(R.id.conferma);

        Button annulla=findViewById(R.id.annulla);

        testoData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });/*
        testoData.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    showDialog();
            }
        });*/
        conferma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utente u;
                if(ControlloInput()) {
                    Database.getInstance().aggiungiUtente(testoUsername.getText().toString(),testoPassword.getText().toString(),testoCitta.getText().toString(),testoData.getText().toString());
                    Intent confermaRegistrazione=new Intent(Registrati.this, Login.class);
                    startActivity(confermaRegistrazione);
                    finish();

                }
            }
        });
        annulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent annullaRegistrazione=new Intent(Registrati.this, Login.class);
                startActivity(annullaRegistrazione);
                finish();
            }
        });
    }
    //la password sia di almeno 8 caratteri e contenga almeno una lettera
    //minuscola, una maiuscola, un numero ed un carattere speciale.
    //o le due password coincidano;
    //o la data di nascita, una volta cliccata, mostri un date picker fragment come
    //visto a lezione. Controllare inoltre che l’utente sia maggiorenne;
    //o nel caso di uno o più campi vuoti o errati, mostrare l’errore specifico
    //corrispondente alla riga del campo (come visto a lezione)
    boolean ControlloInput(){
        ResettaErrori();
        boolean corretto=true;
        corretto=ControlloPassword(testoPassword,testoPassword.getText().toString());


        if(!testoPassword.getText().toString().equals(confermaPassword.getText().toString())){
            confermaPassword.setError("le due password devono essere uguali");
            corretto=false;
        }
        if(dataDiNascita==null){
            testoData.setError("Inserire data di nascita");
            erroreData.setVisibility(View.VISIBLE);
            erroreData.setText("Inserire data di nascita");
            corretto=false;
        }else{
            Calendar oggi=Calendar.getInstance();
            long tempo=oggi.getTimeInMillis()-dataDiNascita.getTimeInMillis();
            int giorni=(int)(tempo/(1000*60*60*24));
            if(giorni/365<18){
                testoData.setError("Devi avere almeno 18 anni");

                erroreData.setVisibility(View.VISIBLE);
                erroreData.setText("Devi avere almeno 18 anni");
                corretto=false;
            }
        }
        if(testoCitta.getText()==null || testoCitta.getText().length()==0){
            corretto=false;
            testoCitta.setError("Inserire Città");

        }
        if(testoUsername.getText()==null || testoUsername.getText().length()==0){
            corretto=false;
            testoUsername.setError("Inserire Username");

        }else {
            if(Database.getInstance().cercaIdUtente(testoUsername.getText().toString())>=0){
                testoUsername.setError("Nome già in uso da un altro utente,scegliere un nome diverso");
                corretto=false;
            }
        }
        return  corretto;
    }
    public static boolean ControlloPassword(EditText testo,String password){
        boolean corretto=true;
        String errore="";
        if(password.length()<8){
            errore+="la password deve essere lunga almeno 8 caratteri";
            corretto=false;
        }
        if(!CaratteriPassword(password)){
            errore+=" la password deve contenere 1 maiuscola, 1 minuscola, 1 numero e 1 carattere speciale";
            corretto=false;
        }
        if(!corretto)
            testo.setError(errore);
        return corretto;
    }
    void ResettaErrori(){
        testoUsername.setError(null);
        testoCitta.setError(null);
        testoData.setError(null);
        testoPassword.setError(null);
        confermaPassword.setError(null);
        erroreData.setVisibility(View.INVISIBLE);

    }
    static boolean CaratteriPassword(final String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[@#$%^&+=!()?])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }
    void showDialog() {
        DialogFragment newFragment = DatePickerFragment.newInstance();
        newFragment.show(getSupportFragmentManager(), "dialog");
    }

    public void doPositiveClick(Calendar data) {
        SimpleDateFormat format=new SimpleDateFormat("dd/MM/YYYY");
        testoData.setText(format.format(data.getTime()));
        dataDiNascita=data;
    }

    public void doNegativeClick() {
        // Do stuff here.
    }
}