package com.example.ivode.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    public void onTwoPlayerClicked(View view) {
        Intent intent = new Intent(StartActivity.this, MainActivity.class);
        intent.putExtra("game", false);
        startActivity(intent);
    }

    public void onComputerClicked(View view) {
        Intent intent = new Intent(StartActivity.this, MainActivity.class);
        intent.putExtra("game", true);
        startActivity(intent);
    }
}
