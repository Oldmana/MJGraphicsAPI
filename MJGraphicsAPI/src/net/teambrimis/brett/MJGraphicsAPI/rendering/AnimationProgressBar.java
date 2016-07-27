package net.teambrimis.brett.MJGraphicsAPI.rendering;

import net.teambrimis.brett.MJGraphicsAPI.components.MJComponent;
import net.teambrimis.brett.MJGraphicsAPI.components.MJProgressBar;

public class AnimationProgressBar extends Animation
{

	public AnimationProgressBar(MJComponent component, int interval)
	{
		super(component, interval);
	}

	@Override
	public void tick()
	{
		MJProgressBar bar = (MJProgressBar) getComponent();
		if (bar.getValue() != bar.getVisibleValue())
		{
			bar.setVisibleValue(bar.getVisibleValue() + (int) Math.ceil(((double) bar.getValue() - bar.getVisibleValue()) / 4));
			bar.requestRepaint();
		}
	}

}
