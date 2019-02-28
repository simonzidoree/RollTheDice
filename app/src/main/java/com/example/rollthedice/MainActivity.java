package com.example.rollthedice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button btnRoll;
    Button btnClearRolls;

    ImageView diceOne;
    ImageView diceTwo;

    ListView listRolls;
    List<String> diceRollsList = new ArrayList<>();

    ArrayAdapter<String> arrayAdapter;

    int amountOfRolls = 1;

    Random rndNumb = new Random();
    int[] diceImages = {R.drawable.d1, R.drawable.d2, R.drawable.d3, R.drawable.d4, R.drawable.d5, R.drawable.d6};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRoll = this.findViewById(R.id.rollBtn);
        btnClearRolls = this.findViewById(R.id.clearRollsBtn);
        diceOne = this.findViewById(R.id.dice1);
        diceTwo = this.findViewById(R.id.dice2);
        listRolls = this.findViewById(R.id.listRolls);

        arrayAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, diceRollsList);

        listRolls.setAdapter(arrayAdapter);

        btnRoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rollTheDices();
            }
        });

        btnClearRolls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearRolls();
            }
        });
    }

    private void clearRolls() {
        diceRollsList.clear();
        amountOfRolls = 1;
        arrayAdapter.notifyDataSetChanged();
    }

    private void rollTheDices() {
        Log.d("RollTheDicesM", "Dices rolled...");

        int diceOneRoll = rndNumb.nextInt(diceImages.length);
        int diceTwoRoll = rndNumb.nextInt(diceImages.length);

        Log.d("First dice rolled ", String.valueOf(diceOneRoll + 1));
        Log.d("Second dice rolled ", String.valueOf(diceTwoRoll + 1));

        diceOne.setImageResource(diceImages[diceOneRoll]);

        diceTwo.setImageResource(diceImages[diceTwoRoll]);

        diceRollsList.add("Roll " + amountOfRolls + ": First dice rolled " + (diceOneRoll + 1) + " - Second dice rolled " + (diceTwoRoll + 1));

        amountOfRolls++;
        arrayAdapter.notifyDataSetChanged();
    }
}
