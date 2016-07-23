package net.teambrimis.brett.MJGraphicsAPI.components;

import java.awt.Color;
import java.awt.Graphics2D;

import net.teambrimis.brett.MJGraphicsAPI.MJGraphicsWindow;
import net.teambrimis.brett.MJGraphicsAPI.components.props.Progressable;
import net.teambrimis.brett.MJGraphicsAPI.rendering.AnimationProgress;

public class MJProgressBar extends MJComponent implements Progressable
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
	
	public void setValue(int v)
	{
		value = v;
		if (!hasAnimation(AnimationProgress.class))
		{
			addAnimation(new AnimationProgress(this, 50, 0));
		}
	}
	
	@Override
	public void paint(Graphics2D g)
	{
		g.setColor(Color.GREEN);
		g.fillRect(1, 1, (int) Math.ceil((getScaledWidth() - 2) * ((double) (visibleValue - min) / (max - min))), getScaledHeight() - 2);
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, getScaledWidth() - 1, getScaledHeight() - 1);
	}

	@Override
	public void progress()
	{
		visibleValue = visibleValue + (int) Math.ceil(((double) value - visibleValue) / 4);
		if (value == visibleValue)
		{
			removeAnimation(AnimationProgress.class);
		}
	}
}
