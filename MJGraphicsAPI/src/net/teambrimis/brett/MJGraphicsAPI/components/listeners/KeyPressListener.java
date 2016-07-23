package net.teambrimis.brett.MJGraphicsAPI.components.listeners;

import net.teambrimis.brett.MJGraphicsAPI.components.listeners.events.KeyEvent;

public abstract class KeyPressListener implements Listener
{
	public static String EVENT_ID = "KEY_PRESS";
	
	public abstract void onKeyPress(KeyEvent event);
	
	public String getName()
	{
		return EVENT_ID;
	}
}
