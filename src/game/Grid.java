package game;

import java.io.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.Toolkit;

public class Grid extends JPanel implements ActionListener
{
    private FruitCollection fruitC;
    private Bird pyoro;
    private Platform plat;
    private static int height = 406;
    private static int width = 460;
    private javax.swing.Timer timer;
    private boolean ingame;
    private int highscore;
    private static String newline = System.getProperty("line.separator");
    private Random R;
    private BufferedImage img;
    public static final int TIMER_TIME = 15;
    
    public Grid()
    {
      try   {
        img = ImageIO.read(new File("PyoroCity.jpg"));
        /* width = img.getWidth();
        height = img.getHeight(); */
      }
      catch (IOException io)
      {
        System.out.println(io);
        System.exit(0);
      } 
    
 
      addKeyListener(new TAdapter());
  //    addKeyListener(new UAdapter());
      setFocusable(true);
      setDoubleBuffered(true);
      
      this.newGame();
      
      highscore = 0;
      R = new Random();
      timer = new javax.swing.Timer(TIMER_TIME, this);
      timer.start();
    }
    
    public void addNotify()
    {
      super.addNotify();
    }
    
    public void newGame()
    {
      ingame = true;
      pyoro = new Bird(this);
      fruitC = new FruitCollection();
      plat = new Platform();
    }
    
    public Platform getPlatform()
    {    return plat;    }
    
    public Bird getPyoro()
    {    return pyoro;    }
    
    public ArrayList<Fruit> getFruits()
    {    return fruitC.getFruits();    }
    
    public boolean gameover()
    {    return !ingame;    }
    
    public static int getGHeight()
    {    return height;    }
    
    public static int getGWidth()
    {    return width;    }
    
    public void paint(Graphics g)
    {
      super.paint(g);
      if (ingame)
      {
        Graphics2D g2d = (Graphics2D)g;
        
        g2d.drawImage(img, 0, 0, this);
        
        g2d.drawImage(pyoro.getImage(), pyoro.getTL_xcor(), pyoro.getTL_ycor(), this);
        
        Tongue t = pyoro.getTongue();
        if (t.getLaunched())
        {
        	g2d.setColor(Color.pink);
            g2d.setStroke(new BasicStroke(3));
            if (pyoro.getRightDirection()) {
            	g2d.drawImage(t.getImage(), t.getXcor2() - t.getWidth(), t.getYcor2() + t.offset(), this);  
		        g2d.drawLine(pyoro.getBR_xcor(), pyoro.getTL_ycor() + t.offset(), 
		        		t.getXcor2() - t.getWidth(), t.getYcor2() + t.getHeight() + t.offset());
            }
            else { // facing left
            	g2d.drawImage(t.getImage(), t.getXcor2(), t.getYcor2() + t.offset(), this);  
            	g2d.drawLine(pyoro.getTL_xcor(), pyoro.getTL_ycor() + t.offset(), 
            			t.getXcor2() + t.getWidth(), t.getYcor2() + t.getHeight() + t.offset());
            }
          	g2d.setColor(Color.black);
        }
        
        for (int i = 0; i < fruitC.getFruits().size(); i++)
        {
          Fruit f = fruitC.getFruits().get(i);
          if (f.onscreen())
          {
            g2d.drawImage(f.getImage(), f.getXcor(), f.getYcor(), this);
          }
        }
        
        for (int i = 0; i < plat.getPlatform().length; i++)
        {
          if (plat.getPlatform()[i])
          {
            g2d.drawImage(plat.getImage(), plat.getWidth()*(i), Grid.getGHeight() - plat.getHeight(), this);
          }
        }
        
        String s1 = "SCORE " + fruitC.getScore() + "                HIGH SCORE " + highscore;
        Font f = new Font("Arial", Font.BOLD, 88);
        FontMetrics fm = this.getFontMetrics(f);
        g2d.setColor(Color.white);
        g2d.drawString(s1, 12, 12); // String, Width, Height
        
   //     System.out.println(pyoro.getPlatformSquare());
      }
      else
      {
        Graphics2D g2d = (Graphics2D)g;
        g2d.drawImage(img, 0, 0, this);
        if (fruitC.getScore() > highscore) highscore = fruitC.getScore();
        String s1 = "SCORE " + fruitC.getScore() + "                HIGH SCORE " + highscore;
        Font f = new Font("Arial", Font.BOLD, 88);
        FontMetrics fm = this.getFontMetrics(f);
        g2d.setColor(Color.white);
        g2d.drawString(s1, 12, 12);
        String s2 = "Game Over!" + newline + " Press n for New Game";
        Font f2 = new Font("Arial", Font.BOLD, 88);
        FontMetrics fm2 = this.getFontMetrics(f2);
        g2d.setColor(Color.white);
        g2d.drawString(s2, 120, 200); //(s2, (width - fm2.stringWidth(s2)) / 2, 200); // centered on screen
      }
      Toolkit.getDefaultToolkit().sync();
      g.dispose();
    }
    
