package net.teambrimis.brett.MJGraphicsAPI.components.listeners.events;

import java.awt.Component;

public class KeyEvent
{
	private int key;
	private char typedChar;
	
	private int x;
	private int y;
	
	public KeyEvent(int key, char typedChar, int x, int y)
	{
		this.key = key;
		this.typedChar = typedChar;
		
		this.x = x;
		this.y = y;
	}
	
	public int getKey()
	{
		return key;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public boolean hasTypedChar()
	{
		//return Character.isDefined(typedChar);
		//return Character.isAlphabetic(typedChar) || Character.isDigit(typedChar) || Character.isWhitespace(typedChar);
		System.err.println(typedChar + " | Code: " + ((int) typedChar));
		return typedChar != 65535 && typedChar != 10 && typedChar != 8;
	}
	
	public char getTypedChar()
	{
		return typedChar;
	}
	
	public static boolean isChar(int key)
	{
		return Keys.getKeyText(key).length() == 1;
	}
	
	public static char getChar(int key)
	{
		return Keys.getKeyText(key).charAt(0);
	}
	
	/**
	 * This class should not be constructed by any means, it's simply convenience for getting key constants.
	 * @author Brett
	 *
	 */
	public class Keys extends java.awt.event.KeyEvent
	{
		private static final long serialVersionUID = 1L;
		
		@SuppressWarnings("deprecation")
		public Keys(Component arg0, int arg1, long arg2, int arg3, int arg4)
		{
			super(arg0, arg1, arg2, arg3, arg4);
			// TODO Auto-generated constructor stub
		}
	}
}
