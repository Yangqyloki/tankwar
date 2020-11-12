package com.sap.tankwar.items;

import com.sap.tankwar.enumeration.Camp;
import com.sap.tankwar.enumeration.Direction;
import com.sap.tankwar.frame.TankFrame;
import com.sap.tankwar.manager.ImageManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Bullet
{

	public static final int BULLET_WIDTH = ImageManager.bulletD.getWidth();
	public static final int BULLET_HEIGHT = ImageManager.bulletD.getHeight();
	private static final int SPEED = 10;


	private int x, y;
	private Direction direction;
	private TankFrame tankFrame;
	private Camp camp;
	//碰撞检查矩形
	private Rectangle rectangle;


	private boolean living = true;


	public Bullet(final int x, final int y, final Direction direction, Camp camp, final TankFrame tankFrame)
	{
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.tankFrame = tankFrame;
		this.camp = camp;
		rectangle = new Rectangle(x, y, BULLET_WIDTH, BULLET_HEIGHT);
	}


	public void paint(final Graphics g)
	{
		paintBullet(g);
		move();
	}

	/**
	 * 子弹绘制自己
	 */
	private void paintBullet(final Graphics g)
	{
		if (!living) tankFrame.getBullets().remove(this);
		switch (direction)
		{
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
	private void move()
	{
		switch (direction)
		{
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

		this.rectangle.x = x;
		this.rectangle.y = y;

		if (x < 0 || y < 0 || x > tankFrame.getWidth() || y > tankFrame.getHeight())
		{
			this.living = false;
		}

		collisionCheck();
	}

	/**
	 * 子弹碰撞检查
	 */
	private void collisionCheck()
	{

		List<Bullet> bullets = tankFrame.getBullets();
		List<Tank> tanks = new ArrayList<>();
		tanks.addAll(tankFrame.getTanks());

		//坦克子弹碰撞检查,不同阵营子弹坦克碰撞爆炸
		for (int i = 0; i < bullets.size(); i++)
		{
			for (int j = 0; j < tanks.size(); j++)
			{
				bullets.get(i).colliedWith(tanks.get(j));
			}
		}

		//子弹碰撞检查,碰到抵消
		for (int i = 0; i < bullets.size(); i++)
		{
			if (bullets.get(i) == this) continue;
			bullets.get(i).colliedWith(this);
		}
	}

	/**
	 * 子弹坦克碰撞逻辑
	 * 不同阵营才会爆炸
	 */
	public void colliedWith(final Tank tank)
	{
		if (this.camp != tank.getCamp() && this.rectangle.intersects(tank.getRectangle()))
		{
			this.die();
			tank.die();
		}
	}

	/**
	 * 子弹子弹碰撞检查
	 * 子弹会抵消
	 */
	public void colliedWith(final Bullet bullet)
	{
		if (this.rectangle.intersects(bullet.getRectangle()))
		{
			this.die();
			bullet.die();
		}
	}

	public void die()
	{
		this.living = false;
	}

	public Camp getCamp()
	{
		return camp;
	}

	public Rectangle getRectangle()
	{
		return rectangle;
	}
}
