package org.ieselcaminas.pmdm.minesweeper;

public class Singleton {

    private static Singleton singleton = null;
    public static final int BUTTON_WIDTH = 60;
    public static final int BUTTON_HEIGHT = 60;
    private int numRows;
    private int numCols;
    private int numBombs;
    private int numBombsLeft;

    private Singleton() {
        numRows = 8;
        numCols = 8;
        numBombs = 10;
        numBombsLeft = numBombs;
    }

    public static Singleton getInstance() {
        if(singleton == null) {
            singleton = new Singleton();
        }
        return singleton;
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumCols() {
        return numCols;
    }

    public int getNumBombs() {
        return numBombs;
    }

    public int getNumBombsLeft() {
        return numBombsLeft;
    }

    public void setNumRows(int numRows){

        this.numRows = numRows;
    }

    public void setNumCols(int numCols){

        this.numCols=numCols;
    }

    public void setNumBombsLeft(int num) {

        this.numBombsLeft=num;
    }

    public void incrementBombsLeft() {

        this.numBombsLeft++;
    }

    public void decrementeBombsLeft() {

        this.numBombsLeft--;
    }

}