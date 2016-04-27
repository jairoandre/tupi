package com.bluefin.tupi.gui;

/**
 * Created by jairo on 27/04/2016.
 */
public class Bitmap3D extends Bitmap {
    public Bitmap3D(int width, int height) {
        super(width, height);
    }

    public void renderFloor() {
        for (int y = 0; y < height; y++) {
            double yd = y - height / 2;
            double z = 1000 / yd;

            for (int x = 0; x < width; x++) {
                double xd = (x - width / 2);
                xd *= z;
                int xx = (int) (xd) & 15;
                int zz = (int) (z) & 15;
                pixels[x + y * width] = xx * 16 + zz * 16 * 256;
            }
        }
    }
}
