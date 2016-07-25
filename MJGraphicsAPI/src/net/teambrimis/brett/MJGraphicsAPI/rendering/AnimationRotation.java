package net.teambrimis.brett.MJGraphicsAPI.rendering;

import net.teambrimis.brett.MJGraphicsAPI.components.MJComponent;

public class AnimationRotation extends Animation
{
	public AnimationRotation(MJComponent component, int interval, int maxStage)
	{
		super(component, interval);
	}

	@Override
	public void tick()
	{
		getComponent().setRotation(360 * ((double) getState() / (double) getMax()));
		getComponent().requestRepaint();
	}

	@Override
	public void complete()
	{
		getComponent().requestRepaint();
	}
}
