package org.ieselcaminas.pmdm.minesweeper;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Settings extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Button okButton = findViewById(R.id.okButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                EditText rowsEditText = findViewById(R.id.rowsEditText);
                EditText colsEditText = findViewById(R.id.columnsEditText);
                EditText minesEditText = findViewById(R.id.minesEditText);

                intent.putExtra("rows", rowsEditText.getText().toString());
                intent.putExtra("cols", colsEditText.getText().toString());
                intent.putExtra("mines", minesEditText.getText().toString());

                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
