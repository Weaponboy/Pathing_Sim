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

        // Rectangle dimensions and center
        int rectWidth = 62;
        int rectHeight = 70;
        int centerX = x + rectWidth / 2;
        int centerY = y + rectHeight / 2;

        // Rotate the rectangle
        g2d.rotate(angle, centerX, centerY);
        g2d.drawRect(x, y, rectWidth, rectHeight);
        g2d.rotate(-angle, centerX, centerY); // Reset rotation

        // Set the color for the line
        g2d.setColor(Color.RED);

        // Adjust angle for the opposite side
        double adjustedAngle = angle - (Math.PI/2); // Add π (180 degrees) to flip the side

        // Calculate the endpoint of the line
        double lineLength = Math.sqrt((rectWidth / 2.0) * (rectWidth / 2.0) + (rectHeight / 2.0) * (rectHeight / 2.0));
        int lineEndX = (int) (centerX + lineLength * Math.cos(adjustedAngle));
        int lineEndY = (int) (centerY + lineLength * Math.sin(adjustedAngle));

        // Draw the line
        g2d.drawLine(centerX, centerY, lineEndX, lineEndY);
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
