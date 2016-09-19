package net.teambrimis.brett.MJGraphicsAPI.utils.scheduler;

public abstract class MJRunnable implements Runnable
{
	private boolean cancelled = false;
	
	public MJRunnable()
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
