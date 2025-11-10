package rental.cosplay.components;


import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JScrollBar;

/**
 * Custom scrollbar with modern styling.
 * Uses ModernScrollBarUI for a sleek appearance.
 *
 * @author Rental Cosplay Team
 */
public class ScrollBarCustom extends JScrollBar {

    /**
     * Creates a new ScrollBarCustom with modern UI styling.
     * Sets preferred size and colors for the scrollbar.
     */
    public ScrollBarCustom() {
        setUI(new ModernScrollBarUI());
        setPreferredSize(new Dimension(8, 8));
        setForeground(new Color(48, 144, 216));
        setBackground(Color.WHITE);
    }
}