package rental.cosplay;

import javax.swing.JPanel;

/**
 * Controls navigation between different panels in the main application view.
 * Manages the dynamic content panel switching functionality.
 *
 * @author ASUS
 * @version 1.0
 */
public class NavigationController {

    /**
     * Navigates to a specified panel by replacing the current content panel.
     * Removes all existing components from the content panel and adds the new panel.
     *
     * @param view the main view containing the content panel
     * @param panel the new panel to display
     * @throws IllegalArgumentException if view or panel is null
     */
    public void goTo(MainView view, JPanel panel) {
        if (view == null) {
            throw new IllegalArgumentException("View cannot be null");
        }
        if (panel == null) {
            throw new IllegalArgumentException("Panel cannot be null");
        }

        JPanel contentPanel = view.getContentPanel();
        contentPanel.removeAll();
        contentPanel.add(panel);
        contentPanel.repaint();
        contentPanel.revalidate();
    }
}
