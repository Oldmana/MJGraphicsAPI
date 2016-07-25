package net.teambrimis.brett.MJGraphicsAPI.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import net.teambrimis.brett.MJGraphicsAPI.MJGraphicsWindow;
import net.teambrimis.brett.MJGraphicsAPI.components.listeners.FocusChangeListener;
import net.teambrimis.brett.MJGraphicsAPI.components.listeners.KeyReleaseListener;
import net.teambrimis.brett.MJGraphicsAPI.components.listeners.MouseClickListener;
import net.teambrimis.brett.MJGraphicsAPI.components.listeners.MouseMoveListener;
import net.teambrimis.brett.MJGraphicsAPI.components.listeners.events.KeyEvent;
import net.teambrimis.brett.MJGraphicsAPI.components.listeners.events.MouseEvent;
import net.teambrimis.brett.MJGraphicsAPI.components.props.ButtonColors;
import net.teambrimis.brett.MJGraphicsAPI.rendering.Animation;
import net.teambrimis.brett.MJGraphicsAPI.rendering.AnimationHover;
import net.teambrimis.brett.MJGraphicsAPI.rendering.RenderingTask;
import net.teambrimis.brett.MJGraphicsAPI.utils.GraphicsUtils;
import net.teambrimis.brett.MJGraphicsAPI.utils.Scaling;

public class MJButton extends MJComponent
{
	private String text;
	
	private int stage = 0;
	private int maxStage = 10;
	
	private ButtonColors colors = new ButtonColors();
	
	private MouseMoveListener MML = new MouseMoveListener()
	{
		@Override
		public void onMouseEntered()
		{
			System.out.println("Entered");
			if (isEnabled())
			{
				requestRepaint();
			}
		}

		@Override
		public void onMouseExited()
		{
			System.out.println("Exited");
			if (isEnabled())
			{
				requestRepaint();
			}
		}
		
		@Override
		public void onMouseDrag()
		{
			// TODO Auto-generated method stub
			
		}
	};
	
	private MouseClickListener MCL = new MouseClickListener()
	{
		@Override
		public void onMouseClick(MouseEvent event)
		{
			System.out.println("Click!");
		}
	};
	
	private KeyReleaseListener KRL = new KeyReleaseListener()
	{
		@Override
		public void onKeyRelease(KeyEvent event)
		{
			
		}
	};
	
	private FocusChangeListener FCL = new FocusChangeListener()
	{
		@Override
		public void onFocusChange(MJComponent previous, MJComponent next)
		{
			requestRepaint();
		}
	};
	
	{
		registerListener(MML);
		registerListener(MCL);
		registerListener(KRL);
		
		registerListener(FCL);
	}
	
	private AnimationHover hoverAnim = new AnimationHover(this, 100);
	{
		hoverAnim.setMax(10);
	}
	public MJButton(MJGraphicsWindow window, int x, int y, int width, int height, int layer, String text)
	{
		super(window, x, y, width, height, layer);
		
		this.text = text;
	}
	
	public MJButton(MJGraphicsWindow window, int layer, String text)
	{
		super(window, 0, 0, 1, 1, layer);
		
		this.text = text;
	}
	
	public String getText()
	{
		return text;
	}
	
	public ButtonColors getButtonColors()
	{
		return colors;
	}
	
	int s = 1;
	
	@Override
	public void paint(Graphics2D g)
	{
		//if (getCache().has("R" + (s * 45)))
		{
			//g.drawImage(getCache().get("R" + (s * 45)), 0, 0, getWindow().getMJFrame());
		}
		//else
		{
		//System.out.println(getX() + " " + getY());
		g.setColor(colors.getCurrentColor(isEnabled(), hoverAnim.getState(), hoverAnim.getMax()));
		g.fillRect(0, 0, getScaledWidth(), getScaledHeight());
		g.setColor(colors.getCurrentBorderColor(isEnabled(), hoverAnim.getState(), hoverAnim.getMax()));
		g.drawRect(0, 0, getScaledWidth() - 1, getScaledHeight() - 1);
		if (isPrimeFocused())
		{
			g.setColor(new Color(0, 0, 255 - (hoverAnim.getState() * 10)));
			g.fillRect(getScaledWidth() / 4, getScaledHeight() / 4, getScaledWidth() / 2, getScaledHeight() / 2);
			g.setColor(Color.RED);
			g.setFont(Scaling.getFont(Font.PLAIN, 8));
		}
		g.setColor(colors.getCurrentTextColor(isEnabled()));
		g.setFont(Scaling.getFont(Font.PLAIN, 3));
		FontMetrics fm = g.getFontMetrics();
		int width = g.getFontMetrics().stringWidth(getText());
		int y = fm.getAscent() + (getScaledHeight() - (fm.getAscent() + fm.getDescent())) / 2;
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.drawString(getText(), (getScaledWidth() / 2) - (width / 2), y);
		}
		if (s == 7)
		{
			s = 0;
		}
		else
		{
			s++;
		}
		
	}

	@Override
	public void componentAdded()
	{
		super.componentAdded();
		
		hoverAnim = new AnimationHover(this, 100);
		hoverAnim.setMax(10);
		addAnimation(hoverAnim);
		
		Timer timer = new Timer(1, new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				RenderingTask t = new RenderingTask(getWidth(), getHeight())
				{
					private int progress = 1;
					
					@Override
					public void paint(Graphics2D g)
					{
						System.out.println("Progress: " + progress);
						if (progress <= 7)
						{
							GraphicsUtils.drawRotatedImage(g, MJButton.this.getCurrentImage(), progress * 45, getScaledWidth(), getScaledHeight());
						}
						
						if (progress != 7)
						{
							getWindow().getRenderManager().render(this);
						}
						progress++;
					}
					
					@Override
					public void paintComplete()
					{
						getCache().set("R" + (45 * (progress - 1)), getCurrentImage());
					}
				};
				getWindow().getRenderManager().render(t);
			}
			
		});
		timer.setRepeats(false);
		timer.start();
	}
}
