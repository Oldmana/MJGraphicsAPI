package net.teambrimis.brett.MJGraphicsAPI.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import net.teambrimis.brett.MJGraphicsAPI.MJGraphicsWindow;
import net.teambrimis.brett.MJGraphicsAPI.components.listeners.KeyReleaseListener;
import net.teambrimis.brett.MJGraphicsAPI.components.listeners.events.KeyEvent;
import net.teambrimis.brett.MJGraphicsAPI.components.listeners.events.KeyEvent.Keys;
import net.teambrimis.brett.MJGraphicsAPI.components.painters.TextPainter;
import net.teambrimis.brett.MJGraphicsAPI.components.painters.TextPainter.TextMeasurement;
import net.teambrimis.brett.MJGraphicsAPI.rendering.Animation;
import net.teambrimis.brett.MJGraphicsAPI.utils.Scaling;

public class MJTextBox extends MJComponent
{
	private String text = "";
	private int pos = 0;
	private boolean blinkOn = false;
	
	private int minBound;
	private int maxBound;
	
	private KeyReleaseListener KRL = new KeyReleaseListener()
	{
		@Override
		public void onKeyRelease(KeyEvent event)
		{
			if (event.getKey() == Keys.VK_BACK_SPACE && pos > 0)
			{
				text = new StringBuilder(text).deleteCharAt(pos - 1).toString();
				pos--;
			}
			else if (event.getKey() == Keys.VK_LEFT)
			{
				if (pos > 0)
				{
					pos--;
					blinkOn = true;
				}
			}
			else if (event.getKey() == Keys.VK_RIGHT)
			{
				if (pos < text.length())
				{
					pos++;
					blinkOn = true;
				}
			}
			else if (event.hasTypedChar())
			{
				text = new StringBuilder(text).insert(pos, event.getTypedChar()).toString();
				//text += event.getTypedChar();
				pos++;
			}
			else
			{
				
			}
			requestRepaint();
		}
	};
	
	private Animation blink = new Animation(this, 500)
	{
		@Override
		public void tick()
		{
			blinkOn = !blinkOn;
			requestRepaint();
		}
		
	};
	
	{
		registerListener(KRL);
		addAnimation(blink);
	}
	
	public MJTextBox(MJGraphicsWindow window, int x, int y, int width, int height, int layer)
	{
		super(window, x, y, width, height, layer);
	}
	
	@Override
	public void paint(Graphics2D g)
	{
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, getScaledWidth() - 1, getScaledHeight() - 1);
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, getScaledWidth() - 1, getScaledHeight() - 1);
		List<String> l = new ArrayList<String>();
		TextMeasurement tm = new TextMeasurement(Scaling.getFont(Font.PLAIN, getHeight() - 0.5));
		String visible = tm.getHorizontalVisibleText(text, getScaledWidth(), pos);
		l.add(visible);
		if (blinkOn)
		{
			if (tm.getWidth(text) > getScaledWidth() && !(tm.getWidth(text.substring(0, Math.min(pos + 1, text.length())))< getScaledWidth()))
			{
				g.fillRect(tm.getWidth(visible) + 0, 2, 2, tm.getHeight());
			}
			else
			{
				g.fillRect(tm.getWidth(text.substring(0, pos)) + 0, 2, 2, tm.getHeight());
			}
		}
		
		TextPainter tp = new TextPainter(l, Scaling.getFont(Font.PLAIN, getHeight() - 0.5), new Rectangle(0, 0, getScaledWidth(), getScaledHeight()), false, true);
		
		tp.paint(g);
	}
}
