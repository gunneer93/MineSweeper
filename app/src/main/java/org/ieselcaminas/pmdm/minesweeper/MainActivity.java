package org.ieselcaminas.pmdm.minesweeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private GridLayout grid;
    private MineButton[][] buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button resetButton = findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        initGrid();
        generateBombs();
    }

    private void initGrid() {
        Singleton s = Singleton.getInstance();
        grid = findViewById(R.id.gridLayout);
        grid.setRowCount(s.getNumRows());
        grid.setColumnCount(s.getNumCols());
        addButtons(grid);
    }

    private void addButtons(GridLayout gridLayout) {
        Singleton s = Singleton.getInstance();
        buttons = new MineButton[s.getNumRows()][s.getNumCols()];
        for(int row = 0; row<s.getNumRows(); row++) {
            for(int col = 0; col<s.getNumCols(); col++) {
                MineButton button = new MineButton(getApplicationContext(), row, col);
                buttons[row][col] = button;
                button.setTag("empty");
                addListenerToButton(button);
                gridLayout.addView(button);
            }
        }
    }

    private void addListenerToButton(final MineButton button) {
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    if(button.getTag().equals("bomb")) {
                        button.setState(ButtonState.BOMB);
                        TextView textGameOver = findViewById(R.id.textView);
                        textGameOver.setText("GAME OVER");
                    } else {
                        button.setState(ButtonState.OPEN);
                    }
                }
                return false;
            }
        });
    }

    private void generateBombs() {
        Singleton s = Singleton.getInstance();
        int bombCounter = 0;
        while(bombCounter < s.getNumBombs()) {
            int row = (int)(Math.random()*s.getNumRows());
            int col = (int)(Math.random()*s.getNumCols());

            if(!buttons[row][col].getTag().equals("bomb")) {
                buttons[row][col].setTag("bomb");
                bombCounter++;
            }
        }
    }
}
