package net.teambrimis.brett.MJGraphicsAPI.rendering;

import net.teambrimis.brett.MJGraphicsAPI.components.MJComponent;

public class AnimationRotation extends Animation
{
	public AnimationRotation(MJComponent component, int interval, int maxStage)
	{
		super(component, interval, maxStage);
	}

	@Override
	public void tick()
	{
		getComponent().setRotation(360 * ((double) getStage() / (double) getMaxStage()));
		getComponent().requestRepaint();
	}

	@Override
	public void complete()
	{
		getComponent().requestRepaint();
	}
}
