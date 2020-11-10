package com.sap.tankwar;

import com.sap.tankwar.frame.TankFrame;

public class Main
{
	public static void main(String[] args)
	{
		TankFrame tankFrame = new TankFrame();
		new Thread(() -> {
			while (true)
			{
				try
				{
					Thread.sleep(25);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				tankFrame.repaint();
			}

		}).start();

	}
}
