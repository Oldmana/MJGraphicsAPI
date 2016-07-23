package net.teambrimis.brett.MJGraphicsAPI.utils;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class ImageCache
{
	private Map<String, BufferedImage> cache = new HashMap<String, BufferedImage>();
	
	public ImageCache()
	{
		
	}
	
	public synchronized void set(String key, BufferedImage image)
	{
		cache.put(key, image);
	}
	
	public synchronized BufferedImage get(String key)
	{
		return cache.get(key);
	}
	
	public synchronized boolean has(String key)
	{
		return cache.containsKey(key);
	}
	
	public synchronized void delete(String key)
	{
		cache.remove(key);
	}
}
