package net.teambrimis.brett.MJGraphicsAPI.rendering;

import net.teambrimis.brett.MJGraphicsAPI.components.MJComponent;
import net.teambrimis.brett.MJGraphicsAPI.utils.Scaling;

public class AnimationMove extends Animation
{
	private int startX;
	private int startY;
	
	private int destX;
	private int destY;
	
	public AnimationMove(MJComponent component, int interval, int maxStage, int destX, int destY)
	{
		super(component, interval, maxStage);
		
		startX = component.getX();
		startY = component.getY();
		
		this.destX = destX;
		this.destY = destY;
	}
	
	private int diffX()
	{
		return destX - startX;
	}
	
	private int diffY()
	{
		return destY - startY;
	}
	
	@Override
	public void tick()
	{
		getComponent().setScaledX(Scaling.scale(startX) + (int) Math.round(Scaling.scale(diffX()) * ((double) getStage() / (double) getMaxStage())));
		getComponent().setScaledY(Scaling.scale(startY) + (int) Math.round(Scaling.scale(diffY()) * ((double) getStage() / (double) getMaxStage())));
		getComponent().requestRepaint();
	}
	
	@Override
	public void complete()
	{
		
	}
}
