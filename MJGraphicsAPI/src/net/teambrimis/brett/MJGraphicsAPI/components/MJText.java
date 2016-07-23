package net.teambrimis.brett.MJGraphicsAPI.components;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import net.teambrimis.brett.MJGraphicsAPI.MJGraphicsWindow;
import net.teambrimis.brett.MJGraphicsAPI.components.painters.TextPainter;
import net.teambrimis.brett.MJGraphicsAPI.components.painters.TextPainter.Alignment;
import net.teambrimis.brett.MJGraphicsAPI.utils.Scaling;

public class MJText extends MJComponent
{
	private String text = "";
	private double size = 1.0;
	
	private Alignment horizontal = Alignment.CENTER;
	private Alignment vertical = Alignment.CENTER;
	
	public MJText(MJGraphicsWindow window, int x, int y, int width, int height, int layer)
	{
		super(window, x, y, width, height, layer);
		setVisibleToEvents(false);
	}
	
	public void setText(String text)
	{
		this.text = text;
	}
	
	public String getText()
	{
		return text;
	}
	
	public void setHorizontalAlignment(Alignment a)
	{
		horizontal = a;
	}
	
	public Alignment getHorizontalAlignment(Alignment a)
	{
		return horizontal;
	}
	
	public void setVerticalAlignment(Alignment a)
	{
		vertical = a;
	}
	
	public Alignment getVerticalAlignment(Alignment a)
	{
		return vertical;
	}
	
	public void setTextSize(double size)
	{
		this.size = size;
	}
	
	public double getTextSize()
	{
		return size;
	}
	
	@Override
	public void paint(Graphics2D g)
	{
		TextPainter tp = new TextPainter(text, Scaling.getFont(Font.PLAIN, size), new Rectangle(0, 0, getScaledWidth(), getScaledHeight()), true, true);
		tp.setHorizontalAlignment(horizontal);
		tp.setVerticalAlignment(vertical);
		tp.paint(g);
	}
}
