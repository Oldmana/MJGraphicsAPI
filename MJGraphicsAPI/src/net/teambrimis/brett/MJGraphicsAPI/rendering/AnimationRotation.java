package net.teambrimis.brett.MJGraphicsAPI.rendering;

import net.teambrimis.brett.MJGraphicsAPI.components.MJComponent;

public class AnimationRotation extends Animation
{
	public AnimationRotation(MJComponent component, int interval, int maxStage)
	{
		super(component, interval);
		setMax(maxStage);
	}

	@Override
	public void tick()
	{
		setState(Math.min(getMax(), getState() + 1));
		getComponent().setRotation(360 * ((double) getState() / (double) getMax()));
		getComponent().requestRepaint();
		if (getState() == getMax())
		{
			getComponent().removeAnimation(this);
		}
	}

	@Override
	public void complete()
	{
		getComponent().requestRepaint();
	}
}
