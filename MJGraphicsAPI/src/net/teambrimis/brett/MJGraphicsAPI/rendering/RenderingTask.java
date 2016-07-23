package net.teambrimis.brett.MJGraphicsAPI.rendering;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import net.teambrimis.brett.MJGraphicsAPI.utils.Scaling;

public abstract class RenderingTask
{
	private BufferedImage currentImage;
	
	private int width;
	private int height;
	
	
	public RenderingTask(int width, int height)
	{
		this.width = width;
		this.height = height;
	}
	
	public synchronized void setCurrentImage(BufferedImage image)
	{
		currentImage = image;
	}
	
	public synchronized BufferedImage getCurrentImage()
	{
		return currentImage;
	}
	
	public void setWidth(int w)
	{
		width = w;
	}
	
	public void setScaledWidth(int w)
	{
		width = Scaling.scale(w);
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getScaledWidth()
	{
		return Scaling.scale(width);
	}
	
	public void setHeight(int h)
	{
		height = h;
	}
	
	public void setScaledHeight(int h)
	{
		height = Scaling.scale(h);
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public int getScaledHeight()
	{
		return Scaling.scale(height);
	}
	
	public abstract void paint(Graphics2D g);
	
	public void paintComplete() {}
}
