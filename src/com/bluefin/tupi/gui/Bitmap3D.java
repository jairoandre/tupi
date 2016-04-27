package com.bluefin.tupi.gui;

import com.bluefin.tupi.Game;

/**
 * Created by jairo on 27/04/2016.
 */
public class Bitmap3D extends Bitmap {
    public Bitmap3D(int width, int height) {
        super(width, height);
    }

    public void render(Game game) {
        for (int y = 0; y < height; y++) {
            double yd = ((y + 0.5) - height / 2.0) / height;

            if (yd < 0)
                yd = -yd;

            double z = 6 / yd;

            for (int x = 0; x < width; x++) {
                double xd = (x - width / 2.0) / height;
                xd *= z;
                int xx = (int) (xd + game.time) & 15;
                int zz = (int) (z + game.time) & 15;
                pixels[x + y * width] = (xx * 16) | (zz * 16) << 8;
            }
        }
    }
}
