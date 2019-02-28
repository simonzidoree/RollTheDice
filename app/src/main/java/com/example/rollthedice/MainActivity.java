package com.example.rollthedice;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button btnRoll;
    Button btnHistory;

    ImageView diceOne;
    ImageView diceTwo;

    List<String> diceRollsList = new ArrayList<>();

    int amountOfRolls = 1;

    Random rndNumb = new Random();
    int[] diceImages = {R.drawable.d1, R.drawable.d2, R.drawable.d3, R.drawable.d4, R.drawable.d5, R.drawable.d6};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRoll = this.findViewById(R.id.rollBtn);
        diceOne = this.findViewById(R.id.dice1);
        diceTwo = this.findViewById(R.id.dice2);
        btnHistory = this.findViewById(R.id.btnHistory);


        btnRoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rollTheDices();
            }
        });


        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent historyIntent = new Intent(MainActivity.this, RollHistoryActivity.class);
                historyIntent.putExtra("diceRollsList", (Serializable) diceRollsList);
                startActivityForResult(historyIntent, 1);
            }
        });
    }

    private void rollTheDices() {
        Log.d("RollTheDicesM", "Dices rolled...");

        int diceOneRoll = rndNumb.nextInt(diceImages.length);
        int diceTwoRoll = rndNumb.nextInt(diceImages.length);

        Log.d("First dice rolled ", String.valueOf(diceOneRoll + 1));
        Log.d("Second dice rolled ", String.valueOf(diceTwoRoll + 1));

        diceOne.setImageResource(diceImages[diceOneRoll]);

        diceTwo.setImageResource(diceImages[diceTwoRoll]);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
        diceRollsList.add("Roll " + amountOfRolls + " at " + simpleDateFormat.format(timeStamp) + ": " + (diceOneRoll + 1) + " - " + (diceTwoRoll + 1));

        amountOfRolls++;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                diceRollsList = (List<String>) data.getSerializableExtra("clearedDiceList");
                amountOfRolls = data.getIntExtra("amountOfRolls", 1);
            }
        }
    }
}
