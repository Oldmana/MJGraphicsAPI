package net.teambrimis.brett.MJGraphicsAPI;

import net.teambrimis.brett.MJGraphicsAPI.components.MJComponent;
import net.teambrimis.brett.MJGraphicsAPI.rendering.RenderManager;

public class MJGraphicsWindow
{
	private MJFrame frame;
	
	private RenderManager renderManager;
	
	public MJGraphicsWindow(String title, int width, int height)
	{
		frame = new MJFrame(this, title, width, height);
		
		renderManager = new RenderManager(this, 1);
	}
	
	public MJFrame getMJFrame()
	{
		return frame;
	}
	
	public RenderManager getRenderManager()
	{
		return renderManager;
	}
	
	public void addComponent(MJComponent c)
	{
		renderManager.addComponent(c.getLayer(), c);
	}
	
	public MJComponent getHovered()
	{
		return frame.getHovered();
	}
	
	public MJComponent getFocused()
	{
		return frame.getFocused();
	}
	
	public void requestRepaint()
	{
		renderManager.renderFrame();
	}
}
