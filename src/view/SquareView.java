package view;

import model.Square;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.io.File;

/**
 * Class for view of the square.
 */
public class SquareView implements ChangeListener {

    public static final int CLEAR = 0;
    public static final int HOVER = 1;
    public static final int MISS = 2;
    public static final int HIT = 3;
    private int x;
    private int y;
    private int width;
    private int height;
    private int state;
    private Image explosionImage;
    private Image water;
    private Image splash;
    private BoardView boardView;
    private Square squareModel;

    /**
     * Constructs SquareView.
     *
     * @param x           x coordinate of the square.
     * @param y           y coordinate of the square.
     * @param width       width of the square.
     * @param height      height of the square.
     * @param boardView   parent board view.
     * @param squareModel model of the square.
     */
    public SquareView(int x, int y, int width, int height, BoardView boardView,
                      Square squareModel) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        state = CLEAR;
        this.boardView = boardView;
        this.squareModel = squareModel;
        squareModel.addChangeListener(this);
        try {
            water = ImageIO.read(new File("resources/water/water.png"));
            splash = ImageIO.read(new File("resources/water/splash.png"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Some files have been deleted.", "Fatal error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Returns current state of the square (One of the following: SquareView.HIT,
     * SquareView.MISS, SquareView.CLEAR, SquareView.HOVER).
     *
     * @return state of the square.
     */
    public int getState() {
        return state;
    }

    /**
     * Sets the state of the square.
     *
     * @param state state of the square.
     */
    public void setState(int state) {
        if (state < 0 || state > 3) {
            throw new IllegalArgumentException("Invalid state");
        }
        this.state = state;
    }

    /**
     * Sets explosion image to be drawn.
     *
     * @param explosionImage explosion image.
     */
    public void setExplosionImage(Image explosionImage) {
        this.explosionImage = explosionImage;
    }

    /**
     * Returns x coordinate of the square.
     *
     * @return x coordinate in the BoardView.
     */
    public int getX() {
        return x;
    }

    /**
     * Returns y coordinate of the square.
     *
     * @return y coordinate of the square in the BoardView.
     */
    public int getY() {
        return y;
    }

    /**
     * Checks if explosion animation is in progress.
     *
     * @return true if animation is in progress, false otherwise
     */
    public boolean animated() {
        return explosionImage != null;
    }

    /**
     * Paints background of the square.
     *
     * @param g Graphics.
     */
    public void paint(Graphics g) {
        g.drawImage(water, x, y, width, height, null);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);

        if (state == HOVER && !animated()) {
            g.setColor(Color.BLUE);
            g.fillRect(x, y, width, height);
        }
        if (state == MISS) {
            g.drawImage(splash, x, y, width, height, null);
        }
    }

    /**
     * Draws cross representing a hit.
     *
     * @param g Graphics.
     */
    public void drawCross(Graphics g) {
        final int padding = 5;
        g.setColor(Color.RED);
        g.drawLine(x + padding, y + padding, x + width - padding, y + height
                - padding);
        g.drawLine(x + width - padding, y + padding, x + padding, y + height
                - padding);
    }

    /**
     * Draws current explosion image.
     *
     * @param g Graphics.
     */
    public void drawExplosion(Graphics g) {
        if (explosionImage != null) {
            g.drawImage(explosionImage, x, y, width, height, null);
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        switch (squareModel.getState()) {
            case CONTAINS_SHIP:
                state = HIT;
                break;
            case NO_SHIP:
                state = MISS;
        }

        if (!boardView.getModel().isOwnBoard()) {
            if (state == SquareView.HIT) {
                new ExplosionAnimation(this, boardView).start();
            }
        }
        boardView.repaint();
    }

}
