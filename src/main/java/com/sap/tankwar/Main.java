package com.sap.tankwar;

import com.sap.tankwar.frame.TankFrame;
import com.sap.tankwar.manager.AudioManager;

public class Main {
    public static void main(String[] args) {
        TankFrame tankFrame = new TankFrame();
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(25);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                tankFrame.repaint();
            }

        }).start();

        new Thread(() -> new AudioManager("audio/war1.wav").loop()).start();
    }
}
