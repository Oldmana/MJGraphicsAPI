package net.teambrimis.brett.MJGraphicsAPI.rendering;

import net.teambrimis.brett.MJGraphicsAPI.components.MJComponent;

public abstract class Animation
{
	private MJComponent component;
	
	private int ID;
	
	private int interval;
	
	private long startTime;
	private long lastTime;
	
	private boolean stopped = false;
	
	private int min = 0;
	private int max = 20;
	private int subtraction = 1;
	private int addition = 1;
	
	private int state = min;
	
	/**
	 * 
	 * @param component - Component being animated
	 * @param interval - In milliseconds
	 */
	public Animation(MJComponent component, int interval)
	{
		this.component = component;
		
		this.interval = interval;
		
		startTime = System.currentTimeMillis();
		lastTime = startTime;
	}
	
	public boolean shouldTick()
	{
		if (interval + lastTime <= System.currentTimeMillis())
		{
			lastTime += interval;
			return true;
		}
		return false;
	}
	
	public MJComponent getComponent()
	{
		return component;
	}
	
	// [MJ-1]
	public int getID()
	{
		return ID;
	}
	
	public void setID(int ID)
	{
		this.ID = ID;
	}
	// End
	
	public int getInterval()
	{
		return interval;
	}
	
	public long getStartTime()
	{
		return startTime;
	}
	
	public void stop()
	{
		stopped = true;
	}
	
	public boolean isStopped()
	{
		return stopped;
	}
	
	public int getMin()
	{
		return min;
	}
	
	public void setMin(int min)
	{
		this.min = min;
	}
	
	public int getMax()
	{
		return max;
	}
	
	public void setMax(int max)
	{
		this.max = max;
	}
	
	public void setChange(int change)
	{
		subtraction = change;
		addition = change;
	}
	
	public int getSubtraction()
	{
		return subtraction;
	}
	
	public void setSubtraction(int subtraction)
	{
		this.subtraction = subtraction;
	}
	
	public int getAddition()
	{
		return addition;
	}
	
	public void setAddition(int addition)
	{
		this.addition = addition;
	}
	
	public int getState()
	{
		return state;
	}
	
	public void setState(int state)
	{
		this.state = state;
	}
	
	public abstract void tick();
	
	public void complete() {}
	
	// ID List
	public static int ID_HOVER = 0;
	public static int ID_PROGRESS_BAR = 1;
}