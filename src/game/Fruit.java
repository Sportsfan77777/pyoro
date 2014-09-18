package game;

import java.io.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;

/**
 * the fruit that will be eaten or dodged
 * (or it could kill the bird)
 * @author Sportsfan77777
 *
 */
public class Fruit
{
    private Random R;
    private int identity; // Normal = 1, Bonus = 2, Super = 4
    private int xcor, ycor;
    private boolean onscreen;
    private final int startycor = -20;
    private ImageIcon i; private Image img;
    private int height, width;
    private int score;
    private int speed;

    public Fruit()
    {  
      R = new Random();
      this.setIdentity();
      this.setSpeed();
      
      ycor = 0 - height + startycor;
      onscreen = false;
      score = 0;
    }
    
    public void setSpeed() {
    	int r = R.nextInt(100);
    	if (r < 10) speed = 1;
    	else if (r < 30) speed = 2;
    	else if (r < 60) speed = 3;
    	else if (r < 90) speed = 4;
    	else speed = 5;
    }

    /**
     * sets Fruit Type (normal, bonus, or super-bonus)
     */
    public void setIdentity()
    {
      xcor = R.nextInt(Grid.getGWidth() - 30);
      int r = R.nextInt(100);
      if (r < 4) // 4% chance of Super
      {
        identity = 4;
        i = new ImageIcon("fruitSuper.jpg");
        registerImage();
      }
      else if (r > 87) // 12% chance of Bonus
      {
        identity = 2;
        i = new ImageIcon("fruitBonus.jpg");
        registerImage();
      }
      else // 84% chance of Normal
      {
        identity = 1;
        i = new ImageIcon("fruitNormal.jpg");
        registerImage();
      }
    }
    
    public int getXcor()
    {    return xcor;    }

    public int getYcor()
    {    return ycor;    }

    public Image getImage()
    {    return img;    }
    
    public int getPlatformSquare()
    {    return (int)(((double)xcor + (double)width/2.0) / (double)Platform.getWidth());    }
    
    public int getScore()
    {    return score;    }

    public int getIdentity()
    {
      return identity;
    }

    public Rectangle getRectangle()
    {
      return new Rectangle((int)xcor, (int)ycor, width, height); // x, y, width, height
    }

    public void registerImage()
    {
      img = i.getImage();
      height = img.getHeight(null);
      width = img.getWidth(null);
    }
    
    /**
     * remove fruit from existence (eaten or hit ground)
     */
    public void resetFruit()
    {
      onscreen = false;
      this.setIdentity();
      if (ycor < Grid.getGHeight() / 4)
      {
        score = 300;
      }
      else if (ycor < Grid.getGHeight() / 4 * 2)
      {
        score = 100;
      }
      else if (ycor < Grid.getGHeight() / 4 * 3)
      {
        score = 50;
      }
      else if (ycor < Grid.getGHeight() / 4 * 4 + Platform.getHeight() + 1)
      {
        score = 10;
      }
      ycor = startycor;
    }

    public boolean onscreen()
    {
      return onscreen;
    }
    
    /**
     * drop the fruit
     */
    public void drop()
    {
      onscreen = true;
    }

    /**
     * move fruit with physics (neglect gravity)
     */
    public void move()
    {
  /*    if (ycor > Grid.getGHeight())
      {
        this.resetFruit();
      } 
      else */
        ycor += speed;
    }
}