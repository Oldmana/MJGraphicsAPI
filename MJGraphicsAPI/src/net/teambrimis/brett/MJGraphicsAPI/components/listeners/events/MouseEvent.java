package net.teambrimis.brett.MJGraphicsAPI.components.listeners.events;

public class MouseEvent extends Event
{
	private int x;
	private int y;
	
	private MouseButton button;
	
	public MouseEvent(int x, int y, MouseButton button)
	{
		this.x = x;
		this.y = y;
		
		this.button = button;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public MouseButton getButton()
	{
		return button;
	}
	
	public enum MouseButton
	{
		LEFT, MIDDLE, RIGHT
	}
}
