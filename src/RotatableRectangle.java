import javax.swing.*;
import java.awt.*;

public class RotatableRectangle extends JComponent {
    private int x, y; // Position of the rectangle
    private double angle; // Rotation angle in radians

    public RotatableRectangle(int x, int y, double angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Set the color and draw the rectangle border
        g2d.setColor(Color.BLACK);

        g2d.setStroke(new BasicStroke(2));

        g2d.rotate(angle, x + 40, y + 40); // Rotate around the center
        g2d.drawRect(x, y, 80, 80); // Rectangle at (x, y) with width 80 and height 100
    }

    // Method to update position
    public void setPosition(int newX, int newY) {
        this.x = newX;
        this.y = newY;
        repaint(); // Redraw the component
    }

    // Method to update rotation angle
    public void setRotation(double newAngle) {
        this.angle = Math.toRadians(newAngle);
        repaint(); // Redraw the component
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(82, 88); // Set preferred size for layout managers
    }
}
