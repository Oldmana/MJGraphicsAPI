package net.teambrimis.brett.MJGraphicsAPI.utils;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;

public class MathUtils
{
	public static Point rotate(Point center, Point point, double angle)
	{
		angle = Math.toRadians(-angle);
		double s = Math.sin(angle);
		double c = Math.cos(angle);
		
		point.x -= center.x;
		point.y -= center.y;
		
		double rX = point.x * c - point.y * s;
		double rY = point.x * s + point.y * c;
		
		return new Point((int) rX + center.x, (int) rY + center.y/**(int) Math.round(rX), (int) Math.round(rY)**/);
	}
	
	public static int[] getWidthHeightWithRotation(Rectangle r, double rotation)
	{
		int[] wh = new int[2];
		Point center = new Point((int) r.getCenterX(), (int) r.getCenterY());
		Polygon p = new Polygon();
		Point p1 = rotate(center, new Point((int) r.getMinX(), (int) r.getMinY()), rotation);
		Point p2 = rotate(center, new Point((int) r.getMaxX(), (int) r.getMinY()), rotation);
		Point p3 = rotate(center, new Point((int) r.getMaxX(), (int) r.getMaxY()), rotation);
		Point p4 = rotate(center, new Point((int) r.getMinX(), (int) r.getMaxY()), rotation);
		p.addPoint((int) p1.getX(), (int) p1.getY());
		p.addPoint((int) p2.getX(), (int) p2.getY());
		p.addPoint((int) p3.getX(), (int) p3.getY());
		p.addPoint((int) p4.getX(), (int) p4.getY());
		Rectangle pB = p.getBounds();
		wh[0] = (int) pB.getWidth();
		wh[1] = (int) pB.getHeight();
		return wh;
	}
	
	public static boolean isInside(Point p, Rectangle r, double rotation)
	{
		Point center = new Point((int) r.getCenterX(), (int) r.getCenterY());
		Polygon polygon = new Polygon();
		Point p1 = rotate(center, new Point((int) r.getMinX(), (int) r.getMinY()), rotation);
		Point p2 = rotate(center, new Point((int) r.getMaxX(), (int) r.getMinY()), rotation);
		Point p3 = rotate(center, new Point((int) r.getMaxX(), (int) r.getMaxY()), rotation);
		Point p4 = rotate(center, new Point((int) r.getMinX(), (int) r.getMaxY()), rotation);
		polygon.addPoint((int) p1.getX(), (int) p1.getY());
		polygon.addPoint((int) p2.getX(), (int) p2.getY());
		polygon.addPoint((int) p3.getX(), (int) p3.getY());
		polygon.addPoint((int) p4.getX(), (int) p4.getY());
		return polygon.contains(p);
	}
}
