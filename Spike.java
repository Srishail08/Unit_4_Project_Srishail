import java.awt.*;


public class Spike extends GameObject {
    

    public int[] triX = new int[3];
    public int[] triY = new int[3];

    public Spike(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    @Override
    public void draw(Graphics2D g2, int scrollX) {
        g2.setColor(Color.RED);
        int drawX = x - scrollX;
        
        // Update triangle points for collision checking in GamePanel
        triX[0] = drawX;          triY[0] = y + h;
        triX[1] = drawX + w / 2;  triY[1] = y;
        triX[2] = drawX + w;      triY[2] = y + h;
        
        g2.fillPolygon(triX, triY, 3);
    }
}