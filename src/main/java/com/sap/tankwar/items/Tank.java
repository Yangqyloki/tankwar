package com.sap.tankwar.items;

import com.sap.tankwar.enumeration.Camp;
import com.sap.tankwar.enumeration.Direction;
import com.sap.tankwar.frame.TankFrame;
import com.sap.tankwar.manager.AudioManager;
import com.sap.tankwar.manager.ImageManager;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class Tank
{
	public static final int TANK_WIDTH = ImageManager.redTankD.getWidth();
	public static final int TANK_HEIGHT = ImageManager.redTankD.getHeight();
	private static final int SPEED = 3;

	private int x, y;
	private Direction direction;
	private TankFrame tankFrame;
	private Random random = new Random();
	private Camp camp;
	//碰撞检查矩形
	private Rectangle rectangle;

	private boolean moving;
	private boolean living = true;


	public Tank(final int x, final int y, final Direction direction, Camp camp, Boolean moving, final TankFrame tankFrame)
	{
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.tankFrame = tankFrame;
		this.camp = camp;
		this.moving = moving;
		rectangle = new Rectangle(x, y, TANK_WIDTH, TANK_HEIGHT);
	}


	public void paint(final Graphics g)
	{
		paintTank(g);
		move();
	}


	/**
	 * 绘制自己
	 */
	private void paintTank(final Graphics g)
	{
		if (!living)
		{
			if (tankFrame.getEnemyTanks().contains(this)) tankFrame.getEnemyTanks().remove(this);
		}
		else
		{
			switch (direction)
			{
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

	}

	/**
	 * 坦克移动
	 */
	private void move()
	{
		if (!moving) return;

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

		//敌方坦克开火,移动，转向
		if (camp == Camp.RED)
		{
			if (random.nextInt(100) > 95)
			{
				randomDir();
			}
			if (random.nextInt(100) > 95)
			{

				fire();
			}

		}

		boundsCheck();
		collisionCheck();
		//主坦克移动音效
//        if (this.camp == Camp.BLUE) new Thread(() -> new AudioManager("audio/tank_move.wav").play()).start();
	}


	/**
	 * 坦克开火
	 */
	public void fire()
	{
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
	public void die()
	{
		this.living = false;
		//计算爆炸位置
		int ex = this.x + Tank.TANK_WIDTH / 2 - Explode.EXPLODE_WIDTH / 2;
		int ey = this.y + Tank.TANK_HEIGHT / 2 - Explode.EXPLODE_HEIGHT / 2;
		Explode explode = new Explode(ex, ey, this.tankFrame);
		this.tankFrame.getExplodes().add(explode);
	}


	/**
	 * 随机转向
	 */
	private void randomDir()
	{
		this.direction = Direction.values()[random.nextInt(4)];
	}


	/**
	 * 墙体碰撞检查
	 */
	private void boundsCheck()
	{
		if (this.x < 0)
		{
			this.x = 0;
			this.reverse();
		}
		if (this.y < 30)
		{
			this.y = 30;
			this.reverse();
		}
		if (this.x + TANK_WIDTH > TankFrame.GAME_WIDTH)
		{
			this.x = TankFrame.GAME_WIDTH - TANK_WIDTH;
			this.reverse();
		}
		if (this.y + TANK_HEIGHT > TankFrame.GAME_HEIGHT)
		{
			this.y = TankFrame.GAME_HEIGHT - TANK_HEIGHT;
			this.reverse();
		}

		this.rectangle.x = x;
		this.rectangle.y = y;
	}

	/**
	 * 坦克调头
	 */
	private void reverse()
	{
		switch (direction)
		{
			case UP:
				this.direction = Direction.DOWN;
				this.y += SPEED;
				break;
			case LEFT:
				this.direction = Direction.RIGHT;
				this.x += SPEED;
				break;
			case DOWN:
				this.direction = Direction.UP;
				this.y -= SPEED;
				break;
			case RIGHT:
				this.direction = Direction.LEFT;
				this.x -= SPEED;
				break;
			default:
				break;
		}
		this.rectangle.x = x;
		this.rectangle.y = y;
	}

	/**
	 * 坦克坦克碰撞检查
	 */
	private void collisionCheck()
	{
		List<Tank> enemyTanks = tankFrame.getEnemyTanks();
		//敌方坦克碰撞检查，碰到调头
		for (int i = 0; i < enemyTanks.size(); i++)
		{
			if (enemyTanks.get(i) == this) continue;
			enemyTanks.get(i).colliedWith(this);
		}
	}


	/**
	 * 坦克坦克碰撞逻辑
	 */
	private void colliedWith(Tank tank)
	{
		//同阵营调头
		if (this.camp == tank.camp)
		{
			if (this.rectangle.intersects(tank.rectangle))
			{
				this.reverse();
				tank.reverse();
			}
		}
		//不同阵营爆炸
		else
		{
			if (this.rectangle.intersects(tank.rectangle))
			{
				//主战坦克无敌开关
				this.die();
				tank.die();
			}
		}
	}

	public void setDirection(final Direction direction)
	{
		this.direction = direction;
	}

	public void setMoving(final boolean moving)
	{
		this.moving = moving;
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
