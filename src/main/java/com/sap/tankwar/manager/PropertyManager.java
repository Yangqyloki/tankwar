package com.sap.tankwar.manager;

import java.io.IOException;
import java.util.Properties;

public class PropertyManager
{
	public static Properties properties = new Properties();

	static
	{
		try
		{
			properties.load(PropertyManager.class.getClassLoader().getResourceAsStream("configuration.properties"));
		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	public static Object get(String key)
	{
		if (properties == null) return null;
		return properties.get(key);
	}

	public static int getInt(String key)
	{
		if (properties == null) return 0;
		return Integer.parseInt((String) properties.get(key));
	}

}
