package com.sap.tankwar.listener;

import com.sap.tankwar.enumeration.Direction;
import com.sap.tankwar.items.Tank;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TankKeyListener extends KeyAdapter
{
	private Tank mainTank;

	private boolean isUp = false;
	private boolean isLeft = false;
	private boolean isDown = false;
	private boolean isRight = false;

	public TankKeyListener(Tank mainTank)
	{
		super();
		this.mainTank = mainTank;
	}

	@Override
	public void keyPressed(final KeyEvent e)
	{
		int keyCode = e.getKeyCode();
		switch (keyCode)
		{
			case KeyEvent.VK_UP:
				isUp = true;
				setMainTankDir();
				break;
			case KeyEvent.VK_LEFT:
				isLeft = true;
				setMainTankDir();
				break;
			case KeyEvent.VK_DOWN:
				isDown = true;
				setMainTankDir();
				break;
			case KeyEvent.VK_RIGHT:
				isRight = true;
				setMainTankDir();
				break;
			default:
				break;
		}
	}


	@Override
	public void keyReleased(final KeyEvent e)
	{
		int keyCode = e.getKeyCode();
		switch (keyCode)
		{
			case KeyEvent.VK_UP:
				isUp = false;
				setMainTankDir();
				break;
			case KeyEvent.VK_LEFT:
				isLeft = false;
				setMainTankDir();
				break;
			case KeyEvent.VK_DOWN:
				isDown = false;
				setMainTankDir();
				break;
			case KeyEvent.VK_RIGHT:
				isRight = false;
				setMainTankDir();
				break;
			case KeyEvent.VK_SPACE:
				mainTank.fire();
				break;
			default:
				break;
		}
	}

	private void setMainTankDir()
	{
		if (!isUp && !isLeft && !isDown && !isRight)
		{
			mainTank.setMoving(false);

		}
		else
		{
			if (isUp) mainTank.setDirection(Direction.UP);
			if (isLeft) mainTank.setDirection(Direction.LEFT);
			if (isDown) mainTank.setDirection(Direction.DOWN);
			if (isRight) mainTank.setDirection(Direction.RIGHT);
			mainTank.setMoving(true);
		}
	}


}
