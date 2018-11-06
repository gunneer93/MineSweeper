package org.ieselcaminas.pmdm.minesweeper;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Game extends AppCompatActivity {

    private GridLayout grid;
    private MineButton[][] buttons;
    public boolean gameOver;
    public TextView remainingBombs;
    private Button resetButton;
    private TextView textGameOver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        resetButton = findViewById(R.id.resetButton);
        textGameOver = findViewById(R.id.textView);

        initGrid();

        Button resetButton = findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }

    private void resetGame() {
        Singleton s = Singleton.getInstance();
        grid.removeAllViews();
        initGrid();
        resetButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.start));
        textGameOver.setText("");
        s.setNumBombsLeft(s.getNumBombs());
    }

    private void initGrid() {
        Singleton s = Singleton.getInstance();
        gameOver = false;
        grid = findViewById(R.id.gridLayout);
        grid.setRowCount(s.getNumRows());
        grid.setColumnCount(s.getNumCols());
        addButtons(grid);
        remainingBombs = findViewById(R.id.remainingBombs);
        remainingBombs.setText("" + s.getNumBombs());
        generateMines();
    }

    private void addButtons(GridLayout gridLayout) {
        Singleton s = Singleton.getInstance();
        buttons = new MineButton[s.getNumRows()][s.getNumCols()];
        for(int row = 0; row<s.getNumRows(); row++) {
            for(int col = 0; col<s.getNumCols(); col++) {
                MineButton button = new MineButton(getApplicationContext(), row, col, this);
                buttons[row][col] = button;
                button.setTag("CLOSED");
                button.setState(ButtonState.CLOSED);
                gridLayout.addView(button);
            }
        }
    }

    public void checkCellValue(MineButton button) {
        int buttonRow = button.getRow();
        int buttonCol = button.getCol();
        int mineCounter = 0;

        button.setState(ButtonState.OPEN);

        for(int row = -1; row<2; row++) {
            for(int col = -1; col<2; col++) {
                try {
                    if(!(row == 0 && col == 0)) {
                        if(buttons[buttonRow+row][buttonCol+col].getTag().equals("MINE")) {
                            mineCounter++;
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException ex) {}
            }
        }
        button.displayNumber(mineCounter);
        if(mineCounter == 0) {
            for(int row = -1; row<2; row++) {
                for(int col = -1; col<2; col++) {
                    try {
                        if(!(row == 0 && col == 0) && buttons[buttonRow+row][buttonCol+col].getState() == ButtonState.CLOSED) {
                            checkCellValue(buttons[buttonRow+row][buttonCol+col]);
                        }
                    } catch (ArrayIndexOutOfBoundsException ex) {}
                }
            }
        }
    }

    public void checkLose(MineButton button) {
        Singleton s = Singleton.getInstance();
        boolean lose = false;
        if(button.getTag().equals("MINE")) {
            button.setImageDrawable(getResources().getDrawable(R.drawable.nexplosion));
            lose = true;
            gameOver = true;
            for (int row = 0; row < s.getNumRows(); row++) {
                for (int col = 0; col < s.getNumCols(); col++) {
                    if(buttons[row][col].getTag().equals("MINE") && buttons[row][col] != button) {
                        if(buttons[row][col].getState() == ButtonState.FLAG) {
                            buttons[row][col].setState(ButtonState.GREEN_FLAG);
                        } else {
                            buttons[row][col].setState(ButtonState.MINE);
                        }
                    }
                    buttons[row][col].setEnabled(false);
                }
            }
        }
        if(lose) {
            textGameOver.setText("GAME OVER!!");
            resetButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.lose));
        }
    }

    public void checkWin() {
        Singleton s = Singleton.getInstance();
        boolean win = true;
        for(int row = 0; row<s.getNumRows(); row++) {
            for (int col = 0; col < s.getNumCols(); col++) {
                if(buttons[row][col].getState() == ButtonState.CLOSED) {
                    win = false;
                }
            }
        }
        if(win) {
            for (int row = 0; row < s.getNumRows(); row++) {
                for (int col = 0; col < s.getNumCols(); col++) {
                    buttons[row][col].setEnabled(false);
                }
            }
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
                //buttons[row][col].setState(ButtonState.MINE);
                bombCounter++;
            }
        }
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater(); inflater.inflate(R.menu.menu_toolbar, menu); return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, Settings.class);
                startActivityForResult(intent, 123);
                return true;
            default:return super.onOptionsItemSelected(item); }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 123 && resultCode==RESULT_OK) {
            Singleton s = Singleton.getInstance();
            Bundle extras = data.getExtras();
            String rows = extras.getString("rows");
            String cols = extras.getString("cols");
            String mines = extras.getString("mines");

            s.setNumRows(Integer.parseInt(rows));
            s.setNumCols(Integer.parseInt(cols));
            s.setNumBombs(Integer.parseInt(mines));

            resetGame();
        }
    }
}
