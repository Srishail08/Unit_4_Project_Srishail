import java.util.*;

//Class builds the level wtih all spikes
public class LevelBuilder {

    private final int HEIGHT;

    public LevelBuilder(int height) {
        this.HEIGHT = height;
    }

    // Returns a list of the abstract base type GameObject
    public ArrayList<GameObject> buildLevel() { 
        ArrayList<GameObject> level = new ArrayList<>();
        int x = 600;
        x += 300;

        spike(level, x);      x += 150;
        spike(level, x);      x += 220;

        tripleSpike(level, x);   x += 300;
        x += 200;

        tripleSpike(level, x);   x += 350;

        spike(level, x);      x += 200;
        spike(level, x);      x += 200;

        platform(level, x, 120);   x += 250; 
        spike(level, x);      x += 200; 
        platform(level, x, 150);   x += 250; 
        spike(level, x);      x += 220;

        tripleSpike(level, x);   x += 400;

        // Blue portal
        portal(level, x, HEIGHT - 50 - 150, 60, 150, true);   
        x += 400;  

        // Ship section
        shipObstacle(level, x, HEIGHT - 50 - 200, 40, 150); x += 200;
        shipObstacle(level, x, HEIGHT - 50 - 120, 40, 120); x += 250;
        shipObstacle(level, x, HEIGHT - 50 - 180, 40, 180); x += 200;

        // Orange portal
        x += 300; 
        portal(level, x, HEIGHT - 50 - 150, 60, 150, false);  
        int orangePortalX = x;
        int orangePortalY = HEIGHT - 50 - 150;
        int orangePortalW = 60;
        int orangePortalH = 150;

        // Grey wall structure
        wall(level, orangePortalX - 200, 0, 200, orangePortalY); 
        wall(level, orangePortalX - 200, orangePortalY + orangePortalH, 200, HEIGHT - (orangePortalY + orangePortalH)); 
        wall(level, orangePortalX + orangePortalW, 0, 200, orangePortalY); 
        wall(level, orangePortalX + orangePortalW, orangePortalY + orangePortalH, 200, HEIGHT - (orangePortalY + orangePortalH)); 
        wall(level, orangePortalX, 0, orangePortalW, orangePortalY); 

        x += 300;

        // Spikes after orange portal
        spike(level, x); x += 150;
        spike(level, x); x += 180;
        tripleSpike(level, x); x += 300;

        // Goal wall
        goalWall(level, x, 0, 50, HEIGHT);
        
        return level;
    }

    private void spike(ArrayList<GameObject> level, int x) {
        level.add(new Spike(x, HEIGHT - 50 - 30, 30, 30));
    }

    private void tripleSpike(ArrayList<GameObject> level, int x) {
        spike(level, x);
        spike(level, x + 35);
        spike(level, x + 70);
    }

    private void platform(ArrayList<GameObject> level, int x, int width) {
        level.add(new Platform(x, HEIGHT - 80, width, 30, Platform.TYPE_PLATFORM));
    }

    private void portal(ArrayList<GameObject> level, int x, int y, int w, int h, boolean blue) {
        int type = blue ? Portal.TYPE_BLUE : Portal.TYPE_ORANGE;
        level.add(new Portal(x, y, w, h, type));
    }

    private void wall(ArrayList<GameObject> level, int x, int y, int w, int h) {
        level.add(new Platform(x, y, w, h, Platform.TYPE_WALL));
    }

    private void shipObstacle(ArrayList<GameObject> level, int x, int y, int w, int h) {
        level.add(new Platform(x, y, w, h, Platform.TYPE_SHIP_OBSTACLE));
    }
    
    private void goalWall(ArrayList<GameObject> level, int x, int y, int w, int h) {
        level.add(new Platform(x, y, w, h, Platform.TYPE_GOAL));
    }
}
