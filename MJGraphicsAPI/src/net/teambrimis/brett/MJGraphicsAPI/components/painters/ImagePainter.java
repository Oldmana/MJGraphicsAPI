package net.teambrimis.brett.MJGraphicsAPI.components.painters;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class ImagePainter extends Painter
{
	private Rectangle bounds;
	
	private BufferedImage image;
	
	public ImagePainter(Rectangle bounds, BufferedImage image)
	{
		this.bounds = bounds;
		
		this.image = image;
	}
	
	@Override
	public void paint(Graphics2D whole)
	{
		Graphics2D g = (Graphics2D) whole.create((int) bounds.getMinX(), (int) bounds.getMinY(), (int) bounds.getWidth(), 
				(int) bounds.getHeight());
		g.drawImage(image, 0, 0, null);
	}
}
