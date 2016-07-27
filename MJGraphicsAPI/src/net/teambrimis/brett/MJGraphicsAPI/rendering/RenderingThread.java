package net.teambrimis.brett.MJGraphicsAPI.rendering;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import net.teambrimis.brett.MJGraphicsAPI.components.MJComponent;
import net.teambrimis.brett.MJGraphicsAPI.utils.GraphicsUtils;

public class RenderingThread extends Thread
{
	private ConcurrentLinkedQueue<RenderingTask> tasks = new ConcurrentLinkedQueue<RenderingTask>();
	
	protected List<Animation> animations = new ArrayList<Animation>();
	
	private volatile boolean rendering = false;
	
	public RenderingThread(String name)
	{
		super(name);
		start();
	}
	
	@Override
	public void run()
	{
		while (true)
		{
			while (!tasks.isEmpty())
			{
				//long time = System.currentTimeMillis();
				rendering = true;
				RenderingTask task = tasks.poll();
				BufferedImage image = GraphicsUtils.getImage(task.getScaledWidth(), task.getScaledHeight());
				Graphics2D g = image.createGraphics();
				g.setColor(new Color(0, 0, 0, 0)); // Overwriting image with transparency
				g.fillRect(0, 0, task.getWidth(), task.getHeight());
				g.setColor(Color.BLACK);
				task.paint(g);
				if (task instanceof MJComponent)
				{
					MJComponent c = (MJComponent) task;
					//BufferedImage old = image;
					//image = GraphicsUtils.getImage(task.getScaledWidth() + c.getWidthPixelsExpanded(), task.getScaledHeight() + c.getHeightPixelsExpanded());
					//enlarged.createGraphics().drawImage(image, c.getWidthPixelsExpanded() / 2, c.getHeightPixelsExpanded() / 2, c.getWindow().getMJFrame());
					//BufferedImage clip = enlarged.getSubimage(c.getWidthPixelsExpanded(), c.getHeightPixelsExpanded(), c.getScaledWidth(), c.getScaledHeight());
					//image = c.executePaintHandlers(image, old);
					//image = enlarged;
					BufferedImage old = image;
					if (((MJComponent) task).getImageWidthPixelsExpanded() > 0 || ((MJComponent) task).getImageHeightPixelsExpanded() > 0)
					{
						
						image = GraphicsUtils.getImage(task.getScaledWidth() + c.getImageWidthPixelsExpanded(), task.getScaledHeight() + c.getImageHeightPixelsExpanded());
						//BufferedImage drawn = image;
						//image = GraphicsUtils.getImage(task.getScaledWidth() + c.getWidthPixelsExpanded(), task.getScaledHeight() + c.getHeightPixelsExpanded());
						image.createGraphics().drawImage(old, c.getImageWidthPixelsExpanded() / 2, c.getImageHeightPixelsExpanded() / 2, c.getWindow().getMJFrame());
					}
					image = c.executePaintHandlers(image, old);
				}
				task.setCurrentImage(image);
				task.paintComplete();
				//System.out.println(System.currentTimeMillis() - time);
			}
			rendering = false;
			
			// Testing: Animations running on rendering thread
			List<Animation> removals = new ArrayList<Animation>();
			for (Animation a : animations)
			{
				if (a.shouldTick())
				{
					a.tick();
				}
				if (a.isStopped())
				{
					removals.add(a);
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
				Thread.sleep(2);
			}
			catch (InterruptedException e) {}
		}
	}
	
	public synchronized void requestRender(RenderingTask task)
	{
		tasks.add(task);
	}
	
	public boolean isRendering()
	{
		return rendering;
	}
	
	public boolean hasTasks()
	{
		return !tasks.isEmpty();
	}
}
