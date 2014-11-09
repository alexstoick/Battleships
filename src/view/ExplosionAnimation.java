package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

/**
 * Class for explosion animation.
 */
public class ExplosionAnimation {

    /**
     * Cell to be animated.
     */
    private SquareView cell;
    /**
     * Board to be repainted.
     */
    private BoardView board;
    /**
     * Explosion images.
     */
    private ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
    /**
     * Index of current explosion image in the list.
     */
    private int currentIndex;

    /**
     * Constructs animation.
     * @param cell cell where the explosion should be drawn.
     * @param board BoardView to be repainted.
     */
    public ExplosionAnimation(SquareView cell, BoardView board) {
        File[] icons = new File("resources/animation").listFiles();
        for (File file : icons) {
            try {
                String filename = file.getName();
                if (filename.startsWith("explosion")
                        && filename.endsWith(".png")) {
                    images.add(ImageIO.read(file));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.cell = cell;
        this.board = board;
        currentIndex = 0;
        cell.setExplosionImage(images.get(currentIndex));
        board.repaint();
    }

    /**
     * Starts animating the explosion.
     */
    public void start() {
        final Timer t = new Timer(10, null);
        t.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                currentIndex++;
                if (currentIndex == images.size() + 25) {
                    cell.setExplosionImage(null);
                    t.stop();
                } else if (currentIndex < images.size()) {
                    cell.setExplosionImage(images.get(currentIndex));
                }
                board.repaint();
            }
        });
        t.start();
    }
}
