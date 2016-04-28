package com.bluefin.tupi.gui;

import com.bluefin.tupi.Art;
import com.bluefin.tupi.Game;

import java.util.Random;

/**
 * Created by jairo on 27/04/2016.
 */
public class Bitmap3D extends Bitmap {
    private double[] zBuffer;

    public Bitmap3D(int width, int height) {
        super(width, height);
        zBuffer = new double[width * height];
    }

    public void render(Game game) {

        double xCam = 0;
        double yCam = game.time % 100 / 100.0;
        double zCam = 0;

        double rot = Math.sin(game.time / 100.0) * 0.2;

        double rCos = Math.cos(rot);
        double rSin = Math.sin(rot);


        for (int y = 0; y < height; y++) {
            double yd = ((y + 0.5) - height / 2.0) / height;

            double zd = (4 + zCam) / yd;

            if (yd < 0)
                zd = (4 - zCam) / -yd;


            for (int x = 0; x < width; x++) {
                double xd = (x - width / 2.0) / height;
                xd *= zd;
                double xx = xd * rCos + zd * rSin + (xCam + 0.5) * 8;
                double yy = zd * rCos - xd * rSin + (yCam) * 8;

                int xPix = (int) (xx);
                int yPix = (int) (yy);

                if (xx < 0) xPix--;
                if (yy < 0) yPix--;

                zBuffer[x + y * width] = zd;
                pixels[x + y * width] = Art.floors.pixels[(xPix & 7) + (yPix & 7) * 64];
            }
        }

        Random random = new Random(100);

        for (int i = 0; i < 1000; i++) {
            double x = random.nextDouble() * 2 - 1;
            double y = random.nextDouble() * 2 - 1;
            double z = 2;

            double xx = x * rCos - z * rSin - xCam;
            double zz = z * rCos + x * rSin - yCam;
            double yy = y;

            int xPixel = (int) (xx / zz * height + width / 2);
            int yPixel = (int) (yy / zz * height + height / 2);

            if (xPixel >= 0 && yPixel >= 0 && xPixel < width && yPixel < height) {
                zBuffer[xPixel + yPixel * width] = zz;
                pixels[xPixel + yPixel * width] = 0xff00ff;
            }

        }
    }

    public void postProcess() {
        for (int i = 0; i < width * height; i++) {
            int col = pixels[i];
            int brightness = (int) (20000 / (zBuffer[i] * zBuffer[i]));

            brightness = brightness > 255 ? 255 : brightness;

            int r = (col >> 16) & 0xff;
            int g = (col >> 8) & 0xff;
            int b = (col) & 0xff;

            r = r * brightness / 255;
            g = g * brightness / 255;
            b = b * brightness / 255;

            pixels[i] = r << 16 | g << 8 | b;

        }
    }
}
