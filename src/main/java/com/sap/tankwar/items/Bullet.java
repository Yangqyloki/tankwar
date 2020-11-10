package com.sap.tankwar.items;

import com.sap.tankwar.enumeration.Camp;
import com.sap.tankwar.enumeration.Direction;
import com.sap.tankwar.frame.TankFrame;
import com.sap.tankwar.manager.ImageManager;

import java.awt.*;

public class Bullet {

    public static final int BULLET_WIDTH = ImageManager.bulletD.getWidth();
    public static final int BULLET_HEIGHT = ImageManager.bulletD.getHeight();
    private static final int SPEED = 15;


    private int x, y;
    private Direction direction;
    private TankFrame tankFrame;
    private Camp camp;


    private boolean living = true;

    public Bullet(final int x, final int y, final Direction direction, Camp camp, final TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.tankFrame = tankFrame;
        this.camp = camp;
    }


    public void paint(final Graphics g) {
        paintBullet(g);
        move();
    }

    /**
     * 子弹绘制自己
     */
    private void paintBullet(final Graphics g) {
        if (!living) tankFrame.getBullets().remove(this);
        switch (direction) {
            case UP:
                g.drawImage(ImageManager.bulletU, x, y, null);
                break;
            case LEFT:
                g.drawImage(ImageManager.bulletL, x, y, null);
                break;
            case DOWN:
                g.drawImage(ImageManager.bulletD, x, y, null);
                break;
            case RIGHT:
                g.drawImage(ImageManager.bulletR, x, y, null);
                break;
        }
    }

    /**
     * 子弹移动
     */
    private void move() {
        switch (direction) {
            case UP:
                y -= SPEED;
                break;
            case LEFT:
                x -= SPEED;
                break;
            case DOWN:
                y += SPEED;
                break;
            case RIGHT:
                x += SPEED;
                break;
            default:
                break;
        }

        if (x < 0 || y < 0 || x > tankFrame.getWidth() || y > tankFrame.getHeight()) {
            this.living = false;
        }
    }

    public void die() {
        this.living = false;
    }

    public void colliedWith(final Tank tank) {
        if (this.camp == tank.getCamp()) return;
        //用矩形类辅助判断坦克和子弹是否相撞
        Rectangle bulletRect = new Rectangle(this.x, this.y, BULLET_WIDTH, BULLET_HEIGHT);
        Rectangle tankRect = new Rectangle(tank.getX(), tank.getY(), Tank.TANK_WIDTH, Tank.TANK_WIDTH);

        if (bulletRect.intersects(tankRect)) {
            tank.die();
            this.die();
            Explode explode = new Explode(tank.getX(), tank.getY(), this.tankFrame);
            this.tankFrame.getExplodes().add(explode);
        }
    }


}
