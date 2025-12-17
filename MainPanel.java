import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;

public class MainPanel extends JPanel {
    private CardLayout card = new CardLayout();

    private LoginPage login = new LoginPage();
    private SignupPage signup = new SignupPage();
    
    private MenuPanel menu = new MenuPanel();
    private GamePanel game = new GamePanel();

    private Controller controller; 
   
    public MainPanel() throws FileNotFoundException {
        this.setLayout(card);
        
        // Initialize controller *after* creating all components
        this.controller = new Controller(menu, game, this, login, signup);
        
        this.add(login, "Login");
        this.add(signup, "Signup");
        this.add(menu, "Menu");
        this.add(game, "Game");

        showLogin();
    }

    public void showLogin() {
        card.show(this, "Login");
    }

    public void showSignup() {
        card.show(this, "Signup");
    }
    
    public void showMenu() {
        card.show(this, "Menu");
    }

    public void showGame() {
        card.show(this, "Game");
    }
}