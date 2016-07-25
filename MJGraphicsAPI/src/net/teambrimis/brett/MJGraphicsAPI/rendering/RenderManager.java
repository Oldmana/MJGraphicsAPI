package net.teambrimis.brett.MJGraphicsAPI.rendering;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

import javax.swing.SwingUtilities;

import net.teambrimis.brett.MJGraphicsAPI.MJGraphicsWindow;
import net.teambrimis.brett.MJGraphicsAPI.components.MJComponent;
import net.teambrimis.brett.MJGraphicsAPI.utils.Scaling;

public class RenderManager
{
	private MJGraphicsWindow window;
	
	private RenderingThread[] renderers;
	
	private volatile boolean renderingFrame = false;
	private volatile boolean renderQueued = false;
	
	private Object lock = new Object();
	
	
	public RenderManager(MJGraphicsWindow window, int threadCount)
	{
		this.window = window;
		renderers = new RenderingThread[threadCount];
		for (int i = 0 ; i < threadCount ; i++)
		{
			renderers[i] = new RenderingThread("Rendering Thread #" + (i + 1));
		}
	}
	
	public synchronized void addComponent(int layer, MJComponent task)
	{
		window.getMJFrame().getEnabledPanel().addComponent(layer, task);
	}
	
	public void render(RenderingTask task)
	{
		synchronized (lock)
		{
			RenderingThread renderer = renderers[getBestRenderingThread()];
			renderer.requestRender(task);
		}
	}
	
	public void renderFrame()
	{
		if (renderingFrame)
		{
			renderQueued = true;
		}
		else if (!renderQueued)
		{
			renderingFrame = true;
			render(new RenderingTask(Scaling.descale(window.getMJFrame().getWidth()), Scaling.descale(window.getMJFrame().getHeight()))
			{
				@Override
				public void paint(Graphics2D g)
				{
					g.setColor(new Color(240, 240, 240));
					g.fillRect(0, 0, window.getMJFrame().getWidth(), window.getMJFrame().getHeight());
					for (List<MJComponent> layer : window.getMJFrame().getEnabledPanel().getComponents())
					{
						if (layer != null)
						{
							for (MJComponent c : layer)
							{
								if (c.getCurrentImage() != null)
								{
									if (c.getCurrentImage().getWidth() != c.getScaledWidth() + c.getImageWidthPixelsExpanded() || c.getCurrentImage().getHeight() != c.getScaledHeight() + c.getImageHeightPixelsExpanded())
									{
										g.drawImage(c.getCurrentImage(), c.getScaledX() + c.getExcessX() - ((c.getCurrentImage().getWidth() - c.getScaledWidth()) / 2), c.getScaledY() + c.getExcessY() - ((c.getCurrentImage().getHeight() - c.getScaledHeight()) / 2), window.getMJFrame());
									}
									else
									{
										g.drawImage(c.getCurrentImage(), c.getScaledX() + c.getExcessX() - (c.getImageWidthPixelsExpanded() / 2), c.getScaledY() + c.getExcessY() - (c.getImageHeightPixelsExpanded() / 2), window.getMJFrame());
									}
								}
							}
						}
					}
					
					if (renderQueued)
					{
						render(this);
						renderQueued = false;
					}
					else
					{
						renderingFrame = false;
					}
					
					SwingUtilities.invokeLater(new Runnable()
					{
						@Override
						public void run()
						{
							window.getMJFrame().getEnabledPanel().setCurrentImage(getCurrentImage());
							window.getMJFrame().repaint();
						}
					});
				}
			});
		}
	}
	
	public synchronized void registerAnimation(Animation a)
	{
		renderers[0].animations.add(a);
	}
	
	private int getBestRenderingThread()
	{
		int lowestLoaded = 0;
		int lowestLoad = 3;
		
		for (int i = 0 ; i < renderers.length ; i++)
		{
			if ((renderers[i].isRendering() ? 1 : 0) + (renderers[i].hasTasks() ? 2 : 0) < lowestLoad)
			{
				lowestLoaded = i;
			}
		}
		return lowestLoaded;
	}
}
