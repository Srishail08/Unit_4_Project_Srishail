import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;; 

//Class controls buttons on login and signup page
public class Controller {

    private String currentUsername; 

    public Controller(MenuPanel menu, GamePanel game, MainPanel main, LoginPage login, SignupPage signup) {
        

        game.setController(this);

        //Login and signup button actions using lambda expressions

        login.getSwitchButton().addActionListener(e -> main.showSignup());
        
        signup.getSwitchButton().addActionListener(e -> {
            signup.getStatusLabel().setText("");
            main.showLogin();
        });
        
        //Login submit
        
        login.getSubmitButton().addActionListener(e -> {
            HashMap<String, String> userAccounts = new HashMap<>(); 
            login.getErrorLabel().setText("");

            // Read Content.txt
            try {
                File file = new File("Content.txt");
                Scanner input = new Scanner(file); 
                while (input.hasNextLine()) {
                    String info = input.nextLine();
                    String[] list = info.split(",");
                    if (list.length >= 2) {
                        userAccounts.put(list[0].trim(), list[1].trim()); 
                    }
                }
                input.close();
            } catch (FileNotFoundException a) {

            }

            String username = login.getUsername().trim(); 
            String password = login.getPassword().trim();
            
            boolean authenticated = userAccounts.containsKey(username) && password.equals(userAccounts.get(username));

            if (authenticated) {
                currentUsername = username; 
                main.showMenu();
            } else {
                login.getErrorLabel().setText("Invalid Username or Password.");
            }
        });
        
        //Signup submit

        signup.getSubmitButton().addActionListener(e -> {
            signup.getStatusLabel().setText(""); 
            signup.getStatusLabel().setForeground(Color.RED); 
            
            String userName = signup.getName().trim();
            String userPassword = signup.getPassword().trim();
            String confirm = signup.getConfirmPassword().trim();
        
            if (userName.isEmpty() || userPassword.isEmpty()){
                signup.getStatusLabel().setText("Username and Password fields must be filled.");
                return;
            }
            
            if (!confirm.equals(userPassword)){
                signup.getStatusLabel().setText("Passwords do not match.");
                return;
            } 

            String information = userName + "," + userPassword;
            try {
                FileWriter writer  = new FileWriter("Content.txt", true);
                writer.write(information + "\n");
                writer.close();
                
                signup.getStatusLabel().setText("Account created successfully! Please log in.");
                signup.getStatusLabel().setForeground(Color.GREEN.darker());
                
                signup.getNameField().setText("");
                signup.getPasswordField().setText("");
                signup.getConfirmField().setText("");
                
            } catch (IOException f) {
                JOptionPane.showMessageDialog(main, "Error saving account data: " + f.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
                signup.getStatusLabel().setText("An unexpected file error occurred.");
            }
        });

        //  Buttons used in the Game

        menu.getBackToLoginButton().addActionListener(e -> {
            main.showLogin();
            currentUsername = null; 
        });

        menu.getplayButton().addActionListener(e -> {
            main.showGame();
            game.resetGame();
        });

        game.getRestartButton().addActionListener(e -> game.resetGame());

        game.getQuitButton().addActionListener(e -> main.showMenu());
    }
    
    public void saveScore(int score) {

        // Formated by Username, Score%
        String scoreData = currentUsername + "," + score + "%\n";
        
        try {
            FileWriter writer = new FileWriter("ScoreLog.txt", true);
            writer.write(scoreData);
            writer.close();
        } catch (IOException a) {
           
        }
    }
}
