package com.sap.tankwar.listener;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TankWindowListener extends WindowAdapter
{
	@Override
	public void windowClosing(final WindowEvent e)
	{
		System.exit(0);
	}
}
