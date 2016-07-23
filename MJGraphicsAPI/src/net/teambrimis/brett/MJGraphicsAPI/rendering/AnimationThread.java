package net.teambrimis.brett.MJGraphicsAPI.rendering;

import java.util.ArrayList;
import java.util.List;

public class AnimationThread extends Thread
{
	protected static List<Animation> animations = new ArrayList<Animation>();
	
	private Object lock = new Object();
	
	public AnimationThread(String name)
	{
		super(name);
		start();
	}
	
	@Override
	public void run()
	{
		while (true)
		{
			List<Animation> removals = new ArrayList<Animation>();
			synchronized (lock)
			{
			for (Animation a : animations)
			{
				if (System.currentTimeMillis() > a.getStartTime() + ((a.getInterval() - 1) * a.getStage()))
				{
					a.tick();
					if (a.isStopped())
					{
						removals.add(a);
					}
					else if (a.getMaxStage() > 0)
					{
						if (a.getStage() >= a.getMaxStage())
						{
							removals.add(a);
						}
						else
						{
							a.incrementStage((int) (System.currentTimeMillis() - Math.ceil((double) (a.getStartTime() + ((a.getInterval() - 1)) * (double) a.getStage()))) / a.getInterval());
						}
					}
					else
					{
						//a.incrementStage((int) (System.currentTimeMillis() - Math.ceil((double) (a.getStartTime() + ((a.getInterval() - 1)) * (double) a.getStage()))) / a.getInterval());
						a.incrementStage(1);
					}
				}
			}
			}
			
			for (Animation a : removals)
			{
				a.stop();
				a.getComponent().removeAnimation(a);
				animations.remove(a);
				a.complete();
			}
			
			try
			{
				Thread.sleep(5);
			}
			catch (InterruptedException e) {}
		}
	}
	
	public static void add(Animation a)
	{
		
		{
			animations.add(a);
		}
	}
	
	public static void remove(Animation a)
	{
		
		{
			animations.remove(a);
		}
	}
}
