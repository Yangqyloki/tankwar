package com.sap.tankwar.frame;

import com.sap.tankwar.enumeration.Camp;
import com.sap.tankwar.enumeration.Direction;
import com.sap.tankwar.items.Bullet;
import com.sap.tankwar.items.Explode;
import com.sap.tankwar.items.Tank;
import com.sap.tankwar.listener.TankKeyListener;
import com.sap.tankwar.listener.TankWindowListener;
import com.sap.tankwar.manager.PropertyManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TankFrame extends Frame
{
	public static final int GAME_WIDTH = PropertyManager.getInt("gameWidth");
	public static final int GAME_HEIGHT = PropertyManager.getInt("gameHeight");
	public static final int INIT_TANK_COUNT = PropertyManager.getInt("initTankCount");

	private Image offScreenImage;
	private Tank mainTank = new Tank(500, 600, Direction.DOWN, Camp.BLUE, false, this);

	private List<Bullet> bullets = new ArrayList<>();
	private List<Tank> tanks = new ArrayList<>();
	private List<Explode> explodes = new ArrayList<>();

	public TankFrame()
	{
		this.setVisible(true);
		this.setResizable(false);
		this.setTitle(PropertyManager.getString("gameInfo"));
		this.setSize(GAME_WIDTH, GAME_HEIGHT);
		this.addWindowListener(new TankWindowListener());
		this.addKeyListener(new TankKeyListener(mainTank));

		//初始化主战坦克
		tanks.add(mainTank);

		//初始化敌方坦克
		for (int i = 0; i < INIT_TANK_COUNT; i++)
		{
			tanks.add(new Tank(50 + i * 120, 200, Direction.values()[new Random().nextInt(4)], Camp.RED, true, this));
		}

	}

	/**
	 * 用二次缓冲解决闪烁问题
	 */
	@Override
	public void update(Graphics g)
	{
		if (offScreenImage == null)
		{
			offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
		}
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.BLACK);
		gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}

	/**
	 * 绘制坦克和子弹
	 */
	@Override
	public void paint(Graphics g)
	{
		//打印战场信息
		paintInfo(g);

		//打印所有坦克
		//如果用for(Bullet b:bullets)等List迭代器循环，print方法里remove时会报错
		for (int i = 0; i < tanks.size(); i++)
		{
			tanks.get(i).paint(g);
		}

		//打印所有子弹
		for (int i = 0; i < bullets.size(); i++)
		{
			bullets.get(i).paint(g);
		}

		//打印爆炸效果
		for (int i = 0; i < explodes.size(); i++)
		{
			explodes.get(i).paint(g);
		}


	}

	/**
	 * 绘制战场信息
	 */
	private void paintInfo(final Graphics g)
	{
		Color color = g.getColor();
		g.setColor(Color.WHITE);
		g.drawString("敌人数量: " + tanks.size(), 10, 60);
		g.drawString("子弹数量: " + bullets.size(), 10, 80);
		g.setColor(color);
	}

	public List<Bullet> getBullets()
	{
		return bullets;
	}

	public List<Tank> getTanks()
	{
		return tanks;
	}

	public List<Explode> getExplodes()
	{
		return explodes;
	}

}
