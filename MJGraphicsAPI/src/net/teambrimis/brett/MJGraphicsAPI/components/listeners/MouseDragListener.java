package net.teambrimis.brett.MJGraphicsAPI.components.listeners;

import net.teambrimis.brett.MJGraphicsAPI.components.listeners.events.MouseEvent;

public abstract class MouseDragListener implements Listener
{
	public static String EVENT_ID = "MOUSE_DRAG";
	
	public abstract void onMouseDrag(MouseEvent event);
	
	@Override
	public String getName()
	{
		return EVENT_ID;
	}
}
