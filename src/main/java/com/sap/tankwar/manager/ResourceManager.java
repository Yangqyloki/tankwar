package com.sap.tankwar.manager;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ResourceManager
{
	//预加载四个方向的Tank,Bullet图片
	public static BufferedImage tankU, tankL, tankD, tankR;
	public static BufferedImage bulletU, bulletL, bulletD, bulletR;
	public static BufferedImage[] explodes = new BufferedImage[16];

	static
	{
		try
		{
			tankU = ImageIO.read(ResourceManager.class.getClassLoader().getResourceAsStream("images/tankU.gif"));
			tankL = ImageIO.read(ResourceManager.class.getClassLoader().getResourceAsStream("images/tankL.gif"));
			tankD = ImageIO.read(ResourceManager.class.getClassLoader().getResourceAsStream("images/tankD.gif"));
			tankR = ImageIO.read(ResourceManager.class.getClassLoader().getResourceAsStream("images/tankR.gif"));

			bulletU = ImageIO.read(ResourceManager.class.getClassLoader().getResourceAsStream("images/bulletU.gif"));
			bulletL = ImageIO.read(ResourceManager.class.getClassLoader().getResourceAsStream("images/bulletL.gif"));
			bulletD = ImageIO.read(ResourceManager.class.getClassLoader().getResourceAsStream("images/bulletD.gif"));
			bulletR = ImageIO.read(ResourceManager.class.getClassLoader().getResourceAsStream("images/bulletR.gif"));

			for (int i = 0; i < 16; i++)
			{
				explodes[i] = ImageIO.read(ResourceManager.class.getClassLoader().getResourceAsStream("images/e" + (i + 1) + ".gif"));
			}

		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}
