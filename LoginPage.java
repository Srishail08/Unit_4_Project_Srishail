import java.awt.*;
import javax.swing.*;

public class LoginPage extends JPanel {
    
    private JTextField enterName = new JTextField(10);
    private JPasswordField enterPassword = new JPasswordField(10); 
    private JButton submit = new JButton("LOG IN");
    private JButton switchToSignup = new JButton("CREATE ACCOUNT");
    private JLabel errorLabel = new JLabel(""); 

    public LoginPage() {
        this.setPreferredSize(new Dimension(800, 400));
        SpringLayout springLayout = new SpringLayout();
        this.setLayout(springLayout);

        JLabel title = new JLabel("BLOCK JUMP LOGIN");
        title.setFont(new Font("SansSerif", Font.BOLD, 40));
        
        JLabel name = new JLabel("Username: ");
        JLabel password = new JLabel("Password: ");

        errorLabel.setForeground(Color.RED);
        
        // Adding constraints
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, title, 0, SpringLayout.HORIZONTAL_CENTER, this);
        springLayout.putConstraint(SpringLayout.NORTH, title, 50, SpringLayout.NORTH, this);
        
        springLayout.putConstraint(SpringLayout.WEST, name, 250, SpringLayout.WEST, this);
        springLayout.putConstraint(SpringLayout.NORTH, name, 150, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.WEST, enterName, 350, SpringLayout.WEST, this);
        springLayout.putConstraint(SpringLayout.NORTH, enterName, 150, SpringLayout.NORTH, this);

        springLayout.putConstraint(SpringLayout.WEST, password, 250, SpringLayout.WEST, this);
        springLayout.putConstraint(SpringLayout.NORTH, password, 200, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.WEST, enterPassword, 350, SpringLayout.WEST, this);
        springLayout.putConstraint(SpringLayout.NORTH, enterPassword, 200, SpringLayout.NORTH, this);

        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, errorLabel, 0, SpringLayout.HORIZONTAL_CENTER, this);
        springLayout.putConstraint(SpringLayout.NORTH, errorLabel, 230, SpringLayout.NORTH, this);

        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, submit, -50, SpringLayout.HORIZONTAL_CENTER, this);
        springLayout.putConstraint(SpringLayout.NORTH, submit, 260, SpringLayout.NORTH, this);
        
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, switchToSignup, 100, SpringLayout.HORIZONTAL_CENTER, this);
        springLayout.putConstraint(SpringLayout.NORTH, switchToSignup, 260, SpringLayout.NORTH, this);
        
        this.add(title);
        this.add(submit);
        this.add(switchToSignup);
        this.add(name);
        this.add(enterName);
        this.add(password);
        this.add(enterPassword);
        this.add(errorLabel);
    }
    
    // Accessor methods for the Controller
    public JButton getSubmitButton() { return submit; }
    public JButton getSwitchButton() { return switchToSignup; }
    public String getUsername() { return enterName.getText(); }
    public String getPassword() { return new String(enterPassword.getPassword()); }
    public JLabel getErrorLabel() { return errorLabel; }
}