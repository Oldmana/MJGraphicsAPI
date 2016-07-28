package net.teambrimis.brett.MJGraphicsAPI.components.listeners.events;

public class MouseWheelEvent extends MouseEvent
{
	private WheelDirection dir;
	private int amount;
	
	public MouseWheelEvent(int x, int y, WheelDirection dir)
	{
		super(x, y, null);
		this.dir = dir;
	}
	
	public WheelDirection getDirection()
	{
		return dir;
	}
	
	/**Gives the absolute value of the amount scrolled.
	 * 
	 * @return Amount scrolled
	 */
	public int getScrollAmount()
	{
		return amount;
	}
	
	public enum WheelDirection
	{
		UP, DOWN
	}
}
