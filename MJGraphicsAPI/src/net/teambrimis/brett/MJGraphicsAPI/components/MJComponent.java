package net.teambrimis.brett.MJGraphicsAPI.components;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.teambrimis.brett.MJGraphicsAPI.MJGraphicsWindow;
import net.teambrimis.brett.MJGraphicsAPI.components.listeners.Listener;
import net.teambrimis.brett.MJGraphicsAPI.rendering.Animation;
import net.teambrimis.brett.MJGraphicsAPI.rendering.AnimationFade;
import net.teambrimis.brett.MJGraphicsAPI.rendering.PaintHandler;
import net.teambrimis.brett.MJGraphicsAPI.rendering.RenderingTask;
import net.teambrimis.brett.MJGraphicsAPI.utils.GraphicsUtils;
import net.teambrimis.brett.MJGraphicsAPI.utils.ImageCache;
import net.teambrimis.brett.MJGraphicsAPI.utils.MathUtils;
import net.teambrimis.brett.MJGraphicsAPI.utils.Scaling;

public abstract class MJComponent extends RenderingTask
{
	private MJGraphicsWindow window;
	
	private MJComponent supercomponent;
	protected List<List<MJComponent>> subcomponents;
	
	private List<Animation> animations = new ArrayList<Animation>();
	
	private int x;
	private int y;
	
	private int excessX = 0;
	private int excessY = 0;
	
	private int layer;
	
	private ImageCache cache;
	
	private boolean refreshRequested = true;
	
	private List<PaintHandler> paintHandlers = new ArrayList<PaintHandler>();
	
	private double widthPercentExpanded = 0;
	private double heightPercentExpanded = 0;
	
	private double rotation = 0.0;
	
	private boolean enabled = true;
	
	private boolean visibleToEvents = true;
	
	private List<Listener> listeners = new ArrayList<Listener>();
	
	public MJComponent(MJGraphicsWindow window, int x, int y, int width, int height, int layer)
	{
		super(width, height);
		this.window = window;
		
		subcomponents = new ArrayList<List<MJComponent>>();
		
		this.x = x;
		this.y = y;
		
		this.layer = layer;
		
		cache = new ImageCache();
		
		addPaintHandler(new PaintHandler(this)
		{
			@Override
			public void handlePaint(Graphics2D g, BufferedImage current, BufferedImage initial)
			{
				g.drawImage(current, 0, 0, getWindow().getMJFrame());
				for (List<MJComponent> layer : getSubcomponents())
				{
					if (layer != null)
					{
						for (MJComponent c : layer)
						{
							if (c.getCurrentImage() != null)
							{
								if (c.getCurrentImage().getWidth() > c.getScaledWidth() + c.getImageWidthPixelsExpanded())
								{
									g.drawImage(c.getCurrentImage(), c.getScaledX() + c.getExcessX() - ((c.getCurrentImage().getWidth() - c.getScaledWidth()) / 2) + (getImageWidthPixelsExpanded() / 2), c.getScaledY() + c.getExcessY() - ((c.getCurrentImage().getHeight() - c.getScaledHeight()) / 2) + (getImageHeightPixelsExpanded() / 2), getWindow().getMJFrame());
								}
								else
								{
									g.drawImage(c.getCurrentImage(), c.getScaledX() + c.getExcessX() - (c.getImageWidthPixelsExpanded() / 2) + (getImageWidthPixelsExpanded() / 2), c.getScaledY() + c.getExcessY() - (c.getImageHeightPixelsExpanded() / 2) + (getImageHeightPixelsExpanded() / 2), getWindow().getMJFrame());
								}
							}
						}
					}
				}
			}

			@Override
			public boolean needsHandle()
			{
				return true;
			}
		});
		
		addPaintHandler(new PaintHandler(this)
		{
			@Override
			public void handlePaint(Graphics2D g, BufferedImage current, BufferedImage initial)
			{
				if (rotation > 0)
				{
					System.out.println(current.getWidth() + ", " + current.getHeight());
					GraphicsUtils.drawRotatedImage(g, current, rotation, getComponent().getScaledWidth() + getComponent().getImageWidthPixelsExpanded(), getComponent().getScaledHeight() + getComponent().getImageHeightPixelsExpanded());
				}
				else
				{
					g.drawImage(current, 0, 0, getWindow().getMJFrame());
				}
			}

			@Override
			public boolean needsHandle()
			{
				if (rotation > 0)
				{
					return true;
				}
				return false;
			}
		});
	}
	
