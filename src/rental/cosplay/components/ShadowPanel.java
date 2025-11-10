package rental.cosplay.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 * Custom JPanel with shadow and rounded border.
 * Provides a panel with configurable shadow size and color.
 *
 * @author Rental Cosplay Team
 */
public class ShadowPanel extends JPanel {
    private int shadowSize;
    private Color shadowColor;
    private Color borderColor = new Color(0xDFDFDF); // Border color #DFDFDF

    /**
     * Creates a new ShadowPanel with specified shadow properties.
     *
     * @param shadowSize the size of the shadow in pixels
     * @param shadowColor the color of the shadow
     */
    public ShadowPanel(int shadowSize, Color shadowColor) {
        this.shadowSize = shadowSize;
        this.shadowColor = shadowColor;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        int width = getWidth();
        int height = getHeight();
        int shadowOffset = shadowSize / 2;

        // Create the shadow image
        BufferedImage shadow = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = shadow.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw shadow
        g2d.setColor(shadowColor);
        g2d.fillRoundRect(shadowOffset, shadowOffset, width - shadowSize, height - shadowSize, 12, 12);

        g2d.dispose();

        // Draw shadow on the panel
        g2.drawImage(shadow, 0, 0, null);

        // Draw panel background
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, width - shadowSize, height - shadowSize, 12, 12);

        // Draw border
        g2.setColor(borderColor);
        g2.drawRoundRect(0, 0, width - shadowSize - 1, height - shadowSize - 1, 12, 12); // Border is drawn around the panel
    }
}
