package net.teambrimis.brett.MJGraphicsAPI.components;

import java.awt.Color;
import java.awt.Graphics2D;

import net.teambrimis.brett.MJGraphicsAPI.MJGraphicsWindow;

public class MJColorPane extends MJComponent
{
	private Color color = new Color(0, 0, 0);
	
	public MJColorPane(MJGraphicsWindow window, int x, int y, int width, int height, int layer, Color color)
	{
		super(window, x, y, width, height, layer);
		this.color = color;
	}
	
	public void setColor(Color c)
	{
		color = c;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	@Override
	public void paint(Graphics2D g)
	{
		g.setColor(color);
		g.fillRect(0, 0, getScaledWidth() - 1, getScaledHeight() - 1);
	}
}
