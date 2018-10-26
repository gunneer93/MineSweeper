package org.ieselcaminas.pmdm.minesweeper;

public class Singleton {

    private static Singleton singleton = null;
    public static final int BUTTON_WIDTH = 45;
    public static final int BUTTON_HEIGHT = 45;
    private static int numRows = 4;
    private static int numCols = 4;
    private static int numBombs = 10;
    private static int numBombsLeft = numBombs;

    private Singleton() {

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
        singleton.numRows=numRows;
    }

    public void setNumCols(int numCols){
        singleton.numCols=numCols;
    }

    public void setNumBombsLeft(int num) {
        singleton.numBombsLeft=num;
    }

    public void incrementBombsLeft() {
        singleton.numBombsLeft++;
    }

    public void decrementeBombsLeft() {
        singleton.numBombsLeft--;
    }

}