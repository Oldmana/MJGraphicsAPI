package net.teambrimis.brett.MJGraphicsAPI.components;

import java.awt.Color;
import java.awt.Graphics2D;

import net.teambrimis.brett.MJGraphicsAPI.MJGraphicsWindow;
import net.teambrimis.brett.MJGraphicsAPI.rendering.Animation;
import net.teambrimis.brett.MJGraphicsAPI.rendering.AnimationProgressBar;

public class MJProgressBar extends MJComponent
{
	private int min;
	private int max;
	
	private int value;
	private int visibleValue;
	
	public MJProgressBar(MJGraphicsWindow window, int x, int y, int width, int height, int layer, int min, int max)
	{
		super(window, x, y, width, height, layer);
		
		this.min = min;
		this.max = max;
		
		value = min;
		visibleValue = min;
	}
	
	public double getMin()
	{
		return min;
	}
	
	public double getMax()
	{
		return max;
	}
	
	public int getValue()
	{
		return value;
	}
	
	public void setValue(int v)
	{
		value = v;
		if (!hasAnimation(Animation.ID_PROGRESS_BAR))
		{
			addAnimation(new AnimationProgressBar(this, 50));
		}
	}
	
	public int getVisibleValue()
	{
		return visibleValue;
	}
	
	public void setVisibleValue(int value)
	{
		visibleValue = value;
	}
	
	@Override
	public void paint(Graphics2D g)
	{
		g.setColor(Color.GREEN);
		g.fillRect(1, 1, (int) Math.ceil((getScaledWidth() - 2) * ((double) (visibleValue - min) / (max - min))), getScaledHeight() - 2);
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, getScaledWidth() - 1, getScaledHeight() - 1);
	}
}
