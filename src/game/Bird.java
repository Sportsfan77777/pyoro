package game;

import java.io.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.Toolkit;
import javax.imageio.*;

public class Bird
{
	private Grid grid;
    private Tongue tongue;
    
    private int xcor; // Left x-cor
    private int ycor; // Top y-cor (lower #)
    
    private ImageIcon i; 
    private Image img;
    private int height;
    private int width;
    
    private boolean alive;
    private boolean facingRight;
   
    private int dx; // change in x when bird is moved 
    private final int DELTA_X = 3 * Grid.TIMER_TIME / 5;
   
    public Bird(Grid g) {
    	
      i = new ImageIcon("pyoroR.jpg");
      registerImage();
      
      grid = g;
      xcor = Grid.getGWidth() / 2;
      ycor = Grid.getGHeight() - Platform.getHeight() - height;
      dx = 0;
      alive = true;
      facingRight = true;
      tongue = new Tongue(this);
    }

    public int getTL_xcor() {
    	return xcor;    
    }

    public int getTL_ycor() {
    	return ycor;    
    }
    
    public int getBR_xcor() {
    	return xcor + width;
    }
    
    public int getBR_ycor() {
    	return ycor + height;
    }

    public Image getImage() {
        return img;
    }
    
    public int getHeight() {
    	return height;    
    }

    public int getWidth() {
    	return width;    
    }
    
    public int getPlatformSquare() {
    	return (int)( ( (double)xcor + (double)width/2.0) 
    		/ Platform.getWidth() );    
    }
    
    public Tongue getTongue()
    {    return tongue;    }

    public void kill()
    {    alive = false;    }

    public boolean died()
    {    return !alive;    }

    public boolean getRightDirection()
    {    return facingRight;    }
    
    public Rectangle getRectangle()
    {
      return new Rectangle(xcor, ycor, width, height);
    }
    
    public void registerImage()
    {
      img = i.getImage();
      height = img.getHeight(null);
      width = img.getWidth(null);
    }

    public void setDirection()
    {
      if (!facingRight && tongue.getLaunched() && dx == 0) {
        i = new ImageIcon("pyoroRo2.jpg");
        registerImage();
      }
      else if (facingRight && tongue.getLaunched() && dx == 0) {
        i = new ImageIcon("pyoroLo2.jpg");
        registerImage();
      }
      else if (dx > 0) {
        i = new ImageIcon("pyoroR.jpg");
        registerImage();
        facingRight = true;
      }
      else if (dx < 0) {
        i = new ImageIcon("pyoroL.jpg");
        registerImage();
        facingRight = false;
      }
      else if (dx == 0 && !tongue.getLaunched() && facingRight) {
        i = new ImageIcon("pyoroR.jpg");
        registerImage();
      }
      else if (dx == 0 && !tongue.getLaunched() && !facingRight) {
        i = new ImageIcon("pyoroL.jpg");
        registerImage();
      }
    }
    
    /**
     * moves the bird according to key presses and releases
     * provided the platform hasn't been destroyed where it
     * wants to go
     * @param p current platform
     */
    public void move(Platform p) {
      if (!( ((xcor + width/2) + dx < p.getLeftLimit()) 
    		 || ( (xcor + width/2) + dx > p.getRightLimit())) ) {
    	  xcor += dx;
      }
    }
    
    /**
     * handles keyboard commands
     * space = launch tongue
     * left = move left
     * right = move right
     * @param e
     */
    public void keyPressed(KeyEvent e)
    {
      int key = e.getKeyCode();
      
      if (key == KeyEvent.VK_SPACE) {
        if (!tongue.getLaunched() && !grid.gameover())
        {
        	tongue.launch(this);
        }
      }
      
      if (key == KeyEvent.VK_N) {
        if (grid.gameover()) grid.newGame();
      }
      
      if (key == KeyEvent.VK_LEFT) {
        if (!tongue.getLaunched()) 
        	dx = -DELTA_X;
      }
      
      if (key == KeyEvent.VK_RIGHT) {
        if (!tongue.getLaunched())
          dx = DELTA_X;
      }
    }
    
    public void keyReleased(KeyEvent e)
    {
      int key = e.getKeyCode();
      
      if (key == KeyEvent.VK_SPACE) {
    	  tongue.retract();
      }
      
      if (key == KeyEvent.VK_LEFT) {
    	  dx = 0;   
      }
      
      if (key == KeyEvent.VK_RIGHT) {
    	  dx = 0;
      }
    }
}