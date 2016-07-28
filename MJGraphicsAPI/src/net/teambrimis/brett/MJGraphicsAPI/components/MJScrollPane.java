package net.teambrimis.brett.MJGraphicsAPI.components;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import net.teambrimis.brett.MJGraphicsAPI.MJGraphicsWindow;
import net.teambrimis.brett.MJGraphicsAPI.components.listeners.MouseClickListener;
import net.teambrimis.brett.MJGraphicsAPI.components.listeners.MouseWheelListener;
import net.teambrimis.brett.MJGraphicsAPI.components.listeners.events.MouseEvent;
import net.teambrimis.brett.MJGraphicsAPI.components.listeners.events.MouseWheelEvent;
import net.teambrimis.brett.MJGraphicsAPI.rendering.PaintHandler;
import net.teambrimis.brett.MJGraphicsAPI.utils.Scaling;

public class MJScrollPane extends MJComponent
{
	private MJComponent wrapped;
	
	private int scrollbarSize = 2;
	
	private int xScroll = 0;
	private int yScroll = 0;
	
	private MouseClickListener MCL = new MouseClickListener()
	{
		@Override
		public void onMouseClick(MouseEvent event)
		{
			int x = event.getX();
			int y = event.getY();
			int sss = getScaledScrollbarSize();
			if (x >= 0 && y >= getScaledHeight() - sss && x <= sss && y <= getScaledHeight())
			{
				xScroll = Math.max(0, xScroll - 1);
			}
			else if (x >= getScaledWidth() - sss * 2 && y >= getScaledHeight() - sss && x <= getScaledWidth() - sss && y <= getScaledHeight())
			{
				xScroll = Math.min(getXRange(), xScroll + 1);
			}
			else if (x >= getScaledWidth() - sss && y >= 0 && x <= getScaledWidth() && y <= sss)
			{
				yScroll = Math.max(0, yScroll - 1);
			}
			else if (x >= getScaledWidth() - sss && y >= getScaledHeight() - sss * 2 && x <= getScaledWidth() && y <= getScaledHeight() - sss)
			{
				yScroll = Math.min(getXRange(), yScroll + 1);
			}
			
			requestRepaint();
		}
	};
	
	private MouseWheelListener MWL = new MouseWheelListener()
	{
		@Override
		public void onMouseWheel(MouseWheelEvent event)
		{
			yScroll = Math.min(getXRange(), yScroll + 1);
			requestRepaint();
		}
	};
	
	{
		this.registerListener(MCL);
		this.registerListener(MWL);
	}
	
	public MJScrollPane(MJGraphicsWindow window, int x, int y, int width, int height, int layer)
	{
		super(window, x, y, width, height, layer);
		
		addPaintHandler(new PaintHandler(this)
		{
			@Override
			public void handlePaint(Graphics2D g, BufferedImage current, BufferedImage initial)
			{
				if (wrapped != null)
				{
					if (wrapped.getCurrentImage() != null)
					{
						if (wrapped.getCurrentImage().getWidth() > wrapped.getScaledWidth() + wrapped.getImageWidthPixelsExpanded())
						{
							g.drawImage(wrapped.getCurrentImage(), -Scaling.scale(xScroll) + wrapped.getScaledX() + 
								wrapped.getExcessX() - ((wrapped.getCurrentImage().getWidth() - 
								wrapped.getScaledWidth()) / 2) + (getImageWidthPixelsExpanded() / 2), 
								-Scaling.scale(yScroll) + wrapped.getScaledY() + wrapped.getExcessY() - 
								((wrapped.getCurrentImage().getHeight() - wrapped.getScaledHeight()) / 2) + 
								(getImageHeightPixelsExpanded() / 2), getWindow().getMJFrame());
						}
						else
						{
							g.drawImage(wrapped.getCurrentImage(), -Scaling.scale(xScroll) + wrapped.getScaledX() + 
								wrapped.getExcessX() - (wrapped.getImageWidthPixelsExpanded() / 2) + 
								(getImageWidthPixelsExpanded() / 2), -Scaling.scale(yScroll) + wrapped.getScaledY() + 
								wrapped.getExcessY() - (wrapped.getImageHeightPixelsExpanded() / 2) + 
								(getImageHeightPixelsExpanded() / 2), getWindow().getMJFrame());
						}
					}
				}
				g.drawImage(current, 0, 0, getWindow().getMJFrame());
			}

			@Override
			public boolean needsHandle()
			{
				if (wrapped != null && wrapped.getCurrentImage() != null)
				{
					return true;
				}
				return false;
			}
		});
	}
	
	public void wrapComponent(MJComponent c)
	{
		wrapped = c;
		c.setSupercomponent(this);
	}
	
	public MJComponent getWrappedComponent()
	{
		return wrapped;
	}
	
	public int getScrollbarSize()
	{
		return scrollbarSize;
	}
	
	public int getScaledScrollbarSize()
	{
		return Scaling.scale(scrollbarSize);
	}
	
	public int getXScroll()
	{
		return xScroll;
	}
	
