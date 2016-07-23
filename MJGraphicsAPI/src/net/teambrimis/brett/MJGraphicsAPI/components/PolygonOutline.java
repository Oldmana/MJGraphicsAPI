package net.teambrimis.brett.MJGraphicsAPI.components;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

import net.teambrimis.brett.MJGraphicsAPI.MJGraphicsWindow;
import net.teambrimis.brett.MJGraphicsAPI.components.props.Progressable;

public class PolygonOutline extends MJComponent implements Progressable
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

	@Override
	public void progress()
	{
		// TODO Auto-generated method stub
		
	}
}
