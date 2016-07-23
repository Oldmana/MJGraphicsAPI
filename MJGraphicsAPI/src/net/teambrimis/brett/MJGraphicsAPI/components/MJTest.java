package net.teambrimis.brett.MJGraphicsAPI.components;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import net.teambrimis.brett.MJGraphicsAPI.MJGraphicsWindow;
import net.teambrimis.brett.MJGraphicsAPI.components.painters.TextPainter;
import net.teambrimis.brett.MJGraphicsAPI.utils.Scaling;

public class MJTest extends MJComponent
{
	public MJTest(MJGraphicsWindow window, int x, int y, int width, int height,
			int layer)
	{
		super(window, x, y, width, height, layer);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void paint(Graphics2D g)
	{
		g.drawRect(0, 0, getScaledWidth() - 1, getScaledHeight() - 1);
		List<String> lines = new ArrayList<String>();
		lines.add("Well why if it isn't the MJ.");
		TextPainter tp = new TextPainter(lines, Scaling.getFont(Font.PLAIN, 2), new Rectangle(0, 0, getScaledWidth() / 4, getScaledHeight() / 4), true, true);
		tp.paint(g);
	}
}
