import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

//The game is on this panel
public class GamePanel extends JPanel implements ActionListener, KeyListener, MouseListener {

    //setting variables
    private final int WIDTH = 800;
    private final int HEIGHT = 400;

    private Timer timer;
    private Player player;

    private ArrayList<GameObject> level; 
    private LevelBuilder levelBuilder;
    private Controller controller; 

    // Game State
    private int scrollX = 0;
    private final int speed = 6;
    private int levelEndX = 0;
    private int currentPercent = 0;
    private int prevPlayerY = 0;
    
    private JButton restartButton  = new JButton("RESTART");
    private JButton quitButton  = new JButton("MENU");
    private JPanel gameoverScreen = new JPanel();
    
    private JLabel gameOverLabel = new JLabel("GAME OVER");
    private JLabel scoreLabel = new JLabel("Final Score: 0%");

    //setting constructor
    public GamePanel() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        
        addKeyListener(this);
        addMouseListener(this);
        setFocusable(true);
        
        //  Setup gameoverScreen panel
        gameoverScreen.setLayout(new BoxLayout(gameoverScreen, BoxLayout.Y_AXIS));
        gameoverScreen.setBackground(Color.BLACK);
        gameoverScreen.setBounds(0, 0, WIDTH, HEIGHT);
        gameoverScreen.setVisible(false);
        
