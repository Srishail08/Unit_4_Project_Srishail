import java.io.*;
import javax.swing.*;

//Main file that I run the game on
public class Game {
    public static void main(String[] args) throws FileNotFoundException {
        //make frame, and dont resize
        JFrame frame = new JFrame("Block Jump - Java Swing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        
        //Add mainPanel (The panel that holds all other panels)
        MainPanel main = new MainPanel();
        frame.add(main);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
}