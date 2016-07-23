package net.teambrimis.brett.MJGraphicsAPI.components.listeners;

import net.teambrimis.brett.MJGraphicsAPI.components.listeners.events.KeyEvent;

public abstract class KeyReleaseListener implements Listener
{
	public static String EVENT_ID = "KEY_RELEASE";
	
	public abstract void onKeyRelease(KeyEvent event);
	
	public String getName()
	{
		return EVENT_ID;
	}
}
