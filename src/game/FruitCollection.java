package game;

import java.io.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;

/**
 * collection of all of the fruit in the game
 * @author Sportsfan77777
 *
 */
public class FruitCollection
{
    private ArrayList<Fruit> fruits;
    private int score;

    public FruitCollection()
    {
      fruits = new ArrayList<Fruit>();
      this.addFruits(20);
    }

    public int getScore()
    {    return score;    }

    public ArrayList<Fruit> getFruits()
    {    return fruits;    }

    public void addFruits(int K)
    {
      for (int i = 0; i < K; i++)
      {
        fruits.add(new Fruit());
      }
    }

    public void addScore(int K)
    {
      score += K;
    }
}