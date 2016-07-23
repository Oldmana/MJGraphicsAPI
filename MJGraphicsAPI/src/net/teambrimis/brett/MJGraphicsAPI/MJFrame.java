package net.teambrimis.brett.MJGraphicsAPI;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JFrame;

import net.teambrimis.brett.MJGraphicsAPI.components.MJComponent;
import net.teambrimis.brett.MJGraphicsAPI.components.listeners.FocusChangeListener;
import net.teambrimis.brett.MJGraphicsAPI.components.listeners.KeyPressListener;
import net.teambrimis.brett.MJGraphicsAPI.components.listeners.KeyReleaseListener;
import net.teambrimis.brett.MJGraphicsAPI.components.listeners.Listener;
import net.teambrimis.brett.MJGraphicsAPI.components.listeners.MouseClickListener;
import net.teambrimis.brett.MJGraphicsAPI.components.listeners.MouseMoveListener;
import net.teambrimis.brett.MJGraphicsAPI.components.listeners.MousePressListener;
import net.teambrimis.brett.MJGraphicsAPI.components.listeners.events.MouseEvent.MouseButton;
import net.teambrimis.brett.MJGraphicsAPI.utils.Scaling;

public class MJFrame extends JFrame
{
	private static final long serialVersionUID = -7830167559573949521L;
	
	@SuppressWarnings("unused")
	private MJGraphicsWindow window;
	
	private JComponent canvas;
	
	private Map<String, MJPanel> panels = new HashMap<String, MJPanel>();
	private MJPanel enabledPanel;
	
	private MJComponent hovered;
	private MJComponent focused;
	
	private boolean shifting = false;
	
	protected MJFrame(MJGraphicsWindow window, String title, int width, int height)
	{
		super(title);
		this.window = window;
		MJPanel mainPanel = createPanel("Main");
		setEnabledPanel(mainPanel);
		Scaling.setupJFrame(this, width, height);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((int) (d.getWidth() / 2) - (Scaling.scale(width) / 2), (int) (d.getHeight() / 2) - (Scaling.scale(height) / 2));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		canvas = new JComponent()
		{
			private static final long serialVersionUID = 1L;
			
			@Override
			public void paintComponent(Graphics g)
			{
				((Graphics2D) g).drawImage(MJFrame.this.enabledPanel.getCurrentImage(), 0, 0, this);
			}
		};
		canvas.setBounds(0, 0, Scaling.scale(width), Scaling.scale(height));
		canvas.setFocusable(true);
		MouseAdapter listener = new MouseAdapter()
		{
			@SuppressWarnings("unchecked")
			@Override
			public void mousePressed(MouseEvent event)
			{
				MJComponent c = getEnabledPanel().getComponentAt(event.getX(), event.getY());
				if (c != null/** && c instanceof MousePressListener**/)
				{
					MouseButton button = null;
					if (event.getButton() == MouseEvent.BUTTON1)
					{
						button = MouseButton.LEFT;
					}
					else if (event.getButton() == MouseEvent.BUTTON2)
					{
						button = MouseButton.MIDDLE;
					}
					else if (event.getButton() == MouseEvent.BUTTON3)
					{
						button = MouseButton.RIGHT;
					}
					for (MousePressListener l : (List<MousePressListener>) c.getListeners(MousePressListener.EVENT_ID))
					{
						l.onMousePress(new net.teambrimis.brett.MJGraphicsAPI.components.listeners.events.MouseEvent(event.getX(), event.getY(), button));
					}
					//((MousePressListener) c).onMousePress(new net.teambrimis.brett.MJGraphicsAPI.components.listeners.events.MouseEvent(event.getX(), event.getY()));
				}
			}
			
			@SuppressWarnings("unchecked")
			@Override
			public void mouseReleased(MouseEvent event)
			{
				MJComponent c = getEnabledPanel().getComponentAt(event.getX(), event.getY());
				
				if (c != null && !c.isEnabled())
				{
					return;
				}
				
				if (focused != null)
				{
					for (FocusChangeListener l : (List<FocusChangeListener>) focused.getListeners(FocusChangeListener.EVENT_ID))
					{
						l.onFocusChange(focused, c);
					}
					//((FocusChangeListener) focused).onFocusChange(focused, c);
				}
				if (c != null)
				{
					MouseButton button = null;
					if (event.getButton() == MouseEvent.BUTTON1)
					{
						button = MouseButton.LEFT;
					}
					else if (event.getButton() == MouseEvent.BUTTON2)
					{
						button = MouseButton.MIDDLE;
					}
					else if (event.getButton() == MouseEvent.BUTTON3)
					{
						button = MouseButton.RIGHT;
					}
					for (MouseClickListener l : (List<MouseClickListener>) c.getListeners(MouseClickListener.EVENT_ID))
					{
						l.onMouseClick(new net.teambrimis.brett.MJGraphicsAPI.components.listeners.events.MouseEvent(event.getX(), event.getY(), button));
					}
						
						//((MouseClickListener) c).onMouseClick(new net.teambrimis.brett.MJGraphicsAPI.components.listeners.events.MouseEvent(event.getX(), event.getY()));
					
					for (FocusChangeListener l : (List<FocusChangeListener>) c.getListeners(FocusChangeListener.EVENT_ID))
					{
						l.onFocusChange(focused, c);
					}
				}
				
				focused = c;
			}
			
			@SuppressWarnings("unchecked")
			@Override
			public void mouseMoved(MouseEvent event)
			{
				MJComponent c = getEnabledPanel().getComponentAt(event.getX(), event.getY());
				if (c == null)
				{
					if (hovered != null)
					{
						for (MouseMoveListener l : (List<MouseMoveListener>) hovered.getListeners(MouseMoveListener.EVENT_ID))
						{
							l.onMouseExited();
						}
					}
					hovered = null;
				}
				else if (c != hovered)
				{
					if (hovered != null)
					{
						for (MouseMoveListener l : (List<MouseMoveListener>) hovered.getListeners(MouseMoveListener.EVENT_ID))
						{
							l.onMouseExited();
						}
					}
					
					for (MouseMoveListener l : (List<MouseMoveListener>) c.getListeners(MouseMoveListener.EVENT_ID))
					{
						l.onMouseExited();
					}
					hovered = c;
				}
			}
		};
		canvas.addMouseListener(listener);
		canvas.addMouseMotionListener(listener);
		
		canvas.addKeyListener(new KeyAdapter()
		{
			@SuppressWarnings("unchecked")
			@Override
			public void keyPressed(KeyEvent event)
			{
				if (event.getKeyCode() == KeyEvent.VK_SHIFT)
				{
					shifting = true;
				}
				if (focused != null)
				{
					for (KeyPressListener l : (List<KeyPressListener>) focused.getListeners(KeyPressListener.EVENT_ID))
					{
						l.onKeyPress(new net.teambrimis.brett.MJGraphicsAPI.components.listeners.events.KeyEvent(event.getKeyCode(), event.getKeyChar(), 0, 0));
					}
				}
			}
			
			@SuppressWarnings("unchecked")
			@Override
			public void keyReleased(KeyEvent event)
			{
				
				if (event.getKeyCode() == KeyEvent.VK_SHIFT)
				{
					shifting = false;
				}
				if (focused != null)
				{
					for (KeyReleaseListener l : (List<KeyReleaseListener>) focused.getListeners(KeyReleaseListener.EVENT_ID))
					{
						l.onKeyRelease(new net.teambrimis.brett.MJGraphicsAPI.components.listeners.events.KeyEvent(event.getKeyCode(), event.getKeyChar(), 0, 0));
					}
				}
			}
		});
		
		add(canvas);
		pack();
		setVisible(true);
		System.out.println(getBounds());
	}
	
