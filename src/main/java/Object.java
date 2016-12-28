package main.java;

/**
 * @ Description: Object class, base class for game objects
 * @ Author: Ryan Wang
 * @ Version: v2.0
 * September 2016
 */

public abstract class Object {

    protected int x;
    protected int y;

    protected Object(int x, int y) {
        this.x = x;
        this.y = y;
    }

    protected int getX() {
        return x;
    }
    protected int getY() {
        return y;
    }

}