    public void actionPerformed(ActionEvent e)
    {
      if (pyoro.died())
      {
        ingame = false;
      }
      
      int max = 60000;
      int denom = 100 * TIMER_TIME / 15;
      int num = denom - 1;
      int r = R.nextInt(max); 
      int selectfruit = (int)(Math.random()*(double)fruitC.getFruits().size()); // R.nextInt(fruitC.getFruits().size());
      
      if (r > ( (max * num / denom) - (fruitC.getScore() * 2/5) ) )
      {
        if (!fruitC.getFruits().get(selectfruit).onscreen())
        {
          fruitC.getFruits().get(selectfruit).drop();
        }
      }
      
      for (int i = 0; i < fruitC.getFruits().size(); i++)
      {
        Fruit f = fruitC.getFruits().get(i);
        if (f.onscreen())
        {
          f.move();
        }
      }

      pyoro.setDirection();
      if (!pyoro.getTongue().getLaunched())
      {
        pyoro.move(plat);
      }
      
      if (pyoro.getTongue().getLaunched())
      {
        pyoro.getTongue().move(pyoro);
      }
      
      checkCollisions();
      repaint();
    }
    
    public void checkCollisions()
    {
      Tongue t = pyoro.getTongue();
      Rectangle pyoroR = pyoro.getRectangle();
      boolean b = t.getLaunched();
      for (int i = 0; i < fruitC.getFruits().size(); i++)
      {
        Fruit f = fruitC.getFruits().get(i);
        Rectangle r = f.getRectangle();
        if (b)
        {
          Rectangle psv = t.getRectangle();
          if (r.intersects(psv)) // Fruit intersects Tongue
          {
            t.retract();
            if (f.getIdentity()==4)
            {
              plat.fillPlatform();
            }
            if (f.getIdentity()==2)
            {
              plat.addPlatform();
            }
            f.resetFruit();
            fruitC.addScore(f.getScore());
          }
        }
        if (f.getYcor() > (Grid.getGHeight() - Platform.getHeight())) // Fruit intersects Platform
        {
          plat.destroyPlatform(f.getPlatformSquare(), pyoro);
          f.resetFruit();
        }
        if (r.intersects(pyoroR)) // Fruit Intersects Bird
        {
          pyoro.kill();
        }
      }
    }

    public void keyPressed(KeyEvent e)
    {
      int key = e.getKeyCode();
      
      if (key == KeyEvent.VK_N)
      {
        this.newGame();
      }
    } 

    
private class TAdapter extends KeyAdapter
{
  public void keyPressed(KeyEvent e)
  {    pyoro.keyPressed(e);    }
  
  public void keyReleased(KeyEvent e)
  {    pyoro.keyReleased(e);    }
}

/*private class UAdapter extends KeyAdapter
{
  public void keyPressed(KeyEvent e)
  {    this.keyPressed(e);    } 
}*/ 

}