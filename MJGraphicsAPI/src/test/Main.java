package test;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import net.teambrimis.brett.MJGraphicsAPI.MJGraphicsWindow;
import net.teambrimis.brett.MJGraphicsAPI.components.MJButton;
import net.teambrimis.brett.MJGraphicsAPI.components.MJComponent;
import net.teambrimis.brett.MJGraphicsAPI.components.MJProgressBar;
import net.teambrimis.brett.MJGraphicsAPI.components.MJScrollPane;
import net.teambrimis.brett.MJGraphicsAPI.components.MJTest;
import net.teambrimis.brett.MJGraphicsAPI.components.MJTextBox;
import net.teambrimis.brett.MJGraphicsAPI.components.PolygonOutline;
import net.teambrimis.brett.MJGraphicsAPI.components.painters.TextPainter;
import net.teambrimis.brett.MJGraphicsAPI.rendering.AnimationMove;
import net.teambrimis.brett.MJGraphicsAPI.rendering.AnimationRotation;
import net.teambrimis.brett.MJGraphicsAPI.utils.GraphicsUtils;
import net.teambrimis.brett.MJGraphicsAPI.utils.Scaling;

public class Main
{
	public static int stage = 0;
	
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				final MJGraphicsWindow window = new MJGraphicsWindow("Testing", 50, 50);
				final MJComponent c = new MJComponent(window, 0, 0, 50, 50, 0)
				{
					@Override
					public void paint(Graphics2D g)
					{
						g.setColor(Color.RED);
						g.fillRect(0, 0, 12 + stage, 46);
						g.setColor(new Color(Math.max(0, 255 - stage), 0, 0, Math.max(0, 255 - stage)));
						g.fillRect(0, 46, 12 + stage, stage);
						window.requestRepaint();
					}
					
					
				};
				final MJButton b = new MJButton(window, 1, "Test");
				System.out.println(Scaling.getRectangle(5, 5, 20, 10));
				b.setBounds(new Rectangle(5, 5, 20, 5));
				window.addComponent(b);
				MJButton b2 = new MJButton(window, 0, "Button2");
				b2.setBounds(new Rectangle(1, 1, 10, 2));
				b.addSubcomponent(0, b2);
				
				MJButton b4 = new MJButton(window, 0, "Button 3");
				b4.setEnabled(false);
				b4.setBounds(new Rectangle(18, 18, 14, 14));
				window.addComponent(b4);
				
				MJProgressBar bar = new MJProgressBar(window, 20, 5, 20, 5, 0, 0, 100);
				bar.setValue(90);
				window.addComponent(bar);
				
				MJScrollPane sp = new MJScrollPane(window, 0, 0, 15, 15, 1);
				
				MJButton spb = new MJButton(window, 0, "SP Button");
				spb.setBounds(new Rectangle(0, 0, 25, 25));
				sp.wrapComponent(spb);
				spb.requestRepaint();
				
				window.addComponent(sp);
				
				
				//MJTest test = new MJTest(window, 5, 5, 45, 45, 2);
				//window.addComponent(test);
				
				//MJTextBox box = new MJTextBox(window, 10, 10, 20, 20, 2);
				//window.addComponent(box);
				
				AnimationMove an = new AnimationMove(b, 10, 20, 30);
				AnimationRotation a = new AnimationRotation(b, 25, 200);
				b.addAnimation(an);
				b.addAnimation(a);
				an.setMax(200);
				a.setMax(80);
				//window.getRenderManager().animate(a);
				//window.getRenderManager().animate(an);
				window.addComponent(c);
				
				MJButton b3 = new MJButton(window, 0, "Button3");
				b3.setBounds(new Rectangle(5, 15, 15, 5));
				//window.addComponent(b3);
				System.out.println(window.getMJFrame().getEnabledPanel().getComponentAt(5, 5));
				/**
				Timer t = new Timer(20, new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent arg0)
					{
						stage++;
						c.requestRepaint();
						b.requestRepaint();
						//window.requestRepaint();
					}
				});
				t.start();
				**/
				//window.requestRepaint();
			}
		});
	}
}
