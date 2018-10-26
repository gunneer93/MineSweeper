package org.ieselcaminas.pmdm.minesweeper;

import android.content.Context;
import android.widget.FrameLayout;


public class MineButton extends android.support.v7.widget.AppCompatImageButton {

    private int row,col;
    private ButtonState state;

    public MineButton(Context context, int row, int col) {
        super(context);
        this.row=row;
        this.col=col;
        state = ButtonState.CLOSED;

        Singleton s = Singleton.getInstance();
        android.view.ViewGroup.LayoutParams params = new FrameLayout.LayoutParams(s.BUTTON_WIDTH, s.BUTTON_HEIGHT);
        setLayoutParams(params);
        setBackgroundDrawable(getResources().getDrawable(R.drawable.boton));
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
            case OPEN:
                setImageDrawable(getResources().getDrawable(R.drawable.boton_pressed));
                setPadding(5,5,5,5);
                setScaleType(ScaleType.FIT_XY);
                break;
            case BOMB:
                setImageDrawable(getResources().getDrawable(R.drawable.bomb));
                setPadding(5,5,5,5);
                setScaleType(ScaleType.FIT_XY);
                break;
            case FLAG:
                setImageDrawable(getResources().getDrawable(R.drawable.flag));
                setPadding(5,5,5,5);
                setScaleType(ScaleType.FIT_XY);
                break;
            case QUESTION:
                setImageDrawable(getResources().getDrawable(R.drawable.question));
                setPadding(5,5,5,5);
                setScaleType(ScaleType.FIT_XY);
                break;
            default:
                setImageBitmap(null);
        }
    }

    public ButtonState getState() {
        return state;
    }

}