package net.teambrimis.brett.MJGraphicsAPI.rendering;

import net.teambrimis.brett.MJGraphicsAPI.components.MJComponent;

public class AnimationHover extends Animation
{
	public AnimationHover(MJComponent component, int interval)
	{
		super(component, interval);
		setID(ID_HOVER);
	}
	
	public AnimationHover(MJComponent component, int interval, int min, int max, int change)
	{
		super(component, interval);
		setID(ID_HOVER);
	}

	@Override
	public void tick()
	{
		int prevState = getState();
		if (getComponent().isMouseHovering())
		{
			setState(Math.min(getMax(), getState() + getAddition()));
		}
		else
		{
			setState(Math.max(getMin(), getState() - getSubtraction()));
		}
		if (prevState != getState() && getComponent().isEnabled())
		{
			getComponent().requestRepaint();
		}
	}
	
}
