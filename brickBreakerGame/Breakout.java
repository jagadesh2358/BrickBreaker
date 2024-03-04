package brickBreakerGame;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Breakout extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int PADDLE_WIDTH = 100;
    private static final int PADDLE_HEIGHT = 40;
    private static final int BALL_SIZE = 20;

    private Timer timer;
    private int paddleX;
    private int ballX, ballY;
    private int ballSpeedX, ballSpeedY;
    private int score;
    private ArrayList<Brick> bricks;

    public Breakout() {
        paddleX = WIDTH / 2 - PADDLE_WIDTH / 2;
        ballX = WIDTH / 2 - BALL_SIZE / 2;
        ballY = HEIGHT - 50;
        ballSpeedX = 4;
        ballSpeedY = -4;
        score = 0;

        bricks = new ArrayList<>();
        Color[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.ORANGE};
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                bricks.add(new Brick(j * 60 + 100, i * 30 + 100, colors[i])); // Assigning different colors to bricks
            }
        }

        setFocusable(true); // Ensure the panel receives keyboard input

        // Use KeyBindings for paddle movement
        getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "moveLeft");
        getActionMap().put("moveLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (paddleX > 0) {
                    paddleX -= 30; // Increase paddle speed to 30 pixels per frame
                }
            }
        });

        getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "moveRight");
        getActionMap().put("moveRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (paddleX < WIDTH - PADDLE_WIDTH) {
                    paddleX += 30; // Increase paddle speed to 30 pixels per frame
                }
            }
        });

        timer = new Timer(1000 / 60, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.setColor(Color.WHITE);
        g.fillRect(paddleX, HEIGHT - PADDLE_HEIGHT, PADDLE_WIDTH, PADDLE_HEIGHT);

        g.setColor(Color.YELLOW);
        g.fillOval(ballX, ballY, BALL_SIZE, BALL_SIZE);

        for (Brick brick : bricks) {
            brick.draw(g);
        }

        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 10, 20);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ballX += ballSpeedX;
        ballY += ballSpeedY;

        if (ballX < 0 || ballX + BALL_SIZE > WIDTH) {
            ballSpeedX = -ballSpeedX;
        }
        if (ballY < 0) {
            ballSpeedY = -ballSpeedY;
        }
        if (ballY + BALL_SIZE > HEIGHT) {
            // Game over when ball goes beyond the bottom of the screen
            gameOver();
            return; // Exit the actionPerformed method to stop game updates
        }

        if (ballX + BALL_SIZE > paddleX && ballX < paddleX + PADDLE_WIDTH && ballY + BALL_SIZE > HEIGHT - PADDLE_HEIGHT) {
            ballSpeedY = -4;
        }

        for (int i = 0; i < bricks.size(); i++) {
            Brick brick = bricks.get(i);
            if (ballX + BALL_SIZE > brick.getX() && ballX < brick.getX() + Brick.WIDTH && ballY + BALL_SIZE > brick.getY() && ballY < brick.getY() + Brick.HEIGHT) {
                bricks.remove(i);
                score += 10;
                ballSpeedY = -ballSpeedY;
                break;
            }
        }

        if (bricks.isEmpty()) {
            // All bricks destroyed, player wins
            winGame();
            return; // Exit the actionPerformed method to stop game updates
        }

        repaint();
    }

    private void gameOver() {
        // Game over logic
        timer.stop();
        JOptionPane.showMessageDialog(this, "Game Over! Your score is: " + score);
        System.exit(0);
    }

    private void winGame() {
        // Win game logic
        timer.stop();
        JOptionPane.showMessageDialog(this, "Congratulations! You won the game with a score of " + score);
        System.exit(0);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Breakout");
        Breakout breakout = new Breakout();
        frame.add(breakout);
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}


