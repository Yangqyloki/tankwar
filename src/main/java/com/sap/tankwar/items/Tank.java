package com.sap.tankwar.items;

import com.sap.tankwar.enumeration.Camp;
import com.sap.tankwar.enumeration.Direction;
import com.sap.tankwar.frame.TankFrame;
import com.sap.tankwar.manager.AudioManager;
import com.sap.tankwar.manager.ImageManager;

import java.awt.*;
import java.util.Random;

public class Tank {
    public static final int TANK_WIDTH = ImageManager.redTankD.getWidth();
    public static final int TANK_HEIGHT = ImageManager.redTankD.getHeight();
    private static final int SPEED = 1;

    private int x, y;
    private Direction direction;
    private TankFrame tankFrame;
    private Random random = new Random();
    private Camp camp;

    private boolean moving;
    private boolean living = true;


    public Tank(final int x, final int y, final Direction direction, Camp camp, Boolean moving, final TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.tankFrame = tankFrame;
        this.camp = camp;
        this.moving = moving;
    }


    public void paint(final Graphics g) {
        paintTank(g);
        move();
    }


    /**
     * 绘制自己
     */
    private void paintTank(final Graphics g) {
        if (!living && tankFrame.getEnemyTanks().contains(this)) tankFrame.getEnemyTanks().remove(this);
        switch (direction) {
            case UP:
                g.drawImage(this.camp == Camp.RED ? ImageManager.redTankU : ImageManager.blueTankU, x, y, null);
                break;
            case LEFT:
                g.drawImage(this.camp == Camp.RED ? ImageManager.redTankL : ImageManager.blueTankL, x, y, null);
                break;
            case DOWN:
                g.drawImage(this.camp == Camp.RED ? ImageManager.redTankD : ImageManager.blueTankD, x, y, null);
                break;
            case RIGHT:
                g.drawImage(this.camp == Camp.RED ? ImageManager.redTankR : ImageManager.blueTankR, x, y, null);
                break;
        }

    }

    /**
     * 坦克移动
     */
    private void move() {
        if (!moving) return;

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

        //敌方坦克开火
        if (camp == Camp.RED && random.nextInt(10) > 8) this.fire();

        //主坦克移动音效
//        if (this.camp == Camp.BLUE) new Thread(() -> new AudioManager("audio/tank_move.wav").play()).start();
    }


    /**
     * 坦克开火
     */
    public void fire() {
        //计算子弹在坦克中心的位置
        int bx = this.x + Tank.TANK_WIDTH / 2 - Bullet.BULLET_WIDTH / 2;
        int by = this.y + Tank.TANK_HEIGHT / 2 - Bullet.BULLET_HEIGHT / 2;

        Bullet bullet = new Bullet(bx, by, this.direction, this.camp, this.tankFrame);
        tankFrame.getBullets().add(bullet);

        //主坦克开火音效
        if (this.camp == Camp.BLUE) new Thread(() -> new AudioManager("audio/tank_fire.wav").play()).start();
    }

    /**
     * 坦克死亡
     */
    public void die() {
        this.living = false;
    }

    public void setDirection(final Direction direction) {
        this.direction = direction;
    }

    public void setMoving(final boolean moving) {
        this.moving = moving;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Camp getCamp() {
        return camp;
    }
}
