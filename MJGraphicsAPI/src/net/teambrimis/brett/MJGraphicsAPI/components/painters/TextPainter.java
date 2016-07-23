package net.teambrimis.brett.MJGraphicsAPI.components.painters;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class TextPainter extends Painter
{
	private List<String> text;
	private Font font;
	
	private Rectangle bounds;
	
	private boolean autoReturn;
	private boolean attachWords;
	
	private Alignment horizontal = Alignment.LEFT;
	private Alignment vertical = Alignment.TOP;
	
	public TextPainter(List<String> text, Font font, Rectangle bounds, boolean autoReturn, boolean attachWords)
	{
		this.text = text;
		this.font = font;
		
		this.bounds = bounds;
		
		this.autoReturn = autoReturn;
		this.attachWords = attachWords;
	}
	
	public TextPainter(String text, Font font, Rectangle bounds, boolean autoReturn, boolean attachWords)
	{
		List<String> list = new ArrayList<String>(1);
		list.add(text);
		this.text = list;
		this.font = font;
		
		this.bounds = bounds;
		
		this.autoReturn = autoReturn;
		this.attachWords = attachWords;
	}
	
	public void setHorizontalAlignment(Alignment a)
	{
		if (a == Alignment.TOP || a == Alignment.BOTTOM)
		{
			throw new IllegalArgumentException("Invalid alignment for the given axis.");
		}
		horizontal = a;
	}
	
	public void setVerticalAlignment(Alignment a)
	{
		if (a == Alignment.LEFT || a == Alignment.RIGHT)
		{
			throw new IllegalArgumentException("Invalid alignment for the given axis.");
		}
		vertical = a;
	}
	
	@Override
	public void paint(Graphics2D whole)
	{
		Graphics2D g = (Graphics2D) whole.create((int) bounds.getMinX(), (int) bounds.getMinY(), (int) bounds.getWidth(), 
				(int) bounds.getHeight());
		g.setFont(font);
		FontMetrics m = g.getFontMetrics();
		List<String> lines = new ArrayList<String>();
		if (autoReturn)
		{
			for (String str : text)
			{
				char[] chars = str.toCharArray();
				String line = "";
				for (char c : chars)
				{
					line += c;
					if (m.stringWidth(line) > bounds.getWidth())
					{
						if (attachWords)
						{
							if (c == ' ')
							{
								lines.add(line.substring(0, line.length() - 1));
								line = "" + c;
							}
							else
							{
								char[] charz = line.toCharArray();
								boolean addedLine = false;
								for (int i = charz.length - 1 ; i > 0 ; i--)
								{
									if (charz[i] == ' ')
									{
										lines.add(line.substring(0, i));
										line = line.substring(i + 1);
										
										addedLine = true;
										break;
									}
								}
								if (!addedLine)
								{
									lines.add(line);
									line = "";
								}
							}
						}
						else
						{
							lines.add(line.substring(0, line.length() - 1));
							line = "" + c;
						}
					}
					else
					{
						
					}
				}
				if (!line.equals(""))
				{
					lines.add(line);
				}
			}
			for (String str : lines)
			{
				System.out.println(str);
			}
			
			int verticalAddition = 0;
			if (vertical == Alignment.CENTER)
			{
				verticalAddition = ((int) bounds.getHeight() - (lines.size() * m.getHeight())) / 2;
			}
			else if (vertical == Alignment.BOTTOM)
			{
				verticalAddition = (int) bounds.getHeight() - (lines.size() * m.getHeight());
			}
			
			for (int i = 0 ; i < lines.size() ; i++)
			{
				int horizontalAddition = 0;
				if (horizontal == Alignment.CENTER)
				{
					horizontalAddition = ((int) bounds.getWidth() - m.stringWidth(lines.get(i))) / 2;
				}
				else if (horizontal == Alignment.RIGHT)
				{
					horizontalAddition = (int) bounds.getWidth() - m.stringWidth(lines.get(i));
				}
				g.drawString(lines.get(i), horizontalAddition, verticalAddition + (i * m.getHeight()) + m.getAscent());
			}
		}
		else
		{
			
		}
	}
	
	public enum Alignment
	{
		LEFT, RIGHT, TOP, BOTTOM, CENTER
	}
}
