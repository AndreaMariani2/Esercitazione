package com.example.esercitazione;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class Login extends AppCompatActivity {


    EditText TestoUsername;
    EditText TestoPassword;
    public static String PATH="com.exanple.esercitazione.Utente";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button Conferma=findViewById(R.id.conferma);
        TextView TestoRegistrati=findViewById(R.id.registrati);

        TestoUsername=findViewById(R.id.username);
        TestoPassword=findViewById(R.id.password);
        Conferma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utente u=null;
                if(ControlloDati())
                    u=Accedi();
                    if(u!=null){
                        Intent utente=new Intent(Login.this,Home.class);
                        utente.putExtra(PATH,u);
                        startActivity(utente);
                    }
            }
        });
        TestoRegistrati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registrazione= new Intent( Login.this,Registrati.class);
                startActivity(registrazione);
            }
        });


    }
    Utente Accedi(){
        String username=TestoUsername.getText().toString();
        String password=TestoPassword.getText().toString();
        ArrayList<Utente> utenti=Database.getInstance().getUtenti();
        int i=Database.getInstance().cercaIdUtente(username);


        if(i<0){
            TestoUsername.setError("L'utente non esiste");
            TestoPassword.setText("");
            return  null;
        }
        if(password.equals(utenti.get(i).getPassword())){
            return utenti.get(i);
        }else{
            TestoPassword.setError("Password errata");
            return  null;
        }

    }
    boolean ControlloDati(){
        boolean corretto=true;
        if(TestoUsername.getText()==null || TestoUsername.getText().length()==0){
            TestoUsername.setError("Inserire Nome utente");
            corretto= false;
        }

        if(TestoPassword.getText()==null || TestoPassword.getText().length()==0){
            TestoPassword.setError("Inserire Password");
            corretto= false;
        }
        return corretto;


    }
}