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
        TextView textGameOver = findViewById(R.id.textView);
        grid.removeAllViews();
        initGrid();
        resetButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.start));
        textGameOver.setText("");
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
                    checkCellValue(button);
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
                s.decrementeBombsLeft();
                remainingBombs.setText("" + s.getNumBombsLeft());
            }
        } else {
            if(!flagStatus) {
                if(button.getState().equals(ButtonState.FLAG)) {
                    button.setState(ButtonState.CLOSED);
                    //button.setTag("CLOSED");
                    s.incrementBombsLeft();
                    TextView remainingBombs = findViewById(R.id.remainingBombs);
                    if(!remainingBombs.getText().equals("" + s.getNumBombs())) {
                        remainingBombs.setText("" + s.getNumBombsLeft());
                    }
                }
                if(button.getTag().equals("MINE")) {
                    //button.setState(ButtonState.MINE);
                    button.setImageDrawable(getResources().getDrawable(R.drawable.nexplosion));
                    for(int row = 0; row<s.getNumRows(); row++) {
                        for(int col = 0; col<s.getNumCols(); col++) {
                            buttons[row][col].setEnabled(false);
                        }
                    }
                    gameOver(true, button);
                } else {
                    checkCellValue(button);
                }
            }
        }
    }

    private void checkCellValue(MineButton button) {
        int buttonRow = button.getRow();
        int buttonCol = button.getCol();
        int mineCounter = 0;

        for(int row = -1; row<2; row++) {
            for(int col = -1; col<2; col++) {
                try {
                    //if((row != buttonRow) && (col != buttonCol)) {
                        if(buttons[buttonRow+row][buttonCol+col].getTag().equals("MINE")) {
                            mineCounter++;
                        }
                    //}
                } catch (ArrayIndexOutOfBoundsException ex) {}
            }
        }
        for(int i = 0; i<6; i++) {
            if(mineCounter == i) {
                switch(i) {
                    case 0:
                        button.setState(ButtonState.ZERO);
                        break;
                    case 1:
                        button.setState(ButtonState.ONE);
                        break;
                    case 2:
                        button.setState(ButtonState.TWO);
                        break;
                    case 3:
                        button.setState(ButtonState.THREE);
                        break;
                    case 4:
                        button.setState(ButtonState.FOUR);
                        break;
                    case 5:
                        button.setState(ButtonState.FIVE);
                        break;
                }
            }
        }
    }

    private void gameOver(boolean state, MineButton mineButton) {
        Singleton s = Singleton.getInstance();
        Button resetButton = findViewById(R.id.resetButton);
        TextView textGameOver = findViewById(R.id.textView);
        if(state) {
            textGameOver.setText("GAME OVER!!");
            resetButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.lose));
            for(int row = 0; row<s.getNumRows(); row++) {
                for(int col = 0; col<s.getNumCols(); col++) {
                    if(buttons[row][col].getTag().equals("MINE") && buttons[row][col] != mineButton) {
                        buttons[row][col].setState(ButtonState.MINE);
                    }
                }
            }
        }
        if(!state) {
            textGameOver.setText("YOU WIN!!");
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
                //Mostrar minas..
                buttons[row][col].setState(ButtonState.MINE);
                bombCounter++;
            }
        }
    }

    /*public boolean checkWin() {
        Singleton s = Singleton.getInstance();
        int totalCells = s.getNumRows() * s.getNumCols();
        if() {
            return true;
        }
        return false;
    }*/
}