	public void repaintCanvas()
	{
		canvas.repaint();
	}
	
	public MJPanel createPanel(String name)
	{
		MJPanel p = new MJPanel(name);
		panels.put(name, p);
		return p;
	}
	
	public MJPanel getPanel(String name)
	{
		return panels.get(name);
	}
	
	public void setEnabledPanel(MJPanel p)
	{
		enabledPanel = p;
		// TODO
	}
	
	public MJPanel getEnabledPanel()
	{
		return enabledPanel;
	}
	
	public MJComponent getHovered()
	{
		return hovered;
	}
	
	public MJComponent getFocused()
	{
		return focused;
	}
	
	public void setFocused(MJComponent c)
	{
		focused = c;
		
		// TODO: Fire FocusChangeEvent
	}
	
	public boolean isShifting()
	{
		return shifting;
	}
	
	public void setShifting(boolean shifting)
	{
		this.shifting = shifting;
	}
	
	public class MJPanel
	{
		private String name;
		
		private List<List<MJComponent>> components;
		
		private BufferedImage currentImage;
		
		private MJPanel(String name)
		{
			this.name = name;
			
			components = new ArrayList<List<MJComponent>>();
		}
		
		public String getName()
		{
			return name;
		}
		
		public synchronized void setCurrentImage(BufferedImage image)
		{
			currentImage = image;
		}
		
		public BufferedImage getCurrentImage()
		{
			return currentImage;
		}
		
		public void addComponent(int layer, MJComponent c)
		{
			if (components.size() <= layer)
			{
				for (int i = 0 ; i <= 1 + layer - components.size() ; i++)
				{
					components.add(new ArrayList<MJComponent>());
				}
			}
			
			//if (components.get(layer) == null)
			//{
			//	components.set(layer, new ArrayList<MJComponent>());
			//}
			
			components.get(layer).add(c);
			c.componentAdded();
		}
		
		public void removeComponent(MJComponent c)
		{
			for (List<MJComponent> layer : components)
			{
				if (layer.remove(c))
				{
					break;
				}
			}
		}
		
		public List<List<MJComponent>> getComponents()
		{
			return components;
		}
		
		public MJComponent getComponentAt(int x, int y)
		{
			/**
			for (int i = components.size() - 1 ; i >= 0 ; i--)
			{
				for (MJComponent c : components.get(i))
				{
					int prevX = x;
					int prevY = y;
					System.out.println("Prev X: " + x);
					if (c.hasRotation())
					{
						System.out.println(c.getScaledCenter());
						Point p = MathUtils.rotate(c.getScaledCenter(), new Point(x, y), c.getRotation());
						//System.out.println(p);
						x = (int) p.getX();
						y = (int) p.getY();
						System.out.println(x);
					}
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
					&& (((c.getCurrentImage().getRGB(prevX - c.getScaledX() - c.getExcessX() + (c.getImageWidthPixelsExpanded() / 2), prevY - c.getScaledY() - c.getExcessY() + (c.getImageHeightPixelsExpanded() / 2)) >> 24) & 0xff) != 0)))
					{
						return c.getComponentAt(x - c.getScaledX() + c.getExcessX() - (c.getImageWidthPixelsExpanded() / 2), y - c.getScaledY() + c.getExcessY() - (c.getImageHeightPixelsExpanded() / 2));
					}
				}
			}
			return null;
			**/
			for (int i = components.size() - 1 ; i >= 0 ; i--)
			{
				for (MJComponent c : components.get(i))
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
			return null;
		}
	}
}
