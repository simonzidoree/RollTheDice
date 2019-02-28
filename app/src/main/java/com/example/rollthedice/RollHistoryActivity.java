package com.example.rollthedice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RollHistoryActivity extends AppCompatActivity {

    Button btnClearRolls;
    ListView listRolls;

    ArrayAdapter<String> arrayAdapter;
    List<String> diceRollsList = new ArrayList<>();

    int amountOfRolls = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roll_history);
        btnClearRolls = this.findViewById(R.id.clearRollsBtn);
        listRolls = this.findViewById(R.id.listRolls);


        btnClearRolls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearRolls();
            }
        });

        diceRollsList = (List<String>) getIntent().getSerializableExtra("diceRollsList");

        arrayAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, diceRollsList);

        listRolls.setAdapter(arrayAdapter);

        arrayAdapter.notifyDataSetChanged();
    }

    private void clearRolls() {
        diceRollsList.clear();
        amountOfRolls = 1;
        arrayAdapter.notifyDataSetChanged();

        Intent clearedRollsIntent = new Intent();
        clearedRollsIntent.putExtra("clearedDiceList", (Serializable) diceRollsList);
        clearedRollsIntent.putExtra("amountOfRolls", amountOfRolls);
        setResult(RESULT_OK, clearedRollsIntent);
//        finish();
    }
}
