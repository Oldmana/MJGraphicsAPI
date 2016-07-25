package net.teambrimis.brett.MJGraphicsAPI.components;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

import net.teambrimis.brett.MJGraphicsAPI.MJGraphicsWindow;

public class PolygonOutline extends MJComponent
{
	private MJComponent watch;
	
	public PolygonOutline(MJGraphicsWindow window, int x, int y, int width, int height, int layer, MJComponent c)
	{
		super(window, x, y, width, height, layer);
		
		watch = c;
	}
	
	/**
	@Override
	public void paint(Graphics2D g)
	{
		if (watch.currentPolygon != null)
		{
		g.setColor(Color.BLACK);
		g.drawPolygon(watch.currentPolygon);
		}
	}
	**/
}
