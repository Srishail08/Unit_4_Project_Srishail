import java.awt.*;


public class Platform extends GameObject {
    
    // Static Type constants
    public static final int TYPE_PLATFORM = 0; // Landing/Jumping surface
    public static final int TYPE_WALL = 1;     // Death wall
    public static final int TYPE_SHIP_OBSTACLE = 2; // Ship mode death block
    public static final int TYPE_GOAL = 3;     // Level end

    private int type;
    private Color color;

    public Platform(int x, int y, int w, int h, int type) {
        super(x, y, w, h);
        this.type = type;
        
        switch (type) {
            case TYPE_PLATFORM:
                this.color = Color.GRAY;
                break;
            case TYPE_WALL:
                this.color = Color.LIGHT_GRAY;
                break;
            case TYPE_SHIP_OBSTACLE:
                this.color = Color.MAGENTA;
                break;
            case TYPE_GOAL:
                this.color = Color.GREEN;
                break;
            default:
                this.color = Color.DARK_GRAY;
        }
    }

    public int getType() {
        return type;
    }

    @Override
    public void draw(Graphics2D g2, int scrollX) {
        g2.setColor(color);
        int drawX = x - scrollX;
        g2.fillRect(drawX, y, w, h);
    }
}