	public MJGraphicsWindow getWindow()
	{
		return window;
	}
	
	public ImageCache getCache()
	{
		return cache;
	}
	
	public void addSubcomponent(int layer, MJComponent c)
	{
		if (subcomponents.size() <= layer)
		{
			for (int i = 0 ; i <= 1 + layer - subcomponents.size() ; i++)
			{
				subcomponents.add(new ArrayList<MJComponent>());
			}
		}
		
		subcomponents.get(layer).add(c);
		c.setSupercomponent(this);
		c.componentAdded();
	}
	
	public void removeSubcomponent(MJComponent c)
	{
		for (List<MJComponent> layer : subcomponents)
		{
			if (layer.remove(c))
			{
				break;
			}
		}
	}
	
	public List<List<MJComponent>> getSubcomponents()
	{
		return subcomponents;
	}
	
	public void setSupercomponent(MJComponent c)
	{
		supercomponent = c;
	}
	
	public MJComponent getSupercomponent()
	{
		return supercomponent;
	}
	
	public boolean hasSupercomponent()
	{
		return supercomponent != null;
	}
	
	public void addAnimation(Animation a)
	{
		/** Removal by [MJ-1]
		if (hasAnimation(a.getClass()))
		{
			return;
		}
		**/
		animations.add(a);
		window.getRenderManager().animate(a);
	}
	
	public void removeAnimation(Animation a)
	{
		animations.remove(a);
		if (!a.isStopped())
		{
			a.stop();
		}
	}
	
	public void removeAnimation(Class<? extends Animation> c)
	{
		removeAnimation(getAnimationByClass(c));
	}
	
	public Animation getAnimationByClass(Class<? extends Animation> cl)
	{
		for (Animation a : animations)
		{
			if (a.getClass() == cl)
			{
				return a;
			}
		}
		return null;
	}
	
	public boolean hasAnimation(Class<? extends Animation> c)
	{
		for (Animation a : animations)
		{
			if (a.getClass() == c)
			{
				return true;
			}
		}
		return false;
	}
	
	public synchronized void setBounds(Rectangle r)
	{
		x = r.x;
		y = r.y;
		setWidth(r.width);
		setHeight(r.height);
	}
	
	public Rectangle getBounds()
	{
		return new Rectangle(x, y, getWidth(), getHeight());
	}
	
	public Rectangle getScaledBounds()
	{
		return Scaling.getRectangle(x, y, getWidth() + x, getHeight() + y);
	}
	
