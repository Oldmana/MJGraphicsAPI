package net.teambrimis.brett.MJGraphicsAPI.components.listeners;

import net.teambrimis.brett.MJGraphicsAPI.components.MJComponent;

public abstract class FocusChangeListener implements Listener
{
	public static String EVENT_ID = "FOCUS_CHANGE";
	
	public abstract void onFocusChange(MJComponent previous, MJComponent next);
	
	@Override
	public String getName()
	{
		return EVENT_ID;
	}
}
