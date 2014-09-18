package game;

import java.io.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;

/**
 * the platform on which the Bird stands
 * @author Sportsfan77777
 *
 */
public class Platform
{
    private boolean[] platform; // 0 = destroyed, 1 = still there
    private int leftlimit;
    private int rightlimit; // where the bird can go
    
    private static final int num_sq = 20;
    
    private static int height = 24;
    private static int width = Grid.getGWidth() / num_sq;
    
    private Image img;

    public Platform()
    {
      ImageIcon i = new ImageIcon("platsquare.jpg");
      img = i.getImage();
      platform = new boolean[num_sq];
      this.fillPlatform();
    }

    public static int getHeight()
    {    return height;    }

    public static int getWidth()
    {    return width;    }

    public Image getImage()
    {    return img;    }

    public void fillPlatform()
    {
      for (int i = 0; i < platform.length; i++)
      {    platform[i] = true;    }
      leftlimit = 0;
      rightlimit = num_sq - 1;
    }

    /**
     * extends the platform boundary by 1 on
     * either the left or right side.
     * if there is no boundary on the left side,
     * the right side is extended.
     * if there is no boundary on the right side,
     * the left side is extended.
     * if the platform is filled, does nothing
     */
    public void addPlatform()
    {
      boolean reset = false;
      if (leftlimit == 0 && rightlimit != num_sq - 1) // only a right limit
      {
        rightlimit += 1;
        platform[rightlimit] = true;
        
        for (int i = rightlimit; i < num_sq && !reset; i++) {
      	  if (!platform[i]) {
      		  rightlimit = i - 1;
      		  reset = true;
      	  }
        }
        if (!reset) // there is no right limit
        	rightlimit = num_sq - 1;
      }
      else if (leftlimit != 0 && rightlimit == num_sq - 1) // only a left limit
      {
        leftlimit -= 1;
        platform[leftlimit] = true;
        
        for (int i = leftlimit; i >= 0 && !reset; i--) {
        
      	  if (!platform[i] && !reset) {
      		  leftlimit = i + 1;
      		  reset = true;
      	  }
        }
        if (!reset) // there is no left limit
        	leftlimit = 0;
      }
      else if (leftlimit != 0 && rightlimit != num_sq - 1) // both limits
      {
        double random = Math.random();
        if (random < 0.5) { 
          // add to the left
          leftlimit = leftlimit - 1;
          platform[leftlimit] = true;
          
          for (int i = leftlimit; i >= 0 && !reset; i--) {
        	  if (!platform[i] && !reset) {
        		  leftlimit = i + 1;
        		  reset = true;
        	  }
          }
          if (!reset) // there is no left limit
    		  leftlimit = 0;
        }
        else {
          // add to the right
          rightlimit = rightlimit + 1;
          platform[rightlimit] = true;
          
          for (int i = rightlimit; i < num_sq && !reset; i++) {
        	  if (!platform[i] && !reset) {
        		  rightlimit = i - 1;
        		  reset = true;
        	  }
          }
          if (!reset) // there is no right limit
    		  rightlimit = num_sq - 1;
        }
      }
    }

    /**
     * removes square K from the platform
     * updates limits so that the left and right limits
     * are the leftmost and rightmost squares on which
     * the bird b is allowed 
     * @param K the square to be removed
     * @param b the bird
     */
    public void destroyPlatform(int K, Bird b)
    {
      if (platform[K])
      {
        platform[K] = false;
        if (b.getPlatformSquare() > K) { 
          // bird is to right of destroyed square
          if (K + 1 > leftlimit)
          {
            leftlimit = K + 1;
          }
        }
        else { 
          // bird is to left of destroyed square
          if (K - 1 < rightlimit)
          {
            rightlimit = K - 1;
          }
        }
      }
    }

    public boolean[] getPlatform()
    {
      return platform;
    }

    /**
     * the leftmost square which the bird can occupy
     * @return
     */
    public int getLeftLimit() {
    	return sqToX(1.0*leftlimit - 0.2);
    }

    /**
     * the rightmost square which the bird can occupy
     * @return
     */
    public int getRightLimit() {
    	return sqToX(1.0*rightlimit + 0.2);
    }
    
    private int sqToX(double sq) {
    	return (int) (sq * width) + (width / 2);
    }
}