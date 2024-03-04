package brickBreakerGame;
//
//import java.awt.*;
//
//public class Brick {
//    public static final int WIDTH = 60;
//    public static final int HEIGHT = 30;
//
//    private int x;
//    private int y;
//
//    public Brick(int x, int y) {
//        this.x = x;
//        this.y = y;
//    }
//
//    public int getX() {
//        return x;
//    }
//
//    public int getY() {
//        return y;
//    }
//
//    public void draw(Graphics g) {
//        g.setColor(Color.BLUE);
//        g.fillRect(x, y, WIDTH, HEIGHT);
//    }
//}

import java.awt.*;

public class Brick {
    public static final int WIDTH = 60;
    public static final int HEIGHT = 30;

    private int x;
    private int y;
    private Color color;

    public Brick(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, WIDTH, HEIGHT);
    }
}
