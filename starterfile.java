a basic outline and code snippets to get you started on implementing the described game.For simplicity,
  I'll focus on creating the GUI and the basic interactions without a complex game loop.

To create this game, we'll use Java's Swing library for the GUI. Here's an outline of the steps we'll take:

Create a JFrame to hold the game.
Add birds and a bread bucket as graphical components.
Implement mouse events to control the bread's movement.
Check for collisions between the birds and the bread.
Keep track of the player's happy points and display them.
Let's start coding!


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BirdGame extends JFrame {
    private final int WINDOW_WIDTH = 800;
    private final int WINDOW_HEIGHT = 600;
    private final int BIRD_SIZE = 40;
    private final int BREAD_SIZE = 20;
    private final int BUCKET_WIDTH = 80;
    private final int BUCKET_HEIGHT = 100;
    private final int HAPPY_POINTS_PER_BREAD = 10;

    private List<Point> birds;
    private Point breadPosition;
    private Point bucketPosition;
    private int happyPoints;

    public BirdGame() {
        setTitle("Bird Game");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        birds = new ArrayList<>();
        breadPosition = new Point(-100, -100); // initialize bread off-screen
        bucketPosition = new Point(WINDOW_WIDTH / 2 - BUCKET_WIDTH / 2, WINDOW_HEIGHT - BUCKET_HEIGHT);
        happyPoints = 0;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                breadPosition.setLocation(e.getX() - BREAD_SIZE / 2, e.getY() - BREAD_SIZE / 2);
            }
        });

        // Add birds randomly
        spawnBirds(5);

        Timer timer = new Timer(100, e -> {
            moveBirds();
            checkCollisions();
            repaint();
        });
        timer.start();
    }

    private void spawnBirds(int count) {
        Random rand = new Random();
        for (int i = 0; i < count; i++) {
            int x = rand.nextInt(WINDOW_WIDTH - BIRD_SIZE);
            int y = rand.nextInt(WINDOW_HEIGHT - BIRD_SIZE - BUCKET_HEIGHT);
            birds.add(new Point(x, y));
        }
    }

    private void moveBirds() {
        Random rand = new Random();
        for (Point bird : birds) {
            bird.translate(rand.nextInt(5) - 2, rand.nextInt(5) - 2);
            // Ensure the bird stays inside the window
            bird.x = Math.max(0, Math.min(bird.x, WINDOW_WIDTH - BIRD_SIZE));
            bird.y = Math.max(0, Math.min(bird.y, WINDOW_HEIGHT - BIRD_SIZE - BUCKET_HEIGHT));
        }
    }

    private void checkCollisions() {
        Rectangle breadBounds = new Rectangle(breadPosition.x, breadPosition.y, BREAD_SIZE, BREAD_SIZE);
        Rectangle bucketBounds = new Rectangle(bucketPosition.x, bucketPosition.y, BUCKET_WIDTH, BUCKET_HEIGHT);

        for (int i = birds.size() - 1; i >= 0; i--) {
            Point bird = birds.get(i);
            Rectangle birdBounds = new Rectangle(bird.x, bird.y, BIRD_SIZE, BIRD_SIZE);

            if (birdBounds.intersects(breadBounds)) {
                birds.remove(i);
                happyPoints += HAPPY_POINTS_PER_BREAD;
            } else if (birdBounds.intersects(bucketBounds)) {
                birds.remove(i);
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        drawBirds(g);
        drawBread(g);
        drawBucket(g);
        drawHappyPoints(g);
    }

    private void drawBirds(Graphics g) {
        g.setColor(Color.RED);
        for (Point bird : birds) {
            g.fillOval(bird.x, bird.y, BIRD_SIZE, BIRD_SIZE);
        }
    }

    private void drawBread(Graphics g) {
        g.setColor(Color.ORANGE);
        g.fillOval(breadPosition.x, breadPosition.y, BREAD_SIZE, BREAD_SIZE);
    }

    private void drawBucket(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(bucketPosition.x, bucketPosition.y, BUCKET_WIDTH, BUCKET_HEIGHT);
    }

    private void drawHappyPoints(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawString("Happy Points: " + happyPoints, 20, 20);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BirdGame game = new BirdGame();
            game.setVisible(true);
        });
    }
}

Please note that this code provides only a basic implementation.
  To make it a full-fledged game, you may need to add more features, such as proper game mechanics, additional levels, and more advanced
  collision detection. Additionally, handling images for the birds, bread, and bucket could enhance the game's visual appeal. Nonetheless, 
this should serve as a starting point for your bird game project.
