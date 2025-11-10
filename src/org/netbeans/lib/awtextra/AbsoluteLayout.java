package org.netbeans.lib.awtextra;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Minimal AbsoluteLayout implementation to avoid requiring the external
 * NetBeans AbsoluteLayout library at compile/javadoc time.
 *
 * This layout places components at fixed x/y coordinates. If width/height is
 * -1, the component's preferred size is used.
 */
public class AbsoluteLayout implements LayoutManager2, Serializable {
    private static final long serialVersionUID = 1L;

    private final Map<Component, AbsoluteConstraints> constraintsMap = new HashMap<>();

    @Override
    public void addLayoutComponent(String name, Component comp) {
        // No-op (use addLayoutComponent(Component, Object))
    }

    @Override
    public void removeLayoutComponent(Component comp) {
        constraintsMap.remove(comp);
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        synchronized (parent.getTreeLock()) {
            Insets insets = parent.getInsets();
            int maxX = 0, maxY = 0;
            for (Component comp : parent.getComponents()) {
                AbsoluteConstraints ac = constraintsMap.get(comp);
                if (ac == null) continue;
                Dimension d = comp.getPreferredSize();
                int w = ac.width < 0 ? d.width : ac.width;
                int h = ac.height < 0 ? d.height : ac.height;
                maxX = Math.max(maxX, ac.x + w);
                maxY = Math.max(maxY, ac.y + h);
            }
            return new Dimension(insets.left + insets.right + maxX,
                                 insets.top + insets.bottom + maxY);
        }
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return preferredLayoutSize(parent);
    }

    @Override
    public void layoutContainer(Container parent) {
        synchronized (parent.getTreeLock()) {
            for (Component comp : parent.getComponents()) {
                AbsoluteConstraints ac = constraintsMap.get(comp);
                if (ac == null) continue;
                Dimension d = comp.getPreferredSize();
                int w = ac.width < 0 ? d.width : ac.width;
                int h = ac.height < 0 ? d.height : ac.height;
                comp.setBounds(ac.x, ac.y, w, h);
            }
        }
    }

    @Override
    public void addLayoutComponent(Component comp, Object constraints) {
        if (constraints instanceof AbsoluteConstraints) {
            constraintsMap.put(comp, (AbsoluteConstraints) constraints);
        } else {
            // If no constraints or wrong type, place at 0,0 preferred size
            constraintsMap.put(comp, new AbsoluteConstraints(0, 0));
        }
    }

    @Override
    public Dimension maximumLayoutSize(Container target) {
        return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    @Override
    public float getLayoutAlignmentX(Container target) {
        return 0.5f;
    }

    @Override
    public float getLayoutAlignmentY(Container target) {
        return 0.5f;
    }

    @Override
    public void invalidateLayout(Container target) {
        // No-op
    }
}