	public int getYScroll()
	{
		return yScroll;
	}
	
	public int getScaledPanelWidth()
	{
		return getScaledWidth() - getScaledScrollbarSize();
	}
	
	public int getScaledPanelHeight()
	{
		return getScaledHeight() - getScaledScrollbarSize();
	}
	
	public int getPanelWidth()
	{
		return getWidth() - getScrollbarSize();
	}
	
	public int getPanelHeight()
	{
		return getHeight() - getScrollbarSize();
	}
	
	public int getScaledXScrollbarArea()
	{
		return getScaledPanelWidth() - getScaledScrollbarSize() * 2;
	}
	
	public int getScaledYScrollbarArea()
	{
		return getScaledPanelHeight() - getScaledScrollbarSize() * 2;
	}
	
	public int getScaledScrollbarWidth()
	{
		return (int) Math.round(getScaledXScrollbarArea() * ((double) getWidth() / wrapped.getWidth()));
	}
	
	public int getScaledScrollbarHeight()
	{
		return (int) Math.round(getScaledYScrollbarArea() * ((double) getHeight() / wrapped.getHeight()));
	}
	
	public int getXRange()
	{
		return wrapped.getWidth() - getPanelWidth();
	}
	
	public int getYRange()
	{
		return wrapped.getHeight() - getPanelHeight();
	}
	
	public double getXScrollPercent()
	{
		return (double) xScroll / getXRange();
	}
	
	public double getYScrollPercent()
	{
		return (double) yScroll / getYRange();
	}
	
	@Override
	public void paint(Graphics2D g)
	{
		int sss = getScaledScrollbarSize();
		
		// --Drawing Base
		Color base = new Color(215, 215, 215);
		g.setColor(base);
		g.fillRect(0, getScaledHeight() - sss, getScaledWidth(), sss);
		g.fillRect(getScaledWidth() - sss, 0, sss, getScaledHeight());
		
		// --Drawing Bars
		g.setColor(Color.LIGHT_GRAY);
		// X-axis
		g.fillRect(sss + (int) ((getScaledXScrollbarArea() - getScaledScrollbarWidth()) * getXScrollPercent()), 
				getScaledHeight() - sss, getScaledScrollbarWidth(), sss);
		
		// Y-axis
		g.fillRect(getScaledWidth() - sss, sss + (int) ((getScaledYScrollbarArea() - getScaledScrollbarHeight()) * 
				getYScrollPercent()), sss, getScaledScrollbarHeight());
		// --Drawing Scroll Buttons
		g.setColor(Color.GRAY);
		// X-axis
		g.fillRect(0, getScaledHeight() - sss, sss, sss);
		g.fillRect(getScaledWidth() - (sss * 2), getScaledHeight() - sss, sss, sss);
		// Y-axis
		g.fillRect(getScaledWidth() - sss, 0, sss, sss);
		g.fillRect(getScaledWidth() - sss, getScaledHeight() - (sss * 2), sss, sss);
		
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, getScaledWidth() - 1, getScaledHeight() - 1);
	}
	
	@Override
	public MJComponent getComponentAt(int x, int y)
	{
		// Real subcomponents get priority
		for (int i = subcomponents.size() - 1 ; i >= 0 ; i--)
		{
			for (MJComponent c : subcomponents.get(i))
			{
				if ((c.isVisibleToEvents() && c.isInside(new Point(x, y))) && (c.getCurrentImage() != null
					/**&& ((c.getCurrentImage().getRGB(x - c.getScaledX() - c.getExcessX() + 
						(c.getImageWidthPixelsExpanded() / 2), y - c.getScaledY() - c.getExcessY() + 
						(c.getImageHeightPixelsExpanded() / 2)) >> 24) & 0xff) != 0**/))
				{
					return c.getComponentAt(x, y);
				}
			}
		}
		
		// Return this component if referring to scrollbars, secondary priority
		if (((x > 0 && x < getActualScaledEndX()) && (y >= getActualScaledEndY() - Scaling.scale(scrollbarSize) && y < getActualScaledEndY())) ||
			((x >= getActualScaledEndX() - Scaling.scale(scrollbarSize) && x < getActualScaledEndX()) && (y > 0 && y < getActualScaledEndY())))
		{
			return this;
		}
		
		// The wrapped component gets last priority
		if ((wrapped != null && wrapped.isVisibleToEvents() && wrapped.isInside(new Point(x + Scaling.scale(xScroll), y + Scaling.scale(yScroll)))) && (wrapped.getCurrentImage() != null
				/**&& ((c.getCurrentImage().getRGB(x - c.getScaledX() - c.getExcessX() + 
					(c.getImageWidthPixelsExpanded() / 2), y - c.getScaledY() - c.getExcessY() + 
					(c.getImageHeightPixelsExpanded() / 2)) >> 24) & 0xff) != 0**/))
			{
				// Adds scroll to mouse position
				return wrapped.getComponentAt(x + Scaling.scale(xScroll), y + Scaling.scale(yScroll));
			}
		return this;
	}
}
