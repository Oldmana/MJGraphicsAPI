package net.teambrimis.brett.MJGraphicsAPI.components.listeners;

import net.teambrimis.brett.MJGraphicsAPI.components.listeners.events.MouseEvent;

public abstract class MouseClickListener implements Listener
{
	public static String EVENT_ID = "MOUSE_CLICK";
	
	public abstract void onMouseClick(MouseEvent event);
	
	public String getName()
	{
		return EVENT_ID;
	}
}
