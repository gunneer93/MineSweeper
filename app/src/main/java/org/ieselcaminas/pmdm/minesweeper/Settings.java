package org.ieselcaminas.pmdm.minesweeper;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Settings extends AppCompatActivity{

    private static final String DEFAULT_ROWS = "8";
    private static final String DEFAULT_COLS = "8";
    private static final String DEFAULT_BOMBS = "8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setTitle("");

        Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

                if(rowsEditText.getText() == null || rowsEditText.getText().toString().equals("")) {
                    intent.putExtra("rows", DEFAULT_ROWS);
                } else {
                    intent.putExtra("rows", rowsEditText.getText().toString());
                }

                if(colsEditText.getText() == null || rowsEditText.getText().toString().equals("")) {
                    intent.putExtra("cols", DEFAULT_COLS);
                } else {
                    intent.putExtra("cols", colsEditText.getText().toString());
                }

                if(minesEditText.getText() == null || rowsEditText.getText().toString().equals("")) {
                    intent.putExtra("mines", DEFAULT_BOMBS);
                } else {
                    intent.putExtra("mines", minesEditText.getText().toString());
                }

                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
