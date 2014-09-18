package game;

import java.io.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;

/**
 * Tongue of the Bird
 * @author Sportsfan77777
 *
 */
public class Tongue
{
	private boolean launched; // only if launched, tongue will be seen
    private boolean pyorodirection;
	
    private int x1;
    private int y1;
    private int x2;
    private int y2;
    
    private ImageIcon i; 
    private Image img;
    private int height;
    private int width;
    
    private int offset; // the tongue is not at the top corner
    
    private int speed;
   
    public Tongue(Bird b)
    {
      x1 = b.getTL_xcor();
      y1 = b.getTL_ycor();
      x2 = x1; 
      y2 = y1;
      
      launched = false;
      pyorodirection = true;
      
      i = new ImageIcon("tongueR.jpg");
      registerImage();
      
      speed = 12 * Grid.TIMER_TIME / 5;
    }

    public Image getImage()
    {    return img;    }
    
    public int getXcor1()
    {    return x1;    }
    
    public int getYcor1()
    {    return y1;    }
    
    public int getXcor2()
    {    return x2;    }
    
    public int getYcor2()
    {    return y2;    }
    
    public int getHeight() {
    	return height;
    }
    
    public int getWidth() {
    	return width;
    }
    
    public int offset() {
    	return offset;
    }
    
    public void registerImage()
    {
      img = i.getImage();
      height = img.getHeight(null);
      width = img.getWidth(null);
      offset = height * 2 / 5;
    }

    public Rectangle getRectangle()
    {    return new Rectangle(x2, y2, width, height);    }

    /**
     * launch the tongue so the bird can eat
     * @param b
     */
    public void launch(Bird b)
    {    
      launched = true;
      if (b.getRightDirection()) // Facing Right
      {
        x1 = b.getBR_xcor(); 
        x2 = x1;
        if ((b.getRightDirection()) && (b.getRightDirection()!= pyorodirection))
        {
          //x2 += b.getWidth();
          //x1 += b.getWidth();
          pyorodirection = b.getRightDirection();
        }
        i = new ImageIcon("tongueR.jpg");
        registerImage();
      }
      else // Facing Left
      {
        x1 = b.getTL_xcor(); 
        x2 = x1;
        if ((!b.getRightDirection()) && (b.getRightDirection() != pyorodirection))
        {
          //x2 -= b.getWidth();
          //x1 -= b.getWidth();
          pyorodirection = b.getRightDirection();
        }
        i = new ImageIcon("tongueL.jpg");
        registerImage();
      }
    }

    /**
     * retract the tongue, so that it can be launched again
     * in the future
     */
    public void retract()
    {    
      launched = false;
      y2 = y1;
      x2 = x1;
    }

    public boolean getLaunched()
    {    return launched;    }

    /**
     * move tongue with physics (neglect gravity)
     * @param b
     */
    public void move(Bird b)
    {
 /*     if /*((y2 - b.getYcor() < Grid.getGHeight()) || 
          (x2 - b.getXcor() < Grid.getGWidth()) ||
          (x2 < 0) || 
          (x2 > Grid.getGWidth())
         && this.getLaunched()) (!((y2 < 0) || (y2 > Grid.getGWidth()) || (y2 < Grid.getGWidth())))*/
      if (this.x2 < Grid.getGHeight() && (y2 < Grid.getGWidth() && y2 > 0)) {
    	  if (b.getRightDirection())
	      {
	          x2 += speed*Math.cos(45*Math.PI/180);
	          y2 -= speed*Math.sin(45*Math.PI/180);
	      }
	      else
	      {
	          x2 -= speed*Math.cos(45*Math.PI/180);
	          y2 -= speed*Math.sin(45*Math.PI/180);
	      }
      }
      else
      {
        this.retract();
      }
    }
}
