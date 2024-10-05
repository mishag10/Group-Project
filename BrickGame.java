import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class BrickGame extends JPanel implements ActionListener {
    
    private Timer timer;
    private final int ballSize = 20;
    private final int width = 800;
    private final int height = 500;
    private final int ballSpeed = 4;
    private final int rectSpeed = 10;
    private final int radius = 10;
    private int x = width / 2;
    private int y = height / 2;
    private int dx = ballSpeed;
    private int dy = ballSpeed;
    
    private final int rectWidth = 100;
    private final int rectHeight = 10;
    private final int brickWidth = 60;
    private final int brickHeight = 20;
    
	/*private void newLevel(){
		x = width / 2;
        y = height / 2;
        dx = ballSpeed;
        dy = ballSpeed;
		rectX = width / 4 - rectWidth / 4;
        rectY = height - rectHeight - 10;
	}*/
	
    private int rectX = width / 2 - rectWidth / 2;
    private int rectY = height - rectHeight - 30;

    private List<Rectangle> holes = new ArrayList<>();
	private List<Rectangle> bricks = new ArrayList<>();
    
    public BrickGame() {
        timer = new Timer(10, this);
        timer.start();
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.PINK);
        setFocusable(true);
		
		//holes
		holes.add(new Rectangle(60,60,25,50));
		
        for(int i = 0; i < 10; i++) {
            for (int k = 0; k < 5; k++) {
                int a = i * (brickWidth + 10) + 30;
                int b = k * (brickHeight + 10) + 50;
                bricks.add(new Rectangle(a, b, brickWidth, brickHeight));
            }
        }

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int left = KeyEvent.VK_LEFT;
                int right = KeyEvent.VK_RIGHT;
                if (e.getKeyCode() == left) {
                    rectX -= rectSpeed;
                }else if (e.getKeyCode() == right) {
                    rectX += rectSpeed;
                }
                rectX = Math.max(0, Math.min(width - rectWidth, rectX));
            }
        });
    }
    
    @Override
    protected void paintComponent(Graphics n) {
        super.paintComponent(n);
		
		// Draw a hole
		n.setColor(Color.RED);
		for(Rectangle hole:holes){
			n.fillOval(hole.x,hole.y, hole.width, hole.height);
		}
		
		//Bricks
        n.setColor(Color.GRAY);
        for (Rectangle brick : bricks) {
            n.fillRect(brick.x, brick.y, brick.width, brick.height);
        }
		
        // Draw the ball
        n.setColor(Color.BLACK);
        n.fillOval(x - radius, y - radius, radius * 2, radius * 2);
        
		//Draw the paddle
        n.setColor(Color.BLUE);
        n.fillRect(rectX, rectY, rectWidth, rectHeight);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        x += dx;
        y += dy;
        
        // Collision with bricks
        for (int v = bricks.size() - 1; v >= 0; v--) {
            Rectangle brick = bricks.get(v);
            if (brick.contains(x, y)) {
                dy = -dy;
                bricks.remove(v);
                break;
            }
        }
        
        // Check ball collision with paddle
        if (x >= rectX && x <= rectX + rectWidth && y + radius >= rectY && y - radius <= rectY + rectHeight) {
            dy = -dy;
            y = rectY - radius; // Prevent the ball from getting stuck in the paddle
        }
        
        // Check ball collision with walls
        if (x <= radius || x >= width - radius) {
            dx = -dx;
        }else if (y <= radius) {
            dy = -dy;
        }else if (y >= height - radius) {
            // Handle game over condition
            timer.stop();
            JOptionPane.showMessageDialog(this, "Game Over", "Level 1", JOptionPane.YES_NO_OPTION);
            System.exit(0);
        }
		for (Rectangle hole:holes){
			if(hole.contains(x,y)){
				timer.stop();
				JOptionPane.showMessageDialog(this, "Next level", "New Level", JOptionPane.YES_NO_OPTION);
				System.exit(0);
				//newLevel();
				//timer.start();
            }
        }
        
        repaint();
    }
	
    public static void main(String[] args) {
        JFrame frame = new JFrame("Brick Game");
        BrickGame panel = new BrickGame();
        frame.add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
