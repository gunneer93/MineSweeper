package org.ieselcaminas.pmdm.minesweeper;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private GridLayout grid;
    private MineButton[][] buttons;
    private boolean flagStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initGrid();

        Button resetButton = findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });

        Button flagOn = findViewById(R.id.flagButton);
        flagOn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //TextView textFlag = findViewById(R.id.flagAction);
                //if(!flagButton.isEnabled()) {
                    //textFlag.setText("ON");
                    //textFlag.setTextColor(Color.GREEN);
                    flagStatus = true;
                    //flagButton.setEnabled(true);
                //}
                /*if(flagButton.isEnabled()) {
                    textFlag.setText("OFF");
                    textFlag.setTextColor(Color.RED);
                    flagStatus = false;
                    flagButton.setEnabled(false);
                }*/
                return false;
            }
        });

        Button flagOff = findViewById(R.id.flagButton2);
        flagOff.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                flagStatus = false;
                return false;
            }
        });
    }

    private void resetGame() {
        Button resetButton = findViewById(R.id.resetButton);
        addButtons(grid);
        generateMines();
        resetButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.start));
    }

    private void initGrid() {
        Singleton s = Singleton.getInstance();
        grid = findViewById(R.id.gridLayout);
        grid.setRowCount(s.getNumRows());
        grid.setColumnCount(s.getNumCols());
        addButtons(grid);
        flagStatus = false;
        TextView remainingBombs = findViewById(R.id.remainingBombs);
        remainingBombs.setText("" + s.getNumBombsLeft());
        generateMines();
    }

    private void addButtons(GridLayout gridLayout) {
        Singleton s = Singleton.getInstance();
        buttons = new MineButton[s.getNumRows()][s.getNumCols()];
        for(int row = 0; row<s.getNumRows(); row++) {
            for(int col = 0; col<s.getNumCols(); col++) {
                MineButton button = new MineButton(getApplicationContext(), row, col);
                buttons[row][col] = button;
                button.setTag("CLOSED");
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
                    checkAction(button);
                }
                return false;
            }
        });
    }

    private void checkAction(MineButton button) {
        Singleton s = Singleton.getInstance();
        if(flagStatus) {
            if(s.getNumBombsLeft() != 0) {
                button.setState(ButtonState.FLAG);
                //button.setTag("FLAG");
                TextView remainingBombs = findViewById(R.id.remainingBombs);
                s.setNumBombsLeft(s.getNumBombsLeft() - 1);
                remainingBombs.setText("" + s.getNumBombsLeft());
            }
        } else {
            if(!flagStatus) {
                if(button.getState().equals(ButtonState.FLAG)) {
                    button.setState(ButtonState.CLOSED);
                    //button.setTag("CLOSED");
                    s.setNumBombsLeft(s.getNumBombsLeft() + 1);
                    TextView remainingBombs = findViewById(R.id.remainingBombs);
                    if(!remainingBombs.getText().equals("" + s.getNumBombs())) {
                        remainingBombs.setText("" + s.getNumBombsLeft());
                    }
                }
                if(button.getTag().equals("MINE")) {
                    button.setState(ButtonState.MINE);
                    for(int row = 0; row<s.getNumRows(); row++) {
                        for(int col = 0; col<s.getNumCols(); col++) {
                            buttons[row][col].setEnabled(false);
                        }
                    }
                    gameOver(true);
                } else {
                    button.setState(ButtonState.OPEN);
                    button.setTag("OPEN");
                }
            }
        }
    }

    private void gameOver(boolean state) {
        Singleton s = Singleton.getInstance();
        Button resetButton = findViewById(R.id.resetButton);
        TextView textGameOver = findViewById(R.id.textView);
        if(state) {
            for(int row = 0; row<s.getNumRows(); row++) {
                for(int col = 0; col<s.getNumCols(); col++) {
                    if(buttons[row][col].getTag().equals("MINE")) {
                        textGameOver.setText("GAME OVER!!");
                        buttons[row][col].setState(ButtonState.MINE);
                        resetButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.lose));
                    }
                }
            }
        }
    }

    private void win(boolean state) {
        Singleton s = Singleton.getInstance();
        Button resetButton = findViewById(R.id.resetButton);
        if(state) {
            resetButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.win));
        }

    }

    private void generateMines() {
        Singleton s = Singleton.getInstance();
        int bombCounter = 0;
        while(bombCounter < s.getNumBombs()) {
            int row = (int)(Math.random()*s.getNumRows());
            int col = (int)(Math.random()*s.getNumCols());

            if(!buttons[row][col].getTag().equals("MINE")) {
                buttons[row][col].setTag("MINE");
                buttons[row][col].setState(ButtonState.MINE);
                bombCounter++;
            }
        }
    }
}
