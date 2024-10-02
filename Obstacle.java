package character;

import java.awt.*;

public class Obstacle {
    public int x;
    public int y;
    public int width;
    public int height;

    public Obstacle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.RED);
        g2d.fillRect(x, y, width, height);
    }

    public boolean checkCollision(int objX, int objY, int objWidth, int objHeight) {
        Rectangle obstacleRect = new Rectangle(x, y, width, height);
        Rectangle objRect = new Rectangle(objX, objY, objWidth, objHeight);
        return obstacleRect.intersects(objRect);
    }
}
