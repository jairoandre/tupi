package com.bluefin.tupi;

import com.bluefin.tupi.gui.Bitmap;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by jairo on 27/04/2016.
 */
public class Art {
    public static Bitmap floors = loadBitmap("/textures/floors.png");
    public static Bitmap panel = loadBitmap("/textures/crate.png");

    public static Bitmap loadBitmap(String fileName) {

        try {
            BufferedImage img = ImageIO.read(Art.class.getResource(fileName));

            int w = img.getWidth();
            int h = img.getHeight();

            Bitmap result =  new Bitmap(w, h);

            img.getRGB(0, 0, w, h, result.pixels, 0, w);

            for(int i = 0 ; i < w * h; i++){
                result.pixels[i] &= 0x00ffffff;
            }

            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
