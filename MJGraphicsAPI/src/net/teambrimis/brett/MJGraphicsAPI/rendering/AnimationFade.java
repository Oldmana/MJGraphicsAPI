package net.teambrimis.brett.MJGraphicsAPI.rendering;

import net.teambrimis.brett.MJGraphicsAPI.components.MJComponent;
import net.teambrimis.brett.MJGraphicsAPI.components.props.Fadable;

public class AnimationFade extends Animation
{
	public AnimationFade(Fadable component, int interval, int maxStage)
	{
		super((MJComponent) component, interval, maxStage);
	}

	@Override
	public void tick()
	{
		((Fadable) getComponent()).fade();
		getComponent().requestRepaint();
	}

	@Override
	public void complete() {}
}
