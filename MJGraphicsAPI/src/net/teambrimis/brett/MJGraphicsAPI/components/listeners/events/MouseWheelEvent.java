package net.teambrimis.brett.MJGraphicsAPI.components.listeners.events;

public class MouseWheelEvent extends MouseEvent
{
	private WheelDirection dir;
	
	public MouseWheelEvent(int x, int y, WheelDirection dir)
	{
		super(x, y, null);
		this.dir = dir;
	}
	
	public WheelDirection getDirection()
	{
		return dir;
	}
	
	public enum WheelDirection
	{
		UP, DOWN, LEFT, RIGHT
	}
}
