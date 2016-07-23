package net.teambrimis.brett.MJGraphicsAPI.components.listeners;

import net.teambrimis.brett.MJGraphicsAPI.components.listeners.events.MouseEvent;

public abstract class MousePressListener implements Listener
{
	public static String EVENT_ID = "MOUSE_PRESS";
	
	public abstract void onMousePress(MouseEvent event);
	
	public String getName()
	{
		return EVENT_ID;
	}
}
