package com.example.esercitazione;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.icu.number.NumberFormatter;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Space;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class GestioneUtenti extends AppCompatActivity {

    Utente user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestione_utenti);
        Intent intent = getIntent();
        user=Home.OttieniUtente(intent);
        EditText ricerca=findViewById(R.id.ricerca);
        Button pulsanteRicerca=findViewById(R.id.pulsantericerca);
        pulsanteRicerca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MostraUtenti(FiltraUtenti(ricerca.getText().toString()));
            }
        });
        MostraUtenti(Database.getInstance().getUtenti());
        Button annulla=findViewById(R.id.annulla);
        annulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent indietro=new Intent(GestioneUtenti.this,Home.class);
                indietro.putExtra(Login.PATH, user);
                startActivity(indietro);
                finish();
            }
        });
    }

    ArrayList<Utente> FiltraUtenti(String nome){
        ArrayList<Utente> lista=new ArrayList<>();
        ArrayList<Utente> utenti=Database.getInstance().getUtenti();
        for(int i=0;i<utenti.size();i++){
            Utente corrente=utenti.get(i);
            if(corrente.getUsername().contains(nome)){
                if(!corrente.getUsername().equals(user.getUsername()))
                    lista.add(corrente);
            }
        }

        return lista;
    }
    void MostraUtenti(ArrayList<Utente> lista){
        RimuoviUtenti();
        LinearLayout view=findViewById(R.id.view);
        if(lista==null || lista.size()==0){
            TextView testo=new TextView(this);
            testo.setText("Nessun utente trovato");
            testo.setGravity(Gravity.CENTER);
            testo.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);
            view.addView(testo);
            return;
        }

        for(int i=0;i<lista.size();i++){

            Utente utenteCorrente=lista.get(i);
            if(utenteCorrente.getUsername().equals(user.getUsername()))
                continue;

            TextView utente=new TextView(this);
            Button interagisci=new Button(this);
            utente.setText(utenteCorrente.getUsername());
            if(utenteCorrente.isAdmin()){
                interagisci.setText("Retrocedi ADMIN");
                interagisci.setBackgroundColor(getResources().getColor(R.color.red));
            }else {
                interagisci.setText("Abilita ad admin");
                interagisci.setBackgroundColor(getResources().getColor(R.color.green));
            }
            interagisci.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isAdmin=utenteCorrente.isAdmin();
                    if(isAdmin){
                        interagisci.setText("Abilita ad admin");
                        interagisci.setBackgroundColor(getResources().getColor(R.color.green));
                    }else {
                        interagisci.setText("Retrocedi ADMIN");
                        interagisci.setBackgroundColor(getResources().getColor(R.color.red));
                    }
                    utenteCorrente.setAdmin(!isAdmin);

                }
            });
            if(utenteCorrente.getUsername().equals("admin")){
                interagisci.setEnabled(false);
                interagisci.setVisibility(View.INVISIBLE);
            }
            LinearLayout layout=new LinearLayout(this);
            Space spazio=new Space(this);
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1.0f
            );
            utente.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);
            spazio.setLayoutParams(param);
            layout.setOrientation(LinearLayout.HORIZONTAL);
            layout.addView(utente);
            layout.addView(spazio);
            layout.addView(interagisci);
            view.addView(layout);
        }

    }
    void RimuoviUtenti(){
        ((LinearLayout)findViewById(R.id.view)).removeAllViews();
    }


}