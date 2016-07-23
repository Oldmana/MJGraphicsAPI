package net.teambrimis.brett.MJGraphicsAPI.components.listeners;

public abstract class MouseMoveListener implements Listener
{
	public static String EVENT_ID = "MOUSE_MOVE";
	
	public abstract void onMouseEntered();
	
	public abstract void onMouseExited();
	
	public abstract void onMouseDrag();
	
	@Override
	public String getName()
	{
		return EVENT_ID;
	}
}
