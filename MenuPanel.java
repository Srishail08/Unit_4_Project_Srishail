import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel{
   private SpringLayout layout = new SpringLayout();
   private JButton play = new JButton("PLAY");
   private JButton backToLogin = new JButton("LOGOUT"); 
   private JLabel title = new JLabel("BLOCK JUMP");

   public MenuPanel(){
    this.setLayout(layout);
   
    Font titleFont = new Font("SansSerif", Font.BOLD, 100);

    title.setFont(titleFont);

    // Title positioning
    layout.putConstraint(SpringLayout.WEST, title, 60, SpringLayout.WEST, this);
    layout.putConstraint(SpringLayout.NORTH, title, 90, SpringLayout.NORTH, this);
    
    // PLAY button positioning - CENTERED
    layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, play, 0, SpringLayout.HORIZONTAL_CENTER, this);
    layout.putConstraint(SpringLayout.NORTH, play, 250, SpringLayout.NORTH, this);
    
    // LOGOUT button positioning - CENTERED
    layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, backToLogin, 0, SpringLayout.HORIZONTAL_CENTER, this);
    layout.putConstraint(SpringLayout.NORTH, backToLogin, 300, SpringLayout.NORTH, this); 
    
    this.add(play);
    this.add(backToLogin);
    this.add(title);
   }

   public JButton getplayButton(){
    return play;
   }
   
   public JButton getBackToLoginButton(){
       return backToLogin;
   }
}