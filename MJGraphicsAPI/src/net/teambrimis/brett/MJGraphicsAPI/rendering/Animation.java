package net.teambrimis.brett.MJGraphicsAPI.rendering;

import net.teambrimis.brett.MJGraphicsAPI.components.MJComponent;

public abstract class Animation
{
	private MJComponent component;
	
	private String name; // [MJ-1]
	
	private int interval;
	private int maxStage;
	
	private long startTime;
	private int stage = 0;
	
	private boolean stopped = false;
	
	public Animation(MJComponent component, int interval, int maxStage)
	{
		this.component = component;
		
		this.interval = interval;
		this.maxStage = maxStage;
		
		startTime = System.currentTimeMillis();
	}
	
	public MJComponent getComponent()
	{
		return component;
	}
	
	// [MJ-1]
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	// End
	
	public int getInterval()
	{
		return interval;
	}
	
	public int getMaxStage()
	{
		return maxStage;
	}
	
	public long getStartTime()
	{
		return startTime;
	}
	
	public int getStage()
	{
		return stage;
	}
	
	public void incrementStage(int amount)
	{
		stage += amount;
	}
	
	public void decrementStage(int amount)
	{
		stage -= amount;
	}
	
	public synchronized void modify(int newStage, int newMaxStage)
	{
		stage = newStage;
		maxStage = newMaxStage;
	}
	
	public void stop()
	{
		stopped = true;
	}
	
	public boolean isStopped()
	{
		return stopped;
	}
	
	public abstract void tick();
	
	public void complete() {}
}