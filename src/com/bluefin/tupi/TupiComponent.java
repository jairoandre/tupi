package com.bluefin.tupi;

import com.bluefin.tupi.gui.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

/**
 * Created by jairo on 26/04/2016.
 */
public class TupiComponent extends Canvas implements Runnable {

    private static final int WIDTH = 160;
    private static final int HEIGHT = 120;
    private static final int SCALE = 4;

    private boolean running;
    private Thread thread;

    private Game game;
    private Screen screen;
    private BufferedImage img;
    private int[] pixels;


    public TupiComponent() {
        Dimension size = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);

        game = new Game();
        screen = new Screen(WIDTH, HEIGHT);

        img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

        pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
    }

    public synchronized void start() {
        if (running) return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {
        if (!running) return;
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        int frames = 0;
        double unprocessedSeconds = 0;
        long lastTime = System.nanoTime();
        double secondsPerTick = 1 / 60.0;
        int tickCount = 0;

        while (running) {
            long now = System.nanoTime();
            long passedTime = now - lastTime;
            lastTime = now;
            if (passedTime < 0) passedTime = 0;
            if (passedTime > 100000000) passedTime = 100000000;

            unprocessedSeconds += passedTime / 1000000000.0;

            boolean ticked = false;
            while (unprocessedSeconds > secondsPerTick) {
                tick();
                unprocessedSeconds -= secondsPerTick;
                ticked = true;
                tickCount++;
                if (tickCount % 60 == 0) {
                    System.out.println(frames + " fps");
                    lastTime += 1000;
                    frames = 0;
                }
            }

            // if (ticked) {
                render();
                frames++;
            // }



        }
    }

    private void tick() {
        game.tick();
    }

    private void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        screen.render(game);

        for (int i = 0; i < WIDTH * HEIGHT; i++) {
            pixels[i] = screen.pixels[i];
        }

        Graphics g = bs.getDrawGraphics();
        g.drawImage(img, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
        g.dispose();
        bs.show();
    }


    public static void main(String[] args) {
        TupiComponent game = new TupiComponent();
        JFrame frame = new JFrame("TupiComponent");
        frame.add(game);
        frame.pack();
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        game.start();
    }


}
