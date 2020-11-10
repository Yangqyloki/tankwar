package com.sap.tankwar.items;

import com.sap.tankwar.frame.TankFrame;
import com.sap.tankwar.manager.AudioManager;
import com.sap.tankwar.manager.ImageManager;

import java.awt.*;

public class Explode {
    public static final int BULLET_WIDTH = ImageManager.explodes[0].getWidth();
    public static final int BULLET_HEIGHT = ImageManager.explodes[0].getHeight();
    private static final int SPEED = 15;


    private int x, y;
    private TankFrame tankFrame;


    //爆炸步骤
    private int step = 0;

    public Explode(final int x, final int y, final TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.tankFrame = tankFrame;
        new Thread(() -> new AudioManager("audio/explode.wav").play()).start();
    }


    public void paint(final Graphics g) {
        paintExplode(g);
    }

    /**
     * 绘制爆炸效果
     */
    private void paintExplode(final Graphics g) {
        g.drawImage(ImageManager.explodes[step++], x, y, null);
        if (step >= ImageManager.explodes.length) {
            tankFrame.getExplodes().remove(this);
        }

        //paintExplode(g);

    }


}
