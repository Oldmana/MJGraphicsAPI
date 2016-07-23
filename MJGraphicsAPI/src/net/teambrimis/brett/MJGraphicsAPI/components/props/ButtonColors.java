package net.teambrimis.brett.MJGraphicsAPI.components.props;

import java.awt.Color;

public class ButtonColors
{
	private Color color = new Color(225, 225, 225);
	private Color borderColor = new Color(0, 0, 0);
	private Color hoverColor = new Color(220, 232, 240);
	private Color hoverBorderColor = new Color(0, 0, 150);
	private Color disabledColor = new Color(230, 230, 230);
	private Color disabledBorderColor = new Color(125, 125, 125);
	
	private Color textColor = new Color(0, 0, 0);
	private Color disabledTextColor = new Color(125, 125, 125);
	
	
	public ButtonColors()
	{
		
	}
	
	public void setColors(Color color, Color hoverColor, Color disabledColor, Color borderColor, Color hoverBorderColor
						, Color disabledBorderColor, Color textColor, Color disabledTextColor)
	{
		this.color = color;
		this.hoverColor = hoverColor;
		this.disabledColor = disabledColor;
		this.borderColor = borderColor;
		this.hoverBorderColor = hoverBorderColor;
		this.disabledBorderColor = disabledBorderColor;
		this.textColor = textColor;
		this.disabledTextColor = disabledTextColor;
	}
	
	public ButtonColors setColor(Color c)
	{
		color = c;
		return this;
	}
	
	public ButtonColors setHoverColor(Color c)
	{
		hoverColor = c;
		return this;
	}
	
	public ButtonColors setDisabledColor(Color c)
	{
		disabledColor = c;
		return this;
	}
	
	public ButtonColors setBorderColor(Color c)
	{
		borderColor = c;
		return this;
	}
	
	public ButtonColors setHoverBorderColor(Color c)
	{
		hoverBorderColor = c;
		return this;
	}
	
	public ButtonColors setDisabledBorderColor(Color c)
	{
		disabledBorderColor = c;
		return this;
	}
	
	public ButtonColors setTextColor(Color c)
	{
		textColor = c;
		return this;
	}
	
	public ButtonColors setDisabledTextColor(Color c)
	{
		disabledTextColor = c;
		return this;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public Color getHoverColor()
	{
		return hoverColor;
	}
	
	public Color getDisabledColor()
	{
		return disabledColor;
	}
	
	public Color getBorderColor()
	{
		return borderColor;
	}
	
	public Color getHoverBorderColor()
	{
		return hoverBorderColor;
	}
	
	public Color getDisabledBorderColor()
	{
		return disabledBorderColor;
	}
	
	public Color getTextColor()
	{
		return textColor;
	}
	
	public Color getDisabledTextColor()
	{
		return disabledTextColor;
	}
	
	public Color getCurrentColor(boolean enabled, int stage, int maxStage)
	{
		if (enabled)
		{
			Color c = new Color(color.getRed() + (((hoverColor.getRed() - color.getRed()) / maxStage) * stage),
					color.getGreen() + (((hoverColor.getGreen() - color.getGreen()) / maxStage) * stage),
					color.getBlue() + (((hoverColor.getBlue() - color.getBlue()) / maxStage) * stage));
			return c;
		}
		return disabledColor;
	}
	
	public Color getCurrentBorderColor(boolean enabled, int stage, int maxStage)
	{
		if (enabled)
		{
			Color c = new Color(borderColor.getRed() + (((hoverBorderColor.getRed() - borderColor.getRed()) / maxStage) * stage),
					borderColor.getGreen() + (((hoverBorderColor.getGreen() - borderColor.getGreen()) / maxStage) * stage),
					borderColor.getBlue() + (((hoverBorderColor.getBlue() - borderColor.getBlue()) / maxStage) * stage));
			return c;
		}
		return disabledBorderColor;
	}
	
	public Color getCurrentTextColor(boolean enabled)
	{
		if (enabled)
		{
			return textColor;
		}
		return disabledTextColor;
	}
}
