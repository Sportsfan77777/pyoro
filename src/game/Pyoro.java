package game;

import java.io.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;

public class Pyoro extends JFrame
{
    public Pyoro()
    {
        add(new Grid());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Grid.getGWidth(), Grid.getGHeight()+Platform.getHeight());
        setLocationRelativeTo(null);
        setTitle("Pyoro");
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) 
    {   new Pyoro();  }
}
