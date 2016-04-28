package com.bluefin.tupi.gui;

import com.bluefin.tupi.Art;
import com.bluefin.tupi.Game;

import java.util.Random;

/**
 * Created by jairo on 26/04/2016.
 */
public class Screen extends Bitmap {

    private static final int PANEL_HEIGHT = 8 * 3;
    private Bitmap testBitmap;
    private Bitmap gamePanel;
    private Bitmap3D viewport;

    public Screen(int width, int height) {
        super(width, height);
        gamePanel = new Bitmap(width, PANEL_HEIGHT);
        viewport = new Bitmap3D(width, height - PANEL_HEIGHT);
        testBitmap = Art.panel;
    }

    int time = 0;

    public void render(Game game) {
        time++;

        for (int i = 0; i < 100; i++) {
            //int x0 = (int) (Math.sin((System.currentTimeMillis() + i*10) % 2000 / 2000.0 * Math.PI * 2) * 100);
            //int y0 = (int) (Math.cos((System.currentTimeMillis() + i*10) % 2000 / 2000.0 * Math.PI * 2) * 60);
            int x0 = (game.time + i * 8) % 400 - 200;
            int y0 = 0;
            gamePanel.draw(testBitmap, (gamePanel.width - 64) / 2 + x0, (gamePanel.height - 64) / 2 + y0);
        }

        viewport.render(game);
        viewport.postProcess();
        draw(viewport, 0, 0);
        draw(gamePanel, 0, height - PANEL_HEIGHT);
        //draw(testBitmap, 40, 40);
    }
}
