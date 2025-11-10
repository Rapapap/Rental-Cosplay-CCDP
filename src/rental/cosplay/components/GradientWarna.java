package rental.cosplay.components;

import javax.swing.*;
import java.awt.*;

/**
 * Custom JPanel with gradient background.
 * Creates a vertical gradient from pink to white.
 *
 * @author Rental Cosplay Team
 */
public class GradientWarna extends JPanel {

    /**
     * Creates a new GradientWarna panel with default gradient colors.
     */
    public GradientWarna() {
        super();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Ubah ke Graphics2D
        Graphics2D g2d = (Graphics2D) g;

        // Ukuran panel
        int width = getWidth();
        int height = getHeight();

        // Gradien warna dari atas ke bawah
        GradientPaint gp = new GradientPaint(
                0, 0, new Color(255, 182, 193), // Warna atas (pink muda)
                0, height, Color.WHITE         // Warna bawah (putih)
        );

        g2d.setPaint(gp);
        g2d.fillRect(0, 0, width, height); // Isi seluruh panel
    }
}
