package org.ieselcaminas.pmdm.minesweeper;

import android.content.Context;
import android.net.wifi.p2p.WifiP2pManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;


public class MineButton extends android.support.v7.widget.AppCompatImageButton {

    private int row,col;
    private ButtonState state;
    private Game game;

    public MineButton(Context context, int row, int col, final Game game) {
        super(context);
        this.row=row;
        this.col=col;
        this.game = game;
        state = ButtonState.CLOSED;

        Singleton s = Singleton.getInstance();
        android.view.ViewGroup.LayoutParams params = new FrameLayout.LayoutParams(s.BUTTON_WIDTH, s.BUTTON_HEIGHT);
        setLayoutParams(params);
        setBackgroundDrawable(getResources().getDrawable(R.drawable.cell));

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Singleton s = Singleton.getInstance();
                if(state == ButtonState.FLAG) {
                    setState(ButtonState.QUESTION);
                    s.incrementBombsLeft();
                    game.remainingBombs.setText("" + s.getNumBombsLeft());
                } else {
                    if(state == ButtonState.QUESTION) {
                        setState(ButtonState.CLOSED);
                    } else {
                        if(!game.gameOver) {
                            game.checkCellValue(MineButton.this);
                        }
                    }
                }
                game.checkLose(MineButton.this);
                game.checkWin();
            }
        });

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    setBackgroundDrawable(getResources().getDrawable(R.drawable.cell_0));
                }
                if(event.getAction() == MotionEvent.ACTION_UP) { }
                return false;
            }
        });

        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Singleton s = Singleton.getInstance();
                if(state == ButtonState.CLOSED && s.getNumBombsLeft() > 0) {
                    setState(ButtonState.FLAG);
                    s.decrementeBombsLeft();
                    game.remainingBombs.setText("" + s.getNumBombsLeft());
                } else {
                    if(state == ButtonState.FLAG) {
                        setState(ButtonState.CLOSED);
                        s.incrementBombsLeft();
                        game.remainingBombs.setText("" + s.getNumBombsLeft());
                    }
                }
                return true;
            }
        });
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void displayNumber(int value) {
        switch (value) {
            case 0:
                setImageDrawable(getResources().getDrawable(R.drawable.cell_0));
                break;
            case 1:
                setImageDrawable(getResources().getDrawable(R.drawable.cell_1));
                break;
            case 2:
                setImageDrawable(getResources().getDrawable(R.drawable.cell_2));
                break;
            case 3:
                setImageDrawable(getResources().getDrawable(R.drawable.cell_3));
                break;
            case 4:
                setImageDrawable(getResources().getDrawable(R.drawable.cell_4));
                break;
            case 5:
                setImageDrawable(getResources().getDrawable(R.drawable.cell_5));
                break;
            default:
                setImageBitmap(null);
        }
    }

    public void setState(ButtonState state) {
        this.state = state;
        switch (state) {
            case CLOSED:
                setImageDrawable(getResources().getDrawable(R.drawable.cell));
                break;
            case FLAG:
                setImageDrawable(getResources().getDrawable(R.drawable.nflag));
                break;
            case GREEN_FLAG:
                setImageDrawable(getResources().getDrawable(R.drawable.greenflag));
                break;
            case MINE:
                setImageDrawable(getResources().getDrawable(R.drawable.mine));
                break;
            case QUESTION:
                setImageDrawable(getResources().getDrawable(R.drawable.nquestion));
                break;
        }
    }

    public ButtonState getState() {
        return state;
    }

}