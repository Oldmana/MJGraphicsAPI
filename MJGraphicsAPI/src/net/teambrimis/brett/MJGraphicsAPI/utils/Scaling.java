package net.teambrimis.brett.MJGraphicsAPI.utils;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Scaling
{
	public static int SCALE = Math.min(Toolkit.getDefaultToolkit().getScreenSize().width / 192, Toolkit.getDefaultToolkit().getScreenSize().height / 108);
	
	private static int excessX = Toolkit.getDefaultToolkit().getScreenSize().width % 192;
	private static int excessY = Toolkit.getDefaultToolkit().getScreenSize().height % 108;
	
	private static Font font = new JButton().getFont();
	
	public void setGraphicsSize(int width, int height)
	{
		SCALE = Math.min(Toolkit.getDefaultToolkit().getScreenSize().width / width, Toolkit.getDefaultToolkit().getScreenSize().height / height);
		
		excessX = Toolkit.getDefaultToolkit().getScreenSize().width % width;
		excessY = Toolkit.getDefaultToolkit().getScreenSize().height % height;
	}
	
	public static void setupJFrame(JFrame frame, int xSize, int ySize)
	{
		frame.setPreferredSize(getDimension(xSize, ySize));
		frame.setLayout(null);
	}
	
	public static Dimension getDimension(int xSize, int ySize)
	{
		System.out.println(new Dimension(xSize * SCALE, ySize * SCALE));
		return new Dimension(xSize * SCALE, ySize * SCALE);
	}
	
	public static Rectangle getRectangle(int startX, int startY, int endX, int endY)
	{
		return new Rectangle(startX * SCALE, startY * SCALE, (endX - startX) * SCALE, (endY - startY) * SCALE);
	}
	
	public static Rectangle getRectangleWithExcess(int startX, int startY, int endX, int endY)
	{
		return new Rectangle((startX * SCALE) + (excessX / 2), (startY * SCALE) + (excessY / 2), (endX - startX) * SCALE, (endY - startY) * SCALE);
	}
	
	public static Font getFont(int type, double size)
	{
		return new Font(font.getName(), type, (int) Math.floor(SCALE * size));
	}
	
	public static int scale(int v)
	{
		return v * SCALE;
	}
	
	public static int descale(int v)
	{
		return (int) Math.floor(v / SCALE);
	}
	
	public static int getExcess(int v)
	{
		return v % SCALE;
	}
	
	public static Rectangle getScreenSize()
	{
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		return new Rectangle(0, 0, (int) d.getWidth(), (int) d.getHeight());
	}
}
