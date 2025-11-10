package rental.cosplay.components;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

/**
 * Custom JPanel with rounded corners.
 * Provides a panel with configurable corner radius and background color.
 *
 * @author Rental Cosplay Team
 */
public class RoundedPanel extends JPanel {
    private int radius;
    private Color backgroundColor;

    /**
     * Creates a new RoundedPanel with specified corner radius and background color.
     *
     * @param radius the corner radius in pixels
     * @param color the background color
     */
    public RoundedPanel(int radius, Color color) {
        this.radius = radius;
        this.backgroundColor = color;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(backgroundColor);
        g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), radius, radius));
    }

    @Override
    public void setBackground(Color color) {
        this.backgroundColor = color;
        super.setBackground(color);
    }
}
