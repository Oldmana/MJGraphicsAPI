package net.teambrimis.brett.MJGraphicsAPI.components.listeners;

import net.teambrimis.brett.MJGraphicsAPI.components.listeners.events.MouseWheelEvent;

public abstract class MouseWheelListener implements Listener
{
	public static String EVENT_ID = "MOUSE_WHEEL";
	
	public abstract void onMouseWheel(MouseWheelEvent event);
	
	@Override
	public String getName()
	{
		return EVENT_ID;
	}
}
