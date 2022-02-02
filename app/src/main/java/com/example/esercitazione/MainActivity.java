package com.example.esercitazione;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Database.getInstance().aggiungiUtente("admin","admin","cagliari","1/1/1970",true);
        Intent login=new Intent(MainActivity.this,Login.class);
        startActivity(login);
        finish();
    }

}