	public synchronized void setX(int x)
	{
		this.x = x;
		excessX = 0;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getEndX()
	{
		return x + getWidth();
	}
	
	public synchronized void setScaledX(int x)
	{
		this.x = Scaling.descale(x);
		excessX = Scaling.getExcess(x);
	}
	
	public int getScaledX()
	{
		return Scaling.scale(x);
	}
	
	public int getScaledEndX()
	{
		return Scaling.scale(x + getWidth());
	}
	
	public synchronized void setExcessX(int excessX)
	{
		this.excessX = excessX;
	}
	
	public int getExcessX()
	{
		return excessX;
	}
	
	public synchronized void setY(int y)
	{
		this.y = y;
		excessY = 0;
	}
	
	public int getY()
	{
		return y;
	}
	
	public int getEndY()
	{
		return y + getHeight();
	}
	
	public synchronized void setScaledY(int y)
	{
		this.y = Scaling.descale(y);
		excessY = Scaling.getExcess(y);
	}
	
	public int getScaledY()
	{
		return Scaling.scale(y);
	}
	
	public int getScaledEndY()
	{
		return Scaling.scale(y + getHeight());
	}
	
	public synchronized void setExcessY(int excessY)
	{
		this.excessY = excessY;
	}
	
	public int getExcessY()
	{
		return excessY;
	}
	
	public Point getScaledCenter()
	{
		return new Point(getScaledX() + (getScaledWidth() / 2), getScaledY() + (getScaledHeight() / 2));
	}
	
	// TODO: Experimental
	
	public Point getGlobalScaledCenter()
	{
		return new Point(getActualScaledX() + (getScaledWidth() / 2), getActualScaledY() + (getScaledHeight() / 2));
	}
	
	// End Experimental
	
	public void setLayer(int layer)
	{
		this.layer = layer;
	}
	
	public int getLayer()
	{
		return layer;
	}
	
	public synchronized void setPercentExpanded(double percent)
	{
		widthPercentExpanded = percent;
		heightPercentExpanded = percent;
	}
	
	public synchronized void setWidthPercentExpanded(double percent)
	{
		widthPercentExpanded = percent;
	}
	
	public double getWidthPercentExpanded()
	{
		return widthPercentExpanded;
	}
	
	public int getWidthPixelsExpanded()
	{
		return Scaling.scale((int) Math.round((double) getWidth() * (widthPercentExpanded / 100.0)));
	}
	
	public int getImageWidthPixelsExpanded()
	{
		return Math.max(0, getWidthPixelsExpanded());
	}
	
	public synchronized void setHeightPercentExpanded(double percent)
	{
		heightPercentExpanded = percent;
	}
	
	public double getHeightPercentExpanded()
	{
		return heightPercentExpanded;
	}
	
	public int getHeightPixelsExpanded()
	{
		//System.out.println("Pixels Expanded: " + ((int) Math.round((double) getScaledHeight() * (heightPercentExpanded / 100.0))));
		return (int) Math.round((double) getScaledHeight() * (heightPercentExpanded / 100.0));
	}
	
	public int getImageHeightPixelsExpanded()
	{
		return Math.max(0, getHeightPixelsExpanded());
	}
	
	public synchronized void setRotation(double rotation)
	{
		this.rotation = rotation;
		setWidthPercentExpanded(getRotationWidthExpansion());
		setHeightPercentExpanded(getRotationHeightExpansion());
	}
	
	public synchronized void addRotation(double addition)
	{
		rotation += addition;
		while (rotation >= 360)
		{
			rotation -= 360;
		}
		setWidthPercentExpanded(getRotationWidthExpansion());
		setHeightPercentExpanded(getRotationHeightExpansion());
	}
	
	public synchronized void subtractRotation(double subtraction)
	{
		rotation -= subtraction;
		while (rotation < 0)
		{
			rotation += 360;
		}
		setWidthPercentExpanded(getRotationWidthExpansion());
		setHeightPercentExpanded(getRotationHeightExpansion());
	}
	
	public double getRotation()
	{
		return rotation;
	}
	
	public boolean hasRotation()
	{
		return rotation != 0;
	}
	
	private double getRotationWidthExpansion()
	{
		return (double) ((double) ((double) MathUtils.getWidthHeightWithRotation(getScaledBounds(), rotation)[0] - getScaledWidth()) / (double) getScaledWidth()) * 100.0;
	}
	
	private double getRotationHeightExpansion()
	{
		return (double) ((double)((double) MathUtils.getWidthHeightWithRotation(getScaledBounds(), rotation)[1] - getScaledHeight()) / (double) getScaledHeight()) * 100.0;
	}
	
	public boolean isMouseHovering()
	{
		return window.getHovered() == this;
	}
	
	public boolean isFading()
	{
		if (getFade() != null)
		{
			return true;
		}
		return false;
	}
	
	public AnimationFade getFade()
	{
		for (Animation a : animations)
		{
			if (a instanceof AnimationFade)
			{
				return (AnimationFade) a;
			}
		}
		return null;
	}
	
	/**Checks if this component or a subcomponent has prime focus.
	 * 
	 * @return If this component is focused.
	 */
	public boolean isFocused()
	{
		if (window.getMJFrame().getFocused() == this)
		{
			return true;
		}
		
		if (!subcomponents.isEmpty())
		{
			for (List<MJComponent> cs : subcomponents)
			{
				for (MJComponent c : cs)
				{
					if (c.isFocused())
					{
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	/**Checks if this component has the prime focus.
	 * 
	 * @return If this component is the prime focus.
	 */
	public boolean isPrimeFocused()
	{
		return window.getMJFrame().getFocused() == this;
	}
	
	/**Grants prime focus to this component.
	 * 
	 */
	public void focus()
	{
		window.getMJFrame().setFocused(this);
	}
	
	public boolean isEnabled()
	{
		return enabled;
	}
	
	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}
	
	/**Request a repaint upon this component and also request a refresh upon the supercomponent.
	 * 
	 */
	public void requestRepaint()
	{
		window.getRenderManager().render(this);
		refreshRequested = true;
	}
	
	/**Request a repaint upon this component and optionally request a refresh upon the supercomponent.
	 * 
	 * @param refresh
	 */
	public void requestRepaint(boolean refresh)
	{
		window.getRenderManager().render(this);
		if (refresh)
		{
			refreshRequested = true;
		}
	}
	
	public void setVisibleToEvents(boolean visible)
	{
		visibleToEvents = visible;
	}
	
	public boolean isVisibleToEvents()
	{
		return visibleToEvents;
	}
	
	@Deprecated
	public MJComponent getComponentAtOld(int x, int y)
	{
		for (int i = subcomponents.size() - 1 ; i >= 0 ; i--)
		{
			for (MJComponent c : subcomponents.get(i))
			{
				int prevX = x;
				int prevY = y;
				if (c.hasRotation())
				{
					Point p = MathUtils.rotate(c.getScaledCenter(), new Point(x, y), c.getRotation());
					x = (int) p.getX();
					y = (int) p.getY();
				}
				// OldTODO: Values are not rotated. Add rotate point method in MJComponent?
				if ((c.getScaledX() + c.getExcessX() - (c.getImageWidthPixelsExpanded() / 2) <= x 
						&& c.getScaledEndX() + c.getExcessX() + (c.getImageWidthPixelsExpanded() / 2) > x) 
						&& (c.getScaledY() + c.getExcessY() - (c.getImageHeightPixelsExpanded() / 2) <= y 
						&& c.getScaledEndY() + c.getExcessY() + (c.getImageHeightPixelsExpanded() / 2) > y)
						&& c.isVisibleToEvents()
				&& (c.getCurrentImage() != null 
						&& c.getCurrentImage().getWidth() > prevX - c.getScaledX()
						&& 0 <= prevX - c.getScaledX()
						&& c.getCurrentImage().getHeight() > prevY - c.getScaledY()
						&& 0 <= prevY - c.getScaledY()
				&& (((c.getCurrentImage().getRGB(x - c.getScaledX() - c.getExcessX() + (c.getImageWidthPixelsExpanded() / 2), y - c.getScaledY() - c.getExcessY() + (c.getImageHeightPixelsExpanded() / 2)) >> 24) & 0xff) != 0)))
				{
					return c.getComponentAt(x - c.getScaledX() + c.getExcessX() - (c.getImageWidthPixelsExpanded() / 2), y - c.getScaledY() + c.getExcessY() - (c.getImageHeightPixelsExpanded() / 2));
				}
			}
		}
		return this;
	}
	
	// Experimental
	
	public MJComponent getComponentAt(int x, int y)
	{
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
		return this;
	}
	
	public int getActualScaledX()
	{
		int x = getScaledX();
		x += getExcessX() / 2;
		MJComponent c = this;
		while (c.hasSupercomponent())
		{
			c = c.getSupercomponent();
			x += c.getScaledX();
			x += c.getExcessX() / 2;
		}
		return x;
	}
	
	public int getActualScaledY()
	{
		int y = getScaledY();
		y += getExcessY();
		MJComponent c = this;
		while (c.hasSupercomponent())
		{
			c = c.getSupercomponent();
			y += c.getScaledY();
			y += c.getExcessY();
		}
		return y;
	}
	
	public int getActualScaledEndX()
	{
		return getActualScaledX() + getScaledWidth();
	}
	
	public int getActualScaledEndY()
	{
		return getActualScaledY() + getScaledHeight();
	}
	
	/**Determines if the point is inside the component with all rotations applied.
	 * 
	 * @param point - The point to be determined whether inside component or not.
	 * @return Whether point is inside component or not.
	 */
	public boolean isInside(Point point)
	{
		if (getScaledShape().contains(point))
		{
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @return The final shape of the component given all rotations.
	 */
	public Polygon getScaledShape()
	{
		Polygon p = new Polygon();
		Point p1 = getPoint1();
		Point p2 = getPoint2();
		Point p3 = getPoint3();
		Point p4 = getPoint4();
		p.addPoint(p1.x, p1.y);
		p.addPoint(p2.x, p2.y);
		p.addPoint(p3.x, p3.y);
		p.addPoint(p4.x, p4.y);
		return p;
	}
	
	/**Point 1 for Polygon bounds.
	 * 
	 * @return The top left point with rotation applied.
	 */
	private Point getPoint1()
	{
		Point p = MathUtils.rotate(getGlobalScaledCenter(), new Point(getActualScaledX(), getActualScaledY()), -getRotation());
		MJComponent c = this;
		while (c.hasSupercomponent())
		{
			
			c = c.getSupercomponent();
			p = MathUtils.rotate(c.getGlobalScaledCenter(), p, -c.getRotation());
		}
		return p;
	}
	
	/**Point 2 for Polygon bounds.
	 * 
	 * @return The top right point with rotation applied.
	 */
	private Point getPoint2()
	{
		Point p = MathUtils.rotate(getGlobalScaledCenter(), new Point(getActualScaledEndX(), getActualScaledY()), -getRotation());
		MJComponent c = this;
		while (c.hasSupercomponent())
		{
			c = c.getSupercomponent();
			p = MathUtils.rotate(c.getGlobalScaledCenter(), p, -c.getRotation());
		}
		return p;
	}
	
	/**Point 3 for Polygon bounds.
	 * 
	 * @return The bottom left point with rotation applied.
	 */
	private Point getPoint3()
	{
		Point p = MathUtils.rotate(getGlobalScaledCenter(), new Point(getActualScaledEndX(), getActualScaledEndY()), -getRotation());
		MJComponent c = this;
		while (c.hasSupercomponent())
		{
			c = c.getSupercomponent();
			p = MathUtils.rotate(c.getGlobalScaledCenter(), p, -c.getRotation());
		}
		return p;
	}
	
	/**Point 4 for Polygon bounds.
	 * 
	 * @return The bottom right point with rotation applied.
	 */
	private Point getPoint4()
	{
		Point p = MathUtils.rotate(getGlobalScaledCenter(), new Point(getActualScaledX(), getActualScaledEndY()), -getRotation());
		MJComponent c = this;
		while (c.hasSupercomponent())
		{
			c = c.getSupercomponent();
			p = MathUtils.rotate(c.getGlobalScaledCenter(), p, -c.getRotation());
		}
		return p;
	}
	
	// End Experimental
	
	public void componentAdded()
	{
		window.getRenderManager().render(this);
	}
	
	public synchronized void addPaintHandler(PaintHandler handler)
	{
		paintHandlers.add(handler);
	}
	
	public synchronized void removePaintHandler(PaintHandler handler)
	{
		paintHandlers.remove(handler);
	}
	
	public synchronized BufferedImage executePaintHandlers(BufferedImage drawing, BufferedImage initial)
	{
		BufferedImage modified = GraphicsUtils.getImage(drawing.getWidth(), drawing.getHeight());
		modified.createGraphics().drawImage(initial, getImageWidthPixelsExpanded() / 2, getImageHeightPixelsExpanded() / 2, getWindow().getMJFrame());
		for (PaintHandler handler : paintHandlers)
		{
			if (handler.needsHandle())
			{
				drawing = GraphicsUtils.getImage(drawing.getWidth(), drawing.getHeight());
				handler.handlePaint(drawing.createGraphics(), modified, initial);
				Graphics2D g = modified.createGraphics();
				g.setBackground(new Color(0, 0, 0, 0));
				g.clearRect(0, 0, modified.getWidth(), modified.getHeight());
				g.drawImage(drawing, 0, 0, getWindow().getMJFrame());
			}
		}
		return modified;
	}
	
	public void registerListener(Listener l)
	{
		listeners.add(l);
	}
	
	public void unregisterListener(Listener l)
	{
		listeners.remove(l);
	}
	
	public List<? extends Listener> getListeners(String ID)
	{
		List<Listener> list = new ArrayList<Listener>();
		for (Listener l : listeners)
		{
			if (l.getName().equals(ID))
			{
				list.add(l);
			}
		}
		return list;
	}
	
	@Override
	public void paint(Graphics2D g)
	{
		
	}
	
	@Override
	public void paintComplete()
	{
		if (refreshRequested)
		{
			if (hasSupercomponent())
			{
				supercomponent.requestRepaint();
			}
			else
			{
				window.requestRepaint();
			}
		}
	}
}
