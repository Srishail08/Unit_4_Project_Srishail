import java.awt.Dimension;
import javax.swing.*;
import java.awt.Font;
import java.awt.Color;

public class SignupPage extends JPanel {
    
    private JTextField insertName = new JTextField(15);
    private JPasswordField insertPassword = new JPasswordField(15);
    private JPasswordField confirmPassword = new JPasswordField(15);
    private JButton submit = new JButton("SIGN UP");
    private JButton switchToLogin = new JButton("BACK TO LOGIN");
    private JLabel statusLabel = new JLabel(""); 

    public SignupPage() {
        this.setPreferredSize(new Dimension(800, 400));
        SpringLayout layout = new SpringLayout();
        this.setLayout(layout);

        JLabel title = new JLabel("CREATE ACCOUNT");
        title.setFont(new Font("SansSerif", Font.BOLD, 40));
        
        JLabel name = new JLabel("Username: ");
        JLabel password = new JLabel("New Password: ");
        JLabel confirmation = new JLabel("Confirm Password: ");

        statusLabel.setForeground(Color.GREEN.darker()); 


        
        // Title
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, title, 0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.NORTH, title, 50, SpringLayout.NORTH, this);
        
        // Username Row
        layout.putConstraint(SpringLayout.WEST, name, 250, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, name, 150, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, insertName, 350, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, insertName, 150, SpringLayout.NORTH, this);

        // Password Row
        layout.putConstraint(SpringLayout.WEST, password, 250, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, password, 190, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, insertPassword, 350, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, insertPassword, 190, SpringLayout.NORTH, this);

        // Confirm Password Row
        layout.putConstraint(SpringLayout.WEST, confirmation, 250, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, confirmation, 230, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, confirmPassword, 360, SpringLayout.WEST, this); // Adjusted slightly for label width
        layout.putConstraint(SpringLayout.NORTH, confirmPassword, 230, SpringLayout.NORTH, this);

        // Status Label
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, statusLabel, 0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.NORTH, statusLabel, 260, SpringLayout.NORTH, this);

        // SIGN UP Button
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, submit, 0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.NORTH, submit, 290, SpringLayout.NORTH, this);
        
        // BACK TO LOGIN Button
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, switchToLogin, 0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.NORTH, switchToLogin, 340, SpringLayout.NORTH, this);

        this.add(title);
        this.add(name);
        this.add(insertName);
        this.add(password);
        this.add(insertPassword);
        this.add(confirmation);
        this.add(confirmPassword);
        this.add(submit);
        this.add(switchToLogin);
        this.add(statusLabel);
    }
    
    // Accessor methods for the Controller
    public JButton getSubmitButton() { return submit; }
    public JButton getSwitchButton() { return switchToLogin; }
    public String getName() { return insertName.getText(); }
    public String getPassword() { return new String(insertPassword.getPassword()); }
    public String getConfirmPassword() { return new String(confirmPassword.getPassword()); }
    public JLabel getStatusLabel() { return statusLabel; }
    

    public JTextField getNameField() { return insertName; }
    public JPasswordField getPasswordField() { return insertPassword; }
    public JPasswordField getConfirmField() { return confirmPassword; }
}