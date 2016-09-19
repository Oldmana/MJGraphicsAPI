package net.teambrimis.brett.MJGraphicsAPI.utils.scheduler;

public abstract class MJTask implements Runnable
{
	private int interval;
	
	private boolean repeat;
	private int runsLeft = -1;
	
	private volatile boolean cancelled = false;
	
	public MJTask()
	{
		
	}
	
	public void cancel()
	{
		cancelled = true;
	}
	
	public boolean isCancelled()
	{
		return cancelled;
	}
}
