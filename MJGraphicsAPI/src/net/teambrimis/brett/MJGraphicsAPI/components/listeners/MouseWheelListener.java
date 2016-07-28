package net.teambrimis.brett.MJGraphicsAPI.components.listeners;

public abstract class MouseWheelListener implements Listener
{
	public static String EVENT_ID = "MOUSE_WHEEL";
	
	
	
	@Override
	public String getName()
	{
		return EVENT_ID;
	}
}
