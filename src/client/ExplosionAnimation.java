package client;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by user on 13.10.2014.
 */
public class ExplosionAnimation {

    private CellView cell;
    private BoardView board;
    private ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
    private int currentIndex;

    public ExplosionAnimation(CellView cell, BoardView board) {
        File[] icons = new File("resources/animation").listFiles();
        for (File f : icons) {
            try {
                images.add(ImageIO.read(f));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.cell = cell;
        this.board = board;
        currentIndex = 0;
        cell.setExplosionImage(images.get(currentIndex));
        board.repaintRoot();
    }

    public void start() {
        final Timer t = new Timer(10, null);
        t.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                currentIndex++;
                if (currentIndex == images.size()) {
                    try {
                        Thread.sleep(500);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    cell.setExplosionImage(null);
                    t.stop();
                } else {
                    cell.setExplosionImage(images.get(currentIndex));
                }
                board.repaintRoot();
            }
        });

        t.start();
    }
}
