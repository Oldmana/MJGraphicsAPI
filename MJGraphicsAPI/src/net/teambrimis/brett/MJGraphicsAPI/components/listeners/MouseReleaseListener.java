package net.teambrimis.brett.MJGraphicsAPI.components.listeners;

import net.teambrimis.brett.MJGraphicsAPI.components.listeners.events.MouseEvent;

public abstract class MouseReleaseListener implements Listener
{
	public static String EVENT_ID = "MOUSE_RELEASE";
	
	public abstract void onMousePress(MouseEvent e);
	
	public String getName()
	{
		return EVENT_ID;
	}
}
