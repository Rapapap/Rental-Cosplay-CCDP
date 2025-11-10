package org.netbeans.lib.awtextra;

import java.io.Serializable;

/**
 * Minimal AbsoluteConstraints implementation to support NetBeans GUI builder
 * generated code without requiring the external AbsoluteLayout library.
 * Width/height of -1 means use the component's preferred size.
 */
public class AbsoluteConstraints implements Serializable {
    private static final long serialVersionUID = 1L;

    public int x;
    public int y;
    public int width;
    public int height;

    /**
     * Creates constraints at the given position using the component's
     * preferred size.
     */
    public AbsoluteConstraints(int x, int y) {
        this(x, y, -1, -1);
    }

    /**
     * Creates constraints at the given position and size.
     * @param x x-coordinate
     * @param y y-coordinate
     * @param width width in pixels or -1 for preferred width
     * @param height height in pixels or -1 for preferred height
     */
    public AbsoluteConstraints(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}

