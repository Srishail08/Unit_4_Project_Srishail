import java.awt.*;


public class Portal extends GameObject {
    public static final int TYPE_BLUE = 0;   // Cube -> Ship
    public static final int TYPE_ORANGE = 1; // Ship -> Cube

    private int type;
    private Color color;

    public Portal(int x, int y, int w, int h, int type) {
        super(x, y, w, h);
        this.type = type;
        this.color = (type == TYPE_BLUE) ? Color.CYAN : Color.ORANGE;
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