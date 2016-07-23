package net.teambrimis.brett.MJGraphicsAPI.rendering;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import net.teambrimis.brett.MJGraphicsAPI.components.MJComponent;

public abstract class PaintHandler
{
	private MJComponent component;
	
	public PaintHandler(MJComponent component)
	{
		this.component = component;
	}
	
	public MJComponent getComponent()
	{
		return component;
	}
	
	public abstract void handlePaint(Graphics2D g, BufferedImage current, BufferedImage initial);
	
	public abstract boolean needsHandle();
}
