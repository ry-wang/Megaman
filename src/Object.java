/**
 * @ Description: Object class, base class for game objects
 * @ Author: Ryan Wang
 * @ Version: v2.0
 * September 2016
 */

public abstract class Object {

    protected int x;
    protected int y;

    public Object(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

}
