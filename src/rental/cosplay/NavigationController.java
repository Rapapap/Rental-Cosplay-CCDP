/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rental.cosplay;

import javax.swing.JPanel;

/**
 *
 * @author ASUS
 */
public class NavigationController {
    public void goTo(MainView view ,JPanel panel){
       view.getContentPanel().removeAll();
       view.getContentPanel().add(panel);
       view.getContentPanel().repaint();
       view.getContentPanel().revalidate();
    }
}
