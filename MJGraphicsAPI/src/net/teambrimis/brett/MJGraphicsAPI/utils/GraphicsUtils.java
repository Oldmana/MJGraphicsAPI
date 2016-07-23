package net.teambrimis.brett.MJGraphicsAPI.utils;

import java.awt.Canvas;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class GraphicsUtils
{
	private static Canvas canvas;
	
	static
	{
		Frame f = new Frame();
		canvas = new Canvas();
		f.add(canvas);
	}
	
	public static GraphicsConfiguration getGraphicsConfiguration()
	{
		return canvas.getGraphicsConfiguration();
	}
	
	public static BufferedImage getImage(int width, int height)
	{
		return getGraphicsConfiguration().createCompatibleImage(width, height, 3);
	}
	
	public static void drawRotatedImage(Graphics2D g, BufferedImage image, double degrees, int width, int height)
	{
		double rotationRequired = Math.toRadians(degrees);
		double centerX = width / 2;
		double centerY = height / 2;
		AffineTransform prev = g.getTransform();
		g.rotate(rotationRequired, centerX, centerY);
		g.drawImage(image, 0, 0, null);
		g.setTransform(prev);
	}
}
