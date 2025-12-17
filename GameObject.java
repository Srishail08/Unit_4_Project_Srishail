import java.awt.*;

// Class is used as an abstract start for all game objects (Spike, Portal, Player)
public abstract class GameObject {
    public int x, y, w, h;

    //Constructor for gameObject with its position as a paramater
    public GameObject(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    //Calling abstract method for subclasses
    public abstract void draw(Graphics2D g2, int scrollX);

    //Getting bounds for the object as they are all rect based
    public Rectangle getRectangleBounds(int scrollX) {
        return new Rectangle(x - scrollX, y, w, h);
    }
}