        // Setup Labels
        gameOverLabel.setForeground(Color.RED);
        gameOverLabel.setFont(new Font("SansSerif", Font.BOLD, 48));
        gameOverLabel.setAlignmentX(Component.CENTER_ALIGNMENT); 
        
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(new Font("SansSerif", Font.PLAIN, 24));
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT); 

        //  Add components to gameoverScreen 
        gameoverScreen.add(Box.createVerticalGlue());
        gameoverScreen.add(gameOverLabel);
        gameoverScreen.add(Box.createRigidArea(new Dimension(0, 20)));
        gameoverScreen.add(scoreLabel);
        gameoverScreen.add(Box.createRigidArea(new Dimension(0, 30)));
        gameoverScreen.add(restartButton);
        gameoverScreen.add(Box.createRigidArea(new Dimension(0, 10)));
        gameoverScreen.add(quitButton);
        gameoverScreen.add(Box.createVerticalGlue());
        
        this.add(gameoverScreen);
        
        //creating level builder object to build the level
        levelBuilder = new LevelBuilder(HEIGHT);

        //make player object
        player = new Player(70, HEIGHT - 50 - Player.SIZE);
    }
    
    //make controller object to change pages, and use buttons
    public void setController(Controller c) {
        this.controller = c;
    }

    //reset game button
    public void resetGame() {
        gameoverScreen.setVisible(false);
        
        // Reset labels
        gameOverLabel.setText("GAME OVER");
        gameOverLabel.setForeground(Color.RED);
        scoreLabel.setText("Final Score: 0%");
        
        player.reset(HEIGHT - 50 - Player.SIZE);
        prevPlayerY = player.getY();
        
        //restart scrollX and my current percent
        scrollX = 0;
        currentPercent = 0;

        level = levelBuilder.buildLevel();
        
        if (!level.isEmpty()) {
            levelEndX = level.get(level.size() - 1).x;
        }

        if (timer != null) timer.stop();
        timer = new Timer(20, this);
        timer.start();
        requestFocusInWindow();
        //request focus method msKim was talking about
    }

    //
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        if (timer != null && timer.isRunning() && !gameoverScreen.isVisible()) {
            // Draw Ground
            g2.setColor(Color.DARK_GRAY);
            g2.fillRect(0, HEIGHT - 50, WIDTH, 50);

            // Draw Level Objects
            for (GameObject obj : level) {
                obj.draw(g2, scrollX);
            }

            // Draw Player
            player.draw(g2);

            // Draw Percentage 
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 24));

        //Scroll X + player X to get total distance travelled, divide by level end X to get percent            
            double progress = (double) (scrollX + player.getX()) / levelEndX;
            currentPercent = (int) (progress * 100);
            
            //incase of any bugs where the percent is out of bounds
            if (currentPercent < 0) currentPercent = 0;
            if (currentPercent > 100) currentPercent = 100;
            
            //Convert percent to string and center it
            String percentStr = currentPercent + "%";
            
            FontMetrics fm = g2.getFontMetrics();
            int textWidth = fm.stringWidth(percentStr);
            g2.drawString(percentStr, (WIDTH / 2) - (textWidth / 2), 40);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Game update logic 
        prevPlayerY = player.getY();
        //Scroll X is the camera moving right, at the speed variable every (20ms from the timer)
        scrollX += speed;

        player.update(HEIGHT);

        boolean standingOnPlatform = false;
        Rectangle playerRect = player.getBounds();

        for (GameObject obj : level) {
            
            //If the object is off screen, skip all the checks for it
            if (obj.x - scrollX + obj.w < -50 || obj.x - scrollX > WIDTH + 50) {
                continue;
            }

            // Platfrom collisions, as the top is safe but the sides and bottom are not
            if (obj instanceof Platform) {
                Platform platform = (Platform) obj;
                Rectangle objRect = platform.getRectangleBounds(scrollX);

                if (platform.getType() == Platform.TYPE_PLATFORM && !player.isShipMode()) {
                    //if the player was above the platform in the last frame than they are falling on top, so its safe
                    int prevBottom = prevPlayerY + Player.SIZE;
                    boolean wasAbove = prevBottom <= platform.y;

                    if (playerRect.intersects(objRect)) {
                        //if they were above and falling down onto the platform, make them land
                        if (wasAbove && player.getYVel() >= 0) {
                            player.landOnPlatform(platform.y);
                            standingOnPlatform = true;
                        } else {
                            gameOver();
                            return;
                        }
                    }
                }
                
                // Hitting walls as a ship results in game over
                if ((platform.getType() == Platform.TYPE_WALL || platform.getType() == Platform.TYPE_SHIP_OBSTACLE) 
                    && playerRect.intersects(objRect)) {
                    gameOver();
                    return;
                }
                
                // If they get to the end they win
                if (platform.getType() == Platform.TYPE_GOAL && playerRect.intersects(objRect)) {
                    currentPercent = 100;
                    repaint(); 
                    winGame();
                    return;
                }
            }
            
            //Check for Portal collisions
            else if (obj instanceof Portal) {
                Portal portal = (Portal) obj;
                Rectangle objRect = portal.getRectangleBounds(scrollX);

                // Entering blue portal puts you in ship mode, orange takes you out
                if (playerRect.intersects(objRect)) {
                    if (portal.getType() == Portal.TYPE_BLUE) {
                        player.setShipMode(true);
                        player.setYVel(0);
                    } else if (portal.getType() == Portal.TYPE_ORANGE) {
                        player.setShipMode(false);
                        player.setYVel(0);
                    }
                }
            }
            
            //Check for Triangle collisions
            else if (obj instanceof Spike) {
                Spike spike = (Spike) obj;
                
                if (!player.isShipMode()) {
                    if (triangleCollision(playerRect, spike.triX, spike.triY)) {
                        gameOver();
                        return;
                    }
                }
            }
        }

        // Gravity and ground Physics 
        if (!player.isShipMode()) {
            int groundY = HEIGHT - 50 - Player.SIZE;
            if (player.getY() >= groundY) {
                player.reset(groundY); 
                player.setOnGround(true); 
            } else if (!standingOnPlatform) {
                player.setOnGround(false);
            } else {
                player.setOnGround(true);
            }
        }
        
        // Check for player falling below screen 
        if (player.getY() > HEIGHT + 10) {
             gameOver();
             return;
        }

        //repaint with new info
        repaint();
    }

    // Triangle Collision Detection
    private boolean triangleCollision(Rectangle rect, int[] triX, int[] triY) {
        int[][] points = {
            {rect.x, rect.y},
            {rect.x + rect.width, rect.y},
            {rect.x, rect.y + rect.height},
            {rect.x + rect.width, rect.y + rect.height}
        };
        for (int[] p : points) {
            if (pointInTriangle(p[0], p[1], triX[0], triY[0], triX[1], triY[1], triX[2], triY[2])) {
                return true;
            }
        }
        return false;
    }

    private boolean pointInTriangle(int px, int py, int x1, int y1, int x2, int y2, int x3, int y3) {
        int dX = px - x3;
        int dY = py - y3;
        int dX21 = x3 - x2;
        int dY12 = y2 - y1;
        int D = dY12 * (x1 - x3) + dX21 * (y1 - y3);
        double s = (dY12 * dX + dX21 * dY) / (double) D;
        double t = ((y3 - y1) * dX + (x1 - x3) * dY) / (double) D;
        return s >= 0 && t >= 0 && (s + t) <= 1;
    }


    // Game Ending 

    private void gameOver() {
        timer.stop();
        
        if (controller != null) {
            controller.saveScore(currentPercent);
        }
        
        scoreLabel.setText("Final Score: " + currentPercent + "%");

        gameoverScreen.setVisible(true);
    }

    private void winGame() {
        timer.stop();
        
        if (controller != null) {
            controller.saveScore(100);
        }
        
        gameOverLabel.setText("LEVEL COMPLETE!");
        gameOverLabel.setForeground(Color.GREEN);
        scoreLabel.setText("Final Score: 100%");
        
        gameoverScreen.setVisible(true);
    }

    public JButton getRestartButton(){
        return restartButton;
    }

    public JButton getQuitButton(){
        return quitButton;
    }

    // Action Listeners
    @Override public void mouseClicked(MouseEvent e) { player.jump(); }
    @Override public void keyPressed(KeyEvent e) { if (e.getKeyCode() == KeyEvent.VK_SPACE) player.jump(); }
    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}
