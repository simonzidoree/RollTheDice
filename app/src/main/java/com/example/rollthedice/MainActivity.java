package com.example.rollthedice;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button btnRoll;
    Button btnHistory;
    NumberPicker np;

    List<String> diceRollsList = new ArrayList<>();

    int amountOfRolls = 1;
    int chosenDiceNumber = 1;

    Random rndNumb = new Random();
    ImageView[] dices;
    int[] diceImages = {R.drawable.d1, R.drawable.d2, R.drawable.d3, R.drawable.d4, R.drawable.d5, R.drawable.d6};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRoll = this.findViewById(R.id.rollBtn);
        btnHistory = this.findViewById(R.id.btnHistory);
        np = this.findViewById(R.id.numberPicker);
        dices = new ImageView[]{findViewById(R.id.dice1), findViewById(R.id.dice2), findViewById(R.id.dice3), findViewById(R.id.dice4), findViewById(R.id.dice5), findViewById(R.id.dice6)};

        np.setMinValue(1);
        np.setMaxValue(6);

        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                chosenDiceNumber = picker.getValue();

                for (int i = 0; i < 6; i++) {
                    dices[i].setVisibility(View.INVISIBLE);
                }

                for (int j = 0; j < picker.getValue(); j++) {
                    dices[j].setVisibility(View.VISIBLE);
                }
            }
        });

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

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < chosenDiceNumber; i++) {
            int randomNumber = rndNumb.nextInt(diceImages.length);
            stringBuilder.append(randomNumber + 1 + ", ");
            dices[i].setImageResource(diceImages[randomNumber]);
        }

        stringBuilder.deleteCharAt(stringBuilder.length() - 2);

        diceRollsList.add("Roll " + amountOfRolls + " at " + simpleDateFormat.format(timeStamp) + ": " + stringBuilder);

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
