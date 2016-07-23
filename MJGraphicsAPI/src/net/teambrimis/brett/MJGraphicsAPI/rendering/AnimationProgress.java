package net.teambrimis.brett.MJGraphicsAPI.rendering;

import net.teambrimis.brett.MJGraphicsAPI.components.MJComponent;
import net.teambrimis.brett.MJGraphicsAPI.components.props.Progressable;

public class AnimationProgress extends Animation
{

	public AnimationProgress(MJComponent component, int interval, int maxStage)
	{
		super(component, interval, maxStage);
	}

	@Override
	public void tick()
	{
		((Progressable) getComponent()).progress();
		getComponent().requestRepaint();
	}

	@Override
	public void complete()
	{
		// TODO Auto-generated method stub
		
	}

}
