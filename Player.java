import java.awt.*;

public class Player {
    // Constants
    public static final int SIZE = 30;
    private static final int GRAVITY = 1;
    private static final int JUMP_STRENGTH = -15;
    private static final int SHIP_LIFT = -8;
    
    // Position & Movement
    private int x, y;
    private int yVel;
    private double rotation;
    private boolean onGround;
    
    // State
    private boolean shipMode;

    public Player(int startX, int startY) {
        this.x = startX;
        this.reset(startY);
    }

    public void reset(int startY) {
        this.y = startY;
        this.yVel = 0;
        this.rotation = 0;
        this.onGround = true;
        this.shipMode = false;
    }

    public void update(int heightLimit) {
        if (shipMode) {
            y += yVel;
            yVel += 1; // Ship gravity

            // Clamp ship inside screen
            if (y < 0) y = 0;
            if (y > heightLimit - SIZE - 50) y = heightLimit - SIZE - 50;
        } else {
            if (!onGround) {
                yVel += GRAVITY;
                rotation += Math.toRadians(12);
            }
            y += yVel;
        }
    }

    public void jump() {
        if (shipMode) {
            yVel = SHIP_LIFT;
        } else if (onGround) {
            yVel = JUMP_STRENGTH;
            onGround = false;
        }
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.BLUE);
        
        if (shipMode) {
            int[] px = {x, x, x + SIZE};
            int[] py = {y, y + SIZE, y + SIZE / 2};
            g2.fillPolygon(px, py, 3);
        } else {
            if (!onGround) {
                g2.rotate(rotation, x + SIZE / 2.0, y + SIZE / 2.0);
            }
            g2.fillRect(x, y, SIZE, SIZE);
            if (!onGround) {
                g2.rotate(-rotation, x + SIZE / 2.0, y + SIZE / 2.0);
            }
        }
    }

    // Platform collision handling
    public void landOnPlatform(int platformY) {
        y = platformY - SIZE;
        yVel = 0;
        rotation = 0;
        onGround = true;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public boolean isOnGround() { return onGround; }
    public void setShipMode(boolean mode) { this.shipMode = mode; }
    public boolean isShipMode() { return shipMode; }
    public void setYVel(int vel) { this.yVel = vel; }
    public int getYVel() { return yVel; }
    public int getX() { return x; }
    public int getY() { return y; }
    public Rectangle getBounds() { return new Rectangle(x, y, SIZE, SIZE); }
}