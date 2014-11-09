package view;

import model.Ship;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Class for view of the ship.
 */
public class ShipView {
    private final int initialX;
    private final int initialY;
    private int length;
    private int squareSize;
    private int x;
    private int y;
    private boolean horizontal;
    private boolean selected;
    private Image horizontalImage;
    private Image verticalImage;
    private Ship model;

    /**
     * Consturcts ShipView.
     * @param length length of the ship.
     * @param squareSize size (width or height) of the cell.
     * @param x x coordinate of the ship.
     * @param y y coordinate of the ship.
     * @param model ship model.
     */
    public ShipView(int length, int squareSize, int x, int y, Ship model) {
        this.length = length;
        this.squareSize = squareSize;
        this.x = this.initialX = x;
        this.y = this.initialY = y;
        this.model = model;
        horizontal = true;
        selected = false;

        String filename = "resources/ships/" + model.getType().getName();
        try {
            horizontalImage = ImageIO.read(new File(filename + ".png"));
            verticalImage = ImageIO.read(new File(filename + "_v.png"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Some files have been deleted",
                    "Fatal error", JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        }
    }

    /**
     * Sets flag indicating whether ship is selected.
     * @param selected true if ship is selected, false otherwise.
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * Returns x coordinate of the ship.
     * @return x coordinate of the ship.
     */
    public int getX() {
        return x;
    }

    /**
     * Sets x coordinate of the ship.
     * @param x x coordinate of the ship
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Returns y coordinate of the ship.
     * @return y coordinate of the ship.
     */
    public int getY() {
        return y;
    }

    /**
     * Sets y coordinate of the ship.
     * @param y y coordinate of the ship.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Checks if given x and y coordinates are inside the ship (user has pressed on ship).
     * @param x x coordinate.
     * @param y y coordinate.
     * @return true if x and y are inside the ship.
     */
    public boolean has(int x, int y) {
        if (horizontal) {
            return this.x <= x && x <= this.x + length * squareSize
                    && this.y <= y && y <= this.y + squareSize;
        } else {
            return this.x <= x && x <= this.x + squareSize && this.y <= y
                    && y <= this.y + length * squareSize;
        }
    }

    /**
     * Puts ship back to initial position.
     */
    public void resetPosition() {
        horizontal = true;
        model.setVertical(false);
        setX(initialX);
        setY(initialY);
    }

    /**
     * Returns ship model
     * @return ship model.
     */
    public Ship getModel() {
        return model;
    }

    /**
     * Rotates ship.
     */
    public void rotate() {
        horizontal = !horizontal;
        model.setVertical(!horizontal);
    }

    /**
     * Paints ship.
     * @param g Graphics.
     */
    public void paint(Graphics g) {
        if (horizontal) {
            if (selected) {
                g.setColor(Color.GREEN);
                g.fillRect(x, y, length * squareSize, squareSize);
            }
            g.drawImage(horizontalImage, x, y, length * squareSize, squareSize,
                    null);
        } else {
            if (selected) {
                g.setColor(Color.GREEN);
                g.fillRect(x, y, squareSize, length * squareSize);
            }
            g.drawImage(verticalImage, x, y, squareSize, length * squareSize, null);
        }
    }
}
