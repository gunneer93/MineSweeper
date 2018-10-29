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
                game.checkAction(MineButton.this);
                if(!game.gameOver) {
                    game.checkCellValue(MineButton.this);
                }
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
                setBackgroundDrawable(getResources().getDrawable(R.drawable.nflag));
                s.decrementeBombsLeft();
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

    public void setState(ButtonState state) {
        this.state = state;
        switch (state) {
            case CLOSED:
                setBackgroundDrawable(getResources().getDrawable(R.drawable.cell));
                break;
            case ZERO:
                setImageDrawable(getResources().getDrawable(R.drawable.cell_0));
                break;
            case ONE:
                setImageDrawable(getResources().getDrawable(R.drawable.cell_1));
                break;
            case TWO:
                setImageDrawable(getResources().getDrawable(R.drawable.cell_2));
                break;
            case THREE:
                setImageDrawable(getResources().getDrawable(R.drawable.cell_3));
                break;
            case FOUR:
                setImageDrawable(getResources().getDrawable(R.drawable.cell_4));
                break;
            case FIVE:
                setImageDrawable(getResources().getDrawable(R.drawable.cell_5));
                break;
            case MINE:
                setImageDrawable(getResources().getDrawable(R.drawable.mine));
                break;
            default:
                setImageBitmap(null);
        }
    }

    public ButtonState getState() {
        return state;
    }

}